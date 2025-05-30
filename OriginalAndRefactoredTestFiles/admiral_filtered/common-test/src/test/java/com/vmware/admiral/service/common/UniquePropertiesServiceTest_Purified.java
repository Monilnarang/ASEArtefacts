package com.vmware.admiral.service.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.common.test.BaseTestCase;
import com.vmware.admiral.host.HostInitCommonServiceConfig;
import com.vmware.admiral.service.common.UniquePropertiesService.UniquePropertiesRequest;
import com.vmware.admiral.service.common.UniquePropertiesService.UniquePropertiesState;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.test.VerificationHost;

public class UniquePropertiesServiceTest_Purified extends BaseTestCase {

    private UniquePropertiesState testState;

    @Before
    public void setup() throws Throwable {
        startServices(host);
        waitForServiceAvailability(UniquePropertiesService.FACTORY_LINK);
        waitForInitialBootServiceToBeSelfStopped(CommonInitialBootService.SELF_LINK);
        UniquePropertiesState state = new UniquePropertiesState();
        state.documentSelfLink = "test-service";
        testState = doPost(state, UniquePropertiesService.FACTORY_LINK);
    }

    private void startServices(VerificationHost host) {
        HostInitCommonServiceConfig.startServices(host);
    }

    @Test
    public void testPost_1() throws Throwable {
        assertNotNull(testState);
    }

    @Test
    public void testPost_2() throws Throwable {
        assertNotNull(testState.documentSelfLink);
    }

    @Test
    public void testPost_3() throws Throwable {
        assertNotNull(testState.uniqueProperties);
    }

    @Test
    public void testPost_4() throws Throwable {
        assertDocumentExists(testState.documentSelfLink);
    }
}
