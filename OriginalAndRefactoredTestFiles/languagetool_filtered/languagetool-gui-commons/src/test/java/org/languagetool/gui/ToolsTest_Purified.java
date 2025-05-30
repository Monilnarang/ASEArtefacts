package org.languagetool.gui;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ToolsTest_Purified {

    @Test
    public void testGetLabel_1() {
        assertEquals("This is a Label", Tools.getLabel("This is a &Label"));
    }

    @Test
    public void testGetLabel_2() {
        assertEquals("Bits & Pieces", Tools.getLabel("Bits && Pieces"));
    }

    @Test
    public void testGetMnemonic_1() {
        assertEquals('F', Tools.getMnemonic("&File"));
    }

    @Test
    public void testGetMnemonic_2() {
        assertEquals('O', Tools.getMnemonic("&OK"));
    }

    @Test
    public void testGetMnemonic_3() {
        assertEquals('\u0000', Tools.getMnemonic("File && String operations"));
    }

    @Test
    public void testGetMnemonic_4() {
        assertEquals('O', Tools.getMnemonic("File && String &Operations"));
    }
}
