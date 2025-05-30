package org.jsoup.select;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CssTest_Purified {

    private Document html = null;

    private static String htmlString;

    @BeforeAll
    public static void initClass() {
        StringBuilder sb = new StringBuilder("<html><head></head><body>");
        sb.append("<div id='pseudo'>");
        for (int i = 1; i <= 10; i++) {
            sb.append(String.format("<p>%d</p>", i));
        }
        sb.append("</div>");
        sb.append("<div id='type'>");
        for (int i = 1; i <= 10; i++) {
            sb.append(String.format("<p>%d</p>", i));
            sb.append(String.format("<span>%d</span>", i));
            sb.append(String.format("<em>%d</em>", i));
            sb.append(String.format("<svg>%d</svg>", i));
        }
        sb.append("</div>");
        sb.append("<span id='onlySpan'><br /></span>");
        sb.append("<p class='empty'><!-- Comment only is still empty! --></p>");
        sb.append("<div id='only'>");
        sb.append("Some text before the <em>only</em> child in this div");
        sb.append("</div>");
        sb.append("</body></html>");
        htmlString = sb.toString();
    }

    @BeforeEach
    public void init() {
        html = Jsoup.parse(htmlString);
    }

    protected void check(Elements result, String... expectedContent) {
        assertEquals(expectedContent.length, result.size(), "Number of elements");
        for (int i = 0; i < expectedContent.length; i++) {
            assertNotNull(result.get(i));
            assertEquals(expectedContent[i], result.get(i).ownText(), "Expected element");
        }
    }

    @Test
    public void root_1_testMerged_1() {
        Elements sel = html.select(":root");
        assertEquals(1, sel.size());
        assertNotNull(sel.get(0));
        assertEquals(Tag.valueOf("html"), sel.get(0).tag());
    }

    @Test
    public void root_4_testMerged_2() {
        Elements sel2 = html.select("body").select(":root");
        assertEquals(1, sel2.size());
        assertNotNull(sel2.get(0));
        assertEquals(Tag.valueOf("body"), sel2.get(0).tag());
    }
}
