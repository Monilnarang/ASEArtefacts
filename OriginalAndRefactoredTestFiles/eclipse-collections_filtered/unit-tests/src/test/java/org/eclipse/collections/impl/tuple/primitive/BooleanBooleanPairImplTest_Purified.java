package org.eclipse.collections.impl.tuple.primitive;

import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanBooleanPairImplTest_Purified {

    @Test
    public void testEqualsAndHashCode_1() {
        Verify.assertEqualsAndHashCode(PrimitiveTuples.pair(true, false), PrimitiveTuples.pair(true, false));
    }

    @Test
    public void testEqualsAndHashCode_2() {
        assertNotEquals(PrimitiveTuples.pair(false, true), PrimitiveTuples.pair(true, false));
    }

    @Test
    public void testEqualsAndHashCode_3() {
        assertEquals(Tuples.pair(true, false).hashCode(), PrimitiveTuples.pair(true, false).hashCode());
    }

    @Test
    public void getOne_1() {
        assertTrue(PrimitiveTuples.pair(true, false).getOne());
    }

    @Test
    public void getOne_2() {
        assertFalse(PrimitiveTuples.pair(false, true).getOne());
    }

    @Test
    public void getTwo_1() {
        assertTrue(PrimitiveTuples.pair(false, true).getTwo());
    }

    @Test
    public void getTwo_2() {
        assertFalse(PrimitiveTuples.pair(true, false).getTwo());
    }

    @Test
    public void testToString_1() {
        assertEquals("true:false", PrimitiveTuples.pair(true, false).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("true:true", PrimitiveTuples.pair(true, true).toString());
    }

    @Test
    public void compareTo_1() {
        assertEquals(1, PrimitiveTuples.pair(true, false).compareTo(PrimitiveTuples.pair(false, false)));
    }

    @Test
    public void compareTo_2() {
        assertEquals(0, PrimitiveTuples.pair(true, false).compareTo(PrimitiveTuples.pair(true, false)));
    }

    @Test
    public void compareTo_3() {
        assertEquals(-1, PrimitiveTuples.pair(true, false).compareTo(PrimitiveTuples.pair(true, true)));
    }
}
