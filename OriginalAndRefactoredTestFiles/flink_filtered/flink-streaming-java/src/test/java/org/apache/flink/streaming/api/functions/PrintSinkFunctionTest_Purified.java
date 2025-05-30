package org.apache.flink.streaming.api.functions;

import org.apache.flink.api.common.functions.DefaultOpenContext;
import org.apache.flink.streaming.api.functions.sink.SinkContextUtil;
import org.apache.flink.streaming.api.functions.sink.legacy.PrintSinkFunction;
import org.apache.flink.streaming.util.MockStreamingRuntimeContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.assertj.core.api.Assertions.assertThat;

class PrintSinkFunctionTest_Purified {

    private final PrintStream originalSystemOut = System.out;

    private final PrintStream originalSystemErr = System.err;

    private final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

    private final ByteArrayOutputStream arrayErrorStream = new ByteArrayOutputStream();

    private final String line = System.lineSeparator();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(arrayOutputStream));
        System.setErr(new PrintStream(arrayErrorStream));
    }

    @AfterEach
    void tearDown() {
        if (System.out != originalSystemOut) {
            System.out.close();
        }
        if (System.err != originalSystemErr) {
            System.err.close();
        }
        System.setOut(originalSystemOut);
        System.setErr(originalSystemErr);
    }

    @Test
    void testPrintSinkStdOut_1() throws Exception {
        PrintSinkFunction<String> printSink = new PrintSinkFunction<>();
        printSink.setRuntimeContext(new MockStreamingRuntimeContext(false, 1, 0));
        printSink.open(DefaultOpenContext.INSTANCE);
        printSink.invoke("hello world!", SinkContextUtil.forTimestamp(0));
        assertThat(printSink).hasToString("Print to System.out");
    }

    @Test
    void testPrintSinkStdOut_2() throws Exception {
        assertThat(arrayOutputStream).hasToString("hello world!" + line);
    }

    @Test
    void testPrintSinkStdErr_1() throws Exception {
        PrintSinkFunction<String> printSink = new PrintSinkFunction<>(true);
        printSink.setRuntimeContext(new MockStreamingRuntimeContext(false, 1, 0));
        printSink.open(DefaultOpenContext.INSTANCE);
        printSink.invoke("hello world!", SinkContextUtil.forTimestamp(0));
        assertThat(printSink).hasToString("Print to System.err");
    }

    @Test
    void testPrintSinkStdErr_2() throws Exception {
        assertThat(arrayErrorStream).hasToString("hello world!" + line);
    }

    @Test
    void testPrintSinkWithPrefix_1() throws Exception {
        PrintSinkFunction<String> printSink = new PrintSinkFunction<>();
        printSink.setRuntimeContext(new MockStreamingRuntimeContext(false, 2, 1));
        printSink.open(DefaultOpenContext.INSTANCE);
        printSink.invoke("hello world!", SinkContextUtil.forTimestamp(0));
        assertThat(printSink).hasToString("Print to System.out");
    }

    @Test
    void testPrintSinkWithPrefix_2() throws Exception {
        assertThat(arrayOutputStream).hasToString("2> hello world!" + line);
    }

    @Test
    void testPrintSinkWithIdentifierAndPrefix_1() throws Exception {
        PrintSinkFunction<String> printSink = new PrintSinkFunction<>("mySink", false);
        printSink.setRuntimeContext(new MockStreamingRuntimeContext(false, 2, 1));
        printSink.open(DefaultOpenContext.INSTANCE);
        printSink.invoke("hello world!", SinkContextUtil.forTimestamp(0));
        assertThat(printSink).hasToString("Print to System.out");
    }

    @Test
    void testPrintSinkWithIdentifierAndPrefix_2() throws Exception {
        assertThat(arrayOutputStream).hasToString("mySink:2> hello world!" + line);
    }

    @Test
    void testPrintSinkWithIdentifierButNoPrefix_1() throws Exception {
        PrintSinkFunction<String> printSink = new PrintSinkFunction<>("mySink", false);
        printSink.setRuntimeContext(new MockStreamingRuntimeContext(false, 1, 0));
        printSink.open(DefaultOpenContext.INSTANCE);
        printSink.invoke("hello world!", SinkContextUtil.forTimestamp(0));
        assertThat(printSink).hasToString("Print to System.out");
    }

    @Test
    void testPrintSinkWithIdentifierButNoPrefix_2() throws Exception {
        assertThat(arrayOutputStream).hasToString("mySink> hello world!" + line);
    }
}
