package org.apache.hadoop.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.fs.impl.WeakReferenceThreadMap;
import org.apache.hadoop.test.AbstractHadoopTestBase;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;

public class TestWeakReferenceMap_Purified extends AbstractHadoopTestBase {

    public static final String FACTORY_STRING = "recreated %d";

    private WeakReferenceMap<Integer, String> referenceMap;

    private List<Integer> lostReferences;

    @Before
    public void setup() {
        lostReferences = new ArrayList<>();
        referenceMap = new WeakReferenceMap<>(this::factory, this::referenceLost);
    }

    private void referenceLost(Integer key) {
        lostReferences.add(key);
    }

    private void assertMapEntryEquals(int key, String val) {
        Assertions.assertThat(referenceMap.get(key)).describedAs("map enty of key %d", key).isEqualTo(val);
    }

    private void assertMapContainsKey(int key) {
        Assertions.assertThat(referenceMap.containsKey(key)).describedAs("map entry of key %d should be present", key).isTrue();
    }

    private void assertMapDoesNotContainKey(int key) {
        Assertions.assertThat(referenceMap.containsKey(key)).describedAs("map enty of key %d should be absent", key).isFalse();
    }

    private void assertMapSize(int size) {
        Assertions.assertThat(referenceMap.size()).describedAs("size of map %s", referenceMap).isEqualTo(size);
    }

    private void assertPruned(int count) {
        Assertions.assertThat(referenceMap.prune()).describedAs("number of entries pruned from map %s", referenceMap).isEqualTo(count);
    }

    private void assertLostCount(int count) {
        Assertions.assertThat(lostReferences).describedAs("number of entries lost from map %s", referenceMap).hasSize(count);
    }

    private String factory(Integer key) {
        return String.format(FACTORY_STRING, key);
    }

    @Test
    public void testBasicOperationsWithValidReferences_1() {
        assertMapSize(2);
    }

    @Test
    public void testBasicOperationsWithValidReferences_2() {
        assertMapContainsKey(1);
    }

    @Test
    public void testBasicOperationsWithValidReferences_3() {
        assertMapEntryEquals(1, "1");
    }

    @Test
    public void testBasicOperationsWithValidReferences_4() {
        assertMapEntryEquals(2, "2");
    }

    @Test
    public void testBasicOperationsWithValidReferences_5() {
        assertMapEntryEquals(1, "3");
    }

    @Test
    public void testBasicOperationsWithValidReferences_6() {
        assertMapDoesNotContainKey(1);
    }

    @Test
    public void testBasicOperationsWithValidReferences_7() {
        assertMapSize(1);
    }

    @Test
    public void testBasicOperationsWithValidReferences_8() {
        assertMapSize(0);
    }

    @Test
    public void testPruneNullEntries_1() {
        assertPruned(0);
    }

    @Test
    public void testPruneNullEntries_2() {
        assertMapSize(2);
    }

    @Test
    public void testPruneNullEntries_3() {
        assertPruned(1);
    }

    @Test
    public void testPruneNullEntries_4() {
        assertMapSize(1);
    }

    @Test
    public void testPruneNullEntries_5() {
        assertMapDoesNotContainKey(2);
    }

    @Test
    public void testPruneNullEntries_6() {
        assertMapEntryEquals(1, "1");
    }

    @Test
    public void testPruneNullEntries_7() {
        assertLostCount(1);
    }

    @Test
    public void testDemandCreateEntries_1() {
        assertMapEntryEquals(1, factory(1));
    }

    @Test
    public void testDemandCreateEntries_2() {
        assertMapSize(1);
    }

    @Test
    public void testDemandCreateEntries_3() {
        assertMapContainsKey(1);
    }

    @Test
    public void testDemandCreateEntries_4() {
        assertLostCount(0);
    }

    @Test
    public void testDemandCreateEntries_5() {
        assertMapEntryEquals(2, factory(2));
    }

    @Test
    public void testDemandCreateEntries_6() {
        assertLostCount(1);
    }
}
