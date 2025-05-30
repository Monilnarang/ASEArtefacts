package org.apache.hadoop.fs.s3a.auth.delegation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import org.apache.hadoop.fs.s3a.AWSCredentialProviderList;
import org.apache.hadoop.fs.s3a.Constants;
import org.apache.hadoop.fs.s3a.DefaultS3ClientFactory;
import org.apache.hadoop.fs.s3a.Invoker;
import org.apache.hadoop.fs.s3a.S3AFileSystem;
import org.apache.hadoop.fs.s3a.S3ATestUtils;
import org.apache.hadoop.fs.s3a.S3ClientFactory;
import org.apache.hadoop.fs.s3a.Statistic;
import org.apache.hadoop.fs.s3a.statistics.impl.EmptyS3AStatisticsContext;
import org.apache.hadoop.hdfs.tools.DelegationTokenFetcher;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.security.TokenCache;
import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.token.DtUtilShell;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.TokenIdentifier;
import org.apache.hadoop.service.ServiceOperations;
import org.apache.hadoop.service.ServiceStateException;
import org.apache.hadoop.util.ExitUtil;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import static java.util.Objects.requireNonNull;
import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.HADOOP_SECURITY_AUTHENTICATION;
import static org.apache.hadoop.fs.s3a.Constants.*;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.assumeSessionTestsEnabled;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.disableFilesystemCaching;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.getTestBucketName;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.removeBaseAndBucketOverrides;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.unsetHadoopCredentialProviders;
import static org.apache.hadoop.fs.s3a.S3AUtils.getEncryptionAlgorithm;
import static org.apache.hadoop.fs.s3a.S3AUtils.getS3EncryptionKey;
import static org.apache.hadoop.fs.s3a.auth.delegation.DelegationConstants.*;
import static org.apache.hadoop.fs.s3a.auth.delegation.DelegationTokenIOException.TOKEN_MISMATCH;
import static org.apache.hadoop.fs.s3a.auth.delegation.MiniKerberizedHadoopCluster.ALICE;
import static org.apache.hadoop.fs.s3a.auth.delegation.MiniKerberizedHadoopCluster.assertSecurityEnabled;
import static org.apache.hadoop.fs.s3a.auth.delegation.S3ADelegationTokens.lookupS3ADelegationToken;
import static org.apache.hadoop.test.LambdaTestUtils.doAs;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SuppressWarnings("StaticNonFinalField")
public class ITestSessionDelegationInFilesystem_Purified extends AbstractDelegationIT {

    private static final Logger LOG = LoggerFactory.getLogger(ITestSessionDelegationInFilesystem.class);

    private static MiniKerberizedHadoopCluster cluster;

    private UserGroupInformation bobUser;

    private UserGroupInformation aliceUser;

    private S3ADelegationTokens delegationTokens;

    @BeforeClass
    public static void setupCluster() throws Exception {
        cluster = new MiniKerberizedHadoopCluster();
        cluster.init(new Configuration());
        cluster.start();
    }

    @SuppressWarnings("ThrowableNotThrown")
    @AfterClass
    public static void teardownCluster() throws Exception {
        ServiceOperations.stopQuietly(LOG, cluster);
    }

    protected static MiniKerberizedHadoopCluster getCluster() {
        return cluster;
    }

    protected String getDelegationBinding() {
        return DELEGATION_TOKEN_SESSION_BINDING;
    }

