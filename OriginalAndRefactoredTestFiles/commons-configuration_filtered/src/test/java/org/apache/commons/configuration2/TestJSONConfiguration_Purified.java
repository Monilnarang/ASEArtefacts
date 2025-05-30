package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

public class TestJSONConfiguration_Purified {

    private final String testJson = ConfigurationAssert.getTestFile("test.json").getAbsolutePath();

    private JSONConfiguration jsonConfiguration;

    @BeforeEach
    public void setUp() throws Exception {
        jsonConfiguration = new JSONConfiguration();
        jsonConfiguration.read(new FileReader(testJson));
    }

    @Test
    public void testGetPropertyDictionary_1() {
        assertEquals("Martin D'vloper", jsonConfiguration.getProperty("martin.name"));
    }

    @Test
    public void testGetPropertyDictionary_2() {
        assertEquals("Developer", jsonConfiguration.getProperty("martin.job"));
    }

    @Test
    public void testGetPropertyDictionary_3() {
        assertEquals("Elite", jsonConfiguration.getProperty("martin.skill"));
    }

    @Test
    public void testGetPropertyDictionaryInList_1() {
        assertEquals("UK", jsonConfiguration.getString("capitals(1).country"));
    }

    @Test
    public void testGetPropertyDictionaryInList_2() {
        assertEquals("Washington", jsonConfiguration.getString("capitals(0).capital"));
    }
}
