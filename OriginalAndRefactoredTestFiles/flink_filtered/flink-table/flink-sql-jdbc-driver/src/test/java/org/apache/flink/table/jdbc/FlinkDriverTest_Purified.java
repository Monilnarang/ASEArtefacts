package org.apache.flink.table.jdbc;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import static org.apache.flink.table.jdbc.DriverInfo.DRIVER_NAME;
import static org.apache.flink.table.jdbc.DriverInfo.DRIVER_VERSION_MAJOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlinkDriverTest_Purified {

    @Test
    public void testDriverInfo_1() {
        assertEquals(DRIVER_VERSION_MAJOR, 2);
    }

    @Test
    public void testDriverInfo_2() {
        assertEquals(DRIVER_NAME, "Flink JDBC Driver");
    }
}
