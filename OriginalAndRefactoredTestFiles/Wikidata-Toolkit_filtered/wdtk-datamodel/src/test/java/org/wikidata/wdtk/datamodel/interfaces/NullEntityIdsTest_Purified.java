package org.wikidata.wdtk.datamodel.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class NullEntityIdsTest_Purified {

    static class TestValueVisitor implements ValueVisitor<String> {

        @Override
        public String visit(EntityIdValue value) {
            return value.getId();
        }

        @Override
        public String visit(GlobeCoordinatesValue value) {
            return null;
        }

        @Override
        public String visit(MonolingualTextValue value) {
            return null;
        }

        @Override
        public String visit(QuantityValue value) {
            return null;
        }

        @Override
        public String visit(StringValue value) {
            return null;
        }

        @Override
        public String visit(TimeValue value) {
            return null;
        }

        @Override
        public String visit(UnsupportedValue value) {
            return null;
        }
    }

    @Test
    public void testNullItemId_1() {
        TestValueVisitor tvv = new TestValueVisitor();
        assertEquals("Q0", ItemIdValue.NULL.accept(tvv));
    }

    @Test
    public void testNullItemId_2() {
        assertEquals("Q0", ItemIdValue.NULL.getId());
    }

    @Test
    public void testNullItemId_3() {
        assertEquals("http://localhost/entity/", ItemIdValue.NULL.getSiteIri());
    }

    @Test
    public void testNullItemId_4() {
        assertEquals(EntityIdValue.ET_ITEM, ItemIdValue.NULL.getEntityType());
    }

    @Test
    public void testNullItemId_5() {
        assertEquals("http://localhost/entity/Q0", ItemIdValue.NULL.getIri());
    }

    @Test
    public void testNullItemId_6() {
        assertTrue(ItemIdValue.NULL.isPlaceholder());
    }

    @Test
    public void testNullPropertyId_1() {
        TestValueVisitor tvv = new TestValueVisitor();
        assertEquals("P0", PropertyIdValue.NULL.accept(tvv));
    }

    @Test
    public void testNullPropertyId_2() {
        assertEquals("P0", PropertyIdValue.NULL.getId());
    }

    @Test
    public void testNullPropertyId_3() {
        assertEquals("http://localhost/entity/", PropertyIdValue.NULL.getSiteIri());
    }

    @Test
    public void testNullPropertyId_4() {
        assertEquals(EntityIdValue.ET_PROPERTY, PropertyIdValue.NULL.getEntityType());
    }

    @Test
    public void testNullPropertyId_5() {
        assertEquals("http://localhost/entity/P0", PropertyIdValue.NULL.getIri());
    }

    @Test
    public void testNullPropertyId_6() {
        assertTrue(PropertyIdValue.NULL.isPlaceholder());
    }

    @Test
    public void testNullMediaInfoId_1() {
        TestValueVisitor tvv = new TestValueVisitor();
        assertEquals("M0", MediaInfoIdValue.NULL.accept(tvv));
    }

    @Test
    public void testNullMediaInfoId_2() {
        assertEquals("M0", MediaInfoIdValue.NULL.getId());
    }

    @Test
    public void testNullMediaInfoId_3() {
        assertEquals("http://localhost/entity/", MediaInfoIdValue.NULL.getSiteIri());
    }

    @Test
    public void testNullMediaInfoId_4() {
        assertEquals(EntityIdValue.ET_MEDIA_INFO, MediaInfoIdValue.NULL.getEntityType());
    }

    @Test
    public void testNullMediaInfoId_5() {
        assertEquals("http://localhost/entity/M0", MediaInfoIdValue.NULL.getIri());
    }

    @Test
    public void testNullMediaInfoId_6() {
        assertTrue(MediaInfoIdValue.NULL.isPlaceholder());
    }

    @Test
    public void testNullLexemeId_1() {
        TestValueVisitor tvv = new TestValueVisitor();
        assertEquals("L0", LexemeIdValue.NULL.accept(tvv));
    }

    @Test
    public void testNullLexemeId_2() {
        assertEquals("L0", LexemeIdValue.NULL.getId());
    }

    @Test
    public void testNullLexemeId_3() {
        assertEquals("http://localhost/entity/", LexemeIdValue.NULL.getSiteIri());
    }

    @Test
    public void testNullLexemeId_4() {
        assertEquals(EntityIdValue.ET_LEXEME, LexemeIdValue.NULL.getEntityType());
    }

    @Test
    public void testNullLexemeId_5() {
        assertEquals("http://localhost/entity/L0", LexemeIdValue.NULL.getIri());
    }

    @Test
    public void testNullLexemeId_6() {
        assertTrue(LexemeIdValue.NULL.isPlaceholder());
    }

    @Test
    public void testNullSenseId_1() {
        TestValueVisitor tvv = new TestValueVisitor();
        assertEquals("L0-S0", SenseIdValue.NULL.accept(tvv));
    }

    @Test
    public void testNullSenseId_2() {
        assertEquals("L0-S0", SenseIdValue.NULL.getId());
    }

    @Test
    public void testNullSenseId_3() {
        assertEquals("http://localhost/entity/", SenseIdValue.NULL.getSiteIri());
    }

    @Test
    public void testNullSenseId_4() {
        assertEquals(EntityIdValue.ET_SENSE, SenseIdValue.NULL.getEntityType());
    }

    @Test
    public void testNullSenseId_5() {
        assertEquals("http://localhost/entity/L0-S0", SenseIdValue.NULL.getIri());
    }

    @Test
    public void testNullSenseId_6() {
        assertTrue(SenseIdValue.NULL.isPlaceholder());
    }

    @Test
    public void testNullFormId_1() {
        TestValueVisitor tvv = new TestValueVisitor();
        assertEquals("L0-F0", FormIdValue.NULL.accept(tvv));
    }

    @Test
    public void testNullFormId_2() {
        assertEquals("L0-F0", FormIdValue.NULL.getId());
    }

    @Test
    public void testNullFormId_3() {
        assertEquals("http://localhost/entity/", FormIdValue.NULL.getSiteIri());
    }

    @Test
    public void testNullFormId_4() {
        assertEquals(EntityIdValue.ET_FORM, FormIdValue.NULL.getEntityType());
    }

    @Test
    public void testNullFormId_5() {
        assertEquals("http://localhost/entity/L0-F0", FormIdValue.NULL.getIri());
    }

    @Test
    public void testNullFormId_6() {
        assertTrue(FormIdValue.NULL.isPlaceholder());
    }
}
