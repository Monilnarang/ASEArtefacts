package org.jline.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorsTest_Purified {

    @Test
    public void testRounding_1() {
        assertEquals(2, Colors.roundColor(71, 16, "cam02"));
    }

    @Test
    public void testRounding_2() {
        assertEquals(2, Colors.roundColor(71, 16, "camlab(1,2)"));
    }

    @Test
    public void testRounding_3() {
        assertEquals(8, Colors.roundColor(71, 16, "rgb"));
    }

    @Test
    public void testRounding_4() {
        assertEquals(8, Colors.roundColor(71, 16, "rgb(2,4,3)"));
    }

    @Test
    public void testRounding_5() {
        assertEquals(2, Colors.roundColor(71, 16, "cie76"));
    }

    @Test
    public void testRounding_6() {
        assertEquals(2, Colors.roundColor(71, 16, "cie94"));
    }

    @Test
    public void testRounding_7() {
        assertEquals(2, Colors.roundColor(71, 16, "cie00"));
    }
}
