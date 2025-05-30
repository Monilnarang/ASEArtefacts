package org.dyn4j.collision;

import junit.framework.TestCase;
import org.junit.Test;

public class TypeFilterTest_Purified {

    private static class All extends TypeFilter {
    }

    private static class Category1 extends All {
    }

    private static class Category2 extends All {
    }

    private static class Category3 extends Category1 {
    }

    private static class Category4 extends Category1 {
    }

    private static final TypeFilter ALL = new All();

    private static final TypeFilter CATEGORY_1 = new Category1();

    private static final TypeFilter CATEGORY_2 = new Category2();

    private static final TypeFilter CATEGORY_3 = new Category3();

    private static final TypeFilter CATEGORY_4 = new Category4();

    @Test
    public void filter_1() {
        TestCase.assertTrue(ALL.isAllowed(ALL));
    }

    @Test
    public void filter_2() {
        TestCase.assertTrue(ALL.isAllowed(CATEGORY_1));
    }

    @Test
    public void filter_3() {
        TestCase.assertTrue(ALL.isAllowed(CATEGORY_2));
    }

    @Test
    public void filter_4() {
        TestCase.assertTrue(ALL.isAllowed(CATEGORY_3));
    }

    @Test
    public void filter_5() {
        TestCase.assertTrue(ALL.isAllowed(CATEGORY_4));
    }

    @Test
    public void filter_6() {
        TestCase.assertTrue(CATEGORY_1.isAllowed(ALL));
    }

    @Test
    public void filter_7() {
        TestCase.assertTrue(CATEGORY_2.isAllowed(ALL));
    }

    @Test
    public void filter_8() {
        TestCase.assertTrue(CATEGORY_3.isAllowed(ALL));
    }

    @Test
    public void filter_9() {
        TestCase.assertTrue(CATEGORY_4.isAllowed(ALL));
    }

    @Test
    public void filter_10() {
        TestCase.assertFalse(CATEGORY_1.isAllowed(CATEGORY_2));
    }

    @Test
    public void filter_11() {
        TestCase.assertFalse(CATEGORY_3.isAllowed(CATEGORY_2));
    }

    @Test
    public void filter_12() {
        TestCase.assertFalse(CATEGORY_4.isAllowed(CATEGORY_2));
    }

    @Test
    public void filter_13() {
        TestCase.assertFalse(CATEGORY_3.isAllowed(CATEGORY_4));
    }

    @Test
    public void filter_14() {
        TestCase.assertFalse(CATEGORY_2.isAllowed(CATEGORY_1));
    }

    @Test
    public void filter_15() {
        TestCase.assertFalse(CATEGORY_2.isAllowed(CATEGORY_3));
    }

    @Test
    public void filter_16() {
        TestCase.assertFalse(CATEGORY_2.isAllowed(CATEGORY_4));
    }

    @Test
    public void filter_17() {
        TestCase.assertFalse(CATEGORY_4.isAllowed(CATEGORY_3));
    }

    @Test
    public void filter_18() {
        TestCase.assertTrue(CATEGORY_1.isAllowed(CATEGORY_3));
    }

    @Test
    public void filter_19() {
        TestCase.assertTrue(CATEGORY_1.isAllowed(CATEGORY_4));
    }

    @Test
    public void filter_20() {
        TestCase.assertTrue(CATEGORY_3.isAllowed(CATEGORY_1));
    }

    @Test
    public void filter_21() {
        TestCase.assertTrue(CATEGORY_4.isAllowed(CATEGORY_1));
    }

    @Test
    public void filter_22() {
        TestCase.assertFalse(ALL.isAllowed(null));
    }

    @Test
    public void filter_23() {
        TestCase.assertFalse(ALL.isAllowed(new CategoryFilter()));
    }

    @Test
    public void filter_24() {
        TestCase.assertNotNull(ALL.toString());
    }
}
