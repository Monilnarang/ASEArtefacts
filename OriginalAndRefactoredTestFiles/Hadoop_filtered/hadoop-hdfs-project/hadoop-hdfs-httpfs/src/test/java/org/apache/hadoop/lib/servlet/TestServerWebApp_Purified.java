package org.apache.hadoop.lib.servlet;

import static org.junit.Assert.assertEquals;
import org.apache.hadoop.lib.server.Server;
import org.apache.hadoop.test.HTestCase;
import org.apache.hadoop.test.TestDir;
import org.apache.hadoop.test.TestDirHelper;
import org.junit.Assert;
import org.junit.Test;
import java.net.InetSocketAddress;

public class TestServerWebApp_Purified extends HTestCase {

    @Test
    public void getHomeDir_1() {
        assertEquals(ServerWebApp.getHomeDir("TestServerWebApp0"), "/tmp");
    }

    @Test
    public void getHomeDir_2() {
        assertEquals(ServerWebApp.getDir("TestServerWebApp0", ".log.dir", "/tmp/log"), "/tmp/log");
    }

    @Test
    public void getHomeDir_3() {
        assertEquals(ServerWebApp.getDir("TestServerWebApp0", ".log.dir", "/tmp/log"), "/tmplog");
    }
}
