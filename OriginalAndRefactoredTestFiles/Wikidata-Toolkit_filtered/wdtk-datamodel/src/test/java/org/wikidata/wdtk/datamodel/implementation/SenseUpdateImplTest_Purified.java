package org.wikidata.wdtk.datamodel.implementation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.producesJson;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.toJson;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.SenseUpdateBuilder;
import org.wikidata.wdtk.datamodel.helpers.TermUpdateBuilder;
import org.wikidata.wdtk.datamodel.interfaces.SenseIdValue;
import org.wikidata.wdtk.datamodel.interfaces.SenseUpdate;
import org.wikidata.wdtk.datamodel.interfaces.StatementUpdate;
import org.wikidata.wdtk.datamodel.interfaces.TermUpdate;

public class SenseUpdateImplTest_Purified {

    private static final SenseIdValue S1 = Datamodel.makeWikidataSenseIdValue("L1-S1");

    private static final StatementUpdate STATEMENTS = StatementDocumentUpdateImplTest.STATEMENTS;

    private static final TermUpdate GLOSSES = TermUpdateBuilder.create().remove("en").build();

    @Test
    public void testEmpty_1() {
        assertFalse(new SenseUpdateImpl(S1, 0, TermUpdate.EMPTY, STATEMENTS).isEmpty());
    }

    @Test
    public void testEmpty_2() {
        assertFalse(new SenseUpdateImpl(S1, 0, GLOSSES, StatementUpdate.EMPTY).isEmpty());
    }

    @Test
    public void testEmpty_3() {
        assertTrue(new SenseUpdateImpl(S1, 0, TermUpdate.EMPTY, StatementUpdate.EMPTY).isEmpty());
    }

    @Test
    public void testJson_1() {
        assertThat(new SenseUpdateImpl(S1, 123, TermUpdate.EMPTY, StatementUpdate.EMPTY), producesJson("{}"));
    }

    @Test
    public void testJson_2() {
        assertThat(SenseUpdateBuilder.forEntityId(S1).updateGlosses(GLOSSES).build(), producesJson("{'glosses':" + toJson(GLOSSES) + "}"));
    }

    @Test
    public void testJson_3() {
        assertThat(SenseUpdateBuilder.forEntityId(S1).updateStatements(STATEMENTS).build(), producesJson("{'claims':" + toJson(STATEMENTS) + "}"));
    }
}
