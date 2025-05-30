package io.elasticjob.lite.spring.job;

import io.elasticjob.lite.internal.schedule.JobRegistry;
import io.elasticjob.lite.spring.fixture.job.DataflowElasticJob;
import io.elasticjob.lite.spring.fixture.job.FooSimpleElasticJob;
import io.elasticjob.lite.spring.test.AbstractZookeeperJUnit4SpringContextTests;
import io.elasticjob.lite.reg.base.CoordinatorRegistryCenter;
import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.annotation.Resource;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public abstract class AbstractJobSpringIntegrateTest_Purified extends AbstractZookeeperJUnit4SpringContextTests {

    private final String simpleJobName;

    private final String throughputDataflowJobName;

    @Resource
    private CoordinatorRegistryCenter regCenter;

    @Before
    @After
    public void reset() {
        FooSimpleElasticJob.reset();
        DataflowElasticJob.reset();
    }

    @After
    public void tearDown() {
        JobRegistry.getInstance().shutdown(simpleJobName);
        JobRegistry.getInstance().shutdown(throughputDataflowJobName);
    }

    private void assertSimpleElasticJobBean() {
        while (!FooSimpleElasticJob.isCompleted()) {
            sleep(100L);
        }
        assertTrue(FooSimpleElasticJob.isCompleted());
        assertTrue(regCenter.isExisted("/" + simpleJobName + "/sharding"));
    }

    private void assertThroughputDataflowElasticJobBean() {
        while (!DataflowElasticJob.isCompleted()) {
            sleep(100L);
        }
        assertTrue(DataflowElasticJob.isCompleted());
        assertTrue(regCenter.isExisted("/" + throughputDataflowJobName + "/sharding"));
    }

    private static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void assertSpringJobBean_1() {
        assertSimpleElasticJobBean();
    }

    @Test
    public void assertSpringJobBean_2() {
        assertThroughputDataflowElasticJobBean();
    }
}
