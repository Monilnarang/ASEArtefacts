package org.apache.flink.util;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TimeUtilsTest_Parameterized {

    @Test
    void testParseDurationNanos_7() {
        assertThat(TimeUtils.parseDuration("9223372036854775807000001 ns")).isEqualByComparingTo(Duration.ofMillis(9223372036854775807L).plusNanos(1));
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

    @ParameterizedTest
    @MethodSource("Provider_testParseDurationNanos_1to6")
    void testParseDurationNanos_1to6(int param1, String param2) {
        assertThat(TimeUtils.parseDuration(param2).getNano()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseDurationNanos_1to6() {
        return Stream.of(arguments(424562, "424562ns"), arguments(424562, "424562nano"), arguments(424562, "424562nanos"), arguments(424562, "424562nanosecond"), arguments(424562, "424562nanoseconds"), arguments(424562, "424562 ns"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseDurationMicros_1to6")
    void testParseDurationMicros_1to6(int param1, long param2, String param3) {
        assertThat(TimeUtils.parseDuration(param3).getNano()).isEqualTo(param1 * param2);
    }

    static public Stream<Arguments> Provider_testParseDurationMicros_1to6() {
        return Stream.of(arguments(565731, 1000L, "565731µs"), arguments(565731, 1000L, "565731micro"), arguments(565731, 1000L, "565731micros"), arguments(565731, 1000L, "565731microsecond"), arguments(565731, 1000L, "565731microseconds"), arguments(565731, 1000L, "565731 µs"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseDurationMillis_1_1_1to2_2to7")
    void testParseDurationMillis_1_1_1to2_2to7(int param1, int param2) {
        assertThat(TimeUtils.parseDuration(param2).toMillis()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseDurationMillis_1_1_1to2_2to7() {
        return Stream.of(arguments(1234, 1234), arguments(1234, "1234ms"), arguments(1234, "1234milli"), arguments(1234, "1234millis"), arguments(1234, "1234millisecond"), arguments(1234, "1234milliseconds"), arguments(1234, "1234 ms"), arguments(155L,       155      ), arguments(155L, "      155      ms   "), arguments(20345, "PT20.345S"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseDurationSeconds_1to6")
    void testParseDurationSeconds_1to6(int param1, String param2) {
        assertThat(TimeUtils.parseDuration(param2).getSeconds()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseDurationSeconds_1to6() {
        return Stream.of(arguments(667766, "667766s"), arguments(667766, "667766sec"), arguments(667766, "667766secs"), arguments(667766, "667766second"), arguments(667766, "667766seconds"), arguments(667766, "667766 s"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseDurationMinutes_1to2_2to4_4to5")
    void testParseDurationMinutes_1to2_2to4_4to5(int param1, String param2) {
        assertThat(TimeUtils.parseDuration(param2).toMinutes()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseDurationMinutes_1to2_2to4_4to5() {
        return Stream.of(arguments(7657623, "7657623m"), arguments(7657623, "7657623min"), arguments(7657623, "7657623minute"), arguments(7657623, "7657623minutes"), arguments(7657623, "7657623 min"), arguments(15, "PT15M"), arguments(3064, "P2DT3H4M"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseDurationHours_1to3_3to4")
    void testParseDurationHours_1to3_3to4(int param1, String param2) {
        assertThat(TimeUtils.parseDuration(param2).toHours()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseDurationHours_1to3_3to4() {
        return Stream.of(arguments(987654, "987654h"), arguments(987654, "987654hour"), arguments(987654, "987654hours"), arguments(987654, "987654 h"), arguments(10, "PT10H"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseDurationDays_1to4")
    void testParseDurationDays_1to4(int param1, double param2) {
        assertThat(TimeUtils.parseDuration(param2).toDays()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseDurationDays_1to4() {
        return Stream.of(arguments(987654, 987654d), arguments(987654, "987654day"), arguments(987654, "987654days"), arguments(987654, "987654 d"));
    }
}
