package org.graylog2.plugin.configuration.fields;

import com.google.common.collect.Maps;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class DropdownFieldTest_Purified {

    @Test
    public void testIsOptional_1() throws Exception {
        DropdownField f = new DropdownField("test", "Name", "fooval", new HashMap<String, String>(), ConfigurationField.Optional.NOT_OPTIONAL);
        assertEquals(ConfigurationField.Optional.NOT_OPTIONAL, f.isOptional());
    }

    @Test
    public void testIsOptional_2() throws Exception {
        DropdownField f2 = new DropdownField("test", "Name", "fooval", new HashMap<String, String>(), ConfigurationField.Optional.OPTIONAL);
        assertEquals(ConfigurationField.Optional.OPTIONAL, f2.isOptional());
    }
}
