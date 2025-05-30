package org.jboss.as.jsf.deployment;

import static org.jboss.as.controller.ModuleIdentifierUtil.canonicalModuleIdentifier;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class JSFModuleIdFactoryTestCase_Purified {

    private static final String API_MODULE = "jakarta.faces.api";

    private static final String IMPL_MODULE = "jakarta.faces.impl";

    private static final String INJECTION_MODULE = "org.jboss.as.jsf-injection";

    private static final JSFModuleIdFactory factory = JSFModuleIdFactory.getInstance();

    @Test
    public void computeSlotTest_1() {
        Assert.assertEquals("main", factory.computeSlot("main"));
    }

    @Test
    public void computeSlotTest_2() {
        Assert.assertEquals("main", factory.computeSlot(null));
    }

    @Test
    public void computeSlotTest_3() {
        Assert.assertEquals("main", factory.computeSlot(JsfVersionMarker.JSF_4_0));
    }

    @Test
    public void computeSlotTest_4() {
        Assert.assertEquals("myfaces2", factory.computeSlot("myfaces2"));
    }

    @Test
    public void validSlotTest_1() {
        Assert.assertTrue(factory.isValidJSFSlot("main"));
    }

    @Test
    public void validSlotTest_2() {
        Assert.assertTrue(factory.isValidJSFSlot("myfaces"));
    }

    @Test
    public void validSlotTest_3() {
        Assert.assertTrue(factory.isValidJSFSlot(JsfVersionMarker.JSF_4_0));
    }

    @Test
    public void validSlotTest_4() {
        Assert.assertFalse(factory.isValidJSFSlot(JsfVersionMarker.WAR_BUNDLES_JSF_IMPL));
    }

    @Test
    public void validSlotTest_5() {
        Assert.assertFalse(factory.isValidJSFSlot("bogus"));
    }

    @Test
    public void validSlotTest_6() {
        Assert.assertFalse(factory.isValidJSFSlot("bogus2"));
    }
}
