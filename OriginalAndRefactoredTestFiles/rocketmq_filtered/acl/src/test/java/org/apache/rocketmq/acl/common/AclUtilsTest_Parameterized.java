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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AclUtilsTest_Parameterized {

    private static String randomTmpFile() {
        String tmpFileName = System.getProperty("java.io.tmpdir");
        if (!tmpFileName.endsWith(File.separator)) {
            tmpFileName += File.separator;
        }
        tmpFileName += UUID.randomUUID() + ".yml";
        return tmpFileName;
    }

    @ParameterizedTest
    @MethodSource("Provider_testExpandIP_1to8")
    public void testExpandIP_1to8(String param1, String param2, int param3) {
        Assert.assertEquals(AclUtils.expandIP(param2, param3), param1);
    }

    static public Stream<Arguments> Provider_testExpandIP_1to8() {
        return Stream.of(arguments("0000:0000:0000:0000:0000:0000:0000:0000", "::", 8), arguments("0000:0000:0000:0000:0000:0000:0000:0001", "::1", 8), arguments("0003:0000:0000:0000:0000:0000:0000:0000", "3::", 8), arguments("0002:0000:0000:0000:0000:0000:0000:0002", "2::2", 8), arguments("0004:0000:0000:0000:0000:0000:AAC4:0092", "4::aac4:92", 8), arguments("AB23:0056:901A:0000:0CC6:0765:00BB:9011", "ab23:56:901a::cc6:765:bb:9011", 8), arguments("AB23:0056:901A:0001:0CC6:0765:00BB:9011", "ab23:56:901a:1:cc6:765:bb:9011", 8), arguments("0005:0000:0000:0000:0007:0006", "5::7:6", 6));
    }
}
