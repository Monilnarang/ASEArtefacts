package org.apache.hadoop.security.alias;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.ProviderUtils;
import org.junit.Test;
import java.net.URI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class TestCredentialProvider_Purified {

    @Test
    public void testUnnestUri_1() throws Exception {
        assertEquals(new Path("hdfs://nn.example.com/my/path"), ProviderUtils.unnestUri(new URI("myscheme://hdfs@nn.example.com/my/path")));
    }

    @Test
    public void testUnnestUri_2() throws Exception {
        assertEquals(new Path("hdfs://nn/my/path?foo=bar&baz=bat#yyy"), ProviderUtils.unnestUri(new URI("myscheme://hdfs@nn/my/path?foo=bar&baz=bat#yyy")));
    }

    @Test
    public void testUnnestUri_3() throws Exception {
        assertEquals(new Path("inner://hdfs@nn1.example.com/my/path"), ProviderUtils.unnestUri(new URI("outer://inner@hdfs@nn1.example.com/my/path")));
    }

    @Test
    public void testUnnestUri_4() throws Exception {
        assertEquals(new Path("user:///"), ProviderUtils.unnestUri(new URI("outer://user/")));
    }
}
