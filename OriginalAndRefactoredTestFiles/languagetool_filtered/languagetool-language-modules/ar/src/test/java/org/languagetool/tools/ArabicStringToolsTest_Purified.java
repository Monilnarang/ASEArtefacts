package org.languagetool.tools;

import org.junit.Test;
import org.languagetool.FakeLanguage;
import org.languagetool.Language;
import org.languagetool.TestTools;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ArabicStringToolsTest_Purified {

    @Test
    public void testRemoveTashkeel_1() {
        assertEquals("", ArabicStringTools.removeTashkeel(""));
    }

    @Test
    public void testRemoveTashkeel_2() {
        assertEquals("a", ArabicStringTools.removeTashkeel("a"));
    }

    @Test
    public void testRemoveTashkeel_3() {
        assertEquals("öäü", ArabicStringTools.removeTashkeel("öäü"));
    }

    @Test
    public void testRemoveTashkeel_4() {
        assertEquals("كتب", ArabicStringTools.removeTashkeel("كَتَب"));
    }
}
