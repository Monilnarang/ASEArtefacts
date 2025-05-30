package org.graylog2.configuration.converters;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.assertj.core.api.Assertions.assertThat;

class JavaDurationConverterTest_Purified {

    private final JavaDurationConverter converter;

    JavaDurationConverterTest() {
        this.converter = new JavaDurationConverter();
    }

    @Test
    public void convertFrom_1() {
        assertThat(converter.convertFrom("10ms")).isEqualTo(Duration.ofMillis(10));
    }

    @Test
    public void convertFrom_2() {
        assertThat(converter.convertFrom("10s")).isEqualTo(Duration.ofSeconds(10));
    }

    @Test
    public void convertFrom_3() {
        assertThat(converter.convertFrom("PT0.01S")).isEqualTo(Duration.ofMillis(10));
    }

    @Test
    public void convertFrom_4() {
        assertThat(converter.convertFrom("PT10S")).isEqualTo(Duration.ofSeconds(10));
    }

    @Test
    public void convertTo_1() {
        assertThat(converter.convertTo(Duration.ofMillis(10))).isEqualTo("PT0.01S");
    }

    @Test
    public void convertTo_2() {
        assertThat(converter.convertTo(Duration.ofSeconds(10))).isEqualTo("PT10S");
    }

    @Test
    public void convertTo_3() {
        assertThat(converter.convertTo(Duration.ofSeconds(70))).isEqualTo("PT1M10S");
    }

    @Test
    public void convertBackAndForth_1() {
        assertThat(converter.convertFrom(converter.convertTo(Duration.ofSeconds(70)))).isEqualTo(Duration.ofSeconds(70));
    }

    @Test
    public void convertBackAndForth_2() {
        assertThat(converter.convertTo(converter.convertFrom("70s"))).isEqualTo("PT1M10S");
    }

    @Test
    public void convertComplex_1() {
        assertThat(converter.convertTo(Duration.parse("PT1h5m"))).isEqualTo("PT1H5M");
    }

    @Test
    public void convertComplex_2() {
        assertThat(converter.convertTo(Duration.parse("PT5m3s"))).isEqualTo("PT5M3S");
    }

    @Test
    public void convertComplex_3() {
        assertThat(converter.convertTo(Duration.parse("PT0M0.25S"))).isEqualTo("PT0.25S");
    }

    @Test
    public void convertComplex_4() {
        assertThat(converter.convertTo(Duration.parse("P1DT2S"))).isEqualTo("PT24H2S");
    }
}
