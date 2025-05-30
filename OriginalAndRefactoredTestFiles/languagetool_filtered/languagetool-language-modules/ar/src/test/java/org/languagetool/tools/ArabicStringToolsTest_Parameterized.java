package org.languagetool.tools;

import org.junit.Test;
import org.languagetool.FakeLanguage;
import org.languagetool.Language;
import org.languagetool.TestTools;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArabicStringToolsTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testRemoveTashkeel_1to4")
    public void testRemoveTashkeel_1to4(String param1, String param2) {
        assertEquals(param1, ArabicStringTools.removeTashkeel(param2));
    }

    static public Stream<Arguments> Provider_testRemoveTashkeel_1to4() {
        return Stream.of(arguments("", ""), arguments("a", "a"), arguments("öäü", "öäü"), arguments("كتب", "كَتَب"));
    }
}
