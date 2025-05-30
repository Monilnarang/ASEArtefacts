package com.graphhopper.reader.dem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SRTMGL1ProviderTest_Parameterized {

    private double precision = .1;

    SRTMGL1Provider instance;

    @BeforeEach
    public void setUp() {
        instance = new SRTMGL1Provider();
    }

    @AfterEach
    public void tearDown() {
        instance.release();
    }

    @Test
    public void testMinLat_3() {
        assertEquals(-60, instance.getMinLatForTile(-59.9));
    }

    @Test
    public void testMinLon_1() {
        assertEquals(-60, instance.getMinLonForTile(-59.9));
    }

    @Test
    public void testMinLon_2() {
        assertEquals(0, instance.getMinLonForTile(0.9));
    }

    @Disabled
    @Test
    public void testGetEle_1() {
        assertEquals(338, instance.getEle(49.949784, 11.57517), precision);
    }

    @Disabled
    @Test
    public void testGetEle_2() {
        assertEquals(468, instance.getEle(49.968668, 11.575127), precision);
    }

    @Disabled
    @Test
    public void testGetEle_3() {
        assertEquals(467, instance.getEle(49.968682, 11.574842), precision);
    }

    @Disabled
    @Test
    public void testGetEle_4() {
        assertEquals(3110, instance.getEle(-22.532854, -65.110474), precision);
    }

    @Disabled
    @Test
    public void testGetEle_5() {
        assertEquals(120, instance.getEle(38.065392, -87.099609), precision);
    }

    @Disabled
    @Test
    public void testGetEle_6() {
        assertEquals(1617, instance.getEle(40, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_7() {
        assertEquals(1617, instance.getEle(39.99999999, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_8() {
        assertEquals(1617, instance.getEle(39.9999999, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_9() {
        assertEquals(1617, instance.getEle(39.999999, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_10() {
        assertEquals(1015, instance.getEle(47.468668, 14.575127), precision);
    }

    @Disabled
    @Test
    public void testGetEle_11() {
        assertEquals(1107, instance.getEle(47.467753, 14.573911), precision);
    }

    @Disabled
    @Test
    public void testGetEle_12() {
        assertEquals(1930, instance.getEle(46.468835, 12.578777), precision);
    }

    @Disabled
    @Test
    public void testGetEle_13() {
        assertEquals(844, instance.getEle(48.469123, 9.576393), precision);
    }

    @Disabled
    @Test
    public void testGetEle_14() {
        assertEquals(0, instance.getEle(56.4787319, 17.6118363), precision);
    }

    @Disabled
    @Test
    public void testGetEle_15() {
        assertEquals(0, instance.getEle(56.4787319, 17.6118363), precision);
    }

    @Disabled
    @Test
    public void testGetEle_16() {
        assertEquals(0, instance.getEle(60.0000001, 16), precision);
    }

    @Disabled
    @Test
    public void testGetEle_17() {
        assertEquals(0, instance.getEle(60.0000001, 16), precision);
    }

    @Disabled
    @Test
    public void testGetEle_18() {
        assertEquals(0, instance.getEle(60.0000001, 19), precision);
    }

    @Disabled
    @Test
    public void testGetEle_19() {
        assertEquals(0, instance.getEle(60.251, 18.805), precision);
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_1() {
        assertEquals("n42e011", instance.getFileName(42.999999, 11.48));
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_2() {
        assertEquals(420, instance.getEle(42.999999, 11.48), precision);
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_3() {
        assertEquals("n43e011", instance.getFileName(43.000001, 11.48));
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_4() {
        assertEquals(420, instance.getEle(43.000001, 11.48), precision);
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_1() {
        assertEquals("n42e011", instance.getFileName(42.1, 11.999999));
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_2() {
        assertEquals(324, instance.getEle(42.1, 11.999999), precision);
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_3() {
        assertEquals("n42e012", instance.getFileName(42.1, 12.000001));
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_4() {
        assertEquals(324, instance.getEle(42.1, 12.000001), precision);
    }

    @ParameterizedTest
    @MethodSource("Provider_testMinLat_1to2")
    public void testMinLat_1to2(int param1, double param2) {
        assertEquals(param1, instance.getMinLatForTile(param2));
    }

    static public Stream<Arguments> Provider_testMinLat_1to2() {
        return Stream.of(arguments(52, 52.5), arguments(29, 29.9));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDownloadUrl_1_5")
    public void testGetDownloadUrl_1_5(String param1, double param2, double param3) {
        assertEquals(param1, instance.getDownloadURL(param2, param3));
    }

    static public Stream<Arguments> Provider_testGetDownloadUrl_1_5() {
        return Stream.of(arguments("North/North_30_60/N42E011.hgt", 42.940339, 11.953125), arguments("North/North_0_29/N24E120.hgt", 24.590108, 120.640625));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDownloadUrl_2to3")
    public void testGetDownloadUrl_2to3(String param1, double param2, double param3) {
        assertEquals(param1, instance.getDownloadURL(param2, -param3));
    }

    static public Stream<Arguments> Provider_testGetDownloadUrl_2to3() {
        return Stream.of(arguments("North/North_30_60/N38W078.hgt", 38.548165, 77.167969), arguments("North/North_0_29/N14W005.hgt", 14.116047, 4.277344));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDownloadUrl_4_6")
    public void testGetDownloadUrl_4_6(String param1, double param2, double param3) {
        assertEquals(param1, instance.getDownloadURL(-param2, -param3));
    }

    static public Stream<Arguments> Provider_testGetDownloadUrl_4_6() {
        return Stream.of(arguments("South/S52W058.hgt", 51.015725, 57.621094), arguments("South/S42W063.hgt", 41.015725, 62.949219));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFileName_1_5")
    public void testGetFileName_1_5(String param1, double param2, double param3) {
        assertEquals(param1, instance.getFileName(param2, param3));
    }

    static public Stream<Arguments> Provider_testGetFileName_1_5() {
        return Stream.of(arguments("n42e011", 42.940339, 11.953125), arguments("n24e120", 24.590108, 120.640625));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFileName_2to3")
    public void testGetFileName_2to3(String param1, double param2, double param3) {
        assertEquals(param1, instance.getFileName(param2, -param3));
    }

    static public Stream<Arguments> Provider_testGetFileName_2to3() {
        return Stream.of(arguments("n38w078", 38.548165, 77.167969), arguments("n14w005", 14.116047, 4.277344));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFileName_4_6")
    public void testGetFileName_4_6(String param1, double param2, double param3) {
        assertEquals(param1, instance.getFileName(-param2, -param3));
    }

    static public Stream<Arguments> Provider_testGetFileName_4_6() {
        return Stream.of(arguments("s52w058", 51.015725, 57.621094), arguments("s42w063", 41.015725, 62.949219));
    }
}
