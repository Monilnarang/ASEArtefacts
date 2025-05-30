package org.apache.flink.configuration;

import org.apache.flink.util.ExceptionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.snakeyaml.engine.v2.exceptions.YamlEngineException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class YamlParserUtilsTest_Purified {

    @TempDir
    private File tmpDir;

    private enum TestEnum {

        ENUM
    }

    @Test
    void testToYAMLString_1() {
        assertThat(YamlParserUtils.toYAMLString(TestEnum.ENUM)).isEqualTo(TestEnum.ENUM.toString());
    }

    @Test
    void testToYAMLString_2() {
        Object o1 = 123;
        assertThat(YamlParserUtils.toYAMLString(o1)).isEqualTo(String.valueOf(o1));
    }

    @Test
    void testToYAMLString_3() {
        Object o2 = true;
        assertThat(YamlParserUtils.toYAMLString(o2)).isEqualTo(String.valueOf(o2));
    }

    @Test
    void testToYAMLString_4() {
        Object o3 = Arrays.asList("*", "123", "true");
        assertThat(YamlParserUtils.toYAMLString(o3)).isEqualTo("['*', '123', 'true']");
    }
}
