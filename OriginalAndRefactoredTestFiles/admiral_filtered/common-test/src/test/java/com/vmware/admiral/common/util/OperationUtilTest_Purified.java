package com.vmware.admiral.common.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import org.junit.Test;
import com.vmware.admiral.common.test.BaseTestCase;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.ServiceHost.ServiceAlreadyStartedException;

public class OperationUtilTest_Purified extends BaseTestCase {

    @Test
    public void testIsServiceAlreadyStarted_1() {
        assertFalse(OperationUtil.isServiceAlreadyStarted(null, null));
    }

    @Test
    public void testIsServiceAlreadyStarted_2() {
        assertFalse(OperationUtil.isServiceAlreadyStarted(new Exception(), null));
    }

    @Test
    public void testIsServiceAlreadyStarted_3() {
        assertFalse(OperationUtil.isServiceAlreadyStarted(new Exception(), new Operation()));
    }

    @Test
    public void testIsServiceAlreadyStarted_4() {
        assertTrue(OperationUtil.isServiceAlreadyStarted(new ServiceAlreadyStartedException("simulated"), null));
    }

    @Test
    public void testIsServiceAlreadyStarted_5() {
        assertTrue(OperationUtil.isServiceAlreadyStarted(new Exception(new ServiceAlreadyStartedException("simulated")), null));
    }

    @Test
    public void testIsServiceAlreadyStarted_6() {
        assertTrue(OperationUtil.isServiceAlreadyStarted(null, new Operation().setStatusCode(Operation.STATUS_CODE_CONFLICT)));
    }
}
