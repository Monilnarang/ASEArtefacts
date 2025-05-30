package org.apache.commons.lang3;

import static org.apache.commons.lang3.JavaVersion.JAVA_1_4;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LocaleUtilsTest_Purified extends AbstractLangTest {

    private static final Locale LOCALE_EN = new Locale("en", "");

    private static final Locale LOCALE_EN_US = new Locale("en", "US");

    private static final Locale LOCALE_EN_US_ZZZZ = new Locale("en", "US", "ZZZZ");

    private static final Locale LOCALE_FR = new Locale("fr", "");

    private static final Locale LOCALE_FR_CA = new Locale("fr", "CA");

    private static final Locale LOCALE_QQ = new Locale("qq", "");

    private static final Locale LOCALE_QQ_ZZ = new Locale("qq", "ZZ");

    private static void assertCountriesByLanguage(final String language, final String[] countries) {
        final List<Locale> list = LocaleUtils.countriesByLanguage(language);
        final List<Locale> list2 = LocaleUtils.countriesByLanguage(language);
        assertNotNull(list);
        assertSame(list, list2);
        for (final String country : countries) {
            boolean found = false;
            for (final Locale locale : list) {
                assertTrue(StringUtils.isEmpty(locale.getVariant()));
                assertEquals(language, locale.getLanguage());
                if (country.equals(locale.getCountry())) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Could not find language: " + country + " for country: " + language);
        }
        assertUnmodifiableCollection(list);
    }

    private static void assertLanguageByCountry(final String country, final String[] languages) {
        final List<Locale> list = LocaleUtils.languagesByCountry(country);
        final List<Locale> list2 = LocaleUtils.languagesByCountry(country);
        assertNotNull(list);
        assertSame(list, list2);
        for (final String language : languages) {
            boolean found = false;
            for (final Locale locale : list) {
                assertTrue(StringUtils.isEmpty(locale.getVariant()));
                assertEquals(country, locale.getCountry());
                if (language.equals(locale.getLanguage())) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Could not find language: " + language + " for country: " + country);
        }
        assertUnmodifiableCollection(list);
    }

    private static void assertLocaleLookupList(final Locale locale, final Locale defaultLocale, final Locale[] expected) {
        final List<Locale> localeList = defaultLocale == null ? LocaleUtils.localeLookupList(locale) : LocaleUtils.localeLookupList(locale, defaultLocale);
        assertEquals(expected.length, localeList.size());
        assertEquals(Arrays.asList(expected), localeList);
        assertUnmodifiableCollection(localeList);
    }

    private static void assertUnmodifiableCollection(final Collection<?> coll) {
        assertThrows(UnsupportedOperationException.class, () -> coll.add(null));
    }

    private static void assertValidToLocale(final String language) {
        final Locale locale = LocaleUtils.toLocale(language);
        assertNotNull(locale, "valid locale");
        assertEquals(language, locale.getLanguage());
        assertTrue(StringUtils.isEmpty(locale.getCountry()));
        assertTrue(StringUtils.isEmpty(locale.getVariant()));
    }

    private static void assertValidToLocale(final String localeString, final String language, final String country) {
        final Locale locale = LocaleUtils.toLocale(localeString);
        assertNotNull(locale, "valid locale");
        assertEquals(language, locale.getLanguage());
        assertEquals(country, locale.getCountry());
        assertTrue(StringUtils.isEmpty(locale.getVariant()));
    }

    private static void assertValidToLocale(final String localeString, final String language, final String country, final String variant) {
        final Locale locale = LocaleUtils.toLocale(localeString);
        assertNotNull(locale, "valid locale");
        assertEquals(language, locale.getLanguage());
        assertEquals(country, locale.getCountry());
        assertEquals(variant, locale.getVariant());
    }

    @BeforeEach
    public void setUp() {
        LocaleUtils.isAvailableLocale(Locale.getDefault());
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new LocaleUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = LocaleUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(LocaleUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(LocaleUtils.class.getModifiers()));
    }

    @Test
    public void testLang328_1() {
        assertValidToLocale("fr__P", "fr", "", "P");
    }

    @Test
    public void testLang328_2() {
        assertValidToLocale("fr__POSIX", "fr", "", "POSIX");
    }

    @Test
    public void testLanguageAndUNM49Numeric3AreaCodeLang1312_1() {
        assertValidToLocale("en_001", "en", "001");
    }

    @Test
    public void testLanguageAndUNM49Numeric3AreaCodeLang1312_2() {
        assertValidToLocale("en_150", "en", "150");
    }

    @Test
    public void testLanguageAndUNM49Numeric3AreaCodeLang1312_3() {
        assertValidToLocale("ar_001", "ar", "001");
    }

    @Test
    public void testLanguageAndUNM49Numeric3AreaCodeLang1312_4() {
        assertValidToLocale("en_001_GB", "en", "001", "GB");
    }

    @Test
    public void testLanguageAndUNM49Numeric3AreaCodeLang1312_5() {
        assertValidToLocale("en_150_US", "en", "150", "US");
    }

    @Test
    public void testToLocale_Locale_defaults_1() {
        assertNull(LocaleUtils.toLocale((String) null));
    }

    @Test
    public void testToLocale_Locale_defaults_2() {
        assertEquals(Locale.getDefault(), LocaleUtils.toLocale((Locale) null));
    }

    @Test
    public void testToLocale_Locale_defaults_3() {
        assertEquals(Locale.getDefault(), LocaleUtils.toLocale(Locale.getDefault()));
    }
}
