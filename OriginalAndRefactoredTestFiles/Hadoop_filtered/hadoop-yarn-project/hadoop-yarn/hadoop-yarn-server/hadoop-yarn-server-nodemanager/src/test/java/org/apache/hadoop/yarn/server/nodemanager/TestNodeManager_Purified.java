package org.apache.hadoop.yarn.server.nodemanager;

import static org.junit.Assert.fail;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnRuntimeException;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.container.ContainerEvent;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.container.ContainerImpl;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.container.ContainerState;
import org.apache.hadoop.yarn.server.nodemanager.nodelabels.NodeLabelsProvider;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestNodeManager_Purified {

    public static final class InvalidContainerExecutor extends DefaultContainerExecutor {

        @Override
        public void init(Context nmContext) throws IOException {
            throw new IOException("dummy executor init called");
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static int initCalls = 0;

    private static int preCalls = 0;

    private static int postCalls = 0;

    private static class DummyCSTListener1 implements ContainerStateTransitionListener {

        @Override
        public void init(Context context) {
            initCalls++;
        }

        @Override
        public void preTransition(ContainerImpl op, ContainerState beforeState, ContainerEvent eventToBeProcessed) {
            preCalls++;
        }

        @Override
        public void postTransition(ContainerImpl op, ContainerState beforeState, ContainerState afterState, ContainerEvent processedEvent) {
            postCalls++;
        }
    }

    private static class DummyCSTListener2 implements ContainerStateTransitionListener {

        @Override
        public void init(Context context) {
            initCalls++;
        }

        @Override
        public void preTransition(ContainerImpl op, ContainerState beforeState, ContainerEvent eventToBeProcessed) {
            preCalls++;
        }

        @Override
        public void postTransition(ContainerImpl op, ContainerState beforeState, ContainerState afterState, ContainerEvent processedEvent) {
            postCalls++;
        }
    }

    @Test
    public void testListenerInitialization_1() throws Exception {
        initCalls = 0;
        Assert.assertEquals(2, initCalls);
    }

    @Test
    public void testListenerInitialization_2() throws Exception {
        preCalls = 0;
        Assert.assertEquals(2, preCalls);
    }

    @Test
    public void testListenerInitialization_3() throws Exception {
        postCalls = 0;
        Assert.assertEquals(2, postCalls);
    }
}
