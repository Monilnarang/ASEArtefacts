package org.jivesoftware.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilsTest_Parameterized {

    @BeforeEach
    public void setUp() {
        JiveGlobals.setLocale(Locale.ENGLISH);
    }

    private void assertValidDomainName(String domain) {
        assertValidDomainName(domain, domain);
    }

    private void assertValidDomainName(String domain, String expected) {
        assertEquals(expected, StringUtils.validateDomainName(domain), "Domain should be valid: " + domain);
    }

    private void assertInvalidDomainName(String domain, String expectedCause) {
        try {
            StringUtils.validateDomainName(domain);
            fail("Domain should not be valid: " + domain);
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void testElapsedTimeInSeconds_1() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofSeconds(1)), is("1 second"));
    }

    @Test
    public void testElapsedTimeInSeconds_2() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofMillis(1001)), is("1 second, 1 ms"));
    }

    @Test
    public void testElapsedTimeInSeconds_3() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofSeconds(30).plus(Duration.ofMillis(30))), is("30 seconds, 30 ms"));
    }

    @Test
    public void testElapsedTimeInMinutes_1() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofMinutes(1)), is("1 minute"));
    }

    @Test
    public void testElapsedTimeInMinutes_2() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofMinutes(1).plus(Duration.ofSeconds(1).plus(Duration.ofMillis(1)))), is("1 minute, 1 second, 1 ms"));
    }

    @Test
    public void testElapsedTimeInMinutes_3() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofMinutes(30).plus(Duration.ofSeconds(30))), is("30 minutes, 30 seconds"));
    }

    @Test
    public void testElapsedTimeInHours_1() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofHours(1)), is("1 hour"));
    }

    @Test
    public void testElapsedTimeInHours_2() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofHours(1).plus(Duration.ofMinutes(1)).plus(Duration.ofSeconds(1)).plus(Duration.ofMillis(1))), is("1 hour, 1 minute, 1 second, 1 ms"));
    }

    @Test
    public void testElapsedTimeInHours_3() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofHours(10).plus(Duration.ofMinutes(30))), is("10 hours, 30 minutes"));
    }

    @Test
    public void testElapsedTimeInDays_1() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofDays(1)), is("1 day"));
    }

    @Test
    public void testElapsedTimeInDays_2() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofDays(1).plus(Duration.ofHours(1)).plus(Duration.ofMinutes(1)).plus(Duration.ofSeconds(1)).plus(Duration.ofMillis(1))), is("1 day, 1 hour, 1 minute, 1 second, 1 ms"));
    }

    @Test
    public void testElapsedTimeInDays_3() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(Duration.ofDays(10).plus(Duration.ofHours(10))), is("10 days, 10 hours"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidDomainNames_1to3")
    public void testValidDomainNames_1to3(String param1) {
        assertValidDomainName(param1);
    }

    static public Stream<Arguments> Provider_testValidDomainNames_1to3() {
        return Stream.of(arguments("www.mycompany.com"), arguments("www.my-company.com"), arguments("abc.de"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidDomainNames_4to5")
    public void testValidDomainNames_4to5(String param1, String param2) {
        assertValidDomainName(param1, param2);
    }

    static public Stream<Arguments> Provider_testValidDomainNames_4to5() {
        return Stream.of(arguments("tronçon.be", "xn--tronon-zua.be"), arguments("öbb.at", "xn--bb-eka.at"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvalidDomainNames_1to4")
    public void testInvalidDomainNames_1to4(String param1, String param2) {
        assertInvalidDomainName(param1, param2);
    }

    static public Stream<Arguments> Provider_testInvalidDomainNames_1to4() {
        return Stream.of(arguments("www.my_company.com", "Contains non-LDH characters"), arguments("www.-dash.com", "Has leading or trailing hyphen"), arguments("www.dash-.com", "Has leading or trailing hyphen"), arguments("abc.<test>.de", "Contains non-LDH characters"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testElapsedTimeInMilliseconds_1to3")
    public void testElapsedTimeInMilliseconds_1to3(int param1, String param2) throws Exception {
        assertThat(StringUtils.getFullElapsedTime(param1), is(param2));
    }

    static public Stream<Arguments> Provider_testElapsedTimeInMilliseconds_1to3() {
        return Stream.of(arguments(0, "0 ms"), arguments(1, "1 ms"), arguments(250, "250 ms"));
    }

    @ParameterizedTest
    @MethodSource("Provider_backToBackQuotedStringsShouldFormSingleToken_1to2")
    public void backToBackQuotedStringsShouldFormSingleToken_1to2(String param1, String param2) {
        assertEquals(List.of(param1), StringUtils.shellSplit(param2));
    }

    static public Stream<Arguments> Provider_backToBackQuotedStringsShouldFormSingleToken_1to2() {
        return Stream.of(arguments("foobarbaz", "\"foo\"'bar'baz"), arguments("three four", "\"three\"' 'four"));
    }
}
