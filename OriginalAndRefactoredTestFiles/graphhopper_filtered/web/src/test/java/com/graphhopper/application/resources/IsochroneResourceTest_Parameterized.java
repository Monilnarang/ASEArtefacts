package com.graphhopper.application.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.graphhopper.application.GraphHopperApplication;
import com.graphhopper.application.GraphHopperServerConfiguration;
import com.graphhopper.application.util.GraphHopperServerTestConfiguration;
import com.graphhopper.routing.TestProfiles;
import com.graphhopper.util.BodyAndStatus;
import com.graphhopper.util.Helper;
import com.graphhopper.util.JsonFeatureCollection;
import com.graphhopper.util.TurnCostsConfig;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.util.Arrays;
import static com.graphhopper.application.resources.Util.getWithStatus;
import static com.graphhopper.application.util.TestUtils.clientTarget;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(DropwizardExtensionsSupport.class)
public class IsochroneResourceTest_Parameterized {

    private static final String DIR = "./target/andorra-gh/";

    public static final DropwizardAppExtension<GraphHopperServerConfiguration> app = new DropwizardAppExtension<>(GraphHopperApplication.class, createConfig());

    private static GraphHopperServerConfiguration createConfig() {
        GraphHopperServerConfiguration config = new GraphHopperServerTestConfiguration();
        config.getGraphHopperConfiguration().putObject("datareader.file", "../core/files/andorra.osm.pbf").putObject("import.osm.ignored_highways", "").putObject("graph.location", DIR).putObject("graph.encoded_values", "car_access, car_average_speed").setProfiles(Arrays.asList(TestProfiles.accessAndSpeed("fast_car", "car").setTurnCostsConfig(TurnCostsConfig.car()), TestProfiles.constantSpeed("short_car", 35).setTurnCostsConfig(TurnCostsConfig.car()), TestProfiles.accessAndSpeed("fast_car_no_turn_restrictions", "car")));
        return config;
    }

    @BeforeAll
    @AfterAll
    public static void cleanUp() {
        Helper.removeDir(new File(DIR));
    }

    private final GeometryFactory geometryFactory = new GeometryFactory();

    private void assertNotAllowed(String hint, String error) {
        BodyAndStatus rsp = getWithStatus(clientTarget(app, "/isochrone?point=42.531073,1.573792" + hint));
        assertEquals(400, rsp.getStatus());
        JsonNode json = rsp.getBody();
        assertTrue(json.get("message").toString().contains(error), json.toString());
    }

    private static void assertIs2D(Geometry geometry) {
        assertAll(Arrays.stream(geometry.getCoordinates()).map(coord -> () -> assertTrue(Double.isNaN(coord.z))));
    }

    @ParameterizedTest
    @MethodSource("Provider_profileWithLegacyParametersNotAllowed_1to2")
    public void profileWithLegacyParametersNotAllowed_1to2(String param1, String param2) {
        assertNotAllowed(param1, param2);
    }

    static public Stream<Arguments> Provider_profileWithLegacyParametersNotAllowed_1to2() {
        return Stream.of(arguments("&profile=fast_car&weighting=fastest", "The 'weighting' parameter is no longer supported. You used 'weighting=fastest'"), arguments("&vehicle=car", "profile parameter required"));
    }
}
