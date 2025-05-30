package com.vmware.admiral.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.net.URI;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.common.ManagementUriParts;
import com.vmware.admiral.compute.container.GroupResourcePlacementService;
import com.vmware.admiral.compute.container.GroupResourcePlacementService.GroupResourcePlacementState;
import com.vmware.admiral.compute.container.GroupResourcePlacementService.ResourcePlacementReservationRequest;
import com.vmware.admiral.request.ReservationRemovalTaskService.ReservationRemovalTaskState;
import com.vmware.admiral.request.util.TestRequestStateFactory;
import com.vmware.admiral.service.common.ServiceTaskCallback;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.test.VerificationHost;

public class ReservationRemovalTaskServiceTest_Purified extends RequestBaseTest {

    @Override
    @Before
    public void setUp() throws Throwable {
        super.setUp();
    }

    private GroupResourcePlacementState makeResourcePlacementReservationRequest(int count, String descLink, GroupResourcePlacementState placementState, boolean expectFailure) throws Throwable {
        ResourcePlacementReservationRequest rsrvRequest = new ResourcePlacementReservationRequest();
        rsrvRequest.resourceCount = count;
        rsrvRequest.resourceDescriptionLink = descLink;
        URI requestReservationTaskURI = UriUtils.buildUri(host, ManagementUriParts.REQUEST_RESERVATION_TASKS);
        rsrvRequest.referer = requestReservationTaskURI.getPath();
        host.testStart(1);
        host.send(Operation.createPatch(UriUtils.buildUri(host, placementState.documentSelfLink)).setBody(rsrvRequest).setCompletion(expectFailure ? host.getExpectedFailureCompletion() : host.getCompletion()));
        host.testWait();
        setPrivateField(VerificationHost.class.getDeclaredField("referer"), host, UriUtils.buildUri(host, "test-client-send"));
        return getDocument(GroupResourcePlacementState.class, placementState.documentSelfLink);
    }

    @Test
    public void testReservationRemovalTaskLife_1_testMerged_1() throws Throwable {
        GroupResourcePlacementState placementState = doPost(TestRequestStateFactory.createGroupResourcePlacementState(), GroupResourcePlacementService.FACTORY_LINK);
        String descLink = containerDesc.documentSelfLink;
        int count = 5;
        boolean expectFailure = false;
        placementState = makeResourcePlacementReservationRequest(count, descLink, placementState, expectFailure);
        assertEquals(placementState.allocatedInstancesCount, count);
        placementState = getDocument(GroupResourcePlacementState.class, placementState.documentSelfLink);
        assertEquals(placementState.allocatedInstancesCount, 0);
    }

    @Test
    public void testReservationRemovalTaskLife_2() throws Throwable {
        ReservationRemovalTaskState task = new ReservationRemovalTaskState();
        task = doPost(task, ReservationRemovalTaskFactoryService.SELF_LINK);
        assertNotNull(task);
    }
}
