package org.activiti.core.el.juel.misc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import org.activiti.core.el.juel.test.TestCase;
import org.junit.jupiter.api.Test;

public class BooleanOperationsTest_Purified extends TestCase {

    static enum Foo {

        BAR, BAZ
    }

    private TypeConverter converter = TypeConverter.DEFAULT;

    @Test
    public void testEmpty_1() {
        assertTrue(BooleanOperations.empty(converter, null));
    }

    @Test
    public void testEmpty_2() {
        assertTrue(BooleanOperations.empty(converter, ""));
    }

    @Test
    public void testEmpty_3() {
        assertTrue(BooleanOperations.empty(converter, new Object[0]));
    }

    @Test
    public void testEmpty_4() {
        assertTrue(BooleanOperations.empty(converter, new HashMap<Object, Object>()));
    }

    @Test
    public void testEmpty_5() {
        assertTrue(BooleanOperations.empty(converter, new ArrayList<Object>()));
    }

    @Test
    public void testEmpty_6() {
        assertFalse(BooleanOperations.empty(converter, "foo"));
    }
}
