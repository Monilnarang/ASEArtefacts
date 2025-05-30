package org.apache.dubbo.common.extension;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.adaptive.HasAdaptiveExt;
import org.apache.dubbo.common.extension.adaptive.impl.HasAdaptiveExt_ManualAdaptive;
import org.apache.dubbo.common.extension.ext1.SimpleExt;
import org.apache.dubbo.common.extension.ext2.Ext2;
import org.apache.dubbo.common.extension.ext2.UrlHolder;
import org.apache.dubbo.common.extension.ext3.UseProtocolKeyExt;
import org.apache.dubbo.common.extension.ext4.NoUrlParamExt;
import org.apache.dubbo.common.extension.ext5.NoAdaptiveMethodExt;
import org.apache.dubbo.common.extension.ext6_inject.Ext6;
import org.apache.dubbo.common.extension.ext6_inject.impl.Ext6Impl2;
import org.apache.dubbo.common.url.component.ServiceConfigURL;
import org.apache.dubbo.common.utils.LogUtil;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ExtensionLoader_Adaptive_Test_Purified {

    @Test
    void test_getAdaptiveExtension_inject_1_testMerged_1() throws Exception {
        Ext6 ext = ExtensionLoader.getExtensionLoader(Ext6.class).getAdaptiveExtension();
        URL url = new ServiceConfigURL("p1", "1.2.3.4", 1010, "path1");
        url = url.addParameters("ext6", "impl1");
        assertEquals("Ext6Impl1-echo-Ext1Impl1-echo", ext.echo(url, "ha"));
        url = url.addParameters("simple.ext", "impl2");
        assertEquals("Ext6Impl1-echo-Ext1Impl2-echo", ext.echo(url, "ha"));
    }

    @Test
    void test_getAdaptiveExtension_inject_2() throws Exception {
        LogUtil.start();
        Assertions.assertTrue(LogUtil.checkNoError(), "can not find error.");
    }
}
