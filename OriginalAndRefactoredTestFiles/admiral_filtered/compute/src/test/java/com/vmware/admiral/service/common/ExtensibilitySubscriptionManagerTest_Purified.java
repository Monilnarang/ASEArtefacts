package com.vmware.admiral.service.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import java.lang.reflect.Field;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.common.test.BaseTestCase;
import com.vmware.admiral.service.common.ConfigurationService.ConfigurationFactoryService;
import com.vmware.admiral.service.common.EventTopicService.EventTopicState;
import com.vmware.admiral.service.common.EventTopicService.TopicTaskInfo;
import com.vmware.admiral.service.common.ExtensibilitySubscriptionService.ExtensibilitySubscription;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.test.TestContext;
import com.vmware.xenon.common.test.TestRequestSender;

public class ExtensibilitySubscriptionManagerTest_Purified extends BaseTestCase {

    private TestRequestSender sender;

    private ExtensibilitySubscriptionManager manager;

    @Before
    public void setUp() throws Throwable {
        sender = host.getTestRequestSender();
        host.startServiceAndWait(ConfigurationFactoryService.class, ConfigurationFactoryService.SELF_LINK);
        manager = new ExtensibilitySubscriptionManager();
        host.startServiceAndWait(manager, ExtensibilitySubscriptionManager.SELF_LINK, null);
        host.startServiceAndWait(ExtensibilitySubscriptionFactoryService.class, ExtensibilitySubscriptionFactoryService.SELF_LINK);
        host.startFactory(new EventTopicService());
        waitForServiceAvailability(EventTopicService.FACTORY_LINK);
    }

    private Map<String, ExtensibilitySubscription> getExtensibilitySubscriptions() throws Exception {
        Field f = ExtensibilitySubscriptionManager.class.getDeclaredField("subscriptions");
        return getPrivateField(f, manager);
    }

    private Map<String, Duration> getTimeoutsPerStageAndSubstage() throws Exception {
        Field f = ExtensibilitySubscriptionManager.class.getDeclaredField("timeoutsPerTaskStageAndSubstage");
        return getPrivateField(f, manager);
    }

    private void verifyMapSize(@SuppressWarnings("rawtypes") Map map, int count) throws Throwable {
        waitFor(map, count);
    }

    private void waitFor(Map map, int count) {
        TestContext context = new TestContext(1, Duration.ofMinutes(1));
        schedule(map, count, context);
        context.await();
    }

    private void schedule(Map map, int count, TestContext context) {
        if (map.size() != count) {
            host.schedule(() -> {
                schedule(map, count, context);
                return;
            }, 3000, TimeUnit.MILLISECONDS);
        }
        context.completeIteration();
    }

    private ExtensibilitySubscription createExtensibilityState(String substage, String uri) {
        ExtensibilitySubscription state = new ExtensibilitySubscription();
        state.task = "task";
        state.stage = "stage";
        state.substage = substage;
        state.callbackReference = UriUtils.buildUri(uri);
        state.blocking = false;
        return state;
    }

    private EventTopicState createTopicState(String substage, String topicId) {
        EventTopicState state = new EventTopicState();
        state.topicTaskInfo = new TopicTaskInfo();
        state.topicTaskInfo.task = "task";
        state.topicTaskInfo.stage = "stage";
        state.topicTaskInfo.substage = substage;
        state.id = topicId;
        state.name = topicId;
        state.blockable = false;
        state.schema = "";
        return state;
    }

    @Test
    public void testNotInitialized_1_testMerged_1() throws Throwable {
        Operation result = sender.sendAndWait(Operation.createDelete(host, ExtensibilitySubscriptionManager.SELF_LINK));
        assertNotNull(result);
        assertEquals(Operation.STATUS_CODE_OK, result.getStatusCode());
    }

    @Test
    public void testNotInitialized_3_testMerged_2() throws Throwable {
        Field field = ExtensibilitySubscriptionManager.class.getDeclaredField("initialized");
        AtomicBoolean b = getPrivateField(field, manager);
        assertNotNull(b);
        assertFalse(b.get());
    }
}
