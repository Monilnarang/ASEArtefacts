package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SinglePassTranslatorTest_Purified {

    private final SinglePassTranslator dummyTranslator = new SinglePassTranslator() {

        @Override
        void translateWhole(final CharSequence input, final Writer writer) throws IOException {
        }
    };

    private StringWriter out;

    @BeforeEach
    public void before() {
        out = new StringWriter();
    }

    @Test
    public void testCodePointsAreReturned_1() throws Exception {
        assertEquals(0, dummyTranslator.translate("", 0, out));
    }

    @Test
    public void testCodePointsAreReturned_2() throws Exception {
        assertEquals(3, dummyTranslator.translate("abc", 0, out));
    }

    @Test
    public void testCodePointsAreReturned_3() throws Exception {
        assertEquals(7, dummyTranslator.translate("abcdefg", 0, out));
    }
}
