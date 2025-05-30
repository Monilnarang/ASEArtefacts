package com.vmware.admiral.compute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import com.vmware.admiral.compute.ContainerHostService.ContainerHostType;
import com.vmware.photon.controller.model.resources.ComputeService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ContainerHostUtilTest_Parameterized {

    @Test
    public void testIsSupportedVchVersion_1() {
        assertTrue(ContainerHostUtil.isSupportedVchVersion("1.0.0", null, null));
    }

    @Test
    public void testIsSupportedVchVersion_2() {
        assertTrue(ContainerHostUtil.isSupportedVchVersion("1.0.0", "1.0.0", null));
    }

    @Test
    public void testIsSupportedVchVersion_3() {
        assertTrue(ContainerHostUtil.isSupportedVchVersion("1.0.0", null, "1.1.0"));
    }

    @Test
    public void testIsSupportedVchVersion_4() {
        assertTrue(ContainerHostUtil.isSupportedVchVersion("1.0.0", "1.0.0", "1.1.0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsSupportedVchVersion_5to6")
    public void testIsSupportedVchVersion_5to6(String param1, String param2) {
        assertFalse(ContainerHostUtil.isSupportedVchVersion(param1, param2, "1.0.0"));
    }

    static public Stream<Arguments> Provider_testIsSupportedVchVersion_5to6() {
        return Stream.of(arguments("1.0.0", "1.0.0"), arguments("1.1.0", "1.0.0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsSupportedVchVersion_7to8")
    public void testIsSupportedVchVersion_7to8(double param1, String param2) {
        assertFalse(ContainerHostUtil.isSupportedVchVersion(param1, param2, null));
    }

    static public Stream<Arguments> Provider_testIsSupportedVchVersion_7to8() {
        return Stream.of(arguments(1.1, "1.1.1"), arguments(1.0, 2.0));
    }
}
