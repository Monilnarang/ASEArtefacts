package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
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
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.SenseDocument;
import org.wikidata.wdtk.datamodel.interfaces.SenseIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementGroup;
import org.wikidata.wdtk.datamodel.interfaces.StatementRank;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SenseDocumentImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://example.com/entity/");

    private final SenseIdValue sid = new SenseIdValueImpl("L42-S1", "http://example.com/entity/");

    private final Statement s = new StatementImpl("MyId", StatementRank.NORMAL, new SomeValueSnakImpl(new PropertyIdValueImpl("P42", "http://example.com/entity/")), Collections.emptyList(), Collections.emptyList(), sid);

    private final List<StatementGroup> statementGroups = Collections.singletonList(new StatementGroupImpl(Collections.singletonList(s)));

    private final MonolingualTextValue rep = new TermImpl("en", "rep");

    private final List<MonolingualTextValue> repList = Collections.singletonList(rep);

    private final SenseDocument sd1 = new SenseDocumentImpl(sid, repList, statementGroups, 1234);

    private final SenseDocument sd2 = new SenseDocumentImpl(sid, repList, statementGroups, 1234);

    private final String JSON_SENSE = "{\"type\":\"sense\",\"id\":\"L42-S1\",\"glosses\":{\"en\":{\"language\":\"en\",\"value\":\"rep\"}},\"claims\":{\"P42\":[{\"rank\":\"normal\",\"id\":\"MyId\",\"mainsnak\":{\"property\":\"P42\",\"snaktype\":\"somevalue\"},\"type\":\"statement\"}]},\"lastrevid\":1234}";

    @Test
    public void fieldsAreCorrect_1() {
        assertEquals(sd1.getEntityId(), sid);
    }

    @Test
    public void fieldsAreCorrect_2() {
        assertEquals(sd1.getGlosses(), Collections.singletonMap(rep.getLanguageCode(), rep));
    }

    @Test
    public void fieldsAreCorrect_3() {
        assertEquals(sd1.getStatementGroups(), statementGroups);
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(sd1, sd1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(sd1, sd2);
    }

    @Test
    public void equalityBasedOnContent_3_testMerged_3() {
        SenseDocument irDiffGlosses = new SenseDocumentImpl(sid, Collections.singletonList(new MonolingualTextValueImpl("fr", "bar")), statementGroups, 1234);
        SenseDocument irDiffStatementGroups = new SenseDocumentImpl(sid, repList, Collections.emptyList(), 1234);
        PropertyDocument pr = new PropertyDocumentImpl(new PropertyIdValueImpl("P42", "foo"), repList, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), new DatatypeIdImpl(DatatypeIdValue.DT_STRING), 1234);
        SenseDocument irDiffSenseIdValue = new SenseDocumentImpl(new SenseIdValueImpl("L42-S2", "http://example.com/entity/"), repList, Collections.emptyList(), 1235);
        assertNotEquals(sd1, irDiffGlosses);
        assertNotEquals(sd1, irDiffStatementGroups);
        assertNotEquals(irDiffStatementGroups, irDiffSenseIdValue);
        assertNotEquals(sd1, pr);
    }

    @Test
    public void equalityBasedOnContent_5() {
        SenseDocument irDiffRevisions = new SenseDocumentImpl(sid, repList, statementGroups, 1235);
        assertNotEquals(sd1, irDiffRevisions);
    }

    @Test
    public void equalityBasedOnContent_8() {
        assertNotEquals(sd1, null);
    }

    @Test
    public void equalityBasedOnContent_9() {
        assertNotEquals(sd1, this);
    }

    @Test
    public void testWithEntityId_1() {
        assertEquals(SenseIdValue.NULL, sd1.withEntityId(SenseIdValue.NULL).getEntityId());
    }

    @Test
    public void testWithEntityId_2() {
        SenseIdValue id = Datamodel.makeWikidataSenseIdValue("L123-S45");
        assertEquals(id, sd1.withEntityId(id).getEntityId());
    }

    @Test
    public void testWithRevisionId_1() {
        assertEquals(1235L, sd1.withRevisionId(1235L).getRevisionId());
    }

    @Test
    public void testWithRevisionId_2() {
        assertEquals(sd1, sd1.withRevisionId(1325L).withRevisionId(sd1.getRevisionId()));
    }
}
