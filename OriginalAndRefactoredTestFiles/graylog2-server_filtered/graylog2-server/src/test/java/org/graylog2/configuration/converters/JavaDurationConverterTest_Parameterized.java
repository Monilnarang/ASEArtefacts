package org.graylog2.configuration.converters;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class JavaDurationConverterTest_Parameterized {

    private final JavaDurationConverter converter;

    JavaDurationConverterTest() {
        this.converter = new JavaDurationConverter();
    }

    @Test
    public void convertTo_1() {
        assertThat(converter.convertTo(Duration.ofMillis(10))).isEqualTo("PT0.01S");
    }

    @Test
    public void convertBackAndForth_1() {
        assertThat(converter.convertFrom(converter.convertTo(Duration.ofSeconds(70)))).isEqualTo(Duration.ofSeconds(70));
    }

    @Test
    public void convertBackAndForth_2() {
        assertThat(converter.convertTo(converter.convertFrom("70s"))).isEqualTo("PT1M10S");
    }

    @ParameterizedTest
    @MethodSource("Provider_convertFrom_1_3")
    public void convertFrom_1_3(int param1, String param2) {
        assertThat(converter.convertFrom(param2)).isEqualTo(Duration.ofMillis(param1));
    }

    static public Stream<Arguments> Provider_convertFrom_1_3() {
        return Stream.of(arguments(10, "10ms"), arguments(10, "PT0.01S"));
    }

    @ParameterizedTest
    @MethodSource("Provider_convertFrom_2_4")
    public void convertFrom_2_4(int param1, String param2) {
        assertThat(converter.convertFrom(param2)).isEqualTo(Duration.ofSeconds(param1));
    }

    static public Stream<Arguments> Provider_convertFrom_2_4() {
        return Stream.of(arguments(10, "10s"), arguments(10, "PT10S"));
    }

    @ParameterizedTest
    @MethodSource("Provider_convertTo_2to3")
    public void convertTo_2to3(String param1, int param2) {
        assertThat(converter.convertTo(Duration.ofSeconds(param2))).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_convertTo_2to3() {
        return Stream.of(arguments("PT10S", 10), arguments("PT1M10S", 70));
    }

    @ParameterizedTest
    @MethodSource("Provider_convertComplex_1to4")
    public void convertComplex_1to4(String param1, String param2) {
        assertThat(converter.convertTo(Duration.parse(param2))).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_convertComplex_1to4() {
        return Stream.of(arguments("PT1H5M", "PT1h5m"), arguments("PT5M3S", "PT5m3s"), arguments("PT0.25S", "PT0M0.25S"), arguments("PT24H2S", "P1DT2S"));
    }
}
