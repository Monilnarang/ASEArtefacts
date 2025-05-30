package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MediaInfoIdValueImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper(Datamodel.SITE_WIKIMEDIA_COMMONS);

    private final MediaInfoIdValueImpl mediaInfo1 = new MediaInfoIdValueImpl("M42", "http://commons.wikimedia.org/entity/");

    private final MediaInfoIdValueImpl mediaInfo2 = new MediaInfoIdValueImpl("M42", "http://commons.wikimedia.org/entity/");

    private final MediaInfoIdValueImpl mediaInfo3 = new MediaInfoIdValueImpl("M57", "http://commons.wikimedia.org/entity/");

    private final MediaInfoIdValueImpl mediaInfo4 = new MediaInfoIdValueImpl("M42", "http://www.example.org/entity/");

    private final String JSON_MEDIA_INFO_ID_VALUE = "{\"type\":\"wikibase-entityid\",\"value\":{\"entity-type\":\"mediainfo\",\"numeric-id\":42,\"id\":\"M42\"}}";

    private final String JSON_MEDIA_INFO_ID_VALUE_WITHOUT_NUMERICAL_ID = "{\"type\":\"wikibase-entityid\",\"value\":{\"id\":\"M42\"}}";

    @Test
    public void iriIsCorrect_1() {
        assertEquals(mediaInfo1.getIri(), "http://commons.wikimedia.org/entity/M42");
    }

    @Test
    public void iriIsCorrect_2() {
        assertEquals(mediaInfo4.getIri(), "http://www.example.org/entity/M42");
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(mediaInfo1, mediaInfo1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(mediaInfo1, mediaInfo2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        assertNotEquals(mediaInfo1, mediaInfo3);
    }

    @Test
    public void equalityBasedOnContent_4() {
        assertNotEquals(mediaInfo1, mediaInfo4);
    }

    @Test
    public void equalityBasedOnContent_5() {
        assertNotEquals(mediaInfo1, null);
    }

    @Test
    public void equalityBasedOnContent_6() {
        assertNotEquals(mediaInfo1, this);
    }
}
