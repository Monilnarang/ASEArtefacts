package org.apache.hadoop.fs.s3a.auth;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.DefaultRequest;
import com.amazonaws.SignableRequest;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.Signer;
import com.amazonaws.auth.SignerFactory;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.s3a.auth.TestSignerManager.SignerInitializerForTest.StoreValue;
import org.apache.hadoop.fs.s3a.auth.delegation.DelegationTokenProvider;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.TokenIdentifier;
import org.apache.hadoop.test.LambdaTestUtils;
import static org.apache.hadoop.fs.s3a.Constants.CUSTOM_SIGNERS;

public class TestSignerManager_Purified {

    private static final Text TEST_TOKEN_KIND = new Text("TestTokenKind");

    private static final Text TEST_TOKEN_SERVICE = new Text("TestTokenService");

    private static final String TEST_KEY_IDENTIFIER = "TEST_KEY_IDENTIFIER";

    private static final String BUCKET1 = "bucket1";

    private static final String BUCKET2 = "bucket2";

    private static final String TESTUSER1 = "testuser1";

    private static final String TESTUSER2 = "testuser2";

    @Rule
    public Timeout testTimeout = new Timeout(10_000L, TimeUnit.MILLISECONDS);

    @Before
    public void beforeTest() {
        SignerForTest1.reset();
        SignerForTest2.reset();
        SignerInitializerForTest.reset();
        SignerForInitializerTest.reset();
        SignerInitializer2ForTest.reset();
    }

    private void attemptSignAndVerify(String identifier, String bucket, UserGroupInformation ugi, boolean expectNullStoreInfo) throws IOException, InterruptedException {
        ugi.doAs((PrivilegedExceptionAction<Void>) () -> {
            Signer signer = new SignerForInitializerTest();
            SignableRequest<?> signableRequest = constructSignableRequest(bucket);
            signer.sign(signableRequest, null);
            verifyStoreValueInSigner(expectNullStoreInfo, bucket, identifier);
            return null;
        });
    }

    private void verifyStoreValueInSigner(boolean expectNull, String bucketName, String identifier) throws IOException {
        if (expectNull) {
            Assertions.assertThat(SignerForInitializerTest.retrievedStoreValue).as("Retrieved store value expected to be null").isNull();
        } else {
            StoreValue storeValue = SignerForInitializerTest.retrievedStoreValue;
            Assertions.assertThat(storeValue).as("StoreValue should not be null").isNotNull();
            Assertions.assertThat(storeValue.getBucketName()).as("Bucket Name mismatch").isEqualTo(bucketName);
            Configuration conf = storeValue.getStoreConf();
            Assertions.assertThat(conf).as("Configuration should not be null").isNotNull();
            Assertions.assertThat(conf.get(TEST_KEY_IDENTIFIER)).as("Identifier mistmatch").isEqualTo(identifier);
            Token<? extends TokenIdentifier> token = storeValue.getDtProvider().getFsDelegationToken();
            String tokenId = new String(token.getIdentifier(), StandardCharsets.UTF_8);
            Assertions.assertThat(tokenId).as("Mismatch in delegation token identifier").isEqualTo(createTokenIdentifierString(identifier, bucketName, UserGroupInformation.getCurrentUser().getShortUserName()));
        }
    }

    private void closeAndVerifyNull(Closeable closeable, String bucketName, UserGroupInformation ugi, int expectedCount) throws IOException, InterruptedException {
        closeable.close();
        attemptSignAndVerify("dontcare", bucketName, ugi, true);
        Assertions.assertThat(SignerInitializerForTest.storeCache.size()).as("StoreCache size mismatch").isEqualTo(expectedCount);
    }

    @Private
    public static class SignerForTest1 implements Signer {

        private static boolean initialized = false;

        @Override
        public void sign(SignableRequest<?> request, AWSCredentials credentials) {
            initialized = true;
        }

        public static void reset() {
            initialized = false;
        }
    }

    @Private
    public static class SignerForTest2 implements Signer {

