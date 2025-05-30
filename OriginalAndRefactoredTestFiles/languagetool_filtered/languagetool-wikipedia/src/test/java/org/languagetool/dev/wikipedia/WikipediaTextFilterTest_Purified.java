package org.languagetool.dev.wikipedia;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

@Ignore
public class WikipediaTextFilterTest_Purified {

    private final SwebleWikipediaTextFilter swebleFilter = new SwebleWikipediaTextFilter();

    private void assertExtract(String input, String expected) {
        assertEquals(expected, swebleFilter.filter(input).getPlainText());
    }

    @Test
    public void testEntity_1() {
        assertExtract("rund 20&nbsp;Kilometer südlich", "rund 20\u00A0Kilometer südlich");
    }

    @Test
    public void testEntity_2() {
        assertExtract("one&lt;br/&gt;two", "one<br/>two");
    }

    @Test
    public void testEntity_3() {
        assertExtract("one &ndash; two", "one – two");
    }

    @Test
    public void testEntity_4() {
        assertExtract("one &mdash; two", "one — two");
    }

    @Test
    public void testEntity_5() {
        assertExtract("one &amp; two", "one & two");
    }

    @Test
    public void testLists_1() {
        assertExtract("# one\n# two\n", "one\n\ntwo");
    }

    @Test
    public void testLists_2() {
        assertExtract("* one\n* two\n", "one\n\ntwo");
    }

    @Test
    public void testOtherStuff_1() {
        assertExtract("Daniel Guerin, ''[http://theanarchistlibrary.org Anarchism: From Theory to Practice]''", "Daniel Guerin, Anarchism: From Theory to Practice");
    }

    @Test
    public void testOtherStuff_2() {
        assertExtract("foo <ref>\"At the end of the century in France [http://theanarchistlibrary.org] [[Daniel Guérin]]. ''Anarchism'']</ref>", "foo");
    }

    @Test
    public void testOtherStuff_3() {
        assertExtract("* [http://theanarchistlibrary.org ''Anarchism: From Theory to Practice''] by [[Daniel Guerin]]. Monthly Review Press.\n", "Anarchism: From Theory to Practice by Daniel Guerin. Monthly Review Press.");
    }

    @Test
    public void testOtherStuff_4() {
        assertExtract("The <code>$pattern</code>", "The $pattern");
    }

    @Test
    public void testOtherStuff_5() {
        assertExtract("foo <source lang=\"bash\">some source</source> bar", "foo bar");
    }
}
