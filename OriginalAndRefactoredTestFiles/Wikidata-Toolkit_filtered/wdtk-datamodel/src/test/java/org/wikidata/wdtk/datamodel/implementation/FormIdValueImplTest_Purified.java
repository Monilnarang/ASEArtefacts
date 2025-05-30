package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import java.io.IOException;

public class FormIdValueImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://www.wikidata.org/entity/");

    private final FormIdValueImpl form1 = new FormIdValueImpl("L42-F1", "http://www.wikidata.org/entity/");

    private final FormIdValueImpl form2 = new FormIdValueImpl("L42-F1", "http://www.wikidata.org/entity/");

    private final FormIdValueImpl form3 = new FormIdValueImpl("L57-F2", "http://www.wikidata.org/entity/");

    private final FormIdValueImpl form4 = new FormIdValueImpl("L42-F1", "http://www.example.org/entity/");

    private final String JSON_FORM_ID_VALUE = "{\"type\":\"wikibase-entityid\",\"value\":{\"entity-type\":\"form\",\"id\":\"L42-F1\"}}";

    private final String JSON_FORM_ID_VALUE_WITHOUT_TYPE = "{\"type\":\"wikibase-entityid\",\"value\":{\"id\":\"L42-F1\"}}";

    @Test
    public void iriIsCorrect_1() {
        assertEquals(form1.getIri(), "http://www.wikidata.org/entity/L42-F1");
    }

    @Test
    public void iriIsCorrect_2() {
        assertEquals(form4.getIri(), "http://www.example.org/entity/L42-F1");
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(form1, form1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(form1, form2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        assertNotEquals(form1, form3);
    }

    @Test
    public void equalityBasedOnContent_4() {
        assertNotEquals(form1, form4);
    }

    @Test
    public void equalityBasedOnContent_5() {
        assertNotEquals(form1, null);
    }

    @Test
    public void equalityBasedOnContent_6() {
        assertNotEquals(form1, this);
    }
}
