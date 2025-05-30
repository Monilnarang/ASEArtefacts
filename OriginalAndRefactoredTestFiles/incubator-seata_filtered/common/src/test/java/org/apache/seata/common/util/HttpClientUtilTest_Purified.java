package org.apache.seata.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;

public class HttpClientUtilTest_Purified {

    @Test
    public void testDoPost_1() throws IOException {
        Assertions.assertNull(HttpClientUtil.doPost("test", new HashMap<>(), new HashMap<>(), 0));
    }

    @Test
    public void testDoPost_2() throws IOException {
        Assertions.assertNull(HttpClientUtil.doGet("test", new HashMap<>(), new HashMap<>(), 0));
    }
}
