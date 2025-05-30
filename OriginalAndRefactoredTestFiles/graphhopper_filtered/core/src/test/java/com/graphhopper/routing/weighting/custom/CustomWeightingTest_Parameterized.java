package com.graphhopper.routing.weighting.custom;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphhopper.json.Statement;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.*;
import com.graphhopper.routing.querygraph.VirtualEdgeIteratorState;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.weighting.DefaultTurnCostProvider;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.storage.BaseGraph;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.graphhopper.json.Statement.*;
import static com.graphhopper.json.Statement.Op.LIMIT;
import static com.graphhopper.json.Statement.Op.MULTIPLY;
import static com.graphhopper.routing.ev.RoadClass.*;
import static com.graphhopper.routing.weighting.TurnCostProvider.NO_TURN_COST_PROVIDER;
import static com.graphhopper.util.GHUtility.getEdge;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CustomWeightingTest_Parameterized {

    BaseGraph graph;

    DecimalEncodedValue avSpeedEnc;

    BooleanEncodedValue accessEnc;

    DecimalEncodedValue maxSpeedEnc;

    EnumEncodedValue<RoadClass> roadClassEnc;

    EncodingManager encodingManager;

    BooleanEncodedValue turnRestrictionEnc = TurnRestriction.create("car");

    @BeforeEach
    public void setup() {
        accessEnc = VehicleAccess.create("car");
        avSpeedEnc = VehicleSpeed.create("car", 5, 5, true);
        encodingManager = new EncodingManager.Builder().add(accessEnc).add(avSpeedEnc).add(Toll.create()).add(Hazmat.create()).add(RouteNetwork.create(BikeNetwork.KEY)).add(MaxSpeed.create()).add(RoadClass.create()).add(RoadClassLink.create()).addTurnCostEncodedValue(turnRestrictionEnc).build();
        maxSpeedEnc = encodingManager.getDecimalEncodedValue(MaxSpeed.KEY);
        roadClassEnc = encodingManager.getEnumEncodedValue(KEY, RoadClass.class);
        graph = new BaseGraph.Builder(encodingManager).create();
    }

    private void setTurnRestriction(Graph graph, int from, int via, int to) {
        graph.getTurnCostStorage().set(turnRestrictionEnc, getEdge(graph, from, via).getEdge(), via, getEdge(graph, via, to).getEdge(), true);
    }

    private CustomModel createSpeedCustomModel(DecimalEncodedValue speedEnc) {
        CustomModel customModel = new CustomModel();
        customModel.addToSpeed(If("true", LIMIT, speedEnc.getName()));
        return customModel;
    }

    private Weighting createWeighting(CustomModel vehicleModel) {
        return CustomModelParser.createWeighting(encodingManager, NO_TURN_COST_PROVIDER, vehicleModel);
    }

    @Test
    public void testMaxSpeed_1() {
        assertEquals(155, avSpeedEnc.getMaxOrMaxStorableDecimal(), 0.1);
    }

    @Test
    public void testMaxSpeed_4() {
        assertEquals(1d / 150 * 3.6, createWeighting(createSpeedCustomModel(avSpeedEnc).addToSpeed(If("road_class == SERVICE", MULTIPLY, "1.5")).addToSpeed(If("true", LIMIT, "150"))).calcMinWeightPerDistance(), .001);
    }

    @ParameterizedTest
    @MethodSource("Provider_testMaxSpeed_2to3")
    public void testMaxSpeed_2to3(double param1, double param2, double param3, int param4, boolean param5, int param6) {
        assertEquals(param3 / param4 * param2, createWeighting(createSpeedCustomModel(avSpeedEnc).addToSpeed(If(param5, LIMIT, param6))).calcMinWeightPerDistance(), param1);
    }

    static public Stream<Arguments> Provider_testMaxSpeed_2to3() {
        return Stream.of(arguments(.001, 3.6, 1d, 72, true, 72), arguments(.001, 3.6, 1d, 155, true, 180));
    }
}
