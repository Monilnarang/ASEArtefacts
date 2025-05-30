package org.apache.commons.lang3.exception;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.test.NotVisibleExceptionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExceptionUtilsTest_Purified extends AbstractLangTest {

    private static final class ExceptionWithCause extends Exception {

        private static final long serialVersionUID = 1L;

        private Throwable cause;

        ExceptionWithCause(final String str, final Throwable cause) {
            super(str);
            setCause(cause);
        }

        ExceptionWithCause(final Throwable cause) {
            setCause(cause);
        }

        @Override
        public synchronized Throwable getCause() {
            return cause;
        }

        public void setCause(final Throwable cause) {
            this.cause = cause;
        }
    }

    private static final class ExceptionWithoutCause extends Exception {

        private static final long serialVersionUID = 1L;

        @SuppressWarnings("unused")
        public void getTargetException() {
        }
    }

    private static final class NestableException extends Exception {

        private static final long serialVersionUID = 1L;

        @SuppressWarnings("unused")
        NestableException() {
        }

        NestableException(final Throwable t) {
            super(t);
        }
    }

    public static class TestThrowable extends Throwable {

        private static final long serialVersionUID = 1L;
    }

    private static int redeclareCheckedException() {
        return throwsCheckedException();
    }

    private static int throwsCheckedException() {
        try {
            throw new IOException();
        } catch (final Exception e) {
            ExceptionUtils.asRuntimeException(e);
            return -1;
        }
    }

    private ExceptionWithCause cyclicCause;

    private Throwable jdkNoCause;

    private NestableException nested;

    private Throwable notVisibleException;

    private Throwable withCause;

    private Throwable withoutCause;

    private Throwable createExceptionWithCause() {
        try {
            try {
                throw new ExceptionWithCause(createExceptionWithoutCause());
            } catch (final Throwable t) {
                throw new ExceptionWithCause(t);
            }
        } catch (final Throwable t) {
            return t;
        }
    }

    private Throwable createExceptionWithoutCause() {
        try {
            throw new ExceptionWithoutCause();
        } catch (final Throwable t) {
            return t;
        }
    }

    @BeforeEach
    public void setUp() {
        withoutCause = createExceptionWithoutCause();
        nested = new NestableException(withoutCause);
        withCause = new ExceptionWithCause(nested);
        jdkNoCause = new NullPointerException();
        final ExceptionWithCause a = new ExceptionWithCause(null);
        final ExceptionWithCause b = new ExceptionWithCause(a);
        a.setCause(b);
        cyclicCause = new ExceptionWithCause(a);
        notVisibleException = NotVisibleExceptionFactory.createException(withoutCause);
    }

    @AfterEach
    public void tearDown() {
        withoutCause = null;
        nested = null;
        withCause = null;
        jdkNoCause = null;
        cyclicCause = null;
        notVisibleException = null;
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new ExceptionUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = ExceptionUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(ExceptionUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(ExceptionUtils.class.getModifiers()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetCause_Throwable_1() {
        assertSame(null, ExceptionUtils.getCause(null));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetCause_Throwable_2() {
        assertSame(null, ExceptionUtils.getCause(withoutCause));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetCause_Throwable_3() {
        assertSame(withoutCause, ExceptionUtils.getCause(nested));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetCause_Throwable_4() {
        assertSame(nested, ExceptionUtils.getCause(withCause));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetCause_Throwable_5() {
        assertSame(null, ExceptionUtils.getCause(jdkNoCause));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetCause_Throwable_6() {
        assertSame(cyclicCause.getCause(), ExceptionUtils.getCause(cyclicCause));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetCause_Throwable_7() {
        assertSame(cyclicCause.getCause().getCause(), ExceptionUtils.getCause(cyclicCause.getCause()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetCause_Throwable_8() {
        assertSame(cyclicCause.getCause(), ExceptionUtils.getCause(cyclicCause.getCause().getCause()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetCause_Throwable_9() {
        assertSame(withoutCause, ExceptionUtils.getCause(notVisibleException));
    }

    @Test
    public void testGetRootCause_Throwable_1() {
        assertSame(null, ExceptionUtils.getRootCause(null));
    }

    @Test
    public void testGetRootCause_Throwable_2() {
        assertSame(withoutCause, ExceptionUtils.getRootCause(withoutCause));
    }

    @Test
    public void testGetRootCause_Throwable_3() {
        assertSame(withoutCause, ExceptionUtils.getRootCause(nested));
    }

    @Test
    public void testGetRootCause_Throwable_4() {
        assertSame(withoutCause, ExceptionUtils.getRootCause(withCause));
    }

    @Test
    public void testGetRootCause_Throwable_5() {
        assertSame(jdkNoCause, ExceptionUtils.getRootCause(jdkNoCause));
    }

    @Test
    public void testGetRootCause_Throwable_6() {
        assertSame(cyclicCause.getCause().getCause(), ExceptionUtils.getRootCause(cyclicCause));
    }

    @Test
    public void testGetThrowableCount_Throwable_1() {
        assertEquals(0, ExceptionUtils.getThrowableCount(null));
    }

    @Test
    public void testGetThrowableCount_Throwable_2() {
        assertEquals(1, ExceptionUtils.getThrowableCount(withoutCause));
    }

    @Test
    public void testGetThrowableCount_Throwable_3() {
        assertEquals(2, ExceptionUtils.getThrowableCount(nested));
    }

    @Test
    public void testGetThrowableCount_Throwable_4() {
        assertEquals(3, ExceptionUtils.getThrowableCount(withCause));
    }

    @Test
    public void testGetThrowableCount_Throwable_5() {
        assertEquals(1, ExceptionUtils.getThrowableCount(jdkNoCause));
    }

    @Test
    public void testGetThrowableCount_Throwable_6() {
        assertEquals(3, ExceptionUtils.getThrowableCount(cyclicCause));
    }

    @Test
    public void testIndexOf_ThrowableClass_1() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(null, null));
    }

    @Test
    public void testIndexOf_ThrowableClass_2() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(null, NestableException.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_3() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withoutCause, null));
    }

    @Test
    public void testIndexOf_ThrowableClass_4() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withoutCause, ExceptionWithCause.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_5() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withoutCause, NestableException.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_6() {
        assertEquals(0, ExceptionUtils.indexOfThrowable(withoutCause, ExceptionWithoutCause.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_7() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(nested, null));
    }

    @Test
    public void testIndexOf_ThrowableClass_8() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(nested, ExceptionWithCause.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_9() {
        assertEquals(0, ExceptionUtils.indexOfThrowable(nested, NestableException.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_10() {
        assertEquals(1, ExceptionUtils.indexOfThrowable(nested, ExceptionWithoutCause.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_11() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withCause, null));
    }

    @Test
    public void testIndexOf_ThrowableClass_12() {
        assertEquals(0, ExceptionUtils.indexOfThrowable(withCause, ExceptionWithCause.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_13() {
        assertEquals(1, ExceptionUtils.indexOfThrowable(withCause, NestableException.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_14() {
        assertEquals(2, ExceptionUtils.indexOfThrowable(withCause, ExceptionWithoutCause.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_15() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withCause, Exception.class));
    }

    @Test
    public void testIndexOf_ThrowableClass_16() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withCause, Throwable.class));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_1() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(null, null, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_2() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(null, NestableException.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_3() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withoutCause, null));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_4() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withoutCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_5() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withoutCause, NestableException.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_6() {
        assertEquals(0, ExceptionUtils.indexOfThrowable(withoutCause, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_7() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(nested, null, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_8() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(nested, ExceptionWithCause.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_9() {
        assertEquals(0, ExceptionUtils.indexOfThrowable(nested, NestableException.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_10() {
        assertEquals(1, ExceptionUtils.indexOfThrowable(nested, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_11() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withCause, null));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_12() {
        assertEquals(0, ExceptionUtils.indexOfThrowable(withCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_13() {
        assertEquals(1, ExceptionUtils.indexOfThrowable(withCause, NestableException.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_14() {
        assertEquals(2, ExceptionUtils.indexOfThrowable(withCause, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_15() {
        assertEquals(0, ExceptionUtils.indexOfThrowable(withCause, ExceptionWithCause.class, -1));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_16() {
        assertEquals(0, ExceptionUtils.indexOfThrowable(withCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_17() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withCause, ExceptionWithCause.class, 1));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_18() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withCause, ExceptionWithCause.class, 9));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_19() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withCause, Exception.class, 0));
    }

    @Test
    public void testIndexOf_ThrowableClassInt_20() {
        assertEquals(-1, ExceptionUtils.indexOfThrowable(withCause, Throwable.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClass_1() {
        assertEquals(-1, ExceptionUtils.indexOfType(null, null));
    }

    @Test
    public void testIndexOfType_ThrowableClass_2() {
        assertEquals(-1, ExceptionUtils.indexOfType(null, NestableException.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_3() {
        assertEquals(-1, ExceptionUtils.indexOfType(withoutCause, null));
    }

    @Test
    public void testIndexOfType_ThrowableClass_4() {
        assertEquals(-1, ExceptionUtils.indexOfType(withoutCause, ExceptionWithCause.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_5() {
        assertEquals(-1, ExceptionUtils.indexOfType(withoutCause, NestableException.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_6() {
        assertEquals(0, ExceptionUtils.indexOfType(withoutCause, ExceptionWithoutCause.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_7() {
        assertEquals(-1, ExceptionUtils.indexOfType(nested, null));
    }

    @Test
    public void testIndexOfType_ThrowableClass_8() {
        assertEquals(-1, ExceptionUtils.indexOfType(nested, ExceptionWithCause.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_9() {
        assertEquals(0, ExceptionUtils.indexOfType(nested, NestableException.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_10() {
        assertEquals(1, ExceptionUtils.indexOfType(nested, ExceptionWithoutCause.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_11() {
        assertEquals(-1, ExceptionUtils.indexOfType(withCause, null));
    }

    @Test
    public void testIndexOfType_ThrowableClass_12() {
        assertEquals(0, ExceptionUtils.indexOfType(withCause, ExceptionWithCause.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_13() {
        assertEquals(1, ExceptionUtils.indexOfType(withCause, NestableException.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_14() {
        assertEquals(2, ExceptionUtils.indexOfType(withCause, ExceptionWithoutCause.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_15() {
        assertEquals(0, ExceptionUtils.indexOfType(withCause, Exception.class));
    }

    @Test
    public void testIndexOfType_ThrowableClass_16() {
        assertEquals(0, ExceptionUtils.indexOfType(withCause, Throwable.class));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_1() {
        assertEquals(-1, ExceptionUtils.indexOfType(null, null, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_2() {
        assertEquals(-1, ExceptionUtils.indexOfType(null, NestableException.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_3() {
        assertEquals(-1, ExceptionUtils.indexOfType(withoutCause, null));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_4() {
        assertEquals(-1, ExceptionUtils.indexOfType(withoutCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_5() {
        assertEquals(-1, ExceptionUtils.indexOfType(withoutCause, NestableException.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_6() {
        assertEquals(0, ExceptionUtils.indexOfType(withoutCause, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_7() {
        assertEquals(-1, ExceptionUtils.indexOfType(nested, null, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_8() {
        assertEquals(-1, ExceptionUtils.indexOfType(nested, ExceptionWithCause.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_9() {
        assertEquals(0, ExceptionUtils.indexOfType(nested, NestableException.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_10() {
        assertEquals(1, ExceptionUtils.indexOfType(nested, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_11() {
        assertEquals(-1, ExceptionUtils.indexOfType(withCause, null));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_12() {
        assertEquals(0, ExceptionUtils.indexOfType(withCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_13() {
        assertEquals(1, ExceptionUtils.indexOfType(withCause, NestableException.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_14() {
        assertEquals(2, ExceptionUtils.indexOfType(withCause, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_15() {
        assertEquals(0, ExceptionUtils.indexOfType(withCause, ExceptionWithCause.class, -1));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_16() {
        assertEquals(0, ExceptionUtils.indexOfType(withCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_17() {
        assertEquals(-1, ExceptionUtils.indexOfType(withCause, ExceptionWithCause.class, 1));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_18() {
        assertEquals(-1, ExceptionUtils.indexOfType(withCause, ExceptionWithCause.class, 9));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_19() {
        assertEquals(0, ExceptionUtils.indexOfType(withCause, Exception.class, 0));
    }

    @Test
    public void testIndexOfType_ThrowableClassInt_20() {
        assertEquals(0, ExceptionUtils.indexOfType(withCause, Throwable.class, 0));
    }

    @Test
    public void testStream_jdkNoCause_1() {
        assertEquals(1, ExceptionUtils.stream(jdkNoCause).count());
    }

    @Test
    public void testStream_jdkNoCause_2() {
        assertSame(jdkNoCause, ExceptionUtils.stream(jdkNoCause).toArray()[0]);
    }

    @Test
    public void testStream_nested_1() {
        assertEquals(2, ExceptionUtils.stream(nested).count());
    }

    @Test
    public void testStream_nested_2_testMerged_2() {
        final Object[] array = ExceptionUtils.stream(nested).toArray();
        assertSame(nested, array[0]);
        assertSame(withoutCause, array[1]);
    }

    @Test
    public void testThrowableOf_ThrowableClass_1() {
        assertNull(ExceptionUtils.throwableOfThrowable(null, null));
    }

    @Test
    public void testThrowableOf_ThrowableClass_2() {
        assertNull(ExceptionUtils.throwableOfThrowable(null, NestableException.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_3() {
        assertNull(ExceptionUtils.throwableOfThrowable(withoutCause, null));
    }

    @Test
    public void testThrowableOf_ThrowableClass_4() {
        assertNull(ExceptionUtils.throwableOfThrowable(withoutCause, ExceptionWithCause.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_5() {
        assertNull(ExceptionUtils.throwableOfThrowable(withoutCause, NestableException.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_6() {
        assertEquals(withoutCause, ExceptionUtils.throwableOfThrowable(withoutCause, ExceptionWithoutCause.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_7() {
        assertNull(ExceptionUtils.throwableOfThrowable(nested, null));
    }

    @Test
    public void testThrowableOf_ThrowableClass_8() {
        assertNull(ExceptionUtils.throwableOfThrowable(nested, ExceptionWithCause.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_9() {
        assertEquals(nested, ExceptionUtils.throwableOfThrowable(nested, NestableException.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_10() {
        assertEquals(nested.getCause(), ExceptionUtils.throwableOfThrowable(nested, ExceptionWithoutCause.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_11() {
        assertNull(ExceptionUtils.throwableOfThrowable(withCause, null));
    }

    @Test
    public void testThrowableOf_ThrowableClass_12() {
        assertEquals(withCause, ExceptionUtils.throwableOfThrowable(withCause, ExceptionWithCause.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_13() {
        assertEquals(withCause.getCause(), ExceptionUtils.throwableOfThrowable(withCause, NestableException.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_14() {
        assertEquals(withCause.getCause().getCause(), ExceptionUtils.throwableOfThrowable(withCause, ExceptionWithoutCause.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_15() {
        assertNull(ExceptionUtils.throwableOfThrowable(withCause, Exception.class));
    }

    @Test
    public void testThrowableOf_ThrowableClass_16() {
        assertNull(ExceptionUtils.throwableOfThrowable(withCause, Throwable.class));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_1() {
        assertNull(ExceptionUtils.throwableOfThrowable(null, null, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_2() {
        assertNull(ExceptionUtils.throwableOfThrowable(null, NestableException.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_3() {
        assertNull(ExceptionUtils.throwableOfThrowable(withoutCause, null));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_4() {
        assertNull(ExceptionUtils.throwableOfThrowable(withoutCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_5() {
        assertNull(ExceptionUtils.throwableOfThrowable(withoutCause, NestableException.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_6() {
        assertEquals(withoutCause, ExceptionUtils.throwableOfThrowable(withoutCause, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_7() {
        assertNull(ExceptionUtils.throwableOfThrowable(nested, null, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_8() {
        assertNull(ExceptionUtils.throwableOfThrowable(nested, ExceptionWithCause.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_9() {
        assertEquals(nested, ExceptionUtils.throwableOfThrowable(nested, NestableException.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_10() {
        assertEquals(nested.getCause(), ExceptionUtils.throwableOfThrowable(nested, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_11() {
        assertNull(ExceptionUtils.throwableOfThrowable(withCause, null));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_12() {
        assertEquals(withCause, ExceptionUtils.throwableOfThrowable(withCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_13() {
        assertEquals(withCause.getCause(), ExceptionUtils.throwableOfThrowable(withCause, NestableException.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_14() {
        assertEquals(withCause.getCause().getCause(), ExceptionUtils.throwableOfThrowable(withCause, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_15() {
        assertEquals(withCause, ExceptionUtils.throwableOfThrowable(withCause, ExceptionWithCause.class, -1));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_16() {
        assertEquals(withCause, ExceptionUtils.throwableOfThrowable(withCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_17() {
        assertNull(ExceptionUtils.throwableOfThrowable(withCause, ExceptionWithCause.class, 1));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_18() {
        assertNull(ExceptionUtils.throwableOfThrowable(withCause, ExceptionWithCause.class, 9));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_19() {
        assertNull(ExceptionUtils.throwableOfThrowable(withCause, Exception.class, 0));
    }

    @Test
    public void testThrowableOf_ThrowableClassInt_20() {
        assertNull(ExceptionUtils.throwableOfThrowable(withCause, Throwable.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_1() {
        assertNull(ExceptionUtils.throwableOfType(null, null));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_2() {
        assertNull(ExceptionUtils.throwableOfType(null, NestableException.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_3() {
        assertNull(ExceptionUtils.throwableOfType(withoutCause, null));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_4() {
        assertNull(ExceptionUtils.throwableOfType(withoutCause, ExceptionWithCause.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_5() {
        assertNull(ExceptionUtils.throwableOfType(withoutCause, NestableException.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_6() {
        assertEquals(withoutCause, ExceptionUtils.throwableOfType(withoutCause, ExceptionWithoutCause.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_7() {
        assertNull(ExceptionUtils.throwableOfType(nested, null));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_8() {
        assertNull(ExceptionUtils.throwableOfType(nested, ExceptionWithCause.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_9() {
        assertEquals(nested, ExceptionUtils.throwableOfType(nested, NestableException.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_10() {
        assertEquals(nested.getCause(), ExceptionUtils.throwableOfType(nested, ExceptionWithoutCause.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_11() {
        assertNull(ExceptionUtils.throwableOfType(withCause, null));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_12() {
        assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, ExceptionWithCause.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_13() {
        assertEquals(withCause.getCause(), ExceptionUtils.throwableOfType(withCause, NestableException.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_14() {
        assertEquals(withCause.getCause().getCause(), ExceptionUtils.throwableOfType(withCause, ExceptionWithoutCause.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_15() {
        assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, Exception.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClass_16() {
        assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, Throwable.class));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_1() {
        assertNull(ExceptionUtils.throwableOfType(null, null, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_2() {
        assertNull(ExceptionUtils.throwableOfType(null, NestableException.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_3() {
        assertNull(ExceptionUtils.throwableOfType(withoutCause, null));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_4() {
        assertNull(ExceptionUtils.throwableOfType(withoutCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_5() {
        assertNull(ExceptionUtils.throwableOfType(withoutCause, NestableException.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_6() {
        assertEquals(withoutCause, ExceptionUtils.throwableOfType(withoutCause, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_7() {
        assertNull(ExceptionUtils.throwableOfType(nested, null, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_8() {
        assertNull(ExceptionUtils.throwableOfType(nested, ExceptionWithCause.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_9() {
        assertEquals(nested, ExceptionUtils.throwableOfType(nested, NestableException.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_10() {
        assertEquals(nested.getCause(), ExceptionUtils.throwableOfType(nested, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_11() {
        assertNull(ExceptionUtils.throwableOfType(withCause, null));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_12() {
        assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_13() {
        assertEquals(withCause.getCause(), ExceptionUtils.throwableOfType(withCause, NestableException.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_14() {
        assertEquals(withCause.getCause().getCause(), ExceptionUtils.throwableOfType(withCause, ExceptionWithoutCause.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_15() {
        assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, ExceptionWithCause.class, -1));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_16() {
        assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, ExceptionWithCause.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_17() {
        assertNull(ExceptionUtils.throwableOfType(withCause, ExceptionWithCause.class, 1));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_18() {
        assertNull(ExceptionUtils.throwableOfType(withCause, ExceptionWithCause.class, 9));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_19() {
        assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, Exception.class, 0));
    }

    @Test
    public void testThrowableOfType_ThrowableClassInt_20() {
        assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, Throwable.class, 0));
    }
}