        private static boolean initialized = false;

        @Override
        public void sign(SignableRequest<?> request, AWSCredentials credentials) {
            initialized = true;
        }

        public static void reset() {
            initialized = false;
        }
    }

    @Private
    public static class SignerInitializerForTest implements AwsSignerInitializer {

        private static int registerCount = 0;

        private static int unregisterCount = 0;

        private static int instanceCount = 0;

        private static final Map<StoreKey, StoreValue> storeCache = new HashMap<>();

        public SignerInitializerForTest() {
            instanceCount++;
        }

        @Override
        public void registerStore(String bucketName, Configuration storeConf, DelegationTokenProvider dtProvider, UserGroupInformation storeUgi) {
            registerCount++;
            StoreKey storeKey = new StoreKey(bucketName, storeUgi);
            StoreValue storeValue = new StoreValue(bucketName, storeConf, dtProvider);
            storeCache.put(storeKey, storeValue);
        }

        @Override
        public void unregisterStore(String bucketName, Configuration storeConf, DelegationTokenProvider dtProvider, UserGroupInformation storeUgi) {
            unregisterCount++;
            StoreKey storeKey = new StoreKey(bucketName, storeUgi);
            storeCache.remove(storeKey);
        }

        public static void reset() {
            registerCount = 0;
            unregisterCount = 0;
            instanceCount = 0;
            storeCache.clear();
        }

        public static StoreValue getStoreInfo(String bucketName, UserGroupInformation storeUgi) {
            StoreKey storeKey = new StoreKey(bucketName, storeUgi);
            return storeCache.get(storeKey);
        }

        private static class StoreKey {

            private final String bucketName;

            private final UserGroupInformation ugi;

            public StoreKey(String bucketName, UserGroupInformation ugi) {
                this.bucketName = bucketName;
                this.ugi = ugi;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                StoreKey storeKey = (StoreKey) o;
                return Objects.equals(bucketName, storeKey.bucketName) && Objects.equals(ugi, storeKey.ugi);
            }

            @Override
            public int hashCode() {
                return Objects.hash(bucketName, ugi);
            }
        }

        static class StoreValue {

            private final String bucketName;

            private final Configuration storeConf;

            private final DelegationTokenProvider dtProvider;

            public StoreValue(String bucketName, Configuration storeConf, DelegationTokenProvider dtProvider) {
                this.bucketName = bucketName;
                this.storeConf = storeConf;
                this.dtProvider = dtProvider;
            }

            String getBucketName() {
                return bucketName;
            }

            Configuration getStoreConf() {
                return storeConf;
            }

            DelegationTokenProvider getDtProvider() {
                return dtProvider;
            }
        }
    }

    @Private
    public static class SignerForInitializerTest implements Signer {

        private static StoreValue retrievedStoreValue;

        @Override
        public void sign(SignableRequest<?> request, AWSCredentials credentials) {
            String bucketName = request.getEndpoint().getHost();
            try {
                retrievedStoreValue = SignerInitializerForTest.getStoreInfo(bucketName, UserGroupInformation.getCurrentUser());
            } catch (IOException e) {
                throw new RuntimeException("Failed to get current ugi", e);
            }
        }

        public static void reset() {
            retrievedStoreValue = null;
        }
    }

    @Private
    private static class DelegationTokenProviderForTest implements DelegationTokenProvider {

        private final Token<? extends TokenIdentifier> token;

        private DelegationTokenProviderForTest(Token<? extends TokenIdentifier> token) {
            this.token = token;
        }

        @Override
        public Token<? extends TokenIdentifier> getFsDelegationToken() throws IOException {
            return this.token;
        }
    }

    @Private
    public static class SignerInitializer2ForTest implements AwsSignerInitializer {

        private static int registerCount = 0;

        private static int unregisterCount = 0;

        private static int instanceCount = 0;

        public SignerInitializer2ForTest() {
            instanceCount++;
        }

        @Override
        public void registerStore(String bucketName, Configuration storeConf, DelegationTokenProvider dtProvider, UserGroupInformation storeUgi) {
            registerCount++;
        }

