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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestJSONConfiguration_Parameterized {

    private final String testJson = ConfigurationAssert.getTestFile("test.json").getAbsolutePath();

    private JSONConfiguration jsonConfiguration;

    @BeforeEach
    public void setUp() throws Exception {
        jsonConfiguration = new JSONConfiguration();
        jsonConfiguration.read(new FileReader(testJson));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetPropertyDictionary_1to3")
    public void testGetPropertyDictionary_1to3(String param1, String param2) {
        assertEquals(param1, jsonConfiguration.getProperty(param2));
    }

    static public Stream<Arguments> Provider_testGetPropertyDictionary_1to3() {
        return Stream.of(arguments("Martin D'vloper", "martin.name"), arguments("Developer", "martin.job"), arguments("Elite", "martin.skill"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetPropertyDictionaryInList_1to2")
    public void testGetPropertyDictionaryInList_1to2(String param1, String param2) {
        assertEquals(param1, jsonConfiguration.getString(param2));
    }

    static public Stream<Arguments> Provider_testGetPropertyDictionaryInList_1to2() {
        return Stream.of(arguments("UK", "capitals(1).country"), arguments("Washington", "capitals(0).capital"));
    }
}
