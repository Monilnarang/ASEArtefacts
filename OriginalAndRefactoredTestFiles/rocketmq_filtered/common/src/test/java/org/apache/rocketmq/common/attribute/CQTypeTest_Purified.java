package org.apache.rocketmq.common.attribute;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CQTypeTest_Purified {

    @Test
    public void testValueOf_1() {
        assertEquals(CQType.SimpleCQ, CQType.valueOf("SimpleCQ"));
    }

    @Test
    public void testValueOf_2() {
        assertEquals(CQType.BatchCQ, CQType.valueOf("BatchCQ"));
    }

    @Test
    public void testValueOf_3() {
        assertEquals(CQType.RocksDBCQ, CQType.valueOf("RocksDBCQ"));
    }
}
