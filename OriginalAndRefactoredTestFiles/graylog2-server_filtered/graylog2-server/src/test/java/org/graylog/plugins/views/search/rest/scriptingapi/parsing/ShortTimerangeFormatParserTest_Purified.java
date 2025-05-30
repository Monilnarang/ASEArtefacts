package org.graylog.plugins.views.search.rest.scriptingapi.parsing;

import org.graylog2.plugin.indexer.searches.timeranges.KeywordRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ShortTimerangeFormatParserTest_Purified {

    private ShortTimerangeFormatParser toTest;

    @BeforeEach
    void setUp() {
        toTest = new ShortTimerangeFormatParser();
    }

    @Test
    void returnsEmptyOptionalOnBadInput_1() {
        assertThat(toTest.parse("#$%")).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOnBadInput_2() {
        assertThat(toTest.parse("13days")).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOnBadInput_3() {
        assertThat(toTest.parse("42x")).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOnBadInput_4() {
        assertThat(toTest.parse("-13days")).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOnBadInput_5() {
        assertThat(toTest.parse("1,5d")).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOnBadInput_6() {
        assertThat(toTest.parse("d13d")).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOnBadInput_7() {
        assertThat(toTest.parse("")).isEmpty();
    }
}
