package io.openmessaging.rocketmq.promise;

import io.openmessaging.Future;
import io.openmessaging.FutureListener;
import io.openmessaging.Promise;
import io.openmessaging.exception.OMSRuntimeException;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;

public class DefaultPromiseTest_Purified {

    private Promise<String> promise;

    @Before
    public void init() {
        promise = new DefaultPromise<>();
    }

    @Test
    public void testIsDone_1() throws Exception {
        assertThat(promise.isDone()).isEqualTo(false);
    }

    @Test
    public void testIsDone_2() throws Exception {
        promise.set("Done");
        assertThat(promise.isDone()).isEqualTo(true);
    }

    @Test
    public void getThrowable_1() throws Exception {
        assertThat(promise.getThrowable()).isNull();
    }

    @Test
    public void getThrowable_2() throws Exception {
        Throwable exception = new OMSRuntimeException("-1", "Test Error");
        promise.setFailure(exception);
        assertThat(promise.getThrowable()).isEqualTo(exception);
    }
}
