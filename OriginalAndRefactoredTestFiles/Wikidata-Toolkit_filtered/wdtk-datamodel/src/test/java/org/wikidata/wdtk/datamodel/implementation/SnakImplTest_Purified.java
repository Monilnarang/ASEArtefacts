package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.NoValueSnak;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.SomeValueSnak;
import org.wikidata.wdtk.datamodel.interfaces.ValueSnak;
import java.io.IOException;

public class SnakImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://example.com/entity/");

    private final PropertyIdValue p1 = new PropertyIdValueImpl("P42", "http://example.com/entity/");

    private final PropertyIdValue p2 = new PropertyIdValueImpl("P43", "http://example.com/entity/");

    private final ValueSnak vs1 = new ValueSnakImpl(p1, p1);

    private final ValueSnak vs2 = new ValueSnakImpl(p1, p1);

    private final ValueSnak vs3 = new ValueSnakImpl(p2, p1);

    private final ValueSnak vs4 = new ValueSnakImpl(p1, p2);

    private final ValueSnak vsmt1 = new ValueSnakImpl(p1, new TermImpl("en", "foo"));

    private final ValueSnak vsmt2 = new ValueSnakImpl(p1, new MonolingualTextValueImpl("foo", "en"));

    private final SomeValueSnak svs1 = new SomeValueSnakImpl(p1);

    private final SomeValueSnak svs2 = new SomeValueSnakImpl(p1);

    private final SomeValueSnak svs3 = new SomeValueSnakImpl(p2);

    private final NoValueSnak nvs1 = new NoValueSnakImpl(p1);

    private final NoValueSnak nvs2 = new NoValueSnakImpl(p1);

    private final NoValueSnak nvs3 = new NoValueSnakImpl(p2);

    private final String JSON_NOVALUE_SNAK = "{\"snaktype\":\"novalue\",\"property\":\"P42\"}";

    private final String JSON_SOMEVALUE_SNAK = "{\"snaktype\":\"somevalue\",\"property\":\"P42\"}";

    private final String JSON_VALUE_SNAK = "{\"snaktype\":\"value\",\"property\":\"P42\",\"datatype\":\"wikibase-property\",\"datavalue\":{\"value\":{\"id\":\"P42\",\"numeric-id\":42,\"entity-type\":\"property\"},\"type\":\"wikibase-entityid\"}}";

    private final String JSON_MONOLINGUAL_TEXT_VALUE_SNAK = "{\"snaktype\":\"value\",\"property\":\"P42\",\"datatype\":\"monolingualtext\",\"datavalue\":{\"value\":{\"language\":\"en\",\"text\":\"foo\"},\"type\":\"monolingualtext\"}}";

    private final String JSON_SNAK_UNKNOWN_ID = "{\"snaktype\":\"value\",\"property\":\"P42\",\"datatype\":\"wikibase-funkyid\",\"datavalue\":{\"value\":{\"id\":\"FUNKY42\",\"entity-type\":\"funky\"},\"type\":\"wikibase-entityid\"}}";

    private final String JSON_SNAK_UNKNOWN_DATAVALUE = "{\"snaktype\":\"value\",\"property\":\"P42\",\"datatype\":\"groovy\",\"datavalue\":{\"foo\":\"bar\",\"type\":\"groovyvalue\"}}";

    @Test
    public void fieldsAreCorrect_1() {
        assertEquals(vs1.getPropertyId(), p1);
    }

    @Test
    public void fieldsAreCorrect_2() {
        assertEquals(vs1.getValue(), p1);
    }

    @Test
    public void snakHashBasedOnContent_1() {
        assertEquals(vs1.hashCode(), vs2.hashCode());
    }

    @Test
    public void snakHashBasedOnContent_2() {
        assertEquals(vsmt1.hashCode(), vsmt2.hashCode());
    }

    @Test
    public void snakHashBasedOnContent_3() {
        assertEquals(svs1.hashCode(), svs2.hashCode());
    }

    @Test
    public void snakHashBasedOnContent_4() {
        assertEquals(nvs1.hashCode(), nvs2.hashCode());
    }

    @Test
    public void snakEqualityBasedOnType_1() {
        assertNotEquals(svs1, nvs1);
    }

    @Test
    public void snakEqualityBasedOnType_2() {
        assertNotEquals(nvs1, svs1);
    }

    @Test
    public void snakEqualityBasedOnType_3() {
        assertNotEquals(vs1, svs1);
    }

    @Test
    public void valueSnakEqualityBasedOnContent_1() {
        assertEquals(vs1, vs1);
    }

    @Test
    public void valueSnakEqualityBasedOnContent_2() {
        assertEquals(vs1, vs2);
    }

    @Test
    public void valueSnakEqualityBasedOnContent_3() {
        assertNotEquals(vs1, vs3);
    }

    @Test
    public void valueSnakEqualityBasedOnContent_4() {
        assertNotEquals(vs1, vs4);
    }

    @Test
    public void valueSnakEqualityBasedOnContent_5() {
        assertNotEquals(vs1, null);
    }

    @Test
    public void someValueSnakEqualityBasedOnContent_1() {
        assertEquals(svs1, svs1);
    }

    @Test
    public void someValueSnakEqualityBasedOnContent_2() {
        assertEquals(svs1, svs2);
    }

    @Test
    public void someValueSnakEqualityBasedOnContent_3() {
        assertNotEquals(svs1, svs3);
    }

    @Test
    public void someValueSnakEqualityBasedOnContent_4() {
        assertNotEquals(svs1, null);
    }

    @Test
    public void someValueSnakEqualityBasedOnContent_5() {
        assertEquals(vsmt1, vsmt2);
    }

    @Test
    public void noValueSnakEqualityBasedOnContent_1() {
        assertEquals(nvs1, nvs1);
    }

    @Test
    public void noValueSnakEqualityBasedOnContent_2() {
        assertEquals(nvs1, nvs2);
    }

    @Test
    public void noValueSnakEqualityBasedOnContent_3() {
        assertNotEquals(nvs1, nvs3);
    }

    @Test
    public void noValueSnakEqualityBasedOnContent_4() {
        assertNotEquals(nvs1, null);
    }

    @Test
    public void testMonolingualTextValueSnakToJava_1() throws IOException {
        assertEquals(vsmt1, mapper.readValue(JSON_MONOLINGUAL_TEXT_VALUE_SNAK, SnakImpl.class));
    }

    @Test
    public void testMonolingualTextValueSnakToJava_2() throws IOException {
        assertEquals(vsmt2, mapper.readValue(JSON_MONOLINGUAL_TEXT_VALUE_SNAK, SnakImpl.class));
    }
}
