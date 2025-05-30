package org.apache.flink.table.api;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.table.catalog.TableDistribution;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TableDescriptorTest_Purified {

    private static final ConfigOption<Boolean> OPTION_A = ConfigOptions.key("a").booleanType().noDefaultValue();

    private static final ConfigOption<Integer> OPTION_B = ConfigOptions.key("b").intType().noDefaultValue();

    private static final ConfigOption<String> KEY_FORMAT = ConfigOptions.key("key.format").stringType().noDefaultValue();

    private static TableDescriptor.Builder getTableDescriptorBuilder() {
        final Schema schema = Schema.newBuilder().column("f0", DataTypes.STRING()).build();
        final FormatDescriptor formatDescriptor = FormatDescriptor.forFormat("test-format").option(OPTION_A, false).build();
        return TableDescriptor.forConnector("test-connector").schema(schema).partitionedBy("f0").option(OPTION_A, true).format(formatDescriptor).comment("Test Comment");
    }

    @Test
    void testDistributedBy_1() {
        assertThat(getTableDescriptorBuilder().distributedByHash(3, "f0").build().toString()).contains("DISTRIBUTED BY HASH(`f0`) INTO 3 BUCKETS\n");
    }

    @Test
    void testDistributedBy_2() {
        assertThat(getTableDescriptorBuilder().distributedByHash("f0").build().toString()).contains("DISTRIBUTED BY HASH(`f0`)\n");
    }

    @Test
    void testDistributedBy_3() {
        assertThat(getTableDescriptorBuilder().distributedByRange(3, "f0").build().toString()).contains("DISTRIBUTED BY RANGE(`f0`) INTO 3 BUCKETS\n");
    }

    @Test
    void testDistributedBy_4() {
        assertThat(getTableDescriptorBuilder().distributedByRange("f0").build().toString()).contains("DISTRIBUTED BY RANGE(`f0`)\n");
    }

    @Test
    void testDistributedBy_5() {
        assertThat(getTableDescriptorBuilder().distributedBy(3, "f0").build().toString()).contains("DISTRIBUTED BY (`f0`) INTO 3 BUCKETS\n");
    }

    @Test
    void testDistributedBy_6() {
        assertThat(getTableDescriptorBuilder().distributedBy("f0").build().toString()).contains("DISTRIBUTED BY (`f0`)\n");
    }

    @Test
    void testDistributedBy_7() {
        assertThat(getTableDescriptorBuilder().distributedInto(3).build().toString()).contains("DISTRIBUTED INTO 3 BUCKETS\n");
    }
}
