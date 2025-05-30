package org.adoptopenjdk.jitwatch.test;

import static org.adoptopenjdk.jitwatch.core.JITWatchConstants.S_TYPE_NAME_VOID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.adoptopenjdk.jitwatch.demo.MakeHotSpotLog;
import org.adoptopenjdk.jitwatch.model.IMetaMember;
import org.adoptopenjdk.jitwatch.model.JITDataModel;
import org.adoptopenjdk.jitwatch.model.LogParseException;
import org.adoptopenjdk.jitwatch.model.MemberSignatureParts;
import org.adoptopenjdk.jitwatch.model.MetaClass;
import org.adoptopenjdk.jitwatch.model.MetaConstructor;
import org.adoptopenjdk.jitwatch.model.MetaMethod;
import org.adoptopenjdk.jitwatch.model.MetaPackage;
import org.adoptopenjdk.jitwatch.model.bytecode.BytecodeInstruction;
import org.adoptopenjdk.jitwatch.util.ClassUtil;
import org.adoptopenjdk.jitwatch.util.ParseUtil;
import org.adoptopenjdk.jitwatch.util.StringUtil;
import org.junit.Test;

public class TestParseUtil_Purified {

    private String loadConvert(char[] in, int off, int len, char[] convtBuf) {
        return "foo";
    }

    public void doSomethingWithVarArgs(String... args) {
    }

    public void doSomethingWithParamAndVarArgs(int foo, String... args) {
    }

    public void method_with_underscores() {
    }

    public void unicodeMethodNameµµµµµ() {
    }

    @Test
    public void testFindClassForLogCompilationParameter_1() throws Exception {
        assertEquals(Class.forName("java.lang.String"), ParseUtil.findClassForLogCompilationParameter("java.lang.String"));
    }

    @Test
    public void testFindClassForLogCompilationParameter_2() throws Exception {
        assertEquals(Class.forName("[Ljava.lang.String;"), ParseUtil.findClassForLogCompilationParameter("java.lang.String[]"));
    }

    @Test
    public void testFindClassForLogCompilationParameter_3() throws Exception {
        assertEquals(Class.forName("[[Ljava.lang.String;"), ParseUtil.findClassForLogCompilationParameter("java.lang.String[][]"));
    }

    @Test
    public void testFindClassForLogCompilationParameter_4() throws Exception {
        assertEquals(Class.forName("[Ljava.lang.String;"), ParseUtil.findClassForLogCompilationParameter("java.lang.String..."));
    }

    @Test
    public void testFindClassForLogCompilationParameter_5() throws Exception {
        assertEquals(int.class, ParseUtil.findClassForLogCompilationParameter("int"));
    }

    @Test
    public void testFindClassForLogCompilationParameter_6() throws Exception {
        assertEquals(Class.forName("[I"), ParseUtil.findClassForLogCompilationParameter("int[]"));
    }

    @Test
    public void testFindClassForLogCompilationParameter_7() throws Exception {
        assertEquals(Class.forName("[[I"), ParseUtil.findClassForLogCompilationParameter("int[][]"));
    }

    @Test
    public void testFindClassForLogCompilationParameterRegressionForGenerics_1() throws Exception {
        assertEquals(Class.forName("java.util.List"), ParseUtil.findClassForLogCompilationParameter("java.util.List<?>"));
    }

    @Test
    public void testFindClassForLogCompilationParameterRegressionForGenerics_2() throws Exception {
        assertEquals(Class.forName("java.util.List"), ParseUtil.findClassForLogCompilationParameter("java.util.List<T>"));
    }

    @Test
    public void testStripGenerics_1() {
        assertEquals("int", ParseUtil.stripGenerics("int"));
    }

    @Test
    public void testStripGenerics_2() {
        assertEquals("java.util.List", ParseUtil.stripGenerics("java.util.List"));
    }

    @Test
    public void testStripGenerics_3() {
        assertEquals("java.util.List", ParseUtil.stripGenerics("java.util.List<T>"));
    }

    @Test
    public void testStripGenerics_4() {
        assertEquals("java.util.List", ParseUtil.stripGenerics("java.util.List<Class<T>>"));
    }

    @Test
    public void testStripGenerics_5() {
        assertEquals("java.util.List", ParseUtil.stripGenerics("java.util.List<? super T>"));
    }

    @Test
    public void testStripGenerics_6() {
        assertEquals("java.util.List[]", ParseUtil.stripGenerics("java.util.List<? super T>[]"));
    }

    @Test
    public void testStripGenerics_7() {
        assertEquals("java.util.List[]", ParseUtil.stripGenerics("java.util.List<?>[]"));
    }
}
