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
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.SiteLink;
import org.wikidata.wdtk.datamodel.interfaces.StatementDocumentUpdate;
import org.wikidata.wdtk.datamodel.interfaces.StatementUpdate;
import org.wikidata.wdtk.datamodel.interfaces.TermUpdate;

public class StatementDocumentUpdateImplTest_Purified {

    private static final ItemIdValue JOHN = StatementUpdateImplTest.JOHN;

    static final StatementUpdate STATEMENTS = StatementUpdateBuilder.create().remove("ID123").build();

    private static final Collection<SiteLink> NO_SITELINKS = Collections.emptyList();

    private static final Collection<String> NO_REMOVED_SITELINKS = Collections.emptyList();

    private static StatementDocumentUpdate create(ItemIdValue entityId, long revisionId, StatementUpdate statements) {
        return new ItemUpdateImpl(entityId, revisionId, TermUpdate.EMPTY, TermUpdate.EMPTY, Collections.emptyMap(), statements, NO_SITELINKS, NO_REMOVED_SITELINKS);
    }

    @Test
    public void testEmpty_1() {
        assertFalse(create(JOHN, 0, STATEMENTS).isEmpty());
    }

    @Test
    public void testEmpty_2() {
        assertTrue(create(JOHN, 0, StatementUpdate.EMPTY).isEmpty());
    }
}
