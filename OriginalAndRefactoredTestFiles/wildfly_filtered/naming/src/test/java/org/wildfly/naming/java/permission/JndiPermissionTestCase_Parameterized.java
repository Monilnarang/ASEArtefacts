package org.wildfly.naming.java.permission;

import static org.junit.Assert.*;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JndiPermissionTestCase_Parameterized {

    @Test
    public void testActions_8() {
        assertTrue(new JndiPermission("foo", "").withActions("bind").implies(new JndiPermission("foo", "bind")));
    }

    @Test
    public void testActions_9() {
        assertFalse(new JndiPermission("foo", "unbind").withoutActions("unbind").implies(new JndiPermission("foo", "unbind")));
    }

    @Test
    public void testSecurity_1() {
        assertEquals(new JndiPermission("-", Integer.MAX_VALUE).getActionBits(), JndiPermission.ACTION_ALL);
    }

    @Test
    public void testSecurity_2() {
        assertEquals(new JndiPermission("-", Integer.MAX_VALUE), new JndiPermission("-", "*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNameImplies_1_1to3_32to35")
    public void testNameImplies_1_1to3_32to35(String param1, String param2, String param3, String param4) {
        assertEquals(new JndiPermission(param1, param2), new JndiPermission(param3, param4));
    }

    static public Stream<Arguments> Provider_testNameImplies_1_1to3_32to35() {
        return Stream.of(arguments("<<ALL BINDINGS>>", "*", "-", "*"), arguments("java:", "*", "", "*"), arguments("java:/", "*", "/", "*"), arguments("java:-", "*", "-", "*"), arguments("java:*", "*", "*", "*"), arguments("foo", "*", "foo", "all"), arguments("foo", "*", "foo", "lookup,bind,rebind,unbind,list,listBindings,createSubcontext,destroySubcontext,addNamingListener"), arguments("foo", "*", "foo", "unbind,list,listBindings,createSubcontext,destroySubcontext,addNamingListener,lookup,bind,rebind"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNameImplies_2to4_4to5_5to6_6to25_29to31")
    public void testNameImplies_2to4_4to5_5to6_6to25_29to31(String param1, String param2, String param3, String param4) {
        assertTrue(new JndiPermission(param1, param2).implies(new JndiPermission(param3, param4)));
    }

    static public Stream<Arguments> Provider_testNameImplies_2to4_4to5_5to6_6to25_29to31() {
        return Stream.of(arguments("-", "*", "-", "*"), arguments("-", "*", "", "*"), arguments("-", "*", "foo", "*"), arguments("-", "*", "/foo", "*"), arguments("-", "*", "foo/", "*"), arguments("-", "*", "foo/bar/baz/zap", "*"), arguments("-", "*", "java:foo", "*"), arguments("/-", "*", "/-", "*"), arguments("/-", "*", "/", "*"), arguments("/-", "*", "//", "*"), arguments("/-", "*", "////", "*"), arguments("/-", "*", "/foo", "*"), arguments("/-", "*", "/foo", "*"), arguments("/-", "*", "/foo/", "*"), arguments("/-", "*", "/foo/bar/baz/zap", "*"), arguments("/-", "*", "java:/foo", "*"), arguments("foo/-", "*", "foo/-", "*"), arguments("foo/-", "*", "foo/foo", "*"), arguments("foo/-", "*", "foo/foo", "*"), arguments("foo/-", "*", "foo/foo/", "*"), arguments("foo/-", "*", "foo/foo/bar/baz/zap", "*"), arguments("foo/-", "*", "java:foo/foo", "*"), arguments("*", "*", "", "*"), arguments("*", "*", "foo", "*"), arguments("*/*", "*", "/foo", "*"), arguments("/*", "*", "/foo", "*"), arguments("*/foo", "*", "/foo", "*"), arguments("foo", "*", "foo", "lookup"), arguments("foo", "", "foo", ""), arguments("foo", "*", "foo", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNameImplies_7_26to28")
    public void testNameImplies_7_26to28(String param1, String param2, String param3, String param4) {
        assertFalse(new JndiPermission(param1, param2).implies(new JndiPermission(param3, param4)));
    }

    static public Stream<Arguments> Provider_testNameImplies_7_26to28() {
        return Stream.of(arguments("*", "*", "foo/bar", "*"), arguments("*", "*", "foo/", "*"), arguments("*", "*", "/foo", "*"), arguments("foo", "", "foo", "bind"));
    }
}
