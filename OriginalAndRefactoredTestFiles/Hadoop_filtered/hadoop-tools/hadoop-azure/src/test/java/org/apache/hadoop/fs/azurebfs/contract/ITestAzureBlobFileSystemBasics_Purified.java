package org.apache.hadoop.fs.azurebfs.contract;

import java.io.IOException;
import org.apache.hadoop.fs.FileSystemContractBaseTest;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.apache.hadoop.fs.azurebfs.constants.TestConfigurationKeys.TEST_TIMEOUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ITestAzureBlobFileSystemBasics_Purified extends FileSystemContractBaseTest {

    private final ABFSContractTestBinding binding;

    public ITestAzureBlobFileSystemBasics() throws Exception {
        binding = new ABFSContractTestBinding(false);
        globalTimeout = Timeout.millis(TEST_TIMEOUT);
    }

    @Before
    public void setUp() throws Exception {
        binding.setup();
        fs = binding.getFileSystem();
    }

    @Override
    public void tearDown() throws Exception {
        try {
            super.tearDown();
        } finally {
            binding.teardown();
        }
    }

    @Test
    public void testListOnFolderWithNoChildren_1() throws IOException {
        assertTrue(fs.mkdirs(path("testListStatus/c/1")));
    }

    @Test
    public void testListOnFolderWithNoChildren_2_testMerged_2() throws IOException {
        FileStatus[] paths;
        paths = fs.listStatus(path("testListStatus"));
        assertEquals(1, paths.length);
        paths = fs.listStatus(path("testListStatus/c"));
        fs.delete(path("testListStatus/c/1"), true);
        assertEquals(0, paths.length);
        assertTrue(fs.delete(path("testListStatus"), true));
    }

    @Test
    public void testListOnfileAndFolder_1() throws IOException {
        Path folderPath = path("testListStatus/folder");
        assertTrue(fs.mkdirs(folderPath));
    }

    @Test
    public void testListOnfileAndFolder_2_testMerged_2() throws IOException {
        Path filePath = path("testListStatus/file");
        ContractTestUtils.touch(fs, filePath);
        FileStatus[] listFolderStatus;
        listFolderStatus = fs.listStatus(path("testListStatus"));
        assertEquals(filePath, listFolderStatus[0].getPath());
        FileStatus[] listFileStatus = fs.listStatus(filePath);
        assertEquals(filePath, listFileStatus[0].getPath());
    }
}
