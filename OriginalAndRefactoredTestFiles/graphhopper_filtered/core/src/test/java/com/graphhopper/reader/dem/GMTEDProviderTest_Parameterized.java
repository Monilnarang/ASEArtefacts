package com.graphhopper.reader.dem;

import com.graphhopper.util.Downloader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GMTEDProviderTest_Parameterized {

    private double precision = .1;

    GMTEDProvider instance;

    @BeforeEach
    public void setUp() {
        instance = new GMTEDProvider();
    }

    @AfterEach
    public void tearDown() {
        instance.release();
    }

    @Test
    public void testMinLat_3() {
        assertEquals(-70, instance.getMinLatForTile(-59.9));
    }

    @Test
    public void testMinLon_1() {
        assertEquals(-60, instance.getMinLonForTile(-59.9));
    }

    @Test
    public void testMinLon_2() {
        assertEquals(0, instance.getMinLonForTile(0.9));
    }

    @Test
    public void testGetDownloadUrl_4() {
        assertTrue(instance.getDownloadURL(-61.015725, -156.621094).contains("W180/70S180W_20101117_gmted_mea075.tif"));
    }

    @Test
    public void testGetDownloadUrl_6() {
        assertTrue(instance.getDownloadURL(-61.015725, 162.949219).contains("E150/70S150E_20101117_gmted_mea075.tif"));
    }

    @Test
    public void testGetFileName_4() {
        assertEquals("70s180w_20101117_gmted_mea075", instance.getFileName(-61.015725, -156.621094));
    }

    @Test
    public void testGetFileName_6() {
        assertEquals("70s150e_20101117_gmted_mea075", instance.getFileName(-61.015725, 162.949219));
    }

    @Disabled
    @Test
    public void testGetEle_1() {
        assertEquals(339, instance.getEle(49.949784, 11.57517), precision);
    }

    @Disabled
    @Test
    public void testGetEle_2() {
        assertEquals(438, instance.getEle(49.968668, 11.575127), precision);
    }

    @Disabled
    @Test
    public void testGetEle_3() {
        assertEquals(432, instance.getEle(49.968682, 11.574842), precision);
    }

    @Disabled
    @Test
    public void testGetEle_4() {
        assertEquals(3169, instance.getEle(-22.532854, -65.110474), precision);
    }

    @Disabled
    @Test
    public void testGetEle_5() {
        assertEquals(124, instance.getEle(38.065392, -87.099609), precision);
    }

    @Disabled
    @Test
    public void testGetEle_6() {
        assertEquals(1615, instance.getEle(40, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_7() {
        assertEquals(1618, instance.getEle(39.99999999, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_8() {
        assertEquals(1618, instance.getEle(39.9999999, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_9() {
        assertEquals(1618, instance.getEle(39.999999, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_10() {
        assertEquals(1070, instance.getEle(47.468668, 14.575127), precision);
    }

    @Disabled
    @Test
    public void testGetEle_11() {
        assertEquals(1115, instance.getEle(47.467753, 14.573911), precision);
    }

    @Disabled
    @Test
    public void testGetEle_12() {
        assertEquals(1990, instance.getEle(46.468835, 12.578777), precision);
    }

    @Disabled
    @Test
    public void testGetEle_13() {
        assertEquals(841, instance.getEle(48.469123, 9.576393), precision);
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
        assertEquals(108, instance.getEle(60.0000001, 16), precision);
    }

    @Disabled
    @Test
    public void testGetEle_17() {
        assertEquals(0, instance.getEle(60.0000001, 19), precision);
    }

    @Disabled
    @Test
    public void testGetEle_18() {
        assertEquals(14, instance.getEle(60.251, 18.805), precision);
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_1() {
        assertEquals("50n000e_20101117_gmted_mea075", instance.getFileName(69.999999, 19.493));
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_2() {
        assertEquals(268, instance.getEle(69.999999, 19.5249), precision);
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_3() {
        assertEquals("70n000e_20101117_gmted_mea075", instance.getFileName(70, 19.493));
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_4() {
        assertEquals(298, instance.getEle(70, 19.5249), precision);
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_5() {
        assertEquals("50n000e_20101117_gmted_mea075", instance.getFileName(69.999999, 19.236));
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_6() {
        assertEquals(245, instance.getEle(69.999999, 19.236), precision);
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_7() {
        assertEquals("70n000e_20101117_gmted_mea075", instance.getFileName(70, 19.236));
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_8() {
        assertEquals(241, instance.getEle(70, 19.236), precision);
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_1() {
        assertEquals("50n000e_20101117_gmted_mea075", instance.getFileName(53, 29.999999));
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_2() {
        assertEquals(143, instance.getEle(53, 29.999999), precision);
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_3() {
        assertEquals("50n030e_20101117_gmted_mea075", instance.getFileName(53, 30.000001));
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_4() {
        assertEquals(142, instance.getEle(53, 30.000001), precision);
    }

    @ParameterizedTest
    @MethodSource("Provider_testMinLat_1to2")
    public void testMinLat_1to2(int param1, double param2) {
        assertEquals(param1, instance.getMinLatForTile(param2));
    }

    static public Stream<Arguments> Provider_testMinLat_1to2() {
        return Stream.of(arguments(50, 52.5), arguments(10, 29.9));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDownloadUrl_1_5")
    public void testGetDownloadUrl_1_5(String param1, double param2, double param3) {
        assertTrue(instance.getDownloadURL(param2, param3).contains(param1));
    }

    static public Stream<Arguments> Provider_testGetDownloadUrl_1_5() {
        return Stream.of(arguments("E000/30N000E_20101117_gmted_mea075.tif", 42.940339, 11.953125), arguments("E150/70N150E_20101117_gmted_mea075.tif", 74.590108, 166.640625));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDownloadUrl_2to3")
    public void testGetDownloadUrl_2to3(String param1, double param2, double param3) {
        assertTrue(instance.getDownloadURL(param2, -param3).contains(param1));
    }

    static public Stream<Arguments> Provider_testGetDownloadUrl_2to3() {
        return Stream.of(arguments("W090/30N090W_20101117_gmted_mea075.tif", 38.548165, 77.167969), arguments("W180/70N180W_20101117_gmted_mea075.tif", 74.116047, 169.277344));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFileName_1_5")
    public void testGetFileName_1_5(String param1, double param2, double param3) {
        assertEquals(param1, instance.getFileName(param2, param3));
    }

    static public Stream<Arguments> Provider_testGetFileName_1_5() {
        return Stream.of(arguments("30n000e_20101117_gmted_mea075", 42.940339, 11.953125), arguments("70n150e_20101117_gmted_mea075", 74.590108, 166.640625));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFileName_2to3")
    public void testGetFileName_2to3(String param1, double param2, double param3) {
        assertEquals(param1, instance.getFileName(param2, -param3));
    }

    static public Stream<Arguments> Provider_testGetFileName_2to3() {
        return Stream.of(arguments("30n090w_20101117_gmted_mea075", 38.548165, 77.167969), arguments("70n180w_20101117_gmted_mea075", 74.116047, 169.277344));
    }
}
