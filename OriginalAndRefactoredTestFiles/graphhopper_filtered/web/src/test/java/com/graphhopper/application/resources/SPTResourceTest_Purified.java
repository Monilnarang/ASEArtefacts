package com.graphhopper.application.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.graphhopper.application.GraphHopperApplication;
import com.graphhopper.application.GraphHopperServerConfiguration;
import com.graphhopper.application.util.GraphHopperServerTestConfiguration;
import com.graphhopper.routing.TestProfiles;
import com.graphhopper.util.BodyAndStatus;
import com.graphhopper.util.Helper;
import com.graphhopper.util.TurnCostsConfig;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import static com.graphhopper.application.resources.Util.getWithStatus;
import static com.graphhopper.application.util.TestUtils.clientTarget;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class SPTResourceTest_Purified {

    private static final String DIR = "./target/spt-gh/";

    private static final DropwizardAppExtension<GraphHopperServerConfiguration> app = new DropwizardAppExtension<>(GraphHopperApplication.class, createConfig());

    private static GraphHopperServerConfiguration createConfig() {
        GraphHopperServerTestConfiguration config = new GraphHopperServerTestConfiguration();
        config.getGraphHopperConfiguration().putObject("graph.encoded_values", "max_speed,road_class").putObject("datareader.file", "../core/files/andorra.osm.pbf").putObject("import.osm.ignored_highways", "").putObject("graph.location", DIR).putObject("graph.encoded_values", "car_access, car_average_speed").setProfiles(List.of(TestProfiles.accessAndSpeed("car_without_turncosts", "car"), TestProfiles.accessAndSpeed("car_with_turncosts", "car").setTurnCostsConfig(TurnCostsConfig.car())));
        return config;
    }

    @BeforeAll
    @AfterAll
    public static void cleanUp() {
        Helper.removeDir(new File(DIR));
    }

    private void assertNotAllowed(String hint, String error) {
        BodyAndStatus rsp = getWithStatus(clientTarget(app, "/spt?point=42.531073,1.573792&time_limit=300&columns=street_name,road_class,max_speed" + hint));
        assertEquals(400, rsp.getStatus());
        JsonNode json = rsp.getBody();
        assertTrue(json.get("message").toString().contains(error), json.toString());
    }

    @Test
    public void profileWithLegacyParametersNotAllowed_1() {
        assertNotAllowed("&profile=car&weighting=fastest", "The 'weighting' parameter is no longer supported. You used 'weighting=fastest'");
    }

    @Test
    public void profileWithLegacyParametersNotAllowed_2() {
        assertNotAllowed("&vehicle=car", "profile parameter required");
    }
}
