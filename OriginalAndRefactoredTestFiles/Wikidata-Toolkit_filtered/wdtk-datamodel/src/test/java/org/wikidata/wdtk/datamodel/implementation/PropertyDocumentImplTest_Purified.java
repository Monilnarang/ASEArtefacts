package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.*;

public class PropertyDocumentImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://example.com/entity/");

    private PropertyIdValue pid = new PropertyIdValueImpl("P2", "http://example.com/entity/");

    private final Statement s = new StatementImpl("MyId", StatementRank.NORMAL, new SomeValueSnakImpl(new PropertyIdValueImpl("P42", "http://example.com/entity/")), Collections.emptyList(), Collections.emptyList(), pid);

    private final List<StatementGroup> statementGroups = Collections.singletonList(new StatementGroupImpl(Collections.singletonList(s)));

    private final MonolingualTextValue label = new TermImpl("en", "label");

    private final List<MonolingualTextValue> labelList = Collections.singletonList(label);

    private final MonolingualTextValue desc = new TermImpl("fr", "des");

    private final List<MonolingualTextValue> descList = Collections.singletonList(desc);

    private final MonolingualTextValue alias = new TermImpl("de", "alias");

    private final List<MonolingualTextValue> aliasList = Collections.singletonList(alias);

    private DatatypeIdValue datatypeId = new DatatypeIdImpl(DatatypeIdValue.DT_ITEM);

    private final PropertyDocument pd1 = new PropertyDocumentImpl(pid, labelList, descList, aliasList, statementGroups, datatypeId, 1234);

    private final PropertyDocument pd2 = new PropertyDocumentImpl(pid, labelList, descList, aliasList, statementGroups, datatypeId, 1234);

    private final String JSON_PROPERTY = "{\"type\":\"property\",\"id\":\"P2\",\"labels\":{\"en\":{\"language\":\"en\",\"value\":\"label\"}},\"descriptions\":{\"fr\":{\"language\":\"fr\",\"value\":\"des\"}},\"aliases\":{\"de\":[{\"language\":\"de\",\"value\":\"alias\"}]},\"claims\":{\"P42\":[{\"rank\":\"normal\",\"id\":\"MyId\",\"mainsnak\":{\"property\":\"P42\",\"snaktype\":\"somevalue\"},\"type\":\"statement\"}]},\"datatype\":\"wikibase-item\",\"lastrevid\":1234}";

    private final String JSON_PROPERTY_WITH_UNKNOWN_DATATYPE = "{\"type\":\"property\",\"id\":\"P2\",\"labels\":{\"en\":{\"language\":\"en\",\"value\":\"label\"}},\"descriptions\":{},\"aliases\":{},\"claims\":{},\"datatype\":\"some-unknownDatatype\",\"lastrevid\":1234}";

    @Test
    public void fieldsAreCorrect_1() {
        assertEquals(pd1.getEntityId(), pid);
    }

    @Test
    public void fieldsAreCorrect_2() {
        assertEquals(pd1.getLabels(), Collections.singletonMap(label.getLanguageCode(), label));
    }

    @Test
    public void fieldsAreCorrect_3() {
        assertEquals(pd1.getDescriptions(), Collections.singletonMap(desc.getLanguageCode(), desc));
    }

    @Test
    public void fieldsAreCorrect_4() {
        assertEquals(pd1.getAliases(), Collections.singletonMap(alias.getLanguageCode(), Collections.singletonList(alias)));
    }

    @Test
    public void fieldsAreCorrect_5() {
        assertEquals(pd1.getStatementGroups(), statementGroups);
    }

    @Test
    public void fieldsAreCorrect_6() {
        assertEquals(pd1.getDatatype(), datatypeId);
    }

    @Test
    public void hasStatements_1() {
        assertTrue(pd1.hasStatement("P42"));
    }

    @Test
    public void hasStatements_2() {
        assertFalse(pd1.hasStatement("P43"));
    }

    @Test
    public void hasStatements_3() {
        assertTrue(pd1.hasStatement(new PropertyIdValueImpl("P42", "http://example.com/entity/")));
    }

    @Test
    public void hasStatements_4() {
        assertFalse(pd1.hasStatement(Datamodel.makePropertyIdValue("P43", "http://example.com/entity/")));
    }

    @Test
    public void findTerms_1() {
        assertEquals("label", pd1.findLabel("en"));
    }

    @Test
    public void findTerms_2() {
        assertNull(pd1.findLabel("ja"));
    }

    @Test
    public void findTerms_3() {
        assertEquals("des", pd1.findDescription("fr"));
    }

    @Test
    public void findTerms_4() {
        assertNull(pd1.findDescription("ja"));
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(pd1, pd1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(pd1, pd2);
    }

    @Test
    public void equalityBasedOnContent_3_testMerged_3() {
        PropertyDocument pdDiffLabel = new PropertyDocumentImpl(pid, Collections.emptyList(), descList, aliasList, statementGroups, datatypeId, 1234);
        PropertyDocument pdDiffDesc = new PropertyDocumentImpl(pid, labelList, Collections.emptyList(), aliasList, statementGroups, datatypeId, 1234);
        PropertyDocument pdDiffAlias = new PropertyDocumentImpl(pid, labelList, descList, Collections.emptyList(), statementGroups, datatypeId, 1234);
        PropertyDocument pdDiffStatementGroups = new PropertyDocumentImpl(pid, labelList, descList, aliasList, Collections.emptyList(), datatypeId, 1234);
        ItemDocument id = new ItemDocumentImpl(new ItemIdValueImpl("Q42", "foo"), labelList, descList, aliasList, Collections.emptyList(), Collections.emptyList(), 1234);
        assertNotEquals(pd1, pdDiffLabel);
        assertNotEquals(pd1, pdDiffDesc);
        assertNotEquals(pd1, pdDiffAlias);
        assertNotEquals(pd1, pdDiffStatementGroups);
        assertNotEquals(pd1, id);
    }

    @Test
    public void equalityBasedOnContent_7() {
        PropertyDocument pdDiffDatatype = new PropertyDocumentImpl(pid, labelList, descList, aliasList, statementGroups, new DatatypeIdImpl(DatatypeIdValue.DT_STRING), 1234);
        assertNotEquals(pd1, pdDiffDatatype);
    }

    @Test
    public void equalityBasedOnContent_8() {
        PropertyDocument pdDiffRevisions = new PropertyDocumentImpl(pid, labelList, descList, aliasList, statementGroups, datatypeId, 1235);
        assertNotEquals(pd1, pdDiffRevisions);
    }

    @Test
    public void equalityBasedOnContent_10() {
        assertNotEquals(pd1, null);
    }

    @Test
    public void equalityBasedOnContent_11() {
        assertNotEquals(pd1, this);
    }

    @Test
    public void testWithEntityId_1() {
        assertEquals(PropertyIdValue.NULL, pd1.withEntityId(PropertyIdValue.NULL).getEntityId());
    }

    @Test
    public void testWithEntityId_2() {
        PropertyIdValue id = Datamodel.makeWikidataPropertyIdValue("P123");
        assertEquals(id, pd1.withEntityId(id).getEntityId());
    }

    @Test
    public void testWithRevisionId_1() {
        assertEquals(1235L, pd1.withRevisionId(1235L).getRevisionId());
    }

    @Test
    public void testWithRevisionId_2() {
        assertEquals(pd1, pd1.withRevisionId(1325L).withRevisionId(pd1.getRevisionId()));
    }
}
