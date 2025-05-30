package com.vmware.admiral.auth.idm;

import static com.vmware.admiral.auth.util.PrincipalUtil.encode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.auth.AuthBaseTest;
import com.vmware.admiral.auth.idm.PrincipalRolesHandler.PrincipalRoleAssignment;
import com.vmware.admiral.auth.idm.SecurityContext.ProjectEntry;
import com.vmware.admiral.auth.idm.SecurityContext.SecurityContextPostDto;
import com.vmware.admiral.auth.idm.local.LocalPrincipalFactoryService;
import com.vmware.admiral.auth.idm.local.LocalPrincipalService.LocalPrincipalState;
import com.vmware.admiral.auth.idm.local.LocalPrincipalService.LocalPrincipalType;
import com.vmware.admiral.auth.project.ProjectFactoryService;
import com.vmware.admiral.auth.project.ProjectRolesHandler.ProjectRoles;
import com.vmware.admiral.auth.project.ProjectService.ProjectState;
import com.vmware.admiral.auth.util.AuthUtil;
import com.vmware.admiral.service.common.RegistryFactoryService;
import com.vmware.admiral.service.common.RegistryService.RegistryState;
import com.vmware.admiral.service.common.harbor.Harbor;
import com.vmware.photon.controller.model.security.util.AuthCredentialsType;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.ServiceHost;
import com.vmware.xenon.common.ServiceHost.ServiceNotFoundException;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.test.TestContext;
import com.vmware.xenon.services.common.AuthCredentialsService;
import com.vmware.xenon.services.common.AuthCredentialsService.AuthCredentialsServiceState;
import com.vmware.xenon.services.common.RoleService;
import com.vmware.xenon.services.common.RoleService.RoleState;
import com.vmware.xenon.services.common.UserGroupService;

public class PrincipalServiceTest_Purified extends AuthBaseTest {

    @Before
    public void setIdentity() throws GeneralSecurityException {
        host.assumeIdentity(buildUserServicePath(USER_EMAIL_ADMIN));
    }

    @Test
    public void testGetSecurityContextWithDefaultHarborRegistryCredentials_1_testMerged_1() throws Throwable {
        AuthCredentialsServiceState credentials = new AuthCredentialsServiceState();
        credentials = getOrCreateDocument(credentials, AuthCredentialsService.FACTORY_LINK);
        assertNotNull("Failed to create credentials", credentials);
        SecurityContext securityContext = getSecurityContextByCredentials(credentials.userEmail, credentials.privateKey);
        assertEquals(credentials.userEmail, securityContext.id);
        assertEquals(1, securityContext.roles.size());
        assertTrue(securityContext.roles.contains(AuthRole.CLOUD_ADMIN));
        securityContext = getSecurityContextByCredentials(USER_EMAIL_GLORIA, "Password1!");
        assertEquals(USER_NAME_GLORIA, securityContext.name);
        assertEquals(USER_EMAIL_GLORIA, securityContext.id);
        assertTrue(securityContext.roles.contains(AuthRole.BASIC_USER));
        assertTrue(securityContext.roles.contains(AuthRole.BASIC_USER_EXTENDED));
    }

    @Test
    public void testGetSecurityContextWithDefaultHarborRegistryCredentials_2() throws Throwable {
        RegistryState registryState = new RegistryState();
        registryState = getOrCreateDocument(registryState, RegistryFactoryService.SELF_LINK);
        assertNotNull("Failed to create registry", registryState);
    }
}
