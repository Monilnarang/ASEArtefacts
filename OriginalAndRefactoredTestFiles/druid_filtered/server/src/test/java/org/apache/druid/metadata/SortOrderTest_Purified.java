package org.apache.druid.metadata;

import org.apache.druid.error.DruidExceptionMatcher;
import org.junit.Assert;
import org.junit.Test;

public class SortOrderTest_Purified {

    @Test
    public void testAsc_1() {
        Assert.assertEquals(SortOrder.ASC, SortOrder.fromValue("asc"));
    }

    @Test
    public void testAsc_2() {
        Assert.assertEquals("ASC", SortOrder.fromValue("asc").toString());
    }

    @Test
    public void testAsc_3() {
        Assert.assertEquals(SortOrder.ASC, SortOrder.fromValue("ASC"));
    }

    @Test
    public void testAsc_4() {
        Assert.assertEquals("ASC", SortOrder.fromValue("ASC").toString());
    }

    @Test
    public void testAsc_5() {
        Assert.assertEquals(SortOrder.ASC, SortOrder.fromValue("AsC"));
    }

    @Test
    public void testAsc_6() {
        Assert.assertEquals("ASC", SortOrder.fromValue("AsC").toString());
    }

    @Test
    public void testDesc_1() {
        Assert.assertEquals(SortOrder.DESC, SortOrder.fromValue("desc"));
    }

    @Test
    public void testDesc_2() {
        Assert.assertEquals("DESC", SortOrder.fromValue("desc").toString());
    }

    @Test
    public void testDesc_3() {
        Assert.assertEquals(SortOrder.DESC, SortOrder.fromValue("DESC"));
    }

    @Test
    public void testDesc_4() {
        Assert.assertEquals("DESC", SortOrder.fromValue("DESC").toString());
    }

    @Test
    public void testDesc_5() {
        Assert.assertEquals(SortOrder.DESC, SortOrder.fromValue("DesC"));
    }

    @Test
    public void testDesc_6() {
        Assert.assertEquals("DESC", SortOrder.fromValue("DesC").toString());
    }
}
