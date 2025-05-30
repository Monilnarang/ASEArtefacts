package org.apache.hadoop.fs.adl;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.Test;
import static org.apache.hadoop.fs.adl.AdlConfKeys.ADL_BLOCK_SIZE;
import static org.apache.hadoop.fs.adl.AdlConfKeys.ADL_DEBUG_OVERRIDE_LOCAL_USER_AS_OWNER;
import static org.apache.hadoop.fs.adl.AdlConfKeys.ADL_DEBUG_SET_LOCAL_USER_AS_OWNER_DEFAULT;
import static org.apache.hadoop.fs.adl.AdlConfKeys.ADL_ENABLEUPN_FOR_OWNERGROUP_DEFAULT;
import static org.apache.hadoop.fs.adl.AdlConfKeys.ADL_ENABLEUPN_FOR_OWNERGROUP_KEY;
import static org.apache.hadoop.fs.adl.AdlConfKeys.ADL_EXPERIMENT_POSITIONAL_READ_DEFAULT;
import static org.apache.hadoop.fs.adl.AdlConfKeys.ADL_EXPERIMENT_POSITIONAL_READ_KEY;
import static org.apache.hadoop.fs.adl.AdlConfKeys.ADL_REPLICATION_FACTOR;
import static org.apache.hadoop.fs.adl.AdlConfKeys.AZURE_AD_CLIENT_ID_KEY;
import static org.apache.hadoop.fs.adl.AdlConfKeys.AZURE_AD_CLIENT_SECRET_KEY;
import static org.apache.hadoop.fs.adl.AdlConfKeys.AZURE_AD_REFRESH_TOKEN_KEY;
import static org.apache.hadoop.fs.adl.AdlConfKeys.AZURE_AD_REFRESH_URL_KEY;
import static org.apache.hadoop.fs.adl.AdlConfKeys.AZURE_AD_TOKEN_PROVIDER_CLASS_KEY;
import static org.apache.hadoop.fs.adl.AdlConfKeys.AZURE_AD_TOKEN_PROVIDER_TYPE_KEY;
import static org.apache.hadoop.fs.adl.AdlConfKeys.DEFAULT_READ_AHEAD_BUFFER_SIZE;
import static org.apache.hadoop.fs.adl.AdlConfKeys.DEFAULT_WRITE_AHEAD_BUFFER_SIZE;
import static org.apache.hadoop.fs.adl.AdlConfKeys.LATENCY_TRACKER_DEFAULT;
import static org.apache.hadoop.fs.adl.AdlConfKeys.LATENCY_TRACKER_KEY;
import static org.apache.hadoop.fs.adl.AdlConfKeys.READ_AHEAD_BUFFER_SIZE_KEY;
import static org.apache.hadoop.fs.adl.AdlConfKeys.TOKEN_PROVIDER_TYPE_CLIENT_CRED;
import static org.apache.hadoop.fs.adl.AdlConfKeys.TOKEN_PROVIDER_TYPE_REFRESH_TOKEN;
import static org.apache.hadoop.fs.adl.AdlConfKeys.WRITE_BUFFER_SIZE_KEY;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestValidateConfiguration_Purified {

    private void setDeprecatedKeys(Configuration conf) {
        conf.set("dfs.adls.oauth2.access.token.provider.type", "dummyType");
        conf.set("dfs.adls.oauth2.client.id", "dummyClientId");
        conf.set("dfs.adls.oauth2.refresh.token", "dummyRefreshToken");
        conf.set("dfs.adls.oauth2.refresh.url", "dummyRefreshUrl");
        conf.set("dfs.adls.oauth2.credential", "dummyCredential");
        conf.set("dfs.adls.oauth2.access.token.provider", "dummyClass");
        conf.set("adl.dfs.enable.client.latency.tracker", "dummyTracker");
    }

    private void assertDeprecatedKeys(Configuration conf) {
        assertEquals("dummyType", conf.get(AZURE_AD_TOKEN_PROVIDER_TYPE_KEY));
        assertEquals("dummyClientId", conf.get(AZURE_AD_CLIENT_ID_KEY));
        assertEquals("dummyRefreshToken", conf.get(AZURE_AD_REFRESH_TOKEN_KEY));
        assertEquals("dummyRefreshUrl", conf.get(AZURE_AD_REFRESH_URL_KEY));
        assertEquals("dummyCredential", conf.get(AZURE_AD_CLIENT_SECRET_KEY));
        assertEquals("dummyClass", conf.get(AZURE_AD_TOKEN_PROVIDER_CLASS_KEY));
        assertEquals("dummyTracker", conf.get(LATENCY_TRACKER_KEY));
    }

    @Test
    public void validateConfigurationKeys_1() {
        assertEquals("fs.adl.oauth2.refresh.url", AZURE_AD_REFRESH_URL_KEY);
    }

    @Test
    public void validateConfigurationKeys_2() {
        assertEquals("fs.adl.oauth2.access.token.provider", AZURE_AD_TOKEN_PROVIDER_CLASS_KEY);
    }

    @Test
    public void validateConfigurationKeys_3() {
        assertEquals("fs.adl.oauth2.client.id", AZURE_AD_CLIENT_ID_KEY);
    }

    @Test
    public void validateConfigurationKeys_4() {
        assertEquals("fs.adl.oauth2.refresh.token", AZURE_AD_REFRESH_TOKEN_KEY);
    }

    @Test
    public void validateConfigurationKeys_5() {
        assertEquals("fs.adl.oauth2.credential", AZURE_AD_CLIENT_SECRET_KEY);
    }

    @Test
    public void validateConfigurationKeys_6() {
        assertEquals("adl.debug.override.localuserasfileowner", ADL_DEBUG_OVERRIDE_LOCAL_USER_AS_OWNER);
    }

    @Test
    public void validateConfigurationKeys_7() {
        assertEquals("fs.adl.oauth2.access.token.provider.type", AZURE_AD_TOKEN_PROVIDER_TYPE_KEY);
    }

    @Test
    public void validateConfigurationKeys_8() {
        assertEquals("adl.feature.client.cache.readahead", READ_AHEAD_BUFFER_SIZE_KEY);
    }

    @Test
    public void validateConfigurationKeys_9() {
        assertEquals("adl.feature.client.cache.drop.behind.writes", WRITE_BUFFER_SIZE_KEY);
    }

    @Test
    public void validateConfigurationKeys_10() {
        assertEquals("RefreshToken", TOKEN_PROVIDER_TYPE_REFRESH_TOKEN);
    }

    @Test
    public void validateConfigurationKeys_11() {
        assertEquals("ClientCredential", TOKEN_PROVIDER_TYPE_CLIENT_CRED);
    }

    @Test
    public void validateConfigurationKeys_12() {
        assertEquals("adl.enable.client.latency.tracker", LATENCY_TRACKER_KEY);
    }

    @Test
    public void validateConfigurationKeys_13() {
        assertEquals(true, LATENCY_TRACKER_DEFAULT);
    }

    @Test
    public void validateConfigurationKeys_14() {
        assertEquals(true, ADL_EXPERIMENT_POSITIONAL_READ_DEFAULT);
    }

    @Test
    public void validateConfigurationKeys_15() {
        assertEquals("adl.feature.experiment.positional.read.enable", ADL_EXPERIMENT_POSITIONAL_READ_KEY);
    }

    @Test
    public void validateConfigurationKeys_16() {
        assertEquals(1, ADL_REPLICATION_FACTOR);
    }

    @Test
    public void validateConfigurationKeys_17() {
        assertEquals(256 * 1024 * 1024, ADL_BLOCK_SIZE);
    }

    @Test
    public void validateConfigurationKeys_18() {
        assertEquals(false, ADL_DEBUG_SET_LOCAL_USER_AS_OWNER_DEFAULT);
    }

    @Test
    public void validateConfigurationKeys_19() {
        assertEquals(4 * 1024 * 1024, DEFAULT_READ_AHEAD_BUFFER_SIZE);
    }

    @Test
    public void validateConfigurationKeys_20() {
        assertEquals(4 * 1024 * 1024, DEFAULT_WRITE_AHEAD_BUFFER_SIZE);
    }

    @Test
    public void validateConfigurationKeys_21() {
        assertEquals("adl.feature.ownerandgroup.enableupn", ADL_ENABLEUPN_FOR_OWNERGROUP_KEY);
    }

    @Test
    public void validateConfigurationKeys_22() {
        assertEquals(false, ADL_ENABLEUPN_FOR_OWNERGROUP_DEFAULT);
    }

    @Test
    public void testGetAccountNameFromFQDN_1() {
        assertEquals("dummy", AdlFileSystem.getAccountNameFromFQDN("dummy.azuredatalakestore.net"));
    }

    @Test
    public void testGetAccountNameFromFQDN_2() {
        assertEquals("localhost", AdlFileSystem.getAccountNameFromFQDN("localhost"));
    }
}
