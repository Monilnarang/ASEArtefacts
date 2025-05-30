package com.graphhopper.reader.dem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SkadiProviderTest_Parameterized {

    SkadiProvider instance;

    @BeforeEach
    public void setUp() {
        instance = new SkadiProvider();
    }

    @AfterEach
    public void tearDown() {
        instance.release();
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDownloadUrl_1_5")
    public void testGetDownloadUrl_1_5(String param1, double param2, double param3) {
        assertEquals(param1, instance.getDownloadURL(param2, param3));
    }

    static public Stream<Arguments> Provider_testGetDownloadUrl_1_5() {
        return Stream.of(arguments("N42/N42E011.hgt.gz", 42.940339, 11.953125), arguments("N24/N24E120.hgt.gz", 24.590108, 120.640625));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDownloadUrl_2to3")
    public void testGetDownloadUrl_2to3(String param1, double param2, double param3) {
        assertEquals(param1, instance.getDownloadURL(param2, -param3));
    }

    static public Stream<Arguments> Provider_testGetDownloadUrl_2to3() {
        return Stream.of(arguments("N38/N38W078.hgt.gz", 38.548165, 77.167969), arguments("N14/N14W005.hgt.gz", 14.116047, 4.277344));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDownloadUrl_4_6")
    public void testGetDownloadUrl_4_6(String param1, double param2, double param3) {
        assertEquals(param1, instance.getDownloadURL(-param2, -param3));
    }

    static public Stream<Arguments> Provider_testGetDownloadUrl_4_6() {
        return Stream.of(arguments("S52/S52W058.hgt.gz", 51.015725, 57.621094), arguments("S42/S42W063.hgt.gz", 41.015725, 62.949219));
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
