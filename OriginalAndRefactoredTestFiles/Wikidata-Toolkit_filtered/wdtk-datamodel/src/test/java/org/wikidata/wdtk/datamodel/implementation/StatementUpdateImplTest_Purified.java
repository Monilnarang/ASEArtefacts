package org.wikidata.wdtk.datamodel.implementation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.producesJson;
import static org.wikidata.wdtk.datamodel.implementation.JsonTestUtils.toJson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.StatementBuilder;
import org.wikidata.wdtk.datamodel.helpers.StatementUpdateBuilder;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementUpdate;
import org.wikidata.wdtk.datamodel.interfaces.StringValue;

public class StatementUpdateImplTest_Purified {

    private static final Collection<Statement> NO_STATEMENTS = Collections.emptyList();

    private static final Collection<String> NO_IDS = Collections.emptyList();

    static final ItemIdValue JOHN = Datamodel.makeWikidataItemIdValue("Q1");

    private static final EntityIdValue RITA = Datamodel.makeWikidataItemIdValue("Q2");

    private static final PropertyIdValue HAIR = Datamodel.makeWikidataPropertyIdValue("P1");

    private static final PropertyIdValue EYES = Datamodel.makeWikidataPropertyIdValue("P2");

    private static final StringValue BROWN = Datamodel.makeStringValue("brown");

    private static final StringValue SILVER = Datamodel.makeStringValue("silver");

    private static final StringValue BLUE = Datamodel.makeStringValue("blue");

    private static final Statement NOBODY_HAS_BROWN_HAIR = StatementBuilder.forSubjectAndProperty(ItemIdValue.NULL, HAIR).withValue(BROWN).build();

    private static final Statement NOBODY_ALREADY_HAS_BROWN_HAIR = NOBODY_HAS_BROWN_HAIR.withStatementId("ID1");

    static final Statement JOHN_HAS_BROWN_HAIR = StatementBuilder.forSubjectAndProperty(JOHN, HAIR).withValue(BROWN).build();

    static final Statement RITA_HAS_BROWN_HAIR = StatementBuilder.forSubjectAndProperty(RITA, HAIR).withValue(BROWN).build();

    private static final Statement JOHN_HAS_SILVER_HAIR = StatementBuilder.forSubjectAndProperty(JOHN, HAIR).withValue(SILVER).build();

    private static final Statement JOHN_ALREADY_HAS_SILVER_HAIR = JOHN_HAS_SILVER_HAIR.withStatementId("ID5");

    private static final Statement JOHN_HAS_BLUE_EYES = StatementBuilder.forSubjectAndProperty(JOHN, EYES).withValue(BLUE).build();

    private static final Statement JOHN_ALREADY_HAS_BLUE_EYES = JOHN_HAS_BLUE_EYES.withStatementId("ID8");

    @Test
    public void testJson_1() {
        assertThat(StatementUpdateBuilder.create().build(), producesJson("[]"));
    }

    @Test
    public void testJson_2() {
        assertThat(StatementUpdateBuilder.create().add(JOHN_HAS_BROWN_HAIR).build(), producesJson("[" + toJson(JOHN_HAS_BROWN_HAIR) + "]"));
    }

    @Test
    public void testJson_3() {
        assertThat(StatementUpdateBuilder.create().replace(JOHN_ALREADY_HAS_BLUE_EYES).build(), producesJson("[" + toJson(JOHN_ALREADY_HAS_BLUE_EYES) + "]"));
    }

    @Test
    public void testJson_4() {
        assertThat(StatementUpdateBuilder.create().remove("ID123").build(), producesJson("[{'id':'ID123','remove':''}]"));
    }
}
