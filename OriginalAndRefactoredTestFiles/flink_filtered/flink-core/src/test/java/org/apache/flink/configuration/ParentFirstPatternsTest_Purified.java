package org.apache.flink.configuration;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.assertj.core.api.Assertions.assertThat;

class ParentFirstPatternsTest_Purified {

    private static final HashSet<String> PARENT_FIRST_PACKAGES = new HashSet<>(CoreOptions.ALWAYS_PARENT_FIRST_LOADER_PATTERNS.defaultValue());

    @Test
    void testAllCorePatterns_1() {
        assertThat(PARENT_FIRST_PACKAGES).contains("java.");
    }

    @Test
    void testAllCorePatterns_2() {
        assertThat(PARENT_FIRST_PACKAGES).contains("org.apache.flink.");
    }

    @Test
    void testAllCorePatterns_3() {
        assertThat(PARENT_FIRST_PACKAGES).contains("javax.annotation.");
    }

    @Test
    void testLoggersParentFirst_1() {
        assertThat(PARENT_FIRST_PACKAGES).contains("org.slf4j");
    }

    @Test
    void testLoggersParentFirst_2() {
        assertThat(PARENT_FIRST_PACKAGES).contains("org.apache.log4j");
    }

    @Test
    void testLoggersParentFirst_3() {
        assertThat(PARENT_FIRST_PACKAGES).contains("org.apache.logging");
    }

    @Test
    void testLoggersParentFirst_4() {
        assertThat(PARENT_FIRST_PACKAGES).contains("org.apache.commons.logging");
    }

    @Test
    void testLoggersParentFirst_5() {
        assertThat(PARENT_FIRST_PACKAGES).contains("ch.qos.logback");
    }
}
