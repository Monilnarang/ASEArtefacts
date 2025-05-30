package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("deprecation")
public class ValuesTest_Parameterized {

    private CommandLine cmd;

    @BeforeEach
    public void setUp() throws Exception {
        final Options options = new Options();
        options.addOption("a", false, "toggle -a");
        options.addOption("b", true, "set -b");
        options.addOption("c", "c", false, "toggle -c");
        options.addOption("d", "d", true, "set -d");
        options.addOption(OptionBuilder.withLongOpt("e").hasArgs().withDescription("set -e ").create('e'));
        options.addOption("f", "f", false, "jk");
        options.addOption(OptionBuilder.withLongOpt("g").hasArgs(2).withDescription("set -g").create('g'));
        options.addOption(OptionBuilder.withLongOpt("h").hasArg().withDescription("set -h").create('h'));
        options.addOption(OptionBuilder.withLongOpt("i").withDescription("set -i").create('i'));
        options.addOption(OptionBuilder.withLongOpt("j").hasArgs().withDescription("set -j").withValueSeparator('=').create('j'));
        options.addOption(OptionBuilder.withLongOpt("k").hasArgs().withDescription("set -k").withValueSeparator('=').create('k'));
        options.addOption(OptionBuilder.withLongOpt("m").hasArgs().withDescription("set -m").withValueSeparator().create('m'));
        final String[] args = { "-a", "-b", "foo", "--c", "--d", "bar", "-e", "one", "two", "-f", "arg1", "arg2", "-g", "val1", "val2", "arg3", "-h", "val1", "-i", "-h", "val2", "-jkey=value", "-j", "key=value", "-kkey1=value1", "-kkey2=value2", "-mkey=value" };
        final CommandLineParser parser = new PosixParser();
        cmd = parser.parse(options, args);
    }

    @ParameterizedTest
    @MethodSource("Provider_testShortArgs_1_1to2_4")
    public void testShortArgs_1_1to2_4(String param1, String param2) {
        assertTrue(cmd.hasOption(param2), param1);
    }

    static public Stream<Arguments> Provider_testShortArgs_1_1to2_4() {
        return Stream.of(arguments("Option a is not set", "a"), arguments("Option c is not set", "c"), arguments("Option b is not set", "b"), arguments("Option b is not set", "b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testShortArgs_3to4")
    public void testShortArgs_3to4(String param1) {
        assertNull(cmd.getOptionValues(param1));
    }

    static public Stream<Arguments> Provider_testShortArgs_3to4() {
        return Stream.of(arguments("a"), arguments("c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testShortArgsWithValue_2_5")
    public void testShortArgsWithValue_2_5(String param1, String param2) {
        assertEquals(param1, cmd.getOptionValue(param2));
    }

    static public Stream<Arguments> Provider_testShortArgsWithValue_2_5() {
        return Stream.of(arguments("foo", "b"), arguments("bar", "d"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testShortArgsWithValue_3_6")
    public void testShortArgsWithValue_3_6(int param1, String param2) {
        assertEquals(param1, cmd.getOptionValues(param2).length);
    }

    static public Stream<Arguments> Provider_testShortArgsWithValue_3_6() {
        return Stream.of(arguments(1, "b"), arguments(1, "d"));
    }
}
