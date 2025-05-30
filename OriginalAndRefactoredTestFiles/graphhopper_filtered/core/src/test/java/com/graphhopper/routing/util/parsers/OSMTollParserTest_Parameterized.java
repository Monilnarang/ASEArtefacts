package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.*;
import com.graphhopper.storage.IntsRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class OSMTollParserTest_Parameterized {

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
    void country_6() {
        assertEquals(Toll.ALL, getToll("motorway", "", Country.FRA));
    }

    @ParameterizedTest
    @MethodSource("Provider_country_1_12")
    void country_1_12(String param1, String param2) {
        assertEquals(Toll.ALL, getToll(param1, param2, Country.HUN));
    }

    static public Stream<Arguments> Provider_country_1_12() {
        return Stream.of(arguments("motorway", ""), arguments("secondary", "toll=yes"));
    }

    @ParameterizedTest
    @MethodSource("Provider_country_2to3_13to14")
    void country_2to3_13to14(String param1, String param2) {
        assertEquals(Toll.HGV, getToll(param1, param2, Country.HUN));
    }

    static public Stream<Arguments> Provider_country_2to3_13to14() {
        return Stream.of(arguments("trunk", ""), arguments("primary", ""), arguments("secondary", "toll:hgv=yes"), arguments("secondary", "toll:N3=yes"));
    }

    @ParameterizedTest
    @MethodSource("Provider_country_4to5_15")
    void country_4to5_15(String param1, String param2) {
        assertEquals(Toll.NO, getToll(param1, param2, Country.HUN));
    }

    static public Stream<Arguments> Provider_country_4to5_15() {
        return Stream.of(arguments("secondary", ""), arguments("tertiary", ""), arguments("secondary", "toll=no"));
    }

    @ParameterizedTest
    @MethodSource("Provider_country_7to8")
    void country_7to8(String param1, String param2) {
        assertEquals(Toll.NO, getToll(param1, param2, Country.FRA));
    }

    static public Stream<Arguments> Provider_country_7to8() {
        return Stream.of(arguments("trunk", ""), arguments("primary", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_country_9to11")
    void country_9to11(String param1, String param2) {
        assertEquals(Toll.NO, getToll(param1, param2, Country.MEX));
    }

    static public Stream<Arguments> Provider_country_9to11() {
        return Stream.of(arguments("motorway", ""), arguments("trunk", ""), arguments("primary", ""));
    }
}
