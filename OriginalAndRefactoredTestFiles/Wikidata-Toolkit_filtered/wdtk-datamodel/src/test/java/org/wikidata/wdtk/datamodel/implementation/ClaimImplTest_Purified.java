package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.Claim;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Snak;
import org.wikidata.wdtk.datamodel.interfaces.SnakGroup;
import org.wikidata.wdtk.datamodel.interfaces.ValueSnak;

public class ClaimImplTest_Purified {

    private final EntityIdValue subject = new ItemIdValueImpl("Q42", "http://wikidata.org/entity/");

    private final ValueSnak mainSnak = new ValueSnakImpl(new PropertyIdValueImpl("P42", "http://wikidata.org/entity/"), subject);

    private final Claim c1 = new ClaimImpl(subject, mainSnak, Collections.emptyList());

    private final Claim c2 = new ClaimImpl(subject, mainSnak, Collections.emptyList());

    @Test
    public void gettersWorking_1() {
        assertEquals(c1.getSubject(), subject);
    }

    @Test
    public void gettersWorking_2() {
        assertEquals(c1.getMainSnak(), mainSnak);
    }

    @Test
    public void gettersWorking_3() {
        assertEquals(c1.getQualifiers(), Collections.emptyList());
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(c1, c1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(c1, c2);
    }

    @Test
    public void equalityBasedOnContent_3_testMerged_3() {
        EntityIdValue subject2 = new ItemIdValueImpl("Q43", "http://wikidata.org/entity/");
        PropertyIdValue property = new PropertyIdValueImpl("P43", "http://wikidata.org/entity/");
        ValueSnak mainSnak2 = new ValueSnakImpl(property, subject2);
        cDiffSubject = new ClaimImpl(subject2, mainSnak, Collections.emptyList());
        cDiffMainSnak = new ClaimImpl(subject, mainSnak2, Collections.emptyList());
        cDiffQualifiers = new ClaimImpl(subject, mainSnak, Collections.singletonList(new SnakGroupImpl(Collections.singletonList(mainSnak))));
        assertNotEquals(c1, cDiffSubject);
        assertNotEquals(c1, cDiffMainSnak);
        assertNotEquals(c1, cDiffQualifiers);
    }

    @Test
    public void equalityBasedOnContent_6() {
        assertNotEquals(c1, null);
    }

    @Test
    public void equalityBasedOnContent_7() {
        assertNotEquals(c1, this);
    }
}
