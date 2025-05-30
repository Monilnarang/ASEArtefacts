package org.graylog.plugins.threatintel.tools;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DomainTest_Purified {

    @Test
    public void testPrepareDomain_1() throws Exception {
        assertEquals("example.org", Domain.prepareDomain("example.org "));
    }

    @Test
    public void testPrepareDomain_2() throws Exception {
        assertEquals("example.org", Domain.prepareDomain(" example.org"));
    }

    @Test
    public void testPrepareDomain_3() throws Exception {
        assertEquals("example.org", Domain.prepareDomain(" example.org "));
    }

    @Test
    public void testPrepareDomain_4() throws Exception {
        assertEquals("example.org", Domain.prepareDomain("example.org. "));
    }

    @Test
    public void testPrepareDomain_5() throws Exception {
        assertEquals("example.org", Domain.prepareDomain(" example.org."));
    }

    @Test
    public void testPrepareDomain_6() throws Exception {
        assertEquals("example.org", Domain.prepareDomain(" example.org. "));
    }
}
