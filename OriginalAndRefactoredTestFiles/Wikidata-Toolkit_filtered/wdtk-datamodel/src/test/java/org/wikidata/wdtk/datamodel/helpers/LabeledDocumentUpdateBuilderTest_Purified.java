package org.wikidata.wdtk.datamodel.helpers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.LabeledDocumentUpdate;
import org.wikidata.wdtk.datamodel.interfaces.LabeledStatementDocumentUpdate;
import org.wikidata.wdtk.datamodel.interfaces.LexemeIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MediaInfoDocument;
import org.wikidata.wdtk.datamodel.interfaces.MediaInfoIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Statement;

public class LabeledDocumentUpdateBuilderTest_Purified {

    private static final ItemIdValue Q1 = EntityUpdateBuilderTest.Q1;

    private static final PropertyIdValue P1 = EntityUpdateBuilderTest.P1;

    private static final MediaInfoIdValue M1 = EntityUpdateBuilderTest.M1;

    private static final LexemeIdValue L1 = EntityUpdateBuilderTest.L1;

    private static final ItemDocument ITEM = EntityUpdateBuilderTest.ITEM;

    private static final PropertyDocument PROPERTY = EntityUpdateBuilderTest.PROPERTY;

    private static final MediaInfoDocument MEDIA = EntityUpdateBuilderTest.MEDIA;

    private static final Statement JOHN_HAS_BROWN_HAIR = StatementUpdateBuilderTest.JOHN_HAS_BROWN_HAIR;

    private static final Statement JOHN_HAS_BLUE_EYES = StatementUpdateBuilderTest.JOHN_HAS_BLUE_EYES;

    private static final MonolingualTextValue EN = TermUpdateBuilderTest.EN;

    private static final MonolingualTextValue SK = TermUpdateBuilderTest.SK;

    @Test
    public void testForBaseRevisionId_1() {
        assertEquals(123, LabeledDocumentUpdateBuilder.forBaseRevisionId(Q1, 123).getBaseRevisionId());
    }

    @Test
    public void testForBaseRevisionId_2() {
        assertEquals(123, LabeledDocumentUpdateBuilder.forBaseRevisionId(P1, 123).getBaseRevisionId());
    }

    @Test
    public void testForBaseRevisionId_3() {
        assertEquals(123, LabeledDocumentUpdateBuilder.forBaseRevisionId(M1, 123).getBaseRevisionId());
    }
}
