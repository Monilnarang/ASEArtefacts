package org.wikidata.wdtk.datamodel.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;

public class LexemeDocumentImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://example.com/entity/");

    private final LexemeIdValue lid = new LexemeIdValueImpl("L42", "http://example.com/entity/");

    private final ItemIdValue lexCat = new ItemIdValueImpl("Q1", "http://example.com/entity/");

    private final ItemIdValue language = new ItemIdValueImpl("Q2", "http://example.com/entity/");

    private final Statement s = new StatementImpl("MyId", StatementRank.NORMAL, new SomeValueSnakImpl(new PropertyIdValueImpl("P42", "http://example.com/entity/")), Collections.emptyList(), Collections.emptyList(), lid);

    private final List<StatementGroup> statementGroups = Collections.singletonList(new StatementGroupImpl(Collections.singletonList(s)));

    private final MonolingualTextValue lemma = new TermImpl("en", "lemma");

    private final List<MonolingualTextValue> lemmaList = Collections.singletonList(lemma);

    private final FormDocument form = new FormDocumentImpl(new FormIdValueImpl("L42-F1", "http://example.com/entity/"), Collections.singletonList(new TermImpl("en", "foo")), Collections.emptyList(), Collections.emptyList(), 0);

    private final List<FormDocument> forms = Collections.singletonList(form);

    private final SenseDocument sense = new SenseDocumentImpl(new SenseIdValueImpl("L42-S1", "http://example.com/entity/"), Collections.singletonList(new TermImpl("en", "foo meaning")), Collections.emptyList(), 0);

    private final List<SenseDocument> senses = Collections.singletonList(sense);

    private final LexemeDocument ld1 = new LexemeDocumentImpl(lid, lexCat, language, lemmaList, statementGroups, forms, senses, 1234);

    private final LexemeDocument ld2 = new LexemeDocumentImpl(lid, lexCat, language, lemmaList, statementGroups, forms, senses, 1234);

    private final LexemeDocument ld3 = new LexemeDocumentImpl(lid, lexCat, language, lemmaList, statementGroups, Collections.emptyList(), Collections.emptyList(), 1234);

    private final String JSON_LEXEME = "{\"type\":\"lexeme\",\"id\":\"L42\",\"lexicalCategory\":\"Q1\",\"language\":\"Q2\",\"lemmas\":{\"en\":{\"language\":\"en\",\"value\":\"lemma\"}},\"claims\":{\"P42\":[{\"rank\":\"normal\",\"id\":\"MyId\",\"mainsnak\":{\"property\":\"P42\",\"snaktype\":\"somevalue\"},\"type\":\"statement\"}]},\"forms\":[{\"type\":\"form\",\"id\":\"L42-F1\",\"representations\":{\"en\":{\"language\":\"en\",\"value\":\"foo\"}},\"grammaticalFeatures\":[],\"claims\":{}}],\"senses\":[{\"type\":\"sense\",\"id\":\"L42-S1\",\"glosses\":{\"en\":{\"language\":\"en\",\"value\":\"foo meaning\"}},\"claims\":{}}],\"lastrevid\":1234}";

    private final String JSON_LEXEME_FOR_ISSUE_568 = "{\"type\":\"lexeme\",\"id\":\"L42\",\"lexicalCategory\":\"Q1\",\"language\":\"Q2\",\"lemmas\":{\"en\":{\"language\":\"en\",\"value\":\"lemma\"}},\"claims\":{\"P42\":[{\"rank\":\"normal\",\"id\":\"MyId\",\"mainsnak\":{\"property\":\"P42\",\"snaktype\":\"somevalue\"},\"type\":\"statement\"}]},\"forms\":{},\"senses\":{},\"lastrevid\":1234}";

    @Test
    public void fieldsAreCorrect_1() {
        assertEquals(ld1.getEntityId(), lid);
    }

    @Test
    public void fieldsAreCorrect_2() {
        assertEquals(ld1.getLanguage(), language);
    }

    @Test
    public void fieldsAreCorrect_3() {
        assertEquals(ld1.getLexicalCategory(), lexCat);
    }

    @Test
    public void fieldsAreCorrect_4() {
        assertEquals(ld1.getLemmas(), Collections.singletonMap(lemma.getLanguageCode(), lemma));
    }

    @Test
    public void fieldsAreCorrect_5() {
        assertEquals(ld1.getStatementGroups(), statementGroups);
    }

    @Test
    public void fieldsAreCorrect_6() {
        assertEquals(ld1.getForms(), forms);
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(ld1, ld1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(ld1, ld2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        LexemeDocument irDiffLexCat = new LexemeDocumentImpl(lid, language, language, lemmaList, statementGroups, forms, senses, 1234);
        assertNotEquals(ld1, irDiffLexCat);
    }

    @Test
    public void equalityBasedOnContent_4() {
        LexemeDocument irDiffLanguage = new LexemeDocumentImpl(lid, lexCat, lexCat, lemmaList, statementGroups, forms, senses, 1234);
        assertNotEquals(ld1, irDiffLanguage);
    }

    @Test
    public void equalityBasedOnContent_5_testMerged_5() {
        LexemeDocument irDiffLemmas = new LexemeDocumentImpl(lid, lexCat, language, Collections.singletonList(new TermImpl("en", "bar")), statementGroups, forms, senses, 1234);
        LexemeDocument irDiffStatementGroups = new LexemeDocumentImpl(lid, lexCat, language, lemmaList, Collections.emptyList(), forms, senses, 1234);
        LexemeDocument irDiffForms = new LexemeDocumentImpl(lid, lexCat, language, lemmaList, statementGroups, Collections.emptyList(), senses, 1234);
        LexemeDocument irDiffSenses = new LexemeDocumentImpl(lid, lexCat, language, lemmaList, statementGroups, forms, Collections.emptyList(), 1234);
        PropertyDocument pr = new PropertyDocumentImpl(new PropertyIdValueImpl("P42", "foo"), lemmaList, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), new DatatypeIdImpl(DatatypeIdValue.DT_STRING), 1234);
        LexemeDocument irDiffLexemeIdValue = new LexemeDocumentImpl(new LexemeIdValueImpl("L43", "http://example.com/entity/"), lexCat, language, lemmaList, Collections.emptyList(), forms, senses, 1235);
        assertNotEquals(ld1, irDiffLemmas);
        assertNotEquals(ld1, irDiffStatementGroups);
        assertNotEquals(ld1, irDiffForms);
        assertNotEquals(ld1, irDiffSenses);
        assertNotEquals(irDiffStatementGroups, irDiffLexemeIdValue);
        assertNotEquals(ld1, pr);
    }

    @Test
    public void equalityBasedOnContent_9() {
        LexemeDocument irDiffRevisions = new LexemeDocumentImpl(lid, lexCat, language, lemmaList, statementGroups, forms, senses, 1235);
        assertNotEquals(ld1, irDiffRevisions);
    }

    @Test
    public void equalityBasedOnContent_12() {
        assertNotEquals(ld1, null);
    }

    @Test
    public void equalityBasedOnContent_13() {
        assertNotEquals(ld1, this);
    }

    @Test
    public void testWithEntityId_1() {
        assertEquals(LexemeIdValue.NULL, ld1.withEntityId(LexemeIdValue.NULL).getEntityId());
    }

    @Test
    public void testWithEntityId_2() {
        LexemeIdValue id = Datamodel.makeWikidataLexemeIdValue("L123");
        assertEquals(id, ld1.withEntityId(id).getEntityId());
    }

    @Test
    public void testWithRevisionId_1() {
        assertEquals(1235L, ld1.withRevisionId(1235L).getRevisionId());
    }

    @Test
    public void testWithRevisionId_2() {
        assertEquals(ld1, ld1.withRevisionId(1325L).withRevisionId(ld1.getRevisionId()));
    }
}
