package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import java.nio.charset.Charset;
import java.util.Arrays;
import static org.jsoup.parser.CharacterReader.BufferSize;
import static org.junit.jupiter.api.Assertions.*;

public class TokeniserTest_Purified {

    @Test
    public void cp1252Entities_1() {
        assertEquals("\u20ac", Jsoup.parse("&#0128;").text());
    }

    @Test
    public void cp1252Entities_2() {
        assertEquals("\u201a", Jsoup.parse("&#0130;").text());
    }

    @Test
    public void cp1252Entities_3() {
        assertEquals("\u20ac", Jsoup.parse("&#x80;").text());
    }
}
