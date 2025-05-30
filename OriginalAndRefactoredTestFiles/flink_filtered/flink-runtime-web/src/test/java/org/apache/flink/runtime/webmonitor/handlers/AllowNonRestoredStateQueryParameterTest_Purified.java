package org.apache.flink.runtime.webmonitor.handlers;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AllowNonRestoredStateQueryParameterTest_Purified {

    private final AllowNonRestoredStateQueryParameter allowNonRestoredStateQueryParameter = new AllowNonRestoredStateQueryParameter();

    @Test
    void testConvertStringToValue_1() {
        assertThat(allowNonRestoredStateQueryParameter.convertValueToString(false)).isEqualTo("false");
    }

    @Test
    void testConvertStringToValue_2() {
        assertThat(allowNonRestoredStateQueryParameter.convertValueToString(true)).isEqualTo("true");
    }

    @Test
    void testConvertValueFromString_1() {
        assertThat(allowNonRestoredStateQueryParameter.convertStringToValue("false")).isEqualTo(false);
    }

    @Test
    void testConvertValueFromString_2() {
        assertThat(allowNonRestoredStateQueryParameter.convertStringToValue("true")).isEqualTo(true);
    }

    @Test
    void testConvertValueFromString_3() {
        assertThat(allowNonRestoredStateQueryParameter.convertStringToValue("TRUE")).isEqualTo(true);
    }
}
