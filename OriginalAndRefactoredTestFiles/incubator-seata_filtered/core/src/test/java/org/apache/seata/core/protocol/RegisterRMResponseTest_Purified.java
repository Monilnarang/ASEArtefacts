package org.apache.seata.core.protocol;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegisterRMResponseTest_Purified {

    @Test
    public void isIdentified_1() {
        RegisterRMResponse registerRMResponse = new RegisterRMResponse();
        Assertions.assertTrue(registerRMResponse.isIdentified());
    }

    @Test
    public void isIdentified_2() {
        RegisterRMResponse registerRMResp = new RegisterRMResponse(false);
        Assertions.assertFalse(registerRMResp.isIdentified());
    }
}
