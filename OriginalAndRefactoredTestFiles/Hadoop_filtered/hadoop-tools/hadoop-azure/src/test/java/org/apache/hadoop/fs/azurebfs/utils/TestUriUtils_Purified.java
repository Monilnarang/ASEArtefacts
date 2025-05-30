package org.apache.hadoop.fs.azurebfs.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;

public final class TestUriUtils_Purified {

    @Test
    public void testIfUriContainsAbfs_1() throws Exception {
        Assert.assertTrue(UriUtils.containsAbfsUrl("abfs.dfs.core.windows.net"));
    }

    @Test
    public void testIfUriContainsAbfs_2() throws Exception {
        Assert.assertTrue(UriUtils.containsAbfsUrl("abfs.dfs.preprod.core.windows.net"));
    }

    @Test
    public void testIfUriContainsAbfs_3_testMerged_3() throws Exception {
        Assert.assertFalse(UriUtils.containsAbfsUrl("abfs.dfs.cores.windows.net"));
    }

    @Test
    public void testIfUriContainsAbfs_4() throws Exception {
        Assert.assertFalse(UriUtils.containsAbfsUrl(""));
    }

    @Test
    public void testIfUriContainsAbfs_5() throws Exception {
        Assert.assertFalse(UriUtils.containsAbfsUrl(null));
    }

    @Test
    public void testIfUriContainsAbfs_7() throws Exception {
        Assert.assertFalse(UriUtils.containsAbfsUrl("xhdfs.blob.core.windows.net"));
    }

    @Test
    public void testExtractRawAccountName_1() throws Exception {
        Assert.assertEquals("abfs", UriUtils.extractAccountNameFromHostName("abfs.dfs.core.windows.net"));
    }

    @Test
    public void testExtractRawAccountName_2() throws Exception {
        Assert.assertEquals("abfs", UriUtils.extractAccountNameFromHostName("abfs.dfs.preprod.core.windows.net"));
    }

    @Test
    public void testExtractRawAccountName_3_testMerged_3() throws Exception {
        Assert.assertEquals(null, UriUtils.extractAccountNameFromHostName("abfs.dfs.cores.windows.net"));
    }

    @Test
    public void testExtractRawAccountName_4() throws Exception {
        Assert.assertEquals(null, UriUtils.extractAccountNameFromHostName(""));
    }

    @Test
    public void testExtractRawAccountName_5() throws Exception {
        Assert.assertEquals(null, UriUtils.extractAccountNameFromHostName(null));
    }
}
