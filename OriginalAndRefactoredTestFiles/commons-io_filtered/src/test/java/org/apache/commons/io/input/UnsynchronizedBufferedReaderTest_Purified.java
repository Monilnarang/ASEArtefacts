package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.function.Supplier;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class UnsynchronizedBufferedReaderTest_Purified {

    private UnsynchronizedBufferedReader br;

    private final String testString = "Test_All_Tests\nTest_java_io_BufferedInputStream\nTest_java_io_BufferedOutputStream\nTest_java_io_ByteArrayInputStream\n" + "Test_java_io_ByteArrayOutputStream\nTest_java_io_DataInputStream\nTest_java_io_File\nTest_java_io_FileDescriptor\nTest_java_io_FileInputStream\n" + "Test_java_io_FileNotFoundException\nTest_java_io_FileOutputStream\nTest_java_io_FilterInputStream\nTest_java_io_FilterOutputStream\n" + "Test_java_io_InputStream\nTest_java_io_IOException\nTest_java_io_OutputStream\nTest_java_io_PrintStream\nTest_java_io_RandomAccessFile\n" + "Test_java_io_SyncFailedException\nTest_java_lang_AbstractMethodError\nTest_java_lang_ArithmeticException\n" + "Test_java_lang_ArrayIndexOutOfBoundsException\nTest_java_lang_ArrayStoreException\nTest_java_lang_Boolean\nTest_java_lang_Byte\n" + "Test_java_lang_Character\nTest_java_lang_Class\nTest_java_lang_ClassCastException\nTest_java_lang_ClassCircularityError\n" + "Test_java_lang_ClassFormatError\nTest_java_lang_ClassLoader\nTest_java_lang_ClassNotFoundException\nTest_java_lang_CloneNotSupportedException\n" + "Test_java_lang_Double\nTest_java_lang_Error\nTest_java_lang_Exception\nTest_java_lang_ExceptionInInitializerError\nTest_java_lang_Float\n" + "Test_java_lang_IllegalAccessError\nTest_java_lang_IllegalAccessException\nTest_java_lang_IllegalArgumentException\n" + "Test_java_lang_IllegalMonitorStateException\nTest_java_lang_IllegalThreadStateException\nTest_java_lang_IncompatibleClassChangeError\n" + "Test_java_lang_IndexOutOfBoundsException\nTest_java_lang_InstantiationError\nTest_java_lang_InstantiationException\nTest_java_lang_Integer\n" + "Test_java_lang_InternalError\nTest_java_lang_InterruptedException\nTest_java_lang_LinkageError\nTest_java_lang_Long\nTest_java_lang_Math\n" + "Test_java_lang_NegativeArraySizeException\nTest_java_lang_NoClassDefFoundError\nTest_java_lang_NoSuchFieldError\n" + "Test_java_lang_NoSuchMethodError\nTest_java_lang_NullPointerException\nTest_java_lang_Number\nTest_java_lang_NumberFormatException\n" + "Test_java_lang_Object\nTest_java_lang_OutOfMemoryError\nTest_java_lang_RuntimeException\nTest_java_lang_SecurityManager\nTest_java_lang_Short\n" + "Test_java_lang_StackOverflowError\nTest_java_lang_String\nTest_java_lang_StringBuffer\nTest_java_lang_StringIndexOutOfBoundsException\n" + "Test_java_lang_System\nTest_java_lang_Thread\nTest_java_lang_ThreadDeath\nTest_java_lang_ThreadGroup\nTest_java_lang_Throwable\n" + "Test_java_lang_UnknownError\nTest_java_lang_UnsatisfiedLinkError\nTest_java_lang_VerifyError\nTest_java_lang_VirtualMachineError\n" + "Test_java_lang_vm_Image\nTest_java_lang_vm_MemorySegment\nTest_java_lang_vm_ROMStoreException\nTest_java_lang_vm_VM\nTest_java_lang_Void\n" + "Test_java_net_BindException\nTest_java_net_ConnectException\nTest_java_net_DatagramPacket\nTest_java_net_DatagramSocket\n" + "Test_java_net_DatagramSocketImpl\nTest_java_net_InetAddress\nTest_java_net_NoRouteToHostException\nTest_java_net_PlainDatagramSocketImpl\n" + "Test_java_net_PlainSocketImpl\nTest_java_net_Socket\nTest_java_net_SocketException\nTest_java_net_SocketImpl\nTest_java_net_SocketInputStream\n" + "Test_java_net_SocketOutputStream\nTest_java_net_UnknownHostException\nTest_java_util_ArrayEnumerator\nTest_java_util_Date\n" + "Test_java_util_EventObject\nTest_java_util_HashEnumerator\nTest_java_util_Hashtable\nTest_java_util_Properties\nTest_java_util_ResourceBundle\n" + "Test_java_util_tm\nTest_java_util_Vector\n";

    @AfterEach
    protected void afterEach() {
        IOUtils.closeQuietly(br);
    }

    private void assertLines(final String input, final String... lines) throws IOException {
        assertReadLines(input, lines);
        assertPeek(input, lines);
    }

    private void assertPeek(final String input, final String... lines) throws IOException {
        try (UnsynchronizedBufferedReader bufferedReader = new UnsynchronizedBufferedReader(new StringReader(input))) {
            for (final String line : lines) {
                final char[] bufAFull = new char[line.length()];
                assertEquals(bufAFull.length, bufferedReader.peek(bufAFull));
                assertArrayEquals(line.toCharArray(), bufAFull);
                if (!line.isEmpty()) {
                    assertEquals(line.charAt(0), bufferedReader.peek());
                    for (int peekLen = 0; peekLen < line.length(); peekLen++) {
                        assertPeekArray(bufferedReader, peekLen, line);
                    }
                }
                assertEquals(line, bufferedReader.readLine());
            }
            assertNull(bufferedReader.readLine());
        }
    }

    private void assertPeekArray(final UnsynchronizedBufferedReader bufferedReader, final int peekLen, final String line) throws IOException {
        final char[] expectedBuf = new char[peekLen];
        final int srcPeekLen = Math.min(peekLen, line.length());
        line.getChars(0, srcPeekLen, expectedBuf, 0);
        final char[] actualBuf = new char[peekLen];
        final Supplier<String> msg = () -> String.format("len=%,d, line='%s'", peekLen, line);
        assertEquals(actualBuf.length, bufferedReader.peek(actualBuf), msg);
        assertArrayEquals(expectedBuf, actualBuf, msg);
    }

    private void assertReadLines(final String input, final String... lines) throws IOException {
        try (UnsynchronizedBufferedReader bufferedReader = new UnsynchronizedBufferedReader(new StringReader(input))) {
            for (final String line : lines) {
                assertEquals(line, bufferedReader.readLine());
            }
            assertNull(bufferedReader.readLine());
        }
    }

    @Test
    public void testReadLineSeparators_1() throws IOException {
        assertLines("A\nB\nC", "A", "B", "C");
    }

    @Test
    public void testReadLineSeparators_2() throws IOException {
        assertLines("A\rB\rC", "A", "B", "C");
    }

    @Test
    public void testReadLineSeparators_3() throws IOException {
        assertLines("A\r\nB\r\nC", "A", "B", "C");
    }

    @Test
    public void testReadLineSeparators_4() throws IOException {
        assertLines("A\n\rB\n\rC", "A", "", "B", "", "C");
    }

    @Test
    public void testReadLineSeparators_5() throws IOException {
        assertLines("A\n\nB\n\nC", "A", "", "B", "", "C");
    }

    @Test
    public void testReadLineSeparators_6() throws IOException {
        assertLines("A\r\rB\r\rC", "A", "", "B", "", "C");
    }

    @Test
    public void testReadLineSeparators_7() throws IOException {
        assertLines("A\n\n", "A", "");
    }

    @Test
    public void testReadLineSeparators_8() throws IOException {
        assertLines("A\n\r", "A", "");
    }

    @Test
    public void testReadLineSeparators_9() throws IOException {
        assertLines("A\r\r", "A", "");
    }

    @Test
    public void testReadLineSeparators_10() throws IOException {
        assertLines("A\r\n", "A");
    }

    @Test
    public void testReadLineSeparators_11() throws IOException {
        assertLines("A\r\n\r\n", "A", "");
    }
}
