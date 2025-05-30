package org.apache.dubbo.common.threadpool.manager;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.awaitility.Awaitility.await;

class ExecutorRepositoryTest_Purified {

    private ApplicationModel applicationModel;

    private ExecutorRepository executorRepository;

    @BeforeEach
    public void setup() {
        applicationModel = FrameworkModel.defaultModel().newApplication();
        executorRepository = ExecutorRepository.getInstance(applicationModel);
    }

    @AfterEach
    public void teardown() {
        applicationModel.destroy();
    }

    @Test
    void testGetExecutor_1() {
        Assertions.assertNotNull(executorRepository.getSharedExecutor());
    }

    @Test
    void testGetExecutor_2() {
        Assertions.assertNotNull(executorRepository.getServiceExportExecutor());
    }

    @Test
    void testGetExecutor_3() {
        Assertions.assertNotNull(executorRepository.getServiceReferExecutor());
    }
}
