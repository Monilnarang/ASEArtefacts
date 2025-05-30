package com.vmware.admiral.host;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import com.vmware.admiral.auth.idm.AuthConfigProvider;
import com.vmware.admiral.common.test.BaseTestCase;
import com.vmware.admiral.compute.ComputeConstants;
import com.vmware.admiral.compute.ContainerHostService;
import com.vmware.admiral.compute.ContainerHostService.ContainerHostType;
import com.vmware.admiral.compute.PlacementZoneConstants;
import com.vmware.admiral.compute.PlacementZoneConstants.PlacementZoneType;
import com.vmware.admiral.host.interceptor.AuthCredentialsInterceptor;
import com.vmware.admiral.host.interceptor.OperationInterceptorRegistry;
import com.vmware.photon.controller.model.resources.ComputeService;
import com.vmware.photon.controller.model.resources.ComputeService.ComputeState;
import com.vmware.photon.controller.model.resources.ResourcePoolService;
import com.vmware.photon.controller.model.resources.ResourcePoolService.ResourcePoolState;
import com.vmware.photon.controller.model.security.util.AuthCredentialsOperationProcessingChain;
import com.vmware.photon.controller.model.security.util.AuthCredentialsType;
import com.vmware.photon.controller.model.security.util.EncryptionUtils;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.services.common.AuthCredentialsService;
import com.vmware.xenon.services.common.AuthCredentialsService.AuthCredentialsServiceState;

public class AuthCredentialsInterceptorTest_Purified extends BaseTestCase {

    @ClassRule
    public static TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Throwable {
        waitForServiceAvailability(AuthCredentialsService.FACTORY_LINK);
        System.clearProperty(EncryptionUtils.ENCRYPTION_KEY);
        System.clearProperty(EncryptionUtils.INIT_KEY_IF_MISSING);
        EncryptionUtils.initEncryptionService();
    }

    @Override
    protected void registerInterceptors(OperationInterceptorRegistry registry) {
        AuthCredentialsInterceptor.register(registry);
    }

    protected AuthCredentialsServiceState createCredentials(String username, String password, boolean isSystem) throws Throwable {
        AuthCredentialsServiceState credentials = new AuthCredentialsServiceState();
        credentials.userEmail = username;
        credentials.privateKey = password;
        credentials.type = AuthCredentialsType.Password.toString();
        if (isSystem) {
            credentials.customProperties = new HashMap<>();
            credentials.customProperties.put(AuthConfigProvider.PROPERTY_SCOPE, AuthConfigProvider.CredentialsScope.SYSTEM.toString());
        }
        return getOrCreateDocument(credentials, AuthCredentialsService.FACTORY_LINK);
    }

    protected AuthCredentialsServiceState createCredentialsWithKeys(String publicKey, String privateKey) throws Throwable {
        AuthCredentialsServiceState credentials = new AuthCredentialsServiceState();
        credentials.publicKey = publicKey;
        credentials.privateKey = privateKey;
        credentials.type = AuthCredentialsType.PublicKey.toString();
        return getOrCreateDocument(credentials, AuthCredentialsService.FACTORY_LINK);
    }

    private ResourcePoolState createResourcePoolState(String placementZoneName) {
        assertNotNull(placementZoneName);
        ResourcePoolState placementZone = new ResourcePoolState();
        placementZone.id = placementZoneName;
        placementZone.name = placementZoneName;
        placementZone.documentSelfLink = ResourcePoolService.FACTORY_LINK + "/" + placementZone.id;
        placementZone.customProperties = new HashMap<>();
        placementZone.customProperties.put(PlacementZoneConstants.PLACEMENT_ZONE_TYPE_CUSTOM_PROP_NAME, PlacementZoneType.DOCKER.toString());
        return placementZone;
    }

    private ResourcePoolState createPlacementZone(String placementZoneName) throws Throwable {
        ResourcePoolState resourcePoolState = createResourcePoolState(placementZoneName);
        return doPost(resourcePoolState, ResourcePoolService.FACTORY_LINK);
    }

    private ComputeState createComputeState(ResourcePoolState placementZone, AuthCredentialsServiceState credentials) throws Throwable {
        assertNotNull(placementZone);
        assertNotNull(credentials);
        ComputeState computeState = new ComputeState();
        computeState.address = "no-address";
        computeState.descriptionLink = "no-description-link";
        computeState.resourcePoolLink = placementZone.documentSelfLink;
        computeState.customProperties = new HashMap<>();
        computeState.customProperties.put(ContainerHostService.CONTAINER_HOST_TYPE_PROP_NAME, ContainerHostType.DOCKER.toString());
        computeState.customProperties.put(ComputeConstants.HOST_AUTH_CREDENTIALS_PROP_NAME, credentials.documentSelfLink);
        return doPost(computeState, ComputeService.FACTORY_LINK);
    }

    private void verifyExceptionMessage(String message, String expected) {
        if (!message.equals(expected)) {
            String errorMessage = String.format("Expected error '%s' but was '%s'", expected, message);
            throw new IllegalStateException(errorMessage);
        }
    }

    @Test
    public void testPlainTextCredentials_1() throws Throwable {
        assertNull("ENCRYPTION_KEY env variable should be null", System.getProperty(EncryptionUtils.ENCRYPTION_KEY));
    }

    @Test
    public void testPlainTextCredentials_2() throws Throwable {
        assertNull("INIT_KEY_IF_MISSING env variable should be null", System.getProperty(EncryptionUtils.INIT_KEY_IF_MISSING));
    }

    @Test
    public void testPlainTextCredentials_3_testMerged_3() throws Throwable {
        AuthCredentialsServiceState credentials = createCredentials("username", "password", false);
        assertEquals("username mismatch", "username", credentials.userEmail);
        assertNotNull("privateKey should not be null", credentials.privateKey);
        assertFalse("private key should not be encrypted", credentials.privateKey.startsWith(EncryptionUtils.ENCRYPTION_PREFIX));
        String publicKey = "-----BEGIN CERTIFICATE-----\nABC\n-----END CERTIFICATE-----";
        credentials = createCredentialsWithKeys(publicKey, "-----BEGIN PRIVATE KEY-----\nDEF\n-----END PRIVATE KEY-----");
        assertEquals("public key mismatch", publicKey, credentials.publicKey);
    }
}
