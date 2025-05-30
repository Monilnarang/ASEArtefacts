package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.*;

public class StatementImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://example.com/entity/");

    private final EntityIdValue subjet = new ItemIdValueImpl("Q1", "http://example.com/entity/");

    private final EntityIdValue value = new ItemIdValueImpl("Q42", "http://example.com/entity/");

    private final PropertyIdValue property = new PropertyIdValueImpl("P42", "http://example.com/entity/");

    private final ValueSnak mainSnak = new ValueSnakImpl(property, value);

    private final List<SnakGroup> qualifiers = Collections.singletonList(new SnakGroupImpl(Collections.singletonList(mainSnak)));

    private final List<Reference> references = Collections.singletonList(new ReferenceImpl(qualifiers));

    private final Claim claim = new ClaimImpl(subjet, mainSnak, qualifiers);

    private final Statement s1 = new StatementImpl("MyId", StatementRank.PREFERRED, mainSnak, qualifiers, references, subjet);

    private final Statement s2 = new StatementImpl("MyId", StatementRank.PREFERRED, mainSnak, qualifiers, references, subjet);

    private final String JSON_STATEMENT = "{\"rank\":\"preferred\",\"references\":[{\"snaks\":{\"P42\":[{\"property\":\"P42\",\"datatype\":\"wikibase-item\",\"datavalue\":{\"value\":{\"id\":\"Q42\",\"numeric-id\":42,\"entity-type\":\"item\"},\"type\":\"wikibase-entityid\"},\"snaktype\":\"value\"}]},\"snaks-order\":[\"P42\"]}],\"id\":\"MyId\",\"mainsnak\":{\"property\":\"P42\",\"datatype\":\"wikibase-item\",\"datavalue\":{\"value\":{\"id\":\"Q42\",\"numeric-id\":42,\"entity-type\":\"item\"},\"type\":\"wikibase-entityid\"},\"snaktype\":\"value\"},\"qualifiers-order\":[\"P42\"],\"type\":\"statement\",\"qualifiers\":{\"P42\":[{\"property\":\"P42\",\"datatype\":\"wikibase-item\",\"datavalue\":{\"value\":{\"id\":\"Q42\",\"numeric-id\":42,\"entity-type\":\"item\"},\"type\":\"wikibase-entityid\"},\"snaktype\":\"value\"}]}}";

    private final Statement smallStatement = new StatementImpl("MyId", StatementRank.PREFERRED, mainSnak, Collections.emptyList(), Collections.emptyList(), subjet);

    private final String JSON_SMALL_STATEMENT = "{\"rank\":\"preferred\",\"id\":\"MyId\",\"mainsnak\":{\"property\":\"P42\",\"datatype\":\"wikibase-item\",\"datavalue\":{\"value\":{\"id\":\"Q42\",\"numeric-id\":42,\"entity-type\":\"item\"},\"type\":\"wikibase-entityid\"},\"snaktype\":\"value\"},\"type\":\"statement\"}";

    @Test
    public void gettersWorking_1() {
        assertEquals(s1.getClaim(), claim);
    }

    @Test
    public void gettersWorking_2() {
        assertEquals(s1.getMainSnak(), mainSnak);
    }

    @Test
    public void gettersWorking_3() {
        assertEquals(s1.getQualifiers(), qualifiers);
    }

    @Test
    public void gettersWorking_4() {
        assertEquals(s1.getReferences(), references);
    }

    @Test
    public void gettersWorking_5() {
        assertEquals(s1.getRank(), StatementRank.PREFERRED);
    }

    @Test
    public void gettersWorking_6() {
        assertEquals(s1.getStatementId(), "MyId");
    }

    @Test
    public void gettersWorking_7() {
        assertEquals(s1.getValue(), value);
    }

    @Test
    public void gettersWorking_8() {
        assertEquals(s1.getSubject(), subjet);
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(s1, s1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(s1, s2);
    }

    @Test
    public void equalityBasedOnContent_3_testMerged_3() {
        Statement sDiffClaim = new StatementImpl("MyId", StatementRank.NORMAL, mainSnak, Collections.emptyList(), Collections.emptyList(), new ItemIdValueImpl("Q43", "http://wikidata.org/entity/"));
        Statement sDiffReferences = new StatementImpl("MyId", StatementRank.NORMAL, mainSnak, Collections.emptyList(), Collections.singletonList(new ReferenceImpl(Collections.singletonList(new SnakGroupImpl(Collections.singletonList(mainSnak))))), value);
        Statement sDiffRank = new StatementImpl("MyId", StatementRank.PREFERRED, mainSnak, Collections.emptyList(), Collections.emptyList(), value);
        Statement sDiffId = new StatementImpl("MyOtherId", StatementRank.NORMAL, mainSnak, Collections.emptyList(), Collections.emptyList(), value);
        assertNotEquals(s1, sDiffClaim);
        assertNotEquals(s1, sDiffReferences);
        assertNotEquals(s1, sDiffRank);
        assertNotEquals(s1, sDiffId);
    }

    @Test
    public void equalityBasedOnContent_7() {
        assertNotEquals(s1, null);
    }

    @Test
    public void equalityBasedOnContent_8() {
        assertNotEquals(s1, this);
    }
}
