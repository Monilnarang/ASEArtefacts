package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import java.io.IOException;

public class MonolingualTextValueImplTest_Purified {

    private final ObjectMapper mapper = new ObjectMapper();

    private final MonolingualTextValue mt1 = new MonolingualTextValueImpl("some string", "en");

    private final MonolingualTextValue mt2 = new MonolingualTextValueImpl("some string", "en");

    private final String JSON_MONOLINGUAL_TEXT_VALUE = "{\"value\":{\"language\":\"en\",\"text\":\"some string\"},\"type\":\"monolingualtext\"}";

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
        MonolingualTextValue mtDiffString = new MonolingualTextValueImpl("another string", "en");
        assertNotEquals(mt1, mtDiffString);
    }

    @Test
    public void equalityBasedOnContent_4() {
        MonolingualTextValue mtDiffLanguageCode = new MonolingualTextValueImpl("some string", "en-GB");
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
