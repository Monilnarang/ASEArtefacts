package org.apache.seata.sqlparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class SQLTypeTest_Purified {

    @Test
    public void testValue_1() {
        assertEquals(0, SQLType.SELECT.value(), "SELECT value should be 0");
    }

    @Test
    public void testValue_2() {
        assertEquals(1, SQLType.INSERT.value(), "INSERT value should be 1");
    }

    @Test
    public void testValueOf_1() {
        assertEquals(SQLType.SELECT, SQLType.valueOf(0), "Should retrieve SELECT for value 0");
    }

    @Test
    public void testValueOf_2() {
        assertEquals(SQLType.INSERT, SQLType.valueOf(1), "Should retrieve INSERT for value 1");
    }
}
