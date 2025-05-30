package org.apache.flink.runtime.concurrent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.annotation.Nonnull;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

class ScheduledFutureAdapterTest_Purified {

    private ScheduledFutureAdapter<Integer> objectUnderTest;

    private TestFuture innerDelegate;

    @BeforeEach
    void before() {
        this.innerDelegate = new TestFuture();
        this.objectUnderTest = new ScheduledFutureAdapter<>(innerDelegate, 4200000321L, TimeUnit.NANOSECONDS);
    }

    private ScheduledFutureAdapter<Integer> getDeepCopyWithAdjustedTime(long nanoAdjust, long uid) {
        return new ScheduledFutureAdapter<>(innerDelegate, objectUnderTest.getScheduleTimeNanos() + nanoAdjust, uid);
    }

    static class TestFuture implements Future<Integer> {

        private boolean cancelExpected = false;

        private boolean isCancelledExpected = false;

        private boolean isDoneExpected = false;

        private int cancelInvocationCount = 0;

        private int isCancelledInvocationCount = 0;

        private int isDoneInvocationCount = 0;

        private int getInvocationCount = 0;

        private int getTimeoutInvocationCount = 0;

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            ++cancelInvocationCount;
            return cancelExpected;
        }

        @Override
        public boolean isCancelled() {
            ++isCancelledInvocationCount;
            return isCancelledExpected;
        }

        @Override
        public boolean isDone() {
            ++isDoneInvocationCount;
            return isDoneExpected;
        }

        @Override
        public Integer get() {
            ++getInvocationCount;
            return 4711;
        }

        @Override
        public Integer get(long timeout, @Nonnull TimeUnit unit) {
            ++getTimeoutInvocationCount;
            return 4711;
        }

        boolean isCancelExpected() {
            return cancelExpected;
        }

        boolean isCancelledExpected() {
            return isCancelledExpected;
        }

        boolean isDoneExpected() {
            return isDoneExpected;
        }

        void setCancelResult(boolean resultCancel) {
            this.cancelExpected = resultCancel;
        }

        void setIsCancelledResult(boolean resultIsCancelled) {
            this.isCancelledExpected = resultIsCancelled;
        }

        void setIsDoneExpected(boolean resultIsDone) {
            this.isDoneExpected = resultIsDone;
        }

        int getCancelInvocationCount() {
            return cancelInvocationCount;
        }

        int getIsCancelledInvocationCount() {
            return isCancelledInvocationCount;
        }

        int getIsDoneInvocationCount() {
            return isDoneInvocationCount;
        }

        int getGetInvocationCount() {
            return getInvocationCount;
        }

        int getGetTimeoutInvocationCount() {
            return getTimeoutInvocationCount;
        }
    }

    @Test
    void testForwardedMethods_1() throws Exception {
        assertThat(objectUnderTest.get()).isEqualTo(4711);
    }

    @Test
    void testForwardedMethods_2() throws Exception {
        assertThat(innerDelegate.getGetInvocationCount()).isEqualTo(1);
    }

    @Test
    void testForwardedMethods_3() throws Exception {
        assertThat(objectUnderTest.get(42L, TimeUnit.SECONDS)).isEqualTo(4711);
    }

    @Test
    void testForwardedMethods_4() throws Exception {
        assertThat(innerDelegate.getGetTimeoutInvocationCount()).isEqualTo(1);
    }

    @Test
    void testForwardedMethods_5() throws Exception {
        assertThat(objectUnderTest.cancel(true)).isEqualTo(innerDelegate.isCancelExpected());
    }

    @Test
    void testForwardedMethods_6() throws Exception {
        assertThat(innerDelegate.getCancelInvocationCount()).isEqualTo(1);
    }

    @Test
    void testForwardedMethods_7() throws Exception {
        assertThat(objectUnderTest.cancel(true)).isEqualTo(innerDelegate.isCancelExpected());
    }

    @Test
    void testForwardedMethods_8_testMerged_8() throws Exception {
        innerDelegate.setCancelResult(!innerDelegate.isCancelExpected());
        assertThat(innerDelegate.getCancelInvocationCount()).isEqualTo(2);
        assertThat(objectUnderTest.isCancelled()).isEqualTo(innerDelegate.isCancelledExpected());
        assertThat(innerDelegate.getIsCancelledInvocationCount()).isEqualTo(1);
        innerDelegate.setIsCancelledResult(!innerDelegate.isCancelledExpected());
        assertThat(innerDelegate.getIsCancelledInvocationCount()).isEqualTo(2);
        assertThat(objectUnderTest.isDone()).isEqualTo(innerDelegate.isDoneExpected());
        assertThat(innerDelegate.getIsDoneInvocationCount()).isEqualTo(1);
        innerDelegate.setIsDoneExpected(!innerDelegate.isDoneExpected());
        assertThat(innerDelegate.getIsDoneInvocationCount()).isEqualTo(2);
    }

    @Test
    void testCompareToEqualsHashCode_1() {
        assertThat(objectUnderTest.compareTo(objectUnderTest)).isEqualTo(0);
    }

    @Test
    void testCompareToEqualsHashCode_2() {
        assertThat((Object) objectUnderTest).isEqualTo(objectUnderTest);
    }

    @Test
    void testCompareToEqualsHashCode_3_testMerged_3() {
        ScheduledFutureAdapter<?> other = getDeepCopyWithAdjustedTime(0L, objectUnderTest.getTieBreakerUid());
        assertThat(objectUnderTest.compareTo(other)).isEqualTo(0);
        assertThat(other.compareTo(objectUnderTest)).isEqualTo(0);
        assertThat((Object) other).isEqualTo(objectUnderTest);
        assertThat(other.hashCode()).isEqualTo(objectUnderTest.hashCode());
        other = getDeepCopyWithAdjustedTime(0L, objectUnderTest.getTieBreakerUid() + 1L);
        assertThat(Integer.signum(objectUnderTest.compareTo(other))).isEqualTo(-1);
        assertThat(Integer.signum(other.compareTo(objectUnderTest))).isEqualTo(+1);
        assertThat((Object) objectUnderTest).isNotEqualTo(other);
        other = getDeepCopyWithAdjustedTime(+1L, objectUnderTest.getTieBreakerUid());
        other = getDeepCopyWithAdjustedTime(-1L, objectUnderTest.getTieBreakerUid());
        assertThat(Integer.signum(objectUnderTest.compareTo(other))).isEqualTo(+1);
        assertThat(Integer.signum(other.compareTo(objectUnderTest))).isEqualTo(-1);
    }
}
