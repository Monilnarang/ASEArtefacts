package org.apache.flink.runtime.state;

import org.apache.flink.api.java.tuple.Tuple2;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.apache.flink.runtime.state.StateUtil.discardStateFuture;
import static org.apache.flink.util.concurrent.FutureUtils.completedExceptionally;
import static org.assertj.core.api.Assertions.assertThat;

class StateUtilTest_Purified {

    private static <T> Future<T> emptyFuture(boolean done, boolean canBeCancelled) {
        return new Future<T>() {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return canBeCancelled;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return done;
            }

            @Override
            public T get() {
                throw new UnsupportedOperationException();
            }

            @Override
            public T get(long timeout, TimeUnit unit) {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static class TestStateObject implements CompositeStateHandle {

        private static final long serialVersionUID = -8070326169926626355L;

        private final int size;

        private final int checkpointedSize;

        private TestStateObject(int size, int checkpointedSize) {
            this.size = size;
            this.checkpointedSize = checkpointedSize;
        }

        @Override
        public long getStateSize() {
            return size;
        }

        @Override
        public void discardState() {
        }

        @Override
        public long getCheckpointedSize() {
            return checkpointedSize;
        }

        @Override
        public void registerSharedStates(SharedStateRegistry stateRegistry, long checkpointID) {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    void testDiscardStateSize_1() throws Exception {
        assertThat(discardStateFuture(completedFuture(new TestStateObject(1234, 123)))).isEqualTo(Tuple2.of(1234L, 123L));
    }

    @Test
    void testDiscardStateSize_2_testMerged_2() throws Exception {
        Tuple2<Long, Long> zeroSize = Tuple2.of(0L, 0L);
        assertThat(discardStateFuture(null)).isEqualTo(zeroSize);
        assertThat(discardStateFuture(new CompletableFuture<>())).isEqualTo(zeroSize);
        assertThat(discardStateFuture(completedExceptionally(new RuntimeException()))).isEqualTo(zeroSize);
        assertThat(discardStateFuture(emptyFuture(false, true))).isEqualTo(zeroSize);
        assertThat(discardStateFuture(emptyFuture(false, false))).isEqualTo(zeroSize);
        assertThat(discardStateFuture(emptyFuture(true, true))).isEqualTo(zeroSize);
        assertThat(discardStateFuture(emptyFuture(true, false))).isEqualTo(zeroSize);
    }
}
