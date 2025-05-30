package io.undertow.util;

import io.undertow.testutils.category.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class NodeStatusCodesTestCase_Purified {

    @Test
    public void testUnknownCode_1() {
        Assert.assertEquals("Unexpected reason phrase", "Unknown", StatusCodes.getReason(-1));
    }

    @Test
    public void testUnknownCode_2() {
        Assert.assertEquals("Unexpected reason phrase", "Unknown", StatusCodes.getReason(999));
    }

    @Test
    public void testUnknownCode_3() {
        Assert.assertEquals("Unexpected reason phrase", "Unknown", StatusCodes.getReason(735));
    }

    @Test
    public void testUnknownCode_4() {
        Assert.assertEquals("Unexpected reason phrase", "Unknown", StatusCodes.getReason(Integer.MAX_VALUE));
    }

    @Test
    public void testUnknownCode_5() {
        Assert.assertEquals("Unexpected reason phrase", "Unknown", StatusCodes.getReason(Integer.MIN_VALUE));
    }
}
