package org.apache.dubbo.common.version;

import org.apache.dubbo.common.Version;
import org.apache.dubbo.common.constants.CommonConstants;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VersionTest_Purified {

    private static Class<?> reloadVersionClass() throws ClassNotFoundException {
        ClassLoader originClassLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader classLoader = new ClassLoader(originClassLoader) {

            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if ("org.apache.dubbo.common.Version".equals(name)) {
                    return findClass(name);
                }
                return super.loadClass(name);
            }

            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                try {
                    byte[] bytes = loadClassData(name);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (Exception e) {
                    return getParent().loadClass(name);
                }
            }

            public byte[] loadClassData(String className) throws IOException {
                className = className.replaceAll("\\.", "/");
                String path = Version.class.getProtectionDomain().getCodeSource().getLocation().getPath() + className + ".class";
                FileInputStream fileInputStream;
                byte[] classBytes;
                fileInputStream = new FileInputStream(path);
                int length = fileInputStream.available();
                classBytes = new byte[length];
                fileInputStream.read(classBytes);
                fileInputStream.close();
                return classBytes;
            }

            @Override
            public Enumeration<URL> getResources(String name) throws IOException {
                if (name.equals(CommonConstants.DUBBO_VERSIONS_KEY + "/dubbo-common")) {
                    return super.getResources("META-INF/test-versions/dubbo-common");
                }
                return super.getResources(name);
            }
        };
        return classLoader.loadClass("org.apache.dubbo.common.Version");
    }

    @Test
    void testSupportResponseAttachment_1() {
        Assertions.assertTrue(Version.isSupportResponseAttachment("2.0.2"));
    }

    @Test
    void testSupportResponseAttachment_2() {
        Assertions.assertTrue(Version.isSupportResponseAttachment("2.0.3"));
    }

    @Test
    void testSupportResponseAttachment_3() {
        Assertions.assertTrue(Version.isSupportResponseAttachment("2.0.99"));
    }

    @Test
    void testSupportResponseAttachment_4() {
        Assertions.assertFalse(Version.isSupportResponseAttachment("2.1.0"));
    }

    @Test
    void testSupportResponseAttachment_5() {
        Assertions.assertFalse(Version.isSupportResponseAttachment("2.0.0"));
    }

    @Test
    void testSupportResponseAttachment_6() {
        Assertions.assertFalse(Version.isSupportResponseAttachment("1.0.0"));
    }

    @Test
    void testSupportResponseAttachment_7() {
        Assertions.assertFalse(Version.isSupportResponseAttachment("3.0.0"));
    }

    @Test
    void testSupportResponseAttachment_8() {
        Assertions.assertFalse(Version.isSupportResponseAttachment("2.6.6-stable"));
    }

    @Test
    void testSupportResponseAttachment_9() {
        Assertions.assertFalse(Version.isSupportResponseAttachment("2.6.6.RC1"));
    }

    @Test
    void testSupportResponseAttachment_10() {
        Assertions.assertFalse(Version.isSupportResponseAttachment("2.0.contains"));
    }

    @Test
    void testSupportResponseAttachment_11() {
        Assertions.assertFalse(Version.isSupportResponseAttachment("version.string"));
    }

    @Test
    void testSupportResponseAttachment_12() {
        Assertions.assertFalse(Version.isSupportResponseAttachment("prefix2.0"));
    }

    @Test
    void testGetIntVersion_1() {
        Assertions.assertEquals(2060100, Version.getIntVersion("2.6.1"));
    }

    @Test
    void testGetIntVersion_2() {
        Assertions.assertEquals(2060101, Version.getIntVersion("2.6.1.1"));
    }

    @Test
    void testGetIntVersion_3() {
        Assertions.assertEquals(2070001, Version.getIntVersion("2.7.0.1"));
    }

    @Test
    void testGetIntVersion_4() {
        Assertions.assertEquals(2070000, Version.getIntVersion("2.7.0"));
    }

    @Test
    void testGetIntVersion_5() {
        Assertions.assertEquals(Version.HIGHEST_PROTOCOL_VERSION, Version.getIntVersion("2.0.99"));
    }

    @Test
    void testGetIntVersion_6() {
        Assertions.assertEquals(2070000, Version.getIntVersion("2.7.0.RC1"));
    }

    @Test
    void testGetIntVersion_7() {
        Assertions.assertEquals(2070000, Version.getIntVersion("2.7.0-SNAPSHOT"));
    }

    @Test
    void testGetIntVersion_8() {
        Assertions.assertEquals(3000000, Version.getIntVersion("3.0.0-SNAPSHOT"));
    }

    @Test
    void testGetIntVersion_9() {
        Assertions.assertEquals(3010000, Version.getIntVersion("3.1.0"));
    }

    @Test
    void testCompare_1() {
        Assertions.assertEquals(0, Version.compare("3.0.0", "3.0.0"));
    }

    @Test
    void testCompare_2() {
        Assertions.assertEquals(0, Version.compare("3.0.0-SNAPSHOT", "3.0.0"));
    }

    @Test
    void testCompare_3() {
        Assertions.assertEquals(1, Version.compare("3.0.0.1", "3.0.0"));
    }

    @Test
    void testCompare_4() {
        Assertions.assertEquals(1, Version.compare("3.1.0", "3.0.0"));
    }

    @Test
    void testCompare_5() {
        Assertions.assertEquals(1, Version.compare("3.1.2.3", "3.0.0"));
    }

    @Test
    void testCompare_6() {
        Assertions.assertEquals(-1, Version.compare("2.9.9.9", "3.0.0"));
    }

    @Test
    void testCompare_7() {
        Assertions.assertEquals(-1, Version.compare("2.6.3.1", "3.0.0"));
    }

    @Test
    void testIsFramework270OrHigher_1() {
        Assertions.assertTrue(Version.isRelease270OrHigher("2.7.0"));
    }

    @Test
    void testIsFramework270OrHigher_2() {
        Assertions.assertTrue(Version.isRelease270OrHigher("2.7.0.1"));
    }

    @Test
    void testIsFramework270OrHigher_3() {
        Assertions.assertTrue(Version.isRelease270OrHigher("2.7.0.2"));
    }

    @Test
    void testIsFramework270OrHigher_4() {
        Assertions.assertTrue(Version.isRelease270OrHigher("2.8.0"));
    }

    @Test
    void testIsFramework270OrHigher_5() {
        Assertions.assertFalse(Version.isRelease270OrHigher("2.6.3"));
    }

    @Test
    void testIsFramework270OrHigher_6() {
        Assertions.assertFalse(Version.isRelease270OrHigher("2.6.3.1"));
    }

    @Test
    void testIsFramework263OrHigher_1() {
        Assertions.assertTrue(Version.isRelease263OrHigher("2.7.0"));
    }

    @Test
    void testIsFramework263OrHigher_2() {
        Assertions.assertTrue(Version.isRelease263OrHigher("2.7.0.1"));
    }

    @Test
    void testIsFramework263OrHigher_3() {
        Assertions.assertTrue(Version.isRelease263OrHigher("2.6.4"));
    }

    @Test
    void testIsFramework263OrHigher_4() {
        Assertions.assertFalse(Version.isRelease263OrHigher("2.6.2"));
    }

    @Test
    void testIsFramework263OrHigher_5() {
        Assertions.assertFalse(Version.isRelease263OrHigher("2.6.2.1"));
    }

    @Test
    void testIsFramework263OrHigher_6() {
        Assertions.assertFalse(Version.isRelease263OrHigher("2.6.1.1"));
    }

    @Test
    void testIsFramework263OrHigher_7() {
        Assertions.assertTrue(Version.isRelease263OrHigher("2.6.3"));
    }

    @Test
    void testIsFramework263OrHigher_8() {
        Assertions.assertTrue(Version.isRelease263OrHigher("2.6.3.0"));
    }
}
