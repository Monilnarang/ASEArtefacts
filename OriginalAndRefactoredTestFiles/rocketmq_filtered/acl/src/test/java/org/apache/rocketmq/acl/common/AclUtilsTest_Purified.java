package org.apache.rocketmq.acl.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.plain.PlainAccessData;
import org.apache.rocketmq.common.PlainAccessConfig;
import org.apache.rocketmq.remoting.RPCHook;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AclUtilsTest_Purified {

    private static String randomTmpFile() {
        String tmpFileName = System.getProperty("java.io.tmpdir");
        if (!tmpFileName.endsWith(File.separator)) {
            tmpFileName += File.separator;
        }
        tmpFileName += UUID.randomUUID() + ".yml";
        return tmpFileName;
    }

    @Test
    public void testExpandIP_1() {
        Assert.assertEquals(AclUtils.expandIP("::", 8), "0000:0000:0000:0000:0000:0000:0000:0000");
    }

    @Test
    public void testExpandIP_2() {
        Assert.assertEquals(AclUtils.expandIP("::1", 8), "0000:0000:0000:0000:0000:0000:0000:0001");
    }

    @Test
    public void testExpandIP_3() {
        Assert.assertEquals(AclUtils.expandIP("3::", 8), "0003:0000:0000:0000:0000:0000:0000:0000");
    }

    @Test
    public void testExpandIP_4() {
        Assert.assertEquals(AclUtils.expandIP("2::2", 8), "0002:0000:0000:0000:0000:0000:0000:0002");
    }

    @Test
    public void testExpandIP_5() {
        Assert.assertEquals(AclUtils.expandIP("4::aac4:92", 8), "0004:0000:0000:0000:0000:0000:AAC4:0092");
    }

    @Test
    public void testExpandIP_6() {
        Assert.assertEquals(AclUtils.expandIP("ab23:56:901a::cc6:765:bb:9011", 8), "AB23:0056:901A:0000:0CC6:0765:00BB:9011");
    }

    @Test
    public void testExpandIP_7() {
        Assert.assertEquals(AclUtils.expandIP("ab23:56:901a:1:cc6:765:bb:9011", 8), "AB23:0056:901A:0001:0CC6:0765:00BB:9011");
    }

    @Test
    public void testExpandIP_8() {
        Assert.assertEquals(AclUtils.expandIP("5::7:6", 6), "0005:0000:0000:0000:0007:0006");
    }
}
