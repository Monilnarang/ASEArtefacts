package org.languagetool.tagging.sr;

import org.junit.Test;
import org.languagetool.TestTools;
import java.io.IOException;
import static org.junit.Assert.*;

public class JekavianTaggerTest_Purified extends AbstractSerbianTaggerTest {

    protected JekavianTagger createTagger() {
        return new JekavianTagger();
    }

    @Test
    public void testTaggerJesam_1() throws IOException {
        assertHasLemmaAndPos("је", "јесам", "GL:PM:PZ:3L:0J");
    }

    @Test
    public void testTaggerJesam_2() throws IOException {
        assertHasLemmaAndPos("јеси", "јесам", "GL:PM:PZ:2L:0J");
    }

    @Test
    public void testTaggerJesam_3() throws IOException {
        assertHasLemmaAndPos("смо", "јесам", "GL:PM:PZ:1L:0M");
    }

    @Test
    public void testTaggerSvijet_1() throws Exception {
        assertHasLemmaAndPos("цвијете", "цвијет", "IM:ZA:MU:0J:VO");
    }

    @Test
    public void testTaggerSvijet_2() throws Exception {
        assertHasLemmaAndPos("цвијетом", "цвијет", "IM:ZA:MU:0J:IN");
    }
}
