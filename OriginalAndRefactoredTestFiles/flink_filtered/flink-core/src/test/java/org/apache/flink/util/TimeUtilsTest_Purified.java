package org.apache.flink.util;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeUtilsTest_Purified {

    @Test
    void testParseDurationNanos_1() {
        assertThat(TimeUtils.parseDuration("424562ns").getNano()).isEqualTo(424562);
    }

    @Test
    void testParseDurationNanos_2() {
        assertThat(TimeUtils.parseDuration("424562nano").getNano()).isEqualTo(424562);
    }

    @Test
    void testParseDurationNanos_3() {
        assertThat(TimeUtils.parseDuration("424562nanos").getNano()).isEqualTo(424562);
    }

    @Test
    void testParseDurationNanos_4() {
        assertThat(TimeUtils.parseDuration("424562nanosecond").getNano()).isEqualTo(424562);
    }

    @Test
    void testParseDurationNanos_5() {
        assertThat(TimeUtils.parseDuration("424562nanoseconds").getNano()).isEqualTo(424562);
    }

    @Test
    void testParseDurationNanos_6() {
        assertThat(TimeUtils.parseDuration("424562 ns").getNano()).isEqualTo(424562);
    }

    @Test
    void testParseDurationNanos_7() {
        assertThat(TimeUtils.parseDuration("9223372036854775807000001 ns")).isEqualByComparingTo(Duration.ofMillis(9223372036854775807L).plusNanos(1));
    }

    @Test
    void testParseDurationMicros_1() {
        assertThat(TimeUtils.parseDuration("565731µs").getNano()).isEqualTo(565731 * 1000L);
    }

    @Test
    void testParseDurationMicros_2() {
        assertThat(TimeUtils.parseDuration("565731micro").getNano()).isEqualTo(565731 * 1000L);
    }

    @Test
    void testParseDurationMicros_3() {
        assertThat(TimeUtils.parseDuration("565731micros").getNano()).isEqualTo(565731 * 1000L);
    }

    @Test
    void testParseDurationMicros_4() {
        assertThat(TimeUtils.parseDuration("565731microsecond").getNano()).isEqualTo(565731 * 1000L);
    }

    @Test
    void testParseDurationMicros_5() {
        assertThat(TimeUtils.parseDuration("565731microseconds").getNano()).isEqualTo(565731 * 1000L);
    }

    @Test
    void testParseDurationMicros_6() {
        assertThat(TimeUtils.parseDuration("565731 µs").getNano()).isEqualTo(565731 * 1000L);
    }

    @Test
    void testParseDurationMillis_1() {
        assertThat(TimeUtils.parseDuration("1234").toMillis()).isEqualTo(1234);
    }

    @Test
    void testParseDurationMillis_2() {
        assertThat(TimeUtils.parseDuration("1234ms").toMillis()).isEqualTo(1234);
    }

    @Test
    void testParseDurationMillis_3() {
        assertThat(TimeUtils.parseDuration("1234milli").toMillis()).isEqualTo(1234);
    }

    @Test
    void testParseDurationMillis_4() {
        assertThat(TimeUtils.parseDuration("1234millis").toMillis()).isEqualTo(1234);
    }

    @Test
    void testParseDurationMillis_5() {
        assertThat(TimeUtils.parseDuration("1234millisecond").toMillis()).isEqualTo(1234);
    }

    @Test
    void testParseDurationMillis_6() {
        assertThat(TimeUtils.parseDuration("1234milliseconds").toMillis()).isEqualTo(1234);
    }

    @Test
    void testParseDurationMillis_7() {
        assertThat(TimeUtils.parseDuration("1234 ms").toMillis()).isEqualTo(1234);
    }

    @Test
    void testParseDurationSeconds_1() {
        assertThat(TimeUtils.parseDuration("667766s").getSeconds()).isEqualTo(667766);
    }

    @Test
    void testParseDurationSeconds_2() {
        assertThat(TimeUtils.parseDuration("667766sec").getSeconds()).isEqualTo(667766);
    }

    @Test
    void testParseDurationSeconds_3() {
        assertThat(TimeUtils.parseDuration("667766secs").getSeconds()).isEqualTo(667766);
    }

    @Test
    void testParseDurationSeconds_4() {
        assertThat(TimeUtils.parseDuration("667766second").getSeconds()).isEqualTo(667766);
    }

    @Test
    void testParseDurationSeconds_5() {
        assertThat(TimeUtils.parseDuration("667766seconds").getSeconds()).isEqualTo(667766);
    }

    @Test
    void testParseDurationSeconds_6() {
        assertThat(TimeUtils.parseDuration("667766 s").getSeconds()).isEqualTo(667766);
    }

    @Test
    void testParseDurationMinutes_1() {
        assertThat(TimeUtils.parseDuration("7657623m").toMinutes()).isEqualTo(7657623);
    }

    @Test
    void testParseDurationMinutes_2() {
        assertThat(TimeUtils.parseDuration("7657623min").toMinutes()).isEqualTo(7657623);
    }

    @Test
    void testParseDurationMinutes_3() {
        assertThat(TimeUtils.parseDuration("7657623minute").toMinutes()).isEqualTo(7657623);
    }

    @Test
    void testParseDurationMinutes_4() {
        assertThat(TimeUtils.parseDuration("7657623minutes").toMinutes()).isEqualTo(7657623);
    }

    @Test
    void testParseDurationMinutes_5() {
        assertThat(TimeUtils.parseDuration("7657623 min").toMinutes()).isEqualTo(7657623);
    }

    @Test
    void testParseDurationHours_1() {
        assertThat(TimeUtils.parseDuration("987654h").toHours()).isEqualTo(987654);
    }

    @Test
    void testParseDurationHours_2() {
        assertThat(TimeUtils.parseDuration("987654hour").toHours()).isEqualTo(987654);
    }

    @Test
    void testParseDurationHours_3() {
        assertThat(TimeUtils.parseDuration("987654hours").toHours()).isEqualTo(987654);
    }

    @Test
    void testParseDurationHours_4() {
        assertThat(TimeUtils.parseDuration("987654 h").toHours()).isEqualTo(987654);
    }

    @Test
    void testParseDurationDays_1() {
        assertThat(TimeUtils.parseDuration("987654d").toDays()).isEqualTo(987654);
    }

    @Test
    void testParseDurationDays_2() {
        assertThat(TimeUtils.parseDuration("987654day").toDays()).isEqualTo(987654);
    }

    @Test
    void testParseDurationDays_3() {
        assertThat(TimeUtils.parseDuration("987654days").toDays()).isEqualTo(987654);
    }

    @Test
    void testParseDurationDays_4() {
        assertThat(TimeUtils.parseDuration("987654 d").toDays()).isEqualTo(987654);
    }

    @Test
    void testParseDurationUpperCase_1() {
        assertThat(TimeUtils.parseDuration("1 NS").toNanos()).isOne();
    }

    @Test
    void testParseDurationUpperCase_2() {
        assertThat(TimeUtils.parseDuration("1 MICRO").toNanos()).isEqualTo(1000L);
    }

    @Test
    void testParseDurationUpperCase_3() {
        assertThat(TimeUtils.parseDuration("1 MS").toMillis()).isOne();
    }

    @Test
    void testParseDurationUpperCase_4() {
        assertThat(TimeUtils.parseDuration("1 S").getSeconds()).isOne();
    }

    @Test
    void testParseDurationUpperCase_5() {
        assertThat(TimeUtils.parseDuration("1 MIN").toMinutes()).isOne();
    }

    @Test
    void testParseDurationUpperCase_6() {
        assertThat(TimeUtils.parseDuration("1 H").toHours()).isOne();
    }

    @Test
    void testParseDurationUpperCase_7() {
        assertThat(TimeUtils.parseDuration("1 D").toDays()).isOne();
    }

    @Test
    void testParseDurationTrim_1() {
        assertThat(TimeUtils.parseDuration("      155      ").toMillis()).isEqualTo(155L);
    }

    @Test
    void testParseDurationTrim_2() {
        assertThat(TimeUtils.parseDuration("      155      ms   ").toMillis()).isEqualTo(155L);
    }

    @Test
    public void testParseDurationISO8601_1() {
        assertThat(TimeUtils.parseDuration("PT20.345S").toMillis()).isEqualTo(20345);
    }

    @Test
    public void testParseDurationISO8601_2() {
        assertThat(TimeUtils.parseDuration("PT15M").toMinutes()).isEqualTo(15);
    }

    @Test
    public void testParseDurationISO8601_3() {
        assertThat(TimeUtils.parseDuration("PT10H").toHours()).isEqualTo(10);
    }

    @Test
    public void testParseDurationISO8601_4() {
        assertThat(TimeUtils.parseDuration("P2DT3H4M").toMinutes()).isEqualTo(3064);
    }

    @Test
    void testGetStringInMillis_1() {
        assertThat(TimeUtils.getStringInMillis(Duration.ofMillis(4567L))).isEqualTo("4567ms");
    }

    @Test
    void testGetStringInMillis_2() {
        assertThat(TimeUtils.getStringInMillis(Duration.ofSeconds(4567L))).isEqualTo("4567000ms");
    }

    @Test
    void testGetStringInMillis_3() {
        assertThat(TimeUtils.getStringInMillis(Duration.of(4567L, ChronoUnit.MICROS))).isEqualTo("4ms");
    }
}
