package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.helper.ValidationException;
import org.jsoup.internal.StringUtil;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TextNodeTest_Purified {

    @Test
    public void testBlank_1() {
        TextNode one = new TextNode("");
        assertTrue(one.isBlank());
    }

    @Test
    public void testBlank_2() {
        TextNode two = new TextNode("     ");
        assertTrue(two.isBlank());
    }

    @Test
    public void testBlank_3() {
        TextNode three = new TextNode("  \n\n   ");
        assertTrue(three.isBlank());
    }

    @Test
    public void testBlank_4() {
        TextNode four = new TextNode("Hello");
        assertFalse(four.isBlank());
    }

    @Test
    public void testBlank_5() {
        TextNode five = new TextNode("  \nHello ");
        assertFalse(five.isBlank());
    }
}
