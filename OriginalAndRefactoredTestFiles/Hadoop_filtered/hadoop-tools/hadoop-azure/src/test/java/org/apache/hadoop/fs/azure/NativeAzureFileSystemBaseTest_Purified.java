package org.apache.hadoop.fs.azure;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.TimeZone;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.XAttrSetFlag;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;
import org.apache.hadoop.fs.azure.NativeAzureFileSystem.FolderRenamePending;
import com.microsoft.azure.storage.AccessCondition;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.hadoop.fs.azure.integration.AzureTestUtils.readStringFromFile;
import static org.apache.hadoop.fs.azure.integration.AzureTestUtils.writeStringToFile;
import static org.apache.hadoop.fs.azure.integration.AzureTestUtils.writeStringToStream;
import static org.apache.hadoop.test.GenericTestUtils.*;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;

public abstract class NativeAzureFileSystemBaseTest_Purified extends AbstractWasbTestBase {

    private final long modifiedTimeErrorMargin = 5 * 1000;

    private static final short READ_WRITE_PERMISSIONS = 644;

    private static final EnumSet<XAttrSetFlag> CREATE_FLAG = EnumSet.of(XAttrSetFlag.CREATE);

    private static final EnumSet<XAttrSetFlag> REPLACE_FLAG = EnumSet.of(XAttrSetFlag.REPLACE);

    public static final Logger LOG = LoggerFactory.getLogger(NativeAzureFileSystemBaseTest.class);

