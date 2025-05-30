package org.wikidata.wdtk.datamodel.implementation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.producesJson;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.toJson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.FormUpdateBuilder;
import org.wikidata.wdtk.datamodel.helpers.TermUpdateBuilder;
import org.wikidata.wdtk.datamodel.interfaces.FormIdValue;
import org.wikidata.wdtk.datamodel.interfaces.FormUpdate;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.StatementUpdate;
import org.wikidata.wdtk.datamodel.interfaces.TermUpdate;

public class FormUpdateImplTest_Purified {

    private static final FormIdValue F1 = Datamodel.makeWikidataFormIdValue("L1-F1");

    private static final StatementUpdate STATEMENTS = StatementDocumentUpdateImplTest.STATEMENTS;

    private static final TermUpdate REPRESENTATIONS = TermUpdateBuilder.create().remove("en").build();

    private static final ItemIdValue FEATURE1 = Datamodel.makeWikidataItemIdValue("Q1");

    private static final ItemIdValue FEATURE2 = Datamodel.makeWikidataItemIdValue("Q2");

    private static final List<ItemIdValue> FEATURES = Arrays.asList(FEATURE1);

    @Test
    public void testEmpty_1() {
        assertFalse(new FormUpdateImpl(F1, 0, REPRESENTATIONS, null, StatementUpdate.EMPTY).isEmpty());
    }

    @Test
    public void testEmpty_2() {
        assertFalse(new FormUpdateImpl(F1, 0, TermUpdate.EMPTY, Collections.emptyList(), StatementUpdate.EMPTY).isEmpty());
    }

    @Test
    public void testEmpty_3() {
        assertFalse(new FormUpdateImpl(F1, 0, TermUpdate.EMPTY, FEATURES, StatementUpdate.EMPTY).isEmpty());
    }

    @Test
    public void testEmpty_4() {
        assertFalse(new FormUpdateImpl(F1, 0, TermUpdate.EMPTY, null, STATEMENTS).isEmpty());
    }

    @Test
    public void testEmpty_5() {
        assertTrue(new FormUpdateImpl(F1, 0, TermUpdate.EMPTY, null, StatementUpdate.EMPTY).isEmpty());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquality_1_testMerged_1() {
        FormUpdate update = new FormUpdateImpl(F1, 0, REPRESENTATIONS, FEATURES, STATEMENTS);
        assertFalse(update.equals(null));
        assertFalse(update.equals(this));
        assertTrue(update.equals(update));
        assertTrue(update.equals(new FormUpdateImpl(F1, 0, TermUpdateBuilder.create().remove("en").build(), Arrays.asList(Datamodel.makeWikidataItemIdValue("Q1")), STATEMENTS)));
        assertFalse(update.equals(new FormUpdateImpl(F1, 123, REPRESENTATIONS, FEATURES, StatementUpdate.EMPTY)));
        assertFalse(update.equals(new FormUpdateImpl(F1, 123, TermUpdate.EMPTY, FEATURES, STATEMENTS)));
        assertFalse(update.equals(new FormUpdateImpl(F1, 123, REPRESENTATIONS, null, STATEMENTS)));
        assertFalse(update.equals(new FormUpdateImpl(F1, 123, REPRESENTATIONS, Collections.emptyList(), STATEMENTS)));
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquality_9() {
        assertFalse(new FormUpdateImpl(F1, 123, REPRESENTATIONS, null, STATEMENTS).equals(new FormUpdateImpl(F1, 123, REPRESENTATIONS, Collections.emptyList(), STATEMENTS)));
    }

    @Test
    public void testJson_1() {
        assertThat(new FormUpdateImpl(F1, 123, TermUpdate.EMPTY, null, StatementUpdate.EMPTY), producesJson("{}"));
    }

    @Test
    public void testJson_2() {
        assertThat(FormUpdateBuilder.forEntityId(F1).updateRepresentations(REPRESENTATIONS).build(), producesJson("{'representations':" + toJson(REPRESENTATIONS) + "}"));
    }

    @Test
    public void testJson_3() {
        assertThat(FormUpdateBuilder.forEntityId(F1).setGrammaticalFeatures(FEATURES).build(), producesJson("{'grammaticalFeatures':['Q1']}"));
    }

    @Test
    public void testJson_4() {
        assertThat(FormUpdateBuilder.forEntityId(F1).setGrammaticalFeatures(Collections.emptyList()).build(), producesJson("{'grammaticalFeatures':[]}"));
    }

    @Test
    public void testJson_5() {
        assertThat(FormUpdateBuilder.forEntityId(F1).updateStatements(STATEMENTS).build(), producesJson("{'claims':" + toJson(STATEMENTS) + "}"));
    }
}
