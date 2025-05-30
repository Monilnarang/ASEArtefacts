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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class VersionTest_Parameterized {

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
    void testGetIntVersion_5() {
        Assertions.assertEquals(Version.HIGHEST_PROTOCOL_VERSION, Version.getIntVersion("2.0.99"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSupportResponseAttachment_1to3")
    void testSupportResponseAttachment_1to3(String param1) {
        Assertions.assertTrue(Version.isSupportResponseAttachment(param1));
    }

    static public Stream<Arguments> Provider_testSupportResponseAttachment_1to3() {
        return Stream.of(arguments("2.0.2"), arguments("2.0.3"), arguments("2.0.99"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSupportResponseAttachment_4to12")
    void testSupportResponseAttachment_4to12(String param1) {
        Assertions.assertFalse(Version.isSupportResponseAttachment(param1));
    }

    static public Stream<Arguments> Provider_testSupportResponseAttachment_4to12() {
        return Stream.of(arguments("2.1.0"), arguments("2.0.0"), arguments("1.0.0"), arguments("3.0.0"), arguments("2.6.6-stable"), arguments("2.6.6.RC1"), arguments("2.0.contains"), arguments("version.string"), arguments("prefix2.0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetIntVersion_1to4_6to9")
    void testGetIntVersion_1to4_6to9(int param1, String param2) {
        Assertions.assertEquals(param1, Version.getIntVersion(param2));
    }

    static public Stream<Arguments> Provider_testGetIntVersion_1to4_6to9() {
        return Stream.of(arguments(2060100, "2.6.1"), arguments(2060101, "2.6.1.1"), arguments(2070001, "2.7.0.1"), arguments(2070000, "2.7.0"), arguments(2070000, "2.7.0.RC1"), arguments(2070000, "2.7.0-SNAPSHOT"), arguments(3000000, "3.0.0-SNAPSHOT"), arguments(3010000, "3.1.0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompare_1to5")
    void testCompare_1to5(int param1, String param2, String param3) {
        Assertions.assertEquals(param1, Version.compare(param2, param3));
    }

    static public Stream<Arguments> Provider_testCompare_1to5() {
        return Stream.of(arguments(0, "3.0.0", "3.0.0"), arguments(0, "3.0.0-SNAPSHOT", "3.0.0"), arguments(1, "3.0.0.1", "3.0.0"), arguments(1, "3.1.0", "3.0.0"), arguments(1, "3.1.2.3", "3.0.0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompare_6to7")
    void testCompare_6to7(int param1, String param2, String param3) {
        Assertions.assertEquals(-param1, Version.compare(param2, param3));
    }

    static public Stream<Arguments> Provider_testCompare_6to7() {
        return Stream.of(arguments(1, "2.9.9.9", "3.0.0"), arguments(1, "2.6.3.1", "3.0.0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsFramework270OrHigher_1to4")
    void testIsFramework270OrHigher_1to4(String param1) {
        Assertions.assertTrue(Version.isRelease270OrHigher(param1));
    }

    static public Stream<Arguments> Provider_testIsFramework270OrHigher_1to4() {
        return Stream.of(arguments("2.7.0"), arguments("2.7.0.1"), arguments("2.7.0.2"), arguments("2.8.0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsFramework270OrHigher_5to6")
    void testIsFramework270OrHigher_5to6(String param1) {
        Assertions.assertFalse(Version.isRelease270OrHigher(param1));
    }

    static public Stream<Arguments> Provider_testIsFramework270OrHigher_5to6() {
        return Stream.of(arguments("2.6.3"), arguments("2.6.3.1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsFramework263OrHigher_1to3_7to8")
    void testIsFramework263OrHigher_1to3_7to8(String param1) {
        Assertions.assertTrue(Version.isRelease263OrHigher(param1));
    }

    static public Stream<Arguments> Provider_testIsFramework263OrHigher_1to3_7to8() {
        return Stream.of(arguments("2.7.0"), arguments("2.7.0.1"), arguments("2.6.4"), arguments("2.6.3"), arguments("2.6.3.0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsFramework263OrHigher_4to6")
    void testIsFramework263OrHigher_4to6(String param1) {
        Assertions.assertFalse(Version.isRelease263OrHigher(param1));
    }

    static public Stream<Arguments> Provider_testIsFramework263OrHigher_4to6() {
        return Stream.of(arguments("2.6.2"), arguments("2.6.2.1"), arguments("2.6.1.1"));
    }
}
