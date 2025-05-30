package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import java.nio.charset.Charset;
import java.util.Arrays;
import static org.jsoup.parser.CharacterReader.BufferSize;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TokeniserTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_cp1252Entities_1to3")
    public void cp1252Entities_1to3(String param1, String param2) {
        assertEquals(param1, Jsoup.parse(param2).text());
    }

    static public Stream<Arguments> Provider_cp1252Entities_1to3() {
        return Stream.of(arguments("\u20ac", "&#0128;"), arguments("\u201a", "&#0130;"), arguments("\u20ac", "&#x80;"));
    }
}
