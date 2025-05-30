package org.wikidata.wdtk.datamodel.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TermImplTest_Purified {

    private final ObjectMapper mapper = new ObjectMapper();

    private final MonolingualTextValue mt1 = new TermImpl("en", "some string");

    private final MonolingualTextValue mt2 = new TermImpl("en", "some string");

    private final String JSON_TERM = "{\"language\":\"en\",\"value\":\"some string\"}";

    @Test
    public void dataIsCorrect_1() {
        assertEquals(mt1.getText(), "some string");
    }

    @Test
    public void dataIsCorrect_2() {
        assertEquals(mt1.getLanguageCode(), "en");
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(mt1, mt1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(mt1, mt2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        MonolingualTextValue mtDiffString = new TermImpl("another string", "en");
        assertNotEquals(mt1, mtDiffString);
    }

    @Test
    public void equalityBasedOnContent_4() {
        MonolingualTextValue mtDiffLanguageCode = new TermImpl("some string", "en-GB");
        assertNotEquals(mt1, mtDiffLanguageCode);
    }

    @Test
    public void equalityBasedOnContent_5() {
        assertNotEquals(mt1, null);
    }

    @Test
    public void equalityBasedOnContent_6() {
        assertNotEquals(mt1, this);
    }
}
