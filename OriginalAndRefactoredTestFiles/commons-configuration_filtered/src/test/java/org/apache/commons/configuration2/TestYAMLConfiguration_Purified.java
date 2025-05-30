package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.yaml.snakeyaml.Yaml;

public class TestYAMLConfiguration_Purified {

    @TempDir
    public File tempFolder;

    private final String testYaml = ConfigurationAssert.getTestFile("test.yaml").getAbsolutePath();

    private YAMLConfiguration yamlConfiguration;

    @BeforeEach
    public void setUp() throws Exception {
        yamlConfiguration = new YAMLConfiguration();
        yamlConfiguration.read(new FileReader(testYaml));
    }

    @Test
    public void testGetPropertyDictionary_1() {
        assertEquals("Martin D'vloper", yamlConfiguration.getProperty("martin.name"));
    }

    @Test
    public void testGetPropertyDictionary_2() {
        assertEquals("Developer", yamlConfiguration.getProperty("martin.job"));
    }

    @Test
    public void testGetPropertyDictionary_3() {
        assertEquals("Elite", yamlConfiguration.getProperty("martin.skill"));
    }
}
