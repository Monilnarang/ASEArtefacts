package org.wikidata.wdtk.datamodel.helpers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.AliasUpdate;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MediaInfoIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.TermedStatementDocumentUpdate;

public class TermedDocumentUpdateBuilderTest_Purified {

    private static final ItemIdValue Q1 = EntityUpdateBuilderTest.Q1;

    private static final PropertyIdValue P1 = EntityUpdateBuilderTest.P1;

    private static final MediaInfoIdValue M1 = EntityUpdateBuilderTest.M1;

    private static final ItemDocument ITEM = EntityUpdateBuilderTest.ITEM;

    private static final PropertyDocument PROPERTY = EntityUpdateBuilderTest.PROPERTY;

    private static final Statement JOHN_HAS_BROWN_HAIR = StatementUpdateBuilderTest.JOHN_HAS_BROWN_HAIR;

    private static final MonolingualTextValue EN = TermUpdateBuilderTest.EN;

    private static final MonolingualTextValue EN2 = TermUpdateBuilderTest.EN2;

    private static final MonolingualTextValue DE = TermUpdateBuilderTest.DE;

    private static final MonolingualTextValue DE2 = TermUpdateBuilderTest.DE2;

    private static final MonolingualTextValue SK = TermUpdateBuilderTest.SK;

    private static final MonolingualTextValue CS = TermUpdateBuilderTest.CS;

    private static final MonolingualTextValue FR = TermUpdateBuilderTest.FR;

    private static final MonolingualTextValue ES = Datamodel.makeMonolingualTextValue("hola", "es");

    @Test
    public void testForBaseRevisionId_1() {
        assertEquals(123, TermedDocumentUpdateBuilder.forBaseRevisionId(Q1, 123).getBaseRevisionId());
    }

    @Test
    public void testForBaseRevisionId_2() {
        assertEquals(123, TermedDocumentUpdateBuilder.forBaseRevisionId(P1, 123).getBaseRevisionId());
    }
}
