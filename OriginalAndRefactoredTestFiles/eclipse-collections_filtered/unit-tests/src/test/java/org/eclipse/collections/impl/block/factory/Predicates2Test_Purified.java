package org.eclipse.collections.impl.block.factory;

import java.io.IOException;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.utility.ListIterate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Predicates2Test_Purified {

    private static final Predicates2<Object, Object> TRUE = Predicates2.alwaysTrue();

    private static final Predicates2<Object, Object> FALSE = Predicates2.alwaysFalse();

    private static final Object OBJECT = new Object();

    private MyRuntimeException throwMyException(Object one, Object two, Throwable exception) {
        return new MyRuntimeException(String.valueOf(one) + two, exception);
    }

    private static class MyRuntimeException extends RuntimeException {

        MyRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Test
    public void staticOr_1() {
        assertTrue(Predicates2.or(TRUE, FALSE).accept(OBJECT, OBJECT));
    }

    @Test
    public void staticOr_2() {
        assertFalse(Predicates2.or(FALSE, FALSE).accept(OBJECT, OBJECT));
    }

    @Test
    public void staticOr_3() {
        assertTrue(Predicates2.or(TRUE, TRUE).accept(OBJECT, OBJECT));
    }

    @Test
    public void staticOr_4() {
        assertNotNull(Predicates2.or(TRUE, TRUE).toString());
    }

    @Test
    public void instanceOr_1() {
        assertTrue(TRUE.or(FALSE).accept(OBJECT, OBJECT));
    }

    @Test
    public void instanceOr_2() {
        assertFalse(FALSE.or(FALSE).accept(OBJECT, OBJECT));
    }

    @Test
    public void instanceOr_3() {
        assertTrue(TRUE.or(TRUE).accept(OBJECT, OBJECT));
    }

    @Test
    public void instanceOr_4() {
        assertNotNull(TRUE.or(TRUE).toString());
    }

    @Test
    public void staticAnd_1() {
        assertTrue(Predicates2.and(TRUE, TRUE).accept(OBJECT, OBJECT));
    }

    @Test
    public void staticAnd_2() {
        assertFalse(Predicates2.and(TRUE, FALSE).accept(OBJECT, OBJECT));
    }

    @Test
    public void staticAnd_3() {
        assertFalse(Predicates2.and(FALSE, FALSE).accept(OBJECT, OBJECT));
    }

    @Test
    public void staticAnd_4() {
        assertNotNull(Predicates2.and(FALSE, FALSE).toString());
    }

    @Test
    public void instanceAnd_1() {
        assertTrue(TRUE.and(TRUE).accept(OBJECT, OBJECT));
    }

    @Test
    public void instanceAnd_2() {
        assertFalse(TRUE.and(FALSE).accept(OBJECT, OBJECT));
    }

    @Test
    public void instanceAnd_3() {
        assertFalse(FALSE.and(FALSE).accept(OBJECT, OBJECT));
    }

    @Test
    public void instanceAnd_4() {
        assertNotNull(FALSE.and(FALSE).toString());
    }

    @Test
    public void equal_1() {
        assertTrue(Predicates2.equal().accept(1, 1));
    }

    @Test
    public void equal_2() {
        assertFalse(Predicates2.equal().accept(2, 1));
    }

    @Test
    public void equal_3() {
        assertFalse(Predicates2.equal().accept(null, 1));
    }

    @Test
    public void equal_4() {
        assertNotNull(Predicates2.equal().toString());
    }

    @Test
    public void notEqual_1() {
        assertFalse(Predicates2.notEqual().accept(1, 1));
    }

    @Test
    public void notEqual_2() {
        assertTrue(Predicates2.notEqual().accept(2, 1));
    }

    @Test
    public void notEqual_3() {
        assertTrue(Predicates2.notEqual().accept(1, 2));
    }

    @Test
    public void notEqual_4() {
        assertTrue(Predicates2.notEqual().accept(null, 1));
    }

    @Test
    public void notEqual_5() {
        assertTrue(Predicates2.notEqual().accept(1, null));
    }

    @Test
    public void notEqual_6() {
        assertFalse(Predicates2.notEqual().accept(null, null));
    }

    @Test
    public void notEqual_7() {
        assertNotNull(Predicates2.notEqual().toString());
    }

    @Test
    public void not_1() {
        assertFalse(Predicates2.not(TRUE).accept(OBJECT, OBJECT));
    }

    @Test
    public void not_2() {
        assertTrue(Predicates2.not(FALSE).accept(OBJECT, OBJECT));
    }

    @Test
    public void not_3() {
        assertNotNull(Predicates2.not(FALSE).toString());
    }

    @Test
    public void testNull_1() {
        assertFalse(Predicates2.isNull().accept(OBJECT, null));
    }

    @Test
    public void testNull_2() {
        assertTrue(Predicates2.isNull().accept(null, null));
    }

    @Test
    public void testNull_3() {
        assertNotNull(Predicates2.isNull().toString());
    }

    @Test
    public void notNull_1() {
        assertTrue(Predicates2.notNull().accept(OBJECT, null));
    }

    @Test
    public void notNull_2() {
        assertFalse(Predicates2.notNull().accept(null, null));
    }

    @Test
    public void notNull_3() {
        assertNotNull(Predicates2.notNull().toString());
    }

    @Test
    public void sameAs_1() {
        assertTrue(Predicates2.sameAs().accept(OBJECT, OBJECT));
    }

    @Test
    public void sameAs_2() {
        assertFalse(Predicates2.sameAs().accept(OBJECT, new Object()));
    }

    @Test
    public void sameAs_3() {
        assertNotNull(Predicates2.sameAs().toString());
    }

    @Test
    public void notSameAs_1() {
        assertFalse(Predicates2.notSameAs().accept(OBJECT, OBJECT));
    }

    @Test
    public void notSameAs_2() {
        assertTrue(Predicates2.notSameAs().accept(OBJECT, new Object()));
    }

    @Test
    public void notSameAs_3() {
        assertNotNull(Predicates2.notSameAs().toString());
    }

    @Test
    public void instanceOf_1() {
        assertTrue(Predicates2.instanceOf().accept(1, Integer.class));
    }

    @Test
    public void instanceOf_2() {
        assertFalse(Predicates2.instanceOf().accept(1.0, Integer.class));
    }

    @Test
    public void instanceOf_3() {
        assertNotNull(Predicates2.instanceOf().toString());
    }

    @Test
    public void notInstanceOf_1() {
        assertFalse(Predicates2.notInstanceOf().accept(1, Integer.class));
    }

    @Test
    public void notInstanceOf_2() {
        assertTrue(Predicates2.notInstanceOf().accept(1.0, Integer.class));
    }

    @Test
    public void notInstanceOf_3() {
        assertNotNull(Predicates2.notInstanceOf().toString());
    }

    @Test
    public void attributeEqual_1_testMerged_1() {
        Integer one = 1;
        assertTrue(Predicates2.attributeEqual(Functions.getToString()).accept(one, "1"));
        assertFalse(Predicates2.attributeEqual(Functions.getToString()).accept(one, "2"));
    }

    @Test
    public void attributeEqual_3() {
        assertNotNull(Predicates2.attributeEqual(Functions.getToString()).toString());
    }

    @Test
    public void attributeNotEqual_1_testMerged_1() {
        Integer one = 1;
        assertFalse(Predicates2.attributeNotEqual(Functions.getToString()).accept(one, "1"));
        assertTrue(Predicates2.attributeNotEqual(Functions.getToString()).accept(one, "2"));
    }

    @Test
    public void attributeNotEqual_3() {
        assertNotNull(Predicates2.attributeNotEqual(Functions.getToString()).toString());
    }

    @Test
    public void attributeLessThan_1_testMerged_1() {
        Integer one = 1;
        assertFalse(Predicates2.attributeLessThan(Functions.getToString()).accept(one, "1"));
        assertTrue(Predicates2.attributeLessThan(Functions.getToString()).accept(one, "2"));
    }

    @Test
    public void attributeLessThan_3() {
        assertNotNull(Predicates2.attributeLessThan(Functions.getToString()).toString());
    }

    @Test
    public void attributeGreaterThan_1_testMerged_1() {
        Integer one = 1;
        assertTrue(Predicates2.attributeGreaterThan(Functions.getToString()).accept(one, "0"));
        assertFalse(Predicates2.attributeGreaterThan(Functions.getToString()).accept(one, "1"));
    }

    @Test
    public void attributeGreaterThan_3() {
        assertNotNull(Predicates2.attributeGreaterThan(Functions.getToString()).toString());
    }

    @Test
    public void attributeGreaterThanOrEqualTo_1_testMerged_1() {
        Integer one = 1;
        assertTrue(Predicates2.attributeGreaterThanOrEqualTo(Functions.getToString()).accept(one, "0"));
        assertTrue(Predicates2.attributeGreaterThanOrEqualTo(Functions.getToString()).accept(one, "1"));
        assertFalse(Predicates2.attributeGreaterThanOrEqualTo(Functions.getToString()).accept(one, "2"));
    }

    @Test
    public void attributeGreaterThanOrEqualTo_4() {
        assertNotNull(Predicates2.attributeGreaterThanOrEqualTo(Functions.getToString()).toString());
    }

    @Test
    public void attributeLessThanOrEqualTo_1() {
        assertFalse(Predicates2.attributeLessThanOrEqualTo(Functions.getToString()).accept(1, "0"));
    }

    @Test
    public void attributeLessThanOrEqualTo_2() {
        assertTrue(Predicates2.attributeLessThanOrEqualTo(Functions.getToString()).accept(1, "1"));
    }

    @Test
    public void attributeLessThanOrEqualTo_3() {
        assertTrue(Predicates2.attributeLessThanOrEqualTo(Functions.getToString()).accept(1, "2"));
    }

    @Test
    public void attributeLessThanOrEqualTo_4() {
        assertNotNull(Predicates2.attributeLessThanOrEqualTo(Functions.getToString()).toString());
    }

    @Test
    public void lessThanNumber_1() {
        assertTrue(Predicates2.<Integer>lessThan().accept(-1, 0));
    }

    @Test
    public void lessThanNumber_2() {
        assertTrue(Predicates2.<Double>lessThan().accept(-1.0, 0.0));
    }

    @Test
    public void lessThanNumber_3() {
        assertFalse(Predicates2.<Double>lessThan().accept(0.0, -1.0));
    }

    @Test
    public void lessThanNumber_4() {
        assertNotNull(Predicates2.<Integer>lessThan().toString());
    }

    @Test
    public void greaterThanNumber_1() {
        assertFalse(Predicates2.<Integer>greaterThan().accept(-1, 0));
    }

    @Test
    public void greaterThanNumber_2() {
        assertFalse(Predicates2.<Double>greaterThan().accept(-1.0, 0.0));
    }

    @Test
    public void greaterThanNumber_3() {
        assertTrue(Predicates2.<Double>greaterThan().accept(0.0, -1.0));
    }

    @Test
    public void greaterThanNumber_4() {
        assertNotNull(Predicates2.<Integer>greaterThan().toString());
    }

    @Test
    public void lessEqualThanNumber_1() {
        assertTrue(Predicates2.<Integer>lessThanOrEqualTo().accept(-1, 0));
    }

    @Test
    public void lessEqualThanNumber_2() {
        assertTrue(Predicates2.<Double>lessThanOrEqualTo().accept(-1.0, 0.0));
    }

    @Test
    public void lessEqualThanNumber_3() {
        assertTrue(Predicates2.<Double>lessThanOrEqualTo().accept(-1.0, -1.0));
    }

    @Test
    public void lessEqualThanNumber_4() {
        assertFalse(Predicates2.<Double>lessThanOrEqualTo().accept(0.0, -1.0));
    }

    @Test
    public void lessEqualThanNumber_5() {
        assertNotNull(Predicates2.<Integer>lessThanOrEqualTo().toString());
    }

    @Test
    public void greaterEqualNumber_1() {
        assertFalse(Predicates2.<Integer>greaterThanOrEqualTo().accept(-1, 0));
    }

    @Test
    public void greaterEqualNumber_2() {
        assertFalse(Predicates2.<Double>greaterThanOrEqualTo().accept(-1.0, 0.0));
    }

    @Test
    public void greaterEqualNumber_3() {
        assertTrue(Predicates2.<Double>greaterThanOrEqualTo().accept(-1.0, -1.0));
    }

    @Test
    public void greaterEqualNumber_4() {
        assertTrue(Predicates2.<Double>greaterThanOrEqualTo().accept(0.0, -1.0));
    }

    @Test
    public void greaterEqualNumber_5() {
        assertNotNull(Predicates2.<Integer>greaterThanOrEqualTo().toString());
    }
}
