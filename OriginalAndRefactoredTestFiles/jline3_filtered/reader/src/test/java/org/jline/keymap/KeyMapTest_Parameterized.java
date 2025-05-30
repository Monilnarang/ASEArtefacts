package org.jline.keymap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jline.reader.Binding;
import org.jline.reader.Reference;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.reader.impl.ReaderTestSupport.EofPipedInputStream;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.impl.DumbTerminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.jline.keymap.KeyMap.alt;
import static org.jline.keymap.KeyMap.display;
import static org.jline.keymap.KeyMap.range;
import static org.jline.keymap.KeyMap.translate;
import static org.jline.reader.LineReader.ACCEPT_LINE;
import static org.jline.reader.LineReader.BACKWARD_WORD;
import static org.jline.reader.LineReader.COMPLETE_WORD;
import static org.jline.reader.LineReader.DOWN_HISTORY;
import static org.jline.reader.LineReader.KILL_WHOLE_LINE;
import static org.jline.reader.LineReader.SEND_BREAK;
import static org.jline.reader.LineReader.UP_HISTORY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class KeyMapTest_Parameterized {

    protected Terminal terminal;

    protected EofPipedInputStream in;

    protected ByteArrayOutputStream out;

    @BeforeEach
    public void setUp() throws Exception {
        Handler ch = new ConsoleHandler();
        ch.setLevel(Level.FINEST);
        Logger logger = Logger.getLogger("org.jline");
        logger.addHandler(ch);
        logger.setLevel(Level.INFO);
        in = new EofPipedInputStream();
        out = new ByteArrayOutputStream();
        terminal = new DumbTerminal(in, out);
        terminal.setSize(new Size(160, 80));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTranslate_1to4")
    public void testTranslate_1to4(String param1, String param2) {
        assertEquals(param1, translate(param2));
    }

    static public Stream<Arguments> Provider_testTranslate_1to4() {
        return Stream.of(arguments("\\\u0007\b\u001b\u001b\f\n\r\t\u000b\u0053\u0045\u2345", "\\\\\\a\\b\\e\\E\\f\\n\\r\\t\\v\\123\\x45\\u2345"), arguments("\u0001\u0001\u0002\u0002\u0003\u0003\u007f^", "\\Ca\\CA\\C-B\\C-b^c^C^?^^"), arguments("\u001b3", "'\\e3'"), arguments("\u001b3", "\"\\e3\""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDisplay_1to2")
    public void testDisplay_1to2(String param1, String param2) {
        assertEquals(param1, display(param2));
    }

    static public Stream<Arguments> Provider_testDisplay_1to2() {
        return Stream.of(arguments("\"\\\\^G^H^[^L^J^M^I\\u0098\\u2345\"", "\\\u0007\b\u001b\f\n\r\t\u0098\u2345"), arguments("\"^A^B^C^?\\^\\\\\"", "\u0001\u0002\u0003\u007f^\\"));
    }
}
