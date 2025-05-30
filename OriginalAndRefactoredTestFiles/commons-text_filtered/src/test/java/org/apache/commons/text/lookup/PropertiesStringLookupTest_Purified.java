package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PropertiesStringLookupTest_Purified {

    private static final Path CURRENT_PATH = Paths.get(StringUtils.EMPTY);

    private static final String DOC_RELATIVE = "src/test/resources/org/apache/commons/text/document.properties";

    private static final String DOC_ROOT = "/foo.txt";

    private static final String KEY = "mykey";

    private static final String KEY_RELATIVE = PropertiesStringLookup.toPropertyKey(DOC_RELATIVE, KEY);

    private static final String KEY_ROOT = PropertiesStringLookup.toPropertyKey(DOC_ROOT, KEY);

    private static final Path[] NULL_PATH_ARRAY = null;

    @Test
    public void testNull_1() {
        Assertions.assertNull(PropertiesStringLookup.INSTANCE.lookup(null));
    }

    @Test
    public void testNull_2() {
        Assertions.assertNull(new PropertiesStringLookup().lookup(null));
    }

    @Test
    public void testNull_3() {
        Assertions.assertNull(new PropertiesStringLookup(NULL_PATH_ARRAY).lookup(null));
    }

    @Test
    public void testNull_4() {
        Assertions.assertNull(new PropertiesStringLookup(CURRENT_PATH).lookup(null));
    }

    @Test
    public void testToString_1() {
        Assertions.assertFalse(PropertiesStringLookup.INSTANCE.toString().isEmpty());
    }

    @Test
    public void testToString_2() {
        Assertions.assertFalse(new PropertiesStringLookup().toString().isEmpty());
    }

    @Test
    public void testToString_3() {
        Assertions.assertFalse(new PropertiesStringLookup(NULL_PATH_ARRAY).toString().isEmpty());
    }

    @Test
    public void testToString_4() {
        Assertions.assertFalse(new PropertiesStringLookup(CURRENT_PATH).toString().isEmpty());
    }
}
