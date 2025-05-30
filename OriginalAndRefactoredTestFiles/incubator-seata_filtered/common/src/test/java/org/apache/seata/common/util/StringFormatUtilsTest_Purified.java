package org.apache.seata.common.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class StringFormatUtilsTest_Purified {

    @Test
    void camelToUnderline_1() {
        assertThat(StringFormatUtils.camelToUnderline(null)).isEqualTo("");
    }

    @Test
    void camelToUnderline_2() {
        assertThat(StringFormatUtils.camelToUnderline("  ")).isEqualTo("");
    }

    @Test
    void camelToUnderline_3() {
        assertThat(StringFormatUtils.camelToUnderline("abcDefGh")).isEqualTo("abc_def_gh");
    }

    @Test
    void underlineToCamel_1() {
        assertThat(StringFormatUtils.underlineToCamel(null)).isEqualTo("");
    }

    @Test
    void underlineToCamel_2() {
        assertThat(StringFormatUtils.underlineToCamel("  ")).isEqualTo("");
    }

    @Test
    void underlineToCamel_3() {
        assertThat(StringFormatUtils.underlineToCamel("abc_def_gh")).isEqualTo("abcDefGh");
    }

    @Test
    void minusToCamel_1() {
        assertThat(StringFormatUtils.minusToCamel(null)).isEqualTo("");
    }

    @Test
    void minusToCamel_2() {
        assertThat(StringFormatUtils.minusToCamel("  ")).isEqualTo("");
    }

    @Test
    void minusToCamel_3() {
        assertThat(StringFormatUtils.minusToCamel("abc-def-gh")).isEqualTo("abcDefGh");
    }

    @Test
    void dotToCamel_1() {
        assertThat(StringFormatUtils.dotToCamel(null)).isEqualTo("");
    }

    @Test
    void dotToCamel_2() {
        assertThat(StringFormatUtils.dotToCamel("  ")).isEqualTo("");
    }

    @Test
    void dotToCamel_3() {
        assertThat(StringFormatUtils.dotToCamel("abc.def.gh")).isEqualTo("abcDefGh");
    }
}
