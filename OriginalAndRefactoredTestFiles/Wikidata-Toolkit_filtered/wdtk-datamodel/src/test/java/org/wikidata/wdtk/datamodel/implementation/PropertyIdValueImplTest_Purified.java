package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import java.io.IOException;

public class PropertyIdValueImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper(Datamodel.SITE_WIKIDATA);

    private final PropertyIdValueImpl prop1 = new PropertyIdValueImpl("P42", "http://www.wikidata.org/entity/");

    private final PropertyIdValueImpl prop2 = new PropertyIdValueImpl("P42", "http://www.wikidata.org/entity/");

    private final PropertyIdValueImpl prop3 = new PropertyIdValueImpl("P57", "http://www.wikidata.org/entity/");

    private final PropertyIdValueImpl prop4 = new PropertyIdValueImpl("P42", "http://www.example.org/entity/");

    private final String JSON_PROPERTY_ID_VALUE = "{\"type\":\"wikibase-entityid\",\"value\":{\"entity-type\":\"property\",\"numeric-id\":42,\"id\":\"P42\"}}";

    private final String JSON_PROPERTY_ID_VALUE_WITHOUT_NUMERICAL_ID = "{\"type\":\"wikibase-entityid\",\"value\":{\"id\":\"P42\"}}";

    @Test
    public void iriIsCorrect_1() {
        assertEquals(prop1.getIri(), "http://www.wikidata.org/entity/P42");
    }

    @Test
    public void iriIsCorrect_2() {
        assertEquals(prop4.getIri(), "http://www.example.org/entity/P42");
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(prop1, prop1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(prop1, prop2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        assertNotEquals(prop1, prop3);
    }

    @Test
    public void equalityBasedOnContent_4() {
        assertNotEquals(prop1, prop4);
    }

    @Test
    public void equalityBasedOnContent_5() {
        assertNotEquals(prop1, null);
    }

    @Test
    public void equalityBasedOnContent_6() {
        assertNotEquals(prop1, this);
    }
}
