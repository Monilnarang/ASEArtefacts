package org.apache.hadoop.security.token.delegation;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.hadoop.fs.statistics.IOStatisticAssertions;
import org.apache.hadoop.fs.statistics.MeanStatistic;
import org.apache.hadoop.metrics2.lib.DefaultMetricsSystem;
import org.apache.hadoop.metrics2.lib.MutableCounterLong;
import org.apache.hadoop.metrics2.lib.MutableRate;
import org.apache.hadoop.test.LambdaTestUtils;
import org.junit.Assert;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.UserGroupInformation.AuthenticationMethod;
import org.apache.hadoop.security.token.SecretManager;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.SecretManager.InvalidToken;
import org.apache.hadoop.security.token.TokenIdentifier;
import org.apache.hadoop.security.token.delegation.AbstractDelegationTokenSecretManager.DelegationTokenInformation;
import org.apache.hadoop.util.Daemon;
import org.apache.hadoop.util.Time;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestDelegationToken_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestDelegationToken.class);

    private static final Text KIND = new Text("MY KIND");

    public static class TestDelegationTokenIdentifier extends AbstractDelegationTokenIdentifier implements Writable {

        public TestDelegationTokenIdentifier() {
        }

        public TestDelegationTokenIdentifier(Text owner, Text renewer, Text realUser) {
            super(owner, renewer, realUser);
        }

        @Override
        public Text getKind() {
            return KIND;
        }

        @Override
        public void write(DataOutput out) throws IOException {
            super.write(out);
        }

        @Override
        public void readFields(DataInput in) throws IOException {
            super.readFields(in);
        }
    }

    public static class TestDelegationTokenSecretManager extends AbstractDelegationTokenSecretManager<TestDelegationTokenIdentifier> {

        public boolean isStoreNewMasterKeyCalled = false;

        public boolean isRemoveStoredMasterKeyCalled = false;

        public boolean isStoreNewTokenCalled = false;

        public boolean isRemoveStoredTokenCalled = false;

        public boolean isUpdateStoredTokenCalled = false;

        public TestDelegationTokenSecretManager(long delegationKeyUpdateInterval, long delegationTokenMaxLifetime, long delegationTokenRenewInterval, long delegationTokenRemoverScanInterval) {
            super(delegationKeyUpdateInterval, delegationTokenMaxLifetime, delegationTokenRenewInterval, delegationTokenRemoverScanInterval);
        }

        @Override
        public TestDelegationTokenIdentifier createIdentifier() {
            return new TestDelegationTokenIdentifier();
        }

        @Override
        protected byte[] createPassword(TestDelegationTokenIdentifier t) {
            return super.createPassword(t);
        }

        @Override
        protected void storeNewMasterKey(DelegationKey key) throws IOException {
            isStoreNewMasterKeyCalled = true;
            super.storeNewMasterKey(key);
        }

        @Override
        protected void removeStoredMasterKey(DelegationKey key) {
            isRemoveStoredMasterKeyCalled = true;
            Assert.assertFalse(key.equals(allKeys.get(currentId)));
        }

        @Override
        protected void storeNewToken(TestDelegationTokenIdentifier ident, long renewDate) throws IOException {
            super.storeNewToken(ident, renewDate);
            isStoreNewTokenCalled = true;
        }

        @Override
        protected void removeStoredToken(TestDelegationTokenIdentifier ident) throws IOException {
            super.removeStoredToken(ident);
            isRemoveStoredTokenCalled = true;
        }

        @Override
        protected void updateStoredToken(TestDelegationTokenIdentifier ident, long renewDate) throws IOException {
            super.updateStoredToken(ident, renewDate);
            isUpdateStoredTokenCalled = true;
        }

        public byte[] createPassword(TestDelegationTokenIdentifier t, DelegationKey key) {
            return SecretManager.createPassword(t.getBytes(), key.getKey());
        }

        public Map<TestDelegationTokenIdentifier, DelegationTokenInformation> getAllTokens() {
            return currentTokens;
        }

        public DelegationKey getKey(TestDelegationTokenIdentifier id) {
            return allKeys.get(id.getMasterKeyId());
        }
    }

    public static class TestFailureDelegationTokenSecretManager extends TestDelegationTokenSecretManager {

        private boolean throwError = false;

        private long errorSleepMillis;

        public TestFailureDelegationTokenSecretManager(long errorSleepMillis) {
            super(24 * 60 * 60 * 1000, 10 * 1000, 1 * 1000, 60 * 60 * 1000);
            this.errorSleepMillis = errorSleepMillis;
        }

        public void setThrowError(boolean throwError) {
            this.throwError = throwError;
        }

        private void sleepAndThrow() throws IOException {
            try {
                Thread.sleep(errorSleepMillis);
                throw new IOException("Test exception");
            } catch (InterruptedException e) {
            }
        }

        @Override
        protected void storeNewToken(TestDelegationTokenIdentifier ident, long renewDate) throws IOException {
            if (throwError) {
                sleepAndThrow();
            }
            super.storeNewToken(ident, renewDate);
        }

        @Override
        protected void removeStoredToken(TestDelegationTokenIdentifier ident) throws IOException {
            if (throwError) {
                sleepAndThrow();
            }
            super.removeStoredToken(ident);
        }

        @Override
        protected void updateStoredToken(TestDelegationTokenIdentifier ident, long renewDate) throws IOException {
            if (throwError) {
                sleepAndThrow();
            }
            super.updateStoredToken(ident, renewDate);
        }
    }

    public static class TokenSelector extends AbstractDelegationTokenSelector<TestDelegationTokenIdentifier> {

        protected TokenSelector() {
            super(KIND);
        }
    }

    private Token<TestDelegationTokenIdentifier> generateDelegationToken(TestDelegationTokenSecretManager dtSecretManager, String owner, String renewer) {
        TestDelegationTokenIdentifier dtId = new TestDelegationTokenIdentifier(new Text(owner), new Text(renewer), null);
        return new Token<TestDelegationTokenIdentifier>(dtId, dtSecretManager);
    }

    private void shouldThrow(PrivilegedExceptionAction<Object> action, Class<? extends Throwable> except) {
        try {
            action.run();
            Assert.fail("action did not throw " + except);
        } catch (Throwable th) {
            LOG.info("Caught an exception: ", th);
            assertEquals("action threw wrong exception", except, th.getClass());
        }
    }

    private <T> T callAndValidateMetrics(TestDelegationTokenSecretManager dtSecretManager, MutableRate metric, String statName, Callable<T> callable) throws Exception {
        MeanStatistic stat = IOStatisticAssertions.lookupMeanStatistic(dtSecretManager.getMetrics().getIoStatistics(), statName + ".mean");
        long metricBefore = metric.lastStat().numSamples();
        long statBefore = stat.getSamples();
        T returnedObject = callable.call();
        assertEquals(metricBefore + 1, metric.lastStat().numSamples());
        assertEquals(statBefore + 1, stat.getSamples());
        return returnedObject;
    }

    private <T> void callAndValidateFailureMetrics(TestDelegationTokenSecretManager dtSecretManager, String statName, boolean expectError, int errorSleepMillis, Callable<T> callable) throws Exception {
        MutableCounterLong counter = dtSecretManager.getMetrics().getTokenFailure();
        MeanStatistic failureStat = IOStatisticAssertions.lookupMeanStatistic(dtSecretManager.getMetrics().getIoStatistics(), statName + ".failures.mean");
        long counterBefore = counter.value();
        long statBefore = failureStat.getSamples();
        if (expectError) {
            LambdaTestUtils.intercept(IOException.class, callable);
        } else {
            callable.call();
        }
        assertEquals(counterBefore + 1, counter.value());
        assertEquals(statBefore + 1, failureStat.getSamples());
        assertTrue(failureStat.getSum() >= errorSleepMillis);
    }

    @Test
    public void testSimpleDtidSerialization_1() throws IOException {
        assertTrue(testDelegationTokenIdentiferSerializationRoundTrip(new Text("owner"), new Text("renewer"), new Text("realUser")));
    }

    @Test
    public void testSimpleDtidSerialization_2() throws IOException {
        assertTrue(testDelegationTokenIdentiferSerializationRoundTrip(new Text(""), new Text(""), new Text("")));
    }

    @Test
    public void testSimpleDtidSerialization_3() throws IOException {
        assertTrue(testDelegationTokenIdentiferSerializationRoundTrip(new Text(""), new Text("b"), new Text("")));
    }

    @Test
    public void testMultipleDelegationTokenSecretManagerMetrics_1() {
        TestDelegationTokenSecretManager dtSecretManager1 = new TestDelegationTokenSecretManager(0, 0, 0, 0);
        assertNotNull(dtSecretManager1.getMetrics());
    }

    @Test
    public void testMultipleDelegationTokenSecretManagerMetrics_2() {
        TestDelegationTokenSecretManager dtSecretManager2 = new TestDelegationTokenSecretManager(0, 0, 0, 0);
        assertNotNull(dtSecretManager2.getMetrics());
    }

    @Test
    public void testMultipleDelegationTokenSecretManagerMetrics_3() {
        TestDelegationTokenSecretManager dtSecretManager3 = new TestDelegationTokenSecretManager(0, 0, 0, 0);
        assertNotNull(dtSecretManager3.getMetrics());
    }
}
