package org.apache.flink.api.common.resources;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CPUResourceTest_Purified {

    @Test
    void toHumanReadableString_1() {
        assertThat(new CPUResource(0).toHumanReadableString()).isEqualTo("0.00 cores");
    }

    @Test
    void toHumanReadableString_2() {
        assertThat(new CPUResource(1).toHumanReadableString()).isEqualTo("1.00 cores");
    }

    @Test
    void toHumanReadableString_3() {
        assertThat(new CPUResource(1.2).toHumanReadableString()).isEqualTo("1.20 cores");
    }

    @Test
    void toHumanReadableString_4() {
        assertThat(new CPUResource(1.23).toHumanReadableString()).isEqualTo("1.23 cores");
    }

    @Test
    void toHumanReadableString_5() {
        assertThat(new CPUResource(1.234).toHumanReadableString()).isEqualTo("1.23 cores");
    }

    @Test
    void toHumanReadableString_6() {
        assertThat(new CPUResource(1.235).toHumanReadableString()).isEqualTo("1.24 cores");
    }

    @Test
    void toHumanReadableString_7() {
        assertThat(new CPUResource(10).toHumanReadableString()).isEqualTo("10.00 cores");
    }

    @Test
    void toHumanReadableString_8() {
        assertThat(new CPUResource(100).toHumanReadableString()).isEqualTo("100.00 cores");
    }

    @Test
    void toHumanReadableString_9() {
        assertThat(new CPUResource(1000).toHumanReadableString()).isEqualTo("1000.00 cores");
    }

    @Test
    void toHumanReadableString_10() {
        assertThat(new CPUResource(123456789).toHumanReadableString()).isEqualTo("123456789.00 cores");
    }

    @Test
    void toHumanReadableString_11() {
        assertThat(new CPUResource(12345.6789).toHumanReadableString()).isEqualTo("12345.68 cores");
    }
}
