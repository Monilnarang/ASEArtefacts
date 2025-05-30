package org.apache.druid.segment.join;

import org.apache.druid.segment.column.ColumnHolder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JoinPrefixUtilsTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_isPrefixedBy_1() {
        Assert.assertTrue(JoinPrefixUtils.isPrefixedBy("foo", ""));
    }

    @Test
    public void test_isPrefixedBy_2() {
        Assert.assertTrue(JoinPrefixUtils.isPrefixedBy("foo", "f"));
    }

    @Test
    public void test_isPrefixedBy_3() {
        Assert.assertTrue(JoinPrefixUtils.isPrefixedBy("foo", "fo"));
    }

    @Test
    public void test_isPrefixedBy_4() {
        Assert.assertFalse(JoinPrefixUtils.isPrefixedBy("foo", "foo"));
    }
}
