package org.dyn4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class AVLTreeTest_Purified {

    private static final int[] VALUES = new int[] { 10, 3, -3, 4, 0, 1, 11, 19, 6, -1, 2, 9, -4, -8, 14, -5, 7, 31, 20, 8, -15, -11, -6, 16, -40 };

    private AVLTree<Integer> tree;

    private List<Integer> list;

    @Before
    public void setup() {
        this.tree = new AVLTree<Integer>();
        this.list = new ArrayList<Integer>();
        for (int i = 0; i < VALUES.length; i++) {
            tree.insert(VALUES[i]);
            list.add(VALUES[i]);
        }
    }

    @Test
    public void remove_1() {
        TestCase.assertNotNull(tree.remove(-3));
    }

    @Test
    public void remove_2() {
        TestCase.assertFalse(tree.contains(-3));
    }

    @Test
    public void remove_3() {
        TestCase.assertTrue(tree.contains(-4));
    }

    @Test
    public void remove_4() {
        TestCase.assertTrue(tree.contains(0));
    }

    @Test
    public void remove_5() {
        TestCase.assertTrue(tree.contains(1));
    }

    @Test
    public void remove_6() {
        TestCase.assertTrue(tree.contains(2));
    }

    @Test
    public void remove_7() {
        TestCase.assertTrue(tree.contains(3));
    }

    @Test
    public void remove_8_testMerged_8() {
        int size = tree.size();
        tree.removeMinimum();
        TestCase.assertTrue(tree.isBalanced());
        TestCase.assertTrue(tree.isValid());
        TestCase.assertFalse(tree.contains(-40));
        TestCase.assertEquals(size - 1, tree.size());
        BinarySearchTreeNode<Integer> node = tree.get(10);
        tree.removeMinimum(node);
        TestCase.assertFalse(tree.contains(4));
        TestCase.assertEquals(size - 2, tree.size());
        tree.removeMaximum();
        TestCase.assertFalse(tree.contains(31));
        TestCase.assertEquals(size - 3, tree.size());
        node = tree.get(0);
        tree.removeMaximum(node);
        TestCase.assertFalse(tree.contains(2));
        TestCase.assertEquals(size - 4, tree.size());
    }

    @Test
    public void getDepth_1() {
        TestCase.assertEquals(5, tree.getHeight());
    }

    @Test
    public void getDepth_2() {
        BinarySearchTreeNode<Integer> node = tree.get(-3);
        TestCase.assertEquals(2, tree.getHeight(node));
    }

    @Test
    public void getMinimum_1() {
        TestCase.assertEquals(-40, (int) tree.getMinimum());
    }

    @Test
    public void getMinimum_2_testMerged_2() {
        BinarySearchTreeNode<Integer> node = tree.get(10);
        TestCase.assertEquals(4, (int) tree.getMinimum(node).comparable);
        node = tree.get(1);
        TestCase.assertEquals(1, (int) tree.getMinimum(node).comparable);
    }

    @Test
    public void getMaximum_1() {
        TestCase.assertEquals(31, (int) tree.getMaximum());
    }

    @Test
    public void getMaximum_2_testMerged_2() {
        BinarySearchTreeNode<Integer> node = tree.get(-3);
        TestCase.assertEquals(-1, (int) tree.getMaximum(node).comparable);
        node = tree.get(6);
        TestCase.assertEquals(9, (int) tree.getMaximum(node).comparable);
    }

    @Test
    public void isEmpty_1() {
        TestCase.assertFalse(tree.isEmpty());
    }

    @Test
    public void isEmpty_2() {
        AVLTree<Integer> test = new AVLTree<Integer>();
        TestCase.assertTrue(test.isEmpty());
    }

    @Test
    public void clear_1() {
        TestCase.assertFalse(tree.isEmpty());
    }

    @Test
    public void clear_2() {
        TestCase.assertEquals(VALUES.length, tree.size());
    }

    @Test
    public void clear_3_testMerged_3() {
        tree.clear();
        TestCase.assertTrue(tree.isEmpty());
        TestCase.assertEquals(0, tree.size());
        TestCase.assertEquals(0, tree.getHeight());
        TestCase.assertEquals(null, tree.getRoot());
    }

    @Test
    public void contains_1() {
        TestCase.assertTrue(tree.contains(9));
    }

    @Test
    public void contains_2() {
        TestCase.assertFalse(tree.contains(-60));
    }

    @Test
    public void get_1() {
        TestCase.assertNotNull(tree.get(-3));
    }

    @Test
    public void get_2() {
        TestCase.assertNull(tree.get(45));
    }

    @Test
    public void size_1() {
        TestCase.assertEquals(VALUES.length, tree.size());
    }

    @Test
    public void size_2() {
        BinarySearchTreeNode<Integer> node = tree.get(-3);
        TestCase.assertEquals(3, tree.size(node));
    }
}
