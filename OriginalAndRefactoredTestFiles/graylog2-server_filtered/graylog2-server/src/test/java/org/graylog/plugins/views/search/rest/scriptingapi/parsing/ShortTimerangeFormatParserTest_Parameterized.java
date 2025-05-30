package org.graylog.plugins.views.search.rest.scriptingapi.parsing;

import org.graylog2.plugin.indexer.searches.timeranges.KeywordRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ShortTimerangeFormatParserTest_Parameterized {

    private ShortTimerangeFormatParser toTest;

    @BeforeEach
    void setUp() {
        toTest = new ShortTimerangeFormatParser();
    }

    @ParameterizedTest
    @MethodSource("Provider_returnsEmptyOptionalOnBadInput_1to7")
    void returnsEmptyOptionalOnBadInput_1to7(String param1) {
        assertThat(toTest.parse(param1)).isEmpty();
    }

    static public Stream<Arguments> Provider_returnsEmptyOptionalOnBadInput_1to7() {
        return Stream.of(arguments("#$%"), arguments("13days"), arguments("42x"), arguments("-13days"), arguments("1,5d"), arguments("d13d"), arguments(""));
    }
}
