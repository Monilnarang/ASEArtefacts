package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MetaphoneTest_Purified extends AbstractStringEncoderTest<Metaphone> {

    public void assertIsMetaphoneEqual(final String source, final String[] matches) {
        for (final String matche : matches) {
            assertTrue(getStringEncoder().isMetaphoneEqual(source, matche), "Source: " + source + ", should have same Metaphone as: " + matche);
        }
        for (final String matche : matches) {
            for (final String matche2 : matches) {
                assertTrue(getStringEncoder().isMetaphoneEqual(matche, matche2));
            }
        }
    }

    public void assertMetaphoneEqual(final String[][] pairs) {
        validateFixture(pairs);
        for (final String[] pair : pairs) {
            final String name0 = pair[0];
            final String name1 = pair[1];
            final String failMsg = "Expected match between " + name0 + " and " + name1;
            assertTrue(getStringEncoder().isMetaphoneEqual(name0, name1), failMsg);
            assertTrue(getStringEncoder().isMetaphoneEqual(name1, name0), failMsg);
        }
    }

    @Override
    protected Metaphone createStringEncoder() {
        return new Metaphone();
    }

    public void validateFixture(final String[][] pairs) {
        if (pairs.length == 0) {
            fail("Test fixture is empty");
        }
        for (int i = 0; i < pairs.length; i++) {
            if (pairs[i].length != 2) {
                fail("Error in test fixture in the data array at index " + i);
            }
        }
    }

    @Test
    public void testDiscardOfSCEOrSCIOrSCY_1() {
        assertEquals("SNS", getStringEncoder().metaphone("SCIENCE"));
    }

    @Test
    public void testDiscardOfSCEOrSCIOrSCY_2() {
        assertEquals("SN", getStringEncoder().metaphone("SCENE"));
    }

    @Test
    public void testDiscardOfSCEOrSCIOrSCY_3() {
        assertEquals("S", getStringEncoder().metaphone("SCY"));
    }

    @Test
    public void testDiscardOfSilentGN_1() {
        assertEquals("N", getStringEncoder().metaphone("GNU"));
    }

    @Test
    public void testDiscardOfSilentGN_2() {
        assertEquals("SNT", getStringEncoder().metaphone("SIGNED"));
    }

    @Test
    public void testDiscardOfSilentHAfterG_1() {
        assertEquals("KNT", getStringEncoder().metaphone("GHENT"));
    }

    @Test
    public void testDiscardOfSilentHAfterG_2() {
        assertEquals("B", getStringEncoder().metaphone("BAUGH"));
    }

    @Test
    public void testMetaphone_1() {
        assertEquals("HL", getStringEncoder().metaphone("howl"));
    }

    @Test
    public void testMetaphone_2() {
        assertEquals("TSTN", getStringEncoder().metaphone("testing"));
    }

    @Test
    public void testMetaphone_3() {
        assertEquals("0", getStringEncoder().metaphone("The"));
    }

    @Test
    public void testMetaphone_4() {
        assertEquals("KK", getStringEncoder().metaphone("quick"));
    }

    @Test
    public void testMetaphone_5() {
        assertEquals("BRN", getStringEncoder().metaphone("brown"));
    }

    @Test
    public void testMetaphone_6() {
        assertEquals("FKS", getStringEncoder().metaphone("fox"));
    }

    @Test
    public void testMetaphone_7() {
        assertEquals("JMPT", getStringEncoder().metaphone("jumped"));
    }

    @Test
    public void testMetaphone_8() {
        assertEquals("OFR", getStringEncoder().metaphone("over"));
    }

    @Test
    public void testMetaphone_9() {
        assertEquals("0", getStringEncoder().metaphone("the"));
    }

    @Test
    public void testMetaphone_10() {
        assertEquals("LS", getStringEncoder().metaphone("lazy"));
    }

    @Test
    public void testMetaphone_11() {
        assertEquals("TKS", getStringEncoder().metaphone("dogs"));
    }

    @Test
    public void testSHAndSIOAndSIAToX_1() {
        assertEquals("XT", getStringEncoder().metaphone("SHOT"));
    }

    @Test
    public void testSHAndSIOAndSIAToX_2() {
        assertEquals("OTXN", getStringEncoder().metaphone("ODSIAN"));
    }

    @Test
    public void testSHAndSIOAndSIAToX_3() {
        assertEquals("PLXN", getStringEncoder().metaphone("PULSION"));
    }

    @Test
    public void testTCH_1() {
        assertEquals("RX", getStringEncoder().metaphone("RETCH"));
    }

    @Test
    public void testTCH_2() {
        assertEquals("WX", getStringEncoder().metaphone("WATCH"));
    }

    @Test
    public void testTIOAndTIAToX_1() {
        assertEquals("OX", getStringEncoder().metaphone("OTIA"));
    }

    @Test
    public void testTIOAndTIAToX_2() {
        assertEquals("PRXN", getStringEncoder().metaphone("PORTION"));
    }

    @Test
    public void testTranslateOfSCHAndCH_1() {
        assertEquals("SKTL", getStringEncoder().metaphone("SCHEDULE"));
    }

    @Test
    public void testTranslateOfSCHAndCH_2() {
        assertEquals("SKMT", getStringEncoder().metaphone("SCHEMATIC"));
    }

    @Test
    public void testTranslateOfSCHAndCH_3() {
        assertEquals("KRKT", getStringEncoder().metaphone("CHARACTER"));
    }

    @Test
    public void testTranslateOfSCHAndCH_4() {
        assertEquals("TX", getStringEncoder().metaphone("TEACH"));
    }

    @Test
    public void testTranslateToJOfDGEOrDGIOrDGY_1() {
        assertEquals("TJ", getStringEncoder().metaphone("DODGY"));
    }

    @Test
    public void testTranslateToJOfDGEOrDGIOrDGY_2() {
        assertEquals("TJ", getStringEncoder().metaphone("DODGE"));
    }

    @Test
    public void testTranslateToJOfDGEOrDGIOrDGY_3() {
        assertEquals("AJMT", getStringEncoder().metaphone("ADGIEMTI"));
    }

    @Test
    public void testWordEndingInMB_1() {
        assertEquals("KM", getStringEncoder().metaphone("COMB"));
    }

    @Test
    public void testWordEndingInMB_2() {
        assertEquals("TM", getStringEncoder().metaphone("TOMB"));
    }

    @Test
    public void testWordEndingInMB_3() {
        assertEquals("WM", getStringEncoder().metaphone("WOMB"));
    }
}
