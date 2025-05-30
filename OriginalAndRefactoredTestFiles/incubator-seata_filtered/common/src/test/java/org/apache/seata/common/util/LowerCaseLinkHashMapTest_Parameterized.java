package org.apache.seata.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LowerCaseLinkHashMapTest_Parameterized {

    private static final Map<String, Object> lowerCaseLinkHashMap = new LowerCaseLinkHashMap<>();

    public LowerCaseLinkHashMapTest() {
        lowerCaseLinkHashMap.put("Key", "Value");
        lowerCaseLinkHashMap.put("Key2", "Value2");
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isEmpty_1() {
        Assertions.assertFalse(lowerCaseLinkHashMap.isEmpty());
    }

    @Test
    void isEmpty_2() {
        Assertions.assertTrue(new LowerCaseLinkHashMap<>().isEmpty());
    }

    @Test
    void containsValue_1() {
        Assertions.assertTrue(lowerCaseLinkHashMap.containsValue("Value"));
    }

    @Test
    void containsValue_2() {
        Assertions.assertFalse(lowerCaseLinkHashMap.containsValue("test"));
    }

    @Test
    void get_1() {
        Assertions.assertEquals("Value", lowerCaseLinkHashMap.get("key"));
    }

    @ParameterizedTest
    @MethodSource("Provider_containsKey_1to3")
    void containsKey_1to3(String param1) {
        Assertions.assertTrue(lowerCaseLinkHashMap.containsKey(param1));
    }

    static public Stream<Arguments> Provider_containsKey_1to3() {
        return Stream.of(arguments("key"), arguments("Key"), arguments("KEY"));
    }

    @ParameterizedTest
    @MethodSource("Provider_containsKey_4to5")
    void containsKey_4to5(String param1) {
        Assertions.assertFalse(lowerCaseLinkHashMap.containsKey(param1));
    }

    static public Stream<Arguments> Provider_containsKey_4to5() {
        return Stream.of(arguments("test"), arguments(123));
    }

    @ParameterizedTest
    @MethodSource("Provider_get_2to3")
    void get_2to3(String param1) {
        Assertions.assertNull(lowerCaseLinkHashMap.get(param1));
    }

    static public Stream<Arguments> Provider_get_2to3() {
        return Stream.of(arguments("key12"), arguments(123));
    }
}
