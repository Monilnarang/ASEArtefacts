package org.apache.commons.lang3;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CharsetsTest_Purified extends AbstractLangTest {

    @Test
    public void testToCharset_Charset_1() {
        Assertions.assertEquals(Charset.defaultCharset(), Charsets.toCharset((Charset) null));
    }

    @Test
    public void testToCharset_Charset_2() {
        Assertions.assertEquals(Charset.defaultCharset(), Charsets.toCharset(Charset.defaultCharset()));
    }

    @Test
    public void testToCharset_Charset_3() {
        Assertions.assertEquals(StandardCharsets.UTF_8, Charsets.toCharset(StandardCharsets.UTF_8));
    }

    @Test
    public void testToCharset_String_1() {
        Assertions.assertEquals(Charset.defaultCharset(), Charsets.toCharset((String) null));
    }

    @Test
    public void testToCharset_String_2() {
        Assertions.assertEquals(Charset.defaultCharset(), Charsets.toCharset(Charset.defaultCharset().name()));
    }

    @Test
    public void testToCharset_String_3() {
        Assertions.assertEquals(StandardCharsets.UTF_8, Charsets.toCharset(StandardCharsets.UTF_8.name()));
    }

    @Test
    public void testToCharsetName_1() {
        Assertions.assertEquals(Charset.defaultCharset().name(), Charsets.toCharsetName((String) null));
    }

    @Test
    public void testToCharsetName_2() {
        Assertions.assertEquals("UTF-8", Charsets.toCharsetName(StandardCharsets.UTF_8.name()));
    }
}
