package org.wikidata.wdtk.datamodel.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.Claim;
import org.wikidata.wdtk.datamodel.interfaces.DatatypeIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MediaInfoDocument;
import org.wikidata.wdtk.datamodel.interfaces.MediaInfoIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementGroup;
import org.wikidata.wdtk.datamodel.interfaces.StatementRank;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.*;

public class MediaInfoDocumentImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://example.com/entity/");

    private final MediaInfoIdValue mid = new MediaInfoIdValueImpl("M42", "http://example.com/entity/");

    private final Statement s = new StatementImpl("MyId", StatementRank.NORMAL, new SomeValueSnakImpl(new PropertyIdValueImpl("P42", "http://example.com/entity/")), Collections.emptyList(), Collections.emptyList(), mid);

    private final List<StatementGroup> statementGroups = Collections.singletonList(new StatementGroupImpl(Collections.singletonList(s)));

    private final MonolingualTextValue label = new TermImpl("en", "label");

    private final List<MonolingualTextValue> labelList = Collections.singletonList(label);

    private final MediaInfoDocument mi1 = new MediaInfoDocumentImpl(mid, labelList, statementGroups, 1234);

    private final MediaInfoDocument mi2 = new MediaInfoDocumentImpl(mid, labelList, statementGroups, 1234);

    private final String JSON_MEDIA_INFO_LABEL = "{\"type\":\"mediainfo\",\"id\":\"M42\",\"labels\":{\"en\":{\"language\":\"en\",\"value\":\"label\"}},\"claims\":{}}";

    private final String JSON_MEDIA_INFO_DESCRIPTION = "{\"type\":\"mediainfo\",\"id\":\"M42\",\"labels\":{},\"descriptions\":{},\"statements\":{}}";

    private final String JSON_MEDIA_INFO_STATEMENTS = "{\"type\":\"mediainfo\",\"id\":\"M42\",\"labels\":{},\"statements\":{\"P42\":[{\"rank\":\"normal\",\"id\":\"MyId\",\"mainsnak\":{\"property\":\"P42\",\"snaktype\":\"somevalue\"},\"type\":\"statement\"}]}}";

    private final String JSON_MEDIA_INFO_CLAIMS = "{\"type\":\"mediainfo\",\"id\":\"M42\",\"labels\":{},\"claims\":{\"P42\":[{\"rank\":\"normal\",\"id\":\"MyId\",\"mainsnak\":{\"property\":\"P42\",\"snaktype\":\"somevalue\"},\"type\":\"statement\"}]}}";

    private final String JSON_MEDIA_INFO_EMPTY_ARRAYS = "{\"type\":\"mediainfo\",\"id\":\"M42\",\"labels\":[],\"descriptions\":[],\"statements\":[],\"sitelinks\":[]}";

    @Test
    public void fieldsAreCorrect_1() {
        assertEquals(mi1.getEntityId(), mid);
    }

    @Test
    public void fieldsAreCorrect_2() {
        assertEquals(mi1.getLabels(), Collections.singletonMap(label.getLanguageCode(), label));
    }

    @Test
    public void fieldsAreCorrect_3() {
        assertEquals(mi1.getStatementGroups(), statementGroups);
    }

    @Test
    public void findLabels_1() {
        assertEquals("label", mi1.findLabel("en"));
    }

    @Test
    public void findLabels_2() {
        assertNull(mi1.findLabel("ja"));
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(mi1, mi1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(mi1, mi2);
    }

    @Test
    public void equalityBasedOnContent_3_testMerged_3() {
        MediaInfoDocument irDiffLabel = new MediaInfoDocumentImpl(mid, Collections.emptyList(), statementGroups, 1234);
        MediaInfoDocument irDiffStatementGroups = new MediaInfoDocumentImpl(mid, labelList, Collections.emptyList(), 1234);
        PropertyDocument pr = new PropertyDocumentImpl(new PropertyIdValueImpl("P42", "foo"), labelList, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), new DatatypeIdImpl(DatatypeIdValue.DT_STRING), 1234);
        MediaInfoDocument irDiffMediaInfoIdValue = new MediaInfoDocumentImpl(new MediaInfoIdValueImpl("M23", "http://example.org/"), labelList, Collections.emptyList(), 1234);
        assertNotEquals(mi1, irDiffLabel);
        assertNotEquals(mi1, irDiffStatementGroups);
        assertNotEquals(irDiffStatementGroups, irDiffMediaInfoIdValue);
        assertNotEquals(mi1, pr);
    }

    @Test
    public void equalityBasedOnContent_5() {
        MediaInfoDocument irDiffRevisions = new MediaInfoDocumentImpl(mid, labelList, statementGroups, 1235);
        assertNotEquals(mi1, irDiffRevisions);
    }

    @Test
    public void equalityBasedOnContent_8() {
        assertNotEquals(mi1, null);
    }

    @Test
    public void equalityBasedOnContent_9() {
        assertNotEquals(mi1, this);
    }

    @Test
    public void testWithEntityId_1() {
        assertEquals(MediaInfoIdValue.NULL, mi1.withEntityId(MediaInfoIdValue.NULL).getEntityId());
    }

    @Test
    public void testWithEntityId_2() {
        MediaInfoIdValue id = Datamodel.makeWikimediaCommonsMediaInfoIdValue("M123");
        assertEquals(id, mi1.withEntityId(id).getEntityId());
    }

    @Test
    public void testWithRevisionId_1() {
        assertEquals(1235L, mi1.withRevisionId(1235L).getRevisionId());
    }

    @Test
    public void testWithRevisionId_2() {
        assertEquals(mi1, mi1.withRevisionId(1325L).withRevisionId(mi1.getRevisionId()));
    }
}
