package org.apache.commons.lang3.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FailableFunctionsTest_Purified extends AbstractLangTest {

    public static class CloseableObject {

        private boolean closed;

        public void close() {
            closed = true;
        }

        public boolean isClosed() {
            return closed;
        }

        public void reset() {
            closed = false;
        }

        public void run(final Throwable throwable) throws Throwable {
            if (throwable != null) {
                throw throwable;
            }
        }
    }

    public static class FailureOnOddInvocations {

        private static int invocations;

        static boolean failingBool() throws SomeException {
            throwOnOdd();
            return true;
        }

        static boolean testDouble(final double value) throws SomeException {
            throwOnOdd();
            return true;
        }

        static boolean testInt(final int value) throws SomeException {
            throwOnOdd();
            return true;
        }

        static boolean testLong(final long value) throws SomeException {
            throwOnOdd();
            return true;
        }

        private static void throwOnOdd() throws SomeException {
            final int i = ++invocations;
            if (i % 2 == 1) {
                throw new SomeException("Odd Invocation: " + i);
            }
        }

        FailureOnOddInvocations() throws SomeException {
            throwOnOdd();
        }

        boolean getAsBoolean() throws SomeException {
            throwOnOdd();
            return true;
        }
    }

    public static class SomeException extends Exception {

        private static final long serialVersionUID = -4965704778119283411L;

        private Throwable t;

        SomeException(final String message) {
            super(message);
        }

        public void setThrowable(final Throwable throwable) {
            t = throwable;
        }

        public void test() throws Throwable {
            if (t != null) {
                throw t;
            }
        }
    }

    public static class Testable<T, P> {

        private T acceptedObject;

        private P acceptedPrimitiveObject1;

        private P acceptedPrimitiveObject2;

        private Throwable throwable;

        Testable(final Throwable throwable) {
            this.throwable = throwable;
        }

        public T getAcceptedObject() {
            return acceptedObject;
        }

        public P getAcceptedPrimitiveObject1() {
            return acceptedPrimitiveObject1;
        }

        public P getAcceptedPrimitiveObject2() {
            return acceptedPrimitiveObject2;
        }

        public void setThrowable(final Throwable throwable) {
            this.throwable = throwable;
        }

        public void test() throws Throwable {
            test(throwable);
        }

        public Object test(final Object input1, final Object input2) throws Throwable {
            test(throwable);
            return acceptedObject;
        }

        public void test(final Throwable throwable) throws Throwable {
            if (throwable != null) {
                throw throwable;
            }
        }

        public boolean testAsBooleanPrimitive() throws Throwable {
            return testAsBooleanPrimitive(throwable);
        }

        public boolean testAsBooleanPrimitive(final Throwable throwable) throws Throwable {
            if (throwable != null) {
                throw throwable;
            }
            return false;
        }

        public double testAsDoublePrimitive() throws Throwable {
            return testAsDoublePrimitive(throwable);
        }

        public double testAsDoublePrimitive(final Throwable throwable) throws Throwable {
            if (throwable != null) {
                throw throwable;
            }
            return 0;
        }

        public Integer testAsInteger() throws Throwable {
            return testAsInteger(throwable);
        }

        public Integer testAsInteger(final Throwable throwable) throws Throwable {
            if (throwable != null) {
                throw throwable;
            }
            return 0;
        }

        public int testAsIntPrimitive() throws Throwable {
            return testAsIntPrimitive(throwable);
        }

        public int testAsIntPrimitive(final Throwable throwable) throws Throwable {
            if (throwable != null) {
                throw throwable;
            }
            return 0;
        }

        public long testAsLongPrimitive() throws Throwable {
            return testAsLongPrimitive(throwable);
        }

        public long testAsLongPrimitive(final Throwable throwable) throws Throwable {
            if (throwable != null) {
                throw throwable;
            }
            return 0;
        }

        public short testAsShortPrimitive() throws Throwable {
            return testAsShortPrimitive(throwable);
        }

        public short testAsShortPrimitive(final Throwable throwable) throws Throwable {
            if (throwable != null) {
                throw throwable;
            }
            return 0;
        }

        public void testDouble(final double i) throws Throwable {
            test(throwable);
            acceptedPrimitiveObject1 = (P) (Double) i;
        }

        public double testDoubleDouble(final double i, final double j) throws Throwable {
            test(throwable);
            acceptedPrimitiveObject1 = (P) (Double) i;
            acceptedPrimitiveObject2 = (P) (Double) j;
            return 3d;
        }

        public void testInt(final int i) throws Throwable {
            test(throwable);
            acceptedPrimitiveObject1 = (P) (Integer) i;
        }

        public void testLong(final long i) throws Throwable {
            test(throwable);
            acceptedPrimitiveObject1 = (P) (Long) i;
        }

        public void testObjDouble(final T object, final double i) throws Throwable {
            test(throwable);
            acceptedObject = object;
            acceptedPrimitiveObject1 = (P) (Double) i;
        }

        public void testObjInt(final T object, final int i) throws Throwable {
            test(throwable);
            acceptedObject = object;
            acceptedPrimitiveObject1 = (P) (Integer) i;
        }

        public void testObjLong(final T object, final long i) throws Throwable {
            test(throwable);
            acceptedObject = object;
            acceptedPrimitiveObject1 = (P) (Long) i;
        }
    }

    private static final OutOfMemoryError ERROR = new OutOfMemoryError();

    private static final IllegalStateException ILLEGAL_STATE_EXCEPTION = new IllegalStateException();

    private String throwingFunction(final String input) throws Exception {
        return input;
    }

    @Test
    public void testBiPredicateNegate_1() throws Throwable {
        assertFalse(FailableBiPredicate.TRUE.negate().test(null, null));
    }

    @Test
    public void testBiPredicateNegate_2() throws Throwable {
        assertFalse(FailableBiPredicate.truePredicate().negate().test(null, null));
    }

    @Test
    public void testBiPredicateNegate_3() throws Throwable {
        assertTrue(FailableBiPredicate.FALSE.negate().test(null, null));
    }

    @Test
    public void testBiPredicateNegate_4() throws Throwable {
        assertTrue(FailableBiPredicate.falsePredicate().negate().test(null, null));
    }

    @Test
    public void testDoublePredicateNegate_1() throws Throwable {
        assertFalse(FailableDoublePredicate.TRUE.negate().test(0d));
    }

    @Test
    public void testDoublePredicateNegate_2() throws Throwable {
        assertFalse(FailableDoublePredicate.truePredicate().negate().test(0d));
    }

    @Test
    public void testDoublePredicateNegate_3() throws Throwable {
        assertTrue(FailableDoublePredicate.FALSE.negate().test(0d));
    }

    @Test
    public void testDoublePredicateNegate_4() throws Throwable {
        assertTrue(FailableDoublePredicate.falsePredicate().negate().test(0d));
    }

    @Test
    public void testIntPredicateNegate_1() throws Throwable {
        assertFalse(FailableIntPredicate.TRUE.negate().test(0));
    }

    @Test
    public void testIntPredicateNegate_2() throws Throwable {
        assertFalse(FailableIntPredicate.truePredicate().negate().test(0));
    }

    @Test
    public void testIntPredicateNegate_3() throws Throwable {
        assertTrue(FailableIntPredicate.FALSE.negate().test(0));
    }

    @Test
    public void testIntPredicateNegate_4() throws Throwable {
        assertTrue(FailableIntPredicate.falsePredicate().negate().test(0));
    }

    @Test
    public void testLongPredicateNegate_1() throws Throwable {
        assertFalse(FailableLongPredicate.TRUE.negate().test(0L));
    }

    @Test
    public void testLongPredicateNegate_2() throws Throwable {
        assertFalse(FailableLongPredicate.truePredicate().negate().test(0L));
    }

    @Test
    public void testLongPredicateNegate_3() throws Throwable {
        assertTrue(FailableLongPredicate.FALSE.negate().test(0L));
    }

    @Test
    public void testLongPredicateNegate_4() throws Throwable {
        assertTrue(FailableLongPredicate.falsePredicate().negate().test(0L));
    }

    @Test
    public void testPredicateNegate_1() throws Throwable {
        assertFalse(FailablePredicate.TRUE.negate().test(null));
    }

    @Test
    public void testPredicateNegate_2() throws Throwable {
        assertFalse(FailablePredicate.truePredicate().negate().test(null));
    }

    @Test
    public void testPredicateNegate_3() throws Throwable {
        assertTrue(FailablePredicate.FALSE.negate().test(null));
    }

    @Test
    public void testPredicateNegate_4() throws Throwable {
        assertTrue(FailablePredicate.falsePredicate().negate().test(null));
    }
}
