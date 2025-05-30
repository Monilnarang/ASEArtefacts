package org.jboss.as.jsf.deployment;

import static org.jboss.as.controller.ModuleIdentifierUtil.canonicalModuleIdentifier;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JSFModuleIdFactoryTestCase_Parameterized {

    private static final String API_MODULE = "jakarta.faces.api";

    private static final String IMPL_MODULE = "jakarta.faces.impl";

    private static final String INJECTION_MODULE = "org.jboss.as.jsf-injection";

    private static final JSFModuleIdFactory factory = JSFModuleIdFactory.getInstance();

    @Test
    public void computeSlotTest_2() {
        Assert.assertEquals("main", factory.computeSlot(null));
    }

    @Test
    public void computeSlotTest_3() {
        Assert.assertEquals("main", factory.computeSlot(JsfVersionMarker.JSF_4_0));
    }

    @Test
    public void validSlotTest_3() {
        Assert.assertTrue(factory.isValidJSFSlot(JsfVersionMarker.JSF_4_0));
    }

    @Test
    public void validSlotTest_4() {
        Assert.assertFalse(factory.isValidJSFSlot(JsfVersionMarker.WAR_BUNDLES_JSF_IMPL));
    }

    @ParameterizedTest
    @MethodSource("Provider_computeSlotTest_1_4")
    public void computeSlotTest_1_4(String param1, String param2) {
        Assert.assertEquals(param1, factory.computeSlot(param2));
    }

    static public Stream<Arguments> Provider_computeSlotTest_1_4() {
        return Stream.of(arguments("main", "main"), arguments("myfaces2", "myfaces2"));
    }

    @ParameterizedTest
    @MethodSource("Provider_validSlotTest_1to2")
    public void validSlotTest_1to2(String param1) {
        Assert.assertTrue(factory.isValidJSFSlot(param1));
    }

    static public Stream<Arguments> Provider_validSlotTest_1to2() {
        return Stream.of(arguments("main"), arguments("myfaces"));
    }

    @ParameterizedTest
    @MethodSource("Provider_validSlotTest_5to6")
    public void validSlotTest_5to6(String param1) {
        Assert.assertFalse(factory.isValidJSFSlot(param1));
    }

    static public Stream<Arguments> Provider_validSlotTest_5to6() {
        return Stream.of(arguments("bogus"), arguments("bogus2"));
    }
}
