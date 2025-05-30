package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.configuration2.ex.ConversionException;
import org.apache.commons.configuration2.io.ConfigurationLogger;
import org.junit.jupiter.api.Test;

public abstract class TestAbstractConfiguration_Purified {

    protected abstract AbstractConfiguration getConfiguration();

    protected abstract AbstractConfiguration getEmptyConfiguration();

    @Test
    public void testIsEmpty_1() {
        final Configuration config = getConfiguration();
        assertFalse(config.isEmpty());
    }

    @Test
    public void testIsEmpty_2() {
        assertTrue(getEmptyConfiguration().isEmpty());
    }
}
