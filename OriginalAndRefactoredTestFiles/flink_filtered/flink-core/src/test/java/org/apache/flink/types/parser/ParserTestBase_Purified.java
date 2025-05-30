package org.apache.flink.types.parser;

import org.apache.flink.configuration.ConfigConstants;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class ParserTestBase_Purified<T> {

    public abstract String[] getValidTestValues();

    public abstract T[] getValidTestResults();

    public abstract String[] getInvalidTestValues();

    public abstract boolean allowsEmptyField();

    public abstract FieldParser<T> getParser();

    public abstract Class<T> getTypeClass();

    private static byte[] concatenate(String[] values, char[] delimiter, boolean delimiterAtEnd) {
        int len = 0;
        for (String s : values) {
            len += s.length() + delimiter.length;
        }
        if (!delimiterAtEnd) {
            len -= delimiter.length;
        }
        int currPos = 0;
        byte[] result = new byte[len];
        for (int i = 0; i < values.length; i++) {
            String s = values[i];
            byte[] bytes = s.getBytes(ConfigConstants.DEFAULT_CHARSET);
            int numBytes = bytes.length;
            System.arraycopy(bytes, 0, result, currPos, numBytes);
            currPos += numBytes;
            if (delimiterAtEnd || i < values.length - 1) {
                for (char c : delimiter) result[currPos++] = (byte) c;
            }
        }
        return result;
    }

    @Test
    void testTest_1() {
        assertThat(getParser()).isNotNull();
    }

    @Test
    void testTest_2() {
        assertThat(getTypeClass()).isNotNull();
    }

    @Test
    void testTest_3() {
        assertThat(getValidTestValues()).isNotNull();
    }

    @Test
    void testTest_4() {
        assertThat(getValidTestResults()).isNotNull();
    }

    @Test
    void testTest_5() {
        assertThat(getInvalidTestValues()).isNotNull();
    }

    @Test
    void testTest_6() {
        assertThat(getValidTestValues()).hasSameSizeAs(getValidTestResults());
    }
}
