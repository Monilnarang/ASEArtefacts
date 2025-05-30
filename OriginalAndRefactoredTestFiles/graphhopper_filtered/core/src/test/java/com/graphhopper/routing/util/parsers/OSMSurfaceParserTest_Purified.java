package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.*;
import com.graphhopper.storage.IntsRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OSMSurfaceParserTest_Purified {

    private EnumEncodedValue<Surface> surfaceEnc;

    private OSMSurfaceParser parser;

    @BeforeEach
    public void setUp() {
        surfaceEnc = Surface.create();
        surfaceEnc.init(new EncodedValue.InitializerConfig());
        parser = new OSMSurfaceParser(surfaceEnc);
    }

    @Test
    public void testSimpleTags_1_testMerged_1() {
        EdgeIntAccess edgeIntAccess = new ArrayEdgeIntAccess(1);
        int edgeId = 0;
        parser.handleWayTags(edgeId, edgeIntAccess, readerWay, relFlags);
        assertEquals(Surface.MISSING, surfaceEnc.getEnum(false, edgeId, edgeIntAccess));
        assertEquals(Surface.COBBLESTONE, surfaceEnc.getEnum(false, edgeId, edgeIntAccess));
        assertEquals(Surface.WOOD, surfaceEnc.getEnum(false, edgeId, edgeIntAccess));
    }

    @Test
    public void testSimpleTags_3() {
        assertTrue(Surface.COBBLESTONE.ordinal() > Surface.ASPHALT.ordinal());
    }
}
