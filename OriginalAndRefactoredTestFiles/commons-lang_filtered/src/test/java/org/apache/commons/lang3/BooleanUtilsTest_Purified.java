package org.apache.commons.lang3;

import static org.apache.commons.lang3.ArraySorter.sort;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BooleanUtilsTest_Purified extends AbstractLangTest {

    @Test
    public void test_isFalse_Boolean_1() {
        assertFalse(BooleanUtils.isFalse(Boolean.TRUE));
    }

    @Test
    public void test_isFalse_Boolean_2() {
        assertTrue(BooleanUtils.isFalse(Boolean.FALSE));
    }

    @Test
    public void test_isFalse_Boolean_3() {
        assertFalse(BooleanUtils.isFalse(null));
    }

    @Test
    public void test_isNotFalse_Boolean_1() {
        assertTrue(BooleanUtils.isNotFalse(Boolean.TRUE));
    }

    @Test
    public void test_isNotFalse_Boolean_2() {
        assertFalse(BooleanUtils.isNotFalse(Boolean.FALSE));
    }

    @Test
    public void test_isNotFalse_Boolean_3() {
        assertTrue(BooleanUtils.isNotFalse(null));
    }

    @Test
    public void test_isNotTrue_Boolean_1() {
        assertFalse(BooleanUtils.isNotTrue(Boolean.TRUE));
    }

    @Test
    public void test_isNotTrue_Boolean_2() {
        assertTrue(BooleanUtils.isNotTrue(Boolean.FALSE));
    }

    @Test
    public void test_isNotTrue_Boolean_3() {
        assertTrue(BooleanUtils.isNotTrue(null));
    }

    @Test
    public void test_isTrue_Boolean_1() {
        assertTrue(BooleanUtils.isTrue(Boolean.TRUE));
    }

    @Test
    public void test_isTrue_Boolean_2() {
        assertFalse(BooleanUtils.isTrue(Boolean.FALSE));
    }

    @Test
    public void test_isTrue_Boolean_3() {
        assertFalse(BooleanUtils.isTrue(null));
    }

    @Test
    public void test_negate_Boolean_1() {
        assertSame(null, BooleanUtils.negate(null));
    }

    @Test
    public void test_negate_Boolean_2() {
        assertSame(Boolean.TRUE, BooleanUtils.negate(Boolean.FALSE));
    }

    @Test
    public void test_negate_Boolean_3() {
        assertSame(Boolean.FALSE, BooleanUtils.negate(Boolean.TRUE));
    }

    @Test
    public void test_toBoolean_Boolean_1() {
        assertTrue(BooleanUtils.toBoolean(Boolean.TRUE));
    }

    @Test
    public void test_toBoolean_Boolean_2() {
        assertFalse(BooleanUtils.toBoolean(Boolean.FALSE));
    }

    @Test
    public void test_toBoolean_Boolean_3() {
        assertFalse(BooleanUtils.toBoolean((Boolean) null));
    }

    @Test
    public void test_toBoolean_int_1() {
        assertTrue(BooleanUtils.toBoolean(1));
    }

    @Test
    public void test_toBoolean_int_2() {
        assertTrue(BooleanUtils.toBoolean(-1));
    }

    @Test
    public void test_toBoolean_int_3() {
        assertFalse(BooleanUtils.toBoolean(0));
    }

    @Test
    public void test_toBoolean_int_int_int_1() {
        assertTrue(BooleanUtils.toBoolean(6, 6, 7));
    }

    @Test
    public void test_toBoolean_int_int_int_2() {
        assertFalse(BooleanUtils.toBoolean(7, 6, 7));
    }

    @Test
    public void test_toBoolean_String_1() {
        assertFalse(BooleanUtils.toBoolean((String) null));
    }

    @Test
    public void test_toBoolean_String_2() {
        assertFalse(BooleanUtils.toBoolean(""));
    }

    @Test
    public void test_toBoolean_String_3() {
        assertFalse(BooleanUtils.toBoolean("off"));
    }

    @Test
    public void test_toBoolean_String_4() {
        assertFalse(BooleanUtils.toBoolean("oof"));
    }

    @Test
    public void test_toBoolean_String_5() {
        assertFalse(BooleanUtils.toBoolean("yep"));
    }

    @Test
    public void test_toBoolean_String_6() {
        assertFalse(BooleanUtils.toBoolean("trux"));
    }

    @Test
    public void test_toBoolean_String_7() {
        assertFalse(BooleanUtils.toBoolean("false"));
    }

    @Test
    public void test_toBoolean_String_8() {
        assertFalse(BooleanUtils.toBoolean("a"));
    }

    @Test
    public void test_toBoolean_String_9() {
        assertTrue(BooleanUtils.toBoolean("true"));
    }

    @Test
    public void test_toBoolean_String_10() {
        assertTrue(BooleanUtils.toBoolean(new StringBuilder("tr").append("ue").toString()));
    }

    @Test
    public void test_toBoolean_String_11() {
        assertTrue(BooleanUtils.toBoolean("truE"));
    }

    @Test
    public void test_toBoolean_String_12() {
        assertTrue(BooleanUtils.toBoolean("trUe"));
    }

    @Test
    public void test_toBoolean_String_13() {
        assertTrue(BooleanUtils.toBoolean("trUE"));
    }

    @Test
    public void test_toBoolean_String_14() {
        assertTrue(BooleanUtils.toBoolean("tRue"));
    }

    @Test
    public void test_toBoolean_String_15() {
        assertTrue(BooleanUtils.toBoolean("tRuE"));
    }

    @Test
    public void test_toBoolean_String_16() {
        assertTrue(BooleanUtils.toBoolean("tRUe"));
    }

    @Test
    public void test_toBoolean_String_17() {
        assertTrue(BooleanUtils.toBoolean("tRUE"));
    }

    @Test
    public void test_toBoolean_String_18() {
        assertTrue(BooleanUtils.toBoolean("TRUE"));
    }

    @Test
    public void test_toBoolean_String_19() {
        assertTrue(BooleanUtils.toBoolean("TRUe"));
    }

    @Test
    public void test_toBoolean_String_20() {
        assertTrue(BooleanUtils.toBoolean("TRuE"));
    }

    @Test
    public void test_toBoolean_String_21() {
        assertTrue(BooleanUtils.toBoolean("TRue"));
    }

    @Test
    public void test_toBoolean_String_22() {
        assertTrue(BooleanUtils.toBoolean("TrUE"));
    }

    @Test
    public void test_toBoolean_String_23() {
        assertTrue(BooleanUtils.toBoolean("TrUe"));
    }

    @Test
    public void test_toBoolean_String_24() {
        assertTrue(BooleanUtils.toBoolean("TruE"));
    }

    @Test
    public void test_toBoolean_String_25() {
        assertTrue(BooleanUtils.toBoolean("True"));
    }

    @Test
    public void test_toBoolean_String_26() {
        assertTrue(BooleanUtils.toBoolean("on"));
    }

    @Test
    public void test_toBoolean_String_27() {
        assertTrue(BooleanUtils.toBoolean("oN"));
    }

    @Test
    public void test_toBoolean_String_28() {
        assertTrue(BooleanUtils.toBoolean("On"));
    }

    @Test
    public void test_toBoolean_String_29() {
        assertTrue(BooleanUtils.toBoolean("ON"));
    }

    @Test
    public void test_toBoolean_String_30() {
        assertTrue(BooleanUtils.toBoolean("yes"));
    }

    @Test
    public void test_toBoolean_String_31() {
        assertTrue(BooleanUtils.toBoolean("yeS"));
    }

    @Test
    public void test_toBoolean_String_32() {
        assertTrue(BooleanUtils.toBoolean("yEs"));
    }

    @Test
    public void test_toBoolean_String_33() {
        assertTrue(BooleanUtils.toBoolean("yES"));
    }

    @Test
    public void test_toBoolean_String_34() {
        assertTrue(BooleanUtils.toBoolean("Yes"));
    }

    @Test
    public void test_toBoolean_String_35() {
        assertTrue(BooleanUtils.toBoolean("YeS"));
    }

    @Test
    public void test_toBoolean_String_36() {
        assertTrue(BooleanUtils.toBoolean("YEs"));
    }

    @Test
    public void test_toBoolean_String_37() {
        assertTrue(BooleanUtils.toBoolean("YES"));
    }

    @Test
    public void test_toBoolean_String_38() {
        assertTrue(BooleanUtils.toBoolean("1"));
    }

    @Test
    public void test_toBoolean_String_39() {
        assertFalse(BooleanUtils.toBoolean("yes?"));
    }

    @Test
    public void test_toBoolean_String_40() {
        assertFalse(BooleanUtils.toBoolean("0"));
    }

    @Test
    public void test_toBoolean_String_41() {
        assertFalse(BooleanUtils.toBoolean("tru"));
    }

    @Test
    public void test_toBoolean_String_42() {
        assertFalse(BooleanUtils.toBoolean("no"));
    }

    @Test
    public void test_toBoolean_String_43() {
        assertFalse(BooleanUtils.toBoolean("off"));
    }

    @Test
    public void test_toBoolean_String_44() {
        assertFalse(BooleanUtils.toBoolean("yoo"));
    }

    @Test
    public void test_toBoolean_String_String_String_1() {
        assertTrue(BooleanUtils.toBoolean(null, null, "N"));
    }

    @Test
    public void test_toBoolean_String_String_String_2() {
        assertFalse(BooleanUtils.toBoolean(null, "Y", null));
    }

    @Test
    public void test_toBoolean_String_String_String_3() {
        assertTrue(BooleanUtils.toBoolean("Y", "Y", "N"));
    }

    @Test
    public void test_toBoolean_String_String_String_4() {
        assertTrue(BooleanUtils.toBoolean("Y", "Y", "N"));
    }

    @Test
    public void test_toBoolean_String_String_String_5() {
        assertFalse(BooleanUtils.toBoolean("N", "Y", "N"));
    }

    @Test
    public void test_toBoolean_String_String_String_6() {
        assertFalse(BooleanUtils.toBoolean("N", "Y", "N"));
    }

    @Test
    public void test_toBoolean_String_String_String_7() {
        assertTrue(BooleanUtils.toBoolean((String) null, null, null));
    }

    @Test
    public void test_toBoolean_String_String_String_8() {
        assertTrue(BooleanUtils.toBoolean("Y", "Y", "Y"));
    }

    @Test
    public void test_toBoolean_String_String_String_9() {
        assertTrue(BooleanUtils.toBoolean("Y", "Y", "Y"));
    }

    @Test
    public void test_toBooleanDefaultIfNull_Boolean_boolean_1() {
        assertTrue(BooleanUtils.toBooleanDefaultIfNull(Boolean.TRUE, true));
    }

    @Test
    public void test_toBooleanDefaultIfNull_Boolean_boolean_2() {
        assertTrue(BooleanUtils.toBooleanDefaultIfNull(Boolean.TRUE, false));
    }

    @Test
    public void test_toBooleanDefaultIfNull_Boolean_boolean_3() {
        assertFalse(BooleanUtils.toBooleanDefaultIfNull(Boolean.FALSE, true));
    }

    @Test
    public void test_toBooleanDefaultIfNull_Boolean_boolean_4() {
        assertFalse(BooleanUtils.toBooleanDefaultIfNull(Boolean.FALSE, false));
    }

    @Test
    public void test_toBooleanDefaultIfNull_Boolean_boolean_5() {
        assertTrue(BooleanUtils.toBooleanDefaultIfNull(null, true));
    }

    @Test
    public void test_toBooleanDefaultIfNull_Boolean_boolean_6() {
        assertFalse(BooleanUtils.toBooleanDefaultIfNull(null, false));
    }

    @Test
    public void test_toBooleanObject_int_1() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject(1));
    }

    @Test
    public void test_toBooleanObject_int_2() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject(-1));
    }

    @Test
    public void test_toBooleanObject_int_3() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject(0));
    }

    @Test
    public void test_toBooleanObject_int_int_int_1() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject(6, 6, 7, 8));
    }

    @Test
    public void test_toBooleanObject_int_int_int_2() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject(7, 6, 7, 8));
    }

    @Test
    public void test_toBooleanObject_int_int_int_3() {
        assertNull(BooleanUtils.toBooleanObject(8, 6, 7, 8));
    }

    @Test
    public void test_toBooleanObject_Integer_1() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject(Integer.valueOf(1)));
    }

    @Test
    public void test_toBooleanObject_Integer_2() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject(Integer.valueOf(-1)));
    }

    @Test
    public void test_toBooleanObject_Integer_3() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject(Integer.valueOf(0)));
    }

    @Test
    public void test_toBooleanObject_Integer_4() {
        assertNull(BooleanUtils.toBooleanObject((Integer) null));
    }

    @Test
    public void test_toBooleanObject_String_1() {
        assertNull(BooleanUtils.toBooleanObject((String) null));
    }

    @Test
    public void test_toBooleanObject_String_2() {
        assertNull(BooleanUtils.toBooleanObject(""));
    }

    @Test
    public void test_toBooleanObject_String_3() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("false"));
    }

    @Test
    public void test_toBooleanObject_String_4() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("no"));
    }

    @Test
    public void test_toBooleanObject_String_5() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("off"));
    }

    @Test
    public void test_toBooleanObject_String_6() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("FALSE"));
    }

    @Test
    public void test_toBooleanObject_String_7() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("NO"));
    }

    @Test
    public void test_toBooleanObject_String_8() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("OFF"));
    }

    @Test
    public void test_toBooleanObject_String_9() {
        assertNull(BooleanUtils.toBooleanObject("oof"));
    }

    @Test
    public void test_toBooleanObject_String_10() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("true"));
    }

    @Test
    public void test_toBooleanObject_String_11() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("yes"));
    }

    @Test
    public void test_toBooleanObject_String_12() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("on"));
    }

    @Test
    public void test_toBooleanObject_String_13() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("TRUE"));
    }

    @Test
    public void test_toBooleanObject_String_14() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("ON"));
    }

    @Test
    public void test_toBooleanObject_String_15() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("YES"));
    }

    @Test
    public void test_toBooleanObject_String_16() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("TruE"));
    }

    @Test
    public void test_toBooleanObject_String_17() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("TruE"));
    }

    @Test
    public void test_toBooleanObject_String_18() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("y"));
    }

    @Test
    public void test_toBooleanObject_String_19() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("Y"));
    }

    @Test
    public void test_toBooleanObject_String_20() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("t"));
    }

    @Test
    public void test_toBooleanObject_String_21() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("T"));
    }

    @Test
    public void test_toBooleanObject_String_22() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("1"));
    }

    @Test
    public void test_toBooleanObject_String_23() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("f"));
    }

    @Test
    public void test_toBooleanObject_String_24() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("F"));
    }

    @Test
    public void test_toBooleanObject_String_25() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("n"));
    }

    @Test
    public void test_toBooleanObject_String_26() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("N"));
    }

    @Test
    public void test_toBooleanObject_String_27() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("0"));
    }

    @Test
    public void test_toBooleanObject_String_28() {
        assertNull(BooleanUtils.toBooleanObject("z"));
    }

    @Test
    public void test_toBooleanObject_String_29() {
        assertNull(BooleanUtils.toBooleanObject("ab"));
    }

    @Test
    public void test_toBooleanObject_String_30() {
        assertNull(BooleanUtils.toBooleanObject("yoo"));
    }

    @Test
    public void test_toBooleanObject_String_31() {
        assertNull(BooleanUtils.toBooleanObject("true "));
    }

    @Test
    public void test_toBooleanObject_String_32() {
        assertNull(BooleanUtils.toBooleanObject("ono"));
    }

    @Test
    public void test_toBooleanObject_String_String_String_String_1() {
        assertSame(Boolean.TRUE, BooleanUtils.toBooleanObject(null, null, "N", "U"));
    }

    @Test
    public void test_toBooleanObject_String_String_String_String_2() {
        assertSame(Boolean.FALSE, BooleanUtils.toBooleanObject(null, "Y", null, "U"));
    }

    @Test
    public void test_toBooleanObject_String_String_String_String_3() {
        assertSame(null, BooleanUtils.toBooleanObject(null, "Y", "N", null));
    }

    @Test
    public void test_toBooleanObject_String_String_String_String_4() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("Y", "Y", "N", "U"));
    }

    @Test
    public void test_toBooleanObject_String_String_String_String_5() {
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("N", "Y", "N", "U"));
    }

    @Test
    public void test_toBooleanObject_String_String_String_String_6() {
        assertNull(BooleanUtils.toBooleanObject("U", "Y", "N", "U"));
    }

    @Test
    public void test_toInteger_boolean_1() {
        assertEquals(1, BooleanUtils.toInteger(true));
    }

    @Test
    public void test_toInteger_boolean_2() {
        assertEquals(0, BooleanUtils.toInteger(false));
    }

    @Test
    public void test_toInteger_boolean_int_int_1() {
        assertEquals(6, BooleanUtils.toInteger(true, 6, 7));
    }

    @Test
    public void test_toInteger_boolean_int_int_2() {
        assertEquals(7, BooleanUtils.toInteger(false, 6, 7));
    }

    @Test
    public void test_toInteger_Boolean_int_int_int_1() {
        assertEquals(6, BooleanUtils.toInteger(Boolean.TRUE, 6, 7, 8));
    }

    @Test
    public void test_toInteger_Boolean_int_int_int_2() {
        assertEquals(7, BooleanUtils.toInteger(Boolean.FALSE, 6, 7, 8));
    }

    @Test
    public void test_toInteger_Boolean_int_int_int_3() {
        assertEquals(8, BooleanUtils.toInteger(null, 6, 7, 8));
    }

    @Test
    public void test_toIntegerObject_boolean_1() {
        assertEquals(Integer.valueOf(1), BooleanUtils.toIntegerObject(true));
    }

    @Test
    public void test_toIntegerObject_boolean_2() {
        assertEquals(Integer.valueOf(0), BooleanUtils.toIntegerObject(false));
    }

    @Test
    public void test_toIntegerObject_Boolean_1() {
        assertEquals(Integer.valueOf(1), BooleanUtils.toIntegerObject(Boolean.TRUE));
    }

    @Test
    public void test_toIntegerObject_Boolean_2() {
        assertEquals(Integer.valueOf(0), BooleanUtils.toIntegerObject(Boolean.FALSE));
    }

    @Test
    public void test_toIntegerObject_Boolean_3() {
        assertNull(BooleanUtils.toIntegerObject(null));
    }

    @Test
    public void test_toString_boolean_String_String_String_1() {
        assertEquals("Y", BooleanUtils.toString(true, "Y", "N"));
    }

    @Test
    public void test_toString_boolean_String_String_String_2() {
        assertEquals("N", BooleanUtils.toString(false, "Y", "N"));
    }

    @Test
    public void test_toString_Boolean_String_String_String_1() {
        assertEquals("U", BooleanUtils.toString(null, "Y", "N", "U"));
    }

    @Test
    public void test_toString_Boolean_String_String_String_2() {
        assertEquals("Y", BooleanUtils.toString(Boolean.TRUE, "Y", "N", "U"));
    }

    @Test
    public void test_toString_Boolean_String_String_String_3() {
        assertEquals("N", BooleanUtils.toString(Boolean.FALSE, "Y", "N", "U"));
    }

    @Test
    public void test_toStringOnOff_boolean_1() {
        assertEquals("on", BooleanUtils.toStringOnOff(true));
    }

    @Test
    public void test_toStringOnOff_boolean_2() {
        assertEquals("off", BooleanUtils.toStringOnOff(false));
    }

    @Test
    public void test_toStringOnOff_Boolean_1() {
        assertNull(BooleanUtils.toStringOnOff(null));
    }

    @Test
    public void test_toStringOnOff_Boolean_2() {
        assertEquals("on", BooleanUtils.toStringOnOff(Boolean.TRUE));
    }

    @Test
    public void test_toStringOnOff_Boolean_3() {
        assertEquals("off", BooleanUtils.toStringOnOff(Boolean.FALSE));
    }

    @Test
    public void test_toStringTrueFalse_boolean_1() {
        assertEquals("true", BooleanUtils.toStringTrueFalse(true));
    }

    @Test
    public void test_toStringTrueFalse_boolean_2() {
        assertEquals("false", BooleanUtils.toStringTrueFalse(false));
    }

    @Test
    public void test_toStringTrueFalse_Boolean_1() {
        assertNull(BooleanUtils.toStringTrueFalse(null));
    }

    @Test
    public void test_toStringTrueFalse_Boolean_2() {
        assertEquals("true", BooleanUtils.toStringTrueFalse(Boolean.TRUE));
    }

    @Test
    public void test_toStringTrueFalse_Boolean_3() {
        assertEquals("false", BooleanUtils.toStringTrueFalse(Boolean.FALSE));
    }

    @Test
    public void test_toStringYesNo_boolean_1() {
        assertEquals("yes", BooleanUtils.toStringYesNo(true));
    }

    @Test
    public void test_toStringYesNo_boolean_2() {
        assertEquals("no", BooleanUtils.toStringYesNo(false));
    }

    @Test
    public void test_toStringYesNo_Boolean_1() {
        assertNull(BooleanUtils.toStringYesNo(null));
    }

    @Test
    public void test_toStringYesNo_Boolean_2() {
        assertEquals("yes", BooleanUtils.toStringYesNo(Boolean.TRUE));
    }

    @Test
    public void test_toStringYesNo_Boolean_3() {
        assertEquals("no", BooleanUtils.toStringYesNo(Boolean.FALSE));
    }

    @Test
    public void testCompare_1() {
        assertTrue(BooleanUtils.compare(true, false) > 0);
    }

    @Test
    public void testCompare_2() {
        assertEquals(0, BooleanUtils.compare(true, true));
    }

    @Test
    public void testCompare_3() {
        assertEquals(0, BooleanUtils.compare(false, false));
    }

    @Test
    public void testCompare_4() {
        assertTrue(BooleanUtils.compare(false, true) < 0);
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new BooleanUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = BooleanUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(BooleanUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(BooleanUtils.class.getModifiers()));
    }

    @Test
    public void testOneHot_object_validInput_2ItemsNullsTreatedAsFalse_1() {
        assertFalse(BooleanUtils.oneHot(null, null), "both null");
    }

    @Test
    public void testOneHot_object_validInput_2ItemsNullsTreatedAsFalse_2() {
        assertTrue(BooleanUtils.oneHot(true, null), "first true");
    }

    @Test
    public void testOneHot_object_validInput_2ItemsNullsTreatedAsFalse_3() {
        assertTrue(BooleanUtils.oneHot(null, true), "last true");
    }
}
