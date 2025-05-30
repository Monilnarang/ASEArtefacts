package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.IDN;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.routines.DomainValidator.ArrayType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DomainValidatorTest_Purified {

    private static void closeQuietly(final Closeable in) {
        if (in != null) {
            try {
                in.close();
            } catch (final IOException ignore) {
            }
        }
    }

    private static long download(final File file, final String tldUrl, final long timestamp) throws IOException {
        final int hour = 60 * 60 * 1000;
        final long modTime;
        if (file.canRead()) {
            modTime = file.lastModified();
            if (modTime > System.currentTimeMillis() - hour) {
                System.out.println("Skipping download - found recent " + file);
                return modTime;
            }
        } else {
            modTime = 0;
        }
        final HttpURLConnection hc = (HttpURLConnection) new URL(tldUrl).openConnection();
        if (modTime > 0) {
            final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            final String since = sdf.format(new Date(modTime));
            hc.addRequestProperty("If-Modified-Since", since);
            System.out.println("Found " + file + " with date " + since);
        }
        if (hc.getResponseCode() == 304) {
            System.out.println("Already have most recent " + tldUrl);
        } else {
            System.out.println("Downloading " + tldUrl);
            try (InputStream is = hc.getInputStream()) {
                Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println("Done");
        }
        return file.lastModified();
    }

    private static Map<String, String[]> getHtmlInfo(final File f) throws IOException {
        final Map<String, String[]> info = new HashMap<>();
        final Pattern domain = Pattern.compile(".*<a href=\"/domains/root/db/([^.]+)\\.html");
        final Pattern type = Pattern.compile("\\s+<td>([^<]+)</td>");
        final Pattern comment = Pattern.compile("\\s+<td>([^<]+)</td>");
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                final Matcher m = domain.matcher(line);
                if (m.lookingAt()) {
                    final String dom = m.group(1);
                    String typ = "??";
                    String com = "??";
                    line = br.readLine();
                    while (line.matches("^\\s*$")) {
                        line = br.readLine();
                    }
                    final Matcher t = type.matcher(line);
                    if (t.lookingAt()) {
                        typ = t.group(1);
                        line = br.readLine();
                        if (line.matches("\\s+<!--.*")) {
                            while (!line.matches(".*-->.*")) {
                                line = br.readLine();
                            }
                            line = br.readLine();
                        }
                        while (!line.matches(".*</td>.*")) {
                            line += " " + br.readLine();
                        }
                        final Matcher n = comment.matcher(line);
                        if (n.lookingAt()) {
                            com = n.group(1);
                        }
                        if (com.contains("Not assigned") || com.contains("Retired") || typ.equals("test")) {
                        } else {
                            info.put(dom.toLowerCase(Locale.ENGLISH), new String[] { typ, com });
                        }
                    } else {
                        System.err.println("Unexpected type: " + line);
                    }
                }
            }
        }
        return info;
    }

    private static boolean isInIanaList(final String arrayName, final Set<String> ianaTlds) throws Exception {
        final Field f = DomainValidator.class.getDeclaredField(arrayName);
        final boolean isPrivate = Modifier.isPrivate(f.getModifiers());
        if (isPrivate) {
            f.setAccessible(true);
        }
        final String[] array = (String[]) f.get(null);
        try {
            return isInIanaList(arrayName, array, ianaTlds);
        } finally {
            if (isPrivate) {
                f.setAccessible(false);
            }
        }
    }

    private static boolean isInIanaList(final String name, final String[] array, final Set<String> ianaTlds) {
        for (final String element : array) {
            if (!ianaTlds.contains(element)) {
                System.out.println(name + " contains unexpected value: " + element);
                return false;
            }
        }
        return true;
    }

    private static boolean isLowerCase(final String string) {
        return string.equals(string.toLowerCase(Locale.ENGLISH));
    }

    private static boolean isNotInRootZone(final String domain) {
        final String tldUrl = "https://www.iana.org/domains/root/db/" + domain + ".html";
        final File rootCheck = new File("target", "tld_" + domain + ".html");
        BufferedReader in = null;
        try {
            download(rootCheck, tldUrl, 0L);
            in = new BufferedReader(new FileReader(rootCheck));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("This domain is not present in the root zone at this time.")) {
                    return true;
                }
            }
            in.close();
        } catch (final IOException ignore) {
        } finally {
            closeQuietly(in);
        }
        return false;
    }

    private static boolean isSortedLowerCase(final String arrayName) throws Exception {
        final Field f = DomainValidator.class.getDeclaredField(arrayName);
        final boolean isPrivate = Modifier.isPrivate(f.getModifiers());
        if (isPrivate) {
            f.setAccessible(true);
        }
        final String[] array = (String[]) f.get(null);
        try {
            return isSortedLowerCase(arrayName, array);
        } finally {
            if (isPrivate) {
                f.setAccessible(false);
            }
        }
    }

    private static boolean isSortedLowerCase(final String name, final String[] array) {
        boolean sorted = true;
        boolean strictlySorted = true;
        final int length = array.length;
        boolean lowerCase = isLowerCase(array[length - 1]);
        for (int i = 0; i < length - 1; i++) {
            final String entry = array[i];
            final String nextEntry = array[i + 1];
            final int cmp = entry.compareTo(nextEntry);
            if (cmp > 0) {
                System.out.println("Out of order entry: " + entry + " < " + nextEntry + " in " + name);
                sorted = false;
            } else if (cmp == 0) {
                strictlySorted = false;
                System.out.println("Duplicated entry: " + entry + " in " + name);
            }
            if (!isLowerCase(entry)) {
                System.out.println("Non lowerCase entry: " + entry + " in " + name);
                lowerCase = false;
            }
        }
        return sorted && strictlySorted && lowerCase;
    }

    public static void main(final String[] a) throws Exception {
        boolean ok = true;
        for (final String list : new String[] { "INFRASTRUCTURE_TLDS", "COUNTRY_CODE_TLDS", "GENERIC_TLDS", "LOCAL_TLDS" }) {
            ok &= isSortedLowerCase(list);
        }
        if (!ok) {
            System.out.println("Fix arrays before retrying; cannot continue");
            return;
        }
        final Set<String> ianaTlds = new HashSet<>();
        final DomainValidator dv = DomainValidator.getInstance();
        final File txtFile = new File("target/tlds-alpha-by-domain.txt");
        final long timestamp = download(txtFile, "https://data.iana.org/TLD/tlds-alpha-by-domain.txt", 0L);
        final File htmlFile = new File("target/tlds-alpha-by-domain.html");
        download(htmlFile, "https://www.iana.org/domains/root/db", timestamp);
        final BufferedReader br = new BufferedReader(new FileReader(txtFile));
        String line;
        final String header;
        line = br.readLine();
        if (!line.startsWith("# Version ")) {
            br.close();
            throw new IOException("File does not have expected Version header");
        }
        header = line.substring(2);
        final boolean generateUnicodeTlds = false;
        final Map<String, String[]> htmlInfo = getHtmlInfo(htmlFile);
        final Map<String, String> missingTLD = new TreeMap<>();
        final Map<String, String> missingCC = new TreeMap<>();
        while ((line = br.readLine()) != null) {
            if (!line.startsWith("#")) {
                final String unicodeTld;
                final String asciiTld = line.toLowerCase(Locale.ENGLISH);
                if (line.startsWith("XN--")) {
                    unicodeTld = IDN.toUnicode(line);
                } else {
                    unicodeTld = asciiTld;
                }
                if (!dv.isValidTld(asciiTld)) {
                    final String[] info = htmlInfo.get(asciiTld);
                    if (info != null) {
                        final String type = info[0];
                        final String comment = info[1];
                        if ("country-code".equals(type)) {
                            missingCC.put(asciiTld, unicodeTld + " " + comment);
                            if (generateUnicodeTlds) {
                                missingCC.put(unicodeTld, asciiTld + " " + comment);
                            }
                        } else {
                            missingTLD.put(asciiTld, unicodeTld + " " + comment);
                            if (generateUnicodeTlds) {
                                missingTLD.put(unicodeTld, asciiTld + " " + comment);
                            }
                        }
                    } else {
                        System.err.println("Expected to find HTML info for " + asciiTld);
                    }
                }
                ianaTlds.add(asciiTld);
                if (generateUnicodeTlds && !unicodeTld.equals(asciiTld)) {
                    ianaTlds.add(unicodeTld);
                }
            }
        }
        br.close();
        int errorsDetected = 0;
        for (final String key : new TreeMap<>(htmlInfo).keySet()) {
            if (!ianaTlds.contains(key)) {
                if (isNotInRootZone(key)) {
                    System.out.println("INFO: HTML entry not yet in root zone: " + key);
                } else {
                    errorsDetected++;
                    System.err.println("WARN: Expected to find text entry for html: " + key);
                }
            }
        }
        if (!missingTLD.isEmpty()) {
            errorsDetected++;
            printMap(header, missingTLD, "GENERIC_TLDS");
        }
        if (!missingCC.isEmpty()) {
            errorsDetected++;
            printMap(header, missingCC, "COUNTRY_CODE_TLDS");
        }
        if (!isInIanaList("INFRASTRUCTURE_TLDS", ianaTlds)) {
            errorsDetected++;
        }
        if (!isInIanaList("COUNTRY_CODE_TLDS", ianaTlds)) {
            errorsDetected++;
        }
        if (!isInIanaList("GENERIC_TLDS", ianaTlds)) {
            errorsDetected++;
        }
        System.out.println("Finished checks");
        if (errorsDetected > 0) {
            throw new RuntimeException("Errors detected: " + errorsDetected);
        }
    }

    private static void printMap(final String header, final Map<String, String> map, final String string) {
        System.out.println("Entries missing from " + string + " List\n");
        if (header != null) {
            System.out.println("        // Taken from " + header);
        }
        for (final Entry<String, String> me : map.entrySet()) {
            System.out.println("        \"" + me.getKey() + "\", // " + me.getValue());
        }
        System.out.println("\nDone");
    }

    private DomainValidator validator;

    @BeforeEach
    public void setUp() {
        validator = DomainValidator.getInstance();
    }

    @Test
    public void testDomainNoDots_1() {
        assertTrue(validator.isValidDomainSyntax("a"), "a (alpha) should validate");
    }

    @Test
    public void testDomainNoDots_2() {
        assertTrue(validator.isValidDomainSyntax("9"), "9 (alphanum) should validate");
    }

    @Test
    public void testDomainNoDots_3() {
        assertTrue(validator.isValidDomainSyntax("c-z"), "c-z (alpha - alpha) should validate");
    }

    @Test
    public void testDomainNoDots_4() {
        assertFalse(validator.isValidDomainSyntax("c-"), "c- (alpha -) should fail");
    }

    @Test
    public void testDomainNoDots_5() {
        assertFalse(validator.isValidDomainSyntax("-c"), "-c (- alpha) should fail");
    }

    @Test
    public void testDomainNoDots_6() {
        assertFalse(validator.isValidDomainSyntax("-"), "- (-) should fail");
    }

    @Test
    public void testGetArray_1() {
        assertNotNull(DomainValidator.getTLDEntries(ArrayType.COUNTRY_CODE_MINUS));
    }

    @Test
    public void testGetArray_2() {
        assertNotNull(DomainValidator.getTLDEntries(ArrayType.COUNTRY_CODE_PLUS));
    }

    @Test
    public void testGetArray_3() {
        assertNotNull(DomainValidator.getTLDEntries(ArrayType.GENERIC_MINUS));
    }

    @Test
    public void testGetArray_4() {
        assertNotNull(DomainValidator.getTLDEntries(ArrayType.GENERIC_PLUS));
    }

    @Test
    public void testGetArray_5() {
        assertNotNull(DomainValidator.getTLDEntries(ArrayType.LOCAL_MINUS));
    }

    @Test
    public void testGetArray_6() {
        assertNotNull(DomainValidator.getTLDEntries(ArrayType.LOCAL_PLUS));
    }

    @Test
    public void testGetArray_7() {
        assertNotNull(DomainValidator.getTLDEntries(ArrayType.COUNTRY_CODE_RO));
    }

    @Test
    public void testGetArray_8() {
        assertNotNull(DomainValidator.getTLDEntries(ArrayType.GENERIC_RO));
    }

    @Test
    public void testGetArray_9() {
        assertNotNull(DomainValidator.getTLDEntries(ArrayType.INFRASTRUCTURE_RO));
    }

    @Test
    public void testGetArray_10() {
        assertNotNull(DomainValidator.getTLDEntries(ArrayType.LOCAL_RO));
    }

    @Test
    public void testIDNJava6OrLater_1() {
        assertTrue(validator.isValid("www.b\u00fccher.ch"), "b\u00fccher.ch should validate");
    }

    @Test
    public void testIDNJava6OrLater_2() {
        assertTrue(validator.isValid("xn--d1abbgf6aiiy.xn--p1ai"), "xn--d1abbgf6aiiy.xn--p1ai should validate");
    }

    @Test
    public void testIDNJava6OrLater_3() {
        assertTrue(validator.isValid("президент.рф"), "президент.рф should validate");
    }

    @Test
    public void testIDNJava6OrLater_4() {
        assertFalse(validator.isValid("www.\uFFFD.ch"), "www.\uFFFD.ch FFFD should fail");
    }

    @Test
    public void testInvalidDomains_1() {
        assertFalse(validator.isValid(".org"), "bare TLD .org shouldn't validate");
    }

    @Test
    public void testInvalidDomains_2() {
        assertFalse(validator.isValid(" apache.org "), "domain name with spaces shouldn't validate");
    }

    @Test
    public void testInvalidDomains_3() {
        assertFalse(validator.isValid("apa che.org"), "domain name containing spaces shouldn't validate");
    }

    @Test
    public void testInvalidDomains_4() {
        assertFalse(validator.isValid("-testdomain.name"), "domain name starting with dash shouldn't validate");
    }

    @Test
    public void testInvalidDomains_5() {
        assertFalse(validator.isValid("testdomain-.name"), "domain name ending with dash shouldn't validate");
    }

    @Test
    public void testInvalidDomains_6() {
        assertFalse(validator.isValid("---c.com"), "domain name starting with multiple dashes shouldn't validate");
    }

    @Test
    public void testInvalidDomains_7() {
        assertFalse(validator.isValid("c--.com"), "domain name ending with multiple dashes shouldn't validate");
    }

    @Test
    public void testInvalidDomains_8() {
        assertFalse(validator.isValid("apache.rog"), "domain name with invalid TLD shouldn't validate");
    }

    @Test
    public void testInvalidDomains_9() {
        assertFalse(validator.isValid("http://www.apache.org"), "URL shouldn't validate");
    }

    @Test
    public void testInvalidDomains_10() {
        assertFalse(validator.isValid(" "), "Empty string shouldn't validate as domain name");
    }

    @Test
    public void testInvalidDomains_11() {
        assertFalse(validator.isValid(null), "Null shouldn't validate as domain name");
    }

    @Test
    public void testRFC2396domainlabel_1() {
        assertTrue(validator.isValid("a.ch"), "a.ch should validate");
    }

    @Test
    public void testRFC2396domainlabel_2() {
        assertTrue(validator.isValid("9.ch"), "9.ch should validate");
    }

    @Test
    public void testRFC2396domainlabel_3() {
        assertTrue(validator.isValid("az.ch"), "az.ch should validate");
    }

    @Test
    public void testRFC2396domainlabel_4() {
        assertTrue(validator.isValid("09.ch"), "09.ch should validate");
    }

    @Test
    public void testRFC2396domainlabel_5() {
        assertTrue(validator.isValid("9-1.ch"), "9-1.ch should validate");
    }

    @Test
    public void testRFC2396domainlabel_6() {
        assertFalse(validator.isValid("91-.ch"), "91-.ch should not validate");
    }

    @Test
    public void testRFC2396domainlabel_7() {
        assertFalse(validator.isValid("-.ch"), "-.ch should not validate");
    }

    @Test
    public void testRFC2396toplabel_1() {
        assertTrue(validator.isValidDomainSyntax("a.c"), "a.c (alpha) should validate");
    }

    @Test
    public void testRFC2396toplabel_2() {
        assertTrue(validator.isValidDomainSyntax("a.cc"), "a.cc (alpha alpha) should validate");
    }

    @Test
    public void testRFC2396toplabel_3() {
        assertTrue(validator.isValidDomainSyntax("a.c9"), "a.c9 (alpha alphanum) should validate");
    }

    @Test
    public void testRFC2396toplabel_4() {
        assertTrue(validator.isValidDomainSyntax("a.c-9"), "a.c-9 (alpha - alphanum) should validate");
    }

    @Test
    public void testRFC2396toplabel_5() {
        assertTrue(validator.isValidDomainSyntax("a.c-z"), "a.c-z (alpha - alpha) should validate");
    }

    @Test
    public void testRFC2396toplabel_6() {
        assertFalse(validator.isValidDomainSyntax("a.9c"), "a.9c (alphanum alpha) should fail");
    }

    @Test
    public void testRFC2396toplabel_7() {
        assertFalse(validator.isValidDomainSyntax("a.c-"), "a.c- (alpha -) should fail");
    }

    @Test
    public void testRFC2396toplabel_8() {
        assertFalse(validator.isValidDomainSyntax("a.-"), "a.- (-) should fail");
    }

    @Test
    public void testRFC2396toplabel_9() {
        assertFalse(validator.isValidDomainSyntax("a.-9"), "a.-9 (- alphanum) should fail");
    }

    @Test
    public void testTopLevelDomains_1() {
        assertTrue(validator.isValidInfrastructureTld(".arpa"), ".arpa should validate as iTLD");
    }

    @Test
    public void testTopLevelDomains_2() {
        assertFalse(validator.isValidInfrastructureTld(".com"), ".com shouldn't validate as iTLD");
    }

    @Test
    public void testTopLevelDomains_3() {
        assertTrue(validator.isValidGenericTld(".name"), ".name should validate as gTLD");
    }

    @Test
    public void testTopLevelDomains_4() {
        assertFalse(validator.isValidGenericTld(".us"), ".us shouldn't validate as gTLD");
    }

    @Test
    public void testTopLevelDomains_5() {
        assertTrue(validator.isValidCountryCodeTld(".uk"), ".uk should validate as ccTLD");
    }

    @Test
    public void testTopLevelDomains_6() {
        assertFalse(validator.isValidCountryCodeTld(".org"), ".org shouldn't validate as ccTLD");
    }

    @Test
    public void testTopLevelDomains_7() {
        assertTrue(validator.isValidTld(".COM"), ".COM should validate as TLD");
    }

    @Test
    public void testTopLevelDomains_8() {
        assertTrue(validator.isValidTld(".BiZ"), ".BiZ should validate as TLD");
    }

    @Test
    public void testTopLevelDomains_9() {
        assertFalse(validator.isValid(".nope"), "invalid TLD shouldn't validate");
    }

    @Test
    public void testTopLevelDomains_10() {
        assertFalse(validator.isValid(""), "empty string shouldn't validate as TLD");
    }

    @Test
    public void testTopLevelDomains_11() {
        assertFalse(validator.isValid(null), "null shouldn't validate as TLD");
    }

    @Test
    public void testValidDomains_1() {
        assertTrue(validator.isValid("apache.org"), "apache.org should validate");
    }

    @Test
    public void testValidDomains_2() {
        assertTrue(validator.isValid("www.google.com"), "www.google.com should validate");
    }

    @Test
    public void testValidDomains_3() {
        assertTrue(validator.isValid("test-domain.com"), "test-domain.com should validate");
    }

    @Test
    public void testValidDomains_4() {
        assertTrue(validator.isValid("test---domain.com"), "test---domain.com should validate");
    }

    @Test
    public void testValidDomains_5() {
        assertTrue(validator.isValid("test-d-o-m-ain.com"), "test-d-o-m-ain.com should validate");
    }

    @Test
    public void testValidDomains_6() {
        assertTrue(validator.isValid("as.uk"), "two-letter domain label should validate");
    }

    @Test
    public void testValidDomains_7() {
        assertTrue(validator.isValid("ApAchE.Org"), "case-insensitive ApAchE.Org should validate");
    }

    @Test
    public void testValidDomains_8() {
        assertTrue(validator.isValid("z.com"), "single-character domain label should validate");
    }

    @Test
    public void testValidDomains_9() {
        assertTrue(validator.isValid("i.have.an-example.domain.name"), "i.have.an-example.domain.name should validate");
    }
}
