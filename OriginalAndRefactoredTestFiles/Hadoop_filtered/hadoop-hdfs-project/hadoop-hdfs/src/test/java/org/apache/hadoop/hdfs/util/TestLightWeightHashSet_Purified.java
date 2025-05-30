package org.apache.hadoop.hdfs.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.util.Time;
import org.junit.Before;
import org.junit.Test;

public class TestLightWeightHashSet_Purified {

    private static final Logger LOG = LoggerFactory.getLogger("org.apache.hadoop.hdfs.TestLightWeightHashSet");

    private final ArrayList<Integer> list = new ArrayList<Integer>();

    private final int NUM = 100;

    private LightWeightHashSet<Integer> set;

    private Random rand;

    @Before
    public void setUp() {
        float maxF = LightWeightHashSet.DEFAULT_MAX_LOAD_FACTOR;
        float minF = LightWeightHashSet.DEFAUT_MIN_LOAD_FACTOR;
        int initCapacity = LightWeightHashSet.MINIMUM_CAPACITY;
        rand = new Random(Time.now());
        list.clear();
        for (int i = 0; i < NUM; i++) {
            list.add(rand.nextInt());
        }
        set = new LightWeightHashSet<Integer>(initCapacity, maxF, minF);
    }

    private static class TestObject {

        private final String value;

        public TestObject(String value) {
            super();
            this.value = value;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            TestObject other = (TestObject) obj;
            return this.value.equals(other.value);
        }
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
        iter = set.iterator();
        assertTrue(iter.hasNext());
    }

    @Test
    public void testRemoveOne_6() {
        assertTrue(set.add(list.get(0)));
    }

    @Test
    public void testRemoveOne_7() {
        assertEquals(1, set.size());
    }
}
