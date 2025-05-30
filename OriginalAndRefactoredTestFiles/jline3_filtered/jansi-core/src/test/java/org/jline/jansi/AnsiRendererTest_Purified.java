package org.jline.jansi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.jline.jansi.Ansi.*;
import static org.jline.jansi.Ansi.Attribute.*;
import static org.jline.jansi.Ansi.Color.*;
import static org.jline.jansi.AnsiRenderer.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnsiRendererTest_Purified {

    @BeforeAll
    static void setUp() {
        Ansi.setEnabled(true);
    }

    @Test
    public void testTest_1() throws Exception {
        assertFalse(test("foo"));
    }

    @Test
    public void testTest_2() throws Exception {
        assertTrue(test("@|foo|"));
    }

    @Test
    public void testTest_3() throws Exception {
        assertTrue(test("@|foo"));
    }
}
