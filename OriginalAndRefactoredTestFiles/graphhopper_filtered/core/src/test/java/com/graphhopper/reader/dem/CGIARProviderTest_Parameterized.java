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

public class CGIARProviderTest_Parameterized {

    private double precision = .1;

    CGIARProvider instance;

    @BeforeEach
    public void setUp() {
        instance = new CGIARProvider();
    }

    @AfterEach
    public void tearDown() {
        instance.release();
    }

    @Disabled
    @Test
    public void testGetEle_1() {
        assertEquals(337, instance.getEle(49.949784, 11.57517), precision);
    }

    @Disabled
    @Test
    public void testGetEle_2() {
        assertEquals(466, instance.getEle(49.968668, 11.575127), precision);
    }

    @Disabled
    @Test
    public void testGetEle_3() {
        assertEquals(455, instance.getEle(49.968682, 11.574842), precision);
    }

    @Disabled
    @Test
    public void testGetEle_4() {
        assertEquals(3134, instance.getEle(-22.532854, -65.110474), precision);
    }

    @Disabled
    @Test
    public void testGetEle_5() {
        assertEquals(120, instance.getEle(38.065392, -87.099609), precision);
    }

    @Disabled
    @Test
    public void testGetEle_6() {
        assertEquals(1615, instance.getEle(40, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_7() {
        assertEquals(1615, instance.getEle(39.99999999, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_8() {
        assertEquals(1615, instance.getEle(39.9999999, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_9() {
        assertEquals(1616, instance.getEle(39.999999, -105.2277023), precision);
    }

    @Disabled
    @Test
    public void testGetEle_10() {
        assertEquals(986, instance.getEle(47.468668, 14.575127), precision);
    }

    @Disabled
    @Test
    public void testGetEle_11() {
        assertEquals(1091, instance.getEle(47.467753, 14.573911), precision);
    }

    @Disabled
    @Test
    public void testGetEle_12() {
        assertEquals(1951, instance.getEle(46.468835, 12.578777), precision);
    }

    @Disabled
    @Test
    public void testGetEle_13() {
        assertEquals(841, instance.getEle(48.469123, 9.576393), precision);
    }

    @Disabled
    @Test
    public void testGetEle_14() {
        assertEquals(Double.NaN, instance.getEle(56.4787319, 17.6118363), precision);
    }

    @Disabled
    @Test
    public void testGetEle_15() {
        assertEquals(0, instance.getEle(60.0000001, 16), precision);
    }

    @Disabled
    @Test
    public void testGetEle_16() {
        assertEquals(0, instance.getEle(60.0000001, 16), precision);
    }

    @Disabled
    @Test
    public void testGetEle_17() {
        assertEquals(0, instance.getEle(60.0000001, 19), precision);
    }

    @Disabled
    @Test
    public void testGetEle_18() {
        assertEquals(0, instance.getEle(60.251, 18.805), precision);
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_1() {
        assertEquals("srtm_39_04", instance.getFileName(44.999999, 11.5));
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_2() {
        assertEquals(5, instance.getEle(44.999999, 11.5), precision);
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_3() {
        assertEquals("srtm_39_03", instance.getFileName(45.000001, 11.5));
    }

    @Disabled
    @Test
    public void testGetEleVerticalBorder_4() {
        assertEquals(6, instance.getEle(45.000001, 11.5), precision);
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_1() {
        assertEquals("srtm_38_04", instance.getFileName(44.94, 9.999999));
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_2() {
        assertEquals(48, instance.getEle(44.94, 9.999999), precision);
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_3() {
        assertEquals("srtm_39_04", instance.getFileName(44.94, 10.000001));
    }

    @Disabled
    @Test
    public void testGetEleHorizontalBorder_4() {
        assertEquals(48, instance.getEle(44.94, 10.000001), precision);
    }

    @ParameterizedTest
    @MethodSource("Provider_testDown_1to3_7to8")
    public void testDown_1to3_7to8(int param1, double param2) {
        assertEquals(param1, instance.down(param2));
    }

    static public Stream<Arguments> Provider_testDown_1to3_7to8() {
        return Stream.of(arguments(50, 52.5), arguments(0, 0.1), arguments(0, 0.01), arguments(50, 50), arguments(45, 49));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDown_4to6")
    public void testDown_4to6(int param1, double param2) {
        assertEquals(-param1, instance.down(-param2));
    }

    static public Stream<Arguments> Provider_testDown_4to6() {
        return Stream.of(arguments(5, 0.01), arguments(5, 2), arguments(10, 5.1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFileName_1to2_6to8")
    public void testFileName_1to2_6to8(String param1, int param2, double param3) {
        assertEquals(param1, instance.getFileName(param2, -param3));
    }

    static public Stream<Arguments> Provider_testFileName_1to2_6to8() {
        return Stream.of(arguments("srtm_36_02", 52, 0.1), arguments("srtm_35_02", 50, 10), arguments("srtm_34_08", 20, 11), arguments("srtm_34_08", 20, 14), arguments("srtm_34_08", 20, 15));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFileName_3to4")
    public void testFileName_3to4(String param1, int param2, double param3) {
        assertEquals(param1, instance.getFileName(-param2, -param3));
    }

    static public Stream<Arguments> Provider_testFileName_3to4() {
        return Stream.of(arguments("srtm_36_23", 52, 0.1), arguments("srtm_35_22", 50, 10));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFileName_5_9")
    public void testFileName_5_9(String param1, double param2, double param3) {
        assertEquals(param1, instance.getFileName(param2, param3));
    }

    static public Stream<Arguments> Provider_testFileName_5_9() {
        return Stream.of(arguments("srtm_39_03", 49.9, 11.5), arguments("srtm_37_02", 52.1943832, 0.1363176));
    }
}
