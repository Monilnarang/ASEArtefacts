package org.languagetool;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.Locale;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LanguagesTest_Purified {

    @Test
    public void testIsLanguageSupported_1() {
        Assert.assertTrue(Languages.isLanguageSupported("xx"));
    }

    @Test
    public void testIsLanguageSupported_2() {
        Assert.assertTrue(Languages.isLanguageSupported("XX"));
    }

    @Test
    public void testIsLanguageSupported_3() {
        Assert.assertTrue(Languages.isLanguageSupported("en-US"));
    }

    @Test
    public void testIsLanguageSupported_4() {
        Assert.assertTrue(Languages.isLanguageSupported("en-us"));
    }

    @Test
    public void testIsLanguageSupported_5() {
        Assert.assertTrue(Languages.isLanguageSupported("EN-US"));
    }

    @Test
    public void testIsLanguageSupported_6() {
        Assert.assertTrue(Languages.isLanguageSupported("de"));
    }

    @Test
    public void testIsLanguageSupported_7() {
        Assert.assertTrue(Languages.isLanguageSupported("de-DE"));
    }

    @Test
    public void testIsLanguageSupported_8() {
        Assert.assertTrue(Languages.isLanguageSupported("de-DE-x-simple-language"));
    }

    @Test
    public void testIsLanguageSupported_9() {
        Assert.assertTrue(Languages.isLanguageSupported("de-DE-x-simple-LANGUAGE"));
    }

    @Test
    public void testIsLanguageSupported_10() {
        assertFalse(Languages.isLanguageSupported("yy-ZZ"));
    }

    @Test
    public void testIsLanguageSupported_11() {
        assertFalse(Languages.isLanguageSupported("zz"));
    }

    @Test
    public void testIsLanguageSupported_12() {
        assertFalse(Languages.isLanguageSupported("somthing totally invalid"));
    }

    @Test
    public void testGetLanguageForName_1() {
        assertEquals("en-US", Languages.getLanguageForName("English (US)").getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetLanguageForName_2() {
        assertEquals("de", Languages.getLanguageForName("German").getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetLanguageForName_3() {
        assertEquals(null, Languages.getLanguageForName("Foobar"));
    }

    @Test
    public void testIsVariant_1() {
        Assert.assertTrue(Languages.getLanguageForShortCode("en-US").isVariant());
    }

    @Test
    public void testIsVariant_2() {
        Assert.assertTrue(Languages.getLanguageForShortCode("de-CH").isVariant());
    }

    @Test
    public void testIsVariant_3() {
        assertFalse(Languages.getLanguageForShortCode("en").isVariant());
    }

    @Test
    public void testIsVariant_4() {
        assertFalse(Languages.getLanguageForShortCode("de").isVariant());
    }

    @Test
    public void testHasPremium_1() {
        assertTrue(Languages.hasPremium("org.languagetool.language.Portuguese"));
    }

    @Test
    public void testHasPremium_2() {
        assertTrue(Languages.hasPremium("org.languagetool.language.GermanyGerman"));
    }

    @Test
    public void testHasPremium_3() {
        assertTrue(Languages.hasPremium("org.languagetool.language.AmericanEnglish"));
    }

    @Test
    public void testHasPremium_4() {
        assertFalse(Languages.hasPremium("org.languagetool.language.Danish"));
    }

    @Test
    public void isHiddenFromGui_1() {
        Assert.assertTrue(Languages.getLanguageForShortCode("en").isHiddenFromGui());
    }

    @Test
    public void isHiddenFromGui_2() {
        Assert.assertTrue(Languages.getLanguageForShortCode("de").isHiddenFromGui());
    }

    @Test
    public void isHiddenFromGui_3() {
        Assert.assertTrue(Languages.getLanguageForShortCode("pt").isHiddenFromGui());
    }

    @Test
    public void isHiddenFromGui_4() {
        assertFalse(Languages.getLanguageForShortCode("en-US").isHiddenFromGui());
    }

    @Test
    public void isHiddenFromGui_5() {
        assertFalse(Languages.getLanguageForShortCode("de-CH").isHiddenFromGui());
    }

    @Test
    public void isHiddenFromGui_6() {
        assertFalse(Languages.getLanguageForShortCode("ast").isHiddenFromGui());
    }

    @Test
    public void isHiddenFromGui_7() {
        assertFalse(Languages.getLanguageForShortCode("pl").isHiddenFromGui());
    }

    @Test
    public void isHiddenFromGui_8() {
        assertFalse(Languages.getLanguageForShortCode("ca-ES").isHiddenFromGui());
    }

    @Test
    public void isHiddenFromGui_9() {
        assertFalse(Languages.getLanguageForShortCode("ca-ES-valencia").isHiddenFromGui());
    }

    @Test
    public void isHiddenFromGui_10() {
        assertFalse(Languages.getLanguageForShortCode("de-DE-x-simple-language").isHiddenFromGui());
    }

    @Test
    public void isHiddenFromGui_11() {
        assertFalse(Languages.getLanguageForShortCode("de-DE").isHiddenFromGui());
    }

    @Test
    public void testGetLanguageForLocale_1() {
        assertEquals("de", Languages.getLanguageForLocale(Locale.GERMAN).getShortCode());
    }

    @Test
    public void testGetLanguageForLocale_2() {
        assertEquals("de", Languages.getLanguageForLocale(Locale.GERMANY).getShortCode());
    }

    @Test
    public void testGetLanguageForLocale_3() {
        assertEquals("de-DE", Languages.getLanguageForLocale(new Locale("de", "DE")).getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetLanguageForLocale_4() {
        assertEquals("de-AT", Languages.getLanguageForLocale(new Locale("de", "AT")).getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetLanguageForLocale_5() {
        assertEquals("en-US", Languages.getLanguageForLocale(new Locale("en", "US")).getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetLanguageForLocale_6() {
        assertEquals("en-GB", Languages.getLanguageForLocale(new Locale("en", "GB")).getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetLanguageForLocale_7() {
        assertEquals("en-US", Languages.getLanguageForLocale(new Locale("en")).getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetLanguageForLocale_8() {
        assertEquals("de-DE", Languages.getLanguageForLocale(new Locale("de")).getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetLanguageForLocale_9() {
        assertEquals("pl-PL", Languages.getLanguageForLocale(new Locale("pl")).getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetLanguageForLocale_10() {
        assertEquals("en-US", Languages.getLanguageForLocale(Locale.KOREAN).getShortCodeWithCountryAndVariant());
    }

    @Test
    public void testGetLanguageForLocale_11() {
        assertEquals("en-US", Languages.getLanguageForLocale(new Locale("zz")).getShortCodeWithCountryAndVariant());
    }
}
