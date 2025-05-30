package org.apache.hadoop.fs.s3a;

import java.io.IOException;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.Test;
import org.apache.hadoop.fs.Path;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.getCurrentThreadNames;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.listInitialThreadsForLifecycleChecks;
import static org.apache.hadoop.test.LambdaTestUtils.*;
import static org.apache.hadoop.fs.s3a.S3AUtils.E_FS_CLOSED;

public class ITestS3AClosedFS_Purified extends AbstractS3ATestBase {

    private Path root = new Path("/");

    @Override
    public void setup() throws Exception {
        super.setup();
        root = getFileSystem().makeQualified(new Path("/"));
        getFileSystem().close();
    }

    @Override
    public void teardown() {
    }

    private static final Set<String> THREAD_SET = listInitialThreadsForLifecycleChecks();

    @AfterClass
    public static void checkForThreadLeakage() {
        Assertions.assertThat(getCurrentThreadNames()).describedAs("The threads at the end of the test run").isSubsetOf(THREAD_SET);
    }

    @Test
    public void testClosedInstrumentation_1() throws Exception {
    }

    @Test
    public void testClosedInstrumentation_2() throws Exception {
    }
}
