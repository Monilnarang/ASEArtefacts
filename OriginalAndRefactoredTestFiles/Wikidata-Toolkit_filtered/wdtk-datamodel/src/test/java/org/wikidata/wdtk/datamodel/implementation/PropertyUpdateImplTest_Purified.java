package org.wikidata.wdtk.datamodel.implementation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.producesJson;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.toJson;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.PropertyUpdateBuilder;
import org.wikidata.wdtk.datamodel.helpers.StatementUpdateBuilder;
import org.wikidata.wdtk.datamodel.interfaces.AliasUpdate;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyUpdate;
import org.wikidata.wdtk.datamodel.interfaces.StatementUpdate;
import org.wikidata.wdtk.datamodel.interfaces.TermUpdate;

public class PropertyUpdateImplTest_Purified {

    private static final PropertyIdValue P1 = Datamodel.makeWikidataPropertyIdValue("P1");

    private static final StatementUpdate STATEMENTS = StatementUpdateBuilder.create().remove("ID123").build();

    private static final TermUpdate LABELS = LabeledDocumentUpdateImplTest.LABELS;

    private static final TermUpdate DESCRIPTIONS = TermedDocumentUpdateImplTest.DESCRIPTIONS;

    private static final AliasUpdate ALIAS = TermedDocumentUpdateImplTest.ALIAS;

    private static final Map<String, AliasUpdate> ALIASES = TermedDocumentUpdateImplTest.ALIASES;

    @Test
    public void testJson_1() {
        assertThat(new PropertyUpdateImpl(P1, 123, TermUpdate.EMPTY, TermUpdate.EMPTY, Collections.emptyMap(), StatementUpdate.EMPTY), producesJson("{}"));
    }

    @Test
    public void testJson_2() {
        assertThat(PropertyUpdateBuilder.forEntityId(P1).updateLabels(LABELS).build(), producesJson("{'labels':" + toJson(LABELS) + "}"));
    }

    @Test
    public void testJson_3() {
        assertThat(PropertyUpdateBuilder.forEntityId(P1).updateDescriptions(DESCRIPTIONS).build(), producesJson("{'descriptions':" + toJson(LABELS) + "}"));
    }

    @Test
    public void testJson_4() {
        assertThat(PropertyUpdateBuilder.forEntityId(P1).updateAliases("en", ALIAS).build(), producesJson("{'aliases':{'en':" + toJson(ALIAS) + "}}"));
    }

    @Test
    public void testJson_5() {
        assertThat(PropertyUpdateBuilder.forEntityId(P1).updateStatements(STATEMENTS).build(), producesJson("{'claims':" + toJson(STATEMENTS) + "}"));
    }
}
