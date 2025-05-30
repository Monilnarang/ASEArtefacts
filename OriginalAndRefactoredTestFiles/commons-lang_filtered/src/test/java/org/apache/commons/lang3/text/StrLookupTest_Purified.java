package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrLookupTest_Purified extends AbstractLangTest {

    @Test
    public void testNoneLookup_1() {
        assertNull(StrLookup.noneLookup().lookup(null));
    }

    @Test
    public void testNoneLookup_2() {
        assertNull(StrLookup.noneLookup().lookup(""));
    }

    @Test
    public void testNoneLookup_3() {
        assertNull(StrLookup.noneLookup().lookup("any"));
    }

    @Test
    public void testSystemPropertiesLookup_1() {
        assertEquals(System.getProperty("os.name"), StrLookup.systemPropertiesLookup().lookup("os.name"));
    }

    @Test
    public void testSystemPropertiesLookup_2() {
        assertNull(StrLookup.systemPropertiesLookup().lookup(""));
    }

    @Test
    public void testSystemPropertiesLookup_3() {
        assertNull(StrLookup.systemPropertiesLookup().lookup("other"));
    }

    @Test
    public void testSystemPropertiesLookup_4() {
        assertNull(StrLookup.systemPropertiesLookup().lookup(null));
    }
}
