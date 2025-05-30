package org.apache.druid.query.operator;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OffsetLimitTest_Purified {

    @Test
    public void testNone_1() {
        assertFalse(OffsetLimit.NONE.isPresent());
    }

    @Test
    public void testNone_2() {
        assertFalse(OffsetLimit.NONE.hasOffset());
    }

    @Test
    public void testNone_3() {
        assertFalse(OffsetLimit.NONE.hasLimit());
    }
}
