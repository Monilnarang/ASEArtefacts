package org.graylog2.shared.utilities;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.graylog2.shared.utilities.StringUtils.toLowerCase;
import static org.graylog2.shared.utilities.StringUtils.toUpperCase;

public class StringUtilsTest_Purified {

    @Test
    public void testHumanReadable_1() {
        assertThat(StringUtils.humanReadableByteCount(1024L * 1024L * 1024L * 5L + 1024L * 1024L * 512L)).isEqualTo("5.5 GiB");
    }

    @Test
    public void testHumanReadable_2() {
        assertThat(StringUtils.humanReadableByteCount(1024L * 1024L * 1024L * 5L)).isEqualTo("5.0 GiB");
    }

    @Test
    public void testHumanReadable_3() {
        assertThat(StringUtils.humanReadableByteCount(1024L * 1024L * 4L + 1024L * 900L)).isEqualTo("4.9 MiB");
    }

    @Test
    public void testHumanReadable_4() {
        assertThat(StringUtils.humanReadableByteCount(1023)).isEqualTo("1023 B");
    }

    @Test
    public void testHumanReadable_5() {
        assertThat(StringUtils.humanReadableByteCount(1024)).isEqualTo("1.0 KiB");
    }

    @Test
    public void testHumanReadable_6() {
        assertThat(StringUtils.humanReadableByteCount(1024L * 1024L * 1024L * 1024L * 5L + 1024L * 1024L * 512L)).isEqualTo("5.0 TiB");
    }

    @Test
    public void testHumanReadable_7() {
        assertThat(StringUtils.humanReadableByteCount(1024L * 5L + 512L)).isEqualTo("5.5 KiB");
    }

    @Test
    public void testSplitByComma_1() {
    }

    @Test
    public void testSplitByComma_2() {
    }

    @Test
    public void testSplitByComma_3() {
    }

    @Test
    public void testSplitByComma_4() {
    }

    @Test
    public void testSplitByComma_5() {
    }
}
