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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestYAMLConfiguration_Parameterized {

    @TempDir
    public File tempFolder;

    private final String testYaml = ConfigurationAssert.getTestFile("test.yaml").getAbsolutePath();

    private YAMLConfiguration yamlConfiguration;

    @BeforeEach
    public void setUp() throws Exception {
        yamlConfiguration = new YAMLConfiguration();
        yamlConfiguration.read(new FileReader(testYaml));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetPropertyDictionary_1to3")
    public void testGetPropertyDictionary_1to3(String param1, String param2) {
        assertEquals(param1, yamlConfiguration.getProperty(param2));
    }

    static public Stream<Arguments> Provider_testGetPropertyDictionary_1to3() {
        return Stream.of(arguments("Martin D'vloper", "martin.name"), arguments("Developer", "martin.job"), arguments("Elite", "martin.skill"));
    }
}
