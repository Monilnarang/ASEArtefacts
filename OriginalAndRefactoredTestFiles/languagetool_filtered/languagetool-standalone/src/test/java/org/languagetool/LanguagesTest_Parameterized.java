package org.languagetool;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.Locale;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LanguagesTest_Parameterized {

    @Test
    public void testGetLanguageForName_3() {
        assertEquals(null, Languages.getLanguageForName("Foobar"));
    }

    @Test
    public void testHasPremium_4() {
        assertFalse(Languages.hasPremium("org.languagetool.language.Danish"));
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
    public void testGetLanguageForLocale_10() {
        assertEquals("en-US", Languages.getLanguageForLocale(Locale.KOREAN).getShortCodeWithCountryAndVariant());
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsLanguageSupported_1to9")
    public void testIsLanguageSupported_1to9(String param1) {
        Assert.assertTrue(Languages.isLanguageSupported(param1));
    }

    static public Stream<Arguments> Provider_testIsLanguageSupported_1to9() {
        return Stream.of(arguments("xx"), arguments("XX"), arguments("en-US"), arguments("en-us"), arguments("EN-US"), arguments("de"), arguments("de-DE"), arguments("de-DE-x-simple-language"), arguments("de-DE-x-simple-LANGUAGE"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsLanguageSupported_10to12")
    public void testIsLanguageSupported_10to12(String param1) {
        assertFalse(Languages.isLanguageSupported(param1));
    }

    static public Stream<Arguments> Provider_testIsLanguageSupported_10to12() {
        return Stream.of(arguments("yy-ZZ"), arguments("zz"), arguments("somthing totally invalid"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetLanguageForName_1to2")
    public void testGetLanguageForName_1to2(String param1, String param2) {
        assertEquals(param1, Languages.getLanguageForName(param2).getShortCodeWithCountryAndVariant());
    }

    static public Stream<Arguments> Provider_testGetLanguageForName_1to2() {
        return Stream.of(arguments("en-US", "English (US)"), arguments("de", "German"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsVariant_1to2")
    public void testIsVariant_1to2(String param1) {
        Assert.assertTrue(Languages.getLanguageForShortCode(param1).isVariant());
    }

    static public Stream<Arguments> Provider_testIsVariant_1to2() {
        return Stream.of(arguments("en-US"), arguments("de-CH"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsVariant_3to4")
    public void testIsVariant_3to4(String param1) {
        assertFalse(Languages.getLanguageForShortCode(param1).isVariant());
    }

    static public Stream<Arguments> Provider_testIsVariant_3to4() {
        return Stream.of(arguments("en"), arguments("de"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testHasPremium_1to3")
    public void testHasPremium_1to3(String param1) {
        assertTrue(Languages.hasPremium(param1));
    }

    static public Stream<Arguments> Provider_testHasPremium_1to3() {
        return Stream.of(arguments("org.languagetool.language.Portuguese"), arguments("org.languagetool.language.GermanyGerman"), arguments("org.languagetool.language.AmericanEnglish"));
    }

    @ParameterizedTest
    @MethodSource("Provider_isHiddenFromGui_1to3")
    public void isHiddenFromGui_1to3(String param1) {
        Assert.assertTrue(Languages.getLanguageForShortCode(param1).isHiddenFromGui());
    }

    static public Stream<Arguments> Provider_isHiddenFromGui_1to3() {
        return Stream.of(arguments("en"), arguments("de"), arguments("pt"));
    }

    @ParameterizedTest
    @MethodSource("Provider_isHiddenFromGui_4to11")
    public void isHiddenFromGui_4to11(String param1) {
        assertFalse(Languages.getLanguageForShortCode(param1).isHiddenFromGui());
    }

    static public Stream<Arguments> Provider_isHiddenFromGui_4to11() {
        return Stream.of(arguments("en-US"), arguments("de-CH"), arguments("ast"), arguments("pl"), arguments("ca-ES"), arguments("ca-ES-valencia"), arguments("de-DE-x-simple-language"), arguments("de-DE"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetLanguageForLocale_3to6")
    public void testGetLanguageForLocale_3to6(String param1, String param2, String param3) {
        assertEquals(param1, Languages.getLanguageForLocale(new Locale(param2, param3)).getShortCodeWithCountryAndVariant());
    }

    static public Stream<Arguments> Provider_testGetLanguageForLocale_3to6() {
        return Stream.of(arguments("de-DE", "de", "DE"), arguments("de-AT", "de", "AT"), arguments("en-US", "en", "US"), arguments("en-GB", "en", "GB"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetLanguageForLocale_7to9_11")
    public void testGetLanguageForLocale_7to9_11(String param1, String param2) {
        assertEquals(param1, Languages.getLanguageForLocale(new Locale(param2)).getShortCodeWithCountryAndVariant());
    }

    static public Stream<Arguments> Provider_testGetLanguageForLocale_7to9_11() {
        return Stream.of(arguments("en-US", "en"), arguments("de-DE", "de"), arguments("pl-PL", "pl"), arguments("en-US", "zz"));
    }
}
