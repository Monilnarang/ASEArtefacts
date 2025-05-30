package org.apache.dubbo.common.convert.multiple;

import org.apache.dubbo.rpc.model.FrameworkModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.util.Objects.deepEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringToArrayConverterTest_Purified {

    private StringToArrayConverter converter;

    @BeforeEach
    public void init() {
        converter = new StringToArrayConverter(FrameworkModel.defaultModel());
    }

    @Test
    void testAccept_1() {
        assertTrue(converter.accept(String.class, char[].class));
    }

    @Test
    void testAccept_2() {
        assertTrue(converter.accept(null, char[].class));
    }

    @Test
    void testAccept_3() {
        assertFalse(converter.accept(null, String.class));
    }

    @Test
    void testAccept_4() {
        assertFalse(converter.accept(null, String.class));
    }

    @Test
    void testAccept_5() {
        assertFalse(converter.accept(null, null));
    }
}
