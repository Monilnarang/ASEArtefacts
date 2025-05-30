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

public class KeyMapTest_Purified {

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

    @Test
    public void testTranslate_1() {
        assertEquals("\\\u0007\b\u001b\u001b\f\n\r\t\u000b\u0053\u0045\u2345", translate("\\\\\\a\\b\\e\\E\\f\\n\\r\\t\\v\\123\\x45\\u2345"));
    }

    @Test
    public void testTranslate_2() {
        assertEquals("\u0001\u0001\u0002\u0002\u0003\u0003\u007f^", translate("\\Ca\\CA\\C-B\\C-b^c^C^?^^"));
    }

    @Test
    public void testTranslate_3() {
        assertEquals("\u001b3", translate("'\\e3'"));
    }

    @Test
    public void testTranslate_4() {
        assertEquals("\u001b3", translate("\"\\e3\""));
    }

    @Test
    public void testDisplay_1() {
        assertEquals("\"\\\\^G^H^[^L^J^M^I\\u0098\\u2345\"", display("\\\u0007\b\u001b\f\n\r\t\u0098\u2345"));
    }

    @Test
    public void testDisplay_2() {
        assertEquals("\"^A^B^C^?\\^\\\\\"", display("\u0001\u0002\u0003\u007f^\\"));
    }
}
