package org.eclipse.collections.impl.block.function;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.impl.block.factory.StringFunctions;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class StringFunctionsTest_Purified {

    @Test
    public void toInteger_1() {
        assertEquals(-42L, StringFunctions.toInteger().valueOf("-42").longValue());
    }

    @Test
    public void toInteger_2() {
        Verify.assertInstanceOf(Integer.class, StringFunctions.toInteger().valueOf("10"));
    }

    @Test
    public void toPrimitiveBoolean_1() {
        assertTrue(StringFunctions.toPrimitiveBoolean().booleanValueOf("true"));
    }

    @Test
    public void toPrimitiveBoolean_2() {
        assertFalse(StringFunctions.toPrimitiveBoolean().booleanValueOf("nah"));
    }
}
