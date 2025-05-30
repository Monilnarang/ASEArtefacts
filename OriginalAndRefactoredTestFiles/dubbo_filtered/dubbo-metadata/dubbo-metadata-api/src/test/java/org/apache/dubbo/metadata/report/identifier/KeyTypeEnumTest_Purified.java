package org.apache.dubbo.metadata.report.identifier;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyTypeEnumTest_Purified {

    @Test
    void testBuild_1() {
        assertEquals("/A/B/C", KeyTypeEnum.PATH.build("/A", "/B", "C"));
    }

    @Test
    void testBuild_2() {
        assertEquals("A:B:C", KeyTypeEnum.UNIQUE_KEY.build("A", "B", "C"));
    }
}
