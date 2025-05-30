package org.apache.dubbo.common.extension;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.convert.Converter;
import org.apache.dubbo.common.convert.StringToBooleanConverter;
import org.apache.dubbo.common.convert.StringToDoubleConverter;
import org.apache.dubbo.common.convert.StringToIntegerConverter;
import org.apache.dubbo.common.extension.activate.ActivateExt1;
import org.apache.dubbo.common.extension.activate.impl.ActivateExt1Impl1;
import org.apache.dubbo.common.extension.activate.impl.GroupActivateExtImpl;
import org.apache.dubbo.common.extension.activate.impl.OrderActivateExtImpl1;
import org.apache.dubbo.common.extension.activate.impl.OrderActivateExtImpl2;
import org.apache.dubbo.common.extension.activate.impl.ValueActivateExtImpl;
import org.apache.dubbo.common.extension.convert.String2BooleanConverter;
import org.apache.dubbo.common.extension.convert.String2DoubleConverter;
import org.apache.dubbo.common.extension.convert.String2IntegerConverter;
import org.apache.dubbo.common.extension.duplicated.DuplicatedOverriddenExt;
import org.apache.dubbo.common.extension.duplicated.DuplicatedWithoutOverriddenExt;
import org.apache.dubbo.common.extension.ext1.SimpleExt;
import org.apache.dubbo.common.extension.ext1.impl.SimpleExtImpl1;
import org.apache.dubbo.common.extension.ext1.impl.SimpleExtImpl2;
import org.apache.dubbo.common.extension.ext10_multi_names.Ext10MultiNames;
import org.apache.dubbo.common.extension.ext11_no_adaptive.NoAdaptiveExt;
import org.apache.dubbo.common.extension.ext11_no_adaptive.NoAdaptiveExtImpl;
import org.apache.dubbo.common.extension.ext2.Ext2;
import org.apache.dubbo.common.extension.ext6_wrap.WrappedExt;
import org.apache.dubbo.common.extension.ext6_wrap.WrappedExtWrapper;
import org.apache.dubbo.common.extension.ext6_wrap.impl.Ext6Impl1;
import org.apache.dubbo.common.extension.ext6_wrap.impl.Ext6Impl3;
import org.apache.dubbo.common.extension.ext6_wrap.impl.Ext6Wrapper1;
import org.apache.dubbo.common.extension.ext6_wrap.impl.Ext6Wrapper2;
import org.apache.dubbo.common.extension.ext6_wrap.impl.Ext6Wrapper3;
import org.apache.dubbo.common.extension.ext6_wrap.impl.Ext6Wrapper4;
import org.apache.dubbo.common.extension.ext7.InitErrorExt;
import org.apache.dubbo.common.extension.ext8_add.AddExt1;
import org.apache.dubbo.common.extension.ext8_add.AddExt2;
import org.apache.dubbo.common.extension.ext8_add.AddExt3;
import org.apache.dubbo.common.extension.ext8_add.AddExt4;
import org.apache.dubbo.common.extension.ext8_add.impl.AddExt1Impl1;
import org.apache.dubbo.common.extension.ext8_add.impl.AddExt1_ManualAdaptive;
import org.apache.dubbo.common.extension.ext8_add.impl.AddExt1_ManualAdd1;
import org.apache.dubbo.common.extension.ext8_add.impl.AddExt1_ManualAdd2;
import org.apache.dubbo.common.extension.ext8_add.impl.AddExt2_ManualAdaptive;
import org.apache.dubbo.common.extension.ext8_add.impl.AddExt3_ManualAdaptive;
import org.apache.dubbo.common.extension.ext8_add.impl.AddExt4_ManualAdaptive;
import org.apache.dubbo.common.extension.ext9_empty.Ext9Empty;
import org.apache.dubbo.common.extension.ext9_empty.impl.Ext9EmptyImpl;
import org.apache.dubbo.common.extension.injection.InjectExt;
import org.apache.dubbo.common.extension.injection.impl.InjectExtImpl;
import org.apache.dubbo.common.extension.wrapper.Demo;
import org.apache.dubbo.common.extension.wrapper.impl.DemoImpl;
import org.apache.dubbo.common.extension.wrapper.impl.DemoWrapper;
import org.apache.dubbo.common.extension.wrapper.impl.DemoWrapper2;
import org.apache.dubbo.common.lang.Prioritized;
import org.apache.dubbo.common.url.component.ServiceConfigURL;
import org.apache.dubbo.rpc.model.ApplicationModel;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.constants.CommonConstants.GROUP_KEY;
import static org.apache.dubbo.common.extension.ExtensionLoader.getLoadingStrategies;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ExtensionLoaderTest_Purified {

    private <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        return ApplicationModel.defaultModel().getExtensionDirector().getExtensionLoader(type);
    }

    private static class DubboExternalLoadingStrategyTest implements LoadingStrategy {

        public DubboExternalLoadingStrategyTest(boolean overridden) {
            this.overridden = overridden;
        }

        private boolean overridden;

        @Override
        public String directory() {
            return "META-INF/dubbo/external/";
        }

        @Override
        public boolean overridden() {
            return this.overridden;
        }

        @Override
        public int getPriority() {
            return MAX_PRIORITY + 1;
        }
    }

    private static class DubboInternalLoadingStrategyTest implements LoadingStrategy {

        public DubboInternalLoadingStrategyTest(boolean overridden) {
            this.overridden = overridden;
        }

        private boolean overridden;

        @Override
        public String directory() {
            return "META-INF/dubbo/internal/";
        }

        @Override
        public boolean overridden() {
            return this.overridden;
        }

        @Override
        public int getPriority() {
            return MAX_PRIORITY;
        }
    }

    @Test
    void test_getDefaultExtension_1() {
        SimpleExt ext = getExtensionLoader(SimpleExt.class).getDefaultExtension();
        assertThat(ext, instanceOf(SimpleExtImpl1.class));
    }

    @Test
    void test_getDefaultExtension_2() {
        String name = getExtensionLoader(SimpleExt.class).getDefaultExtensionName();
        assertEquals("impl1", name);
    }

    @Test
    void test_getDefaultExtension_NULL_1() {
        Ext2 ext = getExtensionLoader(Ext2.class).getDefaultExtension();
        assertNull(ext);
    }

    @Test
    void test_getDefaultExtension_NULL_2() {
        String name = getExtensionLoader(Ext2.class).getDefaultExtensionName();
        assertNull(name);
    }

    @Test
    void test_getExtension_1() {
        assertTrue(getExtensionLoader(SimpleExt.class).getExtension("impl1") instanceof SimpleExtImpl1);
    }

    @Test
    void test_getExtension_2() {
        assertTrue(getExtensionLoader(SimpleExt.class).getExtension("impl2") instanceof SimpleExtImpl2);
    }

    @Test
    void test_AddExtension_NoExtend_1() {
        Ext9Empty ext = getExtensionLoader(Ext9Empty.class).getExtension("ext9");
        assertThat(ext, instanceOf(Ext9Empty.class));
    }

    @Test
    void test_AddExtension_NoExtend_2() {
        assertEquals("ext9", getExtensionLoader(Ext9Empty.class).getExtensionName(Ext9EmptyImpl.class));
    }

    @Test
    void test_getLoadedExtension_1() {
        SimpleExt simpleExt = getExtensionLoader(SimpleExt.class).getExtension("impl1");
        assertThat(simpleExt, instanceOf(SimpleExtImpl1.class));
    }

    @Test
    void test_getLoadedExtension_2() {
        SimpleExt simpleExt1 = getExtensionLoader(SimpleExt.class).getLoadedExtension("impl1");
        assertThat(simpleExt1, instanceOf(SimpleExtImpl1.class));
    }

    @Test
    void isWrapperClass_1() {
        assertFalse(getExtensionLoader(Demo.class).isWrapperClass(DemoImpl.class));
    }

    @Test
    void isWrapperClass_2() {
        assertTrue(getExtensionLoader(Demo.class).isWrapperClass(DemoWrapper.class));
    }

    @Test
    void isWrapperClass_3() {
        assertTrue(getExtensionLoader(Demo.class).isWrapperClass(DemoWrapper2.class));
    }
}
