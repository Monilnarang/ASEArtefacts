package org.languagetool.gui;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ToolsTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testGetLabel_1to2")
    public void testGetLabel_1to2(String param1, String param2) {
        assertEquals(param1, Tools.getLabel(param2));
    }

    static public Stream<Arguments> Provider_testGetLabel_1to2() {
        return Stream.of(arguments("This is a Label", "This is a &Label"), arguments("Bits & Pieces", "Bits && Pieces"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetMnemonic_1to4")
    public void testGetMnemonic_1to4(String param1, String param2) {
        assertEquals(param1, Tools.getMnemonic(param2));
    }

    static public Stream<Arguments> Provider_testGetMnemonic_1to4() {
        return Stream.of(arguments("F", "&File"), arguments("O", "&OK"), arguments("\u0000", "File && String operations"), arguments("O", "File && String &Operations"));
    }
}
