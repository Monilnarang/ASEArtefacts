package org.wikidata.wdtk.datamodel.implementation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.producesJson;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.toJson;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.MediaInfoUpdateBuilder;
import org.wikidata.wdtk.datamodel.interfaces.MediaInfoIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MediaInfoUpdate;
import org.wikidata.wdtk.datamodel.interfaces.StatementUpdate;
import org.wikidata.wdtk.datamodel.interfaces.TermUpdate;

public class MediaInfoUpdateImplTest_Purified {

    private static final MediaInfoIdValue M1 = Datamodel.makeWikimediaCommonsMediaInfoIdValue("M1");

    private static final StatementUpdate STATEMENTS = StatementDocumentUpdateImplTest.STATEMENTS;

    private static final TermUpdate LABELS = LabeledDocumentUpdateImplTest.LABELS;

    @Test
    public void testEmpty_1() {
        assertTrue(new MediaInfoUpdateImpl(M1, 123, TermUpdate.EMPTY, StatementUpdate.EMPTY).isEmpty());
    }

    @Test
    public void testEmpty_2() {
        assertFalse(new MediaInfoUpdateImpl(M1, 123, LABELS, StatementUpdate.EMPTY).isEmpty());
    }

    @Test
    public void testJson_1() {
        assertThat(new MediaInfoUpdateImpl(M1, 123, TermUpdate.EMPTY, StatementUpdate.EMPTY), producesJson("{}"));
    }

    @Test
    public void testJson_2() {
        assertThat(MediaInfoUpdateBuilder.forEntityId(M1).updateLabels(LABELS).build(), producesJson("{'labels':" + toJson(LABELS) + "}"));
    }

    @Test
    public void testJson_3() {
        assertThat(MediaInfoUpdateBuilder.forEntityId(M1).updateStatements(STATEMENTS).build(), producesJson("{'claims':" + toJson(STATEMENTS) + "}"));
    }
}
