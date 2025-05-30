package org.apache.xtable.conversion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class TestExternalTable_Purified {

    @Test
    void sanitizePath_1() {
        ExternalTable tooManySlashes = new ExternalTable("name", "hudi", "s3://bucket//path", null, null, null);
        assertEquals("s3://bucket/path", tooManySlashes.getBasePath());
    }

    @Test
    void sanitizePath_2() {
        ExternalTable localFilePath = new ExternalTable("name", "hudi", "/local/data//path", null, null, null);
        assertEquals("file:///local/data/path", localFilePath.getBasePath());
    }

    @Test
    void sanitizePath_3() {
        ExternalTable properLocalFilePath = new ExternalTable("name", "hudi", "file:///local/data//path", null, null, null);
        assertEquals("file:///local/data/path", properLocalFilePath.getBasePath());
    }
}
