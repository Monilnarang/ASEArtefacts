package org.languagetool;

import org.junit.Test;
import org.languagetool.language.*;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LanguageTest_Parameterized {

    @Test
    public void testGetShortNameWithVariant_1() {
        assertEquals("en-US", AmericanEnglish.getInstance().getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetShortNameWithVariant_2() {
        assertEquals("de", new German().getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testEquals_1() {
        assertEquals(GermanyGerman.getInstance(), GermanyGerman.getInstance());
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(new AustrianGerman(), GermanyGerman.getInstance());
    }

    @Test
    public void testEquals_3() {
        assertNotEquals(new AustrianGerman(), new German());
    }

    @Test
    public void testEqualsConsiderVariantIfSpecified_1() {
        assertTrue(new German().equalsConsiderVariantsIfSpecified(new German()));
    }

    @Test
    public void testEqualsConsiderVariantIfSpecified_2() {
        assertTrue(GermanyGerman.getInstance().equalsConsiderVariantsIfSpecified(GermanyGerman.getInstance()));
    }

    @Test
    public void testEqualsConsiderVariantIfSpecified_3() {
        assertTrue(new English().equalsConsiderVariantsIfSpecified(new English()));
    }

    @Test
    public void testEqualsConsiderVariantIfSpecified_4() {
        assertTrue(AmericanEnglish.getInstance().equalsConsiderVariantsIfSpecified(AmericanEnglish.getInstance()));
    }

    @Test
    public void testEqualsConsiderVariantIfSpecified_5() {
        assertTrue(AmericanEnglish.getInstance().equalsConsiderVariantsIfSpecified(new English()));
    }

    @Test
    public void testEqualsConsiderVariantIfSpecified_6() {
        assertTrue(new English().equalsConsiderVariantsIfSpecified(AmericanEnglish.getInstance()));
    }

    @Test
    public void testEqualsConsiderVariantIfSpecified_7() {
        assertFalse(AmericanEnglish.getInstance().equalsConsiderVariantsIfSpecified(BritishEnglish.getInstance()));
    }

    @Test
    public void testEqualsConsiderVariantIfSpecified_8() {
        assertFalse(new English().equalsConsiderVariantsIfSpecified(new German()));
    }

    @Test
    public void testCreateDefaultJLanguageTool_1_testMerged_1() {
        Language german = new German();
        Language germanyGerman = GermanyGerman.getInstance();
        JLanguageTool ltGerman = german.createDefaultJLanguageTool();
        JLanguageTool ltGerman2 = german.createDefaultJLanguageTool();
        JLanguageTool ltGermanyGerman = germanyGerman.createDefaultJLanguageTool();
        assertNotSame(ltGermanyGerman, ltGerman);
        assertSame(ltGerman2, ltGerman);
        assertEquals(ltGerman.getLanguage(), german);
        assertEquals(ltGermanyGerman.getLanguage(), germanyGerman);
    }

    @Test
    public void testCreateDefaultJLanguageTool_5() {
        JLanguageTool ltEnglish = new English().createDefaultJLanguageTool();
        assertEquals(ltEnglish.getLanguage(), new English());
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetTranslatedName_1_3_5to6")
    public void testGetTranslatedName_1_3_5to6(String param1, String param2) {
        assertEquals(param1, new English().getTranslatedName(TestTools.getMessages(param2)));
    }

    static public Stream<Arguments> Provider_testGetTranslatedName_1_3_5to6() {
        return Stream.of(arguments("English", "en"), arguments("Englisch", "de"), arguments("Deutsch", "de"), arguments("Deutsch (Schweiz)", "de"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetTranslatedName_2_4")
    public void testGetTranslatedName_2_4(String param1, String param2) {
        assertEquals(param1, BritishEnglish.getInstance().getTranslatedName(TestTools.getMessages(param2)));
    }

    static public Stream<Arguments> Provider_testGetTranslatedName_2_4() {
        return Stream.of(arguments("English (British)", "en"), arguments("Englisch (Großbritannien)", "de"));
    }
}
