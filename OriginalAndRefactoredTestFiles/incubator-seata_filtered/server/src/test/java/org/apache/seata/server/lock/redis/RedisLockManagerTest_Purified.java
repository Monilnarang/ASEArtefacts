package org.apache.seata.server.lock.redis;

import org.apache.seata.common.exception.StoreException;
import java.io.IOException;
import org.apache.seata.core.exception.TransactionException;
import org.apache.seata.core.lock.Locker;
import org.apache.seata.core.model.LockStatus;
import org.apache.seata.server.lock.LockManager;
import org.apache.seata.server.session.BranchSession;
import org.apache.seata.server.storage.redis.JedisPooledFactory;
import org.apache.seata.server.storage.redis.lock.RedisLockManager;
import org.apache.seata.server.storage.redis.lock.RedisLocker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootTest
@EnabledIfSystemProperty(named = "redisCaseEnabled", matches = "true")
public class RedisLockManagerTest_Purified {

    static LockManager lockManager = null;

    static Jedis jedis = null;

    @BeforeAll
    public static void start(ApplicationContext context) throws IOException {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(1);
        poolConfig.setMaxIdle(10);
        JedisPooledFactory.getJedisPoolInstance(new JedisPool(poolConfig, "127.0.0.1", 6379, 60000)).getResource();
        lockManager = new RedisLockManagerForTest();
    }

    public static class RedisLockManagerForTest extends RedisLockManager {

        public RedisLockManagerForTest() {
        }

        @Override
        public Locker getLocker(BranchSession branchSession) {
            return new RedisLocker();
        }
    }

    private BranchSession getBranchSession() {
        BranchSession branchSession = new BranchSession();
        branchSession.setXid("abc-123:786756");
        branchSession.setTransactionId(123543465);
        branchSession.setBranchId(5756678);
        branchSession.setResourceId("abcss");
        branchSession.setLockKey("t1:13,14;t2:11,12");
        return branchSession;
    }

    @Test
    public void acquireLockByLuaHolding_1_testMerged_1() throws TransactionException {
        BranchSession branchLockSession = getBranchSession();
        Assertions.assertTrue(lockManager.acquireLock(branchLockSession));
        Assertions.assertTrue(lockManager.releaseLock(branchLockSession));
    }

    @Test
    public void acquireLockByLuaHolding_2() throws TransactionException {
        BranchSession branchSession = getBranchSession();
        branchSession.setXid("abc-123:786754");
        Assertions.assertFalse(lockManager.acquireLock(branchSession));
    }

    @Test
    public void isLockable_1_testMerged_1() throws TransactionException {
        BranchSession branchSession = new BranchSession();
        branchSession.setXid("abc-123:56877898");
        branchSession.setTransactionId(245686786);
        branchSession.setBranchId(467568);
        branchSession.setResourceId("abcss");
        branchSession.setLockKey("t1:8,7;t2:1,2");
        Assertions.assertTrue(lockManager.acquireLock(branchSession));
        Assertions.assertTrue(lockManager.releaseLock(branchSession));
    }

    @Test
    public void isLockable_2() throws TransactionException {
        BranchSession branchSession2 = new BranchSession();
        branchSession2.setXid("abc-123:56877898");
        branchSession2.setTransactionId(245686786);
        branchSession2.setBranchId(1242354576);
        branchSession2.setResourceId("abcss");
        branchSession2.setLockKey("t1:8");
        Assertions.assertTrue(lockManager.isLockable(branchSession2.getXid(), branchSession2.getResourceId(), branchSession2.getLockKey()));
    }
}
