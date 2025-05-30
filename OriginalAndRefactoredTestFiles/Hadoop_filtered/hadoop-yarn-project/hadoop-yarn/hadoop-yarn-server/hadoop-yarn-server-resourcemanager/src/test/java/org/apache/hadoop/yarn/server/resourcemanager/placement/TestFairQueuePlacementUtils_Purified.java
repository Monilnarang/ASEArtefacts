package org.apache.hadoop.yarn.server.resourcemanager.placement;

import org.junit.Test;
import static org.apache.hadoop.yarn.server.resourcemanager.placement.FairQueuePlacementUtils.DOT;
import static org.apache.hadoop.yarn.server.resourcemanager.placement.FairQueuePlacementUtils.DOT_REPLACEMENT;
import static org.apache.hadoop.yarn.server.resourcemanager.placement.FairQueuePlacementUtils.ROOT_QUEUE;
import static org.apache.hadoop.yarn.server.resourcemanager.placement.FairQueuePlacementUtils.assureRoot;
import static org.apache.hadoop.yarn.server.resourcemanager.placement.FairQueuePlacementUtils.cleanName;
import static org.apache.hadoop.yarn.server.resourcemanager.placement.FairQueuePlacementUtils.isValidQueueName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestFairQueuePlacementUtils_Purified {

    @Test
    public void testAssureRoot_1_testMerged_1() {
        final String queueName = "base";
        final String rootOnly = "root";
        final String rootNoDot = "rootbase";
        final String alreadyRoot = "root.base";
        String rooted = assureRoot(queueName);
        assertTrue("Queue should have root prefix (base)", rooted.startsWith(ROOT_QUEUE + DOT));
        rooted = assureRoot(rootOnly);
        assertEquals("'root' queue should not have root prefix (root)", rootOnly, rooted);
        rooted = assureRoot(rootNoDot);
        assertTrue("Queue should have root prefix (rootbase)", rooted.startsWith(ROOT_QUEUE + DOT));
        assertEquals("'root' queue base was replaced and not prefixed", 5, rooted.lastIndexOf(ROOT_QUEUE));
        rooted = assureRoot(alreadyRoot);
        assertEquals("Root prefixed queue changed and it should not (root.base)", rooted, alreadyRoot);
    }

    @Test
    public void testAssureRoot_6() {
        assertNull("Null queue did not return null queue", assureRoot(null));
    }

    @Test
    public void testAssureRoot_7() {
        assertEquals("Empty queue did not return empty name", "", assureRoot(""));
    }

    @Test
    public void testIsValidQueueName_1() {
        assertFalse("'null' queue was not marked as invalid", isValidQueueName(null));
    }

    @Test
    public void testIsValidQueueName_2() {
        assertTrue("empty queue was not tagged valid", isValidQueueName(""));
    }

    @Test
    public void testIsValidQueueName_3() {
        final String valid = "valid";
        assertTrue("Simple queue name was not tagged valid (valid)", isValidQueueName(valid));
    }

    @Test
    public void testIsValidQueueName_4() {
        final String rootOnly = "root";
        assertTrue("Root only queue was not tagged valid (root)", isValidQueueName(rootOnly));
    }

    @Test
    public void testIsValidQueueName_5() {
        final String validRooted = "root.valid";
        assertTrue("Root prefixed queue was not tagged valid (root.valid)", isValidQueueName(validRooted));
    }

    @Test
    public void testIsValidQueueName_6() {
        final String startDot = ".invalid";
        assertFalse("Queue starting with dot was not tagged invalid (.invalid)", isValidQueueName(startDot));
    }

    @Test
    public void testIsValidQueueName_7() {
        final String endDot = "invalid.";
        assertFalse("Queue ending with dot was not tagged invalid (invalid.)", isValidQueueName(endDot));
    }

    @Test
    public void testIsValidQueueName_8() {
        final String startSpace = " invalid";
        assertFalse("Queue starting with space was not tagged invalid ( invalid)", isValidQueueName(startSpace));
    }

    @Test
    public void testIsValidQueueName_9() {
        final String endSpace = "invalid ";
        assertFalse("Queue ending with space was not tagged invalid (invalid )", isValidQueueName(endSpace));
    }

    @Test
    public void testIsValidQueueName_10() {
        final String unicodeSpace = "\u00A0invalid";
        assertFalse("Queue with unicode space was not tagged as invalid (unicode)", isValidQueueName(unicodeSpace));
    }
}
