package org.apache.hadoop.hdfs;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.crypto.key.KeyProvider;
import org.apache.hadoop.crypto.key.KeyProviderFactory;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.junit.Assert;
import org.junit.Test;

public class TestKeyProviderCache_Purified {

    public static class DummyKeyProvider extends KeyProvider {

        public static int CLOSE_CALL_COUNT = 0;

        public DummyKeyProvider(Configuration conf) {
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
            return null;
        }

        @Override
        public KeyVersion createKey(String name, byte[] material, Options options) throws IOException {
            return null;
        }

        @Override
        public void deleteKey(String name) throws IOException {
        }

        @Override
        public KeyVersion rollNewVersion(String name, byte[] material) throws IOException {
            return null;
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() {
            CLOSE_CALL_COUNT += 1;
        }
    }

    public static class Factory extends KeyProviderFactory {

        @Override
        public KeyProvider createProvider(URI providerName, Configuration conf) throws IOException {
            if ("dummy".equals(providerName.getScheme())) {
                return new DummyKeyProvider(conf);
            }
            return null;
        }
    }

    private URI getKeyProviderUriFromConf(Configuration conf) {
        String providerUriStr = conf.get(CommonConfigurationKeysPublic.HADOOP_SECURITY_KEY_PROVIDER_PATH);
        if (providerUriStr == null || providerUriStr.isEmpty()) {
            return null;
        }
        return URI.create(providerUriStr);
    }

    @Test
    public void testCache_1_testMerged_1() throws Exception {
        KeyProviderCache kpCache = new KeyProviderCache(10000);
        Configuration conf = new Configuration();
        conf.set(CommonConfigurationKeysPublic.HADOOP_SECURITY_KEY_PROVIDER_PATH, "dummy://foo:bar@test_provider1");
        KeyProvider keyProvider1 = kpCache.get(conf, getKeyProviderUriFromConf(conf));
        Assert.assertNotNull("Returned Key Provider is null !!", keyProvider1);
        KeyProvider keyProvider2 = kpCache.get(conf, getKeyProviderUriFromConf(conf));
        Assert.assertTrue("Different KeyProviders returned !!", keyProvider1 == keyProvider2);
        conf.set(CommonConfigurationKeysPublic.HADOOP_SECURITY_KEY_PROVIDER_PATH, "dummy://test_provider3");
        KeyProvider keyProvider3 = kpCache.get(conf, getKeyProviderUriFromConf(conf));
        Assert.assertFalse("Same KeyProviders returned !!", keyProvider1 == keyProvider3);
        conf.set(CommonConfigurationKeysPublic.HADOOP_SECURITY_KEY_PROVIDER_PATH, "dummy://hello:there@test_provider1");
        KeyProvider keyProvider4 = kpCache.get(conf, getKeyProviderUriFromConf(conf));
        Assert.assertFalse("Same KeyProviders returned !!", keyProvider1 == keyProvider4);
    }

    @Test
    public void testCache_5() throws Exception {
        Assert.assertEquals("Expected number of closing calls doesn't match", 3, DummyKeyProvider.CLOSE_CALL_COUNT);
    }
}
