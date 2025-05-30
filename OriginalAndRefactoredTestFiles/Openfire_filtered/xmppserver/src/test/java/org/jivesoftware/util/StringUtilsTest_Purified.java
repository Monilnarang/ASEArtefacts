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

public class StringUtilsTest_Purified {

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
    public void testValidDomainNames_1() {
        assertValidDomainName("www.mycompany.com");
    }

    @Test
    public void testValidDomainNames_2() {
        assertValidDomainName("www.my-company.com");
    }

    @Test
    public void testValidDomainNames_3() {
        assertValidDomainName("abc.de");
    }

    @Test
    public void testValidDomainNames_4() {
        assertValidDomainName("tronçon.be", "xn--tronon-zua.be");
    }

    @Test
    public void testValidDomainNames_5() {
        assertValidDomainName("öbb.at", "xn--bb-eka.at");
    }

    @Test
    public void testInvalidDomainNames_1() {
        assertInvalidDomainName("www.my_company.com", "Contains non-LDH characters");
    }

    @Test
    public void testInvalidDomainNames_2() {
        assertInvalidDomainName("www.-dash.com", "Has leading or trailing hyphen");
    }

    @Test
    public void testInvalidDomainNames_3() {
        assertInvalidDomainName("www.dash-.com", "Has leading or trailing hyphen");
    }

    @Test
    public void testInvalidDomainNames_4() {
        assertInvalidDomainName("abc.<test>.de", "Contains non-LDH characters");
    }

    @Test
    public void testElapsedTimeInMilliseconds_1() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(0), is("0 ms"));
    }

    @Test
    public void testElapsedTimeInMilliseconds_2() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(1), is("1 ms"));
    }

    @Test
    public void testElapsedTimeInMilliseconds_3() throws Exception {
        assertThat(StringUtils.getFullElapsedTime(250), is("250 ms"));
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

    @Test
    public void backToBackQuotedStringsShouldFormSingleToken_1() {
        assertEquals(List.of("foobarbaz"), StringUtils.shellSplit("\"foo\"'bar'baz"));
    }

    @Test
    public void backToBackQuotedStringsShouldFormSingleToken_2() {
        assertEquals(List.of("three four"), StringUtils.shellSplit("\"three\"' 'four"));
    }
}
