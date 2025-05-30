package org.apache.hadoop.yarn.util;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestYarnVersionInfo_Purified {

    @Test
    void versionInfoGenerated_1() throws IOException {
        assertNotEquals("Unknown", YarnVersionInfo.getVersion(), "getVersion returned Unknown");
    }

    @Test
    void versionInfoGenerated_2() throws IOException {
        assertNotEquals("Unknown", YarnVersionInfo.getUser(), "getUser returned Unknown");
    }

    @Test
    void versionInfoGenerated_3() throws IOException {
        assertNotEquals("Unknown", YarnVersionInfo.getSrcChecksum(), "getSrcChecksum returned Unknown");
    }

    @Test
    void versionInfoGenerated_4() throws IOException {
        assertNotNull(YarnVersionInfo.getUrl(), "getUrl returned null");
    }

    @Test
    void versionInfoGenerated_5() throws IOException {
        assertNotNull(YarnVersionInfo.getRevision(), "getRevision returned null");
    }

    @Test
    void versionInfoGenerated_6() throws IOException {
        assertNotNull(YarnVersionInfo.getBranch(), "getBranch returned null");
    }

    @Test
    void versionInfoGenerated_7() throws IOException {
        assertTrue(YarnVersionInfo.getBuildVersion().contains("source checksum"), "getBuildVersion check doesn't contain: source checksum");
    }
}
