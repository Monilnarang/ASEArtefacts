package com.graphhopper.reader.osm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import static com.graphhopper.reader.osm.RestrictionType.NO;
import static com.graphhopper.reader.osm.RestrictionType.ONLY;
import static org.junit.jupiter.api.Assertions.*;

class RestrictionTagParserTest_Purified {

    private final Map<String, Object> tags = new LinkedHashMap<>();

    @BeforeEach
    void setup() {
        tags.clear();
        tags.put("type", "restriction");
    }

    private RestrictionTagParser.Result parseForVehicleTypes(String... vehicleTypes) throws OSMRestrictionException {
        return new RestrictionTagParser(Arrays.asList(vehicleTypes), null).parseRestrictionTags(tags);
    }

    @Test
    void exceptBus_1_testMerged_1() throws OSMRestrictionException {
        RestrictionTagParser.Result res = parseForVehicleTypes("motorcar", "motor_vehicle", "vehicle");
        assertEquals("only_right_turn", res.getRestriction());
        assertEquals(ONLY, res.getRestrictionType());
    }

    @Test
    void exceptBus_3() throws OSMRestrictionException {
        assertNull(parseForVehicleTypes("psv"));
    }
}
