package org.apache.rocketmq.proxy.service.transaction;

import java.time.Duration;
import java.util.Random;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.message.MessageClientIDSetter;
import org.apache.rocketmq.proxy.config.InitConfigTest;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class TransactionDataManagerTest_Purified extends InitConfigTest {

    private static final String PRODUCER_GROUP = "producerGroup";

    private static final Random RANDOM = new Random();

    private TransactionDataManager transactionDataManager;

    @Before
    public void before() throws Throwable {
        super.before();
        this.transactionDataManager = new TransactionDataManager();
    }

    @After
    public void after() {
        super.after();
    }

    private static TransactionData createTransactionData() {
        return createTransactionData(MessageClientIDSetter.createUniqID());
    }

    private static TransactionData createTransactionData(String txId) {
        return createTransactionData(txId, System.currentTimeMillis());
    }

    private static TransactionData createTransactionData(String txId, long checkTimestamp) {
        return createTransactionData(txId, checkTimestamp, Duration.ofMinutes(1).toMillis());
    }

    private static TransactionData createTransactionData(String txId, long checkTimestamp, long checkImmunityTime) {
        return new TransactionData("brokerName", "topicName", RANDOM.nextLong(), RANDOM.nextLong(), txId, checkTimestamp, checkImmunityTime);
    }

    @Test
    public void testAddAndRemove_1() {
        assertEquals(1, this.transactionDataManager.transactionIdDataMap.size());
    }

    @Test
    public void testAddAndRemove_2() {
        TransactionData transactionData1 = createTransactionData();
        TransactionData transactionData2 = createTransactionData(transactionData1.getTransactionId());
        this.transactionDataManager.addTransactionData(PRODUCER_GROUP, transactionData1.getTransactionId(), transactionData1);
        this.transactionDataManager.addTransactionData(PRODUCER_GROUP, transactionData1.getTransactionId(), transactionData2);
        assertEquals(2, this.transactionDataManager.transactionIdDataMap.get(transactionDataManager.buildKey(PRODUCER_GROUP, transactionData1.getTransactionId())).size());
    }

    @Test
    public void testAddAndRemove_3() {
        assertEquals(1, this.transactionDataManager.transactionIdDataMap.size());
    }

    @Test
    public void testAddAndRemove_4() {
        assertEquals(0, this.transactionDataManager.transactionIdDataMap.size());
    }
}
