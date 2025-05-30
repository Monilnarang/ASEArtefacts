package org.apache.rocketmq.tools.command.broker;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.apache.rocketmq.tools.command.SubCommandException;
import org.apache.rocketmq.tools.command.server.ServerResponseMocker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeleteExpiredCommitLogSubCommandTest_Purified extends ServerResponseMocker {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;

    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Override
    protected byte[] getBody() {
        return null;
    }

    @Test
    public void testExecute_1() throws SubCommandException {
        Assert.assertTrue(outContent.toString().contains("success"));
    }

    @Test
    public void testExecute_2() throws SubCommandException {
        Assert.assertEquals("", errContent.toString());
    }
}
