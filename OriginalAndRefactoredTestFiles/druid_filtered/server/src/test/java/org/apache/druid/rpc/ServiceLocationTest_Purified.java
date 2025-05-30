package org.apache.druid.rpc;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.server.coordination.DruidServerMetadata;
import org.apache.druid.server.coordination.ServerType;
import org.junit.Assert;
import org.junit.Test;
import java.net.URI;

public class ServiceLocationTest_Purified {

    @Test
    public void test_stripBrackets_1() {
        Assert.assertEquals("1:2:3:4:5:6:7:8", ServiceLocation.stripBrackets("[1:2:3:4:5:6:7:8]"));
    }

    @Test
    public void test_stripBrackets_2() {
        Assert.assertEquals("1:2:3:4:5:6:7:8", ServiceLocation.stripBrackets("1:2:3:4:5:6:7:8"));
    }

    @Test
    public void test_stripBrackets_3() {
        Assert.assertEquals("1.2.3.4", ServiceLocation.stripBrackets("1.2.3.4"));
    }
}
