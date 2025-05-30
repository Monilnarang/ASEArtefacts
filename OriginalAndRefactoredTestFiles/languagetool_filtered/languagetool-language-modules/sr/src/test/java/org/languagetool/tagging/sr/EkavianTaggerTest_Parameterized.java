package org.languagetool.tagging.sr;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.languagetool.TestTools;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EkavianTaggerTest_Parameterized extends AbstractSerbianTaggerTest {

    @NotNull
    protected EkavianTagger createTagger() {
        return new EkavianTagger();
    }

    @ParameterizedTest
    @MethodSource("Provider_testTaggerRaditi_1_1to2_2to3")
    public void testTaggerRaditi_1_1to2_2to3(String param1, String param2, String param3) throws Exception {
        assertHasLemmaAndPos(param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testTaggerRaditi_1_1to2_2to3() {
        return Stream.of(arguments("радим", "радити", "GL:GV:PZ:1L:0J"), arguments("радећи", "радити", "PL:PN"), arguments("је", "јесам", "GL:PM:PZ:3L:0J"), arguments("јеси", "јесам", "GL:PM:PZ:2L:0J"), arguments("смо", "јесам", "GL:PM:PZ:1L:0M"));
    }
}