    public Text getTokenKind() {
        return SESSION_TOKEN_KIND;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Configuration createConfiguration() {
        Configuration conf = super.createConfiguration();
        assumeSessionTestsEnabled(conf);
        disableFilesystemCaching(conf);
        String s3EncryptionMethod;
        try {
            s3EncryptionMethod = getEncryptionAlgorithm(getTestBucketName(conf), conf).getMethod();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to lookup encryption algorithm.", e);
        }
        String s3EncryptionKey = getS3EncryptionKey(getTestBucketName(conf), conf);
        removeBaseAndBucketOverrides(conf, DELEGATION_TOKEN_BINDING, Constants.S3_ENCRYPTION_ALGORITHM, Constants.S3_ENCRYPTION_KEY, SERVER_SIDE_ENCRYPTION_ALGORITHM, SERVER_SIDE_ENCRYPTION_KEY);
        conf.set(HADOOP_SECURITY_AUTHENTICATION, UserGroupInformation.AuthenticationMethod.KERBEROS.name());
        enableDelegationTokens(conf, getDelegationBinding());
        conf.set(AWS_CREDENTIALS_PROVIDER, " ");
        if (conf.getBoolean(KEY_ENCRYPTION_TESTS, true)) {
            conf.set(Constants.S3_ENCRYPTION_ALGORITHM, s3EncryptionMethod);
            conf.set(Constants.S3_ENCRYPTION_KEY, s3EncryptionKey);
        }
        conf.set(YarnConfiguration.RM_PRINCIPAL, YARN_RM);
        conf.set(CANNED_ACL, LOG_DELIVERY_WRITE);
        return conf;
    }

    @Override
    public void setup() throws Exception {
        resetUGI();
        UserGroupInformation.setConfiguration(createConfiguration());
        aliceUser = cluster.createAliceUser();
        bobUser = cluster.createBobUser();
        UserGroupInformation.setLoginUser(aliceUser);
        assertSecurityEnabled();
        super.setup();
        S3AFileSystem fs = getFileSystem();
        assertNull("Unexpectedly found an S3A token", lookupS3ADelegationToken(UserGroupInformation.getCurrentUser().getCredentials(), fs.getUri()));
        delegationTokens = instantiateDTSupport(getConfiguration());
    }

    @SuppressWarnings("ThrowableNotThrown")
    @Override
    public void teardown() throws Exception {
        super.teardown();
        ServiceOperations.stopQuietly(LOG, delegationTokens);
        FileSystem.closeAllForUGI(UserGroupInformation.getCurrentUser());
        MiniKerberizedHadoopCluster.closeUserFileSystems(aliceUser);
        MiniKerberizedHadoopCluster.closeUserFileSystems(bobUser);
        cluster.resetUGI();
    }

    protected boolean encryptionTestEnabled() {
        return getConfiguration().getBoolean(KEY_ENCRYPTION_TESTS, true);
    }

    protected Credentials createDelegationTokens() throws IOException {
        return mkTokens(getFileSystem());
    }

    protected void executeDelegatedFSOperations(final S3AFileSystem delegatedFS, final Path testPath) throws Exception {
        ContractTestUtils.assertIsDirectory(delegatedFS, new Path("/"));
        ContractTestUtils.touch(delegatedFS, testPath);
        ContractTestUtils.assertDeleted(delegatedFS, testPath, false);
        delegatedFS.mkdirs(testPath);
        ContractTestUtils.assertIsDirectory(delegatedFS, testPath);
        Path srcFile = new Path(testPath, "src.txt");
        Path destFile = new Path(testPath, "dest.txt");
        ContractTestUtils.touch(delegatedFS, srcFile);
        ContractTestUtils.rename(delegatedFS, srcFile, destFile);
        ContractTestUtils.assertIsFile(delegatedFS, destFile);
        ContractTestUtils.assertDeleted(delegatedFS, testPath, true);
    }

    protected void verifyRestrictedPermissions(final S3AFileSystem delegatedFS) throws Exception {
        readLandsatMetadata(delegatedFS);
    }

    @SuppressWarnings("deprecation")
    protected ObjectMetadata readLandsatMetadata(final S3AFileSystem delegatedFS) throws Exception {
        AWSCredentialProviderList testingCreds = delegatedFS.shareCredentials("testing");
        URI landsat = new URI(DEFAULT_CSVTEST_FILE);
        DefaultS3ClientFactory factory = new DefaultS3ClientFactory();
        factory.setConf(new Configuration(delegatedFS.getConf()));
        String host = landsat.getHost();
        S3ClientFactory.S3ClientCreationParameters parameters = null;
        parameters = new S3ClientFactory.S3ClientCreationParameters().withCredentialSet(testingCreds).withPathUri(new URI("s3a://localhost/")).withEndpoint(DEFAULT_ENDPOINT).withMetrics(new EmptyS3AStatisticsContext().newStatisticsFromAwsSdk()).withUserAgentSuffix("ITestSessionDelegationInFilesystem");
        AmazonS3 s3 = factory.createS3Client(landsat, parameters);
        return Invoker.once("HEAD", host, () -> s3.getObjectMetadata(host, landsat.getPath().substring(1)));
    }

    protected File createTempTokenFile() throws IOException {
        File tokenfile = File.createTempFile("tokens", ".bin", cluster.getWorkDir());
        tokenfile.delete();
        return tokenfile;
    }

    private String[] args(String... args) {
        return args;
    }

    protected String dtutil(int expected, String... args) throws Exception {
        final ByteArrayOutputStream dtUtilContent = new ByteArrayOutputStream();
        DtUtilShell dt = new DtUtilShell();
        dt.setOut(new PrintStream(dtUtilContent));
        dtUtilContent.reset();
        int r = doAs(aliceUser, () -> ToolRunner.run(getConfiguration(), dt, args));
        String s = dtUtilContent.toString();
        LOG.info("\n{}", s);
        assertEquals(expected, r);
        return s;
    }

    @Test
    public void testDTCredentialProviderFromCurrentUserCreds_1() throws Throwable {
        Credentials cred = createDelegationTokens();
        assertThat("Token size", cred.getAllTokens(), hasSize(1));
    }

    @Test
    public void testDTCredentialProviderFromCurrentUserCreds_2() throws Throwable {
        delegationTokens.start();
        assertTrue("bind to existing DT failed", delegationTokens.isBoundToDT());
    }
}
