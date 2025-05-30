package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnnotationUtilsTest_Purified extends AbstractLangTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface NestAnnotation {

        boolean booleanValue();

        boolean[] booleanValues();

        byte byteValue();

        byte[] byteValues();

        char charValue();

        char[] charValues();

        double doubleValue();

        double[] doubleValues();

        float floatValue();

        float[] floatValues();

        int intValue();

        int[] intValues();

        long longValue();

        long[] longValues();

        short shortValue();

        short[] shortValues();

        Stooge stooge();

        Stooge[] stooges();

        String string();

        String[] strings();

        Class<?> type();

        Class<?>[] types();
    }

    public enum Stooge {

        MOE, LARRY, CURLY, JOE, SHEMP
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {

        boolean booleanValue();

        boolean[] booleanValues();

        byte byteValue();

        byte[] byteValues();

        char charValue();

        char[] charValues();

        double doubleValue();

        double[] doubleValues();

        float floatValue();

        float[] floatValues();

        int intValue();

        int[] intValues();

        long longValue();

        long[] longValues();

        NestAnnotation nest();

        NestAnnotation[] nests();

        short shortValue();

        short[] shortValues();

        Stooge stooge();

        Stooge[] stooges();

        String string();

        String[] strings();

        Class<?> type();

        Class<?>[] types();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD })
    public @interface TestMethodAnnotation {

        final class None extends Throwable {

            private static final long serialVersionUID = 1L;
        }

        Class<? extends Throwable> expected() default None.class;

        long timeout() default 0L;
    }

    @TestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, nest = @NestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.CURLY, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.SHEMP }, string = "", strings = { "" }, type = Object.class, types = { Object.class }), nests = { @NestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.CURLY, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.SHEMP }, string = "", strings = { "" }, type = Object[].class, types = { Object[].class }) }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.SHEMP, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.CURLY }, string = "", strings = { "" }, type = Object.class, types = { Object.class })
    public Object dummy1;

    @TestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, nest = @NestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.CURLY, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.SHEMP }, string = "", strings = { "" }, type = Object.class, types = { Object.class }), nests = { @NestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.CURLY, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.SHEMP }, string = "", strings = { "" }, type = Object[].class, types = { Object[].class }) }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.SHEMP, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.CURLY }, string = "", strings = { "" }, type = Object.class, types = { Object.class })
    public Object dummy2;

    @TestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, nest = @NestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.CURLY, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.SHEMP }, string = "", strings = { "" }, type = Object.class, types = { Object.class }), nests = { @NestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.CURLY, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.SHEMP }, string = "", strings = { "" }, type = Object[].class, types = { Object[].class }), @NestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.CURLY, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.SHEMP }, string = "", strings = { "" }, type = Object[].class, types = { Object[].class }) }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.SHEMP, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.CURLY }, string = "", strings = { "" }, type = Object.class, types = { Object.class })
    public Object dummy3;

    @NestAnnotation(booleanValue = false, booleanValues = { false }, byteValue = 0, byteValues = { 0 }, charValue = 0, charValues = { 0 }, doubleValue = 0, doubleValues = { 0 }, floatValue = 0, floatValues = { 0 }, intValue = 0, intValues = { 0 }, longValue = 0, longValues = { 0 }, shortValue = 0, shortValues = { 0 }, stooge = Stooge.CURLY, stooges = { Stooge.MOE, Stooge.LARRY, Stooge.SHEMP }, string = "", strings = { "" }, type = Object[].class, types = { Object[].class })
    public Object dummy4;

    private Field field1;

    private Field field2;

    private Field field3;

    private Field field4;

    @BeforeEach
    public void setup() throws Exception {
        field1 = getClass().getDeclaredField("dummy1");
        field2 = getClass().getDeclaredField("dummy2");
        field3 = getClass().getDeclaredField("dummy3");
        field4 = getClass().getDeclaredField("dummy4");
    }

    @Test
    public void testAnnotationsOfDifferingTypes_1() {
        assertFalse(AnnotationUtils.equals(field1.getAnnotation(TestAnnotation.class), field4.getAnnotation(NestAnnotation.class)));
    }

    @Test
    public void testAnnotationsOfDifferingTypes_2() {
        assertFalse(AnnotationUtils.equals(field4.getAnnotation(NestAnnotation.class), field1.getAnnotation(TestAnnotation.class)));
    }

    @Test
    public void testEquivalence_1() {
        assertTrue(AnnotationUtils.equals(field1.getAnnotation(TestAnnotation.class), field2.getAnnotation(TestAnnotation.class)));
    }

    @Test
    public void testEquivalence_2() {
        assertTrue(AnnotationUtils.equals(field2.getAnnotation(TestAnnotation.class), field1.getAnnotation(TestAnnotation.class)));
    }

    @Test
    public void testNonEquivalentAnnotationsOfSameType_1() {
        assertFalse(AnnotationUtils.equals(field1.getAnnotation(TestAnnotation.class), field3.getAnnotation(TestAnnotation.class)));
    }

    @Test
    public void testNonEquivalentAnnotationsOfSameType_2() {
        assertFalse(AnnotationUtils.equals(field3.getAnnotation(TestAnnotation.class), field1.getAnnotation(TestAnnotation.class)));
    }

    @Test
    public void testOneArgNull_1() {
        assertFalse(AnnotationUtils.equals(field1.getAnnotation(TestAnnotation.class), null));
    }

    @Test
    public void testOneArgNull_2() {
        assertFalse(AnnotationUtils.equals(null, field1.getAnnotation(TestAnnotation.class)));
    }
}
