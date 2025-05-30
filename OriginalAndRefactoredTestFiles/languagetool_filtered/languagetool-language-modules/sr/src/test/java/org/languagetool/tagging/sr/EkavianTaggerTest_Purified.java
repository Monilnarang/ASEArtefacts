package org.languagetool.tagging.sr;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.languagetool.TestTools;
import java.io.IOException;

public class EkavianTaggerTest_Purified extends AbstractSerbianTaggerTest {

    @NotNull
    protected EkavianTagger createTagger() {
        return new EkavianTagger();
    }

    @Test
    public void testTaggerRaditi_1() throws Exception {
        assertHasLemmaAndPos("радим", "радити", "GL:GV:PZ:1L:0J");
    }

    @Test
    public void testTaggerRaditi_2() throws Exception {
        assertHasLemmaAndPos("радећи", "радити", "PL:PN");
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
}
