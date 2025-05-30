package com.vmware.admiral.compute.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.common.util.SubscriptionManager;
import com.vmware.admiral.common.util.SubscriptionManager.SubscriptionNotification;
import com.vmware.admiral.compute.container.ContainerService.ContainerState;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.Service.Action;
import com.vmware.xenon.common.ServiceDocument;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.test.TestContext;
import com.vmware.xenon.services.common.ServiceUriPaths;

public class SubscriptionManagementTest_Purified extends ComputeBaseTest {

    private List<SubscriptionNotification<ContainerState>> results;

    private ContainerState state;

    private ContainerState updatedState;

    private SubscriptionNotification<ContainerState> notification;

    private SubscriptionManager<ContainerState> subscriptionManager;

    private final String updatedTestId = "updatedTestId";

    private final String updatedTestValue = "updateTestValue";

    private TestContext activeContext;

    @Before
    public void setUp() throws Throwable {
        waitForServiceAvailability(ContainerDescriptionService.FACTORY_LINK);
        waitForServiceAvailability(CompositeDescriptionFactoryService.SELF_LINK);
        waitForServiceAvailability(ContainerFactoryService.SELF_LINK);
        state = new ContainerState();
        state.id = updatedTestId;
        state.image = updatedTestValue;
        state = doPost(state, ContainerFactoryService.SELF_LINK);
        results = Collections.synchronizedList(new ArrayList<>());
        subscriptionManager = new SubscriptionManager<>(host, host.getId(), state.documentSelfLink, ContainerState.class);
        waitForServiceAvailability(ServiceUriPaths.CORE_QUERY_TASKS);
        waitForServiceAvailability(state.documentSelfLink);
    }

    private String subscribe() throws Throwable {
        TestContext ctx = testCreate(2);
        subscriptionManager.setCompletionHandler((e) -> {
            if (e != null) {
                ctx.failIteration(e);
                return;
            }
            ctx.completeIteration();
        });
        final String[] subscriptionId = new String[1];
        subscriptionManager.start(handler(), (id) -> {
            subscriptionId[0] = id;
            ctx.completeIteration();
        });
        testWait(ctx);
        subscriptionManager.setCompletionHandler(null);
        return subscriptionId[0];
    }

    private void doOperation(Action action, Object state) throws Throwable {
        TestContext ctx = testCreate(2);
        this.activeContext = ctx;
        Operation op = new Operation();
        op.setUri(UriUtils.buildUri(host, this.state.documentSelfLink)).setAction(action).setBody(state).setCompletion(ctx.getCompletion());
        host.send(op);
        testWait(ctx);
    }

    private Consumer<SubscriptionNotification<ContainerState>> handler() {
        return (r) -> {
            results.clear();
            results.add(r);
            this.activeContext.completeIteration();
        };
    }

    private SubscriptionNotification<ContainerState> getNotification() {
        if (results.isEmpty()) {
            return null;
        }
        SubscriptionNotification<ContainerState> result = results.get(0);
        results.clear();
        return result;
    }

    @Test
    public void testNotificationSubscriptionUpdates_1_testMerged_1() throws Throwable {
        notification = getNotification();
        assertNotNull(notification);
        assertNotNull(notification.getResult());
        assertTrue(notification.isUpdate());
        updatedState = notification.getResult();
        assertEquals(updatedTestId, updatedState.id);
        assertEquals(updatedTestValue, updatedState.image);
        state.id = updatedTestValue + updatedTestId;
        assertEquals(updatedTestValue + updatedTestId, updatedState.id);
        assertTrue(notification.isDelete());
    }

    @Test
    public void testNotificationSubscriptionUpdates_12() throws Throwable {
        assertFalse(subscriptionManager.isSubscribed());
    }

    @Test
    public void testPollForUpdates_1_testMerged_1() throws Throwable {
        notification = getNotification();
        assertNotNull(notification.getResult());
        assertTrue(notification.isUpdate());
        updatedState = notification.getResult();
        assertEquals(updatedTestId, updatedState.id);
        assertEquals(updatedTestValue, updatedState.image);
        state.id = updatedTestValue + updatedTestId;
        assertEquals(updatedTestValue + updatedTestId, updatedState.id);
        assertTrue(notification.isDelete());
    }

    @Test
    public void testPollForUpdates_11() throws Throwable {
        subscriptionManager.close();
        subscriptionManager = new SubscriptionManager<>(host, host.getId(), state.documentSelfLink, ContainerState.class, true);
        assertFalse(subscriptionManager.isSubscribed());
    }
}
