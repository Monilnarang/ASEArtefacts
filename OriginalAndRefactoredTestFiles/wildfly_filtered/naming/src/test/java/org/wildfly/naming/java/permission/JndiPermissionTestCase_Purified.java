package org.wildfly.naming.java.permission;

import static org.junit.Assert.*;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;
import org.junit.Test;

public class JndiPermissionTestCase_Purified {

    @Test
    public void testNameImplies_1() {
        assertEquals(new JndiPermission("<<ALL BINDINGS>>", "*"), new JndiPermission("-", "*"));
    }

    @Test
    public void testNameImplies_2() {
        assertTrue(new JndiPermission("-", "*").implies(new JndiPermission("-", "*")));
    }

    @Test
    public void testNameImplies_3() {
        assertTrue(new JndiPermission("-", "*").implies(new JndiPermission("", "*")));
    }

    @Test
    public void testNameImplies_4() {
        assertTrue(new JndiPermission("-", "*").implies(new JndiPermission("foo", "*")));
    }

    @Test
    public void testNameImplies_5() {
        assertTrue(new JndiPermission("-", "*").implies(new JndiPermission("/foo", "*")));
    }

    @Test
    public void testNameImplies_6() {
        assertTrue(new JndiPermission("-", "*").implies(new JndiPermission("foo/", "*")));
    }

    @Test
    public void testNameImplies_7() {
        assertTrue(new JndiPermission("-", "*").implies(new JndiPermission("foo/bar/baz/zap", "*")));
    }

    @Test
    public void testNameImplies_8() {
        assertTrue(new JndiPermission("-", "*").implies(new JndiPermission("java:foo", "*")));
    }

    @Test
    public void testNameImplies_9() {
        assertTrue(new JndiPermission("/-", "*").implies(new JndiPermission("/-", "*")));
    }

    @Test
    public void testNameImplies_10() {
        assertTrue(new JndiPermission("/-", "*").implies(new JndiPermission("/", "*")));
    }

    @Test
    public void testNameImplies_11() {
        assertTrue(new JndiPermission("/-", "*").implies(new JndiPermission("//", "*")));
    }

    @Test
    public void testNameImplies_12() {
        assertTrue(new JndiPermission("/-", "*").implies(new JndiPermission("////", "*")));
    }

    @Test
    public void testNameImplies_13() {
        assertTrue(new JndiPermission("/-", "*").implies(new JndiPermission("/foo", "*")));
    }

    @Test
    public void testNameImplies_14() {
        assertTrue(new JndiPermission("/-", "*").implies(new JndiPermission("/foo", "*")));
    }

    @Test
    public void testNameImplies_15() {
        assertTrue(new JndiPermission("/-", "*").implies(new JndiPermission("/foo/", "*")));
    }

    @Test
    public void testNameImplies_16() {
        assertTrue(new JndiPermission("/-", "*").implies(new JndiPermission("/foo/bar/baz/zap", "*")));
    }

    @Test
    public void testNameImplies_17() {
        assertTrue(new JndiPermission("/-", "*").implies(new JndiPermission("java:/foo", "*")));
    }

    @Test
    public void testNameImplies_18() {
        assertTrue(new JndiPermission("foo/-", "*").implies(new JndiPermission("foo/-", "*")));
    }

    @Test
    public void testNameImplies_19() {
        assertTrue(new JndiPermission("foo/-", "*").implies(new JndiPermission("foo/foo", "*")));
    }

    @Test
    public void testNameImplies_20() {
        assertTrue(new JndiPermission("foo/-", "*").implies(new JndiPermission("foo/foo", "*")));
    }

    @Test
    public void testNameImplies_21() {
        assertTrue(new JndiPermission("foo/-", "*").implies(new JndiPermission("foo/foo/", "*")));
    }

    @Test
    public void testNameImplies_22() {
        assertTrue(new JndiPermission("foo/-", "*").implies(new JndiPermission("foo/foo/bar/baz/zap", "*")));
    }

    @Test
    public void testNameImplies_23() {
        assertTrue(new JndiPermission("foo/-", "*").implies(new JndiPermission("java:foo/foo", "*")));
    }

    @Test
    public void testNameImplies_24() {
        assertTrue(new JndiPermission("*", "*").implies(new JndiPermission("", "*")));
    }

    @Test
    public void testNameImplies_25() {
        assertTrue(new JndiPermission("*", "*").implies(new JndiPermission("foo", "*")));
    }

    @Test
    public void testNameImplies_26() {
        assertFalse(new JndiPermission("*", "*").implies(new JndiPermission("foo/bar", "*")));
    }

    @Test
    public void testNameImplies_27() {
        assertFalse(new JndiPermission("*", "*").implies(new JndiPermission("foo/", "*")));
    }

    @Test
    public void testNameImplies_28() {
        assertFalse(new JndiPermission("*", "*").implies(new JndiPermission("/foo", "*")));
    }

    @Test
    public void testNameImplies_29() {
        assertTrue(new JndiPermission("*/*", "*").implies(new JndiPermission("/foo", "*")));
    }

    @Test
    public void testNameImplies_30() {
        assertTrue(new JndiPermission("/*", "*").implies(new JndiPermission("/foo", "*")));
    }

    @Test
    public void testNameImplies_31() {
        assertTrue(new JndiPermission("*/foo", "*").implies(new JndiPermission("/foo", "*")));
    }

    @Test
    public void testNameImplies_32() {
        assertEquals(new JndiPermission("java:", "*"), new JndiPermission("", "*"));
    }

    @Test
    public void testNameImplies_33() {
        assertEquals(new JndiPermission("java:/", "*"), new JndiPermission("/", "*"));
    }

    @Test
    public void testNameImplies_34() {
        assertEquals(new JndiPermission("java:-", "*"), new JndiPermission("-", "*"));
    }

    @Test
    public void testNameImplies_35() {
        assertEquals(new JndiPermission("java:*", "*"), new JndiPermission("*", "*"));
    }

    @Test
    public void testActions_1() {
        assertEquals(new JndiPermission("foo", "*"), new JndiPermission("foo", "all"));
    }

    @Test
    public void testActions_2() {
        assertEquals(new JndiPermission("foo", "*"), new JndiPermission("foo", "lookup,bind,rebind,unbind,list,listBindings,createSubcontext,destroySubcontext,addNamingListener"));
    }

    @Test
    public void testActions_3() {
        assertEquals(new JndiPermission("foo", "*"), new JndiPermission("foo", "unbind,list,listBindings,createSubcontext,destroySubcontext,addNamingListener,lookup,bind,rebind"));
    }

    @Test
    public void testActions_4() {
        assertTrue(new JndiPermission("foo", "*").implies(new JndiPermission("foo", "lookup")));
    }

    @Test
    public void testActions_5() {
        assertTrue(new JndiPermission("foo", "").implies(new JndiPermission("foo", "")));
    }

    @Test
    public void testActions_6() {
        assertTrue(new JndiPermission("foo", "*").implies(new JndiPermission("foo", "")));
    }

    @Test
    public void testActions_7() {
        assertFalse(new JndiPermission("foo", "").implies(new JndiPermission("foo", "bind")));
    }

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
}
