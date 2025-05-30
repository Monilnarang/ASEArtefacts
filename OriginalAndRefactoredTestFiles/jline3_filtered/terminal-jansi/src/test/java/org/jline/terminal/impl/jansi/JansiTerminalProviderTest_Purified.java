package org.jline.terminal.impl.jansi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;
import org.jline.terminal.Terminal;
import org.jline.terminal.spi.SystemStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JansiTerminalProviderTest_Purified {

    @Test
    public void testJansiVersion_1() {
        assertEquals(2, JansiTerminalProvider.JANSI_MAJOR_VERSION);
    }

    @Test
    public void testJansiVersion_2() {
        assertEquals(4, JansiTerminalProvider.JANSI_MINOR_VERSION);
    }
}
