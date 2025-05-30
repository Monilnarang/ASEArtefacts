package org.apache.hadoop.registry.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.hadoop.registry.AbstractRegistryTest;
import org.apache.hadoop.registry.operations.TestRegistryOperations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRegistryCli_Purified extends AbstractRegistryTest {

    protected static final Logger LOG = LoggerFactory.getLogger(TestRegistryOperations.class);

    private ByteArrayOutputStream sysOutStream;

    private PrintStream sysOut;

    private ByteArrayOutputStream sysErrStream;

    private PrintStream sysErr;

    private RegistryCli cli;

    @Before
    public void setUp() throws Exception {
        sysOutStream = new ByteArrayOutputStream();
        sysOut = new PrintStream(sysOutStream);
        sysErrStream = new ByteArrayOutputStream();
        sysErr = new PrintStream(sysErrStream);
        System.setOut(sysOut);
        cli = new RegistryCli(operations, createRegistryConfiguration(), sysOut, sysErr);
    }

    @After
    public void tearDown() throws Exception {
        cli.close();
    }

    private void assertResult(RegistryCli cli, int code, String... args) throws Exception {
        int result = cli.run(args);
        assertEquals(code, result);
    }

    @Test
    public void testInvalidNumArgs_1() throws Exception {
        assertResult(cli, -1, "ls");
    }

    @Test
    public void testInvalidNumArgs_2() throws Exception {
        assertResult(cli, -1, "ls", "/path", "/extraPath");
    }

    @Test
    public void testInvalidNumArgs_3() throws Exception {
        assertResult(cli, -1, "resolve");
    }

    @Test
    public void testInvalidNumArgs_4() throws Exception {
        assertResult(cli, -1, "resolve", "/path", "/extraPath");
    }

    @Test
    public void testInvalidNumArgs_5() throws Exception {
        assertResult(cli, -1, "mknode");
    }

    @Test
    public void testInvalidNumArgs_6() throws Exception {
        assertResult(cli, -1, "mknode", "/path", "/extraPath");
    }

    @Test
    public void testInvalidNumArgs_7() throws Exception {
        assertResult(cli, -1, "rm");
    }

    @Test
    public void testInvalidNumArgs_8() throws Exception {
        assertResult(cli, -1, "rm", "/path", "/extraPath");
    }

    @Test
    public void testInvalidNumArgs_9() throws Exception {
        assertResult(cli, -1, "bind");
    }

    @Test
    public void testInvalidNumArgs_10() throws Exception {
        assertResult(cli, -1, "bind", "foo");
    }

    @Test
    public void testInvalidNumArgs_11() throws Exception {
        assertResult(cli, -1, "bind", "-inet", "foo");
    }

    @Test
    public void testInvalidNumArgs_12() throws Exception {
        assertResult(cli, -1, "bind", "-inet", "-api", "-p", "378", "-h", "host", "/foo");
    }

    @Test
    public void testInvalidNumArgs_13() throws Exception {
        assertResult(cli, -1, "bind", "-inet", "-api", "Api", "-p", "-h", "host", "/foo");
    }

    @Test
    public void testInvalidNumArgs_14() throws Exception {
        assertResult(cli, -1, "bind", "-inet", "-api", "Api", "-p", "378", "-h", "/foo");
    }

    @Test
    public void testInvalidNumArgs_15() throws Exception {
        assertResult(cli, -1, "bind", "-inet", "-api", "Api", "-p", "378", "-h", "host");
    }

    @Test
    public void testInvalidNumArgs_16() throws Exception {
        assertResult(cli, -1, "bind", "-api", "Api", "-p", "378", "-h", "host", "/foo");
    }

    @Test
    public void testInvalidNumArgs_17() throws Exception {
        assertResult(cli, -1, "bind", "-webui", "foo");
    }

    @Test
    public void testInvalidNumArgs_18() throws Exception {
        assertResult(cli, -1, "bind", "-webui", "-api", "Api", "/foo");
    }

    @Test
    public void testInvalidNumArgs_19() throws Exception {
        assertResult(cli, -1, "bind", "-webui", "uriString", "-api", "/foo");
    }

    @Test
    public void testInvalidNumArgs_20() throws Exception {
        assertResult(cli, -1, "bind", "-webui", "uriString", "-api", "Api");
    }

    @Test
    public void testInvalidNumArgs_21() throws Exception {
        assertResult(cli, -1, "bind", "-rest", "foo");
    }

    @Test
    public void testInvalidNumArgs_22() throws Exception {
        assertResult(cli, -1, "bind", "-rest", "uriString", "-api", "Api");
    }

    @Test
    public void testInvalidNumArgs_23() throws Exception {
        assertResult(cli, -1, "bind", "-rest", "-api", "Api", "/foo");
    }

    @Test
    public void testInvalidNumArgs_24() throws Exception {
        assertResult(cli, -1, "bind", "-rest", "uriString", "-api", "/foo");
    }

    @Test
    public void testInvalidNumArgs_25() throws Exception {
        assertResult(cli, -1, "bind", "uriString", "-api", "Api", "/foo");
    }

    @Test
    public void testBadPath_1() throws Exception {
        assertResult(cli, -1, "ls", "NonSlashPath");
    }

    @Test
    public void testBadPath_2() throws Exception {
        assertResult(cli, -1, "ls", "//");
    }

    @Test
    public void testBadPath_3() throws Exception {
        assertResult(cli, -1, "resolve", "NonSlashPath");
    }

    @Test
    public void testBadPath_4() throws Exception {
        assertResult(cli, -1, "resolve", "//");
    }

    @Test
    public void testBadPath_5() throws Exception {
        assertResult(cli, -1, "mknode", "NonSlashPath");
    }

    @Test
    public void testBadPath_6() throws Exception {
        assertResult(cli, -1, "mknode", "//");
    }

    @Test
    public void testBadPath_7() throws Exception {
        assertResult(cli, -1, "rm", "NonSlashPath");
    }

    @Test
    public void testBadPath_8() throws Exception {
        assertResult(cli, -1, "rm", "//");
    }

    @Test
    public void testBadPath_9() throws Exception {
        assertResult(cli, -1, "bind", "-inet", "-api", "Api", "-p", "378", "-h", "host", "NonSlashPath");
    }

    @Test
    public void testBadPath_10() throws Exception {
        assertResult(cli, -1, "bind", "-inet", "-api", "Api", "-p", "378", "-h", "host", "//");
    }

    @Test
    public void testBadPath_11() throws Exception {
        assertResult(cli, -1, "bind", "-webui", "uriString", "-api", "Api", "NonSlashPath");
    }

    @Test
    public void testBadPath_12() throws Exception {
        assertResult(cli, -1, "bind", "-webui", "uriString", "-api", "Api", "//");
    }

    @Test
    public void testBadPath_13() throws Exception {
        assertResult(cli, -1, "bind", "-rest", "uriString", "-api", "Api", "NonSlashPath");
    }

    @Test
    public void testBadPath_14() throws Exception {
        assertResult(cli, -1, "bind", "-rest", "uriString", "-api", "Api", "//");
    }

    @Test
    public void testNotExistingPaths_1() throws Exception {
        assertResult(cli, -1, "ls", "/nonexisting_path");
    }

    @Test
    public void testNotExistingPaths_2() throws Exception {
        assertResult(cli, -1, "ls", "/NonExistingDir/nonexisting_path");
    }

    @Test
    public void testNotExistingPaths_3() throws Exception {
        assertResult(cli, -1, "resolve", "/nonexisting_path");
    }

    @Test
    public void testNotExistingPaths_4() throws Exception {
        assertResult(cli, -1, "resolve", "/NonExistingDir/nonexisting_path");
    }

    @Test
    public void testNotExistingPaths_5() throws Exception {
        assertResult(cli, -1, "bind", "-inet", "-api", "Api", "-p", "378", "-h", "host", "/NonExistingDir/nonexisting_path");
    }

    @Test
    public void testNotExistingPaths_6() throws Exception {
        assertResult(cli, -1, "bind", "-webui", "uriString", "-api", "Api", "/NonExistingDir/nonexisting_path");
    }

    @Test
    public void testNotExistingPaths_7() throws Exception {
        assertResult(cli, -1, "bind", "-rest", "uriString", "-api", "Api", "/NonExistingDir/nonexisting_path");
    }

    @Test
    public void testValidCommands_1() throws Exception {
        assertResult(cli, 0, "bind", "-inet", "-api", "Api", "-p", "378", "-h", "host", "/foo");
    }

    @Test
    public void testValidCommands_2() throws Exception {
        assertResult(cli, 0, "resolve", "/foo");
    }

    @Test
    public void testValidCommands_3() throws Exception {
        assertResult(cli, 0, "rm", "/foo");
    }

    @Test
    public void testValidCommands_4() throws Exception {
        assertResult(cli, -1, "resolve", "/foo");
    }

    @Test
    public void testValidCommands_5() throws Exception {
        assertResult(cli, 0, "bind", "-webui", "uriString", "-api", "Api", "/foo");
    }

    @Test
    public void testValidCommands_6() throws Exception {
        assertResult(cli, 0, "resolve", "/foo");
    }

    @Test
    public void testValidCommands_7() throws Exception {
        assertResult(cli, 0, "rm", "/foo");
    }

    @Test
    public void testValidCommands_8() throws Exception {
        assertResult(cli, -1, "resolve", "/foo");
    }

    @Test
    public void testValidCommands_9() throws Exception {
        assertResult(cli, 0, "bind", "-rest", "uriString", "-api", "Api", "/foo");
    }

    @Test
    public void testValidCommands_10() throws Exception {
        assertResult(cli, 0, "resolve", "/foo");
    }

    @Test
    public void testValidCommands_11() throws Exception {
        assertResult(cli, 0, "rm", "/foo");
    }

    @Test
    public void testValidCommands_12() throws Exception {
        assertResult(cli, -1, "resolve", "/foo");
    }

    @Test
    public void testValidCommands_13() throws Exception {
        assertResult(cli, 0, "mknode", "/subdir");
    }

    @Test
    public void testValidCommands_14() throws Exception {
        assertResult(cli, -1, "resolve", "/subdir");
    }

    @Test
    public void testValidCommands_15() throws Exception {
        assertResult(cli, 0, "bind", "-inet", "-api", "Api", "-p", "378", "-h", "host", "/subdir/foo");
    }

    @Test
    public void testValidCommands_16() throws Exception {
        assertResult(cli, 0, "resolve", "/subdir/foo");
    }

    @Test
    public void testValidCommands_17() throws Exception {
        assertResult(cli, 0, "rm", "/subdir/foo");
    }

    @Test
    public void testValidCommands_18() throws Exception {
        assertResult(cli, -1, "resolve", "/subdir/foo");
    }

    @Test
    public void testValidCommands_19() throws Exception {
        assertResult(cli, 0, "bind", "-webui", "uriString", "-api", "Api", "/subdir/foo");
    }

    @Test
    public void testValidCommands_20() throws Exception {
        assertResult(cli, 0, "resolve", "/subdir/foo");
    }

    @Test
    public void testValidCommands_21() throws Exception {
        assertResult(cli, 0, "rm", "/subdir/foo");
    }

    @Test
    public void testValidCommands_22() throws Exception {
        assertResult(cli, -1, "resolve", "/subdir/foo");
    }

    @Test
    public void testValidCommands_23() throws Exception {
        assertResult(cli, 0, "bind", "-rest", "uriString", "-api", "Api", "/subdir/foo");
    }

    @Test
    public void testValidCommands_24() throws Exception {
        assertResult(cli, 0, "resolve", "/subdir/foo");
    }

    @Test
    public void testValidCommands_25() throws Exception {
        assertResult(cli, 0, "rm", "/subdir/foo");
    }

    @Test
    public void testValidCommands_26() throws Exception {
        assertResult(cli, -1, "resolve", "/subdir/foo");
    }

    @Test
    public void testValidCommands_27() throws Exception {
        assertResult(cli, 0, "rm", "/subdir");
    }

    @Test
    public void testValidCommands_28() throws Exception {
        assertResult(cli, -1, "resolve", "/subdir");
    }

    @Test
    public void testValidCommands_29() throws Exception {
        assertResult(cli, 0, "mknode", "/dir");
    }

    @Test
    public void testValidCommands_30() throws Exception {
        assertResult(cli, -1, "resolve", "/dir");
    }

    @Test
    public void testValidCommands_31() throws Exception {
        assertResult(cli, 0, "bind", "-inet", "-api", "Api", "-p", "378", "-h", "host", "/dir");
    }

    @Test
    public void testValidCommands_32() throws Exception {
        assertResult(cli, 0, "resolve", "/dir");
    }

    @Test
    public void testValidCommands_33() throws Exception {
        assertResult(cli, 0, "rm", "/dir");
    }

    @Test
    public void testValidCommands_34() throws Exception {
        assertResult(cli, -1, "resolve", "/dir");
    }

    @Test
    public void testValidCommands_35() throws Exception {
        assertResult(cli, 0, "mknode", "/dir");
    }

    @Test
    public void testValidCommands_36() throws Exception {
        assertResult(cli, -1, "resolve", "/dir");
    }

    @Test
    public void testValidCommands_37() throws Exception {
        assertResult(cli, 0, "bind", "-webui", "uriString", "-api", "Api", "/dir");
    }

    @Test
    public void testValidCommands_38() throws Exception {
        assertResult(cli, 0, "resolve", "/dir");
    }

    @Test
    public void testValidCommands_39() throws Exception {
        assertResult(cli, 0, "rm", "/dir");
    }

    @Test
    public void testValidCommands_40() throws Exception {
        assertResult(cli, -1, "resolve", "/dir");
    }

    @Test
    public void testValidCommands_41() throws Exception {
        assertResult(cli, 0, "mknode", "/dir");
    }

    @Test
    public void testValidCommands_42() throws Exception {
        assertResult(cli, -1, "resolve", "/dir");
    }

    @Test
    public void testValidCommands_43() throws Exception {
        assertResult(cli, 0, "bind", "-rest", "uriString", "-api", "Api", "/dir");
    }

    @Test
    public void testValidCommands_44() throws Exception {
        assertResult(cli, 0, "resolve", "/dir");
    }

    @Test
    public void testValidCommands_45() throws Exception {
        assertResult(cli, 0, "rm", "/dir");
    }

    @Test
    public void testValidCommands_46() throws Exception {
        assertResult(cli, -1, "resolve", "/dir");
    }

    @Test
    public void testValidCommands_47() throws Exception {
        assertResult(cli, 0, "rm", "/Nonexitent");
    }
}
