package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("deprecation")
public class ValueTest_Parameterized {

    private static final Option NULL_OPTION = null;

    private static final String NULL_STRING = null;

    protected static Stream<CommandLineParser> parsers() {
        return Stream.of(new DefaultParser(), new PosixParser());
    }

    private final Options opts = new Options();

    private CommandLine cl;

    @BeforeEach
    public void setUp() throws Exception {
        opts.addOption("a", false, "toggle -a");
        opts.addOption("b", true, "set -b");
        opts.addOption("c", "c", false, "toggle -c");
        opts.addOption("d", "d", true, "set -d");
        opts.addOption(OptionBuilder.hasOptionalArg().create('e'));
        opts.addOption(OptionBuilder.hasOptionalArg().withLongOpt("fish").create());
        opts.addOption(OptionBuilder.hasOptionalArgs().withLongOpt("gravy").create());
        opts.addOption(OptionBuilder.hasOptionalArgs(2).withLongOpt("hide").create());
        opts.addOption(OptionBuilder.hasOptionalArgs(2).create('i'));
        opts.addOption(OptionBuilder.hasOptionalArgs().create('j'));
        opts.addOption(Option.builder().option("v").hasArg().valueSeparator().build());
        final String[] args = { "-a", "-b", "foo", "--c", "--d", "bar" };
        cl = new PosixParser().parse(opts, args);
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongNoArg_1_1_1_1")
    public void testLongNoArg_1_1_1_1(String param1) {
        assertTrue(cl.hasOption(param1));
    }

    static public Stream<Arguments> Provider_testLongNoArg_1_1_1_1() {
        return Stream.of(arguments("c"), arguments("d"), arguments("a"), arguments("b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongNoArg_2_2")
    public void testLongNoArg_2_2(String param1) {
        assertNull(cl.getOptionValue(param1));
    }

    static public Stream<Arguments> Provider_testLongNoArg_2_2() {
        return Stream.of(arguments("c"), arguments("a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongNoArgWithOption_1_1_1_1")
    public void testLongNoArgWithOption_1_1_1_1(String param1) {
        assertTrue(cl.hasOption(opts.getOption(param1)));
    }

    static public Stream<Arguments> Provider_testLongNoArgWithOption_1_1_1_1() {
        return Stream.of(arguments("c"), arguments("d"), arguments("a"), arguments("b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongNoArgWithOption_2_2")
    public void testLongNoArgWithOption_2_2(String param1) {
        assertNull(cl.getOptionValue(opts.getOption(param1)));
    }

    static public Stream<Arguments> Provider_testLongNoArgWithOption_2_2() {
        return Stream.of(arguments("c"), arguments("a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongWithArg_2_2")
    public void testLongWithArg_2_2(String param1) {
        assertNotNull(cl.getOptionValue(param1));
    }

    static public Stream<Arguments> Provider_testLongWithArg_2_2() {
        return Stream.of(arguments("d"), arguments("b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongWithArg_3_3")
    public void testLongWithArg_3_3(String param1, String param2) {
        assertEquals(cl.getOptionValue(param2), param1);
    }

    static public Stream<Arguments> Provider_testLongWithArg_3_3() {
        return Stream.of(arguments("bar", "d"), arguments("foo", "b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongWithArgWithOption_2_2")
    public void testLongWithArgWithOption_2_2(String param1) {
        assertNotNull(cl.getOptionValue(opts.getOption(param1)));
    }

    static public Stream<Arguments> Provider_testLongWithArgWithOption_2_2() {
        return Stream.of(arguments("d"), arguments("b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongWithArgWithOption_3_3")
    public void testLongWithArgWithOption_3_3(String param1, String param2) {
        assertEquals(cl.getOptionValue(opts.getOption(param2)), param1);
    }

    static public Stream<Arguments> Provider_testLongWithArgWithOption_3_3() {
        return Stream.of(arguments("bar", "d"), arguments("foo", "b"));
    }
}
