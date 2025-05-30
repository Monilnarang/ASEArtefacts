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

public class ContainerHostUtilTest_Purified {

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

    @Test
    public void testIsSupportedVchVersion_5() {
        assertFalse(ContainerHostUtil.isSupportedVchVersion("1.0.0", null, "1.0.0"));
    }

    @Test
    public void testIsSupportedVchVersion_6() {
        assertFalse(ContainerHostUtil.isSupportedVchVersion("1.1.0", null, "1.0.0"));
    }

    @Test
    public void testIsSupportedVchVersion_7() {
        assertFalse(ContainerHostUtil.isSupportedVchVersion("1.1", "1.1.1", null));
    }

    @Test
    public void testIsSupportedVchVersion_8() {
        assertFalse(ContainerHostUtil.isSupportedVchVersion("1.0", "2.0", null));
    }
}
