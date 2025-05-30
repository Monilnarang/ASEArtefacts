package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderNode;
import com.graphhopper.reader.ReaderRelation;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.*;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.OSMParsers;
import com.graphhopper.routing.util.PriorityCode;
import com.graphhopper.storage.IntsRef;
import com.graphhopper.util.PMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.graphhopper.routing.util.PriorityCode.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractBikeTagParserTester_Purified {

    protected EncodingManager encodingManager;

    protected BikeCommonAccessParser accessParser;

    protected BikeCommonAverageSpeedParser speedParser;

    protected BikeCommonPriorityParser priorityParser;

    protected OSMParsers osmParsers;

    protected DecimalEncodedValue priorityEnc;

    protected DecimalEncodedValue avgSpeedEnc;

    protected BooleanEncodedValue accessEnc;

    @BeforeEach
    public void setUp() {
        encodingManager = createEncodingManager();
        accessParser = createAccessParser(encodingManager, new PMap("block_fords=true"));
        speedParser = createAverageSpeedParser(encodingManager);
        priorityParser = createPriorityParser(encodingManager);
        osmParsers = new OSMParsers().addRelationTagParser(relConfig -> new OSMBikeNetworkTagParser(encodingManager.getEnumEncodedValue(BikeNetwork.KEY, RouteNetwork.class), relConfig)).addRelationTagParser(relConfig -> new OSMMtbNetworkTagParser(encodingManager.getEnumEncodedValue(MtbNetwork.KEY, RouteNetwork.class), relConfig)).addWayTagParser(new OSMSmoothnessParser(encodingManager.getEnumEncodedValue(Smoothness.KEY, Smoothness.class))).addWayTagParser(accessParser).addWayTagParser(speedParser).addWayTagParser(priorityParser);
        priorityEnc = priorityParser.getPriorityEnc();
        avgSpeedEnc = speedParser.getAverageSpeedEnc();
        accessEnc = accessParser.getAccessEnc();
    }

    protected abstract EncodingManager createEncodingManager();

    protected abstract BikeCommonAccessParser createAccessParser(EncodedValueLookup lookup, PMap pMap);

    protected abstract BikeCommonAverageSpeedParser createAverageSpeedParser(EncodedValueLookup lookup);

    protected abstract BikeCommonPriorityParser createPriorityParser(EncodedValueLookup lookup);

    protected void assertPriority(PriorityCode expectedPrio, ReaderWay way) {
        IntsRef relFlags = osmParsers.handleRelationTags(new ReaderRelation(0), osmParsers.createRelationFlags());
        ArrayEdgeIntAccess intAccess = ArrayEdgeIntAccess.createFromBytes(encodingManager.getBytesForFlags());
        int edgeId = 0;
        osmParsers.handleWayTags(edgeId, intAccess, way, relFlags);
        assertEquals(PriorityCode.getValue(expectedPrio.getValue()), priorityEnc.getDecimal(false, edgeId, intAccess), 0.01);
    }

    protected void assertPriorityAndSpeed(PriorityCode expectedPrio, double expectedSpeed, ReaderWay way) {
        assertPriorityAndSpeed(expectedPrio, expectedSpeed, way, new ReaderRelation(0));
    }

    protected void assertPriorityAndSpeed(PriorityCode expectedPrio, double expectedSpeed, ReaderWay way, ReaderRelation rel) {
        IntsRef relFlags = osmParsers.handleRelationTags(rel, osmParsers.createRelationFlags());
        ArrayEdgeIntAccess intAccess = ArrayEdgeIntAccess.createFromBytes(encodingManager.getBytesForFlags());
        int edgeId = 0;
        osmParsers.handleWayTags(edgeId, intAccess, way, relFlags);
        assertEquals(PriorityCode.getValue(expectedPrio.getValue()), priorityEnc.getDecimal(false, edgeId, intAccess), 0.01);
        assertEquals(expectedSpeed, avgSpeedEnc.getDecimal(false, edgeId, intAccess), 0.1);
        assertEquals(expectedSpeed, avgSpeedEnc.getDecimal(true, edgeId, intAccess), 0.1);
    }

    protected double getSpeedFromFlags(ReaderWay way) {
        IntsRef relFlags = osmParsers.createRelationFlags();
        ArrayEdgeIntAccess intAccess = ArrayEdgeIntAccess.createFromBytes(encodingManager.getBytesForFlags());
        int edgeId = 0;
        osmParsers.handleWayTags(edgeId, intAccess, way, relFlags);
        return avgSpeedEnc.getDecimal(false, edgeId, intAccess);
    }

    private void assertAccess(ReaderWay way, boolean fwd, boolean bwd) {
        EdgeIntAccess edgeIntAccess = new ArrayEdgeIntAccess(1);
        int edge = 0;
        IntsRef relationFlags = new IntsRef(1);
        accessParser.handleWayTags(edge, edgeIntAccess, way, relationFlags);
        if (fwd)
            assertTrue(accessEnc.getBool(false, edge, edgeIntAccess));
        if (bwd)
            assertTrue(accessEnc.getBool(true, edge, edgeIntAccess));
    }

    @Test
    public void testTramStations_1_testMerged_1() {
        ReaderWay way = new ReaderWay(1);
        way.setTag("highway", "secondary");
        way.setTag("railway", "rail");
        assertTrue(accessParser.getAccess(way).isWay());
        way = new ReaderWay(1);
        way.setTag("railway", "station");
        way.setTag("bicycle", "yes");
        way.setTag("bicycle", "no");
        assertTrue(accessParser.getAccess(way).canSkip());
    }

    @Test
    public void testTramStations_5_testMerged_2() {
        ArrayEdgeIntAccess intAccess = ArrayEdgeIntAccess.createFromBytes(encodingManager.getBytesForFlags());
        int edgeId = 0;
        accessParser.handleWayTags(edgeId, intAccess, way, null);
        speedParser.handleWayTags(edgeId, intAccess, way, null);
        assertEquals(4.0, avgSpeedEnc.getDecimal(false, edgeId, intAccess));
        assertTrue(accessEnc.getBool(false, edgeId, intAccess));
        assertEquals(4, avgSpeedEnc.getDecimal(false, edgeId, intAccess));
        intAccess = ArrayEdgeIntAccess.createFromBytes(encodingManager.getBytesForFlags());
        accessParser.handleWayTags(edgeId, intAccess, way);
        assertEquals(0.0, avgSpeedEnc.getDecimal(false, edgeId, intAccess));
        assertFalse(accessEnc.getBool(false, edgeId, intAccess));
    }
}
