package org.activiti.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;
import org.activiti.spring.SpringAsyncExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource("classpath:application-async-executor.properties")
public class AsyncExecutorConfigurationTest_Purified {

    @Autowired
    private SpringAsyncExecutor asyncExecutor;

    @Autowired
    private AsyncExecutorProperties properties;

    @Test
    public void shouldConfigureAsyncExecutorProperties_1() {
        assertThat(asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()).isEqualTo(properties.getDefaultAsyncJobAcquireWaitTimeInMillis());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_2() {
        assertThat(asyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()).isEqualTo(properties.getDefaultTimerJobAcquireWaitTimeInMillis());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_3() {
        assertThat(asyncExecutor.isMessageQueueMode()).isEqualTo(properties.isMessageQueueMode());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_4() {
        assertThat(asyncExecutor.getAsyncJobLockTimeInMillis()).isEqualTo(properties.getAsyncJobLockTimeInMillis());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_5() {
        assertThat(asyncExecutor.getCorePoolSize()).isEqualTo(properties.getCorePoolSize());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_6() {
        assertThat(asyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis()).isEqualTo(properties.getDefaultQueueSizeFullWaitTime());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_7() {
        assertThat(asyncExecutor.getKeepAliveTime()).isEqualTo(properties.getKeepAliveTime());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_8() {
        assertThat(asyncExecutor.getMaxAsyncJobsDuePerAcquisition()).isEqualTo(properties.getMaxAsyncJobsDuePerAcquisition());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_9() {
        assertThat(asyncExecutor.getRetryWaitTimeInMillis()).isEqualTo(properties.getRetryWaitTimeInMillis());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_10() {
        assertThat(asyncExecutor.getQueueSize()).isEqualTo(properties.getQueueSize());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_11() {
        assertThat(asyncExecutor.getResetExpiredJobsInterval()).isEqualTo(properties.getResetExpiredJobsInterval());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_12() {
        assertThat(asyncExecutor.getResetExpiredJobsPageSize()).isEqualTo(properties.getResetExpiredJobsPageSize());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_13() {
        assertThat(asyncExecutor.getSecondsToWaitOnShutdown()).isEqualTo(properties.getSecondsToWaitOnShutdown());
    }

    @Test
    public void shouldConfigureAsyncExecutorProperties_14() {
        assertThat(asyncExecutor.getTimerLockTimeInMillis()).isEqualTo(properties.getTimerLockTimeInMillis());
    }
}
