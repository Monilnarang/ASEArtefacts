package org.graylog2.plugin.configuration.fields;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TextFieldTest_Purified {

    @Test
    public void testIsOptional_1() throws Exception {
        TextField f = new TextField("test", "Name", "default", "description", ConfigurationField.Optional.OPTIONAL);
        assertEquals(ConfigurationField.Optional.OPTIONAL, f.isOptional());
    }

    @Test
    public void testIsOptional_2() throws Exception {
        TextField f2 = new TextField("test", "Name", "default", "description", ConfigurationField.Optional.NOT_OPTIONAL);
        assertEquals(ConfigurationField.Optional.NOT_OPTIONAL, f2.isOptional());
    }

    @Test
    public void testGetAttributes_1() throws Exception {
        TextField f = new TextField("test", "Name", "default", "description");
        assertEquals(0, f.getAttributes().size());
    }

    @Test
    public void testGetAttributes_2_testMerged_2() throws Exception {
        TextField f1 = new TextField("test", "Name", "default", "description", TextField.Attribute.IS_PASSWORD);
        assertEquals(1, f1.getAttributes().size());
        assertTrue(f1.getAttributes().contains("is_password"));
    }
}
