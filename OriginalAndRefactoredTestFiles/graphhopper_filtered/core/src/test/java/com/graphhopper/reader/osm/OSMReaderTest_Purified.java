package com.graphhopper.reader.osm;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.GraphHopperTest;
import com.graphhopper.reader.ReaderElement;
import com.graphhopper.reader.ReaderRelation;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.reader.dem.ElevationProvider;
import com.graphhopper.reader.dem.SRTMProvider;
import com.graphhopper.routing.OSMReaderConfig;
import com.graphhopper.routing.TestProfiles;
import com.graphhopper.routing.ev.*;
import com.graphhopper.routing.util.*;
import com.graphhopper.routing.util.countryrules.CountryRuleFactory;
import com.graphhopper.routing.util.parsers.CountryParser;
import com.graphhopper.routing.util.parsers.OSMBikeNetworkTagParser;
import com.graphhopper.routing.util.parsers.OSMMtbNetworkTagParser;
import com.graphhopper.routing.util.parsers.OSMRoadAccessParser;
import com.graphhopper.storage.*;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.storage.index.Snap;
import com.graphhopper.util.*;
import com.graphhopper.util.details.PathDetail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import static com.graphhopper.routing.util.TransportationMode.CAR;
import static com.graphhopper.util.GHUtility.readCountries;
import static org.junit.jupiter.api.Assertions.*;

public class OSMReaderTest_Purified {

    private final String file1 = "test-osm.xml";

    private final String file2 = "test-osm2.xml";

    private final String file3 = "test-osm3.xml";

    private final String file4 = "test-osm4.xml";

    private final String fileBarriers = "test-barriers.xml";

    private final String dir = "./target/tmp/test-db";

    private BooleanEncodedValue carAccessEnc;

    private DecimalEncodedValue carSpeedEnc;

    private BooleanEncodedValue footAccessEnc;

    private EdgeExplorer carOutExplorer;

    private EdgeExplorer carAllExplorer;

    @BeforeEach
    public void setUp() {
        new File(dir).mkdirs();
    }

    @AfterEach
    public void tearDown() {
        Helper.removeDir(new File(dir));
    }

    protected int findID(LocationIndex index, double lat, double lon) {
        return index.findClosest(lat, lon, EdgeFilter.ALL_EDGES).getClosestNode();
    }

    void checkLoop(GraphHopper hopper) {
        BaseGraph graph = hopper.getBaseGraph();
        assertEquals(4, graph.getNodes());
        AllEdgesIterator iter = graph.getAllEdges();
        assertEquals(4, iter.length());
        while (iter.next()) {
            assertTrue(iter.getAdjNode() != iter.getBaseNode(), "found a loop");
        }
        int nodeB = AbstractGraphStorageTester.getIdOf(graph, 12);
        assertTrue(nodeB > -1, "could not find OSM node B");
        assertEquals(4, GHUtility.count(graph.createEdgeExplorer().setBaseNode(nodeB)));
    }

    private AreaIndex<CustomArea> createCountryIndex() {
        return new AreaIndex<>(readCountries());
    }

    class GraphHopperFacade extends GraphHopper {

        public GraphHopperFacade(String osmFile) {
            this(osmFile, "");
        }

        public GraphHopperFacade(String osmFile, String prefLang) {
            setStoreOnFlush(false);
            setOSMFile(osmFile);
            setGraphHopperLocation(dir);
            String str = "max_width,max_height,max_weight,foot_access, foot_priority, foot_average_speed, car_access, car_average_speed, bike_access, bike_priority, bike_average_speed";
            setEncodedValuesString(str);
            setProfiles(TestProfiles.accessSpeedAndPriority("foot"), TestProfiles.accessAndSpeed("car").setTurnCostsConfig(new TurnCostsConfig(List.of("motorcar", "motor_vehicle"))), TestProfiles.accessSpeedAndPriority("bike").setTurnCostsConfig(new TurnCostsConfig(List.of("bicycle"))), TestProfiles.constantSpeed("truck", 100).setTurnCostsConfig(new TurnCostsConfig(List.of("hgv", "motor_vehicle"))));
            getReaderConfig().setPreferredLanguage(prefLang);
        }

        @Override
        protected void importOSM() {
            BaseGraph baseGraph = new BaseGraph.Builder(getEncodingManager()).set3D(hasElevation()).withTurnCosts(getEncodingManager().needsTurnCostsSupport()).build();
            setBaseGraph(baseGraph);
            super.importOSM();
            carAccessEnc = getEncodingManager().getBooleanEncodedValue(VehicleAccess.key("car"));
            carSpeedEnc = getEncodingManager().getDecimalEncodedValue(VehicleSpeed.key("car"));
            carOutExplorer = getBaseGraph().createEdgeExplorer(AccessFilter.outEdges(carAccessEnc));
            carAllExplorer = getBaseGraph().createEdgeExplorer(AccessFilter.allEdges(carAccessEnc));
            footAccessEnc = getEncodingManager().getBooleanEncodedValue(VehicleAccess.key("foot"));
        }

        @Override
        protected File _getOSMFile() {
            return new File(getClass().getResource(getOSMFile()).getFile());
        }
    }

    @Test
    public void testFixWayName_1() {
        assertEquals("B8, B12", OSMReader.fixWayName("B8;B12"));
    }

    @Test
    public void testFixWayName_2() {
        assertEquals("B8, B12", OSMReader.fixWayName("B8; B12"));
    }
}
