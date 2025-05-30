package io.dropwizard.jetty;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;
import static org.assertj.core.api.Assertions.assertThat;

class NetUtilTest_Purified {

    private static boolean isTcpBacklogSettingReadable() {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
            try {
                File f = new File(NetUtil.TCP_BACKLOG_SETTING_LOCATION);
                return f.exists() && f.canRead();
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Test
    @EnabledIf("isTcpBacklogSettingReadable")
    @EnabledOnOs(OS.LINUX)
    void testOsTcpBackloc_1() {
        assertThat(NetUtil.getTcpBacklog(-1)).isNotEqualTo(-1);
    }

    @Test
    @EnabledIf("isTcpBacklogSettingReadable")
    @EnabledOnOs(OS.LINUX)
    void testOsTcpBackloc_2() {
    }
}
