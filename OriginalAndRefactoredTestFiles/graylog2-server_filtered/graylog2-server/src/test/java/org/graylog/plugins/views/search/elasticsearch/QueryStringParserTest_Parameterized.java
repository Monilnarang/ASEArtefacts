package org.graylog.plugins.views.search.elasticsearch;

import org.graylog.plugins.views.search.QueryMetadata;
import org.graylog.plugins.views.search.validation.SubstringMultilinePosition;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class QueryStringParserTest_Parameterized {

    private final static QueryStringParser queryStringParser = new QueryStringParser();

    private Set<String> parse(String query) {
        return queryStringParser.parse(query).usedParameterNames();
    }

    @Test
    void testSimpleParsing_3() {
        assertThat(parse("foo:$bar$ AND some:$value$")).containsExactlyInAnyOrder("value", "bar");
    }

    @ParameterizedTest
    @MethodSource("Provider_testSimpleParsing_1_1_1to2_2to3_5to6_14")
    void testSimpleParsing_1_1_1to2_2to3_5to6_14(String param1) {
        assertThat(parse(param1)).isEmpty();
    }

    static public Stream<Arguments> Provider_testSimpleParsing_1_1_1to2_2to3_5to6_14() {
        return Stream.of(arguments("foo:bar AND some:value"), arguments("foo:bar$"), arguments("foo:bar$ OR foo:$baz"), arguments("foo:$$"), arguments("foo:$some parameter$"), arguments("foo:$some-parameter$"), arguments("foo:$some/parameter$"), arguments("foo:$42parameter$"), arguments("foo:$9$"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSimpleParsing_2to4_4to7_7to13")
    void testSimpleParsing_2to4_4to7_7to13(String param1, String param2) {
        assertThat(parse(param2)).containsExactly(param1);
    }

    static public Stream<Arguments> Provider_testSimpleParsing_2to4_4to7_7to13() {
        return Stream.of(arguments("bar", "foo:$bar$ AND some:value"), arguments("baz", "foo:bar$ OR foo:$baz$"), arguments("bar", "foo:$bar$ OR foo:$baz"), arguments("baz", "foo:bar$ AND baz$:$baz$"), arguments("foo", "foo:$foo$ AND bar:$$"), arguments("some42parameter", "foo:$some42parameter$"), arguments("parameter42", "foo:$parameter42$"), arguments("someparameter", "foo:$someparameter$"), arguments("some_parameter", "foo:$some_parameter$"), arguments("_someparameter", "foo:$_someparameter$"), arguments("_someparameter_", "foo:$_someparameter_$"), arguments("_someparameter_", "foo:$_someparameter_$"), arguments("_", "foo:$_$"), arguments("s", "foo:$s$"));
    }
}
