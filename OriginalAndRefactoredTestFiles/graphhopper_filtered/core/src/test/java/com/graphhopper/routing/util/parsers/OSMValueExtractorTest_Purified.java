package com.graphhopper.routing.util.parsers;

import com.graphhopper.routing.util.parsers.helpers.OSMValueExtractor;
import org.junit.jupiter.api.Test;
import static com.graphhopper.routing.util.parsers.helpers.OSMValueExtractor.conditionalWeightToTons;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OSMValueExtractorTest_Purified {

    private final double DELTA = 0.001;

    @Test
    public void stringToTons_1() {
        assertEquals(1.5, OSMValueExtractor.stringToTons("1.5"), DELTA);
    }

    @Test
    public void stringToTons_2() {
        assertEquals(1.5, OSMValueExtractor.stringToTons("1.5 t"), DELTA);
    }

    @Test
    public void stringToTons_3() {
        assertEquals(1.5, OSMValueExtractor.stringToTons("1.5   t"), DELTA);
    }

    @Test
    public void stringToTons_4() {
        assertEquals(1.5, OSMValueExtractor.stringToTons("1.5 tons"), DELTA);
    }

    @Test
    public void stringToTons_5() {
        assertEquals(1.5, OSMValueExtractor.stringToTons("1.5 ton"), DELTA);
    }

    @Test
    public void stringToTons_6() {
        assertEquals(1.5, OSMValueExtractor.stringToTons("3306.9 lbs"), DELTA);
    }

    @Test
    public void stringToTons_7() {
        assertEquals(3, OSMValueExtractor.stringToTons("3 T"), DELTA);
    }

    @Test
    public void stringToTons_8() {
        assertEquals(3, OSMValueExtractor.stringToTons("3ton"), DELTA);
    }

    @Test
    public void stringToTons_9() {
        assertEquals(10, OSMValueExtractor.stringToTons("10000 kg"), DELTA);
    }

    @Test
    public void stringToTons_10() {
        assertEquals(25.401, OSMValueExtractor.stringToTons("28 st"), DELTA);
    }

    @Test
    public void stringToTons_11() {
        assertEquals(6, OSMValueExtractor.stringToTons("6t mgw"), DELTA);
    }

    @Test
    public void stringToMeter_1() {
        assertEquals(1.5, OSMValueExtractor.stringToMeter("1.5"), DELTA);
    }

    @Test
    public void stringToMeter_2() {
        assertEquals(1.5, OSMValueExtractor.stringToMeter("1.5m"), DELTA);
    }

    @Test
    public void stringToMeter_3() {
        assertEquals(1.5, OSMValueExtractor.stringToMeter("1.5 m"), DELTA);
    }

    @Test
    public void stringToMeter_4() {
        assertEquals(1.5, OSMValueExtractor.stringToMeter("1.5   m"), DELTA);
    }

    @Test
    public void stringToMeter_5() {
        assertEquals(1.5, OSMValueExtractor.stringToMeter("1.5 meter"), DELTA);
    }

    @Test
    public void stringToMeter_6() {
        assertEquals(1.499, OSMValueExtractor.stringToMeter("4 ft 11 in"), DELTA);
    }

    @Test
    public void stringToMeter_7() {
        assertEquals(1.499, OSMValueExtractor.stringToMeter("4'11''"), DELTA);
    }

    @Test
    public void stringToMeter_8() {
        assertEquals(3, OSMValueExtractor.stringToMeter("3 m."), DELTA);
    }

    @Test
    public void stringToMeter_9() {
        assertEquals(3, OSMValueExtractor.stringToMeter("3meters"), DELTA);
    }

    @Test
    public void stringToMeter_10() {
        assertEquals(0.8 * 3, OSMValueExtractor.stringToMeter("~3"), DELTA);
    }

    @Test
    public void stringToMeter_11() {
        assertEquals(3 * 0.8, OSMValueExtractor.stringToMeter("3 m approx"), DELTA);
    }

    @Test
    public void stringToMeter_12() {
        assertEquals(2.921, OSMValueExtractor.stringToMeter("9 ft 7in"), DELTA);
    }

    @Test
    public void stringToMeter_13() {
        assertEquals(2.921, OSMValueExtractor.stringToMeter("9'7\""), DELTA);
    }

    @Test
    public void stringToMeter_14() {
        assertEquals(2.921, OSMValueExtractor.stringToMeter("9'7''"), DELTA);
    }

    @Test
    public void stringToMeter_15() {
        assertEquals(2.921, OSMValueExtractor.stringToMeter("9' 7\""), DELTA);
    }

    @Test
    public void stringToMeter_16() {
        assertEquals(2.743, OSMValueExtractor.stringToMeter("9'"), DELTA);
    }

    @Test
    public void stringToMeter_17() {
        assertEquals(2.743, OSMValueExtractor.stringToMeter("9 feet"), DELTA);
    }

    @Test
    public void stringToMeter_18() {
        assertEquals(1.5, OSMValueExtractor.stringToMeter("150 cm"), DELTA);
    }

    @Test
    public void testConditionalWeightToTons_1() {
        assertEquals(7.5, conditionalWeightToTons("no @ (weight>7.5)"));
    }

    @Test
    public void testConditionalWeightToTons_2() {
        assertEquals(7.5, conditionalWeightToTons("delivery @ (Mo-Sa 06:00-12:00); no @ (weight>7.5); no @ (length>12)"));
    }
}
