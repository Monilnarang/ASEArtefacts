package com.graphhopper.reader.dem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SRTMProviderTest_Purified {

    private double precision = .1;

    SRTMProvider instance;

    @BeforeEach
    public void setUp() {
        instance = new SRTMProvider();
    }

    @AfterEach
    public void tearDown() {
        instance.release();
    }

    @Test
    public void testGetFileString_1() {
        assertEquals("Eurasia/N49E011", instance.getFileName(49, 11));
    }

    @Test
    public void testGetFileString_2() {
        assertEquals("Eurasia/N52W002", instance.getFileName(52.268157, -1.230469));
    }

    @Test
    public void testGetFileString_3() {
        assertEquals("Africa/S06E034", instance.getFileName(-5.965754, 34.804687));
    }

    @Test
    public void testGetFileString_4() {
        assertEquals("Australia/S29E131", instance.getFileName(-28.304381, 131.484375));
    }

    @Test
    public void testGetFileString_5() {
        assertEquals("South_America/S09W045", instance.getFileName(-9, -45));
    }

    @Test
    public void testGetFileString_6() {
        assertEquals("South_America/S10W046", instance.getFileName(-9.1, -45.1));
    }

    @Test
    public void testGetFileString_7() {
        assertEquals("South_America/S10W045", instance.getFileName(-9.6, -45));
    }

    @Test
    public void testGetFileString_8() {
        assertEquals("South_America/S28W071", instance.getFileName(-28, -71));
    }

    @Test
    public void testGetFileString_9() {
        assertEquals("South_America/S29W072", instance.getFileName(-28.88316, -71.070557));
    }
}
