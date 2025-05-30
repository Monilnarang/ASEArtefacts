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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestKeyProvider_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testBuildVersionName_1to2")
    public void testBuildVersionName_1to2(String param1, String param2, int param3) throws Exception {
        assertEquals(param1, KeyProvider.buildVersionName(param2, param3));
    }

    static public Stream<Arguments> Provider_testBuildVersionName_1to2() {
        return Stream.of(arguments("/a/b@3", "/a/b", 3), arguments("/aaa@12", "/aaa", 12));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnnestUri_1to9")
    public void testUnnestUri_1to9(String param1, String param2) throws Exception {
        assertUnwraps(param1, param2);
    }

    static public Stream<Arguments> Provider_testUnnestUri_1to9() {
        return Stream.of(arguments("hdfs://nn.example.com/my/path", "myscheme://hdfs@nn.example.com/my/path"), arguments("hdfs://nn/my/path?foo=bar&baz=bat#yyy", "myscheme://hdfs@nn/my/path?foo=bar&baz=bat#yyy"), arguments("inner://hdfs@nn1.example.com/my/path", "outer://inner@hdfs@nn1.example.com/my/path"), arguments("user:///", "outer://user/"), arguments("wasb://account@container/secret.jceks", "jceks://wasb@account@container/secret.jceks"), arguments("abfs://account@container/secret.jceks", "jceks://abfs@account@container/secret.jceks"), arguments("s3a://container/secret.jceks", "jceks://s3a@container/secret.jceks"), arguments("file:///tmp/secret.jceks", "jceks://file/tmp/secret.jceks"), arguments("https://user:pass@service/secret.jceks?token=aia", "jceks://https@user:pass@service/secret.jceks?token=aia"));
    }
}
