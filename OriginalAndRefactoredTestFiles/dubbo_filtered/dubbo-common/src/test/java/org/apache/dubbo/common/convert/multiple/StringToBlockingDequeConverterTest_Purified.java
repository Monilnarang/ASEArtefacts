package org.apache.dubbo.common.convert.multiple;

import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.common.utils.JRE;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TransferQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.util.Arrays.asList;
import static org.apache.dubbo.common.extension.ExtensionLoader.getExtensionLoader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringToBlockingDequeConverterTest_Purified {

    private MultiValueConverter converter;

    @BeforeEach
    public void init() {
        converter = getExtensionLoader(MultiValueConverter.class).getExtension("string-to-blocking-deque");
    }

    @Test
    void testAccept_1() {
        assertFalse(converter.accept(String.class, Collection.class));
    }

    @Test
    void testAccept_2() {
        assertFalse(converter.accept(String.class, List.class));
    }

    @Test
    void testAccept_3() {
        assertFalse(converter.accept(String.class, AbstractList.class));
    }

    @Test
    void testAccept_4() {
        assertFalse(converter.accept(String.class, ArrayList.class));
    }

    @Test
    void testAccept_5() {
        assertFalse(converter.accept(String.class, LinkedList.class));
    }

    @Test
    void testAccept_6() {
        assertFalse(converter.accept(String.class, Set.class));
    }

    @Test
    void testAccept_7() {
        assertFalse(converter.accept(String.class, SortedSet.class));
    }

    @Test
    void testAccept_8() {
        assertFalse(converter.accept(String.class, NavigableSet.class));
    }

    @Test
    void testAccept_9() {
        assertFalse(converter.accept(String.class, TreeSet.class));
    }

    @Test
    void testAccept_10() {
        assertFalse(converter.accept(String.class, ConcurrentSkipListSet.class));
    }

    @Test
    void testAccept_11() {
        assertFalse(converter.accept(String.class, Queue.class));
    }

    @Test
    void testAccept_12() {
        assertFalse(converter.accept(String.class, BlockingQueue.class));
    }

    @Test
    void testAccept_13() {
        assertFalse(converter.accept(String.class, TransferQueue.class));
    }

    @Test
    void testAccept_14() {
        assertFalse(converter.accept(String.class, Deque.class));
    }

    @Test
    void testAccept_15() {
        assertTrue(converter.accept(String.class, BlockingDeque.class));
    }

    @Test
    void testAccept_16() {
        assertFalse(converter.accept(null, char[].class));
    }

    @Test
    void testAccept_17() {
        assertFalse(converter.accept(null, String.class));
    }

    @Test
    void testAccept_18() {
        assertFalse(converter.accept(null, String.class));
    }

    @Test
    void testAccept_19() {
        assertFalse(converter.accept(null, null));
    }
}
