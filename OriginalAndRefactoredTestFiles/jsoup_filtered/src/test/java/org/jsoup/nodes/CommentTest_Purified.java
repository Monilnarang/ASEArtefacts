package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommentTest_Purified {

    private Comment comment = new Comment(" This is one heck of a comment! ");

    private Comment decl = new Comment("?xml encoding='ISO-8859-1'?");

    @Test
    public void testToString_1() {
        assertEquals("<!-- This is one heck of a comment! -->", comment.toString());
    }

    @Test
    public void testToString_2_testMerged_2() {
        Document doc = Jsoup.parse("<div><!-- comment--></div>");
        assertEquals("<div>\n <!-- comment-->\n</div>", doc.body().html());
        doc = Jsoup.parse("<p>One<!-- comment -->Two</p>");
        assertEquals("<p>One<!-- comment -->Two</p>", doc.body().html());
        assertEquals("OneTwo", doc.text());
    }

    @Test
    public void isXmlDeclaration_1() {
        assertFalse(comment.isXmlDeclaration());
    }

    @Test
    public void isXmlDeclaration_2() {
        assertTrue(decl.isXmlDeclaration());
    }
}
