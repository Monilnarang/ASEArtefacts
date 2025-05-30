package org.apache.hadoop.fs.azurebfs;

import java.io.IOException;
import java.lang.reflect.Field;
import org.apache.commons.codec.Charsets;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.azurebfs.constants.ConfigurationKeys;
import org.apache.hadoop.fs.azurebfs.constants.TestConfigurationKeys;
import org.apache.hadoop.fs.azurebfs.contracts.annotations.ConfigurationValidationAnnotations.IntegerConfigurationValidatorAnnotation;
import org.apache.hadoop.fs.azurebfs.contracts.annotations.ConfigurationValidationAnnotations.BooleanConfigurationValidatorAnnotation;
import org.apache.hadoop.fs.azurebfs.contracts.annotations.ConfigurationValidationAnnotations.StringConfigurationValidatorAnnotation;
import org.apache.hadoop.fs.azurebfs.contracts.annotations.ConfigurationValidationAnnotations.LongConfigurationValidatorAnnotation;
import org.apache.hadoop.fs.azurebfs.contracts.annotations.ConfigurationValidationAnnotations.Base64StringConfigurationValidatorAnnotation;
import org.apache.hadoop.fs.azurebfs.contracts.exceptions.KeyProviderException;
import org.apache.hadoop.fs.azurebfs.utils.Base64;
import static org.apache.hadoop.fs.azurebfs.constants.ConfigurationKeys.FS_AZURE_SSL_CHANNEL_MODE_KEY;
import static org.apache.hadoop.fs.azurebfs.constants.FileSystemConfigurations.DEFAULT_READ_AHEAD_RANGE;
import static org.apache.hadoop.fs.azurebfs.constants.FileSystemConfigurations.DEFAULT_READ_BUFFER_SIZE;
import static org.apache.hadoop.fs.azurebfs.constants.FileSystemConfigurations.DEFAULT_WRITE_BUFFER_SIZE;
import static org.apache.hadoop.fs.azurebfs.constants.FileSystemConfigurations.DEFAULT_MAX_RETRY_ATTEMPTS;
import static org.apache.hadoop.fs.azurebfs.constants.FileSystemConfigurations.DEFAULT_BACKOFF_INTERVAL;
import static org.apache.hadoop.fs.azurebfs.constants.FileSystemConfigurations.DEFAULT_MAX_BACKOFF_INTERVAL;
import static org.apache.hadoop.fs.azurebfs.constants.FileSystemConfigurations.DEFAULT_MIN_BACKOFF_INTERVAL;
import static org.apache.hadoop.fs.azurebfs.constants.FileSystemConfigurations.MAX_AZURE_BLOCK_SIZE;
import static org.apache.hadoop.fs.azurebfs.constants.FileSystemConfigurations.AZURE_BLOCK_LOCATION_HOST_DEFAULT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.apache.hadoop.fs.azurebfs.contracts.exceptions.InvalidConfigurationValueException;
import org.apache.hadoop.security.ssl.DelegatingSSLSocketFactory;
import org.junit.Test;

public class TestAbfsConfigurationFieldsValidation_Purified {

    private AbfsConfiguration abfsConfiguration;

    private static final String INT_KEY = "intKey";

    private static final String LONG_KEY = "longKey";

    private static final String STRING_KEY = "stringKey";

    private static final String BASE64_KEY = "base64Key";

    private static final String BOOLEAN_KEY = "booleanKey";

    private static final int DEFAULT_INT = 4194304;

    private static final int DEFAULT_LONG = 4194304;

    private static final int TEST_INT = 1234565;

    private static final int TEST_LONG = 4194304;

    private final String accountName;

    private final String encodedString;

    private final String encodedAccountKey;

    @IntegerConfigurationValidatorAnnotation(ConfigurationKey = INT_KEY, MinValue = Integer.MIN_VALUE, MaxValue = Integer.MAX_VALUE, DefaultValue = DEFAULT_INT)
    private int intField;

    @LongConfigurationValidatorAnnotation(ConfigurationKey = LONG_KEY, MinValue = Long.MIN_VALUE, MaxValue = Long.MAX_VALUE, DefaultValue = DEFAULT_LONG)
    private int longField;

    @StringConfigurationValidatorAnnotation(ConfigurationKey = STRING_KEY, DefaultValue = "default")
    private String stringField;

    @Base64StringConfigurationValidatorAnnotation(ConfigurationKey = BASE64_KEY, DefaultValue = "base64")
    private String base64Field;

    @BooleanConfigurationValidatorAnnotation(ConfigurationKey = BOOLEAN_KEY, DefaultValue = false)
    private boolean boolField;

