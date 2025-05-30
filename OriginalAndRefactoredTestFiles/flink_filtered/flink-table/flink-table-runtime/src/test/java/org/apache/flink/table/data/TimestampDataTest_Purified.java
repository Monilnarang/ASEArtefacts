package org.apache.flink.table.data;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import static org.assertj.core.api.Assertions.assertThat;

class TimestampDataTest_Purified {

    @Test
    void testNormal_1() {
        assertThat(TimestampData.fromEpochMillis(1123L).getMillisecond()).isEqualTo(1123L);
    }

    @Test
    void testNormal_2() {
        assertThat(TimestampData.fromEpochMillis(-1123L).getMillisecond()).isEqualTo(-1123L);
    }

    @Test
    void testNormal_3() {
        assertThat(TimestampData.fromEpochMillis(1123L, 45678).getMillisecond()).isEqualTo(1123L);
    }

    @Test
    void testNormal_20_testMerged_4() {
        Instant instant1 = Instant.ofEpochMilli(123L);
        Instant instant2 = Instant.ofEpochSecond(0L, 123456789L);
        Instant instant3 = Instant.ofEpochSecond(-2L, 123456789L);
        assertThat(TimestampData.fromInstant(instant1).toInstant()).isEqualTo(instant1);
        assertThat(TimestampData.fromInstant(instant2).toInstant()).isEqualTo(instant2);
        assertThat(TimestampData.fromInstant(instant3).toInstant()).isEqualTo(instant3);
    }

    @Test
    void testNormal_4() {
        assertThat(TimestampData.fromEpochMillis(1123L, 45678).getNanoOfMillisecond()).isEqualTo(45678);
    }

    @Test
    void testNormal_5() {
        assertThat(TimestampData.fromEpochMillis(-1123L, 45678).getMillisecond()).isEqualTo(-1123L);
    }

    @Test
    void testNormal_6() {
        assertThat(TimestampData.fromEpochMillis(-1123L, 45678).getNanoOfMillisecond()).isEqualTo(45678);
    }

    @Test
    void testNormal_12() {
        java.sql.Timestamp t3 = new java.sql.Timestamp(1572333940000L);
        assertThat(TimestampData.fromTimestamp(t3).toTimestamp()).isEqualTo(t3);
    }

    @Test
    void testNormal_7_testMerged_9() {
        java.sql.Timestamp t19 = java.sql.Timestamp.valueOf("1969-01-02 00:00:00.123456789");
        java.sql.Timestamp t16 = java.sql.Timestamp.valueOf("1969-01-02 00:00:00.123456");
        java.sql.Timestamp t13 = java.sql.Timestamp.valueOf("1969-01-02 00:00:00.123");
        java.sql.Timestamp t10 = java.sql.Timestamp.valueOf("1969-01-02 00:00:00");
        assertThat(TimestampData.fromTimestamp(t19).toTimestamp()).isEqualTo(t19);
        assertThat(TimestampData.fromTimestamp(t16).toTimestamp()).isEqualTo(t16);
        assertThat(TimestampData.fromTimestamp(t13).toTimestamp()).isEqualTo(t13);
        assertThat(TimestampData.fromTimestamp(t10).toTimestamp()).isEqualTo(t10);
        java.sql.Timestamp t2 = java.sql.Timestamp.valueOf("1979-01-02 00:00:00.123456");
        assertThat(TimestampData.fromTimestamp(t2).toTimestamp()).isEqualTo(t2);
        LocalDateTime ldt19 = LocalDateTime.of(1969, 1, 2, 0, 0, 0, 123456789);
        LocalDateTime ldt16 = LocalDateTime.of(1969, 1, 2, 0, 0, 0, 123456000);
        LocalDateTime ldt13 = LocalDateTime.of(1969, 1, 2, 0, 0, 0, 123000000);
        LocalDateTime ldt10 = LocalDateTime.of(1969, 1, 2, 0, 0, 0, 0);
        assertThat(TimestampData.fromLocalDateTime(ldt19).toLocalDateTime()).isEqualTo(ldt19);
        assertThat(TimestampData.fromLocalDateTime(ldt16).toLocalDateTime()).isEqualTo(ldt16);
        assertThat(TimestampData.fromLocalDateTime(ldt13).toLocalDateTime()).isEqualTo(ldt13);
        assertThat(TimestampData.fromLocalDateTime(ldt10).toLocalDateTime()).isEqualTo(ldt10);
        LocalDateTime ldt2 = LocalDateTime.of(1969, 1, 2, 0, 0, 0, 0);
        assertThat(TimestampData.fromLocalDateTime(ldt2).toLocalDateTime()).isEqualTo(ldt2);
        LocalDateTime ldt3 = LocalDateTime.of(1989, 1, 2, 0, 0, 0, 123456789);
        assertThat(TimestampData.fromLocalDateTime(ldt3).toLocalDateTime()).isEqualTo(ldt3);
        LocalDateTime ldt4 = LocalDateTime.of(1989, 1, 2, 0, 0, 0, 123456789);
        java.sql.Timestamp t4 = java.sql.Timestamp.valueOf(ldt4);
        assertThat(TimestampData.fromTimestamp(t4)).isEqualTo(TimestampData.fromLocalDateTime(ldt4));
    }
}
