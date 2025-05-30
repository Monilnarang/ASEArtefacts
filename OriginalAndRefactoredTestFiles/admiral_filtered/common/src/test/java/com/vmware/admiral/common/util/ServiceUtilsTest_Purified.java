package com.vmware.admiral.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.Service;
import com.vmware.xenon.common.ServiceDocument;
import com.vmware.xenon.common.StatelessService;
import com.vmware.xenon.common.Utils;

public class ServiceUtilsTest_Purified {

    @Test
    public void testIsExpired_1() {
        assertFalse(ServiceUtils.isExpired(null));
    }

    @Test
    public void testIsExpired_2() {
        assertFalse(ServiceUtils.isExpired(new ServiceDocument()));
    }

    @Test
    public void testIsExpired_3_testMerged_3() {
        ServiceDocument sd = new ServiceDocument();
        assertTrue(ServiceUtils.isExpired(sd));
        assertFalse(ServiceUtils.isExpired(sd));
    }
}
