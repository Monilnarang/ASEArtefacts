package org.dyn4j.dynamics;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class SettingsTest_Purified {

    private Settings settings = new Settings();

    @Before
    public void setup() {
        settings.reset();
    }

    @Test
    public void getSetWarmStartingEnabled_1_testMerged_1() {
        TestCase.assertTrue(settings.isWarmStartingEnabled());
    }

    @Test
    public void getSetWarmStartingEnabled_2() {
        settings.setWarmStartingEnabled(false);
        TestCase.assertFalse(settings.isWarmStartingEnabled());
    }
}
