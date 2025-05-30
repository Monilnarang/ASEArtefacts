package org.languagetool.tagging.sr;

import org.junit.Test;
import org.languagetool.TestTools;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JekavianTaggerTest_Parameterized extends AbstractSerbianTaggerTest {

    protected JekavianTagger createTagger() {
        return new JekavianTagger();
    }

    @ParameterizedTest
    @MethodSource("Provider_testTaggerJesam_1_1to2_2to3")
    public void testTaggerJesam_1_1to2_2to3(String param1, String param2, String param3) throws IOException {
        assertHasLemmaAndPos(param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testTaggerJesam_1_1to2_2to3() {
        return Stream.of(arguments("је", "јесам", "GL:PM:PZ:3L:0J"), arguments("јеси", "јесам", "GL:PM:PZ:2L:0J"), arguments("смо", "јесам", "GL:PM:PZ:1L:0M"), arguments("цвијете", "цвијет", "IM:ZA:MU:0J:VO"), arguments("цвијетом", "цвијет", "IM:ZA:MU:0J:IN"));
    }
}
