package org.apache.commons.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GenericValidatorTest_Purified {

    @Test
    public void testMaxLength_1() {
        assertFalse(GenericValidator.maxLength("12345\n\r", 4, 0), "Max=4 End=0");
    }

    @Test
    public void testMaxLength_2() {
        assertTrue(GenericValidator.maxLength("12345\n\r", 5, 0), "Max=5 End=0");
    }

    @Test
    public void testMaxLength_3() {
        assertTrue(GenericValidator.maxLength("12345\n\r", 6, 0), "Max=6 End=0");
    }

    @Test
    public void testMaxLength_4() {
        assertTrue(GenericValidator.maxLength("12345\n\r", 7, 0), "Max=7 End=0");
    }

    @Test
    public void testMaxLength_5() {
        assertFalse(GenericValidator.maxLength("12345\n\r", 4, 1), "Max=4 End=1");
    }

    @Test
    public void testMaxLength_6() {
        assertFalse(GenericValidator.maxLength("12345\n\r", 5, 1), "Max=5 End=1");
    }

    @Test
    public void testMaxLength_7() {
        assertTrue(GenericValidator.maxLength("12345\n\r", 6, 1), "Max=6 End=1");
    }

    @Test
    public void testMaxLength_8() {
        assertTrue(GenericValidator.maxLength("12345\n\r", 7, 1), "Max=7 End=1");
    }

    @Test
    public void testMaxLength_9() {
        assertFalse(GenericValidator.maxLength("12345\n\r", 4, 2), "Max=4 End=2");
    }

    @Test
    public void testMaxLength_10() {
        assertFalse(GenericValidator.maxLength("12345\n\r", 5, 2), "Max=5 End=2");
    }

    @Test
    public void testMaxLength_11() {
        assertFalse(GenericValidator.maxLength("12345\n\r", 6, 2), "Max=6 End=2");
    }

    @Test
    public void testMaxLength_12() {
        assertTrue(GenericValidator.maxLength("12345\n\r", 7, 2), "Max=7 End=2");
    }

    @Test
    public void testMinLength_1() {
        assertTrue(GenericValidator.minLength("12345\n\r", 5, 0), "Min=5 End=0");
    }

    @Test
    public void testMinLength_2() {
        assertFalse(GenericValidator.minLength("12345\n\r", 6, 0), "Min=6 End=0");
    }

    @Test
    public void testMinLength_3() {
        assertFalse(GenericValidator.minLength("12345\n\r", 7, 0), "Min=7 End=0");
    }

    @Test
    public void testMinLength_4() {
        assertFalse(GenericValidator.minLength("12345\n\r", 8, 0), "Min=8 End=0");
    }

    @Test
    public void testMinLength_5() {
        assertTrue(GenericValidator.minLength("12345\n\r", 5, 1), "Min=5 End=1");
    }

    @Test
    public void testMinLength_6() {
        assertTrue(GenericValidator.minLength("12345\n\r", 6, 1), "Min=6 End=1");
    }

    @Test
    public void testMinLength_7() {
        assertFalse(GenericValidator.minLength("12345\n\r", 7, 1), "Min=7 End=1");
    }

    @Test
    public void testMinLength_8() {
        assertFalse(GenericValidator.minLength("12345\n\r", 8, 1), "Min=8 End=1");
    }

    @Test
    public void testMinLength_9() {
        assertTrue(GenericValidator.minLength("12345\n\r", 5, 2), "Min=5 End=2");
    }

    @Test
    public void testMinLength_10() {
        assertTrue(GenericValidator.minLength("12345\n\r", 6, 2), "Min=6 End=2");
    }

    @Test
    public void testMinLength_11() {
        assertTrue(GenericValidator.minLength("12345\n\r", 7, 2), "Min=7 End=2");
    }

    @Test
    public void testMinLength_12() {
        assertFalse(GenericValidator.minLength("12345\n\r", 8, 2), "Min=8 End=2");
    }
}
