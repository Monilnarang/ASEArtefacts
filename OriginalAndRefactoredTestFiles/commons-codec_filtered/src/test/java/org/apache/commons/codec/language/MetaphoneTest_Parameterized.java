package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MetaphoneTest_Parameterized extends AbstractStringEncoderTest<Metaphone> {

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

    @ParameterizedTest
    @MethodSource("Provider_testDiscardOfSCEOrSCIOrSCY_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3to4_4to11")
    public void testDiscardOfSCEOrSCIOrSCY_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3to4_4to11(String param1, String param2) {
        assertEquals(param1, getStringEncoder().metaphone(param2));
    }

    static public Stream<Arguments> Provider_testDiscardOfSCEOrSCIOrSCY_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3to4_4to11() {
        return Stream.of(arguments("SNS", "SCIENCE"), arguments("SN", "SCENE"), arguments("S", "SCY"), arguments("N", "GNU"), arguments("SNT", "SIGNED"), arguments("KNT", "GHENT"), arguments("B", "BAUGH"), arguments("HL", "howl"), arguments("TSTN", "testing"), arguments(0, "The"), arguments("KK", "quick"), arguments("BRN", "brown"), arguments("FKS", "fox"), arguments("JMPT", "jumped"), arguments("OFR", "over"), arguments(0, "the"), arguments("LS", "lazy"), arguments("TKS", "dogs"), arguments("XT", "SHOT"), arguments("OTXN", "ODSIAN"), arguments("PLXN", "PULSION"), arguments("RX", "RETCH"), arguments("WX", "WATCH"), arguments("OX", "OTIA"), arguments("PRXN", "PORTION"), arguments("SKTL", "SCHEDULE"), arguments("SKMT", "SCHEMATIC"), arguments("KRKT", "CHARACTER"), arguments("TX", "TEACH"), arguments("TJ", "DODGY"), arguments("TJ", "DODGE"), arguments("AJMT", "ADGIEMTI"), arguments("KM", "COMB"), arguments("TM", "TOMB"), arguments("WM", "WOMB"));
    }
}
