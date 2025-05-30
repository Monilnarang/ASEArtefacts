package org.apache.seata.core.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GlobalStatusTest_Purified {

    private static final int BEGIN_CODE = 1;

    private static final int NONE = 99;

    private static final int MIN_CODE = 0;

    private static final int MAX_CODE = 15;

    @Test
    public void testIsOnePhaseTimeout_1() {
        Assertions.assertFalse(GlobalStatus.isOnePhaseTimeout(GlobalStatus.Begin));
    }

    @Test
    public void testIsOnePhaseTimeout_2() {
        Assertions.assertFalse(GlobalStatus.isOnePhaseTimeout(GlobalStatus.Rollbacking));
    }

    @Test
    public void testIsOnePhaseTimeout_3() {
        Assertions.assertTrue(GlobalStatus.isOnePhaseTimeout(GlobalStatus.TimeoutRollbacking));
    }

    @Test
    public void testIsOnePhaseTimeout_4() {
        Assertions.assertTrue(GlobalStatus.isOnePhaseTimeout(GlobalStatus.TimeoutRollbackRetrying));
    }

    @Test
    public void testIsOnePhaseTimeout_5() {
        Assertions.assertTrue(GlobalStatus.isOnePhaseTimeout(GlobalStatus.TimeoutRollbacked));
    }

    @Test
    public void testIsOnePhaseTimeout_6() {
        Assertions.assertTrue(GlobalStatus.isOnePhaseTimeout(GlobalStatus.TimeoutRollbackFailed));
    }

    @Test
    public void testIsTwoPhaseSuccess_1() {
        Assertions.assertTrue(GlobalStatus.isTwoPhaseSuccess(GlobalStatus.Committed));
    }

    @Test
    public void testIsTwoPhaseSuccess_2() {
        Assertions.assertTrue(GlobalStatus.isTwoPhaseSuccess(GlobalStatus.Rollbacked));
    }

    @Test
    public void testIsTwoPhaseSuccess_3() {
        Assertions.assertTrue(GlobalStatus.isTwoPhaseSuccess(GlobalStatus.TimeoutRollbacked));
    }

    @Test
    public void testIsTwoPhaseSuccess_4() {
        Assertions.assertFalse(GlobalStatus.isTwoPhaseSuccess(GlobalStatus.Begin));
    }

    @Test
    public void testIsTwoPhaseHeuristic_1() {
        Assertions.assertTrue(GlobalStatus.isTwoPhaseHeuristic(GlobalStatus.Finished));
    }

    @Test
    public void testIsTwoPhaseHeuristic_2() {
        Assertions.assertFalse(GlobalStatus.isTwoPhaseHeuristic(GlobalStatus.Begin));
    }
}
