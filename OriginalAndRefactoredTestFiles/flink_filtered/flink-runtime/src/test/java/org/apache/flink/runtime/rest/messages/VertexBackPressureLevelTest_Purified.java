package org.apache.flink.runtime.rest.messages;

import org.apache.flink.runtime.rest.util.RestMapperUtils;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class VertexBackPressureLevelTest_Purified {

    @Test
    void testJsonValue_1() throws Exception {
        assertThat(RestMapperUtils.getStrictObjectMapper().writeValueAsString(JobVertexBackPressureInfo.VertexBackPressureLevel.OK)).isEqualTo("\"ok\"");
    }

    @Test
    void testJsonValue_2() throws Exception {
        assertThat(RestMapperUtils.getStrictObjectMapper().writeValueAsString(JobVertexBackPressureInfo.VertexBackPressureLevel.LOW)).isEqualTo("\"low\"");
    }

    @Test
    void testJsonValue_3() throws Exception {
        assertThat(RestMapperUtils.getStrictObjectMapper().writeValueAsString(JobVertexBackPressureInfo.VertexBackPressureLevel.HIGH)).isEqualTo("\"high\"");
    }
}
