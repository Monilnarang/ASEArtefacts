package org.jsoup.select;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import static org.jsoup.select.EvaluatorDebug.asElement;
import static org.jsoup.select.EvaluatorDebug.sexpr;
import static org.jsoup.select.Selector.SelectorParseException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class QueryParserTest_Parameterized {

    @Test
    public void parsesOrAfterAttribute_1() {
        String q = "#parent [class*=child], .some-other-selector .nested";
        String parsed = sexpr(q);
        assertEquals("(Or (And (AttributeWithValueContaining '[class*=child]')(Ancestor (Id '#parent')))(And (Class '.nested')(Ancestor (Class '.some-other-selector'))))", parsed);
    }

    @ParameterizedTest
    @MethodSource("Provider_parsesOrAfterAttribute_2to4")
    public void parsesOrAfterAttribute_2to4(String param1, String param2) {
        assertEquals(param1, sexpr(param2));
    }

    static public Stream<Arguments> Provider_parsesOrAfterAttribute_2to4() {
        return Stream.of(arguments("(Or (Class '.some-other-selector')(And (AttributeWithValueContaining '[class*=child]')(Ancestor (Id '#parent'))))", "#parent [class*=child], .some-other-selector"), arguments("(Or (And (Id '#el')(AttributeWithValueContaining '[class*=child]'))(Class '.some-other-selector'))", "#el[class*=child], .some-other-selector"), arguments("(Or (And (AttributeWithValueContaining '[class*=child]')(Ancestor (Id '#parent')))(And (Class '.nested')(Ancestor (Class '.some-other-selector'))))", "#parent [class*=child], .some-other-selector .nested"));
    }
}
