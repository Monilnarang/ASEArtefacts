package org.languagetool.tokenizers.nl;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DutchWordTokenizerTest_Parameterized {

    private final DutchWordTokenizer wordTokenizer = new DutchWordTokenizer();

    private void assertTokenize(String input, String expected) {
        List<String> result = wordTokenizer.tokenize(input);
        assertEquals(expected, result.toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testTokenize_1to20")
    public void testTokenize_1to20(String param1, String param2) {
        assertTokenize(param1, param2);
    }

    static public Stream<Arguments> Provider_testTokenize_1to20() {
        return Stream.of(arguments("This is\u00A0a test", "[This,  , is,  , a,  , test]"), arguments("Bla bla oma's bla bla 'test", "[Bla,  , bla,  , oma's,  , bla,  , bla,  , ', test]"), arguments("Bla bla oma`s bla bla 'test", "[Bla,  , bla,  , oma`s,  , bla,  , bla,  , ', test]"), arguments("Ik zie het''", "[Ik,  , zie,  , het, ', ']"), arguments("Ik zie het``", "[Ik,  , zie,  , het, `, `]"), arguments("''Ik zie het", "[', ', Ik,  , zie,  , het]"), arguments("Ik 'zie' het", "[Ik,  , ', zie, ',  , het]"), arguments("Ik ‘zie’ het", "[Ik,  , ‘, zie, ’,  , het]"), arguments("Ik \"zie\" het", "[Ik,  , \", zie, \",  , het]"), arguments("Ik “zie” het", "[Ik,  , “, zie, ”,  , het]"), arguments("'zie'", "[', zie, ']"), arguments("‘zie’", "[‘, zie, ’]"), arguments("\"zie\"", "[\", zie, \"]"), arguments("“zie”", "[“, zie, ”]"), arguments("Ik `zie het", "[Ik,  , `, zie,  , het]"), arguments("Ik ``zie het", "[Ik,  , `, `, zie,  , het]"), arguments("'", "[']"), arguments("''", "[, ', ']"), arguments("'x'", "[', x, ']"), arguments("`x`", "[`, x, `]"));
    }
}
