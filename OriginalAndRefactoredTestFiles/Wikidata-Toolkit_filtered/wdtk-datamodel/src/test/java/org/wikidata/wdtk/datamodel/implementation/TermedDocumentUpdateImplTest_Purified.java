package org.wikidata.wdtk.datamodel.implementation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.AliasUpdateBuilder;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.TermUpdateBuilder;
import org.wikidata.wdtk.datamodel.interfaces.AliasUpdate;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.datamodel.interfaces.StatementUpdate;
import org.wikidata.wdtk.datamodel.interfaces.TermUpdate;
import org.wikidata.wdtk.datamodel.interfaces.TermedStatementDocumentUpdate;

public class TermedDocumentUpdateImplTest_Purified {

    private static final ItemIdValue JOHN = StatementUpdateImplTest.JOHN;

    private static final StatementUpdate STATEMENTS = StatementDocumentUpdateImplTest.STATEMENTS;

    private static final MonolingualTextValue EN = Datamodel.makeMonolingualTextValue("hello", "en");

    private static final MonolingualTextValue SK = Datamodel.makeMonolingualTextValue("ahoj", "sk");

    private static final TermUpdate LABELS = TermUpdateBuilder.create().remove("de").build();

    static final TermUpdate DESCRIPTIONS = TermUpdateBuilder.create().remove("en").build();

    static final AliasUpdate ALIAS = AliasUpdateBuilder.create().add(EN).build();

    static final Map<String, AliasUpdate> ALIASES = new HashMap<>();

    static {
        ALIASES.put("en", ALIAS);
    }

    private static TermedStatementDocumentUpdate create(ItemIdValue entityId, long revisionId, StatementUpdate statements, TermUpdate labels, TermUpdate descriptions, Map<String, AliasUpdate> aliases) {
        return new ItemUpdateImpl(entityId, revisionId, labels, descriptions, aliases, statements, Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void testEmpty_1() {
        assertTrue(create(JOHN, 0, StatementUpdate.EMPTY, TermUpdate.EMPTY, TermUpdate.EMPTY, Collections.emptyMap()).isEmpty());
    }

    @Test
    public void testEmpty_2() {
        assertFalse(create(JOHN, 0, StatementUpdate.EMPTY, LABELS, TermUpdate.EMPTY, Collections.emptyMap()).isEmpty());
    }

    @Test
    public void testEmpty_3() {
        assertFalse(create(JOHN, 0, StatementUpdate.EMPTY, TermUpdate.EMPTY, DESCRIPTIONS, Collections.emptyMap()).isEmpty());
    }

    @Test
    public void testEmpty_4() {
        assertFalse(create(JOHN, 0, StatementUpdate.EMPTY, TermUpdate.EMPTY, TermUpdate.EMPTY, ALIASES).isEmpty());
    }
}
