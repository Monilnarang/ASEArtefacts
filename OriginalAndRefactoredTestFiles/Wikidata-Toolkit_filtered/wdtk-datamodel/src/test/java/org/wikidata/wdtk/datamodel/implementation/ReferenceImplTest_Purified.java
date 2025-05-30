package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import java.util.Collections;
import java.util.Iterator;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Reference;
import org.wikidata.wdtk.datamodel.interfaces.Snak;
import org.wikidata.wdtk.datamodel.interfaces.SnakGroup;
import org.wikidata.wdtk.datamodel.interfaces.ValueSnak;

public class ReferenceImplTest_Purified {

    private final EntityIdValue subject = new ItemIdValueImpl("Q42", "http://wikidata.org/entity/");

    private final PropertyIdValue property = new PropertyIdValueImpl("P42", "http://wikidata.org/entity/");

    private final ValueSnak valueSnak = new ValueSnakImpl(property, subject);

    private final SnakGroup snakGroup = new SnakGroupImpl(Collections.singletonList(valueSnak));

    private final Reference r1 = new ReferenceImpl(Collections.singletonList(snakGroup));

    private final Reference r2 = new ReferenceImpl(Collections.singletonList(snakGroup));

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(r1, r1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(r1, r2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        Reference r3 = new ReferenceImpl(Collections.emptyList());
        assertNotEquals(r1, r3);
    }

    @Test
    public void equalityBasedOnContent_4() {
        assertNotEquals(r1, null);
    }

    @Test
    public void equalityBasedOnContent_5() {
        assertNotEquals(r1, this);
    }
}
