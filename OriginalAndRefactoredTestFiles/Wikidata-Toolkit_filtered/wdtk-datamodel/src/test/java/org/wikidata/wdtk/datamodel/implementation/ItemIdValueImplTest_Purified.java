package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import org.wikidata.wdtk.datamodel.interfaces.UnsupportedEntityIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Value;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemIdValueImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper(Datamodel.SITE_WIKIDATA);

    private final ItemIdValueImpl item1 = new ItemIdValueImpl("Q42", "http://www.wikidata.org/entity/");

    private final ItemIdValueImpl item2 = new ItemIdValueImpl("Q42", "http://www.wikidata.org/entity/");

    private final ItemIdValueImpl item3 = new ItemIdValueImpl("Q57", "http://www.wikidata.org/entity/");

    private final ItemIdValueImpl item4 = new ItemIdValueImpl("Q42", "http://www.example.org/entity/");

    private final String JSON_ITEM_ID_VALUE = "{\"type\":\"wikibase-entityid\",\"value\":{\"entity-type\":\"item\",\"numeric-id\":42,\"id\":\"Q42\"}}";

    private final String JSON_ITEM_ID_VALUE_WITHOUT_ID = "{\"type\":\"wikibase-entityid\",\"value\":{\"entity-type\":\"item\",\"numeric-id\":\"42\"}}";

    private final String JSON_ITEM_ID_VALUE_WITHOUT_NUMERICAL_ID = "{\"type\":\"wikibase-entityid\",\"value\":{\"id\":\"Q42\"}}";

    private final String JSON_ITEM_ID_VALUE_WRONG_ID = "{\"type\":\"wikibase-entityid\",\"value\":{\"id\":\"W42\"}}";

    private final String JSON_ITEM_ID_VALUE_UNSUPPORTED_TYPE = "{\"type\":\"wikibase-entityid\",\"value\":{\"entity-type\":\"foo\",\"numeric-id\":42,\"id\":\"F42\"}}";

    private final String JSON_ITEM_ID_VALUE_UNSUPPORTED_NO_ID = "{\"type\":\"wikibase-entityid\",\"value\":{}}";

    @Test
    public void iriIsCorrect_1() {
        assertEquals(item1.getIri(), "http://www.wikidata.org/entity/Q42");
    }

    @Test
    public void iriIsCorrect_2() {
        assertEquals(item4.getIri(), "http://www.example.org/entity/Q42");
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(item1, item1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(item1, item2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        assertNotEquals(item1, item3);
    }

    @Test
    public void equalityBasedOnContent_4() {
        assertNotEquals(item1, item4);
    }

    @Test
    public void equalityBasedOnContent_5() {
        assertNotEquals(item1, null);
    }

    @Test
    public void equalityBasedOnContent_6() {
        assertNotEquals(item1, this);
    }
}
