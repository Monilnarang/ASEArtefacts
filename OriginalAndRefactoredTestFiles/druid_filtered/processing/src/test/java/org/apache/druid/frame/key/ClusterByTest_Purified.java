package org.apache.druid.frame.key;

import com.google.common.collect.ImmutableList;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.java.util.common.guava.Comparators;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;

public class ClusterByTest_Purified {

    @Test
    public void test_sortable_1() {
        Assert.assertFalse(new ClusterBy(ImmutableList.of(new KeyColumn("x", KeyOrder.NONE), new KeyColumn("y", KeyOrder.NONE)), 0).sortable());
    }

    @Test
    public void test_sortable_2() {
        Assert.assertTrue(new ClusterBy(ImmutableList.of(new KeyColumn("x", KeyOrder.ASCENDING), new KeyColumn("y", KeyOrder.ASCENDING)), 0).sortable());
    }

    @Test
    public void test_sortable_3() {
        Assert.assertTrue(new ClusterBy(Collections.emptyList(), 0).sortable());
    }
}
