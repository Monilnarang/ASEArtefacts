package com.graphhopper.coll;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinHeapWithUpdateTest_Purified implements BinaryHeapTestInterface {

    private MinHeapWithUpdate heap;

    @Override
    public void create(int capacity) {
        heap = new MinHeapWithUpdate(capacity);
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public void push(int id, float val) {
        heap.push(id, val);
    }

    boolean contains(int id) {
        return heap.contains(id);
    }

    @Override
    public int peekId() {
        return heap.peekId();
    }

    @Override
    public float peekVal() {
        return heap.peekValue();
    }

    @Override
    public void update(int id, float val) {
        heap.update(id, val);
    }

    @Override
    public int poll() {
        return heap.poll();
    }

    @Override
    public void clear() {
        heap.clear();
    }

    @Test
    void testContains_1() {
        assertFalse(contains(3));
    }

    @Test
    void testContains_2() {
        assertTrue(contains(1));
    }

    @Test
    void testContains_3() {
        assertEquals(1, poll());
    }

    @Test
    void testContains_4() {
        assertFalse(contains(1));
    }

    @Test
    void containsAfterClear_1() {
        assertEquals(2, size());
    }

    @Test
    void containsAfterClear_2() {
        assertFalse(contains(0));
    }

    @Test
    void containsAfterClear_3() {
        assertFalse(contains(1));
    }

    @Test
    void containsAfterClear_4() {
        assertFalse(contains(2));
    }
}
