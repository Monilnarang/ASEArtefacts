package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class UtilTest_Purified {

    public static Stream<Arguments> charArgs() {
        final List<Arguments> lst = new ArrayList<>();
        final char[] whitespace = { ' ', '\t', '\n', '\f', '\r', Character.SPACE_SEPARATOR, Character.LINE_SEPARATOR, Character.PARAGRAPH_SEPARATOR, '\u000B', '\u001C', '\u001D', '\u001E', '\u001F' };
        final char[] nonBreakingSpace = { '\u00A0', '\u2007', '\u202F' };
        for (final char c : whitespace) {
            lst.add(Arguments.of(Character.valueOf(c), Boolean.TRUE));
        }
        for (final char c : nonBreakingSpace) {
            lst.add(Arguments.of(Character.valueOf(c), Boolean.FALSE));
        }
        return lst.stream();
    }

    @Test
    public void testFindNonWhitespacePos_1() {
        assertEquals(-1, Util.indexOfNonWhitespace(null, 0));
    }

    @Test
    public void testFindNonWhitespacePos_2() {
        assertEquals(-1, Util.indexOfNonWhitespace("", 0));
    }
}
