package io.elasticjob.lite.internal.monitor;

import io.elasticjob.lite.fixture.TestSimpleJob;
import io.elasticjob.lite.integrate.AbstractBaseStdJobTest;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public final class MonitorServiceEnableTest_Purified extends AbstractBaseStdJobTest {

    private static final int MONITOR_PORT = 9000;

    public MonitorServiceEnableTest() {
        super(TestSimpleJob.class, MONITOR_PORT);
    }

    @Test
    public void assertMonitorWithCommand_1() throws IOException {
        assertNotNull(SocketUtils.sendCommand(MonitorService.DUMP_COMMAND, MONITOR_PORT));
    }

    @Test
    public void assertMonitorWithCommand_2() throws IOException {
        assertNull(SocketUtils.sendCommand("unknown_command", MONITOR_PORT));
    }
}
