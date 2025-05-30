package org.apache.hadoop.registry.client.binding;

import static org.apache.hadoop.registry.client.binding.RegistryPathUtils.*;
import java.io.IOException;
import java.util.List;
import org.apache.hadoop.fs.PathNotFoundException;
import org.apache.hadoop.registry.client.exceptions.InvalidPathnameException;
import org.junit.Assert;
import org.junit.Test;

public class TestRegistryPathUtils_Purified extends Assert {

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

    @Test
    public void testPaths_1() throws Throwable {
        assertCreatedPathEquals("/", "/", "");
    }

    @Test
    public void testPaths_2() throws Throwable {
        assertCreatedPathEquals("/", "", "");
    }

    @Test
    public void testPaths_3() throws Throwable {
        assertCreatedPathEquals("/", "", "/");
    }

    @Test
    public void testPaths_4() throws Throwable {
        assertCreatedPathEquals("/", "/", "/");
    }

    @Test
    public void testPaths_5() throws Throwable {
        assertCreatedPathEquals("/a", "/a", "");
    }

    @Test
    public void testPaths_6() throws Throwable {
        assertCreatedPathEquals("/a", "/", "a");
    }

    @Test
    public void testPaths_7() throws Throwable {
        assertCreatedPathEquals("/a/b", "/a", "b");
    }

    @Test
    public void testPaths_8() throws Throwable {
        assertCreatedPathEquals("/a/b", "/a/", "b");
    }

    @Test
    public void testPaths_9() throws Throwable {
        assertCreatedPathEquals("/a/b", "/a", "/b");
    }

    @Test
    public void testPaths_10() throws Throwable {
        assertCreatedPathEquals("/a/b", "/a", "/b/");
    }

    @Test
    public void testPaths_11() throws Throwable {
        assertCreatedPathEquals("/a", "/a", "/");
    }

    @Test
    public void testPaths_12() throws Throwable {
        assertCreatedPathEquals("/alice", "/", "/alice");
    }

    @Test
    public void testPaths_13() throws Throwable {
        assertCreatedPathEquals("/alice", "/alice", "/");
    }

    @Test
    public void testGetUserFromPath_1() throws Exception {
        assertEquals("bob", RegistryPathUtils.getUsername("/registry/users/bob/services/yarn-service/test1/"));
    }

    @Test
    public void testGetUserFromPath_2() throws Exception {
        assertEquals("bob-dev", RegistryPathUtils.getUsername("/registry/users/bob-dev/services/yarn-service/test1"));
    }

    @Test
    public void testGetUserFromPath_3() throws Exception {
        assertEquals("bob.dev", RegistryPathUtils.getUsername("/registry/users/bob.dev/services/yarn-service/test1"));
    }

    @Test
    public void testComplexPaths_1() throws Throwable {
        assertCreatedPathEquals("/", "", "");
    }

    @Test
    public void testComplexPaths_2() throws Throwable {
        assertCreatedPathEquals("/yarn/registry/users/hadoop/org-apache-hadoop", "/yarn/registry", "users/hadoop/org-apache-hadoop/");
    }

    @Test
    public void testSplittingEmpty_1() throws Throwable {
        assertEquals(0, split("").size());
    }

    @Test
    public void testSplittingEmpty_2() throws Throwable {
        assertEquals(0, split("/").size());
    }

    @Test
    public void testSplittingEmpty_3() throws Throwable {
        assertEquals(0, split("///").size());
    }

    @Test
    public void testParentOf_1() throws Throwable {
        assertEquals("/", parentOf("/a"));
    }

    @Test
    public void testParentOf_2() throws Throwable {
        assertEquals("/", parentOf("/a/"));
    }

    @Test
    public void testParentOf_3() throws Throwable {
        assertEquals("/a", parentOf("/a/b"));
    }

    @Test
    public void testParentOf_4() throws Throwable {
        assertEquals("/a/b", parentOf("/a/b/c"));
    }

    @Test
    public void testLastPathEntry_1() throws Throwable {
        assertEquals("", lastPathEntry("/"));
    }

    @Test
    public void testLastPathEntry_2() throws Throwable {
        assertEquals("", lastPathEntry("//"));
    }

    @Test
    public void testLastPathEntry_3() throws Throwable {
        assertEquals("c", lastPathEntry("/a/b/c"));
    }

    @Test
    public void testLastPathEntry_4() throws Throwable {
        assertEquals("c", lastPathEntry("/a/b/c/"));
    }

    @Test
    public void testValidPaths_1() throws Throwable {
        assertValidPath("/");
    }

    @Test
    public void testValidPaths_2() throws Throwable {
        assertValidPath("/a/b/c");
    }

    @Test
    public void testValidPaths_3() throws Throwable {
        assertValidPath("/users/drwho/org-apache-hadoop/registry/appid-55-55");
    }

    @Test
    public void testValidPaths_4() throws Throwable {
        assertValidPath("/a50");
    }

    @Test
    public void testInvalidPaths_1() throws Throwable {
        assertInvalidPath("/a_b");
    }

    @Test
    public void testInvalidPaths_2() throws Throwable {
        assertInvalidPath("/UpperAndLowerCase");
    }

    @Test
    public void testInvalidPaths_3() throws Throwable {
        assertInvalidPath("/space in string");
    }
}
