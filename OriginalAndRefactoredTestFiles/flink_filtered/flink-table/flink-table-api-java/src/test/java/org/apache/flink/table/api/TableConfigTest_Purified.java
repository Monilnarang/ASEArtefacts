package org.apache.flink.table.api;

import org.apache.flink.table.api.config.TableConfigOptions;
import org.junit.jupiter.api.Test;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.ZoneId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TableConfigTest_Purified {

    private static final TableConfig CONFIG_BY_METHOD = TableConfig.getDefault();

    private static final TableConfig CONFIG_BY_CONFIGURATION = TableConfig.getDefault();

    @Test
    void testSetAndGetMaxGeneratedCodeLength_1() {
        CONFIG_BY_METHOD.setMaxGeneratedCodeLength(5000);
        assertThat(CONFIG_BY_METHOD.getMaxGeneratedCodeLength()).isEqualTo(Integer.valueOf(5000));
    }

    @Test
    void testSetAndGetMaxGeneratedCodeLength_2() {
        CONFIG_BY_CONFIGURATION.set("table.generated-code.max-length", "5000");
        assertThat(CONFIG_BY_CONFIGURATION.getMaxGeneratedCodeLength()).isEqualTo(Integer.valueOf(5000));
    }
}
