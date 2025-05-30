package org.apache.hadoop.fs.viewfs;

import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsConstants;
import org.apache.hadoop.fs.Path;
import static org.apache.hadoop.fs.FileSystem.TRASH_PREFIX;
import org.apache.hadoop.security.UserGroupInformation;
import java.io.IOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestViewFileSystemWithAuthorityLocalFileSystem_Purified extends ViewFileSystemBaseTest {

    URI schemeWithAuthority;

    @Override
    @Before
    public void setUp() throws Exception {
        fsTarget = FileSystem.getLocal(new Configuration());
        super.setUp();
        schemeWithAuthority = new URI(FsConstants.VIEWFS_SCHEME, "default", "/", null, null);
        fsView = FileSystem.get(schemeWithAuthority, conf);
    }

    @Override
    @After
    public void tearDown() throws Exception {
        fsTarget.delete(fileSystemTestHelper.getTestRootPath(fsTarget), true);
        super.tearDown();
    }

    @Override
    Path getTrashRootInFallBackFS() throws IOException {
        return new Path("/" + TRASH_PREFIX + "/" + UserGroupInformation.getCurrentUser().getShortUserName());
    }

    @Override
    @Test
    public void testBasicPaths_1() {
        Assert.assertEquals(schemeWithAuthority, fsView.getUri());
    }

    @Override
    @Test
    public void testBasicPaths_2() {
        Assert.assertEquals(fsView.makeQualified(new Path("/user/" + System.getProperty("user.name"))), fsView.getWorkingDirectory());
    }

    @Override
    @Test
    public void testBasicPaths_3() {
        Assert.assertEquals(fsView.makeQualified(new Path("/user/" + System.getProperty("user.name"))), fsView.getHomeDirectory());
    }

    @Override
    @Test
    public void testBasicPaths_4() {
        Assert.assertEquals(new Path("/foo/bar").makeQualified(schemeWithAuthority, null), fsView.makeQualified(new Path("/foo/bar")));
    }
}
