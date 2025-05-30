package org.apache.flink.fs.gs.utils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ChecksumUtilsTest_Purified {

    @Test
    void shouldConvertToStringChecksum_1() {
        assertThat(ChecksumUtils.convertChecksumToString(12345)).isEqualTo("AAAwOQ==");
    }

    @Test
    void shouldConvertToStringChecksum_2() {
        assertThat(ChecksumUtils.convertChecksumToString(54321)).isEqualTo("AADUMQ==");
    }
}
