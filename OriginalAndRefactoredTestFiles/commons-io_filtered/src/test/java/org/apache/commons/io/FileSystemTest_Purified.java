package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class FileSystemTest_Purified {

    @Test
    public void testSupportsDriveLetter_1() {
        assertTrue(FileSystem.WINDOWS.supportsDriveLetter());
    }

    @Test
    public void testSupportsDriveLetter_2() {
        assertFalse(FileSystem.GENERIC.supportsDriveLetter());
    }

    @Test
    public void testSupportsDriveLetter_3() {
        assertFalse(FileSystem.LINUX.supportsDriveLetter());
    }

    @Test
    public void testSupportsDriveLetter_4() {
        assertFalse(FileSystem.MAC_OSX.supportsDriveLetter());
    }
}
