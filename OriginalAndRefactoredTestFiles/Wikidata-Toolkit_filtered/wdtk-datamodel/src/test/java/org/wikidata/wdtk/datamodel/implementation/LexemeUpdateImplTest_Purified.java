package org.wikidata.wdtk.datamodel.implementation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
import java.util.List;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.FormUpdateBuilder;
import org.wikidata.wdtk.datamodel.helpers.LexemeUpdateBuilder;
import org.wikidata.wdtk.datamodel.helpers.SenseUpdateBuilder;
import org.wikidata.wdtk.datamodel.helpers.TermUpdateBuilder;
import org.wikidata.wdtk.datamodel.interfaces.FormDocument;
import org.wikidata.wdtk.datamodel.interfaces.FormIdValue;
import org.wikidata.wdtk.datamodel.interfaces.FormUpdate;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.LexemeIdValue;
import org.wikidata.wdtk.datamodel.interfaces.LexemeUpdate;
import org.wikidata.wdtk.datamodel.interfaces.SenseDocument;
import org.wikidata.wdtk.datamodel.interfaces.SenseIdValue;
import org.wikidata.wdtk.datamodel.interfaces.SenseUpdate;
import org.wikidata.wdtk.datamodel.interfaces.StatementUpdate;
import org.wikidata.wdtk.datamodel.interfaces.TermUpdate;

public class LexemeUpdateImplTest_Purified {

    private static final LexemeIdValue L1 = Datamodel.makeWikidataLexemeIdValue("L1");

    private static final StatementUpdate STATEMENTS = StatementDocumentUpdateImplTest.STATEMENTS;

    private static final TermUpdate LEMMAS = TermUpdateBuilder.create().remove("en").build();

    private static final ItemIdValue Q1 = Datamodel.makeWikidataItemIdValue("Q1");

    private static final ItemIdValue Q2 = Datamodel.makeWikidataItemIdValue("Q2");

    private static final SenseIdValue S1 = Datamodel.makeWikidataSenseIdValue("L1-S1");

    private static final SenseIdValue S2 = Datamodel.makeWikidataSenseIdValue("L1-S2");

    private static final SenseIdValue S3 = Datamodel.makeWikidataSenseIdValue("L1-S3");

    private static final SenseDocument ADDED_SENSE = Datamodel.makeSenseDocument(SenseIdValue.NULL, Collections.emptyList(), Collections.emptyList());

    private static final List<SenseDocument> ADDED_SENSES = Arrays.asList(ADDED_SENSE);

    private static final SenseUpdate UPDATED_SENSE = SenseUpdateBuilder.forEntityId(S1).updateStatements(STATEMENTS).build();

    private static final SenseUpdate UPDATED_SENSE_REVISION = SenseUpdateBuilder.forBaseRevisionId(S1, 123).append(UPDATED_SENSE).build();

    private static final List<SenseUpdate> UPDATED_SENSES = Arrays.asList(UPDATED_SENSE);

    private static final List<SenseUpdate> UPDATED_SENSE_REVISIONS = Arrays.asList(UPDATED_SENSE_REVISION);

    private static final List<SenseIdValue> REMOVED_SENSES = Arrays.asList(S2);

    private static final FormIdValue F1 = Datamodel.makeWikidataFormIdValue("L1-F1");

    private static final FormIdValue F2 = Datamodel.makeWikidataFormIdValue("L1-F2");

    private static final FormIdValue F3 = Datamodel.makeWikidataFormIdValue("L1-F3");

    private static final FormDocument ADDED_FORM = Datamodel.makeFormDocument(FormIdValue.NULL, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

    private static final List<FormDocument> ADDED_FORMS = Arrays.asList(ADDED_FORM);

    private static final FormUpdate UPDATED_FORM = FormUpdateBuilder.forEntityId(F1).updateStatements(STATEMENTS).build();

    private static final FormUpdate UPDATED_FORM_REVISION = FormUpdateBuilder.forBaseRevisionId(F1, 123).append(UPDATED_FORM).build();

    private static final List<FormUpdate> UPDATED_FORMS = Arrays.asList(UPDATED_FORM);

    private static final List<FormUpdate> UPDATED_FORM_REVISIONS = Arrays.asList(UPDATED_FORM_REVISION);

    private static final List<FormIdValue> REMOVED_FORMS = Arrays.asList(F2);

    @Test
    public void testJson_1() {
        assertThat(new LexemeUpdateImpl(L1, 123, null, null, TermUpdate.EMPTY, StatementUpdate.EMPTY, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()), producesJson("{}"));
    }

    @Test
    public void testJson_2() {
        assertThat(LexemeUpdateBuilder.forEntityId(L1).setLanguage(Q1).build(), producesJson("{'language':'Q1'}"));
    }

    @Test
    public void testJson_3() {
        assertThat(LexemeUpdateBuilder.forEntityId(L1).setLexicalCategory(Q2).build(), producesJson("{'lexicalCategory':'Q2'}"));
    }

    @Test
    public void testJson_4() {
        assertThat(LexemeUpdateBuilder.forEntityId(L1).updateLemmas(LEMMAS).build(), producesJson("{'lemmas':" + toJson(LEMMAS) + "}"));
    }

    @Test
    public void testJson_5() {
        assertThat(LexemeUpdateBuilder.forEntityId(L1).updateStatements(STATEMENTS).build(), producesJson("{'claims':" + toJson(STATEMENTS) + "}"));
    }

    @Test
    public void testJson_6() {
        assertThat(LexemeUpdateBuilder.forEntityId(L1).addSense(ADDED_SENSE).build(), producesJson("{'senses':[{'add':''," + toJson(ADDED_SENSE).substring(1) + "]}"));
    }

    @Test
    public void testJson_7() {
        assertThat(LexemeUpdateBuilder.forEntityId(L1).updateSense(UPDATED_SENSE).build(), producesJson("{'senses':[" + toJson(UPDATED_SENSE) + "]}"));
    }

    @Test
    public void testJson_8() {
        assertThat(LexemeUpdateBuilder.forEntityId(L1).removeSense(S2).build(), producesJson("{'senses':[{'id':'L1-S2','remove':''}]}"));
    }

    @Test
    public void testJson_9() {
        assertThat(LexemeUpdateBuilder.forEntityId(L1).addForm(ADDED_FORM).build(), producesJson("{'forms':[{'add':''," + toJson(ADDED_FORM).substring(1) + "]}"));
    }

    @Test
    public void testJson_10() {
        assertThat(LexemeUpdateBuilder.forEntityId(L1).updateForm(UPDATED_FORM).build(), producesJson("{'forms':[" + toJson(UPDATED_FORM) + "]}"));
    }

    @Test
    public void testJson_11() {
        assertThat(LexemeUpdateBuilder.forEntityId(L1).removeForm(F2).build(), producesJson("{'forms':[{'id':'L1-F2','remove':''}]}"));
    }
}
