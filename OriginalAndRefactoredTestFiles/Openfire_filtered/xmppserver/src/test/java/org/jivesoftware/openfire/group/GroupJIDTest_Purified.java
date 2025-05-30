package org.jivesoftware.openfire.group;

import org.jivesoftware.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.xmpp.packet.JID;
import static org.junit.jupiter.api.Assertions.*;

public class GroupJIDTest_Purified {

    @Test
    public void testBase32Alphabet_1() {
        String testABC = "ABC";
        assertTrue(StringUtils.isBase32(testABC));
    }

    @Test
    public void testBase32Alphabet_2() {
        String test123 = "123";
        assertTrue(StringUtils.isBase32(test123));
    }

    @Test
    public void testBase32Alphabet_3() {
        String testabc = "abc";
        assertTrue(StringUtils.isBase32(testabc));
    }

    @Test
    public void testBase32Alphabet_4() {
        String testXYZ = "XYZ";
        assertFalse(StringUtils.isBase32(testXYZ));
    }
}
