package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_Purified {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);
        Tag script1 = Tag.valueOf("script", ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", ParseSettings.htmlDefault);
        assertSame(script1, script2);
    }

    @Test
    public void knownTags_1() {
        assertTrue(Tag.isKnownTag("div"));
    }

    @Test
    public void knownTags_2() {
        assertFalse(Tag.isKnownTag("explain"));
    }
}
