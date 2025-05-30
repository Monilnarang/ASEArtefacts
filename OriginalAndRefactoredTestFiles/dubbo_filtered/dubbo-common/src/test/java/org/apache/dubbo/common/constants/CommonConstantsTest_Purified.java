package org.apache.dubbo.common.constants;

import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.constants.CommonConstants.COMMA_SEPARATOR_CHAR;
import static org.apache.dubbo.common.constants.CommonConstants.COMPOSITE_METADATA_STORAGE_TYPE;
import static org.apache.dubbo.common.constants.CommonConstants.DEFAULT_SERVICE_NAME_MAPPING_PROPERTIES_PATH;
import static org.apache.dubbo.common.constants.CommonConstants.SERVICE_NAME_MAPPING_PROPERTIES_FILE_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonConstantsTest_Purified {

    @Test
    void test_1() {
        assertEquals(',', COMMA_SEPARATOR_CHAR);
    }

    @Test
    void test_2() {
        assertEquals("composite", COMPOSITE_METADATA_STORAGE_TYPE);
    }

    @Test
    void test_3() {
        assertEquals("service-name-mapping.properties-path", SERVICE_NAME_MAPPING_PROPERTIES_FILE_KEY);
    }

    @Test
    void test_4() {
        assertEquals("META-INF/dubbo/service-name-mapping.properties", DEFAULT_SERVICE_NAME_MAPPING_PROPERTIES_PATH);
    }
}
