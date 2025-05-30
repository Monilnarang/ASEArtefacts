package org.apache.flink.yarn;

import org.apache.flink.util.FlinkException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.LocalResourceType;
import org.apache.hadoop.yarn.api.records.LocalResourceVisibility;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class YarnLocalResourceDescriptionTest_Purified {

    private final String key = "fli'nk 2.jar";

    private final Path path = new Path("hdfs://nn/tmp/fli'nk 2.jar");

    private final long size = 100 * 1024 * 1024;

    private final long ts = System.currentTimeMillis();

    private void assertThrows(final String desc) {
        assertThatThrownBy(() -> YarnLocalResourceDescriptor.fromString(desc)).isInstanceOf(FlinkException.class).hasMessageContaining("Error to parse YarnLocalResourceDescriptor from " + desc);
    }

    @Test
    void testFromStringMalformed_1() {
        final String desc = String.format("{'resourceKey':'%s','path':'%s','size':%s,'modificationTime':%s,'visibility':'%s'}", key, path, size, ts, LocalResourceVisibility.PUBLIC);
        assertThrows(desc);
    }

    @Test
    void testFromStringMalformed_2() {
        assertThrows("{}");
    }

    @Test
    void testFromStringMalformed_3() {
        assertThrows("{");
    }
}
