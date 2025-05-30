package org.wikidata.wdtk.datamodel.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.EntityRedirectDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EntityRedirectDocumentImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://example.com/entity/");

    private final ItemIdValue entityItemId = new ItemIdValueImpl("Q1", "http://example.com/entity/");

    private final ItemIdValue targetItemId = new ItemIdValueImpl("Q2", "http://example.com/entity/");

    private final EntityRedirectDocument itemRedirect = new EntityRedirectDocumentImpl(entityItemId, targetItemId, 0);

    private final EntityRedirectDocument itemRedirect2 = new EntityRedirectDocumentImpl(entityItemId, targetItemId, 0);

    private final EntityRedirectDocument lexemeRedirect = new EntityRedirectDocumentImpl(new LexemeIdValueImpl("L1", "http://example.com/entity/"), new LexemeIdValueImpl("L2", "http://example.com/entity/"), 0);

    private final String JSON_ITEM_REDIRECT = "{\"entity\":\"Q1\",\"redirect\":\"Q2\"}";

    @Test
    public void fieldsAreCorrect_1() {
        assertEquals(itemRedirect.getEntityId(), entityItemId);
    }

    @Test
    public void fieldsAreCorrect_2() {
        assertEquals(itemRedirect.getTargetId(), targetItemId);
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(itemRedirect, itemRedirect2);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertNotEquals(itemRedirect, lexemeRedirect);
    }

    @Test
    public void equalityBasedOnContent_3() {
        EntityRedirectDocumentImpl diffEntity = new EntityRedirectDocumentImpl(targetItemId, targetItemId, 0);
        assertNotEquals(itemRedirect, diffEntity);
    }

    @Test
    public void equalityBasedOnContent_4() {
        EntityRedirectDocumentImpl diffTarget = new EntityRedirectDocumentImpl(entityItemId, entityItemId, 0);
        assertNotEquals(itemRedirect, diffTarget);
    }

    @Test
    public void equalityBasedOnContent_5() {
        EntityRedirectDocumentImpl diffRevisionId = new EntityRedirectDocumentImpl(entityItemId, targetItemId, 1);
        assertNotEquals(itemRedirect, diffRevisionId);
    }

    @Test
    public void equalityBasedOnContent_6() {
        assertNotEquals(itemRedirect, null);
    }

    @Test
    public void equalityBasedOnContent_7() {
        assertNotEquals(itemRedirect, this);
    }
}
