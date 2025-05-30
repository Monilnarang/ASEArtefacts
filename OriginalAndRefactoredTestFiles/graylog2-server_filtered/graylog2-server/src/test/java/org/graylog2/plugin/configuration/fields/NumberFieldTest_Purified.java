package org.graylog2.plugin.configuration.fields;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumberFieldTest_Purified {

    @Test
    public void testIsOptional_1() throws Exception {
        NumberField f = new NumberField("test", "Name", 0, "foo", ConfigurationField.Optional.NOT_OPTIONAL);
        assertEquals(ConfigurationField.Optional.NOT_OPTIONAL, f.isOptional());
    }

    @Test
    public void testIsOptional_2() throws Exception {
        NumberField f2 = new NumberField("test", "Name", 0, "foo", ConfigurationField.Optional.OPTIONAL);
        assertEquals(ConfigurationField.Optional.OPTIONAL, f2.isOptional());
    }

    @Test
    public void testGetAttributes_1() throws Exception {
        NumberField f = new NumberField("test", "Name", 0, "foo", ConfigurationField.Optional.NOT_OPTIONAL);
        assertEquals(0, f.getAttributes().size());
    }

    @Test
    public void testGetAttributes_2_testMerged_2() throws Exception {
        NumberField f1 = new NumberField("test", "Name", 0, "foo", NumberField.Attribute.IS_PORT_NUMBER);
        assertEquals(1, f1.getAttributes().size());
        assertTrue(f1.getAttributes().contains("is_port_number"));
    }

    @Test
    public void testGetAttributes_4_testMerged_3() throws Exception {
        NumberField f2 = new NumberField("test", "Name", 0, "foo", NumberField.Attribute.IS_PORT_NUMBER, NumberField.Attribute.ONLY_POSITIVE);
        assertEquals(2, f2.getAttributes().size());
        assertTrue(f2.getAttributes().contains("is_port_number"));
        assertTrue(f2.getAttributes().contains("only_positive"));
    }
}
