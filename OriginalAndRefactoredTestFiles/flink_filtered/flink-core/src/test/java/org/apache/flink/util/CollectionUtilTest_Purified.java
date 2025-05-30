package org.apache.flink.util;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import static org.apache.flink.util.CollectionUtil.HASH_MAP_DEFAULT_LOAD_FACTOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CollectionUtilTest_Purified {

    static class A {
    }

    static class B extends A {
    }

    static class C extends B {
    }

    @Test
    void testIsEmptyOrAllElementsNull_1() {
        assertThat(CollectionUtil.isEmptyOrAllElementsNull(Collections.emptyList())).isTrue();
    }

    @Test
    void testIsEmptyOrAllElementsNull_2() {
        assertThat(CollectionUtil.isEmptyOrAllElementsNull(Collections.singletonList(null))).isTrue();
    }

    @Test
    void testIsEmptyOrAllElementsNull_3() {
        assertThat(CollectionUtil.isEmptyOrAllElementsNull(Arrays.asList(null, null))).isTrue();
    }

    @Test
    void testIsEmptyOrAllElementsNull_4() {
        assertThat(CollectionUtil.isEmptyOrAllElementsNull(Collections.singletonList("test"))).isFalse();
    }

    @Test
    void testIsEmptyOrAllElementsNull_5() {
        assertThat(CollectionUtil.isEmptyOrAllElementsNull(Arrays.asList(null, "test"))).isFalse();
    }

    @Test
    void testIsEmptyOrAllElementsNull_6() {
        assertThat(CollectionUtil.isEmptyOrAllElementsNull(Arrays.asList("test", null))).isFalse();
    }

    @Test
    void testIsEmptyOrAllElementsNull_7() {
        assertThat(CollectionUtil.isEmptyOrAllElementsNull(Arrays.asList(null, "test", null))).isFalse();
    }
}
