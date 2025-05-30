package org.apache.commons.codec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.io.Charsets;
import org.junit.jupiter.api.Test;

public class CharsetsTest_Purified {

    private static final TreeSet<String> AVAILABLE_CHARSET_NAMES = new TreeSet<>(Charset.availableCharsets().keySet());

    public static SortedSet<String> getAvailableCharsetNames() {
        return AVAILABLE_CHARSET_NAMES;
    }

    public static Collection<Charset> getRequiredCharsets() {
        return Charsets.requiredCharsets().values();
    }

    @Test
    public void testToCharset_1() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharset((String) null));
    }

    @Test
    public void testToCharset_2() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharset((Charset) null));
    }

    @Test
    public void testToCharset_3() {
        assertEquals(Charset.defaultCharset(), Charsets.toCharset(Charset.defaultCharset()));
    }

    @Test
    public void testToCharset_4() {
        assertEquals(StandardCharsets.UTF_8, Charsets.toCharset(StandardCharsets.UTF_8));
    }
}
