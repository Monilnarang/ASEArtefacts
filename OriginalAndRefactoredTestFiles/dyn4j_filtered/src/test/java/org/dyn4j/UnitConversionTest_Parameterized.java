package org.dyn4j;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UnitConversionTest_Parameterized {

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
    public void poundNewton_1() {
        TestCase.assertEquals(1.000, UnitConversion.POUND_TO_NEWTON * UnitConversion.NEWTON_TO_POUND);
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

    @ParameterizedTest
    @MethodSource("Provider_poundKilogram_2_2")
    public void poundKilogram_2_2(double param1, double param2) {
        double p = param2;
        double k = UnitConversion.poundsToKilograms(p);
        double r = UnitConversion.kilogramsToPounds(k);
        TestCase.assertEquals(p, r, param1);
    }

    static public Stream<Arguments> Provider_poundKilogram_2_2() {
        return Stream.of(arguments(2.5, 1.0e-9), arguments(2.5, 1.0e-9));
    }
}
