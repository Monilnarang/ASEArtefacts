package org.apache.hadoop.fs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNotEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXAttr_Purified {

    private static XAttr XATTR, XATTR1, XATTR2, XATTR3, XATTR4, XATTR5;

    @BeforeClass
    public static void setUp() throws Exception {
        byte[] value = { 0x31, 0x32, 0x33 };
        XATTR = new XAttr.Builder().setName("name").setValue(value).build();
        XATTR1 = new XAttr.Builder().setNameSpace(XAttr.NameSpace.USER).setName("name").setValue(value).build();
        XATTR2 = new XAttr.Builder().setNameSpace(XAttr.NameSpace.TRUSTED).setName("name").setValue(value).build();
        XATTR3 = new XAttr.Builder().setNameSpace(XAttr.NameSpace.SYSTEM).setName("name").setValue(value).build();
        XATTR4 = new XAttr.Builder().setNameSpace(XAttr.NameSpace.SECURITY).setName("name").setValue(value).build();
        XATTR5 = new XAttr.Builder().setNameSpace(XAttr.NameSpace.RAW).setName("name").setValue(value).build();
    }

    @Test
    public void testXAttrEquals_1() {
        assertNotSame(XATTR1, XATTR2);
    }

    @Test
    public void testXAttrEquals_2() {
        assertNotSame(XATTR2, XATTR3);
    }

    @Test
    public void testXAttrEquals_3() {
        assertNotSame(XATTR3, XATTR4);
    }

    @Test
    public void testXAttrEquals_4() {
        assertNotSame(XATTR4, XATTR5);
    }

    @Test
    public void testXAttrEquals_5() {
        assertEquals(XATTR, XATTR1);
    }

    @Test
    public void testXAttrEquals_6() {
        assertEquals(XATTR1, XATTR1);
    }

    @Test
    public void testXAttrEquals_7() {
        assertEquals(XATTR2, XATTR2);
    }

    @Test
    public void testXAttrEquals_8() {
        assertEquals(XATTR3, XATTR3);
    }

    @Test
    public void testXAttrEquals_9() {
        assertEquals(XATTR4, XATTR4);
    }

    @Test
    public void testXAttrEquals_10() {
        assertEquals(XATTR5, XATTR5);
    }

    @Test
    public void testXAttrEquals_11() {
        assertNotEquals(XATTR1, XATTR2);
    }

    @Test
    public void testXAttrEquals_12() {
        assertNotEquals(XATTR2, XATTR3);
    }

    @Test
    public void testXAttrEquals_13() {
        assertNotEquals(XATTR3, XATTR4);
    }

    @Test
    public void testXAttrEquals_14() {
        assertNotEquals(XATTR4, XATTR5);
    }

    @Test
    public void testXAttrHashCode_1() {
        assertEquals(XATTR.hashCode(), XATTR1.hashCode());
    }

    @Test
    public void testXAttrHashCode_2() {
        assertNotEquals(XATTR1.hashCode(), XATTR2.hashCode());
    }

    @Test
    public void testXAttrHashCode_3() {
        assertNotEquals(XATTR2.hashCode(), XATTR3.hashCode());
    }

    @Test
    public void testXAttrHashCode_4() {
        assertNotEquals(XATTR3.hashCode(), XATTR4.hashCode());
    }

    @Test
    public void testXAttrHashCode_5() {
        assertNotEquals(XATTR4.hashCode(), XATTR5.hashCode());
    }
}
