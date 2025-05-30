package org.apache.seata.common.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringFormatUtilsTest_Parameterized {

    @Test
    void camelToUnderline_1() {
        assertThat(StringFormatUtils.camelToUnderline(null)).isEqualTo("");
    }

    @Test
    void underlineToCamel_1() {
        assertThat(StringFormatUtils.underlineToCamel(null)).isEqualTo("");
    }

    @Test
    void minusToCamel_1() {
        assertThat(StringFormatUtils.minusToCamel(null)).isEqualTo("");
    }

    @Test
    void dotToCamel_1() {
        assertThat(StringFormatUtils.dotToCamel(null)).isEqualTo("");
    }

    @ParameterizedTest
    @MethodSource("Provider_camelToUnderline_2to3")
    void camelToUnderline_2to3(String param1, String param2) {
        assertThat(StringFormatUtils.camelToUnderline(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_camelToUnderline_2to3() {
        return Stream.of(arguments("", "  "), arguments("abc_def_gh", "abcDefGh"));
    }

    @ParameterizedTest
    @MethodSource("Provider_underlineToCamel_2to3")
    void underlineToCamel_2to3(String param1, String param2) {
        assertThat(StringFormatUtils.underlineToCamel(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_underlineToCamel_2to3() {
        return Stream.of(arguments("", "  "), arguments("abcDefGh", "abc_def_gh"));
    }

    @ParameterizedTest
    @MethodSource("Provider_minusToCamel_2to3")
    void minusToCamel_2to3(String param1, String param2) {
        assertThat(StringFormatUtils.minusToCamel(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_minusToCamel_2to3() {
        return Stream.of(arguments("", "  "), arguments("abcDefGh", "abc-def-gh"));
    }

    @ParameterizedTest
    @MethodSource("Provider_dotToCamel_2to3")
    void dotToCamel_2to3(String param1, String param2) {
        assertThat(StringFormatUtils.dotToCamel(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_dotToCamel_2to3() {
        return Stream.of(arguments("", "  "), arguments("abcDefGh", "abc.def.gh"));
    }
}
