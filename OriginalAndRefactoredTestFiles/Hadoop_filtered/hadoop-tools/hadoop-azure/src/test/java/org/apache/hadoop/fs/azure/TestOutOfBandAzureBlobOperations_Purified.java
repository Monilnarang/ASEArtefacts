package org.apache.hadoop.fs.azure;

import java.util.HashMap;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestOutOfBandAzureBlobOperations_Purified extends AbstractWasbTestWithTimeout {

    private AzureBlobStorageTestAccount testAccount;

    private FileSystem fs;

    private InMemoryBlockBlobStore backingStore;

    @Before
    public void setUp() throws Exception {
        testAccount = AzureBlobStorageTestAccount.createMock();
        fs = testAccount.getFileSystem();
        backingStore = testAccount.getMockStorage().getBackingStore();
    }

    @After
    public void tearDown() throws Exception {
        testAccount.cleanup();
        fs = null;
        backingStore = null;
    }

    private void createEmptyBlobOutOfBand(String path) {
        backingStore.setContent(AzureBlobStorageTestAccount.toMockUri(path), new byte[] { 1, 2 }, new HashMap<String, String>(), false, 0);
    }

    private static enum DeepCreateTestVariation {

        File, Folder
    }

    @Test
    public void testImplicitFolderDeleted_1() throws Exception {
        assertTrue(fs.exists(new Path("/root")));
    }

    @Test
    public void testImplicitFolderDeleted_2() throws Exception {
        assertTrue(fs.delete(new Path("/root"), true));
    }

    @Test
    public void testImplicitFolderDeleted_3() throws Exception {
        assertFalse(fs.exists(new Path("/root")));
    }

    @Test
    public void testFileInImplicitFolderDeleted_1() throws Exception {
        assertTrue(fs.exists(new Path("/root")));
    }

    @Test
    public void testFileInImplicitFolderDeleted_2() throws Exception {
        assertTrue(fs.delete(new Path("/root/b"), true));
    }

    @Test
    public void testFileInImplicitFolderDeleted_3() throws Exception {
        assertTrue(fs.exists(new Path("/root")));
    }
}
