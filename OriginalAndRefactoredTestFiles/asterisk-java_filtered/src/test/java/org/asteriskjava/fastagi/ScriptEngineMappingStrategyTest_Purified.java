package org.asteriskjava.fastagi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.asteriskjava.fastagi.ScriptEngineMappingStrategy.getExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ScriptEngineMappingStrategyTest_Purified {

    private ScriptEngineMappingStrategy scriptEngineMappingStrategy;

    @BeforeEach
    void setUp() {
        this.scriptEngineMappingStrategy = new ScriptEngineMappingStrategy();
    }

    @Test
    void testGetExtension_1() {
        assertEquals("txt", getExtension("hello.txt"));
    }

    @Test
    void testGetExtension_2() {
        assertEquals("txt", getExtension("/some/path/hello.txt"));
    }

    @Test
    void testGetExtension_3() {
        assertEquals("txt", getExtension("C:\\some\\path\\hello.txt"));
    }

    @Test
    void testGetExtension_4() {
        assertEquals("txt", getExtension("C:\\some\\path\\hel.lo.txt"));
    }

    @Test
    void testGetExtension_5() {
        assertEquals("txt", getExtension("C:\\some\\pa.th\\hel.lo.txt"));
    }

    @Test
    void testGetExtension_6() {
        assertEquals("txt", getExtension(".txt"));
    }

    @Test
    void testGetExtension_7() {
        assertEquals(null, getExtension(null));
    }

    @Test
    void testGetExtension_8() {
        assertEquals(null, getExtension(""));
    }

    @Test
    void testGetExtension_9() {
        assertEquals(null, getExtension("hello"));
    }

    @Test
    void testGetExtension_10() {
        assertEquals(null, getExtension("/some/path/hello"));
    }

    @Test
    void testGetExtension_11() {
        assertEquals(null, getExtension("/some/pa.th/hello"));
    }

    @Test
    void testGetExtension_12() {
        assertEquals(null, getExtension("C:\\some\\path\\hello"));
    }

    @Test
    void testGetExtension_13() {
        assertEquals(null, getExtension("C:\\some\\pa.th\\hello"));
    }

    @Test
    void testGetExtension_14() {
        assertEquals(null, getExtension("/some/pa.th\\hello"));
    }

    @Test
    void testGetExtension_15() {
        assertEquals(null, getExtension("C:\\some\\pa.th/hello"));
    }
}
