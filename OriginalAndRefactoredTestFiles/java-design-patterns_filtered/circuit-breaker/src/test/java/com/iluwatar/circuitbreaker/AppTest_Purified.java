package com.iluwatar.circuitbreaker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AppTest_Purified {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

    private static final int STARTUP_DELAY = 4;

    private static final int FAILURE_THRESHOLD = 1;

    private static final int RETRY_PERIOD = 2;

    private MonitoringService monitoringService;

    private CircuitBreaker delayedServiceCircuitBreaker;

    private CircuitBreaker quickServiceCircuitBreaker;

    @BeforeEach
    void setupCircuitBreakers() {
        var delayedService = new DelayedRemoteService(System.nanoTime(), STARTUP_DELAY);
        delayedServiceCircuitBreaker = new DefaultCircuitBreaker(delayedService, 3000, FAILURE_THRESHOLD, RETRY_PERIOD * 1000 * 1000 * 1000);
        var quickService = new QuickRemoteService();
        quickServiceCircuitBreaker = new DefaultCircuitBreaker(quickService, 3000, FAILURE_THRESHOLD, RETRY_PERIOD * 1000 * 1000 * 1000);
        monitoringService = new MonitoringService(delayedServiceCircuitBreaker, quickServiceCircuitBreaker);
    }

    @Test
    void testFailure_OpenStateTransition_1() {
        assertEquals("Delayed service is down", monitoringService.delayedServiceResponse());
    }

    @Test
    void testFailure_OpenStateTransition_2() {
        assertEquals("OPEN", delayedServiceCircuitBreaker.getState());
    }

    @Test
    void testFailure_OpenStateTransition_3() {
        assertEquals("Delayed service is down", monitoringService.delayedServiceResponse());
    }

    @Test
    void testFailure_OpenStateTransition_4() {
        assertEquals("Quick Service is working", monitoringService.quickServiceResponse());
    }

    @Test
    void testFailure_OpenStateTransition_5() {
        assertEquals("CLOSED", quickServiceCircuitBreaker.getState());
    }
}
