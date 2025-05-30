package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.*;
import com.graphhopper.storage.IntsRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OSMTollParserTest_Purified {

    private EnumEncodedValue<Toll> tollEnc;

    private OSMTollParser parser;

    @BeforeEach
    public void setUp() {
        tollEnc = Toll.create();
        tollEnc.init(new EncodedValue.InitializerConfig());
        parser = new OSMTollParser(tollEnc);
    }

    private Toll getToll(String highway, String toll, Country country) {
        ReaderWay readerWay = new ReaderWay(123L);
        readerWay.setTag("highway", highway);
        readerWay.setTag("country", country);
        String[] tollKV = toll.split("=");
        if (tollKV.length > 1)
            readerWay.setTag(tollKV[0], tollKV[1]);
        IntsRef relFlags = new IntsRef(2);
        EdgeIntAccess edgeIntAccess = new ArrayEdgeIntAccess(1);
        int edgeId = 0;
        parser.handleWayTags(edgeId, edgeIntAccess, readerWay, relFlags);
        return tollEnc.getEnum(false, edgeId, edgeIntAccess);
    }

    @Test
    void country_1() {
        assertEquals(Toll.ALL, getToll("motorway", "", Country.HUN));
    }

    @Test
    void country_2() {
        assertEquals(Toll.HGV, getToll("trunk", "", Country.HUN));
    }

    @Test
    void country_3() {
        assertEquals(Toll.HGV, getToll("primary", "", Country.HUN));
    }

    @Test
    void country_4() {
        assertEquals(Toll.NO, getToll("secondary", "", Country.HUN));
    }

    @Test
    void country_5() {
        assertEquals(Toll.NO, getToll("tertiary", "", Country.HUN));
    }

    @Test
    void country_6() {
        assertEquals(Toll.ALL, getToll("motorway", "", Country.FRA));
    }

    @Test
    void country_7() {
        assertEquals(Toll.NO, getToll("trunk", "", Country.FRA));
    }

    @Test
    void country_8() {
        assertEquals(Toll.NO, getToll("primary", "", Country.FRA));
    }

    @Test
    void country_9() {
        assertEquals(Toll.NO, getToll("motorway", "", Country.MEX));
    }

    @Test
    void country_10() {
        assertEquals(Toll.NO, getToll("trunk", "", Country.MEX));
    }

    @Test
    void country_11() {
        assertEquals(Toll.NO, getToll("primary", "", Country.MEX));
    }

    @Test
    void country_12() {
        assertEquals(Toll.ALL, getToll("secondary", "toll=yes", Country.HUN));
    }

    @Test
    void country_13() {
        assertEquals(Toll.HGV, getToll("secondary", "toll:hgv=yes", Country.HUN));
    }

    @Test
    void country_14() {
        assertEquals(Toll.HGV, getToll("secondary", "toll:N3=yes", Country.HUN));
    }

    @Test
    void country_15() {
        assertEquals(Toll.NO, getToll("secondary", "toll=no", Country.HUN));
    }
}
