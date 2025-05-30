package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang3.ClassUtils.Interfaces;
import org.apache.commons.lang3.reflect.testbed.GenericConsumer;
import org.apache.commons.lang3.reflect.testbed.GenericParent;
import org.apache.commons.lang3.reflect.testbed.StringParameterizedChild;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class ClassUtilsTest_Purified extends AbstractLangTest {

    private static class CX implements IB, IA, IE {
    }

    @SuppressWarnings("unused")
    private static final class CY extends CX implements IB, IC {
    }

    private interface IA {
    }

    private interface IB {
    }

    private interface IC extends ID, IE {
    }

    private interface ID {
    }

    private interface IE extends IF {
    }

    private interface IF {
    }

    private static final class Inner {

        private static final class DeeplyNested {
        }
    }

    private static final String OBJECT_CANONICAL_NAME = "java.lang.Object";

    private void assertGetClassReturnsClass(final Class<?> c) throws Exception {
        assertEquals(c, ClassUtils.getClass(c.getName()));
    }

    private void assertGetClassThrowsClassNotFound(final String className) {
        assertGetClassThrowsException(className, ClassNotFoundException.class);
    }

    private void assertGetClassThrowsException(final String className, final Class<? extends Exception> exceptionType) {
        assertThrows(exceptionType, () -> ClassUtils.getClass(className), "ClassUtils.getClass() should fail with an exception of type " + exceptionType.getName() + " when given class name \"" + className + "\".");
    }

    private void assertGetClassThrowsNullPointerException(final String className) {
        assertGetClassThrowsException(className, NullPointerException.class);
    }

    @Test
    public void test_getAbbreviatedName_Class_1() {
        assertEquals("", ClassUtils.getAbbreviatedName((Class<?>) null, 1));
    }

    @Test
    public void test_getAbbreviatedName_Class_2() {
        assertEquals("j.l.String", ClassUtils.getAbbreviatedName(String.class, 1));
    }

    @Test
    public void test_getAbbreviatedName_Class_3() {
        assertEquals("j.l.String", ClassUtils.getAbbreviatedName(String.class, 5));
    }

    @Test
    public void test_getAbbreviatedName_Class_4() {
        assertEquals("o.a.c.l.ClassUtils", ClassUtils.getAbbreviatedName(ClassUtils.class, 18));
    }

    @Test
    public void test_getAbbreviatedName_Class_5() {
        assertEquals("j.lang.String", ClassUtils.getAbbreviatedName(String.class, 13));
    }

    @Test
    public void test_getAbbreviatedName_Class_6() {
        assertEquals("j.lang.String", ClassUtils.getAbbreviatedName(String.class, 15));
    }

    @Test
    public void test_getAbbreviatedName_Class_7() {
        assertEquals("java.lang.String", ClassUtils.getAbbreviatedName(String.class, 20));
    }

    @Test
    public void test_getAbbreviatedName_String_1() {
        assertEquals("", ClassUtils.getAbbreviatedName((String) null, 1));
    }

    @Test
    public void test_getAbbreviatedName_String_2() {
        assertEquals("", ClassUtils.getAbbreviatedName("", 1));
    }

    @Test
    public void test_getAbbreviatedName_String_3() {
        assertEquals("WithoutPackage", ClassUtils.getAbbreviatedName("WithoutPackage", 1));
    }

    @Test
    public void test_getAbbreviatedName_String_4() {
        assertEquals("j.l.String", ClassUtils.getAbbreviatedName("java.lang.String", 1));
    }

    @Test
    public void test_getAbbreviatedName_String_5() {
        assertEquals("o.a.c.l.ClassUtils", ClassUtils.getAbbreviatedName("org.apache.commons.lang3.ClassUtils", 18));
    }

    @Test
    public void test_getAbbreviatedName_String_6() {
        assertEquals("org.apache.commons.lang3.ClassUtils", ClassUtils.getAbbreviatedName("org.apache.commons.lang3.ClassUtils", "org.apache.commons.lang3.ClassUtils".length()));
    }

    @Test
    public void test_getAbbreviatedName_String_7() {
        assertEquals("o.a.c.l.ClassUtils", ClassUtils.getAbbreviatedName("o.a.c.l.ClassUtils", 18));
    }

    @Test
    public void test_getAbbreviatedName_String_8() {
        assertEquals("o..c.l.ClassUtils", ClassUtils.getAbbreviatedName("o..c.l.ClassUtils", 18));
    }

    @Test
    public void test_getAbbreviatedName_String_9() {
        assertEquals(".", ClassUtils.getAbbreviatedName(".", 18));
    }

    @Test
    public void test_getAbbreviatedName_String_10() {
        assertEquals(".", ClassUtils.getAbbreviatedName(".", 1));
    }

    @Test
    public void test_getAbbreviatedName_String_11() {
        assertEquals("..", ClassUtils.getAbbreviatedName("..", 1));
    }

    @Test
    public void test_getAbbreviatedName_String_12() {
        assertEquals("...", ClassUtils.getAbbreviatedName("...", 2));
    }

    @Test
    public void test_getAbbreviatedName_String_13() {
        assertEquals("...", ClassUtils.getAbbreviatedName("...", 3));
    }

    @Test
    public void test_getAbbreviatedName_String_14() {
        assertEquals("java.lang.String", ClassUtils.getAbbreviatedName("java.lang.String", Integer.MAX_VALUE));
    }

    @Test
    public void test_getAbbreviatedName_String_15() {
        assertEquals("j.lang.String", ClassUtils.getAbbreviatedName("java.lang.String", "j.lang.String".length()));
    }

    @Test
    public void test_getAbbreviatedName_String_16() {
        assertEquals("j.l.String", ClassUtils.getAbbreviatedName("java.lang.String", "j.lang.String".length() - 1));
    }

    @Test
    public void test_getAbbreviatedName_String_17() {
        assertEquals("j.l.String", ClassUtils.getAbbreviatedName("java.lang.String", "j.l.String".length()));
    }

    @Test
    public void test_getAbbreviatedName_String_18() {
        assertEquals("j.l.String", ClassUtils.getAbbreviatedName("java.lang.String", "j.l.String".length() - 1));
    }

    @Test
    public void test_getCanonicalName_Class_1() {
        assertEquals("org.apache.commons.lang3.ClassUtils", ClassUtils.getCanonicalName(ClassUtils.class));
    }

    @Test
    public void test_getCanonicalName_Class_2() {
        assertEquals("java.util.Map.Entry", ClassUtils.getCanonicalName(Map.Entry.class));
    }

    @Test
    public void test_getCanonicalName_Class_3() {
        assertEquals("", ClassUtils.getCanonicalName((Class<?>) null));
    }

    @Test
    public void test_getCanonicalName_Class_4() {
        assertEquals("java.lang.String[]", ClassUtils.getCanonicalName(String[].class));
    }

    @Test
    public void test_getCanonicalName_Class_5() {
        assertEquals("java.util.Map.Entry[]", ClassUtils.getCanonicalName(Map.Entry[].class));
    }

    @Test
    public void test_getCanonicalName_Class_6() {
        assertEquals("boolean", ClassUtils.getCanonicalName(boolean.class));
    }

    @Test
    public void test_getCanonicalName_Class_7() {
        assertEquals("byte", ClassUtils.getCanonicalName(byte.class));
    }

    @Test
    public void test_getCanonicalName_Class_8() {
        assertEquals("char", ClassUtils.getCanonicalName(char.class));
    }

    @Test
    public void test_getCanonicalName_Class_9() {
        assertEquals("short", ClassUtils.getCanonicalName(short.class));
    }

    @Test
    public void test_getCanonicalName_Class_10() {
        assertEquals("int", ClassUtils.getCanonicalName(int.class));
    }

    @Test
    public void test_getCanonicalName_Class_11() {
        assertEquals("long", ClassUtils.getCanonicalName(long.class));
    }

    @Test
    public void test_getCanonicalName_Class_12() {
        assertEquals("float", ClassUtils.getCanonicalName(float.class));
    }

    @Test
    public void test_getCanonicalName_Class_13() {
        assertEquals("double", ClassUtils.getCanonicalName(double.class));
    }

    @Test
    public void test_getCanonicalName_Class_14() {
        assertEquals("boolean[]", ClassUtils.getCanonicalName(boolean[].class));
    }

    @Test
    public void test_getCanonicalName_Class_15() {
        assertEquals("byte[]", ClassUtils.getCanonicalName(byte[].class));
    }

    @Test
    public void test_getCanonicalName_Class_16() {
        assertEquals("char[]", ClassUtils.getCanonicalName(char[].class));
    }

    @Test
    public void test_getCanonicalName_Class_17() {
        assertEquals("short[]", ClassUtils.getCanonicalName(short[].class));
    }

    @Test
    public void test_getCanonicalName_Class_18() {
        assertEquals("int[]", ClassUtils.getCanonicalName(int[].class));
    }

    @Test
    public void test_getCanonicalName_Class_19() {
        assertEquals("long[]", ClassUtils.getCanonicalName(long[].class));
    }

    @Test
    public void test_getCanonicalName_Class_20() {
        assertEquals("float[]", ClassUtils.getCanonicalName(float[].class));
    }

    @Test
    public void test_getCanonicalName_Class_21() {
        assertEquals("double[]", ClassUtils.getCanonicalName(double[].class));
    }

    @Test
    public void test_getCanonicalName_Class_22() {
        assertEquals("java.lang.String[][]", ClassUtils.getCanonicalName(String[][].class));
    }

    @Test
    public void test_getCanonicalName_Class_23() {
        assertEquals("java.lang.String[][][]", ClassUtils.getCanonicalName(String[][][].class));
    }

    @Test
    public void test_getCanonicalName_Class_24() {
        assertEquals("java.lang.String[][][][]", ClassUtils.getCanonicalName(String[][][][].class));
    }

    @Test
    public void test_getCanonicalName_Class_25_testMerged_25() {
        assertEquals(StringUtils.EMPTY, ClassUtils.getCanonicalName(Named.class));
        assertEquals("org.apache.commons.lang3.ClassUtilsTest.Inner", ClassUtils.getCanonicalName(Inner.class));
    }

    @Test
    public void test_getCanonicalName_Class_String_1() {
        assertEquals("org.apache.commons.lang3.ClassUtils", ClassUtils.getCanonicalName(ClassUtils.class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_2() {
        assertEquals("java.util.Map.Entry", ClassUtils.getCanonicalName(Map.Entry.class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_3() {
        assertEquals("X", ClassUtils.getCanonicalName((Class<?>) null, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_4() {
        assertEquals("java.lang.String[]", ClassUtils.getCanonicalName(String[].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_5() {
        assertEquals("java.util.Map.Entry[]", ClassUtils.getCanonicalName(Map.Entry[].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_6() {
        assertEquals("boolean", ClassUtils.getCanonicalName(boolean.class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_7() {
        assertEquals("byte", ClassUtils.getCanonicalName(byte.class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_8() {
        assertEquals("char", ClassUtils.getCanonicalName(char.class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_9() {
        assertEquals("short", ClassUtils.getCanonicalName(short.class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_10() {
        assertEquals("int", ClassUtils.getCanonicalName(int.class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_11() {
        assertEquals("long", ClassUtils.getCanonicalName(long.class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_12() {
        assertEquals("float", ClassUtils.getCanonicalName(float.class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_13() {
        assertEquals("double", ClassUtils.getCanonicalName(double.class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_14() {
        assertEquals("boolean[]", ClassUtils.getCanonicalName(boolean[].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_15() {
        assertEquals("byte[]", ClassUtils.getCanonicalName(byte[].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_16() {
        assertEquals("char[]", ClassUtils.getCanonicalName(char[].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_17() {
        assertEquals("short[]", ClassUtils.getCanonicalName(short[].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_18() {
        assertEquals("int[]", ClassUtils.getCanonicalName(int[].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_19() {
        assertEquals("long[]", ClassUtils.getCanonicalName(long[].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_20() {
        assertEquals("float[]", ClassUtils.getCanonicalName(float[].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_21() {
        assertEquals("double[]", ClassUtils.getCanonicalName(double[].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_22() {
        assertEquals("java.lang.String[][]", ClassUtils.getCanonicalName(String[][].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_23() {
        assertEquals("java.lang.String[][][]", ClassUtils.getCanonicalName(String[][][].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_24() {
        assertEquals("java.lang.String[][][][]", ClassUtils.getCanonicalName(String[][][][].class, "X"));
    }

    @Test
    public void test_getCanonicalName_Class_String_25_testMerged_25() {
        assertEquals("X", ClassUtils.getCanonicalName(Named.class, "X"));
        assertEquals("org.apache.commons.lang3.ClassUtilsTest.Inner", ClassUtils.getCanonicalName(Inner.class, "X"));
        assertEquals("X", ClassUtils.getCanonicalName((Object) null, "X"));
        assertEquals(OBJECT_CANONICAL_NAME, ClassUtils.getCanonicalName(new Object()));
    }

    @Test
    public void test_getName_Class_1() {
        assertEquals("org.apache.commons.lang3.ClassUtils", ClassUtils.getName(ClassUtils.class));
    }

    @Test
    public void test_getName_Class_2() {
        assertEquals("java.util.Map$Entry", ClassUtils.getName(Map.Entry.class));
    }

    @Test
    public void test_getName_Class_3() {
        assertEquals("", ClassUtils.getName((Class<?>) null));
    }

    @Test
    public void test_getName_Class_4() {
        assertEquals("[Ljava.lang.String;", ClassUtils.getName(String[].class));
    }

    @Test
    public void test_getName_Class_5() {
        assertEquals("[Ljava.util.Map$Entry;", ClassUtils.getName(Map.Entry[].class));
    }

    @Test
    public void test_getName_Class_6() {
        assertEquals("boolean", ClassUtils.getName(boolean.class));
    }

    @Test
    public void test_getName_Class_7() {
        assertEquals("byte", ClassUtils.getName(byte.class));
    }

    @Test
    public void test_getName_Class_8() {
        assertEquals("char", ClassUtils.getName(char.class));
    }

    @Test
    public void test_getName_Class_9() {
        assertEquals("short", ClassUtils.getName(short.class));
    }

    @Test
    public void test_getName_Class_10() {
        assertEquals("int", ClassUtils.getName(int.class));
    }

    @Test
    public void test_getName_Class_11() {
        assertEquals("long", ClassUtils.getName(long.class));
    }

    @Test
    public void test_getName_Class_12() {
        assertEquals("float", ClassUtils.getName(float.class));
    }

    @Test
    public void test_getName_Class_13() {
        assertEquals("double", ClassUtils.getName(double.class));
    }

    @Test
    public void test_getName_Class_14() {
        assertEquals("[Z", ClassUtils.getName(boolean[].class));
    }

    @Test
    public void test_getName_Class_15() {
        assertEquals("[B", ClassUtils.getName(byte[].class));
    }

    @Test
    public void test_getName_Class_16() {
        assertEquals("[C", ClassUtils.getName(char[].class));
    }

    @Test
    public void test_getName_Class_17() {
        assertEquals("[S", ClassUtils.getName(short[].class));
    }

    @Test
    public void test_getName_Class_18() {
        assertEquals("[I", ClassUtils.getName(int[].class));
    }

    @Test
    public void test_getName_Class_19() {
        assertEquals("[J", ClassUtils.getName(long[].class));
    }

    @Test
    public void test_getName_Class_20() {
        assertEquals("[F", ClassUtils.getName(float[].class));
    }

    @Test
    public void test_getName_Class_21() {
        assertEquals("[D", ClassUtils.getName(double[].class));
    }

    @Test
    public void test_getName_Class_22() {
        assertEquals("[[Ljava.lang.String;", ClassUtils.getName(String[][].class));
    }

    @Test
    public void test_getName_Class_23() {
        assertEquals("[[[Ljava.lang.String;", ClassUtils.getName(String[][][].class));
    }

    @Test
    public void test_getName_Class_24() {
        assertEquals("[[[[Ljava.lang.String;", ClassUtils.getName(String[][][][].class));
    }

    @Test
    public void test_getName_Class_25_testMerged_25() {
        assertEquals("org.apache.commons.lang3.ClassUtilsTest$3Named", ClassUtils.getName(Named.class));
        assertEquals("org.apache.commons.lang3.ClassUtilsTest$Inner", ClassUtils.getName(Inner.class));
        assertEquals(OBJECT_CANONICAL_NAME, ClassUtils.getName(new Object()));
    }

    @Test
    public void test_getName_Object_1() {
        assertEquals("org.apache.commons.lang3.ClassUtils", ClassUtils.getName(new ClassUtils(), "<null>"));
    }

    @Test
    public void test_getName_Object_2() {
        assertEquals("org.apache.commons.lang3.ClassUtilsTest$Inner", ClassUtils.getName(new Inner(), "<null>"));
    }

    @Test
    public void test_getName_Object_3() {
        assertEquals("java.lang.String", ClassUtils.getName("hello", "<null>"));
    }

    @Test
    public void test_getName_Object_4() {
        assertEquals("<null>", ClassUtils.getName(null, "<null>"));
    }

    @Test
    public void test_getName_Object_5_testMerged_5() {
        assertEquals("org.apache.commons.lang3.ClassUtilsTest$4Named", ClassUtils.getName(new Named(), "<null>"));
    }

    @Test
    public void test_getName_Object_7() {
        assertEquals("org.apache.commons.lang3.ClassUtilsTest$Inner", ClassUtils.getName(new Inner(), "<null>"));
    }

    @Test
    public void test_getPackageCanonicalName_Class_1() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(ClassUtils.class));
    }

    @Test
    public void test_getPackageCanonicalName_Class_2() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(ClassUtils[].class));
    }

    @Test
    public void test_getPackageCanonicalName_Class_3() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(ClassUtils[][].class));
    }

    @Test
    public void test_getPackageCanonicalName_Class_4() {
        assertEquals("", ClassUtils.getPackageCanonicalName(int[].class));
    }

    @Test
    public void test_getPackageCanonicalName_Class_5() {
        assertEquals("", ClassUtils.getPackageCanonicalName(int[][].class));
    }

    @Test
    public void test_getPackageCanonicalName_Class_6_testMerged_6() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(Named.class));
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(Inner.class));
        assertEquals(StringUtils.EMPTY, ClassUtils.getPackageCanonicalName((Class<?>) null));
    }

    @Test
    public void test_getPackageCanonicalName_String_1() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtils"));
    }

    @Test
    public void test_getPackageCanonicalName_String_2() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName("[Lorg.apache.commons.lang3.ClassUtils;"));
    }

    @Test
    public void test_getPackageCanonicalName_String_3() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName("[[Lorg.apache.commons.lang3.ClassUtils;"));
    }

    @Test
    public void test_getPackageCanonicalName_String_4() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtils[]"));
    }

    @Test
    public void test_getPackageCanonicalName_String_5() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtils[][]"));
    }

    @Test
    public void test_getPackageCanonicalName_String_6() {
        assertEquals("", ClassUtils.getPackageCanonicalName("[I"));
    }

    @Test
    public void test_getPackageCanonicalName_String_7() {
        assertEquals("", ClassUtils.getPackageCanonicalName("[[I"));
    }

    @Test
    public void test_getPackageCanonicalName_String_8() {
        assertEquals("", ClassUtils.getPackageCanonicalName("int[]"));
    }

    @Test
    public void test_getPackageCanonicalName_String_9() {
        assertEquals("", ClassUtils.getPackageCanonicalName("int[][]"));
    }

    @Test
    public void test_getPackageCanonicalName_String_10() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtilsTest$6"));
    }

    @Test
    public void test_getPackageCanonicalName_String_11() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtilsTest$5Named"));
    }

    @Test
    public void test_getPackageCanonicalName_String_12() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtilsTest$Inner"));
    }

    @Test
    public void test_getPackageName_Class_1() {
        assertEquals("java.lang", ClassUtils.getPackageName(String.class));
    }

    @Test
    public void test_getPackageName_Class_2() {
        assertEquals("java.util", ClassUtils.getPackageName(Map.Entry.class));
    }

    @Test
    public void test_getPackageName_Class_3() {
        assertEquals("", ClassUtils.getPackageName((Class<?>) null));
    }

    @Test
    public void test_getPackageName_Class_4() {
        assertEquals("java.lang", ClassUtils.getPackageName(String[].class));
    }

    @Test
    public void test_getPackageName_Class_5() {
        assertEquals("", ClassUtils.getPackageName(boolean[].class));
    }

    @Test
    public void test_getPackageName_Class_6() {
        assertEquals("", ClassUtils.getPackageName(byte[].class));
    }

    @Test
    public void test_getPackageName_Class_7() {
        assertEquals("", ClassUtils.getPackageName(char[].class));
    }

    @Test
    public void test_getPackageName_Class_8() {
        assertEquals("", ClassUtils.getPackageName(short[].class));
    }

    @Test
    public void test_getPackageName_Class_9() {
        assertEquals("", ClassUtils.getPackageName(int[].class));
    }

    @Test
    public void test_getPackageName_Class_10() {
        assertEquals("", ClassUtils.getPackageName(long[].class));
    }

    @Test
    public void test_getPackageName_Class_11() {
        assertEquals("", ClassUtils.getPackageName(float[].class));
    }

    @Test
    public void test_getPackageName_Class_12() {
        assertEquals("", ClassUtils.getPackageName(double[].class));
    }

    @Test
    public void test_getPackageName_Class_13() {
        assertEquals("java.lang", ClassUtils.getPackageName(String[][].class));
    }

    @Test
    public void test_getPackageName_Class_14() {
        assertEquals("java.lang", ClassUtils.getPackageName(String[][][].class));
    }

    @Test
    public void test_getPackageName_Class_15() {
        assertEquals("java.lang", ClassUtils.getPackageName(String[][][][].class));
    }

    @Test
    public void test_getPackageName_Class_16_testMerged_16() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName(Named.class));
    }

    @Test
    public void test_getPackageName_Object_1() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName(new ClassUtils(), "<null>"));
    }

    @Test
    public void test_getPackageName_Object_2() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName(new Inner(), "<null>"));
    }

    @Test
    public void test_getPackageName_Object_3() {
        assertEquals("<null>", ClassUtils.getPackageName(null, "<null>"));
    }

    @Test
    public void test_getPackageName_String_1() {
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName(ClassUtils.class.getName()));
    }

    @Test
    public void test_getPackageName_String_2() {
        assertEquals("java.util", ClassUtils.getPackageName(Map.Entry.class.getName()));
    }

    @Test
    public void test_getPackageName_String_3() {
        assertEquals("", ClassUtils.getPackageName((String) null));
    }

    @Test
    public void test_getPackageName_String_4() {
        assertEquals("", ClassUtils.getPackageName(""));
    }

    @Test
    public void test_getShortCanonicalName_Class_1() {
        assertEquals("ClassUtils", ClassUtils.getShortCanonicalName(ClassUtils.class));
    }

    @Test
    public void test_getShortCanonicalName_Class_2() {
        assertEquals("ClassUtils[]", ClassUtils.getShortCanonicalName(ClassUtils[].class));
    }

    @Test
    public void test_getShortCanonicalName_Class_3() {
        assertEquals("ClassUtils[][]", ClassUtils.getShortCanonicalName(ClassUtils[][].class));
    }

    @Test
    public void test_getShortCanonicalName_Class_4() {
        assertEquals("int[]", ClassUtils.getShortCanonicalName(int[].class));
    }

    @Test
    public void test_getShortCanonicalName_Class_5() {
        assertEquals("int[][]", ClassUtils.getShortCanonicalName(int[][].class));
    }

    @Test
    public void test_getShortCanonicalName_Class_6_testMerged_6() {
        assertEquals("", ClassUtils.getShortCanonicalName(Named.class));
        assertEquals("Inner", ClassUtils.getShortCanonicalName(Inner.class));
        assertEquals(StringUtils.EMPTY, ClassUtils.getShortCanonicalName((Class<?>) null));
    }

    @Test
    public void test_getShortClassName_Class_1() {
        assertEquals("ClassUtils", ClassUtils.getShortClassName(ClassUtils.class));
    }

    @Test
    public void test_getShortClassName_Class_2() {
        assertEquals("Map.Entry", ClassUtils.getShortClassName(Map.Entry.class));
    }

    @Test
    public void test_getShortClassName_Class_3() {
        assertEquals("", ClassUtils.getShortClassName((Class<?>) null));
    }

    @Test
    public void test_getShortClassName_Class_4() {
        assertEquals("String[]", ClassUtils.getShortClassName(String[].class));
    }

    @Test
    public void test_getShortClassName_Class_5() {
        assertEquals("Map.Entry[]", ClassUtils.getShortClassName(Map.Entry[].class));
    }

    @Test
    public void test_getShortClassName_Class_6() {
        assertEquals("boolean", ClassUtils.getShortClassName(boolean.class));
    }

    @Test
    public void test_getShortClassName_Class_7() {
        assertEquals("byte", ClassUtils.getShortClassName(byte.class));
    }

    @Test
    public void test_getShortClassName_Class_8() {
        assertEquals("char", ClassUtils.getShortClassName(char.class));
    }

    @Test
    public void test_getShortClassName_Class_9() {
        assertEquals("short", ClassUtils.getShortClassName(short.class));
    }

    @Test
    public void test_getShortClassName_Class_10() {
        assertEquals("int", ClassUtils.getShortClassName(int.class));
    }

    @Test
    public void test_getShortClassName_Class_11() {
        assertEquals("long", ClassUtils.getShortClassName(long.class));
    }

    @Test
    public void test_getShortClassName_Class_12() {
        assertEquals("float", ClassUtils.getShortClassName(float.class));
    }

    @Test
    public void test_getShortClassName_Class_13() {
        assertEquals("double", ClassUtils.getShortClassName(double.class));
    }

    @Test
    public void test_getShortClassName_Class_14() {
        assertEquals("boolean[]", ClassUtils.getShortClassName(boolean[].class));
    }

    @Test
    public void test_getShortClassName_Class_15() {
        assertEquals("byte[]", ClassUtils.getShortClassName(byte[].class));
    }

    @Test
    public void test_getShortClassName_Class_16() {
        assertEquals("char[]", ClassUtils.getShortClassName(char[].class));
    }

    @Test
    public void test_getShortClassName_Class_17() {
        assertEquals("short[]", ClassUtils.getShortClassName(short[].class));
    }

    @Test
    public void test_getShortClassName_Class_18() {
        assertEquals("int[]", ClassUtils.getShortClassName(int[].class));
    }

    @Test
    public void test_getShortClassName_Class_19() {
        assertEquals("long[]", ClassUtils.getShortClassName(long[].class));
    }

    @Test
    public void test_getShortClassName_Class_20() {
        assertEquals("float[]", ClassUtils.getShortClassName(float[].class));
    }

    @Test
    public void test_getShortClassName_Class_21() {
        assertEquals("double[]", ClassUtils.getShortClassName(double[].class));
    }

    @Test
    public void test_getShortClassName_Class_22() {
        assertEquals("String[][]", ClassUtils.getShortClassName(String[][].class));
    }

    @Test
    public void test_getShortClassName_Class_23() {
        assertEquals("String[][][]", ClassUtils.getShortClassName(String[][][].class));
    }

    @Test
    public void test_getShortClassName_Class_24() {
        assertEquals("String[][][][]", ClassUtils.getShortClassName(String[][][][].class));
    }

    @Test
    public void test_getShortClassName_Class_25_testMerged_25() {
        assertEquals("ClassUtilsTest.10Named", ClassUtils.getShortClassName(Named.class));
        assertEquals("ClassUtilsTest.Inner", ClassUtils.getShortClassName(Inner.class));
    }

    @Test
    public void test_getShortClassName_Object_1() {
        assertEquals("ClassUtils", ClassUtils.getShortClassName(new ClassUtils(), "<null>"));
    }

    @Test
    public void test_getShortClassName_Object_2() {
        assertEquals("ClassUtilsTest.Inner", ClassUtils.getShortClassName(new Inner(), "<null>"));
    }

    @Test
    public void test_getShortClassName_Object_3() {
        assertEquals("String", ClassUtils.getShortClassName("hello", "<null>"));
    }

    @Test
    public void test_getShortClassName_Object_4() {
        assertEquals("<null>", ClassUtils.getShortClassName(null, "<null>"));
    }

    @Test
    public void test_getShortClassName_Object_5_testMerged_5() {
        assertEquals("ClassUtilsTest.11Named", ClassUtils.getShortClassName(new Named(), "<null>"));
    }

    @Test
    public void test_getShortClassName_Object_7() {
        assertEquals("ClassUtilsTest.Inner", ClassUtils.getShortClassName(new Inner(), "<null>"));
    }

    @Test
    public void test_getShortClassName_String_1() {
        assertEquals("ClassUtils", ClassUtils.getShortClassName(ClassUtils.class.getName()));
    }

    @Test
    public void test_getShortClassName_String_2() {
        assertEquals("Map.Entry", ClassUtils.getShortClassName(Map.Entry.class.getName()));
    }

    @Test
    public void test_getShortClassName_String_3() {
        assertEquals("", ClassUtils.getShortClassName((String) null));
    }

    @Test
    public void test_getShortClassName_String_4() {
        assertEquals("", ClassUtils.getShortClassName(""));
    }

    @Test
    public void test_getSimpleName_Class_1() {
        assertEquals("ClassUtils", ClassUtils.getSimpleName(ClassUtils.class));
    }

    @Test
    public void test_getSimpleName_Class_2() {
        assertEquals("Entry", ClassUtils.getSimpleName(Map.Entry.class));
    }

    @Test
    public void test_getSimpleName_Class_3() {
        assertEquals("", ClassUtils.getSimpleName(null));
    }

    @Test
    public void test_getSimpleName_Class_4() {
        assertEquals("String[]", ClassUtils.getSimpleName(String[].class));
    }

    @Test
    public void test_getSimpleName_Class_5() {
        assertEquals("Entry[]", ClassUtils.getSimpleName(Map.Entry[].class));
    }

    @Test
    public void test_getSimpleName_Class_6() {
        assertEquals("boolean", ClassUtils.getSimpleName(boolean.class));
    }

    @Test
    public void test_getSimpleName_Class_7() {
        assertEquals("byte", ClassUtils.getSimpleName(byte.class));
    }

    @Test
    public void test_getSimpleName_Class_8() {
        assertEquals("char", ClassUtils.getSimpleName(char.class));
    }

    @Test
    public void test_getSimpleName_Class_9() {
        assertEquals("short", ClassUtils.getSimpleName(short.class));
    }

    @Test
    public void test_getSimpleName_Class_10() {
        assertEquals("int", ClassUtils.getSimpleName(int.class));
    }

    @Test
    public void test_getSimpleName_Class_11() {
        assertEquals("long", ClassUtils.getSimpleName(long.class));
    }

    @Test
    public void test_getSimpleName_Class_12() {
        assertEquals("float", ClassUtils.getSimpleName(float.class));
    }

    @Test
    public void test_getSimpleName_Class_13() {
        assertEquals("double", ClassUtils.getSimpleName(double.class));
    }

    @Test
    public void test_getSimpleName_Class_14() {
        assertEquals("boolean[]", ClassUtils.getSimpleName(boolean[].class));
    }

    @Test
    public void test_getSimpleName_Class_15() {
        assertEquals("byte[]", ClassUtils.getSimpleName(byte[].class));
    }

    @Test
    public void test_getSimpleName_Class_16() {
        assertEquals("char[]", ClassUtils.getSimpleName(char[].class));
    }

    @Test
    public void test_getSimpleName_Class_17() {
        assertEquals("short[]", ClassUtils.getSimpleName(short[].class));
    }

    @Test
    public void test_getSimpleName_Class_18() {
        assertEquals("int[]", ClassUtils.getSimpleName(int[].class));
    }

    @Test
    public void test_getSimpleName_Class_19() {
        assertEquals("long[]", ClassUtils.getSimpleName(long[].class));
    }

    @Test
    public void test_getSimpleName_Class_20() {
        assertEquals("float[]", ClassUtils.getSimpleName(float[].class));
    }

    @Test
    public void test_getSimpleName_Class_21() {
        assertEquals("double[]", ClassUtils.getSimpleName(double[].class));
    }

    @Test
    public void test_getSimpleName_Class_22() {
        assertEquals("String[][]", ClassUtils.getSimpleName(String[][].class));
    }

    @Test
    public void test_getSimpleName_Class_23() {
        assertEquals("String[][][]", ClassUtils.getSimpleName(String[][][].class));
    }

    @Test
    public void test_getSimpleName_Class_24() {
        assertEquals("String[][][][]", ClassUtils.getSimpleName(String[][][][].class));
    }

    @Test
    public void test_getSimpleName_Class_25_testMerged_25() {
        assertEquals("Named", ClassUtils.getSimpleName(Named.class));
    }

    @Test
    public void test_getSimpleName_Object_1() {
        assertEquals("ClassUtils", ClassUtils.getSimpleName(new ClassUtils()));
    }

    @Test
    public void test_getSimpleName_Object_2() {
        assertEquals("Inner", ClassUtils.getSimpleName(new Inner()));
    }

    @Test
    public void test_getSimpleName_Object_3() {
        assertEquals("String", ClassUtils.getSimpleName("hello"));
    }

    @Test
    public void test_getSimpleName_Object_4() {
        assertEquals(StringUtils.EMPTY, ClassUtils.getSimpleName(null));
    }

    @Test
    public void test_getSimpleName_Object_5() {
        assertEquals(StringUtils.EMPTY, ClassUtils.getSimpleName(null));
    }

    @Test
    public void test_getSimpleName_Object_String_1() {
        assertEquals("ClassUtils", ClassUtils.getSimpleName(new ClassUtils(), "<null>"));
    }

    @Test
    public void test_getSimpleName_Object_String_2() {
        assertEquals("Inner", ClassUtils.getSimpleName(new Inner(), "<null>"));
    }

    @Test
    public void test_getSimpleName_Object_String_3() {
        assertEquals("String", ClassUtils.getSimpleName("hello", "<null>"));
    }

    @Test
    public void test_getSimpleName_Object_String_4() {
        assertEquals("<null>", ClassUtils.getSimpleName(null, "<null>"));
    }

    @Test
    public void test_getSimpleName_Object_String_5() {
        assertNull(ClassUtils.getSimpleName(null, null));
    }

    @Test
    public void test_isAssignable_1() {
        assertFalse(ClassUtils.isAssignable((Class<?>) null, null));
    }

    @Test
    public void test_isAssignable_2() {
        assertFalse(ClassUtils.isAssignable(String.class, null));
    }

    @Test
    public void test_isAssignable_3() {
        assertTrue(ClassUtils.isAssignable(null, Object.class));
    }

    @Test
    public void test_isAssignable_4() {
        assertTrue(ClassUtils.isAssignable(null, Integer.class));
    }

    @Test
    public void test_isAssignable_5() {
        assertFalse(ClassUtils.isAssignable(null, Integer.TYPE));
    }

    @Test
    public void test_isAssignable_6() {
        assertTrue(ClassUtils.isAssignable(String.class, Object.class));
    }

    @Test
    public void test_isAssignable_7() {
        assertTrue(ClassUtils.isAssignable(String.class, String.class));
    }

    @Test
    public void test_isAssignable_8() {
        assertFalse(ClassUtils.isAssignable(Object.class, String.class));
    }

    @Test
    public void test_isAssignable_9() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.class));
    }

    @Test
    public void test_isAssignable_10() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Object.class));
    }

    @Test
    public void test_isAssignable_11() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Integer.TYPE));
    }

    @Test
    public void test_isAssignable_12() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Object.class));
    }

    @Test
    public void test_isAssignable_13() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE));
    }

    @Test
    public void test_isAssignable_14() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Integer.class));
    }

    @Test
    public void test_isAssignable_15() {
        assertTrue(ClassUtils.isAssignable(Boolean.TYPE, Boolean.class));
    }

    @Test
    public void test_isAssignable_16() {
        assertTrue(ClassUtils.isAssignable(Boolean.TYPE, Object.class));
    }

    @Test
    public void test_isAssignable_17() {
        assertTrue(ClassUtils.isAssignable(Boolean.class, Boolean.TYPE));
    }

    @Test
    public void test_isAssignable_18() {
        assertTrue(ClassUtils.isAssignable(Boolean.class, Object.class));
    }

    @Test
    public void test_isAssignable_19() {
        assertTrue(ClassUtils.isAssignable(Boolean.TYPE, Boolean.TYPE));
    }

    @Test
    public void test_isAssignable_20() {
        assertTrue(ClassUtils.isAssignable(Boolean.class, Boolean.class));
    }

    @Test
    public void test_isAssignable_Autoboxing_1() {
        assertFalse(ClassUtils.isAssignable((Class<?>) null, null, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_2() {
        assertFalse(ClassUtils.isAssignable(String.class, null, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_3() {
        assertTrue(ClassUtils.isAssignable(null, Object.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_4() {
        assertTrue(ClassUtils.isAssignable(null, Integer.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_5() {
        assertFalse(ClassUtils.isAssignable(null, Integer.TYPE, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_6() {
        assertTrue(ClassUtils.isAssignable(String.class, Object.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_7() {
        assertTrue(ClassUtils.isAssignable(String.class, String.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_8() {
        assertFalse(ClassUtils.isAssignable(Object.class, String.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_9() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_10() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Object.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_11() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Integer.TYPE, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_12() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Object.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_13() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_14() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Integer.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_15() {
        assertTrue(ClassUtils.isAssignable(Boolean.TYPE, Boolean.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_16() {
        assertTrue(ClassUtils.isAssignable(Boolean.class, Boolean.TYPE, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_17() {
        assertTrue(ClassUtils.isAssignable(Boolean.class, Object.class, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_18() {
        assertTrue(ClassUtils.isAssignable(Boolean.TYPE, Boolean.TYPE, true));
    }

    @Test
    public void test_isAssignable_Autoboxing_19() {
        assertTrue(ClassUtils.isAssignable(Boolean.class, Boolean.class, true));
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_1() {
        assertFalse(ClassUtils.isAssignable(Byte.class, Character.TYPE), "byte -> char");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_2() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Byte.TYPE), "byte -> byte");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_3() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Short.TYPE), "byte -> short");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_4() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Integer.TYPE), "byte -> int");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_5() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Long.TYPE), "byte -> long");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_6() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Float.TYPE), "byte -> float");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_7() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Double.TYPE), "byte -> double");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_8() {
        assertFalse(ClassUtils.isAssignable(Byte.class, Boolean.TYPE), "byte -> boolean");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_9() {
        assertFalse(ClassUtils.isAssignable(Short.class, Character.TYPE), "short -> char");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_10() {
        assertFalse(ClassUtils.isAssignable(Short.class, Byte.TYPE), "short -> byte");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_11() {
        assertTrue(ClassUtils.isAssignable(Short.class, Short.TYPE), "short -> short");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_12() {
        assertTrue(ClassUtils.isAssignable(Short.class, Integer.TYPE), "short -> int");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_13() {
        assertTrue(ClassUtils.isAssignable(Short.class, Long.TYPE), "short -> long");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_14() {
        assertTrue(ClassUtils.isAssignable(Short.class, Float.TYPE), "short -> float");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_15() {
        assertTrue(ClassUtils.isAssignable(Short.class, Double.TYPE), "short -> double");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_16() {
        assertFalse(ClassUtils.isAssignable(Short.class, Boolean.TYPE), "short -> boolean");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_17() {
        assertTrue(ClassUtils.isAssignable(Character.class, Character.TYPE), "char -> char");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_18() {
        assertFalse(ClassUtils.isAssignable(Character.class, Byte.TYPE), "char -> byte");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_19() {
        assertFalse(ClassUtils.isAssignable(Character.class, Short.TYPE), "char -> short");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_20() {
        assertTrue(ClassUtils.isAssignable(Character.class, Integer.TYPE), "char -> int");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_21() {
        assertTrue(ClassUtils.isAssignable(Character.class, Long.TYPE), "char -> long");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_22() {
        assertTrue(ClassUtils.isAssignable(Character.class, Float.TYPE), "char -> float");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_23() {
        assertTrue(ClassUtils.isAssignable(Character.class, Double.TYPE), "char -> double");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_24() {
        assertFalse(ClassUtils.isAssignable(Character.class, Boolean.TYPE), "char -> boolean");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_25() {
        assertFalse(ClassUtils.isAssignable(Integer.class, Character.TYPE), "int -> char");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_26() {
        assertFalse(ClassUtils.isAssignable(Integer.class, Byte.TYPE), "int -> byte");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_27() {
        assertFalse(ClassUtils.isAssignable(Integer.class, Short.TYPE), "int -> short");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_28() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Integer.TYPE), "int -> int");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_29() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Long.TYPE), "int -> long");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_30() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Float.TYPE), "int -> float");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_31() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Double.TYPE), "int -> double");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_32() {
        assertFalse(ClassUtils.isAssignable(Integer.class, Boolean.TYPE), "int -> boolean");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_33() {
        assertFalse(ClassUtils.isAssignable(Long.class, Character.TYPE), "long -> char");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_34() {
        assertFalse(ClassUtils.isAssignable(Long.class, Byte.TYPE), "long -> byte");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_35() {
        assertFalse(ClassUtils.isAssignable(Long.class, Short.TYPE), "long -> short");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_36() {
        assertFalse(ClassUtils.isAssignable(Long.class, Integer.TYPE), "long -> int");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_37() {
        assertTrue(ClassUtils.isAssignable(Long.class, Long.TYPE), "long -> long");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_38() {
        assertTrue(ClassUtils.isAssignable(Long.class, Float.TYPE), "long -> float");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_39() {
        assertTrue(ClassUtils.isAssignable(Long.class, Double.TYPE), "long -> double");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_40() {
        assertFalse(ClassUtils.isAssignable(Long.class, Boolean.TYPE), "long -> boolean");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_41() {
        assertFalse(ClassUtils.isAssignable(Float.class, Character.TYPE), "float -> char");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_42() {
        assertFalse(ClassUtils.isAssignable(Float.class, Byte.TYPE), "float -> byte");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_43() {
        assertFalse(ClassUtils.isAssignable(Float.class, Short.TYPE), "float -> short");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_44() {
        assertFalse(ClassUtils.isAssignable(Float.class, Integer.TYPE), "float -> int");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_45() {
        assertFalse(ClassUtils.isAssignable(Float.class, Long.TYPE), "float -> long");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_46() {
        assertTrue(ClassUtils.isAssignable(Float.class, Float.TYPE), "float -> float");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_47() {
        assertTrue(ClassUtils.isAssignable(Float.class, Double.TYPE), "float -> double");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_48() {
        assertFalse(ClassUtils.isAssignable(Float.class, Boolean.TYPE), "float -> boolean");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_49() {
        assertFalse(ClassUtils.isAssignable(Double.class, Character.TYPE), "double -> char");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_50() {
        assertFalse(ClassUtils.isAssignable(Double.class, Byte.TYPE), "double -> byte");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_51() {
        assertFalse(ClassUtils.isAssignable(Double.class, Short.TYPE), "double -> short");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_52() {
        assertFalse(ClassUtils.isAssignable(Double.class, Integer.TYPE), "double -> int");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_53() {
        assertFalse(ClassUtils.isAssignable(Double.class, Long.TYPE), "double -> long");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_54() {
        assertFalse(ClassUtils.isAssignable(Double.class, Float.TYPE), "double -> float");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_55() {
        assertTrue(ClassUtils.isAssignable(Double.class, Double.TYPE), "double -> double");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_56() {
        assertFalse(ClassUtils.isAssignable(Double.class, Boolean.TYPE), "double -> boolean");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_57() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Character.TYPE), "boolean -> char");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_58() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Byte.TYPE), "boolean -> byte");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_59() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Short.TYPE), "boolean -> short");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_60() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Integer.TYPE), "boolean -> int");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_61() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Long.TYPE), "boolean -> long");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_62() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Float.TYPE), "boolean -> float");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_63() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Double.TYPE), "boolean -> double");
    }

    @Test
    public void test_isAssignable_DefaultUnboxing_Widening_64() {
        assertTrue(ClassUtils.isAssignable(Boolean.class, Boolean.TYPE), "boolean -> boolean");
    }

    @Test
    public void test_isAssignable_NoAutoboxing_1() {
        assertFalse(ClassUtils.isAssignable((Class<?>) null, null, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_2() {
        assertFalse(ClassUtils.isAssignable(String.class, null, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_3() {
        assertTrue(ClassUtils.isAssignable(null, Object.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_4() {
        assertTrue(ClassUtils.isAssignable(null, Integer.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_5() {
        assertFalse(ClassUtils.isAssignable(null, Integer.TYPE, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_6() {
        assertTrue(ClassUtils.isAssignable(String.class, Object.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_7() {
        assertTrue(ClassUtils.isAssignable(String.class, String.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_8() {
        assertFalse(ClassUtils.isAssignable(Object.class, String.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_9() {
        assertFalse(ClassUtils.isAssignable(Integer.TYPE, Integer.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_10() {
        assertFalse(ClassUtils.isAssignable(Integer.TYPE, Object.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_11() {
        assertFalse(ClassUtils.isAssignable(Integer.class, Integer.TYPE, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_12() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_13() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Integer.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_14() {
        assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Boolean.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_15() {
        assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Object.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_16() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Boolean.TYPE, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_17() {
        assertTrue(ClassUtils.isAssignable(Boolean.class, Object.class, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_18() {
        assertTrue(ClassUtils.isAssignable(Boolean.TYPE, Boolean.TYPE, false));
    }

    @Test
    public void test_isAssignable_NoAutoboxing_19() {
        assertTrue(ClassUtils.isAssignable(Boolean.class, Boolean.class, false));
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_1() {
        assertFalse(ClassUtils.isAssignable(Byte.class, Character.TYPE, true), "byte -> char");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_2() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Byte.TYPE, true), "byte -> byte");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_3() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Short.TYPE, true), "byte -> short");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_4() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Integer.TYPE, true), "byte -> int");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_5() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Long.TYPE, true), "byte -> long");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_6() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Float.TYPE, true), "byte -> float");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_7() {
        assertTrue(ClassUtils.isAssignable(Byte.class, Double.TYPE, true), "byte -> double");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_8() {
        assertFalse(ClassUtils.isAssignable(Byte.class, Boolean.TYPE, true), "byte -> boolean");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_9() {
        assertFalse(ClassUtils.isAssignable(Short.class, Character.TYPE, true), "short -> char");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_10() {
        assertFalse(ClassUtils.isAssignable(Short.class, Byte.TYPE, true), "short -> byte");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_11() {
        assertTrue(ClassUtils.isAssignable(Short.class, Short.TYPE, true), "short -> short");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_12() {
        assertTrue(ClassUtils.isAssignable(Short.class, Integer.TYPE, true), "short -> int");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_13() {
        assertTrue(ClassUtils.isAssignable(Short.class, Long.TYPE, true), "short -> long");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_14() {
        assertTrue(ClassUtils.isAssignable(Short.class, Float.TYPE, true), "short -> float");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_15() {
        assertTrue(ClassUtils.isAssignable(Short.class, Double.TYPE, true), "short -> double");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_16() {
        assertFalse(ClassUtils.isAssignable(Short.class, Boolean.TYPE, true), "short -> boolean");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_17() {
        assertTrue(ClassUtils.isAssignable(Character.class, Character.TYPE, true), "char -> char");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_18() {
        assertFalse(ClassUtils.isAssignable(Character.class, Byte.TYPE, true), "char -> byte");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_19() {
        assertFalse(ClassUtils.isAssignable(Character.class, Short.TYPE, true), "char -> short");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_20() {
        assertTrue(ClassUtils.isAssignable(Character.class, Integer.TYPE, true), "char -> int");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_21() {
        assertTrue(ClassUtils.isAssignable(Character.class, Long.TYPE, true), "char -> long");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_22() {
        assertTrue(ClassUtils.isAssignable(Character.class, Float.TYPE, true), "char -> float");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_23() {
        assertTrue(ClassUtils.isAssignable(Character.class, Double.TYPE, true), "char -> double");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_24() {
        assertFalse(ClassUtils.isAssignable(Character.class, Boolean.TYPE, true), "char -> boolean");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_25() {
        assertFalse(ClassUtils.isAssignable(Integer.class, Character.TYPE, true), "int -> char");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_26() {
        assertFalse(ClassUtils.isAssignable(Integer.class, Byte.TYPE, true), "int -> byte");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_27() {
        assertFalse(ClassUtils.isAssignable(Integer.class, Short.TYPE, true), "int -> short");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_28() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Integer.TYPE, true), "int -> int");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_29() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Long.TYPE, true), "int -> long");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_30() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Float.TYPE, true), "int -> float");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_31() {
        assertTrue(ClassUtils.isAssignable(Integer.class, Double.TYPE, true), "int -> double");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_32() {
        assertFalse(ClassUtils.isAssignable(Integer.class, Boolean.TYPE, true), "int -> boolean");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_33() {
        assertFalse(ClassUtils.isAssignable(Long.class, Character.TYPE, true), "long -> char");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_34() {
        assertFalse(ClassUtils.isAssignable(Long.class, Byte.TYPE, true), "long -> byte");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_35() {
        assertFalse(ClassUtils.isAssignable(Long.class, Short.TYPE, true), "long -> short");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_36() {
        assertFalse(ClassUtils.isAssignable(Long.class, Integer.TYPE, true), "long -> int");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_37() {
        assertTrue(ClassUtils.isAssignable(Long.class, Long.TYPE, true), "long -> long");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_38() {
        assertTrue(ClassUtils.isAssignable(Long.class, Float.TYPE, true), "long -> float");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_39() {
        assertTrue(ClassUtils.isAssignable(Long.class, Double.TYPE, true), "long -> double");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_40() {
        assertFalse(ClassUtils.isAssignable(Long.class, Boolean.TYPE, true), "long -> boolean");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_41() {
        assertFalse(ClassUtils.isAssignable(Float.class, Character.TYPE, true), "float -> char");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_42() {
        assertFalse(ClassUtils.isAssignable(Float.class, Byte.TYPE, true), "float -> byte");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_43() {
        assertFalse(ClassUtils.isAssignable(Float.class, Short.TYPE, true), "float -> short");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_44() {
        assertFalse(ClassUtils.isAssignable(Float.class, Integer.TYPE, true), "float -> int");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_45() {
        assertFalse(ClassUtils.isAssignable(Float.class, Long.TYPE, true), "float -> long");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_46() {
        assertTrue(ClassUtils.isAssignable(Float.class, Float.TYPE, true), "float -> float");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_47() {
        assertTrue(ClassUtils.isAssignable(Float.class, Double.TYPE, true), "float -> double");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_48() {
        assertFalse(ClassUtils.isAssignable(Float.class, Boolean.TYPE, true), "float -> boolean");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_49() {
        assertFalse(ClassUtils.isAssignable(Double.class, Character.TYPE, true), "double -> char");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_50() {
        assertFalse(ClassUtils.isAssignable(Double.class, Byte.TYPE, true), "double -> byte");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_51() {
        assertFalse(ClassUtils.isAssignable(Double.class, Short.TYPE, true), "double -> short");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_52() {
        assertFalse(ClassUtils.isAssignable(Double.class, Integer.TYPE, true), "double -> int");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_53() {
        assertFalse(ClassUtils.isAssignable(Double.class, Long.TYPE, true), "double -> long");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_54() {
        assertFalse(ClassUtils.isAssignable(Double.class, Float.TYPE, true), "double -> float");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_55() {
        assertTrue(ClassUtils.isAssignable(Double.class, Double.TYPE, true), "double -> double");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_56() {
        assertFalse(ClassUtils.isAssignable(Double.class, Boolean.TYPE, true), "double -> boolean");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_57() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Character.TYPE, true), "boolean -> char");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_58() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Byte.TYPE, true), "boolean -> byte");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_59() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Short.TYPE, true), "boolean -> short");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_60() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Integer.TYPE, true), "boolean -> int");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_61() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Long.TYPE, true), "boolean -> long");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_62() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Float.TYPE, true), "boolean -> float");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_63() {
        assertFalse(ClassUtils.isAssignable(Boolean.class, Double.TYPE, true), "boolean -> double");
    }

    @Test
    public void test_isAssignable_Unboxing_Widening_64() {
        assertTrue(ClassUtils.isAssignable(Boolean.class, Boolean.TYPE, true), "boolean -> boolean");
    }

    @Test
    public void test_isAssignable_Widening_1() {
        assertFalse(ClassUtils.isAssignable(Byte.TYPE, Character.TYPE), "byte -> char");
    }

    @Test
    public void test_isAssignable_Widening_2() {
        assertTrue(ClassUtils.isAssignable(Byte.TYPE, Byte.TYPE), "byte -> byte");
    }

    @Test
    public void test_isAssignable_Widening_3() {
        assertTrue(ClassUtils.isAssignable(Byte.TYPE, Short.TYPE), "byte -> short");
    }

    @Test
    public void test_isAssignable_Widening_4() {
        assertTrue(ClassUtils.isAssignable(Byte.TYPE, Integer.TYPE), "byte -> int");
    }

    @Test
    public void test_isAssignable_Widening_5() {
        assertTrue(ClassUtils.isAssignable(Byte.TYPE, Long.TYPE), "byte -> long");
    }

    @Test
    public void test_isAssignable_Widening_6() {
        assertTrue(ClassUtils.isAssignable(Byte.TYPE, Float.TYPE), "byte -> float");
    }

    @Test
    public void test_isAssignable_Widening_7() {
        assertTrue(ClassUtils.isAssignable(Byte.TYPE, Double.TYPE), "byte -> double");
    }

    @Test
    public void test_isAssignable_Widening_8() {
        assertFalse(ClassUtils.isAssignable(Byte.TYPE, Boolean.TYPE), "byte -> boolean");
    }

    @Test
    public void test_isAssignable_Widening_9() {
        assertFalse(ClassUtils.isAssignable(Short.TYPE, Character.TYPE), "short -> char");
    }

    @Test
    public void test_isAssignable_Widening_10() {
        assertFalse(ClassUtils.isAssignable(Short.TYPE, Byte.TYPE), "short -> byte");
    }

    @Test
    public void test_isAssignable_Widening_11() {
        assertTrue(ClassUtils.isAssignable(Short.TYPE, Short.TYPE), "short -> short");
    }

    @Test
    public void test_isAssignable_Widening_12() {
        assertTrue(ClassUtils.isAssignable(Short.TYPE, Integer.TYPE), "short -> int");
    }

    @Test
    public void test_isAssignable_Widening_13() {
        assertTrue(ClassUtils.isAssignable(Short.TYPE, Long.TYPE), "short -> long");
    }

    @Test
    public void test_isAssignable_Widening_14() {
        assertTrue(ClassUtils.isAssignable(Short.TYPE, Float.TYPE), "short -> float");
    }

    @Test
    public void test_isAssignable_Widening_15() {
        assertTrue(ClassUtils.isAssignable(Short.TYPE, Double.TYPE), "short -> double");
    }

    @Test
    public void test_isAssignable_Widening_16() {
        assertFalse(ClassUtils.isAssignable(Short.TYPE, Boolean.TYPE), "short -> boolean");
    }

    @Test
    public void test_isAssignable_Widening_17() {
        assertTrue(ClassUtils.isAssignable(Character.TYPE, Character.TYPE), "char -> char");
    }

    @Test
    public void test_isAssignable_Widening_18() {
        assertFalse(ClassUtils.isAssignable(Character.TYPE, Byte.TYPE), "char -> byte");
    }

    @Test
    public void test_isAssignable_Widening_19() {
        assertFalse(ClassUtils.isAssignable(Character.TYPE, Short.TYPE), "char -> short");
    }

    @Test
    public void test_isAssignable_Widening_20() {
        assertTrue(ClassUtils.isAssignable(Character.TYPE, Integer.TYPE), "char -> int");
    }

    @Test
    public void test_isAssignable_Widening_21() {
        assertTrue(ClassUtils.isAssignable(Character.TYPE, Long.TYPE), "char -> long");
    }

    @Test
    public void test_isAssignable_Widening_22() {
        assertTrue(ClassUtils.isAssignable(Character.TYPE, Float.TYPE), "char -> float");
    }

    @Test
    public void test_isAssignable_Widening_23() {
        assertTrue(ClassUtils.isAssignable(Character.TYPE, Double.TYPE), "char -> double");
    }

    @Test
    public void test_isAssignable_Widening_24() {
        assertFalse(ClassUtils.isAssignable(Character.TYPE, Boolean.TYPE), "char -> boolean");
    }

    @Test
    public void test_isAssignable_Widening_25() {
        assertFalse(ClassUtils.isAssignable(Integer.TYPE, Character.TYPE), "int -> char");
    }

    @Test
    public void test_isAssignable_Widening_26() {
        assertFalse(ClassUtils.isAssignable(Integer.TYPE, Byte.TYPE), "int -> byte");
    }

    @Test
    public void test_isAssignable_Widening_27() {
        assertFalse(ClassUtils.isAssignable(Integer.TYPE, Short.TYPE), "int -> short");
    }

    @Test
    public void test_isAssignable_Widening_28() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE), "int -> int");
    }

    @Test
    public void test_isAssignable_Widening_29() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Long.TYPE), "int -> long");
    }

    @Test
    public void test_isAssignable_Widening_30() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Float.TYPE), "int -> float");
    }

    @Test
    public void test_isAssignable_Widening_31() {
        assertTrue(ClassUtils.isAssignable(Integer.TYPE, Double.TYPE), "int -> double");
    }

    @Test
    public void test_isAssignable_Widening_32() {
        assertFalse(ClassUtils.isAssignable(Integer.TYPE, Boolean.TYPE), "int -> boolean");
    }

    @Test
    public void test_isAssignable_Widening_33() {
        assertFalse(ClassUtils.isAssignable(Long.TYPE, Character.TYPE), "long -> char");
    }

    @Test
    public void test_isAssignable_Widening_34() {
        assertFalse(ClassUtils.isAssignable(Long.TYPE, Byte.TYPE), "long -> byte");
    }

    @Test
    public void test_isAssignable_Widening_35() {
        assertFalse(ClassUtils.isAssignable(Long.TYPE, Short.TYPE), "long -> short");
    }

    @Test
    public void test_isAssignable_Widening_36() {
        assertFalse(ClassUtils.isAssignable(Long.TYPE, Integer.TYPE), "long -> int");
    }

    @Test
    public void test_isAssignable_Widening_37() {
        assertTrue(ClassUtils.isAssignable(Long.TYPE, Long.TYPE), "long -> long");
    }

    @Test
    public void test_isAssignable_Widening_38() {
        assertTrue(ClassUtils.isAssignable(Long.TYPE, Float.TYPE), "long -> float");
    }

    @Test
    public void test_isAssignable_Widening_39() {
        assertTrue(ClassUtils.isAssignable(Long.TYPE, Double.TYPE), "long -> double");
    }

    @Test
    public void test_isAssignable_Widening_40() {
        assertFalse(ClassUtils.isAssignable(Long.TYPE, Boolean.TYPE), "long -> boolean");
    }

    @Test
    public void test_isAssignable_Widening_41() {
        assertFalse(ClassUtils.isAssignable(Float.TYPE, Character.TYPE), "float -> char");
    }

    @Test
    public void test_isAssignable_Widening_42() {
        assertFalse(ClassUtils.isAssignable(Float.TYPE, Byte.TYPE), "float -> byte");
    }

    @Test
    public void test_isAssignable_Widening_43() {
        assertFalse(ClassUtils.isAssignable(Float.TYPE, Short.TYPE), "float -> short");
    }

    @Test
    public void test_isAssignable_Widening_44() {
        assertFalse(ClassUtils.isAssignable(Float.TYPE, Integer.TYPE), "float -> int");
    }

    @Test
    public void test_isAssignable_Widening_45() {
        assertFalse(ClassUtils.isAssignable(Float.TYPE, Long.TYPE), "float -> long");
    }

    @Test
    public void test_isAssignable_Widening_46() {
        assertTrue(ClassUtils.isAssignable(Float.TYPE, Float.TYPE), "float -> float");
    }

    @Test
    public void test_isAssignable_Widening_47() {
        assertTrue(ClassUtils.isAssignable(Float.TYPE, Double.TYPE), "float -> double");
    }

    @Test
    public void test_isAssignable_Widening_48() {
        assertFalse(ClassUtils.isAssignable(Float.TYPE, Boolean.TYPE), "float -> boolean");
    }

    @Test
    public void test_isAssignable_Widening_49() {
        assertFalse(ClassUtils.isAssignable(Double.TYPE, Character.TYPE), "double -> char");
    }

    @Test
    public void test_isAssignable_Widening_50() {
        assertFalse(ClassUtils.isAssignable(Double.TYPE, Byte.TYPE), "double -> byte");
    }

    @Test
    public void test_isAssignable_Widening_51() {
        assertFalse(ClassUtils.isAssignable(Double.TYPE, Short.TYPE), "double -> short");
    }

    @Test
    public void test_isAssignable_Widening_52() {
        assertFalse(ClassUtils.isAssignable(Double.TYPE, Integer.TYPE), "double -> int");
    }

    @Test
    public void test_isAssignable_Widening_53() {
        assertFalse(ClassUtils.isAssignable(Double.TYPE, Long.TYPE), "double -> long");
    }

    @Test
    public void test_isAssignable_Widening_54() {
        assertFalse(ClassUtils.isAssignable(Double.TYPE, Float.TYPE), "double -> float");
    }

    @Test
    public void test_isAssignable_Widening_55() {
        assertTrue(ClassUtils.isAssignable(Double.TYPE, Double.TYPE), "double -> double");
    }

    @Test
    public void test_isAssignable_Widening_56() {
        assertFalse(ClassUtils.isAssignable(Double.TYPE, Boolean.TYPE), "double -> boolean");
    }

    @Test
    public void test_isAssignable_Widening_57() {
        assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Character.TYPE), "boolean -> char");
    }

    @Test
    public void test_isAssignable_Widening_58() {
        assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Byte.TYPE), "boolean -> byte");
    }

    @Test
    public void test_isAssignable_Widening_59() {
        assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Short.TYPE), "boolean -> short");
    }

    @Test
    public void test_isAssignable_Widening_60() {
        assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Integer.TYPE), "boolean -> int");
    }

    @Test
    public void test_isAssignable_Widening_61() {
        assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Long.TYPE), "boolean -> long");
    }

    @Test
    public void test_isAssignable_Widening_62() {
        assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Float.TYPE), "boolean -> float");
    }

    @Test
    public void test_isAssignable_Widening_63() {
        assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Double.TYPE), "boolean -> double");
    }

    @Test
    public void test_isAssignable_Widening_64() {
        assertTrue(ClassUtils.isAssignable(Boolean.TYPE, Boolean.TYPE), "boolean -> boolean");
    }

    @Test
    public void test_isInnerClass_Class_1() {
        assertTrue(ClassUtils.isInnerClass(Inner.class));
    }

    @Test
    public void test_isInnerClass_Class_2() {
        assertTrue(ClassUtils.isInnerClass(Map.Entry.class));
    }

    @Test
    public void test_isInnerClass_Class_3() {
        assertTrue(ClassUtils.isInnerClass(new Cloneable() {
        }.getClass()));
    }

    @Test
    public void test_isInnerClass_Class_4() {
        assertFalse(ClassUtils.isInnerClass(this.getClass()));
    }

    @Test
    public void test_isInnerClass_Class_5() {
        assertFalse(ClassUtils.isInnerClass(String.class));
    }

    @Test
    public void test_isInnerClass_Class_6() {
        assertFalse(ClassUtils.isInnerClass(null));
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new ClassUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = ClassUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(ClassUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(ClassUtils.class.getModifiers()));
    }

    @Test
    public void testGetClassByNormalNameArrays_1() throws ClassNotFoundException {
        assertEquals(int[].class, ClassUtils.getClass("int[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_2() throws ClassNotFoundException {
        assertEquals(long[].class, ClassUtils.getClass("long[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_3() throws ClassNotFoundException {
        assertEquals(short[].class, ClassUtils.getClass("short[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_4() throws ClassNotFoundException {
        assertEquals(byte[].class, ClassUtils.getClass("byte[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_5() throws ClassNotFoundException {
        assertEquals(char[].class, ClassUtils.getClass("char[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_6() throws ClassNotFoundException {
        assertEquals(float[].class, ClassUtils.getClass("float[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_7() throws ClassNotFoundException {
        assertEquals(double[].class, ClassUtils.getClass("double[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_8() throws ClassNotFoundException {
        assertEquals(boolean[].class, ClassUtils.getClass("boolean[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_9() throws ClassNotFoundException {
        assertEquals(String[].class, ClassUtils.getClass("java.lang.String[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_10() throws ClassNotFoundException {
        assertEquals(java.util.Map.Entry[].class, ClassUtils.getClass("java.util.Map.Entry[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_11() throws ClassNotFoundException {
        assertEquals(java.util.Map.Entry[].class, ClassUtils.getClass("java.util.Map$Entry[]"));
    }

    @Test
    public void testGetClassByNormalNameArrays_12() throws ClassNotFoundException {
        assertEquals(java.util.Map.Entry[].class, ClassUtils.getClass("[Ljava.util.Map.Entry;"));
    }

    @Test
    public void testGetClassByNormalNameArrays_13() throws ClassNotFoundException {
        assertEquals(java.util.Map.Entry[].class, ClassUtils.getClass("[Ljava.util.Map$Entry;"));
    }

    @Test
    public void testGetClassByNormalNameArrays2D_1() throws ClassNotFoundException {
        assertEquals(int[][].class, ClassUtils.getClass("int[][]"));
    }

    @Test
    public void testGetClassByNormalNameArrays2D_2() throws ClassNotFoundException {
        assertEquals(long[][].class, ClassUtils.getClass("long[][]"));
    }

    @Test
    public void testGetClassByNormalNameArrays2D_3() throws ClassNotFoundException {
        assertEquals(short[][].class, ClassUtils.getClass("short[][]"));
    }

    @Test
    public void testGetClassByNormalNameArrays2D_4() throws ClassNotFoundException {
        assertEquals(byte[][].class, ClassUtils.getClass("byte[][]"));
    }

    @Test
    public void testGetClassByNormalNameArrays2D_5() throws ClassNotFoundException {
        assertEquals(char[][].class, ClassUtils.getClass("char[][]"));
    }

    @Test
    public void testGetClassByNormalNameArrays2D_6() throws ClassNotFoundException {
        assertEquals(float[][].class, ClassUtils.getClass("float[][]"));
    }

    @Test
    public void testGetClassByNormalNameArrays2D_7() throws ClassNotFoundException {
        assertEquals(double[][].class, ClassUtils.getClass("double[][]"));
    }

    @Test
    public void testGetClassByNormalNameArrays2D_8() throws ClassNotFoundException {
        assertEquals(boolean[][].class, ClassUtils.getClass("boolean[][]"));
    }

    @Test
    public void testGetClassByNormalNameArrays2D_9() throws ClassNotFoundException {
        assertEquals(String[][].class, ClassUtils.getClass("java.lang.String[][]"));
    }

    @Test
    public void testGetClassClassNotFound_1() throws Exception {
        assertGetClassThrowsClassNotFound("bool");
    }

    @Test
    public void testGetClassClassNotFound_2() throws Exception {
        assertGetClassThrowsClassNotFound("bool[]");
    }

    @Test
    public void testGetClassClassNotFound_3() throws Exception {
        assertGetClassThrowsClassNotFound("integer[]");
    }

    @Test
    public void testGetClassInvalidArguments_1() throws Exception {
        assertGetClassThrowsNullPointerException(null);
    }

    @Test
    public void testGetClassInvalidArguments_2() throws Exception {
        assertGetClassThrowsClassNotFound("[][][]");
    }

    @Test
    public void testGetClassInvalidArguments_3() throws Exception {
        assertGetClassThrowsClassNotFound("[[]");
    }

    @Test
    public void testGetClassInvalidArguments_4() throws Exception {
        assertGetClassThrowsClassNotFound("[");
    }

    @Test
    public void testGetClassInvalidArguments_5() throws Exception {
        assertGetClassThrowsClassNotFound("java.lang.String][");
    }

    @Test
    public void testGetClassInvalidArguments_6() throws Exception {
        assertGetClassThrowsClassNotFound(".hello.world");
    }

    @Test
    public void testGetClassInvalidArguments_7() throws Exception {
        assertGetClassThrowsClassNotFound("hello..world");
    }

    @Test
    public void testGetClassRawPrimitives_1() throws ClassNotFoundException {
        assertEquals(int.class, ClassUtils.getClass("int"));
    }

    @Test
    public void testGetClassRawPrimitives_2() throws ClassNotFoundException {
        assertEquals(long.class, ClassUtils.getClass("long"));
    }

    @Test
    public void testGetClassRawPrimitives_3() throws ClassNotFoundException {
        assertEquals(short.class, ClassUtils.getClass("short"));
    }

    @Test
    public void testGetClassRawPrimitives_4() throws ClassNotFoundException {
        assertEquals(byte.class, ClassUtils.getClass("byte"));
    }

    @Test
    public void testGetClassRawPrimitives_5() throws ClassNotFoundException {
        assertEquals(char.class, ClassUtils.getClass("char"));
    }

    @Test
    public void testGetClassRawPrimitives_6() throws ClassNotFoundException {
        assertEquals(float.class, ClassUtils.getClass("float"));
    }

    @Test
    public void testGetClassRawPrimitives_7() throws ClassNotFoundException {
        assertEquals(double.class, ClassUtils.getClass("double"));
    }

    @Test
    public void testGetClassRawPrimitives_8() throws ClassNotFoundException {
        assertEquals(boolean.class, ClassUtils.getClass("boolean"));
    }

    @Test
    public void testGetClassRawPrimitives_9() throws ClassNotFoundException {
        assertEquals(void.class, ClassUtils.getClass("void"));
    }

    @Test
    public void testGetClassWithArrayClasses_1() throws Exception {
        assertGetClassReturnsClass(String[].class);
    }

    @Test
    public void testGetClassWithArrayClasses_2() throws Exception {
        assertGetClassReturnsClass(int[].class);
    }

    @Test
    public void testGetClassWithArrayClasses_3() throws Exception {
        assertGetClassReturnsClass(long[].class);
    }

    @Test
    public void testGetClassWithArrayClasses_4() throws Exception {
        assertGetClassReturnsClass(short[].class);
    }

    @Test
    public void testGetClassWithArrayClasses_5() throws Exception {
        assertGetClassReturnsClass(byte[].class);
    }

    @Test
    public void testGetClassWithArrayClasses_6() throws Exception {
        assertGetClassReturnsClass(char[].class);
    }

    @Test
    public void testGetClassWithArrayClasses_7() throws Exception {
        assertGetClassReturnsClass(float[].class);
    }

    @Test
    public void testGetClassWithArrayClasses_8() throws Exception {
        assertGetClassReturnsClass(double[].class);
    }

    @Test
    public void testGetClassWithArrayClasses_9() throws Exception {
        assertGetClassReturnsClass(boolean[].class);
    }

    @Test
    public void testGetClassWithArrayClasses2D_1() throws Exception {
        assertGetClassReturnsClass(String[][].class);
    }

    @Test
    public void testGetClassWithArrayClasses2D_2() throws Exception {
        assertGetClassReturnsClass(int[][].class);
    }

    @Test
    public void testGetClassWithArrayClasses2D_3() throws Exception {
        assertGetClassReturnsClass(long[][].class);
    }

    @Test
    public void testGetClassWithArrayClasses2D_4() throws Exception {
        assertGetClassReturnsClass(short[][].class);
    }

    @Test
    public void testGetClassWithArrayClasses2D_5() throws Exception {
        assertGetClassReturnsClass(byte[][].class);
    }

    @Test
    public void testGetClassWithArrayClasses2D_6() throws Exception {
        assertGetClassReturnsClass(char[][].class);
    }

    @Test
    public void testGetClassWithArrayClasses2D_7() throws Exception {
        assertGetClassReturnsClass(float[][].class);
    }

    @Test
    public void testGetClassWithArrayClasses2D_8() throws Exception {
        assertGetClassReturnsClass(double[][].class);
    }

    @Test
    public void testGetClassWithArrayClasses2D_9() throws Exception {
        assertGetClassReturnsClass(boolean[][].class);
    }

    @Test
    public void testGetInnerClass_1() throws ClassNotFoundException {
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest.Inner.DeeplyNested"));
    }

    @Test
    public void testGetInnerClass_2() throws ClassNotFoundException {
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest.Inner$DeeplyNested"));
    }

    @Test
    public void testGetInnerClass_3() throws ClassNotFoundException {
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest$Inner$DeeplyNested"));
    }

    @Test
    public void testGetInnerClass_4() throws ClassNotFoundException {
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest$Inner.DeeplyNested"));
    }

    @Test
    public void testGetInnerClass_5() throws ClassNotFoundException {
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest.Inner.DeeplyNested", true));
    }

    @Test
    public void testGetInnerClass_6() throws ClassNotFoundException {
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest.Inner$DeeplyNested", true));
    }

    @Test
    public void testGetInnerClass_7() throws ClassNotFoundException {
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest$Inner$DeeplyNested", true));
    }

    @Test
    public void testGetInnerClass_8() throws ClassNotFoundException {
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest$Inner.DeeplyNested", true));
    }

    @Test
    public void testGetInnerClass_9_testMerged_9() throws ClassNotFoundException {
        final ClassLoader classLoader = Inner.DeeplyNested.class.getClassLoader();
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass(classLoader, "org.apache.commons.lang3.ClassUtilsTest.Inner.DeeplyNested"));
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass(classLoader, "org.apache.commons.lang3.ClassUtilsTest.Inner$DeeplyNested"));
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass(classLoader, "org.apache.commons.lang3.ClassUtilsTest$Inner$DeeplyNested"));
        assertEquals(Inner.DeeplyNested.class, ClassUtils.getClass(classLoader, "org.apache.commons.lang3.ClassUtilsTest$Inner.DeeplyNested"));
    }

    @Test
    public void testIsPrimitiveOrWrapper_1() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Boolean.class), "Boolean.class");
    }

    @Test
    public void testIsPrimitiveOrWrapper_2() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Byte.class), "Byte.class");
    }

    @Test
    public void testIsPrimitiveOrWrapper_3() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Character.class), "Character.class");
    }

    @Test
    public void testIsPrimitiveOrWrapper_4() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Short.class), "Short.class");
    }

    @Test
    public void testIsPrimitiveOrWrapper_5() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Integer.class), "Integer.class");
    }

    @Test
    public void testIsPrimitiveOrWrapper_6() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Long.class), "Long.class");
    }

    @Test
    public void testIsPrimitiveOrWrapper_7() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Double.class), "Double.class");
    }

    @Test
    public void testIsPrimitiveOrWrapper_8() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Float.class), "Float.class");
    }

    @Test
    public void testIsPrimitiveOrWrapper_9() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Boolean.TYPE), "boolean");
    }

    @Test
    public void testIsPrimitiveOrWrapper_10() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Byte.TYPE), "byte");
    }

    @Test
    public void testIsPrimitiveOrWrapper_11() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Character.TYPE), "char");
    }

    @Test
    public void testIsPrimitiveOrWrapper_12() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Short.TYPE), "short");
    }

    @Test
    public void testIsPrimitiveOrWrapper_13() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Integer.TYPE), "int");
    }

    @Test
    public void testIsPrimitiveOrWrapper_14() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Long.TYPE), "long");
    }

    @Test
    public void testIsPrimitiveOrWrapper_15() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Double.TYPE), "double");
    }

    @Test
    public void testIsPrimitiveOrWrapper_16() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Float.TYPE), "float");
    }

    @Test
    public void testIsPrimitiveOrWrapper_17() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Void.TYPE), "Void.TYPE");
    }

    @Test
    public void testIsPrimitiveOrWrapper_18() {
        assertFalse(ClassUtils.isPrimitiveOrWrapper(null), "null");
    }

    @Test
    public void testIsPrimitiveOrWrapper_19() {
        assertFalse(ClassUtils.isPrimitiveOrWrapper(Void.class), "Void.class");
    }

    @Test
    public void testIsPrimitiveOrWrapper_20() {
        assertFalse(ClassUtils.isPrimitiveOrWrapper(String.class), "String.class");
    }

    @Test
    public void testIsPrimitiveOrWrapper_21() {
        assertFalse(ClassUtils.isPrimitiveOrWrapper(this.getClass()), "this.getClass()");
    }

    @Test
    public void testIsPrimitiveWrapper_1() {
        assertTrue(ClassUtils.isPrimitiveWrapper(Boolean.class), "Boolean.class");
    }

    @Test
    public void testIsPrimitiveWrapper_2() {
        assertTrue(ClassUtils.isPrimitiveWrapper(Byte.class), "Byte.class");
    }

    @Test
    public void testIsPrimitiveWrapper_3() {
        assertTrue(ClassUtils.isPrimitiveWrapper(Character.class), "Character.class");
    }

    @Test
    public void testIsPrimitiveWrapper_4() {
        assertTrue(ClassUtils.isPrimitiveWrapper(Short.class), "Short.class");
    }

    @Test
    public void testIsPrimitiveWrapper_5() {
        assertTrue(ClassUtils.isPrimitiveWrapper(Integer.class), "Integer.class");
    }

    @Test
    public void testIsPrimitiveWrapper_6() {
        assertTrue(ClassUtils.isPrimitiveWrapper(Long.class), "Long.class");
    }

    @Test
    public void testIsPrimitiveWrapper_7() {
        assertTrue(ClassUtils.isPrimitiveWrapper(Double.class), "Double.class");
    }

    @Test
    public void testIsPrimitiveWrapper_8() {
        assertTrue(ClassUtils.isPrimitiveWrapper(Float.class), "Float.class");
    }

    @Test
    public void testIsPrimitiveWrapper_9() {
        assertFalse(ClassUtils.isPrimitiveWrapper(Boolean.TYPE), "boolean");
    }

    @Test
    public void testIsPrimitiveWrapper_10() {
        assertFalse(ClassUtils.isPrimitiveWrapper(Byte.TYPE), "byte");
    }

    @Test
    public void testIsPrimitiveWrapper_11() {
        assertFalse(ClassUtils.isPrimitiveWrapper(Character.TYPE), "char");
    }

    @Test
    public void testIsPrimitiveWrapper_12() {
        assertFalse(ClassUtils.isPrimitiveWrapper(Short.TYPE), "short");
    }

    @Test
    public void testIsPrimitiveWrapper_13() {
        assertFalse(ClassUtils.isPrimitiveWrapper(Integer.TYPE), "int");
    }

    @Test
    public void testIsPrimitiveWrapper_14() {
        assertFalse(ClassUtils.isPrimitiveWrapper(Long.TYPE), "long");
    }

    @Test
    public void testIsPrimitiveWrapper_15() {
        assertFalse(ClassUtils.isPrimitiveWrapper(Double.TYPE), "double");
    }

    @Test
    public void testIsPrimitiveWrapper_16() {
        assertFalse(ClassUtils.isPrimitiveWrapper(Float.TYPE), "float");
    }

    @Test
    public void testIsPrimitiveWrapper_17() {
        assertFalse(ClassUtils.isPrimitiveWrapper(null), "null");
    }

    @Test
    public void testIsPrimitiveWrapper_18() {
        assertFalse(ClassUtils.isPrimitiveWrapper(Void.class), "Void.class");
    }

    @Test
    public void testIsPrimitiveWrapper_19() {
        assertFalse(ClassUtils.isPrimitiveWrapper(Void.TYPE), "Void.TYPE");
    }

    @Test
    public void testIsPrimitiveWrapper_20() {
        assertFalse(ClassUtils.isPrimitiveWrapper(String.class), "String.class");
    }

    @Test
    public void testIsPrimitiveWrapper_21() {
        assertFalse(ClassUtils.isPrimitiveWrapper(this.getClass()), "this.getClass()");
    }

    @Test
    public void testPrimitiveToWrapper_1() {
        assertEquals(Boolean.class, ClassUtils.primitiveToWrapper(Boolean.TYPE), "boolean -> Boolean.class");
    }

    @Test
    public void testPrimitiveToWrapper_2() {
        assertEquals(Byte.class, ClassUtils.primitiveToWrapper(Byte.TYPE), "byte -> Byte.class");
    }

    @Test
    public void testPrimitiveToWrapper_3() {
        assertEquals(Character.class, ClassUtils.primitiveToWrapper(Character.TYPE), "char -> Character.class");
    }

    @Test
    public void testPrimitiveToWrapper_4() {
        assertEquals(Short.class, ClassUtils.primitiveToWrapper(Short.TYPE), "short -> Short.class");
    }

    @Test
    public void testPrimitiveToWrapper_5() {
        assertEquals(Integer.class, ClassUtils.primitiveToWrapper(Integer.TYPE), "int -> Integer.class");
    }

    @Test
    public void testPrimitiveToWrapper_6() {
        assertEquals(Long.class, ClassUtils.primitiveToWrapper(Long.TYPE), "long -> Long.class");
    }

    @Test
    public void testPrimitiveToWrapper_7() {
        assertEquals(Double.class, ClassUtils.primitiveToWrapper(Double.TYPE), "double -> Double.class");
    }

    @Test
    public void testPrimitiveToWrapper_8() {
        assertEquals(Float.class, ClassUtils.primitiveToWrapper(Float.TYPE), "float -> Float.class");
    }

    @Test
    public void testPrimitiveToWrapper_9() {
        assertEquals(String.class, ClassUtils.primitiveToWrapper(String.class), "String.class -> String.class");
    }

    @Test
    public void testPrimitiveToWrapper_10() {
        assertEquals(ClassUtils.class, ClassUtils.primitiveToWrapper(ClassUtils.class), "ClassUtils.class -> ClassUtils.class");
    }

    @Test
    public void testPrimitiveToWrapper_11() {
        assertEquals(Void.TYPE, ClassUtils.primitiveToWrapper(Void.TYPE), "Void.TYPE -> Void.TYPE");
    }

    @Test
    public void testPrimitiveToWrapper_12() {
        assertNull(ClassUtils.primitiveToWrapper(null), "null -> null");
    }

    @Test
    public void testWithInterleavingWhitespace_1() throws ClassNotFoundException {
        assertEquals(int[].class, ClassUtils.getClass(" int [ ] "));
    }

    @Test
    public void testWithInterleavingWhitespace_2() throws ClassNotFoundException {
        assertEquals(long[].class, ClassUtils.getClass("\rlong\t[\n]\r"));
    }

    @Test
    public void testWithInterleavingWhitespace_3() throws ClassNotFoundException {
        assertEquals(short[].class, ClassUtils.getClass("\tshort                \t\t[]"));
    }

    @Test
    public void testWithInterleavingWhitespace_4() throws ClassNotFoundException {
        assertEquals(byte[].class, ClassUtils.getClass("byte[\t\t\n\r]   "));
    }
}
