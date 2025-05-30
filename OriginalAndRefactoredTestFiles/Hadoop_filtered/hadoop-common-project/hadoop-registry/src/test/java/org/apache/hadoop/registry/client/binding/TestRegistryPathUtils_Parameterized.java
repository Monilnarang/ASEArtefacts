package org.apache.hadoop.registry.client.binding;

import static org.apache.hadoop.registry.client.binding.RegistryPathUtils.*;
import java.io.IOException;
import java.util.List;
import org.apache.hadoop.fs.PathNotFoundException;
import org.apache.hadoop.registry.client.exceptions.InvalidPathnameException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestRegistryPathUtils_Parameterized extends Assert {

    public static final String EURO = "\u20AC";

    protected void assertConverted(String expected, String in) {
        String out = RegistryPathUtils.encodeForRegistry(in);
        assertEquals("Conversion of " + in, expected, out);
    }

    private static void assertCreatedPathEquals(String expected, String base, String path) throws IOException {
        String fullPath = createFullPath(base, path);
        assertEquals("\"" + base + "\" + \"" + path + "\" =\"" + fullPath + "\"", expected, fullPath);
    }

    private void assertValidPath(String path) throws InvalidPathnameException {
        validateZKPath(path);
    }

    private void assertInvalidPath(String path) throws InvalidPathnameException {
        try {
            validateElementsAsDNS(path);
            fail("path considered valid: " + path);
        } catch (InvalidPathnameException expected) {
        }
    }

    @ParameterizedTest
    @MethodSource("Provider_testPaths_1_1to2_2to13")
    public void testPaths_1_1to2_2to13(String param1, String param2, String param3) throws Throwable {
        assertCreatedPathEquals(param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testPaths_1_1to2_2to13() {
        return Stream.of(arguments("/", "/", ""), arguments("/", "", ""), arguments("/", "", "/"), arguments("/", "/", "/"), arguments("/a", "/a", ""), arguments("/a", "/", "a"), arguments("/a/b", "/a", "b"), arguments("/a/b", "/a/", "b"), arguments("/a/b", "/a", "/b"), arguments("/a/b", "/a", "/b/"), arguments("/a", "/a", "/"), arguments("/alice", "/", "/alice"), arguments("/alice", "/alice", "/"), arguments("/", "", ""), arguments("/yarn/registry/users/hadoop/org-apache-hadoop", "/yarn/registry", "users/hadoop/org-apache-hadoop/"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetUserFromPath_1to3")
    public void testGetUserFromPath_1to3(String param1, String param2) throws Exception {
        assertEquals(param1, RegistryPathUtils.getUsername(param2));
    }

    static public Stream<Arguments> Provider_testGetUserFromPath_1to3() {
        return Stream.of(arguments("bob", "/registry/users/bob/services/yarn-service/test1/"), arguments("bob-dev", "/registry/users/bob-dev/services/yarn-service/test1"), arguments("bob.dev", "/registry/users/bob.dev/services/yarn-service/test1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSplittingEmpty_1to3")
    public void testSplittingEmpty_1to3(int param1, String param2) throws Throwable {
        assertEquals(param1, split(param2).size());
    }

    static public Stream<Arguments> Provider_testSplittingEmpty_1to3() {
        return Stream.of(arguments(0, ""), arguments(0, "/"), arguments(0, "///"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParentOf_1to4")
    public void testParentOf_1to4(String param1, String param2) throws Throwable {
        assertEquals(param1, parentOf(param2));
    }

    static public Stream<Arguments> Provider_testParentOf_1to4() {
        return Stream.of(arguments("/", "/a"), arguments("/", "/a/"), arguments("/a", "/a/b"), arguments("/a/b", "/a/b/c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastPathEntry_1to4")
    public void testLastPathEntry_1to4(String param1, String param2) throws Throwable {
        assertEquals(param1, lastPathEntry(param2));
    }

    static public Stream<Arguments> Provider_testLastPathEntry_1to4() {
        return Stream.of(arguments("", "/"), arguments("", "//"), arguments("c", "/a/b/c"), arguments("c", "/a/b/c/"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidPaths_1to4")
    public void testValidPaths_1to4(String param1) throws Throwable {
        assertValidPath(param1);
    }

    static public Stream<Arguments> Provider_testValidPaths_1to4() {
        return Stream.of(arguments("/"), arguments("/a/b/c"), arguments("/users/drwho/org-apache-hadoop/registry/appid-55-55"), arguments("/a50"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvalidPaths_1to3")
    public void testInvalidPaths_1to3(String param1) throws Throwable {
        assertInvalidPath(param1);
    }

    static public Stream<Arguments> Provider_testInvalidPaths_1to3() {
        return Stream.of(arguments("/a_b"), arguments("/UpperAndLowerCase"), arguments("/space in string"));
    }
}
