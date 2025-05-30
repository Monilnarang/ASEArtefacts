package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.*;
import com.graphhopper.storage.IntsRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OSMSmoothnessParserTest_Purified {

    private EnumEncodedValue<Smoothness> smoothnessEnc;

    private OSMSmoothnessParser parser;

    @BeforeEach
    public void setUp() {
        smoothnessEnc = Smoothness.create();
        smoothnessEnc.init(new EncodedValue.InitializerConfig());
        parser = new OSMSmoothnessParser(smoothnessEnc);
    }

    @Test
    public void testSimpleTags_1_testMerged_1() {
        EdgeIntAccess edgeIntAccess = new ArrayEdgeIntAccess(1);
        int edgeId = 0;
        parser.handleWayTags(edgeId, edgeIntAccess, readerWay, relFlags);
        assertEquals(Smoothness.MISSING, smoothnessEnc.getEnum(false, edgeId, edgeIntAccess));
        assertEquals(Smoothness.BAD, smoothnessEnc.getEnum(false, edgeId, edgeIntAccess));
    }

    @Test
    public void testSimpleTags_3() {
        assertTrue(Smoothness.BAD.ordinal() < Smoothness.VERY_BAD.ordinal());
    }
}
