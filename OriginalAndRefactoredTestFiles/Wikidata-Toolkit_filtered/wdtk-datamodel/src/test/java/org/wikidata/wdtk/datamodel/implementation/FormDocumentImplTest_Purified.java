package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.Claim;
import org.wikidata.wdtk.datamodel.interfaces.DatatypeIdValue;
import org.wikidata.wdtk.datamodel.interfaces.FormDocument;
import org.wikidata.wdtk.datamodel.interfaces.FormIdValue;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementGroup;
import org.wikidata.wdtk.datamodel.interfaces.StatementRank;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FormDocumentImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://example.com/entity/");

    private final FormIdValue fid = new FormIdValueImpl("L42-F1", "http://example.com/entity/");

    private final List<ItemIdValue> gramFeatures = Arrays.asList(new ItemIdValueImpl("Q2", "http://example.com/entity/"), new ItemIdValueImpl("Q1", "http://example.com/entity/"));

    private final Statement s = new StatementImpl("MyId", StatementRank.NORMAL, new SomeValueSnakImpl(new PropertyIdValueImpl("P42", "http://example.com/entity/")), Collections.emptyList(), Collections.emptyList(), fid);

    private final List<StatementGroup> statementGroups = Collections.singletonList(new StatementGroupImpl(Collections.singletonList(s)));

    private final MonolingualTextValue rep = new TermImpl("en", "rep");

    private final List<MonolingualTextValue> repList = Collections.singletonList(rep);

    private final FormDocument fd1 = new FormDocumentImpl(fid, repList, gramFeatures, statementGroups, 1234);

    private final FormDocument fd2 = new FormDocumentImpl(fid, repList, gramFeatures, statementGroups, 1234);

    private final String JSON_FORM = "{\"type\":\"form\",\"id\":\"L42-F1\",\"grammaticalFeatures\":[\"Q1\",\"Q2\"],\"representations\":{\"en\":{\"language\":\"en\",\"value\":\"rep\"}},\"claims\":{\"P42\":[{\"rank\":\"normal\",\"id\":\"MyId\",\"mainsnak\":{\"property\":\"P42\",\"snaktype\":\"somevalue\"},\"type\":\"statement\"}]},\"lastrevid\":1234}";

    @Test
    public void fieldsAreCorrect_1() {
        assertEquals(fd1.getEntityId(), fid);
    }

    @Test
    public void fieldsAreCorrect_2() {
        assertEquals(fd1.getRepresentations(), Collections.singletonMap(rep.getLanguageCode(), rep));
    }

    @Test
    public void fieldsAreCorrect_3() {
        assertEquals(fd1.getGrammaticalFeatures(), gramFeatures);
    }

    @Test
    public void fieldsAreCorrect_4() {
        assertEquals(fd1.getStatementGroups(), statementGroups);
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(fd1, fd1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(fd1, fd2);
    }

    @Test
    public void equalityBasedOnContent_3_testMerged_3() {
        FormDocument irDiffRepresentations = new FormDocumentImpl(fid, Collections.singletonList(new MonolingualTextValueImpl("fr", "bar")), gramFeatures, statementGroups, 1234);
        FormDocument irDiffGramFeatures = new FormDocumentImpl(fid, repList, Collections.emptyList(), statementGroups, 1234);
        FormDocument irDiffStatementGroups = new FormDocumentImpl(fid, repList, gramFeatures, Collections.emptyList(), 1234);
        PropertyDocument pr = new PropertyDocumentImpl(new PropertyIdValueImpl("P42", "foo"), repList, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), new DatatypeIdImpl(DatatypeIdValue.DT_STRING), 1234);
        FormDocument irDiffFormIdValue = new FormDocumentImpl(new FormIdValueImpl("L42-F2", "http://example.com/entity/"), repList, gramFeatures, Collections.emptyList(), 1235);
        assertNotEquals(fd1, irDiffRepresentations);
        assertNotEquals(fd1, irDiffGramFeatures);
        assertNotEquals(fd1, irDiffStatementGroups);
        assertNotEquals(irDiffStatementGroups, irDiffFormIdValue);
        assertNotEquals(fd1, pr);
    }

    @Test
    public void equalityBasedOnContent_6() {
        FormDocument irDiffRevisions = new FormDocumentImpl(fid, repList, gramFeatures, statementGroups, 1235);
        assertNotEquals(fd1, irDiffRevisions);
    }

    @Test
    public void equalityBasedOnContent_9() {
        assertNotEquals(fd1, null);
    }

    @Test
    public void equalityBasedOnContent_10() {
        assertNotEquals(fd1, this);
    }

    @Test
    public void testWithEntityId_1() {
        assertEquals(FormIdValue.NULL, fd1.withEntityId(FormIdValue.NULL).getEntityId());
    }

    @Test
    public void testWithEntityId_2() {
        FormIdValue id = Datamodel.makeWikidataFormIdValue("L123-F45");
        assertEquals(id, fd1.withEntityId(id).getEntityId());
    }

    @Test
    public void testWithRevisionId_1() {
        assertEquals(1235L, fd1.withRevisionId(1235L).getRevisionId());
    }

    @Test
    public void testWithRevisionId_2() {
        assertEquals(fd1, fd1.withRevisionId(1325L).withRevisionId(fd1.getRevisionId()));
    }
}
