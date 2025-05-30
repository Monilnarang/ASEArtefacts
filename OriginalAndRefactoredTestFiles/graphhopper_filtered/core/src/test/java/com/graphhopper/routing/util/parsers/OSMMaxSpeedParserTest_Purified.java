package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.*;
import com.graphhopper.storage.IntsRef;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OSMMaxSpeedParserTest_Purified {

    @Test
    public void parseMaxspeedString_1() {
        assertEquals(40, OSMMaxSpeedParser.parseMaxspeedString("40 km/h"), 0.1);
    }

    @Test
    public void parseMaxspeedString_2() {
        assertEquals(40, OSMMaxSpeedParser.parseMaxspeedString("40km/h"), 0.1);
    }

    @Test
    public void parseMaxspeedString_3() {
        assertEquals(40, OSMMaxSpeedParser.parseMaxspeedString("40kmh"), 0.1);
    }

    @Test
    public void parseMaxspeedString_4() {
        assertEquals(64.4, OSMMaxSpeedParser.parseMaxspeedString("40mph"), 0.1);
    }

    @Test
    public void parseMaxspeedString_5() {
        assertEquals(48.3, OSMMaxSpeedParser.parseMaxspeedString("30 mph"), 0.1);
    }

    @Test
    public void parseMaxspeedString_6() {
        assertEquals(18.5, OSMMaxSpeedParser.parseMaxspeedString("10 knots"), 0.1);
    }

    @Test
    public void parseMaxspeedString_7() {
        assertEquals(19, OSMMaxSpeedParser.parseMaxspeedString("19 kph"), 0.1);
    }

    @Test
    public void parseMaxspeedString_8() {
        assertEquals(19, OSMMaxSpeedParser.parseMaxspeedString("19kph"), 0.1);
    }

    @Test
    public void parseMaxspeedString_9() {
        assertEquals(100, OSMMaxSpeedParser.parseMaxspeedString("100"), 0.1);
    }

    @Test
    public void parseMaxspeedString_10() {
        assertEquals(100.5, OSMMaxSpeedParser.parseMaxspeedString("100.5"), 0.1);
    }

    @Test
    public void parseMaxspeedString_11() {
        assertEquals(4.8, OSMMaxSpeedParser.parseMaxspeedString("3 mph"), 0.1);
    }

    @Test
    public void parseMaxspeedString_12() {
        assertEquals(OSMMaxSpeedParser.MAXSPEED_NONE, OSMMaxSpeedParser.parseMaxspeedString("none"), 0.1);
    }

    @Test
    public void parseMaxspeedString_invalid_1() {
        assertEquals(MaxSpeed.MAXSPEED_MISSING, OSMMaxSpeedParser.parseMaxspeedString(null));
    }

    @Test
    public void parseMaxspeedString_invalid_2() {
        assertEquals(MaxSpeed.MAXSPEED_MISSING, OSMMaxSpeedParser.parseMaxspeedString("-20"));
    }

    @Test
    public void parseMaxspeedString_invalid_3() {
        assertEquals(MaxSpeed.MAXSPEED_MISSING, OSMMaxSpeedParser.parseMaxspeedString("0"));
    }

    @Test
    public void parseMaxspeedString_invalid_4() {
        assertEquals(MaxSpeed.MAXSPEED_MISSING, OSMMaxSpeedParser.parseMaxspeedString("1"));
    }

    @Test
    public void parseMaxspeedString_invalid_5() {
        assertEquals(MaxSpeed.MAXSPEED_MISSING, OSMMaxSpeedParser.parseMaxspeedString("1km/h"));
    }

    @Test
    public void parseMaxspeedString_invalid_6() {
        assertEquals(MaxSpeed.MAXSPEED_MISSING, OSMMaxSpeedParser.parseMaxspeedString("1mph"));
    }

    @Test
    public void parseMaxspeedString_invalid_7() {
        assertEquals(MaxSpeed.MAXSPEED_MISSING, OSMMaxSpeedParser.parseMaxspeedString("2"));
    }

    @Test
    public void parseMaxspeedString_invalid_8() {
        assertEquals(MaxSpeed.MAXSPEED_MISSING, OSMMaxSpeedParser.parseMaxspeedString("3"));
    }

    @Test
    public void parseMaxspeedString_invalid_9() {
        assertEquals(MaxSpeed.MAXSPEED_MISSING, OSMMaxSpeedParser.parseMaxspeedString("4"));
    }
}
