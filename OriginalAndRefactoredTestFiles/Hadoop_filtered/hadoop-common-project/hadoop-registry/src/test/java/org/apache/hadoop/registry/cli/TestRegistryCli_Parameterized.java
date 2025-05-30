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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestRegistryCli_Parameterized extends AbstractRegistryTest {

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

    @ParameterizedTest
    @MethodSource("Provider_testInvalidNumArgs_1_3_5_7_9")
    public void testInvalidNumArgs_1_3_5_7_9(String param1, int param2) throws Exception {
        assertResult(cli, -param2, param1);
    }

    static public Stream<Arguments> Provider_testInvalidNumArgs_1_3_5_7_9() {
        return Stream.of(arguments("ls", 1), arguments("resolve", 1), arguments("mknode", 1), arguments("rm", 1), arguments("bind", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvalidNumArgs_2_4_6_8_11_17_21")
    public void testInvalidNumArgs_2_4_6_8_11_17_21(String param1, String param2, String param3, int param4) throws Exception {
        assertResult(cli, -param4, param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testInvalidNumArgs_2_4_6_8_11_17_21() {
        return Stream.of(arguments("ls", "/path", "/extraPath", 1), arguments("resolve", "/path", "/extraPath", 1), arguments("mknode", "/path", "/extraPath", 1), arguments("rm", "/path", "/extraPath", 1), arguments("bind", "-inet", "foo", 1), arguments("bind", "-webui", "foo", 1), arguments("bind", "-rest", "foo", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvalidNumArgs_1_1to2_2to3_3to4_4_4to8_8_10_12_14_18_22_26_28_30_34_36_40_42_46")
    public void testInvalidNumArgs_1_1to2_2to3_3to4_4_4to8_8_10_12_14_18_22_26_28_30_34_36_40_42_46(String param1, String param2, int param3) throws Exception {
        assertResult(cli, -param3, param1, param2);
    }

    static public Stream<Arguments> Provider_testInvalidNumArgs_1_1to2_2to3_3to4_4_4to8_8_10_12_14_18_22_26_28_30_34_36_40_42_46() {
        return Stream.of(arguments("bind", "foo", 1), arguments("ls", "NonSlashPath", 1), arguments("ls", "//", 1), arguments("resolve", "NonSlashPath", 1), arguments("resolve", "//", 1), arguments("mknode", "NonSlashPath", 1), arguments("mknode", "//", 1), arguments("rm", "NonSlashPath", 1), arguments("rm", "//", 1), arguments("ls", "/nonexisting_path", 1), arguments("ls", "/NonExistingDir/nonexisting_path", 1), arguments("resolve", "/nonexisting_path", 1), arguments("resolve", "/NonExistingDir/nonexisting_path", 1), arguments("resolve", "/foo", 1), arguments("resolve", "/foo", 1), arguments("resolve", "/foo", 1), arguments("resolve", "/subdir", 1), arguments("resolve", "/subdir/foo", 1), arguments("resolve", "/subdir/foo", 1), arguments("resolve", "/subdir/foo", 1), arguments("resolve", "/subdir", 1), arguments("resolve", "/dir", 1), arguments("resolve", "/dir", 1), arguments("resolve", "/dir", 1), arguments("resolve", "/dir", 1), arguments("resolve", "/dir", 1), arguments("resolve", "/dir", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvalidNumArgs_12to16")
    public void testInvalidNumArgs_12to16(String param1, String param2, String param3, String param4, int param5, String param6, String param7, String param8, int param9) throws Exception {
        assertResult(cli, -param9, param1, param2, param3, param4, param5, param6, param7, param8);
    }

    static public Stream<Arguments> Provider_testInvalidNumArgs_12to16() {
        return Stream.of(arguments("bind", "-inet", "-api", "-p", 378, "-h", "host", "/foo", 1), arguments("bind", "-inet", "-api", "Api", "-p", "-h", "host", "/foo", 1), arguments("bind", "-inet", "-api", "Api", "-p", 378, "-h", "/foo", 1), arguments("bind", "-inet", "-api", "Api", "-p", 378, "-h", "host", 1), arguments("bind", "-api", "Api", "-p", 378, "-h", "host", "/foo", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvalidNumArgs_18to20_22to25")
    public void testInvalidNumArgs_18to20_22to25(String param1, String param2, String param3, String param4, String param5, int param6) throws Exception {
        assertResult(cli, -param6, param1, param2, param3, param4, param5);
    }

    static public Stream<Arguments> Provider_testInvalidNumArgs_18to20_22to25() {
        return Stream.of(arguments("bind", "-webui", "-api", "Api", "/foo", 1), arguments("bind", "-webui", "uriString", "-api", "/foo", 1), arguments("bind", "-webui", "uriString", "-api", "Api", 1), arguments("bind", "-rest", "uriString", "-api", "Api", 1), arguments("bind", "-rest", "-api", "Api", "/foo", 1), arguments("bind", "-rest", "uriString", "-api", "/foo", 1), arguments("bind", "uriString", "-api", "Api", "/foo", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testBadPath_5_9to10")
    public void testBadPath_5_9to10(String param1, String param2, String param3, String param4, String param5, int param6, String param7, String param8, String param9, int param10) throws Exception {
        assertResult(cli, -param10, param1, param2, param3, param4, param5, param6, param7, param8, param9);
    }

    static public Stream<Arguments> Provider_testBadPath_5_9to10() {
        return Stream.of(arguments("bind", "-inet", "-api", "Api", "-p", 378, "-h", "host", "NonSlashPath", 1), arguments("bind", "-inet", "-api", "Api", "-p", 378, "-h", "host", "//", 1), arguments("bind", "-inet", "-api", "Api", "-p", 378, "-h", "host", "/NonExistingDir/nonexisting_path", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testBadPath_6to7_11to14")
    public void testBadPath_6to7_11to14(String param1, String param2, String param3, String param4, String param5, String param6, int param7) throws Exception {
        assertResult(cli, -param7, param1, param2, param3, param4, param5, param6);
    }

    static public Stream<Arguments> Provider_testBadPath_6to7_11to14() {
        return Stream.of(arguments("bind", "-webui", "uriString", "-api", "Api", "NonSlashPath", 1), arguments("bind", "-webui", "uriString", "-api", "Api", "//", 1), arguments("bind", "-rest", "uriString", "-api", "Api", "NonSlashPath", 1), arguments("bind", "-rest", "uriString", "-api", "Api", "//", 1), arguments("bind", "-webui", "uriString", "-api", "Api", "/NonExistingDir/nonexisting_path", 1), arguments("bind", "-rest", "uriString", "-api", "Api", "/NonExistingDir/nonexisting_path", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidCommands_1_15_31")
    public void testValidCommands_1_15_31(int param1, String param2, String param3, String param4, String param5, String param6, int param7, String param8, String param9, String param10) throws Exception {
        assertResult(cli, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
    }

    static public Stream<Arguments> Provider_testValidCommands_1_15_31() {
        return Stream.of(arguments(0, "bind", "-inet", "-api", "Api", "-p", 378, "-h", "host", "/foo"), arguments(0, "bind", "-inet", "-api", "Api", "-p", 378, "-h", "host", "/subdir/foo"), arguments(0, "bind", "-inet", "-api", "Api", "-p", 378, "-h", "host", "/dir"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidCommands_2to3_6to7_10to11_13_16to17_20to21_24to25_27_29_32to33_35_38to39_41_44to45_47")
    public void testValidCommands_2to3_6to7_10to11_13_16to17_20to21_24to25_27_29_32to33_35_38to39_41_44to45_47(int param1, String param2, String param3) throws Exception {
        assertResult(cli, param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testValidCommands_2to3_6to7_10to11_13_16to17_20to21_24to25_27_29_32to33_35_38to39_41_44to45_47() {
        return Stream.of(arguments(0, "resolve", "/foo"), arguments(0, "rm", "/foo"), arguments(0, "resolve", "/foo"), arguments(0, "rm", "/foo"), arguments(0, "resolve", "/foo"), arguments(0, "rm", "/foo"), arguments(0, "mknode", "/subdir"), arguments(0, "resolve", "/subdir/foo"), arguments(0, "rm", "/subdir/foo"), arguments(0, "resolve", "/subdir/foo"), arguments(0, "rm", "/subdir/foo"), arguments(0, "resolve", "/subdir/foo"), arguments(0, "rm", "/subdir/foo"), arguments(0, "rm", "/subdir"), arguments(0, "mknode", "/dir"), arguments(0, "resolve", "/dir"), arguments(0, "rm", "/dir"), arguments(0, "mknode", "/dir"), arguments(0, "resolve", "/dir"), arguments(0, "rm", "/dir"), arguments(0, "mknode", "/dir"), arguments(0, "resolve", "/dir"), arguments(0, "rm", "/dir"), arguments(0, "rm", "/Nonexitent"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidCommands_5_9_19_23_37_43")
    public void testValidCommands_5_9_19_23_37_43(int param1, String param2, String param3, String param4, String param5, String param6, String param7) throws Exception {
        assertResult(cli, param1, param2, param3, param4, param5, param6, param7);
    }

    static public Stream<Arguments> Provider_testValidCommands_5_9_19_23_37_43() {
        return Stream.of(arguments(0, "bind", "-webui", "uriString", "-api", "Api", "/foo"), arguments(0, "bind", "-rest", "uriString", "-api", "Api", "/foo"), arguments(0, "bind", "-webui", "uriString", "-api", "Api", "/subdir/foo"), arguments(0, "bind", "-rest", "uriString", "-api", "Api", "/subdir/foo"), arguments(0, "bind", "-webui", "uriString", "-api", "Api", "/dir"), arguments(0, "bind", "-rest", "uriString", "-api", "Api", "/dir"));
    }
}
