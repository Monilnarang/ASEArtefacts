package io.dropwizard.testing.junit5;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.app.TestApplication;
import io.dropwizard.testing.app.TestConfiguration;
import org.junit.jupiter.api.Test;
import static io.dropwizard.testing.ConfigOverride.config;
import static org.assertj.core.api.Assertions.assertThat;

class DropwizardAppExtensionResetConfigOverrideTest_Purified {

    private final DropwizardAppExtension<TestConfiguration> dropwizardAppExtension = new DropwizardAppExtension<>(TestApplication.class, "test-config.yaml", new ResourceConfigurationSourceProvider(), "app-rule-reset", config("app-rule-reset", "message", "A new way to say Hooray!"));

    @Test
    void test2_1() throws Exception {
        assertThat(System.getProperty("app-rule-reset.message")).isEqualTo("A new way to say Hooray!");
    }

    @Test
    void test2_2() throws Exception {
        assertThat(System.getProperty("app-rule-reset.extra")).isNull();
    }

    @Test
    void test2_3() throws Exception {
        assertThat(System.getProperty("app-rule-reset.message")).isEqualTo("A new way to say Hooray!");
    }

    @Test
    void test2_4_testMerged_4() throws Exception {
        System.setProperty("app-rule-reset.extra", "Some extra system property");
        assertThat(System.getProperty("app-rule-reset.extra")).isEqualTo("Some extra system property");
        assertThat(System.getProperty("app-rule-reset.message")).isNull();
    }
}
