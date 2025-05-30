package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public abstract class AbstractFileUtilsDeleteDirectoryTest_Purified {

    @TempDir
    public File top;

    protected abstract boolean setupSymlink(File res, File link) throws Exception;

    @Test
    public void testDeleteDirWithASymbolicLinkDir2_1_testMerged_1() throws Exception {
        final File realOuter = new File(top, "realouter");
        assertTrue(realOuter.mkdirs());
        final File realInner = new File(realOuter, "realinner");
        assertTrue(realInner.mkdirs());
        FileUtils.touch(new File(realInner, "file1"));
        assertEquals(1, realInner.list().length);
        final File randomDirectory = new File(top, "randomDir");
        assertTrue(randomDirectory.mkdirs());
        FileUtils.touch(new File(randomDirectory, "randomfile"));
        assertEquals(1, randomDirectory.list().length);
        final File symlinkDirectory = new File(realOuter, "fakeinner");
        Files.createSymbolicLink(symlinkDirectory.toPath(), randomDirectory.toPath());
        assertEquals(1, symlinkDirectory.list().length);
        assertEquals(1, randomDirectory.list().length, "Contents of symbolic link should not have been removed");
    }

    @Test
    public void testDeleteDirWithASymbolicLinkDir2_7() throws Exception {
        assertEquals(1, top.list().length);
    }

    @Test
    public void testDeleteDirWithASymlinkDir_1_testMerged_1() throws Exception {
        final File realOuter = new File(top, "realouter");
        assertTrue(realOuter.mkdirs());
        final File realInner = new File(realOuter, "realinner");
        assertTrue(realInner.mkdirs());
        FileUtils.touch(new File(realInner, "file1"));
        assertEquals(1, realInner.list().length);
        final File randomDirectory = new File(top, "randomDir");
        assertTrue(randomDirectory.mkdirs());
        FileUtils.touch(new File(randomDirectory, "randomfile"));
        assertEquals(1, randomDirectory.list().length);
        final File symlinkDirectory = new File(realOuter, "fakeinner");
        assertTrue(setupSymlink(randomDirectory, symlinkDirectory));
        assertEquals(1, symlinkDirectory.list().length);
        assertEquals(1, randomDirectory.list().length, "Contents of symbolic link should not have been removed");
    }

    @Test
    public void testDeleteDirWithASymlinkDir_8() throws Exception {
        assertEquals(1, top.list().length);
    }

    @Test
    public void testDeleteDirWithSymlinkFile_1_testMerged_1() throws Exception {
        final File realOuter = new File(top, "realouter");
        assertTrue(realOuter.mkdirs());
        final File realInner = new File(realOuter, "realinner");
        assertTrue(realInner.mkdirs());
        assertEquals(1, realInner.list().length);
        final File randomFile = new File(top, "randomfile");
        FileUtils.touch(randomFile);
        final File symlinkFile = new File(realInner, "fakeinner");
        assertTrue(setupSymlink(randomFile, symlinkFile));
        assertEquals(2, realInner.list().length);
        assertTrue(randomFile.exists());
        assertFalse(symlinkFile.exists());
    }

    @Test
    public void testDeleteDirWithSymlinkFile_6() throws Exception {
        assertEquals(2, top.list().length);
    }

    @Test
    public void testDeleteDirWithSymlinkFile_7() throws Exception {
        assertEquals(1, top.list().length);
    }

    @Test
    public void testDeleteInvalidSymbolicLinks_1_testMerged_1() throws Exception {
        final File aFile = new File(top, "realParentDirA");
        assertTrue(aFile.mkdir());
        final File bFile = new File(aFile, "realChildDirB");
        assertTrue(bFile.mkdir());
    }

    @Test
    public void testDeleteInvalidSymbolicLinks_3_testMerged_2() throws Exception {
        final File cFile = new File(top, "realParentDirC");
        assertTrue(cFile.mkdir());
        final File dFile = new File(cFile, "realChildDirD");
        assertTrue(dFile.mkdir());
    }

    @Test
    public void testDeleteInvalidSymbolicLinks_5() throws Exception {
        assertEquals(0, top.list().length);
    }

    @Test
    public void testDeleteParentSymbolicLink2_1_testMerged_1() throws Exception {
        final File realParent = new File(top, "realparent");
        assertTrue(realParent.mkdirs());
        final File realInner = new File(realParent, "realinner");
        assertTrue(realInner.mkdirs());
        FileUtils.touch(new File(realInner, "file1"));
        assertEquals(1, realInner.list().length);
        final File randomDirectory = new File(top, "randomDir");
        assertTrue(randomDirectory.mkdirs());
        FileUtils.touch(new File(randomDirectory, "randomfile"));
        assertEquals(1, randomDirectory.list().length);
        final File symlinkDirectory = new File(realParent, "fakeinner");
        Files.createSymbolicLink(symlinkDirectory.toPath(), randomDirectory.toPath());
        assertEquals(1, symlinkDirectory.list().length);
        assertEquals(1, randomDirectory.list().length, "Contents of symbolic link should not have been removed");
    }

    @Test
    public void testDeleteParentSymbolicLink2_7() throws Exception {
        assertEquals(2, top.list().length);
    }

    @Test
    public void testDeleteParentSymlink_1_testMerged_1() throws Exception {
        final File realParent = new File(top, "realparent");
        assertTrue(realParent.mkdirs());
        final File realInner = new File(realParent, "realinner");
        assertTrue(realInner.mkdirs());
        FileUtils.touch(new File(realInner, "file1"));
        assertEquals(1, realInner.list().length);
        final File randomDirectory = new File(top, "randomDir");
        assertTrue(randomDirectory.mkdirs());
        FileUtils.touch(new File(randomDirectory, "randomfile"));
        assertEquals(1, randomDirectory.list().length);
        final File symlinkDirectory = new File(realParent, "fakeinner");
        assertTrue(setupSymlink(randomDirectory, symlinkDirectory));
        assertEquals(1, symlinkDirectory.list().length);
        final File symlinkParentDirectory = new File(top, "fakeouter");
        assertTrue(setupSymlink(realParent, symlinkParentDirectory));
        assertEquals(1, randomDirectory.list().length, "Contents of symbolic link should not have been removed");
    }

    @Test
    public void testDeleteParentSymlink_9() throws Exception {
        assertEquals(2, top.list().length);
    }

    @Test
    public void testDeletesNested_1_testMerged_1() throws Exception {
        final File nested = new File(top, "nested");
        assertTrue(nested.mkdirs());
        FileUtils.touch(new File(nested, "regular"));
        FileUtils.touch(new File(nested, ".hidden"));
        assertEquals(2, nested.list().length);
    }

    @Test
    public void testDeletesNested_2() throws Exception {
        assertEquals(1, top.list().length);
    }

    @Test
    public void testDeletesNested_4() throws Exception {
        assertEquals(0, top.list().length);
    }

    @Test
    public void testDeletesRegular_1_testMerged_1() throws Exception {
        final File nested = new File(top, "nested");
        assertTrue(nested.mkdirs());
        assertEquals(0, nested.list().length);
    }

    @Test
    public void testDeletesRegular_2() throws Exception {
        assertEquals(1, top.list().length);
    }

    @Test
    public void testDeletesRegular_4() throws Exception {
        assertEquals(0, top.list().length);
    }
}
