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

@SuppressWarnings("deprecation")
public class ValueTest_Purified {

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

    @Test
    public void testLongNoArg_1() {
        assertTrue(cl.hasOption("c"));
    }

    @Test
    public void testLongNoArg_2() {
        assertNull(cl.getOptionValue("c"));
    }

    @Test
    public void testLongNoArgWithOption_1() {
        assertTrue(cl.hasOption(opts.getOption("c")));
    }

    @Test
    public void testLongNoArgWithOption_2() {
        assertNull(cl.getOptionValue(opts.getOption("c")));
    }

    @Test
    public void testLongWithArg_1() {
        assertTrue(cl.hasOption("d"));
    }

    @Test
    public void testLongWithArg_2() {
        assertNotNull(cl.getOptionValue("d"));
    }

    @Test
    public void testLongWithArg_3() {
        assertEquals(cl.getOptionValue("d"), "bar");
    }

    @Test
    public void testLongWithArgWithOption_1() {
        assertTrue(cl.hasOption(opts.getOption("d")));
    }

    @Test
    public void testLongWithArgWithOption_2() {
        assertNotNull(cl.getOptionValue(opts.getOption("d")));
    }

    @Test
    public void testLongWithArgWithOption_3() {
        assertEquals(cl.getOptionValue(opts.getOption("d")), "bar");
    }

    @Test
    public void testShortNoArg_1() {
        assertTrue(cl.hasOption("a"));
    }

    @Test
    public void testShortNoArg_2() {
        assertNull(cl.getOptionValue("a"));
    }

    @Test
    public void testShortNoArgWithOption_1() {
        assertTrue(cl.hasOption(opts.getOption("a")));
    }

    @Test
    public void testShortNoArgWithOption_2() {
        assertNull(cl.getOptionValue(opts.getOption("a")));
    }

    @Test
    public void testShortWithArg_1() {
        assertTrue(cl.hasOption("b"));
    }

    @Test
    public void testShortWithArg_2() {
        assertNotNull(cl.getOptionValue("b"));
    }

    @Test
    public void testShortWithArg_3() {
        assertEquals(cl.getOptionValue("b"), "foo");
    }

    @Test
    public void testShortWithArgWithOption_1() {
        assertTrue(cl.hasOption(opts.getOption("b")));
    }

    @Test
    public void testShortWithArgWithOption_2() {
        assertNotNull(cl.getOptionValue(opts.getOption("b")));
    }

    @Test
    public void testShortWithArgWithOption_3() {
        assertEquals(cl.getOptionValue(opts.getOption("b")), "foo");
    }
}
