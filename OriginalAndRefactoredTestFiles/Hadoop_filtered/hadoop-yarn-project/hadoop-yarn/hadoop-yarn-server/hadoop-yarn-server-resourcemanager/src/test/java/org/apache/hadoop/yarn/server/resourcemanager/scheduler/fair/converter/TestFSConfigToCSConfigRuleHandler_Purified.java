package org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.converter;

import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.converter.FSConfigToCSConfigRuleHandler.DYNAMIC_MAX_ASSIGN;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.converter.FSConfigToCSConfigRuleHandler.MAX_CAPACITY_PERCENTAGE;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.converter.FSConfigToCSConfigRuleHandler.MAX_RESOURCES;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.converter.FSConfigToCSConfigRuleHandler.MIN_RESOURCES;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.converter.FSConfigToCSConfigRuleHandler.MAX_CHILD_CAPACITY;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.converter.FSConfigToCSConfigRuleHandler.MAX_CHILD_QUEUE_LIMIT;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.converter.FSConfigToCSConfigRuleHandler.QUEUE_AUTO_CREATE;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.converter.FSConfigToCSConfigRuleHandler.RESERVATION_SYSTEM;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.converter.FSConfigToCSConfigRuleHandler.FAIR_AS_DRF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;

public class TestFSConfigToCSConfigRuleHandler_Purified {

    private static final String ABORT = "abort";

    private static final String WARNING = "warning";

    private FSConfigToCSConfigRuleHandler ruleHandler;

    private DryRunResultHolder dryRunResultHolder;

    @Before
    public void setup() {
        dryRunResultHolder = new DryRunResultHolder();
    }

    private ConversionOptions createDryRunConversionOptions() {
        return new ConversionOptions(dryRunResultHolder, true);
    }

    private ConversionOptions createDefaultConversionOptions() {
        return new ConversionOptions(dryRunResultHolder, false);
    }

    private void expectAbort(VoidCall call) {
        expectAbort(call, UnsupportedPropertyException.class);
    }

    private void expectAbort(VoidCall call, Class<?> exceptionClass) {
        boolean exceptionThrown = false;
        Throwable thrown = null;
        try {
            call.apply();
        } catch (Throwable t) {
            thrown = t;
            exceptionThrown = true;
        }
        assertTrue("Exception was not thrown", exceptionThrown);
        assertEquals("Unexpected exception", exceptionClass, thrown.getClass());
    }

    @FunctionalInterface
    private interface VoidCall {

        void apply();
    }

    @Test
    public void testDryRunWarning_1() {
        assertEquals("Number of warnings", 2, dryRunResultHolder.getWarnings().size());
    }

    @Test
    public void testDryRunWarning_2() {
        assertEquals("Number of errors", 0, dryRunResultHolder.getErrors().size());
    }

    @Test
    public void testDryRunError_1() {
        assertEquals("Number of warnings", 0, dryRunResultHolder.getWarnings().size());
    }

    @Test
    public void testDryRunError_2() {
        assertEquals("Number of errors", 2, dryRunResultHolder.getErrors().size());
    }
}
