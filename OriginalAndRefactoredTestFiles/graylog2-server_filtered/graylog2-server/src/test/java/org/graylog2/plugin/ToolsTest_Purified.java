package org.graylog2.plugin;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.common.net.InetAddresses;
import org.graylog2.inputs.TestHelper;
import org.joda.time.DateTime;
import org.junit.Test;
import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ToolsTest_Purified {

    @Test
    public void testGetUriWithPort_1() throws Exception {
        final URI uriWithPort = new URI("http://example.com:12345");
        assertEquals(12345, Tools.getUriWithPort(uriWithPort, 1).getPort());
    }

    @Test
    public void testGetUriWithPort_2() throws Exception {
        final URI httpUriWithoutPort = new URI("http://example.com");
        assertEquals(80, Tools.getUriWithPort(httpUriWithoutPort, 1).getPort());
    }

    @Test
    public void testGetUriWithPort_3() throws Exception {
        final URI httpsUriWithoutPort = new URI("https://example.com");
        assertEquals(443, Tools.getUriWithPort(httpsUriWithoutPort, 1).getPort());
    }

    @Test
    public void testGetUriWithPort_4() throws Exception {
        final URI uriWithUnknownSchemeAndWithoutPort = new URI("foobar://example.com");
        assertEquals(1, Tools.getUriWithPort(uriWithUnknownSchemeAndWithoutPort, 1).getPort());
    }

    @Test
    public void testGetUriWithScheme_1() throws Exception {
        assertEquals("gopher", Tools.getUriWithScheme(new URI("http://example.com"), "gopher").getScheme());
    }

    @Test
    public void testGetUriWithScheme_2() throws Exception {
        assertNull(Tools.getUriWithScheme(new URI("http://example.com"), null).getScheme());
    }

    @Test
    public void testGetUriWithScheme_3() throws Exception {
        assertNull(Tools.getUriWithScheme(null, "http"));
    }

    @Test
    public void testGetUTCTimestampWithMilliseconds_1() {
        assertTrue(Tools.getUTCTimestampWithMilliseconds() > 0.0d);
    }

    @Test
    public void testGetUTCTimestampWithMilliseconds_2() {
        assertTrue(Tools.getUTCTimestampWithMilliseconds(Instant.now().toEpochMilli()) > 0.0d);
    }

    @Test
    public void testSyslogLevelToReadable_1() {
        assertEquals("Invalid", Tools.syslogLevelToReadable(1337));
    }

    @Test
    public void testSyslogLevelToReadable_2() {
        assertEquals("Emergency", Tools.syslogLevelToReadable(0));
    }

    @Test
    public void testSyslogLevelToReadable_3() {
        assertEquals("Critical", Tools.syslogLevelToReadable(2));
    }

    @Test
    public void testSyslogLevelToReadable_4() {
        assertEquals("Informational", Tools.syslogLevelToReadable(6));
    }

    @Test
    public void testSyslogFacilityToReadable_1() {
        assertEquals("Unknown", Tools.syslogFacilityToReadable(9001));
    }

    @Test
    public void testSyslogFacilityToReadable_2() {
        assertEquals("kernel", Tools.syslogFacilityToReadable(0));
    }

    @Test
    public void testSyslogFacilityToReadable_3() {
        assertEquals("FTP", Tools.syslogFacilityToReadable(11));
    }

    @Test
    public void testSyslogFacilityToReadable_4() {
        assertEquals("local6", Tools.syslogFacilityToReadable(22));
    }

    @Test
    public void testSafeSubstring_1() {
        assertNull(Tools.safeSubstring(null, 10, 20));
    }

    @Test
    public void testSafeSubstring_2() {
        assertNull(Tools.safeSubstring("", 10, 20));
    }

    @Test
    public void testSafeSubstring_3() {
        assertNull(Tools.safeSubstring("foo", -1, 2));
    }

    @Test
    public void testSafeSubstring_4() {
        assertNull(Tools.safeSubstring("foo", 1, 0));
    }

    @Test
    public void testSafeSubstring_5() {
        assertNull(Tools.safeSubstring("foo", 5, 2));
    }

    @Test
    public void testSafeSubstring_6() {
        assertNull(Tools.safeSubstring("foo", 1, 1));
    }

    @Test
    public void testSafeSubstring_7() {
        assertNull(Tools.safeSubstring("foo", 2, 1));
    }

    @Test
    public void testSafeSubstring_8() {
        assertEquals("justatest", Tools.safeSubstring("justatest", 0, 9));
    }

    @Test
    public void testSafeSubstring_9() {
        assertEquals("tat", Tools.safeSubstring("justatest", 3, 6));
    }

    @Test
    public void testSafeSubstring_10() {
        assertEquals("just", Tools.safeSubstring("justatest", 0, 4));
    }

    @Test
    public void testSafeSubstring_11() {
        assertEquals("atest", Tools.safeSubstring("justatest", 4, 9));
    }

    @Test
    public void testGetNumberForDifferentFormats_1() {
        assertEquals(1, Tools.getNumber(1, null).intValue(), 1);
    }

    @Test
    public void testGetNumberForDifferentFormats_2() {
        assertEquals(1.0, Tools.getNumber(1, null).doubleValue(), 0.0);
    }

    @Test
    public void testGetNumberForDifferentFormats_3() {
        assertEquals(42, Tools.getNumber(42.23, null).intValue());
    }

    @Test
    public void testGetNumberForDifferentFormats_4() {
        assertEquals(42.23, Tools.getNumber(42.23, null).doubleValue(), 0.0);
    }

    @Test
    public void testGetNumberForDifferentFormats_5() {
        assertEquals(17, Tools.getNumber("17", null).intValue());
    }

    @Test
    public void testGetNumberForDifferentFormats_6() {
        assertEquals(17.0, Tools.getNumber("17", null).doubleValue(), 0.0);
    }

    @Test
    public void testGetNumberForDifferentFormats_7() {
        assertEquals(23, Tools.getNumber("23.42", null).intValue());
    }

    @Test
    public void testGetNumberForDifferentFormats_8() {
        assertEquals(23.42, Tools.getNumber("23.42", null).doubleValue(), 0.0);
    }

    @Test
    public void testGetNumberForDifferentFormats_9() {
        assertNull(Tools.getNumber(null, null));
    }

    @Test
    public void testGetNumberForDifferentFormats_10() {
        assertNull(Tools.getNumber(null, null));
    }

    @Test
    public void testGetNumberForDifferentFormats_11() {
        assertEquals(1, Tools.getNumber(null, 1).intValue());
    }

    @Test
    public void testGetNumberForDifferentFormats_12() {
        assertEquals(1.0, Tools.getNumber(null, 1).doubleValue(), 0.0);
    }

    @Test
    public void testTimeFormatterWithOptionalMilliseconds_1() {
        assertTrue(DateTime.parse("2013-09-15 02:21:02", Tools.timeFormatterWithOptionalMilliseconds()).toString().startsWith("2013-09-15T02:21:02.000"));
    }

    @Test
    public void testTimeFormatterWithOptionalMilliseconds_2() {
        assertTrue(DateTime.parse("2013-09-15 02:21:02.123", Tools.timeFormatterWithOptionalMilliseconds()).toString().startsWith("2013-09-15T02:21:02.123"));
    }

    @Test
    public void testTimeFormatterWithOptionalMilliseconds_3() {
        assertTrue(DateTime.parse("2013-09-15 02:21:02.12", Tools.timeFormatterWithOptionalMilliseconds()).toString().startsWith("2013-09-15T02:21:02.120"));
    }

    @Test
    public void testTimeFormatterWithOptionalMilliseconds_4() {
        assertTrue(DateTime.parse("2013-09-15 02:21:02.1", Tools.timeFormatterWithOptionalMilliseconds()).toString().startsWith("2013-09-15T02:21:02.100"));
    }

    @Test
    public void testTimeFromDouble_1() {
        assertTrue(Tools.dateTimeFromDouble(1381076986.306509).toString().startsWith("2013-10-06T"));
    }

    @Test
    public void testTimeFromDouble_2() {
        assertTrue(Tools.dateTimeFromDouble(1381076986).toString().startsWith("2013-10-06T"));
    }

    @Test
    public void testTimeFromDouble_3() {
        assertTrue(Tools.dateTimeFromDouble(1381079085.6).toString().startsWith("2013-10-06T"));
    }

    @Test
    public void testTimeFromDouble_4() {
        assertTrue(Tools.dateTimeFromDouble(1381079085.06).toString().startsWith("2013-10-06T"));
    }

    @Test
    public void isWildcardAddress_1() {
        assertTrue(Tools.isWildcardInetAddress(InetAddresses.forString("0.0.0.0")));
    }

    @Test
    public void isWildcardAddress_2() {
        assertTrue(Tools.isWildcardInetAddress(InetAddresses.forString("::")));
    }

    @Test
    public void isWildcardAddress_3() {
        assertFalse(Tools.isWildcardInetAddress(null));
    }

    @Test
    public void isWildcardAddress_4() {
        assertFalse(Tools.isWildcardInetAddress(InetAddresses.forString("127.0.0.1")));
    }

    @Test
    public void isWildcardAddress_5() {
        assertFalse(Tools.isWildcardInetAddress(InetAddresses.forString("::1")));
    }

    @Test
    public void isWildcardAddress_6() {
        assertFalse(Tools.isWildcardInetAddress(InetAddresses.forString("198.51.100.23")));
    }

    @Test
    public void isWildcardAddress_7() {
        assertFalse(Tools.isWildcardInetAddress(InetAddresses.forString("2001:DB8::42")));
    }
}