    protected NativeAzureFileSystem fs;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        fs = getFileSystem();
    }

    public void assertPathDoesNotExist(String message, Path path) throws IOException {
        ContractTestUtils.assertPathDoesNotExist(fs, message, path);
    }

    public void assertPathExists(String message, Path path) throws IOException {
        ContractTestUtils.assertPathExists(fs, message, path);
    }

    private static FsPermission ignoreStickyBit(FsPermission original) {
        return new FsPermission(original.getUserAction(), original.getGroupAction(), original.getOtherAction());
    }

    private static void assertEqualsIgnoreStickyBit(FsPermission expected, FsPermission actual) {
        assertEquals(ignoreStickyBit(expected), ignoreStickyBit(actual));
    }

    private static enum RenameVariation {

        NormalFileName, SourceInAFolder, SourceWithSpace, SourceWithPlusAndPercent
    }

    private enum RenameFolderVariation {

        CreateFolderAndInnerFile, CreateJustInnerFile, CreateJustFolder
    }

    private class FileFolder {

        private String name;

        private boolean present;

        ArrayList<FileFolder> members;

        public FileFolder(String name) {
            this.name = name;
            this.present = true;
            members = new ArrayList<FileFolder>();
        }

        public FileFolder getMember(int i) {
            return members.get(i);
        }

        public void verifyGone() throws IllegalArgumentException, IOException {
            assertFalse(fs.exists(new Path(name)));
            assertTrue(isFolder());
            verifyGone(new Path(name), members);
        }

        private void verifyGone(Path prefix, ArrayList<FileFolder> members2) throws IOException {
            for (FileFolder f : members2) {
                f.verifyGone(prefix);
            }
        }

        private void verifyGone(Path prefix) throws IOException {
            assertFalse(fs.exists(new Path(prefix, name)));
            if (isLeaf()) {
                return;
            }
            for (FileFolder f : members) {
                f.verifyGone(new Path(prefix, name));
            }
        }

        public void verifyExists() throws IllegalArgumentException, IOException {
            assertTrue(fs.exists(new Path(name)));
            assertTrue(isFolder());
            verifyExists(new Path(name), members);
        }

        private void verifyExists(Path prefix, ArrayList<FileFolder> members2) throws IOException {
            for (FileFolder f : members2) {
                f.verifyExists(prefix);
            }
        }

        private void verifyExists(Path prefix) throws IOException {
            assertTrue(fs.exists(new Path(prefix, name)));
            if (isLeaf()) {
                return;
            }
            for (FileFolder f : members) {
                f.verifyExists(new Path(prefix, name));
            }
        }

        public boolean exists() throws IOException {
            return fs.exists(new Path(name));
        }

        public void makeRenamePending(FileFolder dst) throws IOException {
            Path home = fs.getHomeDirectory();
            String relativeHomeDir = getRelativePath(home.toString());
            NativeAzureFileSystem.FolderRenamePending pending = new NativeAzureFileSystem.FolderRenamePending(relativeHomeDir + "/" + this.getName(), relativeHomeDir + "/" + dst.getName(), null, (NativeAzureFileSystem) fs);
            String renameDescription = pending.makeRenamePendingFileContents();
            final String renamePendingStr = this.getName() + "-RenamePending.json";
            Path renamePendingFile = new Path(renamePendingStr);
            FSDataOutputStream out = fs.create(renamePendingFile, true);
            assertTrue(out != null);
            writeStringToStream(out, renameDescription);
        }

        public void setPresent(int i, boolean b) {
            members.get(i).setPresent(b);
        }

        private FileFolder() {
            this.present = true;
        }

        public void setPresent(boolean value) {
            present = value;
        }

        public FileFolder makeLeaf(String name) {
            FileFolder f = new FileFolder();
            f.setName(name);
            return f;
        }

        void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean isLeaf() {
            return members == null;
        }

        public boolean isFolder() {
            return members != null;
        }

        FileFolder add(FileFolder folder) {
            members.add(folder);
            return this;
        }

        FileFolder add(String file) {
            FileFolder leaf = makeLeaf(file);
            members.add(leaf);
            return this;
        }

        public FileFolder copy() {
            if (isLeaf()) {
                return makeLeaf(name);
            } else {
                FileFolder f = new FileFolder(name);
                for (FileFolder member : members) {
                    f.add(member.copy());
                }
                return f;
            }
        }

        public void create() throws IllegalArgumentException, IOException {
            create(null);
        }

        private void create(Path prefix) throws IllegalArgumentException, IOException {
            if (isFolder()) {
                if (present) {
                    assertTrue(fs.mkdirs(makePath(prefix, name)));
                }
                create(makePath(prefix, name), members);
            } else if (isLeaf()) {
                if (present) {
                    assertTrue(fs.createNewFile(makePath(prefix, name)));
                }
            } else {
                assertTrue("The object must be a (leaf) file or a folder.", false);
            }
        }

        private void create(Path prefix, ArrayList<FileFolder> members2) throws IllegalArgumentException, IOException {
            for (FileFolder f : members2) {
                f.create(prefix);
            }
        }

        private Path makePath(Path prefix, String name) {
            if (prefix == null) {
                return new Path(name);
            } else {
                return new Path(prefix, name);
            }
        }

        public void prune() throws IOException {
            prune(null);
        }

        private void prune(Path prefix) throws IOException {
            Path path = null;
            if (prefix == null) {
                path = new Path(name);
            } else {
                path = new Path(prefix, name);
            }
            if (isLeaf() && !present) {
                assertTrue(fs.delete(path, false));
            } else if (isFolder() && !present) {
                assertTrue(fs.delete(path, true));
            } else if (isFolder()) {
                for (FileFolder f : members) {
                    f.prune(path);
                }
            }
        }
    }

    private String getRelativePath(String path) {
        int slashCount = 0;
        int i;
        for (i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '/') {
                slashCount++;
                if (slashCount == 3) {
                    return path.substring(i + 1, path.length());
                }
            }
        }
        throw new RuntimeException("Incorrect path prefix -- expected wasb://.../...");
    }

    int FILE_SIZE = 4 * 1024 * 1024 + 1;

    int MAX_STRIDE = FILE_SIZE + 1;

    Path PATH = new Path("/available.dat");

    private void verifyAvailable(int readStride) throws IOException {
        FSDataInputStream in = fs.open(PATH);
        try {
            byte[] inputBuffer = new byte[MAX_STRIDE];
            int position = 0;
            int bytesRead = 0;
            while (bytesRead != FILE_SIZE) {
                bytesRead += in.read(inputBuffer, position, readStride);
                int available = in.available();
                if (bytesRead < FILE_SIZE) {
                    if (available < 1) {
                        fail(String.format("expected available > 0 but got: " + "position = %d, bytesRead = %d, in.available() = %d", position, bytesRead, available));
                    }
                }
            }
            int available = in.available();
            assertTrue(available == 0);
        } finally {
            in.close();
        }
    }

    private void createEmptyFile(Path testFile, FsPermission permission) throws IOException {
        FSDataOutputStream outputStream = fs.create(testFile, permission, true, 4096, (short) 1, 1024, null);
        outputStream.close();
    }

    private String readString(Path testFile) throws IOException {
        return readStringFromFile(fs, testFile);
    }

    private void writeString(Path path, String value) throws IOException {
        writeStringToFile(fs, path, value);
    }

    private long firstEndTime;

    private long secondStartTime;

    private class LeaseLockAction implements Runnable {

        private String name;

        private String key;

        LeaseLockAction(String name, String key) {
            this.name = name;
            this.key = key;
        }

        @Override
        public void run() {
            LOG.info("starting thread " + name);
            SelfRenewingLease lease = null;
            NativeAzureFileSystem nfs = (NativeAzureFileSystem) fs;
            if (name.equals("first-thread")) {
                try {
                    lease = nfs.getStore().acquireLease(key);
                    LOG.info(name + " acquired lease " + lease.getLeaseID());
                } catch (AzureException e) {
                    assertTrue("Unanticipated exception", false);
                }
                assertTrue(lease != null);
                try {
                    Thread.sleep(SelfRenewingLease.LEASE_RENEWAL_PERIOD + 2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                try {
                    firstEndTime = System.currentTimeMillis();
                    lease.free();
                    LOG.info(name + " freed lease " + lease.getLeaseID());
                } catch (StorageException e) {
                    fail("Unanticipated exception");
                }
            } else if (name.equals("second-thread")) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                try {
                    LOG.info(name + " before getting lease");
                    lease = nfs.getStore().acquireLease(key);
                    secondStartTime = System.currentTimeMillis();
                    LOG.info(name + " acquired lease " + lease.getLeaseID());
                } catch (AzureException e) {
                    assertTrue("Unanticipated exception", false);
                }
                assertTrue(lease != null);
                try {
                    lease.free();
                    LOG.info(name + " freed lease " + lease.getLeaseID());
                } catch (StorageException e) {
                    assertTrue("Unanticipated exception", false);
                }
            } else {
                fail("Unknown thread name");
            }
            LOG.info(name + " is exiting.");
        }
    }

    @Test
    public void testListSlash_1_testMerged_1() throws Exception {
        Path testFolder = new Path("/testFolder");
        Path testFile = new Path(testFolder, "testFile");
        assertTrue(fs.mkdirs(testFolder));
        assertTrue(fs.createNewFile(testFile));
    }

    @Test
    public void testListSlash_3_testMerged_2() throws Exception {
        FileStatus status;
        status = fs.getFileStatus(new Path("/testFolder"));
        assertTrue(status.isDirectory());
    }
}
