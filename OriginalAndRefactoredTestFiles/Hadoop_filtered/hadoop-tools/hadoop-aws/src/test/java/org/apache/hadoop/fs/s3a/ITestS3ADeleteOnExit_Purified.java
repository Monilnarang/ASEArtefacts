package org.apache.hadoop.fs.s3a;

import org.junit.Test;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import static org.apache.hadoop.fs.contract.ContractTestUtils.createFile;
import static org.apache.hadoop.fs.contract.ContractTestUtils.dataset;

public class ITestS3ADeleteOnExit_Purified extends AbstractS3ATestBase {

    private static final String PARENT_DIR_PATH_STR = "testDeleteOnExitDir";

    private static final String NON_EXIST_FILE_PATH_STR = PARENT_DIR_PATH_STR + "/nonExistFile";

    private static final String INORDER_FILE_PATH_STR = PARENT_DIR_PATH_STR + "/inOrderFile";

    private static final String OUT_OF_ORDER_FILE_PATH_STR = PARENT_DIR_PATH_STR + "/outOfOrderFile";

    private static final String SUBDIR_PATH_STR = PARENT_DIR_PATH_STR + "/subDir";

    private static final String FILE_UNDER_SUBDIR_PATH_STR = SUBDIR_PATH_STR + "/subDirFile";

    @Test
    public void testDeleteOnExit_1_testMerged_1() throws Exception {
        Path nonExistFilePath = path(NON_EXIST_FILE_PATH_STR);
        s3aFs.deleteOnExit(nonExistFilePath);
        assertPathDoesNotExist("File " + NON_EXIST_FILE_PATH_STR + " should not exist", nonExistFilePath);
    }

    @Test
    public void testDeleteOnExit_2_testMerged_2() throws Exception {
        Path inOrderFilePath = path(INORDER_FILE_PATH_STR);
        createFile(s3aFs, inOrderFilePath, true, data);
        assertPathExists("File " + INORDER_FILE_PATH_STR + " should exist", inOrderFilePath);
        s3aFs.deleteOnExit(inOrderFilePath);
        assertPathDoesNotExist("File " + INORDER_FILE_PATH_STR + " should not exist", inOrderFilePath);
    }

    @Test
    public void testDeleteOnExit_3_testMerged_3() throws Exception {
        Path outOfOrderFilePath = path(OUT_OF_ORDER_FILE_PATH_STR);
        s3aFs.deleteOnExit(outOfOrderFilePath);
        createFile(s3aFs, outOfOrderFilePath, true, data);
        assertPathExists("File " + OUT_OF_ORDER_FILE_PATH_STR + " should exist", outOfOrderFilePath);
        assertPathDoesNotExist("File " + OUT_OF_ORDER_FILE_PATH_STR + " should not exist", outOfOrderFilePath);
    }

    @Test
    public void testDeleteOnExit_4_testMerged_4() throws Exception {
        Path subDirPath = path(SUBDIR_PATH_STR);
        s3aFs.mkdirs(subDirPath);
        s3aFs.deleteOnExit(subDirPath);
        assertPathExists("Directory " + SUBDIR_PATH_STR + " should exist", subDirPath);
        assertPathDoesNotExist("Directory " + SUBDIR_PATH_STR + " should not exist", subDirPath);
    }

    @Test
    public void testDeleteOnExit_5() throws Exception {
        Path fileUnderSubDirPath = path(FILE_UNDER_SUBDIR_PATH_STR);
        createFile(s3aFs, fileUnderSubDirPath, true, data);
        assertPathExists("File " + FILE_UNDER_SUBDIR_PATH_STR + " should exist", fileUnderSubDirPath);
    }
}
