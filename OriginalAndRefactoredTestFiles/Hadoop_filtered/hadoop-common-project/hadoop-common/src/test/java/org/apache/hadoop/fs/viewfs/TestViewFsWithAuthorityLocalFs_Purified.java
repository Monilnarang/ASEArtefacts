package org.apache.hadoop.fs.viewfs;

import java.net.URI;
import org.apache.hadoop.fs.FileContext;
import org.apache.hadoop.fs.FsConstants;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestViewFsWithAuthorityLocalFs_Purified extends ViewFsBaseTest {

    URI schemeWithAuthority;

    @Override
    @Before
    public void setUp() throws Exception {
        fcTarget = FileContext.getLocalFSFileContext();
        super.setUp();
        schemeWithAuthority = new URI(FsConstants.VIEWFS_SCHEME, MOUNT_TABLE_NAME, "/", null, null);
        fcView = FileContext.getFileContext(schemeWithAuthority, conf);
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    @Test
    public void testBasicPaths_1() {
        Assert.assertEquals(schemeWithAuthority, fcView.getDefaultFileSystem().getUri());
    }

    @Override
    @Test
    public void testBasicPaths_2() {
        Assert.assertEquals(fcView.makeQualified(new Path("/user/" + System.getProperty("user.name"))), fcView.getWorkingDirectory());
    }

    @Override
    @Test
    public void testBasicPaths_3() {
        Assert.assertEquals(fcView.makeQualified(new Path("/user/" + System.getProperty("user.name"))), fcView.getHomeDirectory());
    }

    @Override
    @Test
    public void testBasicPaths_4() {
        Assert.assertEquals(new Path("/foo/bar").makeQualified(schemeWithAuthority, null), fcView.makeQualified(new Path("/foo/bar")));
    }
}
