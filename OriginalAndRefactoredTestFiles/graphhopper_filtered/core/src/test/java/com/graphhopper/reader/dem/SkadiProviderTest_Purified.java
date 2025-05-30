package com.graphhopper.reader.dem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkadiProviderTest_Purified {

    SkadiProvider instance;

    @BeforeEach
    public void setUp() {
        instance = new SkadiProvider();
    }

    @AfterEach
    public void tearDown() {
        instance.release();
    }

    @Test
    public void testGetDownloadUrl_1() {
        assertEquals("N42/N42E011.hgt.gz", instance.getDownloadURL(42.940339, 11.953125));
    }

    @Test
    public void testGetDownloadUrl_2() {
        assertEquals("N38/N38W078.hgt.gz", instance.getDownloadURL(38.548165, -77.167969));
    }

    @Test
    public void testGetDownloadUrl_3() {
        assertEquals("N14/N14W005.hgt.gz", instance.getDownloadURL(14.116047, -4.277344));
    }

    @Test
    public void testGetDownloadUrl_4() {
        assertEquals("S52/S52W058.hgt.gz", instance.getDownloadURL(-51.015725, -57.621094));
    }

    @Test
    public void testGetDownloadUrl_5() {
        assertEquals("N24/N24E120.hgt.gz", instance.getDownloadURL(24.590108, 120.640625));
    }

    @Test
    public void testGetDownloadUrl_6() {
        assertEquals("S42/S42W063.hgt.gz", instance.getDownloadURL(-41.015725, -62.949219));
    }

    @Test
    public void testGetFileName_1() {
        assertEquals("n42e011", instance.getFileName(42.940339, 11.953125));
    }

    @Test
    public void testGetFileName_2() {
        assertEquals("n38w078", instance.getFileName(38.548165, -77.167969));
    }

    @Test
    public void testGetFileName_3() {
        assertEquals("n14w005", instance.getFileName(14.116047, -4.277344));
    }

    @Test
    public void testGetFileName_4() {
        assertEquals("s52w058", instance.getFileName(-51.015725, -57.621094));
    }

    @Test
    public void testGetFileName_5() {
        assertEquals("n24e120", instance.getFileName(24.590108, 120.640625));
    }

    @Test
    public void testGetFileName_6() {
        assertEquals("s42w063", instance.getFileName(-41.015725, -62.949219));
    }
}
