package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.Collections;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.StatementUpdateBuilder;
import org.wikidata.wdtk.datamodel.helpers.TermUpdateBuilder;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.LabeledStatementDocumentUpdate;
import org.wikidata.wdtk.datamodel.interfaces.SiteLink;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementUpdate;
import org.wikidata.wdtk.datamodel.interfaces.TermUpdate;

public class LabeledDocumentUpdateImplTest_Purified {

    static final ItemIdValue JOHN = StatementUpdateImplTest.JOHN;

    private static final Statement JOHN_HAS_BROWN_HAIR = StatementUpdateImplTest.JOHN_HAS_BROWN_HAIR;

    private static final Collection<SiteLink> NO_SITELINKS = Collections.emptyList();

    private static final Collection<String> NO_REMOVED_SITELINKS = Collections.emptyList();

    private static final StatementUpdate STATEMENTS = StatementUpdateBuilder.create().add(JOHN_HAS_BROWN_HAIR).build();

    static final TermUpdate LABELS = TermUpdateBuilder.create().remove("en").build();

    private static LabeledStatementDocumentUpdate create(ItemIdValue entityId, long revisionId, StatementUpdate statements, TermUpdate labels) {
        return new ItemUpdateImpl(entityId, revisionId, labels, TermUpdate.EMPTY, Collections.emptyMap(), statements, NO_SITELINKS, NO_REMOVED_SITELINKS);
    }

    @Test
    public void testEmpty_1() {
        assertFalse(create(JOHN, 0, STATEMENTS, TermUpdate.EMPTY).isEmpty());
    }

    @Test
    public void testEmpty_2() {
        assertFalse(create(JOHN, 0, StatementUpdate.EMPTY, LABELS).isEmpty());
    }

    @Test
    public void testEmpty_3() {
        assertTrue(create(JOHN, 0, StatementUpdate.EMPTY, TermUpdate.EMPTY).isEmpty());
    }
}
