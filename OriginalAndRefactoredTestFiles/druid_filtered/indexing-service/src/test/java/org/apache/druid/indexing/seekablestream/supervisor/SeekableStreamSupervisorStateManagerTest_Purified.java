package org.apache.druid.indexing.seekablestream.supervisor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.druid.indexer.TaskState;
import org.apache.druid.indexing.overlord.supervisor.SupervisorStateManager;
import org.apache.druid.indexing.overlord.supervisor.SupervisorStateManager.BasicState;
import org.apache.druid.indexing.overlord.supervisor.SupervisorStateManagerConfig;
import org.apache.druid.indexing.seekablestream.common.StreamException;
import org.apache.druid.indexing.seekablestream.supervisor.SeekableStreamSupervisorStateManager.SeekableStreamExceptionEvent;
import org.apache.druid.indexing.seekablestream.supervisor.SeekableStreamSupervisorStateManager.SeekableStreamState;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SeekableStreamSupervisorStateManagerTest_Purified {

    private SeekableStreamSupervisorStateManager stateManager;

    private SupervisorStateManagerConfig config;

    private ObjectMapper defaultMapper;

    @Before
    public void setupTest() {
        config = new SupervisorStateManagerConfig(10);
        stateManager = new SeekableStreamSupervisorStateManager(config, false);
        defaultMapper = new DefaultObjectMapper();
    }

    @Test
    public void testHappyPath_1() {
        Assert.assertEquals(BasicState.PENDING, stateManager.getSupervisorState());
    }

    @Test
    public void testHappyPath_2() {
        Assert.assertEquals(BasicState.PENDING, stateManager.getSupervisorState().getBasicState());
    }

    @Test
    public void testHappyPath_3_testMerged_3() {
        stateManager.maybeSetState(SeekableStreamSupervisorStateManager.SeekableStreamState.CONNECTING_TO_STREAM);
        Assert.assertEquals(SeekableStreamState.CONNECTING_TO_STREAM, stateManager.getSupervisorState());
        Assert.assertEquals(BasicState.RUNNING, stateManager.getSupervisorState().getBasicState());
        stateManager.maybeSetState(SeekableStreamState.DISCOVERING_INITIAL_TASKS);
        Assert.assertEquals(SeekableStreamState.DISCOVERING_INITIAL_TASKS, stateManager.getSupervisorState());
        stateManager.maybeSetState(SeekableStreamState.CREATING_TASKS);
        Assert.assertEquals(SeekableStreamState.CREATING_TASKS, stateManager.getSupervisorState());
        stateManager.markRunFinished();
        Assert.assertEquals(BasicState.RUNNING, stateManager.getSupervisorState());
    }

    @Test
    public void testIdlePath_1() {
        Assert.assertEquals(BasicState.PENDING, stateManager.getSupervisorState());
    }

    @Test
    public void testIdlePath_2() {
        Assert.assertEquals(BasicState.PENDING, stateManager.getSupervisorState().getBasicState());
    }

    @Test
    public void testIdlePath_3_testMerged_3() {
        stateManager.maybeSetState(SeekableStreamSupervisorStateManager.SeekableStreamState.CONNECTING_TO_STREAM);
        Assert.assertEquals(SeekableStreamState.CONNECTING_TO_STREAM, stateManager.getSupervisorState());
        Assert.assertEquals(BasicState.RUNNING, stateManager.getSupervisorState().getBasicState());
        stateManager.maybeSetState(SeekableStreamState.DISCOVERING_INITIAL_TASKS);
        Assert.assertEquals(SeekableStreamState.DISCOVERING_INITIAL_TASKS, stateManager.getSupervisorState());
        stateManager.maybeSetState(SeekableStreamState.CREATING_TASKS);
        Assert.assertEquals(SeekableStreamState.CREATING_TASKS, stateManager.getSupervisorState());
        stateManager.markRunFinished();
        Assert.assertEquals(BasicState.RUNNING, stateManager.getSupervisorState());
        stateManager.maybeSetState(BasicState.IDLE);
        Assert.assertEquals(BasicState.IDLE, stateManager.getSupervisorState());
        Assert.assertEquals(BasicState.IDLE, stateManager.getSupervisorState().getBasicState());
    }

    @Test
    public void testStoppingPath_1() {
        Assert.assertEquals(BasicState.PENDING, stateManager.getSupervisorState());
    }

    @Test
    public void testStoppingPath_2() {
        Assert.assertEquals(BasicState.PENDING, stateManager.getSupervisorState().getBasicState());
    }

    @Test
    public void testStoppingPath_3_testMerged_3() {
        stateManager.maybeSetState(SeekableStreamSupervisorStateManager.SeekableStreamState.CONNECTING_TO_STREAM);
        Assert.assertEquals(SeekableStreamState.CONNECTING_TO_STREAM, stateManager.getSupervisorState());
        Assert.assertEquals(BasicState.RUNNING, stateManager.getSupervisorState().getBasicState());
        stateManager.maybeSetState(SeekableStreamState.DISCOVERING_INITIAL_TASKS);
        Assert.assertEquals(SeekableStreamState.DISCOVERING_INITIAL_TASKS, stateManager.getSupervisorState());
        stateManager.maybeSetState(BasicState.STOPPING);
        stateManager.maybeSetState(SeekableStreamState.CREATING_TASKS);
        Assert.assertEquals(BasicState.STOPPING, stateManager.getSupervisorState());
        Assert.assertEquals(BasicState.STOPPING, stateManager.getSupervisorState().getBasicState());
    }
}
