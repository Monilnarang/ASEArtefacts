package org.apache.hadoop.fs;

import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.AvroTestUtil;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Shell;
import org.apache.hadoop.thirdparty.com.google.common.base.Joiner;
import static org.apache.hadoop.test.PlatformAssumptions.assumeNotWindows;
import static org.apache.hadoop.test.PlatformAssumptions.assumeWindows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestPath_Purified {

    public static String mergeStatuses(Path[] paths) {
        String[] pathStrings = new String[paths.length];
        int i = 0;
        for (Path path : paths) {
            pathStrings[i++] = path.toUri().getPath();
        }
        Arrays.sort(pathStrings);
        return Joiner.on(";").join(pathStrings);
    }

    public static String mergeStatuses(FileStatus[] statuses) {
        Path[] paths = new Path[statuses.length];
        int i = 0;
        for (FileStatus status : statuses) {
            paths[i++] = status.getPath();
        }
        return mergeStatuses(paths);
    }

    private void toStringTest(String pathString) {
        assertEquals(pathString, new Path(pathString).toString());
    }

    @Test(timeout = 30000)
    public void testDots_1() {
        assertEquals(new Path("/foo/bar/baz").toString(), "/foo/bar/baz");
    }

    @Test(timeout = 30000)
    public void testDots_2() {
        assertEquals(new Path("/foo/bar", ".").toString(), "/foo/bar");
    }

    @Test(timeout = 30000)
    public void testDots_3() {
        assertEquals(new Path("/foo/bar/../baz").toString(), "/foo/baz");
    }

    @Test(timeout = 30000)
    public void testDots_4() {
        assertEquals(new Path("/foo/bar/./baz").toString(), "/foo/bar/baz");
    }

    @Test(timeout = 30000)
    public void testDots_5() {
        assertEquals(new Path("/foo/bar/baz/../../fud").toString(), "/foo/fud");
    }

    @Test(timeout = 30000)
    public void testDots_6() {
        assertEquals(new Path("/foo/bar/baz/.././../fud").toString(), "/foo/fud");
    }

    @Test(timeout = 30000)
    public void testDots_7() {
        assertEquals(new Path("../../foo/bar").toString(), "../../foo/bar");
    }

    @Test(timeout = 30000)
    public void testDots_8() {
        assertEquals(new Path(".././../foo/bar").toString(), "../../foo/bar");
    }

    @Test(timeout = 30000)
    public void testDots_9() {
        assertEquals(new Path("./foo/bar/baz").toString(), "foo/bar/baz");
    }

    @Test(timeout = 30000)
    public void testDots_10() {
        assertEquals(new Path("/foo/bar/../../baz/boo").toString(), "/baz/boo");
    }

    @Test(timeout = 30000)
    public void testDots_11() {
        assertEquals(new Path("foo/bar/").toString(), "foo/bar");
    }

    @Test(timeout = 30000)
    public void testDots_12() {
        assertEquals(new Path("foo/bar/../baz").toString(), "foo/baz");
    }

    @Test(timeout = 30000)
    public void testDots_13() {
        assertEquals(new Path("foo/bar/../../baz/boo").toString(), "baz/boo");
    }

    @Test(timeout = 30000)
    public void testDots_14() {
        assertEquals(new Path("/foo/bar", "baz/boo").toString(), "/foo/bar/baz/boo");
    }

    @Test(timeout = 30000)
    public void testDots_15() {
        assertEquals(new Path("foo/bar/", "baz/bud").toString(), "foo/bar/baz/bud");
    }

    @Test(timeout = 30000)
    public void testDots_16() {
        assertEquals(new Path("/foo/bar", "../../boo/bud").toString(), "/boo/bud");
    }

    @Test(timeout = 30000)
    public void testDots_17() {
        assertEquals(new Path("foo/bar", "../../boo/bud").toString(), "boo/bud");
    }

    @Test(timeout = 30000)
    public void testDots_18() {
        assertEquals(new Path(".", "boo/bud").toString(), "boo/bud");
    }

    @Test(timeout = 30000)
    public void testDots_19() {
        assertEquals(new Path("/foo/bar/baz", "../../boo/bud").toString(), "/foo/boo/bud");
    }

    @Test(timeout = 30000)
    public void testDots_20() {
        assertEquals(new Path("foo/bar/baz", "../../boo/bud").toString(), "foo/boo/bud");
    }

    @Test(timeout = 30000)
    public void testDots_21() {
        assertEquals(new Path("../../", "../../boo/bud").toString(), "../../../../boo/bud");
    }

    @Test(timeout = 30000)
    public void testDots_22() {
        assertEquals(new Path("../../foo", "../../../boo/bud").toString(), "../../../../boo/bud");
    }

    @Test(timeout = 30000)
    public void testDots_23() {
        assertEquals(new Path("../../foo/bar", "../boo/bud").toString(), "../../foo/boo/bud");
    }

    @Test(timeout = 30000)
    public void testDots_24() {
        assertEquals(new Path("foo/bar/baz", "../../..").toString(), "");
    }

    @Test(timeout = 30000)
    public void testDots_25() {
        assertEquals(new Path("foo/bar/baz", "../../../../..").toString(), "../..");
    }

    @Test(timeout = 5000)
    public void testWindowsPaths_1() throws URISyntaxException, IOException {
        assertEquals(new Path("c:\\foo\\bar").toString(), "c:/foo/bar");
    }

    @Test(timeout = 5000)
    public void testWindowsPaths_2() throws URISyntaxException, IOException {
        assertEquals(new Path("c:/foo/bar").toString(), "c:/foo/bar");
    }

    @Test(timeout = 5000)
    public void testWindowsPaths_3() throws URISyntaxException, IOException {
        assertEquals(new Path("/c:/foo/bar").toString(), "c:/foo/bar");
    }

    @Test(timeout = 5000)
    public void testWindowsPaths_4() throws URISyntaxException, IOException {
        assertEquals(new Path("file://c:/foo/bar").toString(), "file://c:/foo/bar");
    }

    @Test(timeout = 30000)
    public void testScheme_1() throws java.io.IOException {
        assertEquals("foo:/bar", new Path("foo:/", "/bar").toString());
    }

    @Test(timeout = 30000)
    public void testScheme_2() throws java.io.IOException {
        assertEquals("foo://bar/baz", new Path("foo://bar/", "/baz").toString());
    }

    @Test(timeout = 30000)
    public void testURI_1_testMerged_1() throws URISyntaxException, IOException {
        URI uri = new URI("file:///bar#baz");
        Path path = new Path(uri);
        assertTrue(uri.equals(new URI(path.toString())));
        FileSystem fs = path.getFileSystem(new Configuration());
        assertTrue(uri.equals(new URI(fs.makeQualified(path).toString())));
        URI uri2 = new URI("file:///bar/baz");
        assertTrue(uri2.equals(new URI(fs.makeQualified(new Path(uri2)).toString())));
    }

    @Test(timeout = 30000)
    public void testURI_4() throws URISyntaxException, IOException {
        assertEquals("foo://bar/baz#boo", new Path("foo://bar/", new Path(new URI("/baz#boo"))).toString());
    }

    @Test(timeout = 30000)
    public void testURI_5() throws URISyntaxException, IOException {
        assertEquals("foo://bar/baz/fud#boo", new Path(new Path(new URI("foo://bar/baz#bud")), new Path(new URI("fud#boo"))).toString());
    }

    @Test(timeout = 30000)
    public void testURI_6() throws URISyntaxException, IOException {
        assertEquals("foo://bar/fud#boo", new Path(new Path(new URI("foo://bar/baz#bud")), new Path(new URI("/fud#boo"))).toString());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_1() throws URISyntaxException, IOException {
        assertEquals("? mark char in to URI", new URI(null, null, "/foo?bar", null, null), new Path("/foo?bar").toUri());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_2() throws URISyntaxException, IOException {
        assertEquals("escape slashes chars in to URI", new URI(null, null, "/foo\"bar", null, null), new Path("/foo\"bar").toUri());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_3() throws URISyntaxException, IOException {
        assertEquals("spaces in chars to URI", new URI(null, null, "/foo bar", null, null), new Path("/foo bar").toUri());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_4() throws URISyntaxException, IOException {
        assertEquals("/foo?bar", new Path("http://localhost/foo?bar").toUri().getPath());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_5() throws URISyntaxException, IOException {
        assertEquals("/foo", new URI("http://localhost/foo?bar").getPath());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_6() throws URISyntaxException, IOException {
        assertEquals(new URI("/foo;bar").getPath(), new Path("/foo;bar").toUri().getPath());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_7() throws URISyntaxException, IOException {
        assertEquals(new URI("/foo;bar"), new Path("/foo;bar").toUri());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_8() throws URISyntaxException, IOException {
        assertEquals(new URI("/foo+bar"), new Path("/foo+bar").toUri());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_9() throws URISyntaxException, IOException {
        assertEquals(new URI("/foo-bar"), new Path("/foo-bar").toUri());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_10() throws URISyntaxException, IOException {
        assertEquals(new URI("/foo=bar"), new Path("/foo=bar").toUri());
    }

    @Test(timeout = 30000)
    public void testPathToUriConversion_11() throws URISyntaxException, IOException {
        assertEquals(new URI("/foo,bar"), new Path("/foo,bar").toUri());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_1() throws URISyntaxException, IOException {
        assertEquals("/foo%20bar", new URI(null, null, "/foo bar", null, null).getRawPath());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_2() throws URISyntaxException, IOException {
        assertEquals("/foo bar", new URI(null, null, "/foo bar", null, null).getPath());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_3() throws URISyntaxException, IOException {
        assertEquals("/foo%20bar", new URI(null, null, "/foo bar", null, null).toString());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_4() throws URISyntaxException, IOException {
        assertEquals("/foo%20bar", new Path("/foo bar").toUri().toString());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_5() throws URISyntaxException, IOException {
        assertEquals("/foo;bar", new URI("/foo;bar").getPath());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_6() throws URISyntaxException, IOException {
        assertEquals("/foo;bar", new URI("/foo;bar").getRawPath());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_7() throws URISyntaxException, IOException {
        assertEquals("/foo+bar", new URI("/foo+bar").getPath());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_8() throws URISyntaxException, IOException {
        assertEquals("/foo+bar", new URI("/foo+bar").getRawPath());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_9() throws URISyntaxException, IOException {
        assertEquals("/foo bar", new Path("http://localhost/foo bar").toUri().getPath());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_10() throws URISyntaxException, IOException {
        assertEquals("/foo%20bar", new Path("http://localhost/foo bar").toUri().toURL().getPath());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_11() throws URISyntaxException, IOException {
        assertEquals("/foo?bar", new URI("http", "localhost", "/foo?bar", null, null).getPath());
    }

    @Test(timeout = 30000)
    public void testReservedCharacters_12() throws URISyntaxException, IOException {
        assertEquals("/foo%3Fbar", new URI("http", "localhost", "/foo?bar", null, null).toURL().getPath());
    }

    @Test(timeout = 30000)
    public void testGetName_1() {
        assertEquals("", new Path("/").getName());
    }

    @Test(timeout = 30000)
    public void testGetName_2() {
        assertEquals("foo", new Path("foo").getName());
    }

    @Test(timeout = 30000)
    public void testGetName_3() {
        assertEquals("foo", new Path("/foo").getName());
    }

    @Test(timeout = 30000)
    public void testGetName_4() {
        assertEquals("foo", new Path("/foo/").getName());
    }

    @Test(timeout = 30000)
    public void testGetName_5() {
        assertEquals("bar", new Path("/foo/bar").getName());
    }

    @Test(timeout = 30000)
    public void testGetName_6() {
        assertEquals("bar", new Path("hdfs://host/foo/bar").getName());
    }

    @Test(timeout = 30000)
    public void testMergePaths_1() {
        assertEquals(new Path("/foo/bar"), Path.mergePaths(new Path("/foo"), new Path("/bar")));
    }

    @Test(timeout = 30000)
    public void testMergePaths_2() {
        assertEquals(new Path("/foo/bar/baz"), Path.mergePaths(new Path("/foo/bar"), new Path("/baz")));
    }

    @Test(timeout = 30000)
    public void testMergePaths_3() {
        assertEquals(new Path("/foo/bar/baz"), Path.mergePaths(new Path("/foo"), new Path("/bar/baz")));
    }

    @Test(timeout = 30000)
    public void testMergePaths_4() {
        assertEquals(new Path(Shell.WINDOWS ? "/C:/foo/bar" : "/C:/foo/C:/bar"), Path.mergePaths(new Path("/C:/foo"), new Path("/C:/bar")));
    }

    @Test(timeout = 30000)
    public void testMergePaths_5() {
        assertEquals(new Path(Shell.WINDOWS ? "/C:/bar" : "/C:/C:/bar"), Path.mergePaths(new Path("/C:/"), new Path("/C:/bar")));
    }

    @Test(timeout = 30000)
    public void testMergePaths_6() {
        assertEquals(new Path("/bar"), Path.mergePaths(new Path("/"), new Path("/bar")));
    }

    @Test(timeout = 30000)
    public void testMergePaths_7() {
        assertEquals(new Path("viewfs:///foo/bar"), Path.mergePaths(new Path("viewfs:///foo"), new Path("file:///bar")));
    }

    @Test(timeout = 30000)
    public void testMergePaths_8() {
        assertEquals(new Path("viewfs://vfsauthority/foo/bar"), Path.mergePaths(new Path("viewfs://vfsauthority/foo"), new Path("file://fileauthority/bar")));
    }

    @Test(timeout = 30000)
    public void testIsWindowsAbsolutePath_1() {
        assertTrue(Path.isWindowsAbsolutePath("C:\\test", false));
    }

    @Test(timeout = 30000)
    public void testIsWindowsAbsolutePath_2() {
        assertTrue(Path.isWindowsAbsolutePath("C:/test", false));
    }

    @Test(timeout = 30000)
    public void testIsWindowsAbsolutePath_3() {
        assertTrue(Path.isWindowsAbsolutePath("/C:/test", true));
    }

    @Test(timeout = 30000)
    public void testIsWindowsAbsolutePath_4() {
        assertFalse(Path.isWindowsAbsolutePath("/test", false));
    }

    @Test(timeout = 30000)
    public void testIsWindowsAbsolutePath_5() {
        assertFalse(Path.isWindowsAbsolutePath("/test", true));
    }

    @Test(timeout = 30000)
    public void testIsWindowsAbsolutePath_6() {
        assertFalse(Path.isWindowsAbsolutePath("C:test", false));
    }

    @Test(timeout = 30000)
    public void testIsWindowsAbsolutePath_7() {
        assertFalse(Path.isWindowsAbsolutePath("/C:test", true));
    }
}
