package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("deprecation")
public class ValuesTest_Purified {

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

    @Test
    public void testShortArgs_1() {
        assertTrue(cmd.hasOption("a"), "Option a is not set");
    }

    @Test
    public void testShortArgs_2() {
        assertTrue(cmd.hasOption("c"), "Option c is not set");
    }

    @Test
    public void testShortArgs_3() {
        assertNull(cmd.getOptionValues("a"));
    }

    @Test
    public void testShortArgs_4() {
        assertNull(cmd.getOptionValues("c"));
    }

    @Test
    public void testShortArgsWithValue_1() {
        assertTrue(cmd.hasOption("b"), "Option b is not set");
    }

    @Test
    public void testShortArgsWithValue_2() {
        assertEquals("foo", cmd.getOptionValue("b"));
    }

    @Test
    public void testShortArgsWithValue_3() {
        assertEquals(1, cmd.getOptionValues("b").length);
    }

    @Test
    public void testShortArgsWithValue_4() {
        assertTrue(cmd.hasOption("b"), "Option b is not set");
    }

    @Test
    public void testShortArgsWithValue_5() {
        assertEquals("bar", cmd.getOptionValue("d"));
    }

    @Test
    public void testShortArgsWithValue_6() {
        assertEquals(1, cmd.getOptionValues("d").length);
    }
}
