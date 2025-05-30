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

public class LowerCaseLinkHashMapTest_Purified {

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
    void containsKey_1() {
        Assertions.assertTrue(lowerCaseLinkHashMap.containsKey("key"));
    }

    @Test
    void containsKey_2() {
        Assertions.assertTrue(lowerCaseLinkHashMap.containsKey("Key"));
    }

    @Test
    void containsKey_3() {
        Assertions.assertTrue(lowerCaseLinkHashMap.containsKey("KEY"));
    }

    @Test
    void containsKey_4() {
        Assertions.assertFalse(lowerCaseLinkHashMap.containsKey("test"));
    }

    @Test
    void containsKey_5() {
        Assertions.assertFalse(lowerCaseLinkHashMap.containsKey(123));
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

    @Test
    void get_2() {
        Assertions.assertNull(lowerCaseLinkHashMap.get("key12"));
    }

    @Test
    void get_3() {
        Assertions.assertNull(lowerCaseLinkHashMap.get(123));
    }
}
