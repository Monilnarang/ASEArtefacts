package org.apache.commons.pool3.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class TestLinkedBlockingDeque_Purified {

    private static final Duration TIMEOUT_50_MILLIS = Duration.ofMillis(50);

    private static final Integer ONE = Integer.valueOf(1);

    private static final Integer TWO = Integer.valueOf(2);

    private static final Integer THREE = Integer.valueOf(3);

    LinkedBlockingDeque<Integer> deque;

    @BeforeEach
    public void setUp() {
        deque = new LinkedBlockingDeque<>(2);
    }

    @Test
    public void testPeek_1() {
        assertNull(deque.peek());
    }

    @Test
    public void testPeek_2() {
        deque.add(ONE);
        deque.add(TWO);
        assertEquals(Integer.valueOf(1), deque.peek());
    }

    @Test
    public void testPeekFirst_1() {
        assertNull(deque.peekFirst());
    }

    @Test
    public void testPeekFirst_2() {
        deque.add(ONE);
        deque.add(TWO);
        assertEquals(Integer.valueOf(1), deque.peekFirst());
    }

    @Test
    public void testPeekLast_1() {
        assertNull(deque.peekLast());
    }

    @Test
    public void testPeekLast_2() {
        deque.add(ONE);
        deque.add(TWO);
        assertEquals(Integer.valueOf(2), deque.peekLast());
    }

    @Test
    public void testPollFirst_1() {
        assertNull(deque.pollFirst());
    }

    @Test
    public void testPollFirst_2() {
        assertTrue(deque.offerFirst(ONE));
    }

    @Test
    public void testPollFirst_3() {
        assertTrue(deque.offerFirst(TWO));
    }

    @Test
    public void testPollFirst_4() {
        assertEquals(Integer.valueOf(2), deque.pollFirst());
    }

    @Test
    public void testPollFirstWithTimeout_1() throws InterruptedException {
        assertNull(deque.pollFirst());
    }

    @Test
    public void testPollFirstWithTimeout_2() throws InterruptedException {
        assertNull(deque.pollFirst(TIMEOUT_50_MILLIS));
    }

    @Test
    public void testPollLast_1() {
        assertNull(deque.pollLast());
    }

    @Test
    public void testPollLast_2() {
        assertTrue(deque.offerFirst(ONE));
    }

    @Test
    public void testPollLast_3() {
        assertTrue(deque.offerFirst(TWO));
    }

    @Test
    public void testPollLast_4() {
        assertEquals(Integer.valueOf(1), deque.pollLast());
    }

    @Test
    public void testPollLastWithTimeout_1() throws InterruptedException {
        assertNull(deque.pollLast());
    }

    @Test
    public void testPollLastWithTimeout_2() throws InterruptedException {
        assertNull(deque.pollLast(TIMEOUT_50_MILLIS));
    }

    @Test
    public void testPollWithTimeout_1() throws InterruptedException {
        assertNull(deque.poll(TIMEOUT_50_MILLIS));
    }

    @Test
    public void testPollWithTimeout_2() throws InterruptedException {
        assertNull(deque.poll(TIMEOUT_50_MILLIS));
    }

    @Test
    public void testRemoveLastOccurrence_1() {
        assertFalse(deque.removeLastOccurrence(null));
    }

    @Test
    public void testRemoveLastOccurrence_2() {
        assertFalse(deque.removeLastOccurrence(ONE));
    }

    @Test
    public void testRemoveLastOccurrence_3_testMerged_3() {
        deque.add(ONE);
        assertTrue(deque.removeLastOccurrence(ONE));
        assertEquals(1, deque.size());
    }

    @Test
    public void testTake_1() throws InterruptedException {
        assertTrue(deque.offerFirst(ONE));
    }

    @Test
    public void testTake_2() throws InterruptedException {
        assertTrue(deque.offerFirst(TWO));
    }

    @Test
    public void testTake_3() throws InterruptedException {
        assertEquals(Integer.valueOf(2), deque.take());
    }

    @Test
    public void testTakeFirst_1() throws InterruptedException {
        assertTrue(deque.offerFirst(ONE));
    }

    @Test
    public void testTakeFirst_2() throws InterruptedException {
        assertTrue(deque.offerFirst(TWO));
    }

    @Test
    public void testTakeFirst_3() throws InterruptedException {
        assertEquals(Integer.valueOf(2), deque.takeFirst());
    }

    @Test
    public void testTakeLast_1() throws InterruptedException {
        assertTrue(deque.offerFirst(ONE));
    }

    @Test
    public void testTakeLast_2() throws InterruptedException {
        assertTrue(deque.offerFirst(TWO));
    }

    @Test
    public void testTakeLast_3() throws InterruptedException {
        assertEquals(Integer.valueOf(1), deque.takeLast());
    }
}
