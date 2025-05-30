package org.apache.hadoop.security.authentication.server;

import org.junit.Assert;
import org.junit.Test;

public class TestAuthenticationToken_Purified {

    @Test
    public void testAnonymous_1() {
        Assert.assertNotNull(AuthenticationToken.ANONYMOUS);
    }

    @Test
    public void testAnonymous_2() {
        Assert.assertEquals(null, AuthenticationToken.ANONYMOUS.getUserName());
    }

    @Test
    public void testAnonymous_3() {
        Assert.assertEquals(null, AuthenticationToken.ANONYMOUS.getName());
    }

    @Test
    public void testAnonymous_4() {
        Assert.assertEquals(null, AuthenticationToken.ANONYMOUS.getType());
    }

    @Test
    public void testAnonymous_5() {
        Assert.assertEquals(-1, AuthenticationToken.ANONYMOUS.getExpires());
    }

    @Test
    public void testAnonymous_6() {
        Assert.assertFalse(AuthenticationToken.ANONYMOUS.isExpired());
    }
}
