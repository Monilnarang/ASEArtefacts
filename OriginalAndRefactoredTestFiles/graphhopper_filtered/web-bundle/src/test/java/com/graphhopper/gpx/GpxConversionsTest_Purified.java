package com.graphhopper.gpx;

import com.graphhopper.routing.Dijkstra;
import com.graphhopper.routing.InstructionsFromEdges;
import com.graphhopper.routing.Path;
import com.graphhopper.routing.ev.*;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.TraversalMode;
import com.graphhopper.routing.weighting.SpeedWeighting;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.storage.BaseGraph;
import com.graphhopper.storage.NodeAccess;
import com.graphhopper.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import static com.graphhopper.search.KVStorage.KValue;
import static com.graphhopper.util.Parameters.Details.STREET_NAME;
import static org.junit.jupiter.api.Assertions.*;

public class GpxConversionsTest_Purified {

    private DecimalEncodedValue speedEnc;

    private EncodingManager carManager;

    private TranslationMap trMap;

    @BeforeEach
    public void setUp() {
        speedEnc = new DecimalEncodedValueImpl("speed", 5, 5, false);
        carManager = EncodingManager.start().add(speedEnc).add(VehicleAccess.create("car")).add(Roundabout.create()).add(RoadClass.create()).add(RoadClassLink.create()).add(MaxSpeed.create()).build();
        trMap = new TranslationMap().doImport();
    }

    private void verifyGPX(String gpx) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;
        try {
            Source schemaFile = new StreamSource(getClass().getResourceAsStream("gpx-schema.xsd"));
            schema = schemaFactory.newSchema(schemaFile);
        } catch (SAXException e1) {
            throw new IllegalStateException("There was a problem with the schema supplied for validation. Message:" + e1.getMessage());
        }
        Validator validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(new StringReader(gpx)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private double sumDistances(InstructionList il) {
        double val = 0;
        for (Instruction i : il) {
            val += i.getDistance();
        }
        return val;
    }

    @Test
    public void testXMLEscape_issue572_1() {
        assertEquals("_", GpxConversions.simpleXMLEscape("<"));
    }

    @Test
    public void testXMLEscape_issue572_2() {
        assertEquals("_blup_", GpxConversions.simpleXMLEscape("<blup>"));
    }

    @Test
    public void testXMLEscape_issue572_3() {
        assertEquals("a&amp;b", GpxConversions.simpleXMLEscape("a&b"));
    }
}
