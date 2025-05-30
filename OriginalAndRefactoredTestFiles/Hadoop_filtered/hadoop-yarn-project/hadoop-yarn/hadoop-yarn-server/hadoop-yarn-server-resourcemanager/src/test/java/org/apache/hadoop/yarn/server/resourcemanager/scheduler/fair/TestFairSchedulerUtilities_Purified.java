package org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair;

import org.junit.Test;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.FairSchedulerUtilities.trimQueueName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestFairSchedulerUtilities_Purified {

    @Test
    public void testTrimQueueNamesEmpty_1() throws Exception {
        assertNull(trimQueueName(null));
    }

    @Test
    public void testTrimQueueNamesEmpty_2() throws Exception {
        final String spaces = "\u2002\u3000\r\u0085\u200A\u2005\u2000\u3000" + "\u2029\u000B\u3000\u2008\u2003\u205F\u3000\u1680" + "\u0009\u0020\u2006\u2001\u202F\u00A0\u000C\u2009" + "\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";
        assertTrue(trimQueueName(spaces).isEmpty());
    }
}
