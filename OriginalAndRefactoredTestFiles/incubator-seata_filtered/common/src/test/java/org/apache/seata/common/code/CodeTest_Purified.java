package org.apache.seata.common.code;

import org.apache.seata.common.result.Code;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CodeTest_Purified {

    @Test
    public void testGetErrorMsgWithValidCodeReturnsExpectedMsg_1() {
        assertEquals("ok", Code.SUCCESS.getMsg());
    }

    @Test
    public void testGetErrorMsgWithValidCodeReturnsExpectedMsg_2() {
        assertEquals("Server error", Code.ERROR.getMsg());
    }

    @Test
    public void testGetErrorMsgWithValidCodeReturnsExpectedMsg_3() {
        assertEquals("Login failed", Code.LOGIN_FAILED.getMsg());
    }
}
