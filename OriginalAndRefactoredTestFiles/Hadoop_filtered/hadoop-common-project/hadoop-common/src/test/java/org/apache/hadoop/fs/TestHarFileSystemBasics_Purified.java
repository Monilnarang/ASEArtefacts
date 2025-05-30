package org.apache.hadoop.fs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Shell;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestHarFileSystemBasics_Purified {

    private static final String ROOT_PATH = GenericTestUtils.getTempPath("testharfilesystembasics");

    private static final Path rootPath;

    static {
        String root = new Path(new File(ROOT_PATH).getAbsolutePath(), "localfs").toUri().getPath();
        if (Shell.WINDOWS) {
            root = root.substring(root.indexOf(':') + 1);
        }
        rootPath = new Path(root);
    }

    private static final Path harPath = new Path(rootPath, "path1/path2/my.har");

    private FileSystem localFileSystem;

    private HarFileSystem harFileSystem;

    private Configuration conf;

    private HarFileSystem createHarFileSystem(final Configuration conf) throws Exception {
        localFileSystem = FileSystem.getLocal(conf);
        localFileSystem.initialize(new URI("file:///"), conf);
        localFileSystem.mkdirs(rootPath);
        localFileSystem.mkdirs(harPath);
        final Path indexPath = new Path(harPath, "_index");
        final Path masterIndexPath = new Path(harPath, "_masterindex");
        localFileSystem.createNewFile(indexPath);
        assertTrue(localFileSystem.exists(indexPath));
        localFileSystem.createNewFile(masterIndexPath);
        assertTrue(localFileSystem.exists(masterIndexPath));
        writeVersionToMasterIndexImpl(HarFileSystem.VERSION, masterIndexPath);
        final HarFileSystem harFileSystem = new HarFileSystem(localFileSystem);
        final URI uri = new URI("har://" + harPath.toString());
        harFileSystem.initialize(uri, conf);
        return harFileSystem;
    }

    private HarFileSystem createHarFileSystem(final Configuration conf, Path aHarPath) throws Exception {
        localFileSystem.mkdirs(aHarPath);
        final Path indexPath = new Path(aHarPath, "_index");
        final Path masterIndexPath = new Path(aHarPath, "_masterindex");
        localFileSystem.createNewFile(indexPath);
        assertTrue(localFileSystem.exists(indexPath));
        localFileSystem.createNewFile(masterIndexPath);
        assertTrue(localFileSystem.exists(masterIndexPath));
        writeVersionToMasterIndexImpl(HarFileSystem.VERSION, masterIndexPath);
        final HarFileSystem harFileSystem = new HarFileSystem(localFileSystem);
        final URI uri = new URI("har://" + aHarPath.toString());
        harFileSystem.initialize(uri, conf);
        return harFileSystem;
    }

    private void writeVersionToMasterIndexImpl(int version, Path masterIndexPath) throws IOException {
        final FSDataOutputStream fsdos = localFileSystem.create(masterIndexPath);
        try {
            String versionString = version + "\n";
            fsdos.write(versionString.getBytes("UTF-8"));
            fsdos.flush();
        } finally {
            fsdos.close();
        }
    }

    @Before
    public void before() throws Exception {
        final File rootDirIoFile = new File(rootPath.toUri().getPath());
        rootDirIoFile.mkdirs();
        if (!rootDirIoFile.exists()) {
            throw new IOException("Failed to create temp directory [" + rootDirIoFile.getAbsolutePath() + "]");
        }
        conf = new Configuration();
        harFileSystem = createHarFileSystem(conf);
    }

    @After
    public void after() throws Exception {
        final FileSystem harFS = harFileSystem;
        if (harFS != null) {
            harFS.close();
            harFileSystem = null;
        }
        final File rootDirIoFile = new File(rootPath.toUri().getPath());
        if (rootDirIoFile.exists()) {
            FileUtil.fullyDelete(rootDirIoFile);
        }
        if (rootDirIoFile.exists()) {
            throw new IOException("Failed to delete temp directory [" + rootDirIoFile.getAbsolutePath() + "]");
        }
    }

    @Test
    public void testPositiveHarFileSystemBasics_1() throws Exception {
        assertEquals(HarFileSystem.VERSION, harFileSystem.getHarVersion());
    }

    @Test
    public void testPositiveHarFileSystemBasics_2_testMerged_2() throws Exception {
        final URI harUri = harFileSystem.getUri();
        assertEquals(harPath.toUri().getPath(), harUri.getPath());
        assertEquals("har", harUri.getScheme());
        final Path homePath = harFileSystem.getHomeDirectory();
        assertEquals(harPath.toUri().getPath(), homePath.toUri().getPath());
        final Path workDirPath0 = harFileSystem.getWorkingDirectory();
        assertEquals(homePath, workDirPath0);
        harFileSystem.setWorkingDirectory(new Path("/foo/bar"));
        assertEquals(workDirPath0, harFileSystem.getWorkingDirectory());
    }
}
