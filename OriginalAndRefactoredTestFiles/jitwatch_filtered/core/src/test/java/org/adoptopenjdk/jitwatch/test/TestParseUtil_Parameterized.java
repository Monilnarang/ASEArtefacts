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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestParseUtil_Parameterized {

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
    public void testFindClassForLogCompilationParameter_5() throws Exception {
        assertEquals(int.class, ParseUtil.findClassForLogCompilationParameter("int"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFindClassForLogCompilationParameter_1_1to2_2to4_6to7")
    public void testFindClassForLogCompilationParameter_1_1to2_2to4_6to7(String param1, String param2) throws Exception {
        assertEquals(Class.forName(param1), ParseUtil.findClassForLogCompilationParameter(param2));
    }

    static public Stream<Arguments> Provider_testFindClassForLogCompilationParameter_1_1to2_2to4_6to7() {
        return Stream.of(arguments("java.lang.String", "java.lang.String"), arguments("[Ljava.lang.String;", "java.lang.String[]"), arguments("[[Ljava.lang.String;", "java.lang.String[][]"), arguments("[Ljava.lang.String;", "java.lang.String..."), arguments("[I", "int[]"), arguments("[[I", "int[][]"), arguments("java.util.List", "java.util.List<?>"), arguments("java.util.List", "java.util.List<T>"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripGenerics_1to7")
    public void testStripGenerics_1to7(String param1, String param2) {
        assertEquals(param1, ParseUtil.stripGenerics(param2));
    }

    static public Stream<Arguments> Provider_testStripGenerics_1to7() {
        return Stream.of(arguments("int", "int"), arguments("java.util.List", "java.util.List"), arguments("java.util.List", "java.util.List<T>"), arguments("java.util.List", "java.util.List<Class<T>>"), arguments("java.util.List", "java.util.List<? super T>"), arguments("java.util.List[]", "java.util.List<? super T>[]"), arguments("java.util.List[]", "java.util.List<?>[]"));
    }
}
