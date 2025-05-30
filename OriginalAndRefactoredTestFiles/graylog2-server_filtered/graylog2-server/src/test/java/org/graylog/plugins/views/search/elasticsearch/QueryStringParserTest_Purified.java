package org.graylog.plugins.views.search.elasticsearch;

import org.graylog.plugins.views.search.QueryMetadata;
import org.graylog.plugins.views.search.validation.SubstringMultilinePosition;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringParserTest_Purified {

    private final static QueryStringParser queryStringParser = new QueryStringParser();

    private Set<String> parse(String query) {
        return queryStringParser.parse(query).usedParameterNames();
    }

    @Test
    void testSimpleParsing_1() {
        assertThat(parse("foo:bar AND some:value")).isEmpty();
    }

    @Test
    void testSimpleParsing_2() {
        assertThat(parse("foo:$bar$ AND some:value")).containsExactly("bar");
    }

    @Test
    void testSimpleParsing_3() {
        assertThat(parse("foo:$bar$ AND some:$value$")).containsExactlyInAnyOrder("value", "bar");
    }

    @Test
    void testStringsContainingDollars_1() {
        assertThat(parse("foo:bar$")).isEmpty();
    }

    @Test
    void testStringsContainingDollars_2() {
        assertThat(parse("foo:bar$ OR foo:$baz")).isEmpty();
    }

    @Test
    void testStringsContainingDollars_3() {
        assertThat(parse("foo:bar$ OR foo:$baz$")).containsExactly("baz");
    }

    @Test
    void testStringsContainingDollars_4() {
        assertThat(parse("foo:$bar$ OR foo:$baz")).containsExactly("bar");
    }

    @Test
    void testStringsContainingDollars_5() {
        assertThat(parse("foo:bar$ AND baz$:$baz$")).containsExactly("baz");
    }

    @Test
    void testStringsContainingDollars_6() {
        assertThat(parse("foo:$$")).isEmpty();
    }

    @Test
    void testStringsContainingDollars_7() {
        assertThat(parse("foo:$foo$ AND bar:$$")).containsExactly("foo");
    }

    @Test
    void testCharacterSpaceOfParameterNames_1() {
        assertThat(parse("foo:$some parameter$")).isEmpty();
    }

    @Test
    void testCharacterSpaceOfParameterNames_2() {
        assertThat(parse("foo:$some-parameter$")).isEmpty();
    }

    @Test
    void testCharacterSpaceOfParameterNames_3() {
        assertThat(parse("foo:$some/parameter$")).isEmpty();
    }

    @Test
    void testCharacterSpaceOfParameterNames_4() {
        assertThat(parse("foo:$some42parameter$")).containsExactly("some42parameter");
    }

    @Test
    void testCharacterSpaceOfParameterNames_5() {
        assertThat(parse("foo:$42parameter$")).isEmpty();
    }

    @Test
    void testCharacterSpaceOfParameterNames_6() {
        assertThat(parse("foo:$parameter42$")).containsExactly("parameter42");
    }

    @Test
    void testCharacterSpaceOfParameterNames_7() {
        assertThat(parse("foo:$someparameter$")).containsExactly("someparameter");
    }

    @Test
    void testCharacterSpaceOfParameterNames_8() {
        assertThat(parse("foo:$some_parameter$")).containsExactly("some_parameter");
    }

    @Test
    void testCharacterSpaceOfParameterNames_9() {
        assertThat(parse("foo:$_someparameter$")).containsExactly("_someparameter");
    }

    @Test
    void testCharacterSpaceOfParameterNames_10() {
        assertThat(parse("foo:$_someparameter_$")).containsExactly("_someparameter_");
    }

    @Test
    void testCharacterSpaceOfParameterNames_11() {
        assertThat(parse("foo:$_someparameter_$")).containsExactly("_someparameter_");
    }

    @Test
    void testCharacterSpaceOfParameterNames_12() {
        assertThat(parse("foo:$_$")).containsExactly("_");
    }

    @Test
    void testCharacterSpaceOfParameterNames_13() {
        assertThat(parse("foo:$s$")).containsExactly("s");
    }

    @Test
    void testCharacterSpaceOfParameterNames_14() {
        assertThat(parse("foo:$9$")).isEmpty();
    }
}
