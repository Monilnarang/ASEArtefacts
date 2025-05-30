package org.apache.seata.common.util;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;
import org.apache.seata.common.Constants;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BlobUtilsTest_Purified {

    @Test
    public void testString2blob_1() throws SQLException {
        assertNull(BlobUtils.string2blob(null));
    }

    @Test
    public void testString2blob_2() throws SQLException {
        assertThat(BlobUtils.string2blob("123abc")).isEqualTo(new SerialBlob("123abc".getBytes(Constants.DEFAULT_CHARSET)));
    }

    @Test
    public void testBlob2string_1() throws SQLException {
        assertNull(BlobUtils.blob2string(null));
    }

    @Test
    public void testBlob2string_2() throws SQLException {
        assertThat(BlobUtils.blob2string(new SerialBlob("123absent".getBytes(Constants.DEFAULT_CHARSET)))).isEqualTo("123absent");
    }
}
