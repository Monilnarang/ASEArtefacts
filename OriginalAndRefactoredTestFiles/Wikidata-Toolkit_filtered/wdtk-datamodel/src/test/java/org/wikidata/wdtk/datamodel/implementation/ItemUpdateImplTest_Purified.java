package org.wikidata.wdtk.datamodel.implementation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.producesJson;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.toJson;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.ItemUpdateBuilder;
import org.wikidata.wdtk.datamodel.interfaces.AliasUpdate;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.ItemUpdate;
import org.wikidata.wdtk.datamodel.interfaces.SiteLink;
import org.wikidata.wdtk.datamodel.interfaces.StatementUpdate;
import org.wikidata.wdtk.datamodel.interfaces.TermUpdate;

public class ItemUpdateImplTest_Purified {

    private static final ItemIdValue Q1 = LabeledDocumentUpdateImplTest.JOHN;

    private static final StatementUpdate STATEMENTS = StatementDocumentUpdateImplTest.STATEMENTS;

    private static final TermUpdate LABELS = LabeledDocumentUpdateImplTest.LABELS;

    private static final TermUpdate DESCRIPTIONS = TermedDocumentUpdateImplTest.DESCRIPTIONS;

    private static final AliasUpdate ALIAS = TermedDocumentUpdateImplTest.ALIAS;

    private static final Map<String, AliasUpdate> ALIASES = TermedDocumentUpdateImplTest.ALIASES;

    private static final SiteLink SITELINK1 = Datamodel.makeSiteLink("Something", "enwiki");

    private static final List<SiteLink> SITELINKS = Arrays.asList(SITELINK1);

    private static final List<String> REMOVED_SITELINKS = Arrays.asList("skwiki");

    @Test
    public void testJson_1() {
        assertThat(new ItemUpdateImpl(Q1, 123, TermUpdate.EMPTY, TermUpdate.EMPTY, Collections.emptyMap(), StatementUpdate.EMPTY, Collections.emptyList(), Collections.emptyList()), producesJson("{}"));
    }

    @Test
    public void testJson_2() {
        assertThat(ItemUpdateBuilder.forEntityId(Q1).updateLabels(LABELS).build(), producesJson("{'labels':" + toJson(LABELS) + "}"));
    }

    @Test
    public void testJson_3() {
        assertThat(ItemUpdateBuilder.forEntityId(Q1).updateDescriptions(DESCRIPTIONS).build(), producesJson("{'descriptions':" + toJson(LABELS) + "}"));
    }

    @Test
    public void testJson_4() {
        assertThat(ItemUpdateBuilder.forEntityId(Q1).updateAliases("en", ALIAS).build(), producesJson("{'aliases':{'en':" + toJson(ALIAS) + "}}"));
    }

    @Test
    public void testJson_5() {
        assertThat(ItemUpdateBuilder.forEntityId(Q1).updateStatements(STATEMENTS).build(), producesJson("{'claims':" + toJson(STATEMENTS) + "}"));
    }

    @Test
    public void testJson_6() {
        assertThat(ItemUpdateBuilder.forEntityId(Q1).putSiteLink(SITELINK1).build(), producesJson("{'sitelinks':{'enwiki':" + toJson(SITELINK1) + "}}"));
    }

    @Test
    public void testJson_7() {
        assertThat(ItemUpdateBuilder.forEntityId(Q1).removeSiteLink("enwiki").build(), producesJson("{'sitelinks':{'enwiki':{'remove':'','site':'enwiki'}}}"));
    }
}
