package org.wikidata.wdtk.datamodel.helpers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import org.wikidata.wdtk.datamodel.interfaces.FormDocument;
import org.wikidata.wdtk.datamodel.interfaces.FormIdValue;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.LexemeDocument;
import org.wikidata.wdtk.datamodel.interfaces.LexemeIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MediaInfoDocument;
import org.wikidata.wdtk.datamodel.interfaces.MediaInfoIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.SenseDocument;
import org.wikidata.wdtk.datamodel.interfaces.SenseIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementDocumentUpdate;

public class StatementDocumentUpdateBuilderTest_Purified {

    private static final ItemIdValue Q1 = EntityUpdateBuilderTest.Q1;

    private static final PropertyIdValue P1 = EntityUpdateBuilderTest.P1;

    private static final MediaInfoIdValue M1 = EntityUpdateBuilderTest.M1;

    private static final LexemeIdValue L1 = EntityUpdateBuilderTest.L1;

    private static final FormIdValue F1 = EntityUpdateBuilderTest.F1;

    private static final SenseIdValue S1 = EntityUpdateBuilderTest.S1;

    private static final ItemDocument ITEM = EntityUpdateBuilderTest.ITEM;

    private static final PropertyDocument PROPERTY = EntityUpdateBuilderTest.PROPERTY;

    private static final MediaInfoDocument MEDIA = EntityUpdateBuilderTest.MEDIA;

    private static final LexemeDocument LEXEME = EntityUpdateBuilderTest.LEXEME;

    private static final FormDocument FORM = EntityUpdateBuilderTest.FORM;

    private static final SenseDocument SENSE = EntityUpdateBuilderTest.SENSE;

    private static final EntityIdValue JOHN = StatementUpdateBuilderTest.JOHN;

    private static final EntityIdValue RITA = StatementUpdateBuilderTest.RITA;

    private static final Statement JOHN_ALREADY_HAS_BROWN_HAIR = StatementUpdateBuilderTest.JOHN_ALREADY_HAS_BROWN_HAIR;

    private static final Statement JOHN_ALREADY_HAS_BLUE_EYES = StatementUpdateBuilderTest.JOHN_ALREADY_HAS_BLUE_EYES;

    @Test
    public void testForBaseRevisionId_1() {
        assertEquals(123, StatementDocumentUpdateBuilder.forBaseRevisionId(Q1, 123).getBaseRevisionId());
    }

    @Test
    public void testForBaseRevisionId_2() {
        assertEquals(123, StatementDocumentUpdateBuilder.forBaseRevisionId(P1, 123).getBaseRevisionId());
    }

    @Test
    public void testForBaseRevisionId_3() {
        assertEquals(123, StatementDocumentUpdateBuilder.forBaseRevisionId(M1, 123).getBaseRevisionId());
    }

    @Test
    public void testForBaseRevisionId_4() {
        assertEquals(123, StatementDocumentUpdateBuilder.forBaseRevisionId(L1, 123).getBaseRevisionId());
    }

    @Test
    public void testForBaseRevisionId_5() {
        assertEquals(123, StatementDocumentUpdateBuilder.forBaseRevisionId(F1, 123).getBaseRevisionId());
    }

    @Test
    public void testForBaseRevisionId_6() {
        assertEquals(123, StatementDocumentUpdateBuilder.forBaseRevisionId(S1, 123).getBaseRevisionId());
    }
}
