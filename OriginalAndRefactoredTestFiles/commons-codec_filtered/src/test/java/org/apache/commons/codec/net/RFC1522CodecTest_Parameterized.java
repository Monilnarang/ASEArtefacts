package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class RFC1522CodecTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testDecodeInvalid_1to12")
    public void testDecodeInvalid_1to12(String param1) throws Exception {
        assertExpectedDecoderException(param1);
    }

    static public Stream<Arguments> Provider_testDecodeInvalid_1to12() {
        return Stream.of(arguments("whatever"), arguments("=?"), arguments("?="), arguments("=="), arguments("=??="), arguments("=?stuff?="), arguments("=?UTF-8??="), arguments("=?UTF-8?stuff?="), arguments("=?UTF-8?T?stuff"), arguments("=??T?stuff?="), arguments("=?UTF-8??stuff?="), arguments("=?UTF-8?W?stuff?="));
    }
}
