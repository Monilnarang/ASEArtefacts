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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestUtils_Parameterized {

    public static PStmtKey getPStmtKey(final PoolablePreparedStatement<PStmtKey> poolablePreparedStatement) {
        return poolablePreparedStatement.getKey();
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsDisconnectionSqlCode_1to3")
    public void testIsDisconnectionSqlCode_1to3(String param1, String param2) {
        assertTrue(Utils.isDisconnectionSqlCode(param2), param1);
    }

    static public Stream<Arguments> Provider_testIsDisconnectionSqlCode_1to3() {
        return Stream.of(arguments("57P01 should be recognised as a disconnection SQL code.", "57P01"), arguments("01002 should be recognised as a disconnection SQL code.", 01002), arguments("JZ0C0 should be recognised as a disconnection SQL code.", "JZ0C0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsDisconnectionSqlCode_4to5")
    public void testIsDisconnectionSqlCode_4to5(String param1, String param2) {
        assertFalse(Utils.isDisconnectionSqlCode(param2), param1);
    }

    static public Stream<Arguments> Provider_testIsDisconnectionSqlCode_4to5() {
        return Stream.of(arguments("INVALID should not be recognised as a disconnection SQL code.", "INVALID"), arguments("00000 should not be recognised as a disconnection SQL code.", 00000));
    }
}
