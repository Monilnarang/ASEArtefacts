package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.validator.routines.IBANValidator.Validator;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.MethodSource;

public class IBANValidatorTest_Purified {

    private static final IBANValidator VALIDATOR = IBANValidator.getInstance();

    private static final String IBAN_PART = "(?:(\\d+)!([acn]))";

    private static final Pattern IBAN_PAT = Pattern.compile(IBAN_PART + IBAN_PART + IBAN_PART + IBAN_PART + "?" + IBAN_PART + "?" + IBAN_PART + "?" + IBAN_PART + "?");

    private static final String IBAN_REGISTRY = "iban_registry_v99.txt";

    private static final Charset IBAN_REGISTRY_CHARSET = Charset.forName("windows-1252");

    private static final int MS_PER_DAY = 1000 * 60 * 60 * 24;

    private static final long MAX_AGE_DAYS = 180;

    private static final List<String> VALID_IBAN_FIXTURES = Arrays.asList("AD1200012030200359100100", "AE070331234567890123456", "AL47212110090000000235698741", "AT611904300234573201", "AZ21NABZ00000000137010001944", "BA391290079401028494", "BE68539007547034", "BG80BNBG96611020345678", "BH67BMAG00001299123456", "BI4210000100010000332045181", "BR1800000000141455123924100C2", "BR1800360305000010009795493C1", "BR9700360305000010009795493P1", "BY13NBRB3600900000002Z00AB00", "CH9300762011623852957", "CR05015202001026284066", "CY17002001280000001200527600", "CZ6508000000192000145399", "CZ9455000000001011038930", "DE89370400440532013000", "DJ2110002010010409943020008", "DK5000400440116243", "DO28BAGR00000001212453611324", "EE382200221020145685", "EG380019000500000000263180002", "ES9121000418450200051332", "FI2112345600000785", "FI5542345670000081", "AX2112345600000785", "AX5542345670000081", "FK88SC123456789012", "FO6264600001631634", "FR1420041010050500013M02606", "BL6820041010050500013M02606", "GF4120041010050500013M02606", "GP1120041010050500013M02606", "MF8420041010050500013M02606", "MQ5120041010050500013M02606", "NC8420041010050500013M02606", "PF5720041010050500013M02606", "PM3620041010050500013M02606", "RE4220041010050500013M02606", "TF2120041010050500013M02606", "WF9120041010050500013M02606", "YT3120041010050500013M02606", "GB29NWBK60161331926819", "GE29NB0000000101904917", "GI75NWBK000000007099453", "GL8964710001000206", "GR1601101250000000012300695", "GT82TRAJ01020000001210029690", "HN88CABF00000000000250005469", "HR1210010051863000160", "HU42117730161111101800000000", "IE29AIBK93115212345678", "IL620108000000099999999", "IQ98NBIQ850123456789012", "IS140159260076545510730339", "IT60X0542811101000000123456", "JO94CBJO0010000000000131000302", "KW81CBKU0000000000001234560101", "KZ86125KZT5004100100", "LB62099900000001001901229114", "LC55HEMM000100010012001200023015", "LI21088100002324013AA", "LT121000011101001000", "LU280019400644750000", "LY83002048000020100120361", "LV80BANK0000435195001", "LY83002048000020100120361", "MC5811222000010123456789030", "MD24AG000225100013104168", "ME25505000012345678951", "MK07250120000058984", "MN121234123456789123", "MR1300020001010000123456753", "MT84MALT011000012345MTLCAST001S", "MU17BOMM0101101030300200000MUR", "NI45BAPR00000013000003558124", "NL91ABNA0417164300", "NO9386011117947", "OM810180000001299123456", "PK36SCBL0000001123456702", "PL61109010140000071219812874", "PS92PALS000000000400123456702", "PT50000201231234567890154", "QA58DOHB00001234567890ABCDEFG", "RO49AAAA1B31007593840000", "RS35260005601001611379", "RU0204452560040702810412345678901", "SA0380000000608010167519", "SC18SSCB11010000000000001497USD", "SD8811123456789012", "SE4550000000058398257466", "SI56191000000123438", "SI56263300012039086", "SK3112000000198742637541", "SM86U0322509800000000270100", "SO211000001001000100141", "ST68000100010051845310112", "SV62CENR00000000000000700025", "SV43ACAT00000000000000123123", "TL380080012345678910157", "TN5910006035183598478831", "TR330006100519786457841326", "UA213223130000026007233566001", "UA213996220000026007233566001", "VA59001123000012345678", "VG96VPVG0000012345678901", "XK051212012345678906", "YE15CBYE0001018861234567891234");

    private static final List<String> INVALID_IBAN_FIXTURES = Arrays.asList("", "   ", "A", "AB", "FR1420041010050500013m02606", "MT84MALT011000012345mtlcast001s", "LI21088100002324013aa", "QA58DOHB00001234567890abcdefg", "RO49AAAA1b31007593840000", "LC62HEMM000100010012001200023015", "BY00NBRB3600000000000Z00AB00", "ST68000200010192194210112", "SV62CENR0000000000000700025", "NI04BAPR00000013000003558124", "RU1704452522540817810538091310419");

    private static String fmtRE(final String ibanPat, final int ibanLength) {
        final Matcher m = IBAN_PAT.matcher(ibanPat);
        if (!m.matches()) {
            throw new IllegalArgumentException("Unexpected IBAN pattern " + ibanPat);
        }
        final StringBuilder sb = new StringBuilder();
        int len = Integer.parseInt(m.group(1));
        int totLen = len;
        String curType = m.group(2);
        for (int i = 3; i <= m.groupCount(); i += 2) {
            if (m.group(i) == null) {
                break;
            }
            final int count = Integer.parseInt(m.group(i));
            totLen += count;
            final String type = m.group(i + 1);
            if (type.equals(curType)) {
                len += count;
            } else {
                sb.append(formatToRE(curType, len));
                curType = type;
                len = count;
            }
        }
        sb.append(formatToRE(curType, len));
        assertEquals(ibanLength, totLen, "Wrong length for " + ibanPat);
        return sb.toString();
    }

