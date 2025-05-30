package org.apache.storm.jdbc.common;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilTest_Purified {

    @Test
    public void testBasic_1() {
        assertEquals(String.class, Util.getJavaType(Types.CHAR));
    }

    @Test
    public void testBasic_2() {
        assertEquals(String.class, Util.getJavaType(Types.VARCHAR));
    }

    @Test
    public void testBasic_3() {
        assertEquals(String.class, Util.getJavaType(Types.LONGVARCHAR));
    }

    @Test
    public void testBasic_4() {
        assertEquals(byte[].class, Util.getJavaType(Types.BINARY));
    }

    @Test
    public void testBasic_5() {
        assertEquals(byte[].class, Util.getJavaType(Types.VARBINARY));
    }

    @Test
    public void testBasic_6() {
        assertEquals(byte[].class, Util.getJavaType(Types.LONGVARBINARY));
    }

    @Test
    public void testBasic_7() {
        assertEquals(Boolean.class, Util.getJavaType(Types.BIT));
    }

    @Test
    public void testBasic_8() {
        assertEquals(Short.class, Util.getJavaType(Types.TINYINT));
    }

    @Test
    public void testBasic_9() {
        assertEquals(Short.class, Util.getJavaType(Types.SMALLINT));
    }

    @Test
    public void testBasic_10() {
        assertEquals(Integer.class, Util.getJavaType(Types.INTEGER));
    }

    @Test
    public void testBasic_11() {
        assertEquals(Long.class, Util.getJavaType(Types.BIGINT));
    }

    @Test
    public void testBasic_12() {
        assertEquals(Float.class, Util.getJavaType(Types.REAL));
    }

    @Test
    public void testBasic_13() {
        assertEquals(Double.class, Util.getJavaType(Types.DOUBLE));
    }

    @Test
    public void testBasic_14() {
        assertEquals(Double.class, Util.getJavaType(Types.FLOAT));
    }

    @Test
    public void testBasic_15() {
        assertEquals(Date.class, Util.getJavaType(Types.DATE));
    }

    @Test
    public void testBasic_16() {
        assertEquals(Time.class, Util.getJavaType(Types.TIME));
    }

    @Test
    public void testBasic_17() {
        assertEquals(Timestamp.class, Util.getJavaType(Types.TIMESTAMP));
    }
}
