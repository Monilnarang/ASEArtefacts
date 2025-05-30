package org.apache.seata.core.message;

import org.apache.seata.core.protocol.MessageType;
import org.apache.seata.core.protocol.RegisterTMResponse;
import org.apache.seata.core.protocol.ResultCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegisterTMResponseTest_Purified {

    @Test
    public void isIdentified_1() {
        RegisterTMResponse registerTMResponse = new RegisterTMResponse();
        Assertions.assertTrue(registerTMResponse.isIdentified());
    }

    @Test
    public void isIdentified_2() {
        RegisterTMResponse registerTMResp = new RegisterTMResponse(false);
        Assertions.assertFalse(registerTMResp.isIdentified());
    }
}
