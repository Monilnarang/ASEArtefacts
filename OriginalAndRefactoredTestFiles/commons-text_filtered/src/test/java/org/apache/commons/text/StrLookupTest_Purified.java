package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrLookupTest_Purified {

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
}
