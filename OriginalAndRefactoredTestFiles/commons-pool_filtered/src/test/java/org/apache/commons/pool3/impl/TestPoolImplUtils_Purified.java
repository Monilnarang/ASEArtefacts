package org.apache.commons.pool3.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.apache.commons.pool3.BasePooledObjectFactory;
import org.apache.commons.pool3.PooledObject;
import org.junit.jupiter.api.Test;

public class TestPoolImplUtils_Purified {

    @SuppressWarnings("unused")
    private abstract static class FactoryAB<A, B> extends BasePooledObjectFactory<B, RuntimeException> {
    }

    private abstract static class FactoryBA<A, B> extends FactoryAB<B, A> {
    }

    private abstract static class FactoryC<C> extends FactoryBA<C, String> {
    }

    @SuppressWarnings("unused")
    private abstract static class FactoryDE<D, E> extends FactoryC<D> {
    }

    private abstract static class FactoryF<F> extends FactoryDE<Long, F> {
    }

    private static final class NotSimpleFactory extends FactoryF<Integer> {

        @Override
        public Long create() {
            return null;
        }

        @Override
        public PooledObject<Long> wrap(final Long obj) {
            return null;
        }
    }

    private static final class SimpleFactory extends BasePooledObjectFactory<String, RuntimeException> {

        @Override
        public String create() {
            return null;
        }

        @Override
        public PooledObject<String> wrap(final String obj) {
            return null;
        }
    }

    private static final Instant INSTANT_1 = Instant.ofEpochMilli(1);

    private static final Instant INSTANT_0 = Instant.ofEpochMilli(0);

    @Test
    public void testMaxInstants_1() {
        assertEquals(INSTANT_1, PoolImplUtils.max(INSTANT_0, INSTANT_1));
    }

    @Test
    public void testMaxInstants_2() {
        assertEquals(INSTANT_1, PoolImplUtils.max(INSTANT_1, INSTANT_0));
    }

    @Test
    public void testMaxInstants_3() {
        assertEquals(INSTANT_1, PoolImplUtils.max(INSTANT_1, INSTANT_1));
    }

    @Test
    public void testMaxInstants_4() {
        assertEquals(INSTANT_0, PoolImplUtils.max(INSTANT_0, INSTANT_0));
    }

    @Test
    public void testMinInstants_1() {
        assertEquals(INSTANT_0, PoolImplUtils.min(INSTANT_0, INSTANT_1));
    }

    @Test
    public void testMinInstants_2() {
        assertEquals(INSTANT_0, PoolImplUtils.min(INSTANT_1, INSTANT_0));
    }

    @Test
    public void testMinInstants_3() {
        assertEquals(INSTANT_1, PoolImplUtils.min(INSTANT_1, INSTANT_1));
    }

    @Test
    public void testMinInstants_4() {
        assertEquals(INSTANT_0, PoolImplUtils.min(INSTANT_0, INSTANT_0));
    }

    @Test
    public void testToChronoUnit_1() {
        assertEquals(ChronoUnit.NANOS, PoolImplUtils.toChronoUnit(TimeUnit.NANOSECONDS));
    }

    @Test
    public void testToChronoUnit_2() {
        assertEquals(ChronoUnit.MICROS, PoolImplUtils.toChronoUnit(TimeUnit.MICROSECONDS));
    }

    @Test
    public void testToChronoUnit_3() {
        assertEquals(ChronoUnit.MILLIS, PoolImplUtils.toChronoUnit(TimeUnit.MILLISECONDS));
    }

    @Test
    public void testToChronoUnit_4() {
        assertEquals(ChronoUnit.SECONDS, PoolImplUtils.toChronoUnit(TimeUnit.SECONDS));
    }

    @Test
    public void testToChronoUnit_5() {
        assertEquals(ChronoUnit.MINUTES, PoolImplUtils.toChronoUnit(TimeUnit.MINUTES));
    }

    @Test
    public void testToChronoUnit_6() {
        assertEquals(ChronoUnit.HOURS, PoolImplUtils.toChronoUnit(TimeUnit.HOURS));
    }

    @Test
    public void testToChronoUnit_7() {
        assertEquals(ChronoUnit.DAYS, PoolImplUtils.toChronoUnit(TimeUnit.DAYS));
    }
}