    private static String formatToRE(final String type, final int len) {
        final char ctype = type.charAt(0);
        switch(ctype) {
            case 'n':
                return String.format("\\d{%d}", len);
            case 'a':
                return String.format("[A-Z]{%d}", len);
            case 'c':
                return String.format("[A-Z0-9]{%d}", len);
            default:
                throw new IllegalArgumentException("Unexpected type " + type);
        }
    }

    static Collection<Arguments> ibanRegistrySource() throws Exception {
        final Path ibanRegistry = Paths.get(IBANValidator.class.getResource(IBAN_REGISTRY).toURI());
        final CSVFormat format = CSVFormat.DEFAULT.builder().setDelimiter('\t').build();
        final Reader rdr = Files.newBufferedReader(ibanRegistry, IBAN_REGISTRY_CHARSET);
        CSVRecord country = null;
        CSVRecord cc = null;
        CSVRecord additionalCc = null;
        CSVRecord structure = null;
        CSVRecord length = null;
        try (CSVParser p = new CSVParser(rdr, format)) {
            for (final CSVRecord o : p) {
                final String item = o.get(0);
                switch(item) {
                    case "Name of country":
                        country = o;
                        break;
                    case "IBAN prefix country code (ISO 3166)":
                        cc = o;
                        break;
                    case "Country code includes other countries/territories":
                        additionalCc = o;
                        break;
                    case "IBAN structure":
                        structure = o;
                        break;
                    case "IBAN length":
                        length = o;
                        break;
                    default:
                        break;
                }
            }
        }
        assertNotNull(country);
        assertNotNull(cc);
        assertNotNull(additionalCc);
        assertNotNull(structure);
        assertNotNull(length);
        final Collection<Arguments> result = new ArrayList<>();
        for (int i = 1; i < country.size(); i++) {
            final String ac = additionalCc.get(i);
            final List<String> aCountry = Arrays.stream(ac.split(",")).filter(s -> !"N/A".equals(s)).map(s -> s.replace("(French part)", "")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
            result.add(Arguments.of(country.get(i), cc.get(i), aCountry, Integer.parseInt(length.get(i)), structure.get(i)));
        }
        return result;
    }

    static Collection<Arguments> ibanRegistrySourceExamples() throws Exception {
        final Path ibanRegistry = Paths.get(IBANValidator.class.getResource(IBAN_REGISTRY).toURI());
        final CSVFormat format = CSVFormat.DEFAULT.builder().setDelimiter('\t').build();
        final Reader rdr = Files.newBufferedReader(ibanRegistry, IBAN_REGISTRY_CHARSET);
        CSVRecord country = null;
        CSVRecord electronicExample = null;
        CSVRecord lastUpdateDate = null;
        try (CSVParser p = new CSVParser(rdr, format)) {
            for (final CSVRecord o : p) {
                final String item = o.get(0);
                switch(item) {
                    case "Name of country":
                        country = o;
                        break;
                    case "IBAN electronic format example":
                        electronicExample = o;
                        break;
                    case "Last update date":
                        lastUpdateDate = o;
                        break;
                    default:
                        break;
                }
            }
        }
        assertNotNull(country);
        final int arraySize = country.size();
        assertNotNull(electronicExample);
        assertEquals(arraySize, electronicExample.size());
        assertNotNull(lastUpdateDate);
        assertEquals(arraySize, lastUpdateDate.size());
        final Collection<Arguments> result = new ArrayList<>();
        Date lastDate = new Date(0);
        String lastUpdated = null;
        for (int i = 1; i < country.size(); i++) {
            result.add(Arguments.of(country.get(i), electronicExample.get(i)));
            final String mmyy = lastUpdateDate.get(i);
            final Date dt = DateValidator.getInstance().validate(mmyy, "MMM-yy", Locale.ROOT);
            if (dt.after(lastDate)) {
                lastDate = dt;
                lastUpdated = mmyy;
            }
        }
        final long age = (new Date().getTime() - lastDate.getTime()) / MS_PER_DAY;
        if (age > MAX_AGE_DAYS) {
            System.out.println("WARNING: expected recent last update date, but found: " + lastUpdated);
        }
        return result;
    }

    public static Stream<Arguments> validateIbanStatuses() {
        return Stream.of(Arguments.of("XX", IBANValidatorStatus.UNKNOWN_COUNTRY), Arguments.of("AD0101", IBANValidatorStatus.INVALID_LENGTH), Arguments.of("AD12XX012030200359100100", IBANValidatorStatus.INVALID_PATTERN), Arguments.of("AD9900012030200359100100", IBANValidatorStatus.INVALID_CHECKSUM), Arguments.of("AD1200012030200359100100", IBANValidatorStatus.VALID));
    }

    @Test
    public void testGetValidator_1() {
        assertNotNull(VALIDATOR.getValidator("GB"), "GB");
    }

    @Test
    public void testGetValidator_2() {
        assertNull(VALIDATOR.getValidator("gb"), "gb");
    }

    @Test
    public void testHasValidator_1() {
        assertTrue(VALIDATOR.hasValidator("GB"), "GB");
    }

    @Test
    public void testHasValidator_2() {
        assertFalse(VALIDATOR.hasValidator("gb"), "gb");
    }
}
