package org.apache.dubbo.common.utils;

import org.apache.dubbo.config.ProtocolConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.apache.dubbo.common.utils.CollectionUtils.isEmpty;
import static org.apache.dubbo.common.utils.CollectionUtils.isNotEmpty;
import static org.apache.dubbo.common.utils.CollectionUtils.ofSet;
import static org.apache.dubbo.common.utils.CollectionUtils.toMap;
import static org.apache.dubbo.common.utils.CollectionUtils.toStringMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollectionUtilsTest_Purified {

    @Test
    void testSortNull_1() {
        assertNull(CollectionUtils.sort(null));
    }

    @Test
    void testSortNull_2() {
        assertTrue(CollectionUtils.sort(new ArrayList<Integer>()).isEmpty());
    }

    @Test
    void testSortSimpleNameNull_1() {
        assertNull(CollectionUtils.sortSimpleName(null));
    }

    @Test
    void testSortSimpleNameNull_2() {
        assertTrue(CollectionUtils.sortSimpleName(new ArrayList<String>()).isEmpty());
    }

    @Test
    void testMapEquals_1() {
        assertTrue(CollectionUtils.mapEquals(null, null));
    }

    @Test
    void testMapEquals_2() {
        assertFalse(CollectionUtils.mapEquals(null, new HashMap<String, String>()));
    }

    @Test
    void testMapEquals_3() {
        assertFalse(CollectionUtils.mapEquals(new HashMap<String, String>(), null));
    }

    @Test
    void testMapEquals_4() {
        assertTrue(CollectionUtils.mapEquals(CollectionUtils.toStringMap("1", "a", "2", "b"), CollectionUtils.toStringMap("1", "a", "2", "b")));
    }

    @Test
    void testMapEquals_5() {
        assertFalse(CollectionUtils.mapEquals(CollectionUtils.toStringMap("1", "a"), CollectionUtils.toStringMap("1", "a", "2", "b")));
    }

    @Test
    void testIsEmpty_1() {
        assertThat(isEmpty(null), is(true));
    }

    @Test
    void testIsEmpty_2() {
        assertThat(isEmpty(new HashSet()), is(true));
    }

    @Test
    void testIsEmpty_3() {
        assertThat(isEmpty(emptyList()), is(true));
    }
}
