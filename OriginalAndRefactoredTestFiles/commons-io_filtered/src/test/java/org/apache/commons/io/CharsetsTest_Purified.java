package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("deprecation")
public class CharsetsTest_Purified {

    public static final String AVAIL_CHARSETS = "org.apache.commons.io.CharsetsTest#availableCharsetsKeySet";

    public static final String REQUIRED_CHARSETS = "org.apache.commons.io.CharsetsTest#getRequiredCharsetNames";

    public static Set<String> availableCharsetsKeySet() {
        return Charset.availableCharsets().keySet();
    }

    public static Collection<Charset> availableCharsetsValues() {
        return Charset.availableCharsets().values();
    }

    static Stream<Arguments> charsetAliasProvider() {
        return Charset.availableCharsets().entrySet().stream().flatMap(entry -> entry.getValue().aliases().stream().map(a -> Arguments.of(entry.getValue(), a)));
    }

    public static Set<String> getRequiredCharsetNames() {
        return Charsets.requiredCharsets().keySet();
    }

    @Test
    public void testToCharset_String_1() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharset((String) null));
    }

    @Test
    public void testToCharset_String_2() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharset((Charset) null));
    }

    @Test
    public void testToCharset_String_3() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharset(Charset.defaultCharset()));
    }

    @Test
    public void testToCharset_String_4() {
        assertEquals(StandardCharsets.UTF_8, Charsets.toCharset(StandardCharsets.UTF_8));
    }

    @Test
    public void testToCharsetDefault_1() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharsetDefault((String) null, null));
    }

    @Test
    public void testToCharsetDefault_2() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharsetDefault(StringUtils.EMPTY, null));
    }

    @Test
    public void testToCharsetDefault_3() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharsetDefault(".", null));
    }

    @Test
    public void testToCharsetDefault_4() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharsetDefault(null, Charset.defaultCharset()));
    }

    @Test
    public void testToCharsetDefault_5() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharsetDefault(Charset.defaultCharset().name(), Charset.defaultCharset()));
    }

    @Test
    public void testToCharsetDefault_6() {
        assertEquals(StandardCharsets.UTF_8, Charsets.toCharsetDefault(StandardCharsets.UTF_8.name(), Charset.defaultCharset()));
    }

    @Test
    public void testToCharsetDefault_7() {
        assertEquals(StandardCharsets.UTF_8, Charsets.toCharsetDefault(StandardCharsets.UTF_8.name(), null));
    }

    @Test
    public void testToCharsetWithStringCharset_1() {
        assertNull(Charsets.toCharset((String) null, null));
    }

    @Test
    public void testToCharsetWithStringCharset_2() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharset((String) null, Charset.defaultCharset()));
    }

    @Test
    public void testToCharsetWithStringCharset_3() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharset((Charset) null, Charset.defaultCharset()));
    }

    @Test
    public void testToCharsetWithStringCharset_4() {
        assertNull(Charsets.toCharset((Charset) null, null));
    }

    @Test
    public void testToCharsetWithStringCharset_5() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharset(Charset.defaultCharset(), Charset.defaultCharset()));
    }

    @Test
    public void testToCharsetWithStringCharset_6() {
        assertEquals(StandardCharsets.UTF_8, Charsets.toCharset(StandardCharsets.UTF_8, Charset.defaultCharset()));
    }

    @Test
    public void testToCharsetWithStringCharset_7() {
        assertEquals(StandardCharsets.UTF_8, Charsets.toCharset(StandardCharsets.UTF_8, null));
    }
}
