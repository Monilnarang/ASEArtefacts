package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class SystemUtilsTest_Purified extends AbstractLangTest {

    private boolean getIS_JAVA(final int version) throws Exception {
        return SystemUtils.class.getField("IS_JAVA_" + version).getBoolean(null);
    }

    public int getLastSupportedJavaVersion() {
        int lastSupportedVersion = 0;
        for (final Field field : SystemUtils.class.getFields()) {
            if (field.getName().matches("IS_JAVA_\\d+")) {
                lastSupportedVersion = Math.max(lastSupportedVersion, Integer.parseInt(field.getName().substring(8)));
            }
        }
        return lastSupportedVersion;
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new SystemUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = SystemUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(SystemUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(SystemUtils.class.getModifiers()));
    }

    @Test
    public void testGetUserName_1() {
        assertEquals(System.getProperty("user.name"), SystemUtils.getUserName());
    }

    @Test
    public void testGetUserName_2() {
        assertEquals(System.getProperty("user.name", "foo"), SystemUtils.getUserName("foo"));
    }
}
