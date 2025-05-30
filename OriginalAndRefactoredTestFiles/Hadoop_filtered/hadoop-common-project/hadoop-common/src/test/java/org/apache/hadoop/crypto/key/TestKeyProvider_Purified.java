package org.apache.hadoop.crypto.key;

import org.junit.Assert;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.ProviderUtils;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class TestKeyProvider_Purified {

    private static final String CIPHER = "AES";

    protected void assertUnwraps(final String unwrapped, final String outer) throws URISyntaxException {
        assertEquals(new Path(unwrapped), ProviderUtils.unnestUri(new URI(outer)));
    }

    private static class MyKeyProvider extends KeyProvider {

        private String algorithm;

        private int size;

        private byte[] material;

        public MyKeyProvider(Configuration conf) {
            super(conf);
        }

        @Override
        public KeyVersion getKeyVersion(String versionName) throws IOException {
            return null;
        }

        @Override
        public List<String> getKeys() throws IOException {
            return null;
        }

        @Override
        public List<KeyVersion> getKeyVersions(String name) throws IOException {
            return null;
        }

        @Override
        public Metadata getMetadata(String name) throws IOException {
            if (!"unknown".equals(name)) {
                return new Metadata(CIPHER, 128, "description", null, new Date(), 0);
            }
            return null;
        }

        @Override
        public KeyVersion createKey(String name, byte[] material, Options options) throws IOException {
            this.material = material;
            return null;
        }

        @Override
        public void deleteKey(String name) throws IOException {
        }

        @Override
        public KeyVersion rollNewVersion(String name, byte[] material) throws IOException {
            this.material = material;
            return null;
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        protected byte[] generateKey(int size, String algorithm) throws NoSuchAlgorithmException {
            this.size = size;
            this.algorithm = algorithm;
            return super.generateKey(size, algorithm);
        }
    }

    @Test
    public void testBuildVersionName_1() throws Exception {
        assertEquals("/a/b@3", KeyProvider.buildVersionName("/a/b", 3));
    }

    @Test
    public void testBuildVersionName_2() throws Exception {
        assertEquals("/aaa@12", KeyProvider.buildVersionName("/aaa", 12));
    }

    @Test
    public void testUnnestUri_1() throws Exception {
        assertUnwraps("hdfs://nn.example.com/my/path", "myscheme://hdfs@nn.example.com/my/path");
    }

    @Test
    public void testUnnestUri_2() throws Exception {
        assertUnwraps("hdfs://nn/my/path?foo=bar&baz=bat#yyy", "myscheme://hdfs@nn/my/path?foo=bar&baz=bat#yyy");
    }

    @Test
    public void testUnnestUri_3() throws Exception {
        assertUnwraps("inner://hdfs@nn1.example.com/my/path", "outer://inner@hdfs@nn1.example.com/my/path");
    }

    @Test
    public void testUnnestUri_4() throws Exception {
        assertUnwraps("user:///", "outer://user/");
    }

    @Test
    public void testUnnestUri_5() throws Exception {
        assertUnwraps("wasb://account@container/secret.jceks", "jceks://wasb@account@container/secret.jceks");
    }

    @Test
    public void testUnnestUri_6() throws Exception {
        assertUnwraps("abfs://account@container/secret.jceks", "jceks://abfs@account@container/secret.jceks");
    }

    @Test
    public void testUnnestUri_7() throws Exception {
        assertUnwraps("s3a://container/secret.jceks", "jceks://s3a@container/secret.jceks");
    }

    @Test
    public void testUnnestUri_8() throws Exception {
        assertUnwraps("file:///tmp/secret.jceks", "jceks://file/tmp/secret.jceks");
    }

    @Test
    public void testUnnestUri_9() throws Exception {
        assertUnwraps("https://user:pass@service/secret.jceks?token=aia", "jceks://https@user:pass@service/secret.jceks?token=aia");
    }
}
