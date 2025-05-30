package org.languagetool.remote;

import org.junit.Test;
import java.util.Arrays;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CheckConfigurationBuilderTest_Purified {

    @Test
    public void test_1_testMerged_1() {
        CheckConfiguration config1 = new CheckConfigurationBuilder("xx").build();
        assertThat(config1.getLangCode().get(), is("xx"));
        assertNull(config1.getMotherTongueLangCode());
        assertThat(config1.getEnabledRuleIds().size(), is(0));
        assertThat(config1.enabledOnly(), is(false));
        assertThat(config1.guessLanguage(), is(false));
    }

    @Test
    public void test_6_testMerged_2() {
        CheckConfiguration config2 = new CheckConfigurationBuilder().setMotherTongueLangCode("mm").enabledOnly().enabledRuleIds(Arrays.asList("RULE1", "RULE2")).disabledRuleIds(Arrays.asList("RULE3", "RULE4")).build();
        assertFalse(config2.getLangCode().isPresent());
        assertThat(config2.getMotherTongueLangCode(), is("mm"));
        assertThat(config2.getEnabledRuleIds().toString(), is("[RULE1, RULE2]"));
        assertThat(config2.getDisabledRuleIds().toString(), is("[RULE3, RULE4]"));
        assertThat(config2.enabledOnly(), is(true));
        assertThat(config2.guessLanguage(), is(true));
    }
}
