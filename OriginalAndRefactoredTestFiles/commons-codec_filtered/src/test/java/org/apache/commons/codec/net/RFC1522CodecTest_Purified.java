package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class RFC1522CodecTest_Purified {

    static class RFC1522TestCodec extends RFC1522Codec {

        RFC1522TestCodec() {
            super(StandardCharsets.UTF_8);
        }

        @Override
        protected byte[] doDecoding(final byte[] bytes) {
            return bytes;
        }

        @Override
        protected byte[] doEncoding(final byte[] bytes) {
            return bytes;
        }

        @Override
        protected String getEncoding() {
            return "T";
        }
    }

    private void assertExpectedDecoderException(final String s) {
        assertThrows(DecoderException.class, () -> new RFC1522TestCodec().decodeText(s));
    }

    @Test
    public void testDecodeInvalid_1() throws Exception {
        assertExpectedDecoderException("whatever");
    }

    @Test
    public void testDecodeInvalid_2() throws Exception {
        assertExpectedDecoderException("=?");
    }

    @Test
    public void testDecodeInvalid_3() throws Exception {
        assertExpectedDecoderException("?=");
    }

    @Test
    public void testDecodeInvalid_4() throws Exception {
        assertExpectedDecoderException("==");
    }

    @Test
    public void testDecodeInvalid_5() throws Exception {
        assertExpectedDecoderException("=??=");
    }

    @Test
    public void testDecodeInvalid_6() throws Exception {
        assertExpectedDecoderException("=?stuff?=");
    }

    @Test
    public void testDecodeInvalid_7() throws Exception {
        assertExpectedDecoderException("=?UTF-8??=");
    }

    @Test
    public void testDecodeInvalid_8() throws Exception {
        assertExpectedDecoderException("=?UTF-8?stuff?=");
    }

    @Test
    public void testDecodeInvalid_9() throws Exception {
        assertExpectedDecoderException("=?UTF-8?T?stuff");
    }

    @Test
    public void testDecodeInvalid_10() throws Exception {
        assertExpectedDecoderException("=??T?stuff?=");
    }

    @Test
    public void testDecodeInvalid_11() throws Exception {
        assertExpectedDecoderException("=?UTF-8??stuff?=");
    }

    @Test
    public void testDecodeInvalid_12() throws Exception {
        assertExpectedDecoderException("=?UTF-8?W?stuff?=");
    }
}
