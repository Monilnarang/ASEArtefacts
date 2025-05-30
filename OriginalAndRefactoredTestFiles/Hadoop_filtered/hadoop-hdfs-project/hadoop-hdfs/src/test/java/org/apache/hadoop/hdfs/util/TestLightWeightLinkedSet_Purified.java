package org.apache.hadoop.hdfs.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.util.Time;
import org.junit.Before;
import org.junit.Test;

public class TestLightWeightLinkedSet_Purified {

    private static final Logger LOG = LoggerFactory.getLogger("org.apache.hadoop.hdfs.TestLightWeightLinkedSet");

    private final ArrayList<Integer> list = new ArrayList<Integer>();

    private final int NUM = 100;

    private LightWeightLinkedSet<Integer> set;

    private Random rand;

    @Before
    public void setUp() {
        float maxF = LightWeightLinkedSet.DEFAULT_MAX_LOAD_FACTOR;
        float minF = LightWeightLinkedSet.DEFAUT_MIN_LOAD_FACTOR;
        int initCapacity = LightWeightLinkedSet.MINIMUM_CAPACITY;
        rand = new Random(Time.now());
        list.clear();
        for (int i = 0; i < NUM; i++) {
            list.add(rand.nextInt());
        }
        set = new LightWeightLinkedSet<Integer>(initCapacity, maxF, minF);
    }

    @Test
    public void testRemoveOne_1() {
        assertTrue(set.add(list.get(0)));
    }

    @Test
    public void testRemoveOne_2() {
        assertEquals(1, set.size());
    }

    @Test
    public void testRemoveOne_3() {
        assertTrue(set.remove(list.get(0)));
    }

    @Test
    public void testRemoveOne_4() {
        assertEquals(0, set.size());
    }

    @Test
    public void testRemoveOne_5_testMerged_5() {
        Iterator<Integer> iter = set.iterator();
        assertFalse(iter.hasNext());
        assertNull(set.pollFirst());
        assertEquals(0, set.pollAll().size());
        assertEquals(0, set.pollN(10).size());
        iter = set.iterator();
        assertTrue(iter.hasNext());
    }

    @Test
    public void testRemoveOne_9() {
        assertTrue(set.add(list.get(0)));
    }

    @Test
    public void testRemoveOne_10() {
        assertEquals(1, set.size());
    }

    @Test(timeout = 60000)
    public void testBookmarkAdvancesOnRemoveOfSameElement_1() {
        assertTrue(set.add(list.get(0)));
    }

    @Test(timeout = 60000)
    public void testBookmarkAdvancesOnRemoveOfSameElement_2() {
        assertTrue(set.add(list.get(1)));
    }

    @Test(timeout = 60000)
    public void testBookmarkAdvancesOnRemoveOfSameElement_3() {
        assertTrue(set.add(list.get(2)));
    }

    @Test(timeout = 60000)
    public void testBookmarkAdvancesOnRemoveOfSameElement_4_testMerged_4() {
        Iterator<Integer> it = set.getBookmark();
        assertEquals(it.next(), list.get(0));
        set.remove(list.get(1));
        it = set.getBookmark();
        assertEquals(it.next(), list.get(2));
    }
}
