package org.dyn4j;

import org.junit.Test;
import junit.framework.TestCase;

public class ReferenceTest_Purified {

    @Test
    public void testToString_1() {
        TestCase.assertEquals("null", new Reference<Integer>().toString());
    }

    @Test
    public void testToString_2() {
        TestCase.assertEquals(Integer.valueOf(5).toString(), new Reference<Integer>(5).toString());
    }

    @Test
    public void testEquals_1() {
        TestCase.assertEquals(new Reference<Integer>(), new Reference<Integer>());
    }

    @Test
    public void testEquals_2() {
        TestCase.assertEquals(new Reference<Integer>(5), new Reference<Integer>(5));
    }

    @Test
    public void testEquals_3_testMerged_3() {
        Reference<Integer> test = new Reference<Integer>(5);
        TestCase.assertEquals(test, new Reference<Integer>(5));
        TestCase.assertEquals(test, test);
        TestCase.assertFalse(test.equals(new Reference<Integer>(4)));
        TestCase.assertFalse(test.equals(null));
    }

    @Test
    public void testEquals_5() {
        TestCase.assertEquals(new Reference<Object>(), new Reference<Object>());
    }

    @Test
    public void testEquals_6_testMerged_5() {
        Object obj = new Object();
        Reference<Object> test2 = new Reference<Object>(obj);
        TestCase.assertEquals(new Reference<Object>(obj), new Reference<Object>(obj));
        TestCase.assertEquals(test2, new Reference<Object>(obj));
        TestCase.assertEquals(test2, test2);
    }

    @Test
    public void testEquals_9() {
        TestCase.assertFalse(new Reference<Integer>(1).equals(new Reference<Integer>()));
    }

    @Test
    public void testEquals_10() {
        TestCase.assertFalse(new Reference<Integer>().equals(new Reference<Integer>(1)));
    }

    @Test
    public void testEquals_11() {
        TestCase.assertFalse(new Reference<Integer>(1).equals(new Reference<Integer>(5)));
    }

    @Test
    public void testHashcode_1() {
        TestCase.assertEquals(new Reference<Integer>().hashCode(), new Reference<Integer>().hashCode());
    }

    @Test
    public void testHashcode_2() {
        TestCase.assertEquals(new Reference<Integer>(5).hashCode(), new Reference<Integer>(5).hashCode());
    }

    @Test
    public void testHashcode_3_testMerged_3() {
        Reference<Integer> test = new Reference<Integer>(5);
        TestCase.assertEquals(test.hashCode(), new Reference<Integer>(5).hashCode());
        TestCase.assertEquals(test.hashCode(), test.hashCode());
        TestCase.assertFalse(test.hashCode() == new Reference<Integer>(4).hashCode());
    }

    @Test
    public void testHashcode_5() {
        TestCase.assertEquals(new Reference<Object>().hashCode(), new Reference<Object>().hashCode());
    }

    @Test
    public void testHashcode_6_testMerged_5() {
        Object obj = new Object();
        Reference<Object> test2 = new Reference<Object>(obj);
        TestCase.assertEquals(new Reference<Object>(obj).hashCode(), new Reference<Object>(obj).hashCode());
        TestCase.assertEquals(test2.hashCode(), new Reference<Object>(obj).hashCode());
        TestCase.assertEquals(test2.hashCode(), test2.hashCode());
    }

    @Test
    public void testHashcode_9() {
        TestCase.assertFalse(new Reference<Integer>(1).hashCode() == new Reference<Integer>().hashCode());
    }

    @Test
    public void testHashcode_10() {
        TestCase.assertFalse(new Reference<Integer>().hashCode() == new Reference<Integer>(1).hashCode());
    }

    @Test
    public void testHashcode_11() {
        TestCase.assertFalse(new Reference<Integer>(1).hashCode() == new Reference<Integer>(5).hashCode());
    }
}
