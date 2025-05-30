package org.apache.hadoop.fs.contract;

import org.apache.hadoop.fs.Path;
import org.junit.Test;
import java.io.IOException;

public abstract class AbstractContractDeleteTest_Purified extends AbstractFSContractTestBase {

    @Test
    public void testDeleteDeepEmptyDir_1() throws Throwable {
        assertDeleted(path("testDeleteDeepEmptyDir/d1/d2/d3"), true);
    }

    @Test
    public void testDeleteDeepEmptyDir_2() throws Throwable {
        assertPathDoesNotExist("not deleted", path("testDeleteDeepEmptyDir/d1/d2/d3/d4"));
    }

    @Test
    public void testDeleteDeepEmptyDir_3() throws Throwable {
        assertPathDoesNotExist("not deleted", path("testDeleteDeepEmptyDir/d1/d2/d3"));
    }

    @Test
    public void testDeleteDeepEmptyDir_4() throws Throwable {
        assertPathExists("parent dir is deleted", path("testDeleteDeepEmptyDir/d1/d2"));
    }
}
