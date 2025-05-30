package org.graylog.plugins.views.search.validation;

import org.apache.lucene.queryparser.classic.QueryParserConstants;
import org.apache.lucene.queryparser.classic.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class FieldTypeValidationTest_Purified {

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

    @Test
    void validateDateFieldValueType_1() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("2019-07-23 09:53:08.175"), "date")).isNotPresent();
    }

    @Test
    void validateDateFieldValueType_2() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("2019-07-23 09:53:08"), "date")).isNotPresent();
    }

    @Test
    void validateDateFieldValueType_3() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("2019-07-23"), "date")).isNotPresent();
    }

    @Test
    void validateDateFieldValueType_4() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("2020-07-29T12:00:00.000-05:00"), "date")).isNotPresent();
    }

    @Test
    void validateDateFieldValueType_5() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("ABC"), "date")).isPresent();
    }

    @Test
    void validateIPFieldValueType_1() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("123.34.45.56"), "ip")).isNotPresent();
    }

    @Test
    void validateIPFieldValueType_2() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("123.999.45.56"), "ip")).isPresent();
    }

    @Test
    void validateIPFieldValueType_3() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("2001:db8:3333:4444:5555:6666:7777:8888"), "ip")).isNotPresent();
    }

    @Test
    void validateIPFieldValueType_4() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("2001:db8:3333:4444:CCCC:DDDD:EEEE:FFFF"), "ip")).isNotPresent();
    }

    @Test
    void validateIPFieldValueType_5() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("::"), "ip")).isNotPresent();
    }

    @Test
    void validateIPFieldValueType_6() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("2001:db8::"), "ip")).isNotPresent();
    }

    @Test
    void validateIPFieldValueType_7() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("::1234:5678"), "ip")).isNotPresent();
    }

    @Test
    void validateIPFieldValueType_8() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("2001:db8::1234:5678"), "ip")).isNotPresent();
    }

    @Test
    void validateIPFieldValueType_9() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("2001:0db8:0001:0000:0000:0ab9:C0A8:0102"), "ip")).isNotPresent();
    }

    @Test
    void validateIPFieldValueType_10() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("2001:db8:1::ab9:C0A8:102"), "ip")).isNotPresent();
    }

    @Test
    void validateIPFieldValueType_11() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("123.34.45.56/24"), "ip")).isNotPresent();
    }

    @Test
    void validateIPFieldValueType_12() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("123.34.45.56/"), "ip")).isPresent();
    }

    @Test
    void validateIPFieldValueType_13() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("123.34.45.56/299"), "ip")).isPresent();
    }

    @Test
    void validateIPFieldValueType_14() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("ABC"), "ip")).isPresent();
    }

    @Test
    void validateBooleanFieldValueType_1() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("true"), "boolean")).isNotPresent();
    }

    @Test
    void validateBooleanFieldValueType_2() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("false"), "boolean")).isNotPresent();
    }

    @Test
    void validateBooleanFieldValueType_3() {
        assertThat(fieldTypeValidation.validateFieldValueType(term("hard to say"), "boolean")).isNotPresent();
    }
}
