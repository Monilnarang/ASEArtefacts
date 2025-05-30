package org.apache.rocketmq.tieredstore.common;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FileSegmentTypeTest_Purified {

    @Test
    public void getTypeCodeTest_1() {
        assertEquals(0, FileSegmentType.COMMIT_LOG.getCode());
    }

    @Test
    public void getTypeCodeTest_2() {
        assertEquals(1, FileSegmentType.CONSUME_QUEUE.getCode());
    }

    @Test
    public void getTypeCodeTest_3() {
        assertEquals(2, FileSegmentType.INDEX.getCode());
    }

    @Test
    public void getTypeFromValueTest_1() {
        assertEquals(FileSegmentType.COMMIT_LOG, FileSegmentType.valueOf(0));
    }

    @Test
    public void getTypeFromValueTest_2() {
        assertEquals(FileSegmentType.CONSUME_QUEUE, FileSegmentType.valueOf(1));
    }

    @Test
    public void getTypeFromValueTest_3() {
        assertEquals(FileSegmentType.INDEX, FileSegmentType.valueOf(2));
    }
}
