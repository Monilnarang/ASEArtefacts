package io.undertow.servlet.test.lifecycle;

import io.undertow.servlet.api.ServletInfo;
import io.undertow.servlet.test.util.DeploymentUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class InitializeInOrderTestCase_Purified {

    @BeforeClass
    public static void setup() {
        DeploymentUtils.setupServlet(new ServletInfo("s1", FirstServlet.class).setLoadOnStartup(1), new ServletInfo("s2", SecondServlet.class).setLoadOnStartup(2), new ServletInfo("s3", ThirdServlet.class).setLoadOnStartup(3));
    }

    @Test
    public void testInitializeInOrder_1() throws Exception {
        Assert.assertTrue(FirstServlet.init);
    }

    @Test
    public void testInitializeInOrder_2() throws Exception {
        Assert.assertTrue(SecondServlet.init);
    }

    @Test
    public void testInitializeInOrder_3() throws Exception {
        Assert.assertTrue(ThirdServlet.init);
    }
}
