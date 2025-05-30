package org.apache.hadoop.io;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestBooleanWritable_Parameterized {

    private int compare(WritableComparator writableComparator, DataOutputBuffer buf1, DataOutputBuffer buf2) {
        return writableComparator.compare(buf1.getData(), 0, buf1.size(), buf2.getData(), 0, buf2.size());
    }

    protected DataOutputBuffer writeWritable(Writable writable) throws IOException {
        DataOutputBuffer out = new DataOutputBuffer(1024);
        writable.write(out);
        out.flush();
        return out;
    }

    private boolean checkHashCode(BooleanWritable f, BooleanWritable s) {
        return f.hashCode() == s.hashCode();
    }

    private static BooleanWritable newInstance(boolean flag) {
        return new BooleanWritable(flag);
    }

    @Test
    public void testCommonMethods_3() {
        assertFalse("testCommonMethods3 error !!!", newInstance(false).equals(newInstance(true)));
    }

    @Test
    public void testCommonMethods_4() {
        assertTrue("testCommonMethods4 error !!!", checkHashCode(newInstance(true), newInstance(true)));
    }

    @Test
    public void testCommonMethods_5() {
        assertFalse("testCommonMethods5 error !!! ", checkHashCode(newInstance(true), newInstance(false)));
    }

    @Test
    public void testCommonMethods_9() {
        assertEquals("testCommonMethods9 error !!!", "true", newInstance(true).toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testCommonMethods_1to2")
    public void testCommonMethods_1to2(String param1) {
        assertTrue(param1, newInstance(true).equals(newInstance(true)));
    }

    static public Stream<Arguments> Provider_testCommonMethods_1to2() {
        return Stream.of(arguments("testCommonMethods1 error !!!"), arguments("testCommonMethods2 error  !!!"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCommonMethods_6to8")
    public void testCommonMethods_6to8(String param1, int param2) {
        assertTrue(param1, newInstance(true).compareTo(newInstance(false)) > param2);
    }

    static public Stream<Arguments> Provider_testCommonMethods_6to8() {
        return Stream.of(arguments("testCommonMethods6 error !!!", 0), arguments("testCommonMethods7 error !!!", 0), arguments("testCommonMethods8 error !!!", 0));
    }
}
