package com.vmware.admiral.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.lang.reflect.Field;
import java.util.Base64;
import io.netty.handler.codec.http.cookie.ClientCookieDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import org.junit.Before;
import org.junit.Test;
import com.vmware.photon.controller.model.security.util.AuthCredentialsType;
import com.vmware.xenon.common.Claims;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.Operation.AuthorizationContext;
import com.vmware.xenon.common.ReflectionUtils;
import com.vmware.xenon.common.Service;
import com.vmware.xenon.common.ServiceHost;
import com.vmware.xenon.common.TestServiceHost.SomeExampleService;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.test.VerificationHost;
import com.vmware.xenon.services.common.AuthCredentialsService.AuthCredentialsServiceState;
import com.vmware.xenon.services.common.SystemUserService;
import com.vmware.xenon.services.common.authn.AuthenticationConstants;

public class AuthUtilsTest_Purified {

    private static final Field AUTH_CTX_FIELD = ReflectionUtils.getField(Operation.class, "authorizationCtx");

    private VerificationHost host;

    @Before
    public void setUp() throws Throwable {
        ServiceHost.Arguments args = new ServiceHost.Arguments();
        args.sandbox = null;
        args.port = 0;
        args.isAuthorizationEnabled = false;
        host = new VerificationHost();
        host = VerificationHost.initialize(host, args);
        host.start();
    }

    private void assertAuthCookie(Operation op) {
        String cookieHeader = op.getResponseHeader(Operation.SET_COOKIE_HEADER);
        Cookie cookie = ClientCookieDecoder.LAX.decode(cookieHeader);
        assertEquals(AuthenticationConstants.REQUEST_AUTH_TOKEN_COOKIE, cookie.name());
        assertEquals("", cookie.value());
        assertEquals(0, cookie.maxAge());
    }

    @Test
    public void testCreateAuthorizationHeader_1() {
        assertNull(AuthUtils.createAuthorizationHeader(null));
    }

    @Test
    public void testCreateAuthorizationHeader_2_testMerged_2() {
        AuthCredentialsServiceState credentials = new AuthCredentialsServiceState();
        assertNull(AuthUtils.createAuthorizationHeader(credentials));
        String email = "test@test.test";
        String password = "test";
        String expectedHeader = String.format("Basic %s", new String(Base64.getEncoder().encode(String.format("%s:%s", email, password).getBytes())));
        credentials = new AuthCredentialsServiceState();
        assertEquals(expectedHeader, AuthUtils.createAuthorizationHeader(credentials));
    }
}
