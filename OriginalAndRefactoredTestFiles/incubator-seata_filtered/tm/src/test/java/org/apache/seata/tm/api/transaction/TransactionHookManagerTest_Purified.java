package org.apache.seata.tm.api.transaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionHookManagerTest_Purified {

    @AfterEach
    public void clear() {
        TransactionHookManager.clear();
    }

    @Test
    public void testGetHooks_1() {
        assertThat(TransactionHookManager.getHooks()).isEmpty();
    }

    @Test
    public void testGetHooks_2() {
        TransactionHookManager.registerHook(new TransactionHookAdapter());
        assertThat(TransactionHookManager.getHooks()).isNotEmpty();
    }

    @Test
    public void testClear_1() {
        assertThat(TransactionHookManager.getHooks()).isEmpty();
    }

    @Test
    public void testClear_2() {
        TransactionHookManager.registerHook(new TransactionHookAdapter());
        assertThat(TransactionHookManager.getHooks()).isNotEmpty();
    }

    @Test
    public void testClear_3() {
        assertThat(TransactionHookManager.getHooks()).isEmpty();
    }
}
