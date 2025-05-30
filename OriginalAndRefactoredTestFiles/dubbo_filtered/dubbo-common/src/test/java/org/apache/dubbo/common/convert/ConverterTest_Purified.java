package org.apache.dubbo.common.convert;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ConverterTest_Purified {

    private ConverterUtil converterUtil;

    @BeforeEach
    public void setup() {
        converterUtil = FrameworkModel.defaultModel().getBeanFactory().getBean(ConverterUtil.class);
    }

    @AfterEach
    public void tearDown() {
        FrameworkModel.destroyAll();
    }

    private <T> ExtensionLoader<T> getExtensionLoader(Class<T> extClass) {
        return ApplicationModel.defaultModel().getDefaultModule().getExtensionLoader(extClass);
    }

    @Test
    void testConvertIfPossible_1() {
        assertEquals(Integer.valueOf(2), converterUtil.convertIfPossible("2", Integer.class));
    }

    @Test
    void testConvertIfPossible_2() {
        assertEquals(Boolean.FALSE, converterUtil.convertIfPossible("false", Boolean.class));
    }

    @Test
    void testConvertIfPossible_3() {
        assertEquals(Double.valueOf(1), converterUtil.convertIfPossible("1", Double.class));
    }
}
