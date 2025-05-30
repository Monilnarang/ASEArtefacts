package org.apache.dubbo.common.function;

import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.function.Predicates.alwaysFalse;
import static org.apache.dubbo.common.function.Predicates.alwaysTrue;
import static org.apache.dubbo.common.function.Predicates.and;
import static org.apache.dubbo.common.function.Predicates.or;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PredicatesTest_Purified {

    @Test
    void testAnd_1() {
        assertTrue(and(alwaysTrue(), alwaysTrue(), alwaysTrue()).test(null));
    }

    @Test
    void testAnd_2() {
        assertFalse(and(alwaysFalse(), alwaysFalse(), alwaysFalse()).test(null));
    }

    @Test
    void testAnd_3() {
        assertFalse(and(alwaysTrue(), alwaysFalse(), alwaysFalse()).test(null));
    }

    @Test
    void testAnd_4() {
        assertFalse(and(alwaysTrue(), alwaysTrue(), alwaysFalse()).test(null));
    }

    @Test
    void testOr_1() {
        assertTrue(or(alwaysTrue(), alwaysTrue(), alwaysTrue()).test(null));
    }

    @Test
    void testOr_2() {
        assertTrue(or(alwaysTrue(), alwaysTrue(), alwaysFalse()).test(null));
    }

    @Test
    void testOr_3() {
        assertTrue(or(alwaysTrue(), alwaysFalse(), alwaysFalse()).test(null));
    }

    @Test
    void testOr_4() {
        assertFalse(or(alwaysFalse(), alwaysFalse(), alwaysFalse()).test(null));
    }
}
