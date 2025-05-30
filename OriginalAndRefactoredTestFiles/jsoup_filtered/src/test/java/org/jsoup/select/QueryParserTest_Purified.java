package org.jsoup.select;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import static org.jsoup.select.EvaluatorDebug.asElement;
import static org.jsoup.select.EvaluatorDebug.sexpr;
import static org.jsoup.select.Selector.SelectorParseException;
import static org.junit.jupiter.api.Assertions.*;

public class QueryParserTest_Purified {

    @Test
    public void parsesOrAfterAttribute_1() {
        String q = "#parent [class*=child], .some-other-selector .nested";
        String parsed = sexpr(q);
        assertEquals("(Or (And (AttributeWithValueContaining '[class*=child]')(Ancestor (Id '#parent')))(And (Class '.nested')(Ancestor (Class '.some-other-selector'))))", parsed);
    }

    @Test
    public void parsesOrAfterAttribute_2() {
        assertEquals("(Or (Class '.some-other-selector')(And (AttributeWithValueContaining '[class*=child]')(Ancestor (Id '#parent'))))", sexpr("#parent [class*=child], .some-other-selector"));
    }

    @Test
    public void parsesOrAfterAttribute_3() {
        assertEquals("(Or (And (Id '#el')(AttributeWithValueContaining '[class*=child]'))(Class '.some-other-selector'))", sexpr("#el[class*=child], .some-other-selector"));
    }

    @Test
    public void parsesOrAfterAttribute_4() {
        assertEquals("(Or (And (AttributeWithValueContaining '[class*=child]')(Ancestor (Id '#parent')))(And (Class '.nested')(Ancestor (Class '.some-other-selector'))))", sexpr("#parent [class*=child], .some-other-selector .nested"));
    }
}
