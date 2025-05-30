package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

public class CharRangeTest_Purified extends AbstractLangTest {

    @Test
    public void testClass_1() {
        assertFalse(Modifier.isPublic(CharRange.class.getModifiers()));
    }

    @Test
    public void testClass_2() {
        assertTrue(Modifier.isFinal(CharRange.class.getModifiers()));
    }
}
