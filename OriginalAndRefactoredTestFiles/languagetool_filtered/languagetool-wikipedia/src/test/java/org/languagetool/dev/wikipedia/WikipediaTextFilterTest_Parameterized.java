package org.languagetool.dev.wikipedia;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Ignore
public class WikipediaTextFilterTest_Parameterized {

    private final SwebleWikipediaTextFilter swebleFilter = new SwebleWikipediaTextFilter();

    private void assertExtract(String input, String expected) {
        assertEquals(expected, swebleFilter.filter(input).getPlainText());
    }

    @ParameterizedTest
    @MethodSource("Provider_testEntity_1_1_1to2_2_2to3_3to4_4to5_5")
    public void testEntity_1_1_1to2_2_2to3_3to4_4to5_5(String param1, String param2) {
        assertExtract(param1, param2);
    }

    static public Stream<Arguments> Provider_testEntity_1_1_1to2_2_2to3_3to4_4to5_5() {
        return Stream.of(arguments("rund 20&nbsp;Kilometer südlich", "rund 20\u00A0Kilometer südlich"), arguments("one&lt;br/&gt;two", "one<br/>two"), arguments("one &ndash; two", "one – two"), arguments("one &mdash; two", "one — two"), arguments("one &amp; two", "one & two"), arguments("# one\n# two\n", "one\n\ntwo"), arguments("* one\n* two\n", "one\n\ntwo"), arguments("Daniel Guerin, ''[http://theanarchistlibrary.org Anarchism: From Theory to Practice]''", "Daniel Guerin, Anarchism: From Theory to Practice"), arguments("foo <ref>\"At the end of the century in France [http://theanarchistlibrary.org] [[Daniel Guérin]]. ''Anarchism'']</ref>", "foo"), arguments("* [http://theanarchistlibrary.org ''Anarchism: From Theory to Practice''] by [[Daniel Guerin]]. Monthly Review Press.\n", "Anarchism: From Theory to Practice by Daniel Guerin. Monthly Review Press."), arguments("The <code>$pattern</code>", "The $pattern"), arguments("foo <source lang=\"bash\">some source</source> bar", "foo bar"));
    }
}
