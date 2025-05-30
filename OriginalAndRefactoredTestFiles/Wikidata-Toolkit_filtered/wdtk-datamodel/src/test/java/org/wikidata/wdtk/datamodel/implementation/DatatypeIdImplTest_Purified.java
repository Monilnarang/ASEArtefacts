package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.DatatypeIdValue;

public class DatatypeIdImplTest_Purified {

    private final DatatypeIdImpl d1 = new DatatypeIdImpl(DatatypeIdValue.DT_ITEM);

    private final DatatypeIdImpl d2 = new DatatypeIdImpl("http://wikiba.se/ontology#WikibaseItem");

    private final DatatypeIdImpl d3 = new DatatypeIdImpl(DatatypeIdValue.DT_TIME);

    private final DatatypeIdImpl d4 = new DatatypeIdImpl("http://wikiba.se/ontology#SomeUnknownDatatype", "some-unknownDatatype");

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(d1, d1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(d1, d2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        assertNotEquals(d1, d3);
    }

    @Test
    public void equalityBasedOnContent_4() {
        assertNotEquals(d1, null);
    }

    @Test
    public void equalityBasedOnContent_5() {
        assertNotEquals(d1, new StringValueImpl("foo"));
    }

    @Test
    public void doNotChokeOnUnknownDatatypes_1() {
        assertEquals("some-unknownDatatype", d4.getJsonString());
    }

    @Test
    public void doNotChokeOnUnknownDatatypes_2() {
        assertEquals("http://wikiba.se/ontology#SomeUnknownDatatype", d4.getIri());
    }
}
