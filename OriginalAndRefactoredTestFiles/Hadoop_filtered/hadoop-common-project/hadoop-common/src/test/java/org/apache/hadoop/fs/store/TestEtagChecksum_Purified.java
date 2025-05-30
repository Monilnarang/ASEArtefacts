package org.apache.hadoop.fs.store;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;

public class TestEtagChecksum_Purified extends Assert {

    private final EtagChecksum empty1 = tag("");

    private final EtagChecksum empty2 = tag("");

    private final EtagChecksum valid1 = tag("valid");

    private final EtagChecksum valid2 = tag("valid");

    private EtagChecksum tag(String t) {
        return new EtagChecksum(t);
    }

    private EtagChecksum roundTrip(EtagChecksum tag) throws IOException {
        try (DataOutputBuffer dob = new DataOutputBuffer();
            DataInputBuffer dib = new DataInputBuffer()) {
            tag.write(dob);
            dib.reset(dob.getData(), dob.getLength());
            EtagChecksum t2 = new EtagChecksum();
            t2.readFields(dib);
            return t2;
        }
    }

    @Test
    public void testValidAndEmptyTagsDontMatch_1() {
        assertNotEquals(valid1, empty1);
    }

    @Test
    public void testValidAndEmptyTagsDontMatch_2() {
        assertNotEquals(valid1, tag("other valid one"));
    }
}
