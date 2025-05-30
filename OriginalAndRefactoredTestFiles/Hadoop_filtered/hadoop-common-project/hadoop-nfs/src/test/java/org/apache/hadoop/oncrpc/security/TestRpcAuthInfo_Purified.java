package org.apache.hadoop.oncrpc.security;

import static org.junit.Assert.assertEquals;
import org.apache.hadoop.oncrpc.security.RpcAuthInfo;
import org.apache.hadoop.oncrpc.security.RpcAuthInfo.AuthFlavor;
import org.junit.Test;

public class TestRpcAuthInfo_Purified {

    @Test
    public void testAuthFlavor_1() {
        assertEquals(AuthFlavor.AUTH_NONE, AuthFlavor.fromValue(0));
    }

    @Test
    public void testAuthFlavor_2() {
        assertEquals(AuthFlavor.AUTH_SYS, AuthFlavor.fromValue(1));
    }

    @Test
    public void testAuthFlavor_3() {
        assertEquals(AuthFlavor.AUTH_SHORT, AuthFlavor.fromValue(2));
    }

    @Test
    public void testAuthFlavor_4() {
        assertEquals(AuthFlavor.AUTH_DH, AuthFlavor.fromValue(3));
    }

    @Test
    public void testAuthFlavor_5() {
        assertEquals(AuthFlavor.RPCSEC_GSS, AuthFlavor.fromValue(6));
    }
}
