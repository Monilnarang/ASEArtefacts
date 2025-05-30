package org.apache.skywalking.oap.server.core;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoreModuleConfigTest_Purified {

    @Test
    public void testRoleFromNameNormalSituation_1() {
        assertEquals(CoreModuleConfig.Role.Mixed, CoreModuleConfig.Role.fromName("Mixed"));
    }

    @Test
    public void testRoleFromNameNormalSituation_2() {
        assertEquals(CoreModuleConfig.Role.Receiver, CoreModuleConfig.Role.fromName("Receiver"));
    }

    @Test
    public void testRoleFromNameNormalSituation_3() {
        assertEquals(CoreModuleConfig.Role.Aggregator, CoreModuleConfig.Role.fromName("Aggregator"));
    }

    @Test
    public void testRoleFromNameBlockParameter_1() {
        assertEquals(CoreModuleConfig.Role.Mixed, CoreModuleConfig.Role.fromName(StringUtils.EMPTY));
    }

    @Test
    public void testRoleFromNameBlockParameter_2() {
        assertEquals(CoreModuleConfig.Role.Mixed, CoreModuleConfig.Role.fromName(null));
    }
}