        @Override
        public void unregisterStore(String bucketName, Configuration storeConf, DelegationTokenProvider dtProvider, UserGroupInformation storeUgi) {
            unregisterCount++;
        }

        public static void reset() {
            registerCount = 0;
            unregisterCount = 0;
            instanceCount = 0;
        }
    }

    private Token<? extends TokenIdentifier> createTokenForTest(String idString) {
        byte[] identifier = idString.getBytes(StandardCharsets.UTF_8);
        byte[] password = "notapassword".getBytes(StandardCharsets.UTF_8);
        Token<? extends TokenIdentifier> token = new Token<>(identifier, password, TEST_TOKEN_KIND, TEST_TOKEN_SERVICE);
        return token;
    }

    private SignerManager fakeS3AInstanceCreation(String identifier, Class<? extends Signer> signerClazz, Class<? extends AwsSignerInitializer> signerInitializerClazz, String bucketName, UserGroupInformation ugi) {
        Objects.requireNonNull(signerClazz, "SignerClazz missing");
        Objects.requireNonNull(signerInitializerClazz, "SignerInitializerClazzMissing");
        Configuration config = new Configuration();
        config.set(TEST_KEY_IDENTIFIER, identifier);
        config.set(CUSTOM_SIGNERS, signerClazz.getCanonicalName() + ":" + signerClazz.getName() + ":" + signerInitializerClazz.getName());
        Token<? extends TokenIdentifier> token1 = createTokenForTest(createTokenIdentifierString(identifier, bucketName, ugi.getShortUserName()));
        DelegationTokenProvider dtProvider1 = new DelegationTokenProviderForTest(token1);
        SignerManager signerManager = new SignerManager(bucketName, dtProvider1, config, ugi);
        signerManager.initCustomSigners();
        return signerManager;
    }

    private String createTokenIdentifierString(String identifier, String bucketName, String user) {
        return identifier + "_" + bucketName + "_" + user;
    }

    private SignableRequest<?> constructSignableRequest(String bucketName) throws URISyntaxException {
        DefaultRequest signableRequest = new DefaultRequest(AmazonWebServiceRequest.NOOP, "fakeservice");
        URI uri = new URI("s3://" + bucketName + "/");
        signableRequest.setEndpoint(uri);
        return signableRequest;
    }

    @Test
    public void testMultipleCustomSignerInitialization_1() throws IOException {
    }

    @Test
    public void testMultipleCustomSignerInitialization_2() throws IOException {
    }

    @Test
    public void testSimpleSignerInitializer_1() throws IOException {
    }

    @Test
    public void testSimpleSignerInitializer_2() throws IOException {
    }

    @Test
    public void testSimpleSignerInitializer_3() throws IOException {
    }

    @Test
    public void testSimpleSignerInitializer_4() throws IOException {
    }

    @Test
    public void testMultipleSignerInitializers_1() throws IOException {
    }

    @Test
    public void testMultipleSignerInitializers_2() throws IOException {
    }

    @Test
    public void testMultipleSignerInitializers_3() throws IOException {
    }

    @Test
    public void testMultipleSignerInitializers_4() throws IOException {
    }

    @Test
    public void testMultipleSignerInitializers_5() throws IOException {
    }

    @Test
    public void testMultipleSignerInitializers_6() throws IOException {
    }

    @Test
    public void testMultipleSignerInitializers_7() throws IOException {
    }

    @Test
    public void testMultipleSignerInitializers_8() throws IOException {
    }

    @Test
    public void testSignerInitializerMultipleInstances_1() throws IOException, InterruptedException {
    }

    @Test
    public void testSignerInitializerMultipleInstances_2() throws IOException, InterruptedException {
    }

    @Test
    public void testSignerInitializerMultipleInstances_3() throws IOException, InterruptedException {
    }

    @Test
    public void testSignerInitializerMultipleInstances_4() throws IOException, InterruptedException {
    }
}
