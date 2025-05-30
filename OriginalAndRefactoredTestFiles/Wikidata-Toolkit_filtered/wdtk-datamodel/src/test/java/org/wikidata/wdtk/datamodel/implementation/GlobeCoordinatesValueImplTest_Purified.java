package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.GlobeCoordinatesValue;
import java.io.IOException;

public class GlobeCoordinatesValueImplTest_Purified {

    private final ObjectMapper mapper = new ObjectMapper();

    private final GlobeCoordinatesValue c1 = new GlobeCoordinatesValueImpl(12.3, 14.1, GlobeCoordinatesValue.PREC_DEGREE, GlobeCoordinatesValue.GLOBE_EARTH);

    private final GlobeCoordinatesValue c2 = new GlobeCoordinatesValueImpl(12.3, 14.1, GlobeCoordinatesValue.PREC_DEGREE, GlobeCoordinatesValue.GLOBE_EARTH);

    private final GlobeCoordinatesValue c3 = new GlobeCoordinatesValueImpl(12.3, 14.1, GlobeCoordinatesValue.PREC_DEGREE, "earth");

    private final String JSON_GLOBE_COORDINATES_VALUE = "{\"type\":\"" + ValueImpl.JSON_VALUE_TYPE_GLOBE_COORDINATES + "\", \"value\":{\"latitude\":12.3,\"longitude\":14.1,\"precision\":1.0,\"globe\":\"http://www.wikidata.org/entity/Q2\"}}";

    @Test
    public void dataIsCorrect_1() {
        assertEquals(c1.getLatitude(), 12.3, 0);
    }

    @Test
    public void dataIsCorrect_2() {
        assertEquals(c1.getLongitude(), 14.1, 0);
    }

    @Test
    public void dataIsCorrect_3() {
        assertEquals(c1.getPrecision(), GlobeCoordinatesValue.PREC_DEGREE, 0);
    }

    @Test
    public void dataIsCorrect_4() {
        assertEquals(c1.getGlobe(), GlobeCoordinatesValue.GLOBE_EARTH);
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(c1, c1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(c1, c2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        GlobeCoordinatesValue gcDiffLatitude = new GlobeCoordinatesValueImpl(12.1, 14.1, GlobeCoordinatesValue.PREC_DEGREE, GlobeCoordinatesValue.GLOBE_EARTH);
        assertNotEquals(c1, gcDiffLatitude);
    }

    @Test
    public void equalityBasedOnContent_4() {
        GlobeCoordinatesValue gcDiffLongitude = new GlobeCoordinatesValueImpl(12.3, 14.2, GlobeCoordinatesValue.PREC_DEGREE, GlobeCoordinatesValue.GLOBE_EARTH);
        assertNotEquals(c1, gcDiffLongitude);
    }

    @Test
    public void equalityBasedOnContent_5() {
        GlobeCoordinatesValue gcDiffPrecision = new GlobeCoordinatesValueImpl(12.3, 14.1, GlobeCoordinatesValue.PREC_MILLI_ARCSECOND, GlobeCoordinatesValue.GLOBE_EARTH);
        assertNotEquals(c1, gcDiffPrecision);
    }

    @Test
    public void equalityBasedOnContent_6() {
        GlobeCoordinatesValue gcDiffGlobe = new GlobeCoordinatesValueImpl(12.3, 14.1, GlobeCoordinatesValue.PREC_DEGREE, "http://wikidata.org/entity/Q367221");
        assertNotEquals(c1, gcDiffGlobe);
    }

    @Test
    public void equalityBasedOnContent_7() {
        assertNotEquals(c1, null);
    }

    @Test
    public void equalityBasedOnContent_8() {
        assertNotEquals(c1, this);
    }
}
