package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestStrictConfigurationComparator_Purified {

    protected ConfigurationComparator comparator = new StrictConfigurationComparator();

    protected Configuration configuration = new BaseConfiguration();

    @Test
    public void testCompare_1() {
        assertTrue(comparator.compare(configuration, configuration));
    }

    @Test
    public void testCompare_2() {
        assertTrue(comparator.compare(configuration, configuration));
    }

    @Test
    public void testCompare_3_testMerged_3() {
        configuration.setProperty("one", "1");
        configuration.setProperty("two", "2");
        configuration.setProperty("three", "3");
        final Configuration other = new BaseConfiguration();
        assertFalse(comparator.compare(configuration, other));
        other.setProperty("one", "1");
        other.setProperty("two", "2");
        other.setProperty("three", "3");
        assertTrue(comparator.compare(configuration, other));
    }

    @Test
    public void testCompareNull_1() {
        assertTrue(comparator.compare(null, null));
    }

    @Test
    public void testCompareNull_2() {
        assertFalse(comparator.compare(configuration, null));
    }

    @Test
    public void testCompareNull_3() {
        assertFalse(comparator.compare(null, configuration));
    }
}
