package com.vmware.admiral.auth.idm.local;

import static com.vmware.admiral.auth.util.AuthUtil.BASIC_USERS_USER_GROUP_LINK;
import static com.vmware.admiral.auth.util.AuthUtil.CLOUD_ADMINS_USER_GROUP_LINK;
import static com.vmware.admiral.auth.util.PrincipalUtil.encode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.auth.AuthBaseTest;
import com.vmware.admiral.auth.idm.local.LocalPrincipalService.LocalPrincipalState;
import com.vmware.admiral.auth.idm.local.LocalPrincipalService.LocalPrincipalType;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.test.TestContext;
import com.vmware.xenon.services.common.ResourceGroupService;
import com.vmware.xenon.services.common.ResourceGroupService.ResourceGroupState;
import com.vmware.xenon.services.common.RoleService;
import com.vmware.xenon.services.common.RoleService.RoleState;
import com.vmware.xenon.services.common.UserGroupService;
import com.vmware.xenon.services.common.UserGroupService.UserGroupState;
import com.vmware.xenon.services.common.UserService.UserState;

public class LocalPrincipalServiceTest_Purified extends AuthBaseTest {

    @Before
    public void setup() throws Throwable {
        host.assumeIdentity(buildUserServicePath(USER_EMAIL_ADMIN));
        waitForServiceAvailability(LocalPrincipalFactoryService.SELF_LINK);
    }

    @Test
    public void testUsersAreCreatedOnInitBoot_1_testMerged_1() throws Throwable {
        String fritzEmail = "fritz@admiral.com";
        String fritzSelfLink = LocalPrincipalFactoryService.SELF_LINK + "/" + encode(fritzEmail);
        LocalPrincipalState fritzState = getDocument(LocalPrincipalState.class, fritzSelfLink);
        assertNotNull(fritzState);
        assertEquals(fritzEmail, fritzState.id);
        assertEquals(fritzEmail, fritzState.email);
        assertEquals("Fritz", fritzState.name);
    }

    @Test
    public void testUsersAreCreatedOnInitBoot_5_testMerged_2() throws Throwable {
        String connieEmail = "connie@admiral.com";
        String connieSelfLink = LocalPrincipalFactoryService.SELF_LINK + "/" + encode(connieEmail);
        LocalPrincipalState connieState = getDocument(LocalPrincipalState.class, connieSelfLink);
        assertNotNull(connieState);
        assertEquals(connieEmail, connieState.id);
        assertEquals(connieEmail, connieState.email);
        assertEquals("Connie", connieState.name);
    }

    @Test
    public void testUsersAreCreatedOnInitBoot_9_testMerged_3() throws Throwable {
        String gloriaEmail = "gloria@admiral.com";
        String gloriaSelfLink = LocalPrincipalFactoryService.SELF_LINK + "/" + encode(gloriaEmail);
        LocalPrincipalState gloriaState = getDocument(LocalPrincipalState.class, gloriaSelfLink);
        assertNotNull(gloriaState);
        assertEquals(gloriaEmail, gloriaState.id);
        assertEquals(gloriaEmail, gloriaState.email);
        assertEquals("Gloria", gloriaState.name);
    }
}
