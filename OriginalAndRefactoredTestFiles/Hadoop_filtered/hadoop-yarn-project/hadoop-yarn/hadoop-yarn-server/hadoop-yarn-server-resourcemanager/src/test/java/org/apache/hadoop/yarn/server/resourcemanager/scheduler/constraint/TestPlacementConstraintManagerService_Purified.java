package org.apache.hadoop.yarn.server.resourcemanager.scheduler.constraint;

import static org.apache.hadoop.yarn.api.resource.PlacementConstraints.NODE;
import static org.apache.hadoop.yarn.api.resource.PlacementConstraints.RACK;
import static org.apache.hadoop.yarn.api.resource.PlacementConstraints.targetCardinality;
import static org.apache.hadoop.yarn.api.resource.PlacementConstraints.targetIn;
import static org.apache.hadoop.yarn.api.resource.PlacementConstraints.targetNotIn;
import static org.apache.hadoop.yarn.api.resource.PlacementConstraints.PlacementTargets.allocationTag;
import static org.apache.hadoop.yarn.api.resource.PlacementConstraints.PlacementTargets.nodeAttribute;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.hadoop.util.Sets;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.SchedulingRequest;
import org.apache.hadoop.yarn.api.resource.PlacementConstraint;
import org.apache.hadoop.yarn.api.resource.PlacementConstraint.And;
import org.apache.hadoop.yarn.api.resource.PlacementConstraints;
import org.apache.hadoop.yarn.server.utils.BuilderUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPlacementConstraintManagerService_Purified {

    private PlacementConstraintManagerService pcm;

    protected PlacementConstraintManagerService createPCM() {
        return new MemoryPlacementConstraintManager();
    }

    private ApplicationId appId1, appId2;

    private PlacementConstraint c1, c2, c3, c4;

    private Set<String> sourceTag1, sourceTag2, sourceTag3, sourceTag4;

    private Map<Set<String>, PlacementConstraint> constraintMap1, constraintMap2;

    @Before
    public void before() {
        this.pcm = createPCM();
        long ts = System.currentTimeMillis();
        appId1 = BuilderUtils.newApplicationId(ts, 123);
        appId2 = BuilderUtils.newApplicationId(ts, 234);
        c1 = PlacementConstraints.build(targetIn(NODE, allocationTag("hbase-m")));
        c2 = PlacementConstraints.build(targetIn(RACK, allocationTag("hbase-rs")));
        c3 = PlacementConstraints.build(targetNotIn(NODE, nodeAttribute("java", "1.8")));
        c4 = PlacementConstraints.build(targetCardinality(RACK, 2, 10, allocationTag("zk")));
        sourceTag1 = new HashSet<>(Arrays.asList("spark"));
        sourceTag2 = new HashSet<>(Arrays.asList("zk"));
        sourceTag3 = new HashSet<>(Arrays.asList("storm"));
        sourceTag4 = new HashSet<>(Arrays.asList("hbase-m", "hbase-sec"));
        constraintMap1 = Stream.of(new SimpleEntry<>(sourceTag1, c1), new SimpleEntry<>(sourceTag2, c2)).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        constraintMap2 = Stream.of(new SimpleEntry<>(sourceTag3, c4)).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    @Test
    public void testAddConstraint_1_testMerged_1() {
        Assert.assertEquals(0, pcm.getNumRegisteredApplications());
    }

    @Test
    public void testAddConstraint_3_testMerged_2() {
        pcm.addConstraint(appId1, sourceTag1, c1, false);
        pcm.registerApplication(appId1, new HashMap<>());
        Assert.assertEquals(1, pcm.getNumRegisteredApplications());
        Assert.assertEquals(0, pcm.getConstraints(appId1).size());
        pcm.addConstraint(appId1, sourceTag2, c3, false);
        Assert.assertEquals(2, pcm.getConstraints(appId1).size());
        pcm.addConstraint(appId1, sourceTag1, c2, false);
        Assert.assertEquals(c1, pcm.getConstraint(appId1, sourceTag1));
        Assert.assertNotEquals(c2, pcm.getConstraint(appId1, sourceTag1));
        pcm.addConstraint(appId1, sourceTag1, c2, true);
        Assert.assertEquals(c2, pcm.getConstraint(appId1, sourceTag1));
    }

    @Test
    public void testGlobalConstraints_1_testMerged_1() {
        Assert.assertEquals(0, pcm.getNumGlobalConstraints());
    }

    @Test
    public void testGlobalConstraints_2_testMerged_2() {
        pcm.addGlobalConstraint(sourceTag1, c1, false);
        Assert.assertEquals(1, pcm.getNumGlobalConstraints());
        Assert.assertNotNull(pcm.getGlobalConstraint(sourceTag1));
        pcm.addGlobalConstraint(sourceTag1, c2, false);
        Assert.assertEquals(c1, pcm.getGlobalConstraint(sourceTag1));
        Assert.assertNotEquals(c2, pcm.getGlobalConstraint(sourceTag1));
        pcm.addGlobalConstraint(sourceTag1, c2, true);
        Assert.assertEquals(c2, pcm.getGlobalConstraint(sourceTag1));
    }

    @Test
    public void testValidateConstraint_1() {
        Assert.assertTrue(pcm.validateConstraint(sourceTag1, c1));
    }

    @Test
    public void testValidateConstraint_2() {
        Assert.assertFalse(pcm.validateConstraint(sourceTag4, c1));
    }
}
