package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.WikimediaLanguageCodes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WikimediaLanguageCodesTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_getSomeLanguageCodes_1to2")
    public void getSomeLanguageCodes_1to2(String param1, String param2) {
        assertEquals(param1, WikimediaLanguageCodes.getLanguageCode(param2));
    }

    static public Stream<Arguments> Provider_getSomeLanguageCodes_1to2() {
        return Stream.of(arguments("gsw", "als"), arguments("en", "en"));
    }

    @ParameterizedTest
    @MethodSource("Provider_fixDeprecatedLanguageCode_1to2")
    public void fixDeprecatedLanguageCode_1to2(String param1, String param2) {
        assertEquals(param1, WikimediaLanguageCodes.fixLanguageCodeIfDeprecated(param2));
    }

    static public Stream<Arguments> Provider_fixDeprecatedLanguageCode_1to2() {
        return Stream.of(arguments("nb", "no"), arguments("en", "en"));
    }
}