    public TestAbfsConfigurationFieldsValidation() throws Exception {
        super();
        this.accountName = "testaccount1.blob.core.windows.net";
        this.encodedString = Base64.encode("base64Value".getBytes(Charsets.UTF_8));
        this.encodedAccountKey = Base64.encode("someAccountKey".getBytes(Charsets.UTF_8));
        Configuration configuration = new Configuration();
        configuration.addResource(TestConfigurationKeys.TEST_CONFIGURATION_FILE_NAME);
        configuration.set(INT_KEY, "1234565");
        configuration.set(LONG_KEY, "4194304");
        configuration.set(STRING_KEY, "stringValue");
        configuration.set(BASE64_KEY, encodedString);
        configuration.set(BOOLEAN_KEY, "true");
        configuration.set(ConfigurationKeys.FS_AZURE_ACCOUNT_KEY_PROPERTY_NAME + "." + accountName, this.encodedAccountKey);
        abfsConfiguration = new AbfsConfiguration(configuration, accountName);
    }

    public static AbfsConfiguration updateRetryConfigs(AbfsConfiguration abfsConfig, int retryCount, int backoffTime) {
        abfsConfig.setMaxIoRetries(retryCount);
        abfsConfig.setMaxBackoffIntervalMilliseconds(backoffTime);
        return abfsConfig;
    }

    @Test
    public void testConfigServiceImplAnnotatedFieldsInitialized_1() throws Exception {
        assertEquals(DEFAULT_WRITE_BUFFER_SIZE, abfsConfiguration.getWriteBufferSize());
    }

    @Test
    public void testConfigServiceImplAnnotatedFieldsInitialized_2() throws Exception {
        assertEquals(DEFAULT_READ_BUFFER_SIZE, abfsConfiguration.getReadBufferSize());
    }

    @Test
    public void testConfigServiceImplAnnotatedFieldsInitialized_3() throws Exception {
        assertEquals(DEFAULT_MIN_BACKOFF_INTERVAL, abfsConfiguration.getMinBackoffIntervalMilliseconds());
    }

    @Test
    public void testConfigServiceImplAnnotatedFieldsInitialized_4() throws Exception {
        assertEquals(DEFAULT_MAX_BACKOFF_INTERVAL, abfsConfiguration.getMaxBackoffIntervalMilliseconds());
    }

    @Test
    public void testConfigServiceImplAnnotatedFieldsInitialized_5() throws Exception {
        assertEquals(DEFAULT_BACKOFF_INTERVAL, abfsConfiguration.getBackoffIntervalMilliseconds());
    }

    @Test
    public void testConfigServiceImplAnnotatedFieldsInitialized_6() throws Exception {
        assertEquals(DEFAULT_MAX_RETRY_ATTEMPTS, abfsConfiguration.getMaxIoRetries());
    }

    @Test
    public void testConfigServiceImplAnnotatedFieldsInitialized_7() throws Exception {
        assertEquals(MAX_AZURE_BLOCK_SIZE, abfsConfiguration.getAzureBlockSize());
    }

    @Test
    public void testConfigServiceImplAnnotatedFieldsInitialized_8() throws Exception {
        assertEquals(AZURE_BLOCK_LOCATION_HOST_DEFAULT, abfsConfiguration.getAzureBlockLocationHost());
    }

    @Test
    public void testConfigServiceImplAnnotatedFieldsInitialized_9() throws Exception {
        assertEquals(DEFAULT_READ_AHEAD_RANGE, abfsConfiguration.getReadAheadRange());
    }

    @Test
    public void testSSLSocketFactoryConfiguration_1() throws InvalidConfigurationValueException, IllegalAccessException, IOException {
        assertEquals(DelegatingSSLSocketFactory.SSLChannelMode.Default, abfsConfiguration.getPreferredSSLFactoryOption());
    }

    @Test
    public void testSSLSocketFactoryConfiguration_2() throws InvalidConfigurationValueException, IllegalAccessException, IOException {
        assertNotEquals(DelegatingSSLSocketFactory.SSLChannelMode.Default_JSSE, abfsConfiguration.getPreferredSSLFactoryOption());
    }

    @Test
    public void testSSLSocketFactoryConfiguration_3() throws InvalidConfigurationValueException, IllegalAccessException, IOException {
        assertNotEquals(DelegatingSSLSocketFactory.SSLChannelMode.OpenSSL, abfsConfiguration.getPreferredSSLFactoryOption());
    }

    @Test
    public void testSSLSocketFactoryConfiguration_4_testMerged_4() throws InvalidConfigurationValueException, IllegalAccessException, IOException {
        Configuration configuration = new Configuration();
        configuration.setEnum(FS_AZURE_SSL_CHANNEL_MODE_KEY, DelegatingSSLSocketFactory.SSLChannelMode.Default_JSSE);
        AbfsConfiguration localAbfsConfiguration = new AbfsConfiguration(configuration, accountName);
        assertEquals(DelegatingSSLSocketFactory.SSLChannelMode.Default_JSSE, localAbfsConfiguration.getPreferredSSLFactoryOption());
        configuration = new Configuration();
        configuration.setEnum(FS_AZURE_SSL_CHANNEL_MODE_KEY, DelegatingSSLSocketFactory.SSLChannelMode.OpenSSL);
        localAbfsConfiguration = new AbfsConfiguration(configuration, accountName);
        assertEquals(DelegatingSSLSocketFactory.SSLChannelMode.OpenSSL, localAbfsConfiguration.getPreferredSSLFactoryOption());
    }
}
