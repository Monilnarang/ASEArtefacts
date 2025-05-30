package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.Claim;
import org.wikidata.wdtk.datamodel.interfaces.DatatypeIdValue;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.SiteLink;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementGroup;
import org.wikidata.wdtk.datamodel.interfaces.StatementRank;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemDocumentImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://example.com/entity/");

    private final ItemIdValue iid = new ItemIdValueImpl("Q42", "http://example.com/entity/");

    private final Statement s = new StatementImpl("MyId", StatementRank.NORMAL, new SomeValueSnakImpl(new PropertyIdValueImpl("P42", "http://example.com/entity/")), Collections.emptyList(), Collections.emptyList(), iid);

    private final List<StatementGroup> statementGroups = Collections.singletonList(new StatementGroupImpl(Collections.singletonList(s)));

    private final MonolingualTextValue label = new TermImpl("en", "label");

    private final List<MonolingualTextValue> labelList = Collections.singletonList(label);

    private final MonolingualTextValue desc = new MonolingualTextValueImpl("des", "fr");

    private final List<MonolingualTextValue> descList = Collections.singletonList(desc);

    private final MonolingualTextValue alias = new MonolingualTextValueImpl("alias", "de");

    private final List<MonolingualTextValue> aliasList = Collections.singletonList(alias);

    private final List<SiteLink> sitelinks = Collections.singletonList(new SiteLinkImpl("Douglas Adams", "enwiki", Collections.emptyList()));

    private final ItemDocument ir1 = new ItemDocumentImpl(iid, labelList, descList, aliasList, statementGroups, sitelinks, 1234);

    private final ItemDocument ir2 = new ItemDocumentImpl(iid, labelList, descList, aliasList, statementGroups, sitelinks, 1234);

    private final String JSON_ITEM_LABEL = "{\"type\":\"item\",\"id\":\"Q42\",\"labels\":{\"en\":{\"language\":\"en\",\"value\":\"label\"}},\"descriptions\":{},\"aliases\":{},\"claims\":{},\"sitelinks\":{}}";

    private final String JSON_ITEM_DESCRIPTION = "{\"type\":\"item\",\"id\":\"Q42\",\"labels\":{},\"descriptions\":{\"fr\":{\"language\":\"fr\",\"value\":\"des\"}},\"aliases\":{},\"claims\":{},\"sitelinks\":{}}";

    private final String JSON_ITEM_ALIASES = "{\"type\":\"item\",\"id\":\"Q42\",\"labels\":{},\"descriptions\":{},\"aliases\":{\"de\":[{\"language\":\"de\",\"value\":\"alias\"}]},\"claims\":{},\"sitelinks\":{}}";

    private final String JSON_ITEM_STATEMENTS = "{\"type\":\"item\",\"id\":\"Q42\",\"labels\":{},\"descriptions\":{},\"aliases\":{},\"claims\":{\"P42\":[{\"rank\":\"normal\",\"id\":\"MyId\",\"mainsnak\":{\"property\":\"P42\",\"snaktype\":\"somevalue\"},\"type\":\"statement\"}]},\"sitelinks\":{}}";

    private final String JSON_ITEM_SITELINKS = "{\"type\":\"item\",\"id\":\"Q42\",\"labels\":{},\"descriptions\":{},\"aliases\":{},\"claims\":{},\"sitelinks\":{\"enwiki\":{\"title\":\"Douglas Adams\",\"site\":\"enwiki\",\"badges\":[]}}}";

    private final String JSON_ITEM_EMPTY_ARRAYS = "{\"type\":\"item\",\"id\":\"Q42\",\"labels\":[],\"descriptions\":[],\"aliases\":[],\"claims\":[],\"sitelinks\":[]}";

    @Test
    public void fieldsAreCorrect_1() {
        assertEquals(ir1.getEntityId(), iid);
    }

    @Test
    public void fieldsAreCorrect_2() {
        assertEquals(ir1.getLabels(), Collections.singletonMap(label.getLanguageCode(), label));
    }

    @Test
    public void fieldsAreCorrect_3() {
        assertEquals(ir1.getDescriptions(), Collections.singletonMap(desc.getLanguageCode(), desc));
    }

    @Test
    public void fieldsAreCorrect_4() {
        assertEquals(ir1.getAliases(), Collections.singletonMap(alias.getLanguageCode(), Collections.singletonList(alias)));
    }

    @Test
    public void fieldsAreCorrect_5() {
        assertEquals(ir1.getStatementGroups(), statementGroups);
    }

    @Test
    public void fieldsAreCorrect_6() {
        assertEquals(new ArrayList<>(ir1.getSiteLinks().values()), sitelinks);
    }

    @Test
    public void findTerms_1() {
        assertEquals("label", ir1.findLabel("en"));
    }

    @Test
    public void findTerms_2() {
        assertNull(ir1.findLabel("ja"));
    }

    @Test
    public void findTerms_3() {
        assertEquals("des", ir1.findDescription("fr"));
    }

    @Test
    public void findTerms_4() {
        assertNull(ir1.findDescription("ja"));
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(ir1, ir1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(ir1, ir2);
    }

    @Test
    public void equalityBasedOnContent_3_testMerged_3() {
        ItemDocument irDiffLabel = new ItemDocumentImpl(iid, Collections.emptyList(), descList, aliasList, statementGroups, sitelinks, 1234);
        ItemDocument irDiffDesc = new ItemDocumentImpl(iid, labelList, Collections.emptyList(), aliasList, statementGroups, sitelinks, 1234);
        ItemDocument irDiffAlias = new ItemDocumentImpl(iid, labelList, descList, Collections.emptyList(), statementGroups, sitelinks, 1234);
        ItemDocument irDiffStatementGroups = new ItemDocumentImpl(iid, labelList, descList, aliasList, Collections.emptyList(), sitelinks, 1234);
        ItemDocument irDiffSiteLinks = new ItemDocumentImpl(iid, labelList, descList, aliasList, statementGroups, Collections.emptyList(), 1234);
        PropertyDocument pr = new PropertyDocumentImpl(new PropertyIdValueImpl("P42", "foo"), labelList, descList, aliasList, Collections.emptyList(), new DatatypeIdImpl(DatatypeIdValue.DT_STRING), 1234);
        ItemDocument irDiffItemIdValue = new ItemDocumentImpl(new ItemIdValueImpl("Q23", "http://example.org/"), labelList, descList, aliasList, Collections.emptyList(), sitelinks, 1234);
        assertNotEquals(ir1, irDiffLabel);
        assertNotEquals(ir1, irDiffDesc);
        assertNotEquals(ir1, irDiffAlias);
        assertNotEquals(ir1, irDiffStatementGroups);
        assertNotEquals(ir1, irDiffSiteLinks);
        assertNotEquals(irDiffStatementGroups, irDiffItemIdValue);
        assertNotEquals(ir1, pr);
    }

    @Test
    public void equalityBasedOnContent_8() {
        ItemDocument irDiffRevisions = new ItemDocumentImpl(iid, labelList, descList, aliasList, statementGroups, sitelinks, 1235);
        assertNotEquals(ir1, irDiffRevisions);
    }

    @Test
    public void equalityBasedOnContent_11() {
        assertNotEquals(ir1, null);
    }

    @Test
    public void equalityBasedOnContent_12() {
        assertNotEquals(ir1, this);
    }

    @Test
    public void testWithEntityId_1() {
        assertEquals(ItemIdValue.NULL, ir1.withEntityId(ItemIdValue.NULL).getEntityId());
    }

    @Test
    public void testWithEntityId_2() {
        ItemIdValue id = Datamodel.makeWikidataItemIdValue("Q123");
        assertEquals(id, ir1.withEntityId(id).getEntityId());
    }

    @Test
    public void testWithRevisionId_1() {
        assertEquals(1235L, ir1.withRevisionId(1235L).getRevisionId());
    }

    @Test
    public void testWithRevisionId_2() {
        assertEquals(ir1, ir1.withRevisionId(1325L).withRevisionId(ir1.getRevisionId()));
    }
}
