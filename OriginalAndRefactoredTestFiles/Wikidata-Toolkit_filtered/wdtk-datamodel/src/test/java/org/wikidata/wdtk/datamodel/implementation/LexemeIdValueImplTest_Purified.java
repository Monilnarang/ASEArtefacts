package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import java.io.IOException;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LexemeIdValueImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper(Datamodel.SITE_WIKIDATA);

    private final LexemeIdValueImpl lexeme1 = new LexemeIdValueImpl("L42", "http://www.wikidata.org/entity/");

    private final LexemeIdValueImpl lexeme2 = new LexemeIdValueImpl("L42", "http://www.wikidata.org/entity/");

    private final LexemeIdValueImpl lexeme3 = new LexemeIdValueImpl("L57", "http://www.wikidata.org/entity/");

    private final LexemeIdValueImpl lexeme4 = new LexemeIdValueImpl("L42", "http://www.example.org/entity/");

    private final String JSON_LEXEME_ID_VALUE = "{\"type\":\"wikibase-entityid\",\"value\":{\"entity-type\":\"lexeme\",\"numeric-id\":42,\"id\":\"L42\"}}";

    private final String JSON_LEXEME_ID_VALUE_WITHOUT_NUMERICAL_ID = "{\"type\":\"wikibase-entityid\",\"value\":{\"id\":\"L42\"}}";

    @Test
    public void iriIsCorrect_1() {
        assertEquals(lexeme1.getIri(), "http://www.wikidata.org/entity/L42");
    }

    @Test
    public void iriIsCorrect_2() {
        assertEquals(lexeme4.getIri(), "http://www.example.org/entity/L42");
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(lexeme1, lexeme1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(lexeme1, lexeme2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        assertNotEquals(lexeme1, lexeme3);
    }

    @Test
    public void equalityBasedOnContent_4() {
        assertNotEquals(lexeme1, lexeme4);
    }

    @Test
    public void equalityBasedOnContent_5() {
        assertNotEquals(lexeme1, null);
    }

    @Test
    public void equalityBasedOnContent_6() {
        assertNotEquals(lexeme1, this);
    }
}
