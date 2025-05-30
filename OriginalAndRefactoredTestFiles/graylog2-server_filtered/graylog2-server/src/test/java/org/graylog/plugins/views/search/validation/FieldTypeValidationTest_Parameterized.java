package org.graylog.plugins.views.search.validation;

import org.apache.lucene.queryparser.classic.QueryParserConstants;
import org.apache.lucene.queryparser.classic.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class FieldTypeValidationTest_Parameterized {

    private FieldTypeValidation fieldTypeValidation;

    @BeforeEach
    void setUp() {
        fieldTypeValidation = new FieldTypeValidationImpl();
    }

    private void isValidTerm(String term, String fieldType) {
        assertThat(fieldTypeValidation.validateFieldValueType(term(term), fieldType)).isNotPresent();
    }

    private void isNotValidTerm(String term, String fieldType) {
        assertThat(fieldTypeValidation.validateFieldValueType(term(term), fieldType)).isPresent();
    }

    private ParsedTerm term(String value) {
        final Token token = Token.newToken(QueryParserConstants.TERM, "foo");
        token.beginLine = 1;
        token.beginColumn = 0;
        token.endLine = 1;
        token.endColumn = 3;
        return ParsedTerm.builder().field("foo").valueToken(ImmutableToken.create(token)).value(value).build();
    }

    @ParameterizedTest
    @MethodSource("Provider_validateDateFieldValueType_1_1_1to2_2to3_3_3to4_4to11")
    void validateDateFieldValueType_1_1_1to2_2to3_3_3to4_4to11(String param1, String param2) {
        assertThat(fieldTypeValidation.validateFieldValueType(term(param2), param1)).isNotPresent();
    }

    static public Stream<Arguments> Provider_validateDateFieldValueType_1_1_1to2_2to3_3_3to4_4to11() {
        return Stream.of(arguments("date", "2019-07-23 09:53:08.175"), arguments("date", "2019-07-23 09:53:08"), arguments("date", "2019-07-23"), arguments("date", "2020-07-29T12:00:00.000-05:00"), arguments("ip", "123.34.45.56"), arguments("ip", "2001:db8:3333:4444:5555:6666:7777:8888"), arguments("ip", "2001:db8:3333:4444:CCCC:DDDD:EEEE:FFFF"), arguments("ip", "::"), arguments("ip", "2001:db8::"), arguments("ip", "::1234:5678"), arguments("ip", "2001:db8::1234:5678"), arguments("ip", "2001:0db8:0001:0000:0000:0ab9:C0A8:0102"), arguments("ip", "2001:db8:1::ab9:C0A8:102"), arguments("ip", "123.34.45.56/24"), arguments("boolean", true), arguments("boolean", false), arguments("boolean", "hard to say"));
    }

    @ParameterizedTest
    @MethodSource("Provider_validateDateFieldValueType_2_5_12to14")
    void validateDateFieldValueType_2_5_12to14(String param1, String param2) {
        assertThat(fieldTypeValidation.validateFieldValueType(term(param2), param1)).isPresent();
    }

    static public Stream<Arguments> Provider_validateDateFieldValueType_2_5_12to14() {
        return Stream.of(arguments("date", "ABC"), arguments("ip", "123.999.45.56"), arguments("ip", "123.34.45.56/"), arguments("ip", "123.34.45.56/299"), arguments("ip", "ABC"));
    }
}
