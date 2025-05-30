package org.apache.commons.io.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public abstract class ComparatorAbstractTest_Purified {

    @TempDir
    public File dir;

    protected AbstractFileComparator comparator;

    protected Comparator<File> reverse;

    protected File equalFile1;

    protected File equalFile2;

    protected File lessFile;

    protected File moreFile;

    @Test
    public void testComparator_1() {
        assertEquals(0, comparator.compare(equalFile1, equalFile2), "equal");
    }

    @Test
    public void testComparator_2() {
        assertTrue(comparator.compare(lessFile, moreFile) < 0, "less");
    }

    @Test
    public void testComparator_3() {
        assertTrue(comparator.compare(moreFile, lessFile) > 0, "more");
    }

    @Test
    public void testReverseComparator_1() {
        assertEquals(0, reverse.compare(equalFile1, equalFile2), "equal");
    }

    @Test
    public void testReverseComparator_2() {
        assertTrue(reverse.compare(moreFile, lessFile) < 0, "less");
    }

    @Test
    public void testReverseComparator_3() {
        assertTrue(reverse.compare(lessFile, moreFile) > 0, "more");
    }

    @Test
    public void testToString_1() {
        assertNotNull(comparator.toString(), "comparator");
    }

    @Test
    public void testToString_2() {
        assertTrue(reverse.toString().startsWith("ReverseFileComparator["), "reverse");
    }
}
