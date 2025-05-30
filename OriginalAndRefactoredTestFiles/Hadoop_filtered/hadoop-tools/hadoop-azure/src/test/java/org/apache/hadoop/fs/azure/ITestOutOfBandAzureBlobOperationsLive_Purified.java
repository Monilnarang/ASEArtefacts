package org.apache.hadoop.fs.azure;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;
import com.microsoft.azure.storage.blob.BlobOutputStream;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

public class ITestOutOfBandAzureBlobOperationsLive_Purified extends AbstractWasbTestBase {

    @Override
    protected AzureBlobStorageTestAccount createTestAccount() throws Exception {
        return AzureBlobStorageTestAccount.create();
    }

    @Test
    public void outOfBandFolder_uncleMkdirs_1() throws Exception {
        assertTrue(fs.exists(new Path("testFolder1/a/input/file")));
    }

    @Test
    public void outOfBandFolder_uncleMkdirs_2() throws Exception {
        Path targetFolder = new Path("testFolder1/a/output");
        assertTrue(fs.mkdirs(targetFolder));
    }

    @Test
    public void outOfBandFolder_parentDelete_1() throws Exception {
        assertTrue(fs.exists(new Path("testFolder2/a/input/file")));
    }

    @Test
    public void outOfBandFolder_parentDelete_2() throws Exception {
        Path targetFolder = new Path("testFolder2/a/input");
        assertTrue(fs.delete(targetFolder, true));
    }

    @Test
    public void outOfBandFolder_rootFileDelete_1() throws Exception {
        assertTrue(fs.exists(new Path("/fileY")));
    }

    @Test
    public void outOfBandFolder_rootFileDelete_2() throws Exception {
        assertTrue(fs.delete(new Path("/fileY"), true));
    }

    @Test
    public void outOfBandFolder_firstLevelFolderDelete_1() throws Exception {
        assertTrue(fs.exists(new Path("/folderW")));
    }

    @Test
    public void outOfBandFolder_firstLevelFolderDelete_2() throws Exception {
        assertTrue(fs.exists(new Path("/folderW/file")));
    }

    @Test
    public void outOfBandFolder_firstLevelFolderDelete_3() throws Exception {
        assertTrue(fs.delete(new Path("/folderW"), true));
    }
}
