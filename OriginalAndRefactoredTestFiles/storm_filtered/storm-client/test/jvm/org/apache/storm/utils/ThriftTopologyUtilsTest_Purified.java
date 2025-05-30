package org.apache.storm.utils;

import java.nio.ByteBuffer;
import java.util.Set;
import org.apache.storm.generated.Bolt;
import org.apache.storm.generated.ComponentCommon;
import org.apache.storm.generated.SpoutSpec;
import org.apache.storm.generated.StateSpoutSpec;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.hooks.BaseWorkerHook;
import org.apache.storm.shade.com.google.common.collect.ImmutableMap;
import org.apache.storm.shade.com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThriftTopologyUtilsTest_Purified {

    private StormTopology genereateStormTopology(boolean withWorkerHook) {
        ImmutableMap<String, SpoutSpec> spouts = ImmutableMap.of("spout-1", new SpoutSpec());
        ImmutableMap<String, Bolt> bolts = ImmutableMap.of("bolt-1", new Bolt());
        ImmutableMap<String, StateSpoutSpec> state_spouts = ImmutableMap.of();
        StormTopology stormTopology = new StormTopology(spouts, bolts, state_spouts);
        if (withWorkerHook) {
            BaseWorkerHook workerHook = new BaseWorkerHook();
            stormTopology.add_to_worker_hooks(ByteBuffer.wrap(Utils.javaSerialize(workerHook)));
        }
        return stormTopology;
    }

    @Test
    public void testIsWorkerHook_1() {
        assertFalse(ThriftTopologyUtils.isWorkerHook(StormTopology._Fields.BOLTS));
    }

    @Test
    public void testIsWorkerHook_2() {
        assertFalse(ThriftTopologyUtils.isWorkerHook(StormTopology._Fields.SPOUTS));
    }

    @Test
    public void testIsWorkerHook_3() {
        assertFalse(ThriftTopologyUtils.isWorkerHook(StormTopology._Fields.STATE_SPOUTS));
    }

    @Test
    public void testIsWorkerHook_4() {
        assertFalse(ThriftTopologyUtils.isWorkerHook(StormTopology._Fields.DEPENDENCY_JARS));
    }

    @Test
    public void testIsWorkerHook_5() {
        assertFalse(ThriftTopologyUtils.isWorkerHook(StormTopology._Fields.DEPENDENCY_ARTIFACTS));
    }

    @Test
    public void testIsWorkerHook_6() {
        assertTrue(ThriftTopologyUtils.isWorkerHook(StormTopology._Fields.WORKER_HOOKS));
    }

    @Test
    public void testIsDependencies_1() {
        assertFalse(ThriftTopologyUtils.isDependencies(StormTopology._Fields.BOLTS));
    }

    @Test
    public void testIsDependencies_2() {
        assertFalse(ThriftTopologyUtils.isDependencies(StormTopology._Fields.SPOUTS));
    }

    @Test
    public void testIsDependencies_3() {
        assertFalse(ThriftTopologyUtils.isDependencies(StormTopology._Fields.STATE_SPOUTS));
    }

    @Test
    public void testIsDependencies_4() {
        assertFalse(ThriftTopologyUtils.isDependencies(StormTopology._Fields.WORKER_HOOKS));
    }

    @Test
    public void testIsDependencies_5() {
        assertTrue(ThriftTopologyUtils.isDependencies(StormTopology._Fields.DEPENDENCY_JARS));
    }

    @Test
    public void testIsDependencies_6() {
        assertTrue(ThriftTopologyUtils.isDependencies(StormTopology._Fields.DEPENDENCY_ARTIFACTS));
    }
}
