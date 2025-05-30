package org.dyn4j;

import junit.framework.TestCase;
import org.junit.Test;

public class UnitConversionTest_Purified {

    @Test
    public void footMeter_1() {
        TestCase.assertEquals(1.000, UnitConversion.FOOT_TO_METER * UnitConversion.METER_TO_FOOT);
    }

    @Test
    public void footMeter_2() {
        double m = 2.5;
        double f = UnitConversion.metersToFeet(m);
        double r = UnitConversion.feetToMeters(f);
        TestCase.assertEquals(m, r, 1.0e-9);
    }

    @Test
    public void slugKilogram_1() {
        TestCase.assertEquals(1.000, UnitConversion.SLUG_TO_KILOGRAM * UnitConversion.KILOGRAM_TO_SLUG);
    }

    @Test
    public void slugKilogram_2() {
        double s = 2.5;
        double k = UnitConversion.slugsToKilograms(s);
        double r = UnitConversion.kilogramsToSlugs(k);
        TestCase.assertEquals(s, r, 1.0e-9);
    }

    @Test
    public void poundKilogram_1() {
        TestCase.assertEquals(1.000, UnitConversion.POUND_TO_KILOGRAM * UnitConversion.KILOGRAM_TO_POUND);
    }

    @Test
    public void poundKilogram_2() {
        double p = 2.5;
        double k = UnitConversion.poundsToKilograms(p);
        double r = UnitConversion.kilogramsToPounds(k);
        TestCase.assertEquals(p, r, 1.0e-9);
    }

    @Test
    public void poundNewton_1() {
        TestCase.assertEquals(1.000, UnitConversion.POUND_TO_NEWTON * UnitConversion.NEWTON_TO_POUND);
    }

    @Test
    public void poundNewton_2() {
        double p = 2.5;
        double n = UnitConversion.poundsToNewtons(p);
        double r = UnitConversion.newtonsToPounds(n);
        TestCase.assertEquals(p, r, 1.0e-9);
    }

    @Test
    public void footPoundNewtonMeter_1() {
        TestCase.assertEquals(1.000, UnitConversion.FOOT_POUND_TO_NEWTON_METER * UnitConversion.NEWTON_METER_TO_FOOT_POUND);
    }

    @Test
    public void footPoundNewtonMeter_2() {
        double fp = 2.5;
        double nm = UnitConversion.footPoundsToNewtonMeters(fp);
        double r = UnitConversion.newtonMetersToFootPounds(nm);
        TestCase.assertEquals(fp, r, 1.0e-9);
    }
}
