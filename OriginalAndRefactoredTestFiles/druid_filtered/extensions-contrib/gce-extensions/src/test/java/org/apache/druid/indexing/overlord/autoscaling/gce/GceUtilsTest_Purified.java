package org.apache.druid.indexing.overlord.autoscaling.gce;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class GceUtilsTest_Purified {

    @Test
    public void testExtractNameFromInstance_1() {
        String instance0 = "https://www.googleapis.com/compute/v1/projects/X/zones/Y/instances/name-of-the-thing";
        Assert.assertEquals("name-of-the-thing", GceUtils.extractNameFromInstance(instance0));
    }

    @Test
    public void testExtractNameFromInstance_2() {
        String instance1 = "https://www.googleapis.com/compute/v1/projects/X/zones/Y/instances/";
        Assert.assertEquals("", GceUtils.extractNameFromInstance(instance1));
    }

    @Test
    public void testExtractNameFromInstance_3() {
        String instance2 = "name-of-the-thing";
        Assert.assertEquals("name-of-the-thing", GceUtils.extractNameFromInstance(instance2));
    }

    @Test
    public void testExtractNameFromInstance_4() {
        String instance3 = null;
        Assert.assertEquals(null, GceUtils.extractNameFromInstance(instance3));
    }

    @Test
    public void testExtractNameFromInstance_5() {
        String instance4 = "";
        Assert.assertEquals("", GceUtils.extractNameFromInstance(instance4));
    }
}
