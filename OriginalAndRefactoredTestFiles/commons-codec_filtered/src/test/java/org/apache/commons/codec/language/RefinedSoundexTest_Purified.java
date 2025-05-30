package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class RefinedSoundexTest_Purified extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    public void testDifference_1() throws EncoderException {
        assertEquals(0, getStringEncoder().difference(null, null));
    }

    @Test
    public void testDifference_2() throws EncoderException {
        assertEquals(0, getStringEncoder().difference("", ""));
    }

    @Test
    public void testDifference_3() throws EncoderException {
        assertEquals(0, getStringEncoder().difference(" ", " "));
    }

    @Test
    public void testDifference_4() throws EncoderException {
        assertEquals(6, getStringEncoder().difference("Smith", "Smythe"));
    }

    @Test
    public void testDifference_5() throws EncoderException {
        assertEquals(3, getStringEncoder().difference("Ann", "Andrew"));
    }

    @Test
    public void testDifference_6() throws EncoderException {
        assertEquals(1, getStringEncoder().difference("Margaret", "Andrew"));
    }

    @Test
    public void testDifference_7() throws EncoderException {
        assertEquals(1, getStringEncoder().difference("Janet", "Margaret"));
    }

    @Test
    public void testDifference_8() throws EncoderException {
        assertEquals(5, getStringEncoder().difference("Green", "Greene"));
    }

    @Test
    public void testDifference_9() throws EncoderException {
        assertEquals(1, getStringEncoder().difference("Blotchet-Halls", "Greene"));
    }

    @Test
    public void testDifference_10() throws EncoderException {
        assertEquals(6, getStringEncoder().difference("Smith", "Smythe"));
    }

    @Test
    public void testDifference_11() throws EncoderException {
        assertEquals(8, getStringEncoder().difference("Smithers", "Smythers"));
    }

    @Test
    public void testDifference_12() throws EncoderException {
        assertEquals(5, getStringEncoder().difference("Anothers", "Brothers"));
    }

    @Test
    public void testEncode_1() {
        assertEquals("T6036084", getStringEncoder().encode("testing"));
    }

    @Test
    public void testEncode_2() {
        assertEquals("T6036084", getStringEncoder().encode("TESTING"));
    }

    @Test
    public void testEncode_3() {
        assertEquals("T60", getStringEncoder().encode("The"));
    }

    @Test
    public void testEncode_4() {
        assertEquals("Q503", getStringEncoder().encode("quick"));
    }

    @Test
    public void testEncode_5() {
        assertEquals("B1908", getStringEncoder().encode("brown"));
    }

    @Test
    public void testEncode_6() {
        assertEquals("F205", getStringEncoder().encode("fox"));
    }

    @Test
    public void testEncode_7() {
        assertEquals("J408106", getStringEncoder().encode("jumped"));
    }

    @Test
    public void testEncode_8() {
        assertEquals("O0209", getStringEncoder().encode("over"));
    }

    @Test
    public void testEncode_9() {
        assertEquals("T60", getStringEncoder().encode("the"));
    }

    @Test
    public void testEncode_10() {
        assertEquals("L7050", getStringEncoder().encode("lazy"));
    }

    @Test
    public void testEncode_11() {
        assertEquals("D6043", getStringEncoder().encode("dogs"));
    }

    @Test
    public void testEncode_12() {
        assertEquals("D6043", RefinedSoundex.US_ENGLISH.encode("dogs"));
    }
}
