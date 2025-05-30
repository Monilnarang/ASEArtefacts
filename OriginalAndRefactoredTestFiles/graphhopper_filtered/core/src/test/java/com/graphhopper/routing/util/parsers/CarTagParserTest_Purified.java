package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderNode;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.*;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.PriorityCode;
import com.graphhopper.routing.util.WayAccess;
import com.graphhopper.util.PMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class CarTagParserTest_Purified {

    private final EncodingManager em = createEncodingManager("car");

    final CarAccessParser parser = createParser(em, new PMap("block_fords=true"));

    final CarAverageSpeedParser speedParser = new CarAverageSpeedParser(em);

    private final BooleanEncodedValue roundaboutEnc = em.getBooleanEncodedValue(Roundabout.KEY);

    private final BooleanEncodedValue accessEnc = parser.getAccessEnc();

    private final DecimalEncodedValue avSpeedEnc = speedParser.getAverageSpeedEnc();

    private EncodingManager createEncodingManager(String carName) {
        return new EncodingManager.Builder().add(VehicleAccess.create(carName)).add(VehicleSpeed.create(carName, 7, 2, true)).addTurnCostEncodedValue(TurnCost.create(carName, 1)).add(VehicleAccess.create("bike")).add(VehicleSpeed.create("bike", 4, 2, false)).add(VehiclePriority.create("bike", 4, PriorityCode.getFactor(1), false)).add(RouteNetwork.create(BikeNetwork.KEY)).add(Smoothness.create()).add(Roundabout.create()).add(FerrySpeed.create()).build();
    }

    CarAccessParser createParser(EncodedValueLookup lookup, PMap properties) {
        return new CarAccessParser(lookup, properties);
    }

    @Test
    public void testFordAccess_1() {
        assertTrue(parser.isBlockFords());
    }

    @Test
    public void testFordAccess_2_testMerged_2() {
        ReaderNode node = new ReaderNode(0, 0.0, 0.0);
        node.setTag("ford", "yes");
        ReaderWay way = new ReaderWay(1);
        way.setTag("highway", "unclassified");
        way.setTag("ford", "yes");
        assertTrue(parser.getAccess(way).canSkip());
        assertTrue(parser.isBarrier(node));
        CarAccessParser tmpParser = new CarAccessParser(em, new PMap("block_fords=false"));
        assertTrue(tmpParser.getAccess(way).isWay());
        assertFalse(tmpParser.isBarrier(node));
    }
}
