package org.apache.hadoop.yarn.server.resourcemanager.resourcetracker;

import static org.apache.hadoop.yarn.server.resourcemanager.MockNM.createMockNodeStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.NodeId;
import org.apache.hadoop.yarn.api.records.NodeState;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.conf.ConfigurationProvider;
import org.apache.hadoop.yarn.conf.ConfigurationProviderFactory;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.event.Dispatcher;
import org.apache.hadoop.yarn.event.EventHandler;
import org.apache.hadoop.yarn.event.InlineDispatcher;
import org.apache.hadoop.yarn.factories.RecordFactory;
import org.apache.hadoop.yarn.factory.providers.RecordFactoryProvider;
import org.apache.hadoop.yarn.server.api.protocolrecords.RegisterNodeManagerRequest;
import org.apache.hadoop.yarn.server.api.protocolrecords.RegisterNodeManagerResponse;
import org.apache.hadoop.yarn.server.api.records.NodeAction;
import org.apache.hadoop.yarn.server.api.records.NodeStatus;
import org.apache.hadoop.yarn.server.resourcemanager.MockNM;
import org.apache.hadoop.yarn.server.resourcemanager.MockRM;
import org.apache.hadoop.yarn.server.resourcemanager.ParameterizedSchedulerTestBase;
import org.apache.hadoop.yarn.server.resourcemanager.NMLivelinessMonitor;
import org.apache.hadoop.yarn.server.resourcemanager.nodelabels.RMNodeLabelsManager;
import org.apache.hadoop.yarn.server.resourcemanager.NodesListManager;
import org.apache.hadoop.yarn.server.resourcemanager.RMContextImpl;
import org.apache.hadoop.yarn.server.resourcemanager.ResourceManager.NodeEventDispatcher;
import org.apache.hadoop.yarn.server.resourcemanager.ResourceTrackerService;
import org.apache.hadoop.yarn.server.resourcemanager.rmnode.RMNode;
import org.apache.hadoop.yarn.server.resourcemanager.rmnode.RMNodeEvent;
import org.apache.hadoop.yarn.server.resourcemanager.rmnode.RMNodeEventType;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.AbstractYarnScheduler;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.event.SchedulerEventType;
import org.apache.hadoop.yarn.server.resourcemanager.security.NMTokenSecretManagerInRM;
import org.apache.hadoop.yarn.server.resourcemanager.security.RMContainerTokenSecretManager;
import org.apache.hadoop.yarn.util.resource.Resources;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestNMReconnect_Purified extends ParameterizedSchedulerTestBase {

    private static final RecordFactory recordFactory = RecordFactoryProvider.getRecordFactory(null);

    private List<RMNodeEvent> rmNodeEvents = new ArrayList<RMNodeEvent>();

    private Dispatcher dispatcher;

    private RMContextImpl context;

    public TestNMReconnect(SchedulerType type) throws IOException {
        super(type);
    }

    private class TestRMNodeEventDispatcher implements EventHandler<RMNodeEvent> {

        @Override
        public void handle(RMNodeEvent event) {
            rmNodeEvents.add(event);
        }
    }

    ResourceTrackerService resourceTrackerService;

    @Before
    public void setUp() {
        Configuration conf = new Configuration();
        dispatcher = new InlineDispatcher();
        dispatcher.register(RMNodeEventType.class, new TestRMNodeEventDispatcher());
        context = new RMContextImpl(dispatcher, null, null, null, null, null, null, null, null, null);
        dispatcher.register(SchedulerEventType.class, new InlineDispatcher.EmptyEventHandler());
        dispatcher.register(RMNodeEventType.class, new NodeEventDispatcher(context));
        NMLivelinessMonitor nmLivelinessMonitor = new NMLivelinessMonitor(dispatcher);
        nmLivelinessMonitor.init(conf);
        nmLivelinessMonitor.start();
        NodesListManager nodesListManager = new NodesListManager(context);
        nodesListManager.init(conf);
        RMContainerTokenSecretManager containerTokenSecretManager = new RMContainerTokenSecretManager(conf);
        containerTokenSecretManager.start();
        NMTokenSecretManagerInRM nmTokenSecretManager = new NMTokenSecretManagerInRM(conf);
        nmTokenSecretManager.start();
        resourceTrackerService = new ResourceTrackerService(context, nodesListManager, nmLivelinessMonitor, containerTokenSecretManager, nmTokenSecretManager);
        resourceTrackerService.init(conf);
        resourceTrackerService.start();
    }

    @After
    public void tearDown() {
        resourceTrackerService.stop();
    }

    @Test
    public void testReconnect_1() throws Exception {
        Assert.assertEquals(RMNodeEventType.STARTED, rmNodeEvents.get(0).getType());
    }

    @Test
    public void testReconnect_2_testMerged_2() throws Exception {
        rmNodeEvents.clear();
        Assert.assertEquals(RMNodeEventType.RECONNECTED, rmNodeEvents.get(0).getType());
    }
}
