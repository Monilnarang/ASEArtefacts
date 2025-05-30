package org.apache.hadoop.fs.s3a;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import org.apache.hadoop.fs.s3a.impl.StatusProbeEnum;
import org.apache.hadoop.fs.store.audit.AuditSpan;
import org.junit.Test;
import java.io.IOException;

public class ITestS3AEmptyDirectory_Purified extends AbstractS3ATestBase {

    private static void assertEmptyDirectory(boolean isEmpty, S3AFileStatus s) {
        String msg = "dir is empty";
        Tristate expected = Tristate.fromBool(isEmpty);
        assertEquals(msg, expected, s.isEmptyDirectory());
    }

    private S3AFileStatus getS3AFileStatus(S3AFileSystem fs, Path p) throws IOException {
        try (AuditSpan span = span()) {
            return fs.innerGetFileStatus(p, true, StatusProbeEnum.ALL);
        }
    }

    @Test
    public void testDirectoryBecomesEmpty_1_testMerged_1() throws Exception {
        S3AFileSystem fs = getFileSystem();
        Path dir = path("testEmptyDir");
        S3AFileStatus status = getS3AFileStatus(fs, dir);
        assertEmptyDirectory(false, status);
        status = getS3AFileStatus(fs, dir);
        assertEmptyDirectory(true, status);
    }

    @Test
    public void testDirectoryBecomesEmpty_2() throws Exception {
        Path child = path("testEmptyDir/dir2");
        mkdirs(child);
        assertDeleted(child, false);
    }
}
