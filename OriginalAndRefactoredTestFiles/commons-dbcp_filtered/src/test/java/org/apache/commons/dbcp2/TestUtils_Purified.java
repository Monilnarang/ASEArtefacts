package org.apache.commons.dbcp2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class TestUtils_Purified {

    public static PStmtKey getPStmtKey(final PoolablePreparedStatement<PStmtKey> poolablePreparedStatement) {
        return poolablePreparedStatement.getKey();
    }

    @Test
    public void testIsDisconnectionSqlCode_1() {
        assertTrue(Utils.isDisconnectionSqlCode("57P01"), "57P01 should be recognised as a disconnection SQL code.");
    }

    @Test
    public void testIsDisconnectionSqlCode_2() {
        assertTrue(Utils.isDisconnectionSqlCode("01002"), "01002 should be recognised as a disconnection SQL code.");
    }

    @Test
    public void testIsDisconnectionSqlCode_3() {
        assertTrue(Utils.isDisconnectionSqlCode("JZ0C0"), "JZ0C0 should be recognised as a disconnection SQL code.");
    }

    @Test
    public void testIsDisconnectionSqlCode_4() {
        assertFalse(Utils.isDisconnectionSqlCode("INVALID"), "INVALID should not be recognised as a disconnection SQL code.");
    }

    @Test
    public void testIsDisconnectionSqlCode_5() {
        assertFalse(Utils.isDisconnectionSqlCode("00000"), "00000 should not be recognised as a disconnection SQL code.");
    }
}
