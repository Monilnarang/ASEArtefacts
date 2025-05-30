package org.apache.hadoop.io;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestBooleanWritable_Purified {

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
    public void testCommonMethods_1() {
        assertTrue("testCommonMethods1 error !!!", newInstance(true).equals(newInstance(true)));
    }

    @Test
    public void testCommonMethods_2() {
        assertTrue("testCommonMethods2 error  !!!", newInstance(false).equals(newInstance(false)));
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
    public void testCommonMethods_6() {
        assertTrue("testCommonMethods6 error !!!", newInstance(true).compareTo(newInstance(false)) > 0);
    }

    @Test
    public void testCommonMethods_7() {
        assertTrue("testCommonMethods7 error !!!", newInstance(false).compareTo(newInstance(true)) < 0);
    }

    @Test
    public void testCommonMethods_8() {
        assertTrue("testCommonMethods8 error !!!", newInstance(false).compareTo(newInstance(false)) == 0);
    }

    @Test
    public void testCommonMethods_9() {
        assertEquals("testCommonMethods9 error !!!", "true", newInstance(true).toString());
    }
}
