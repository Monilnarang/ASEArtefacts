package com.vmware.admiral.common.util;

import static org.junit.Assert.assertEquals;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.service.common.SslTrustCertificateFactoryService;
import com.vmware.admiral.service.common.SslTrustCertificateService;
import com.vmware.admiral.service.common.SslTrustCertificateService.SslTrustCertificateState;
import com.vmware.photon.controller.model.resources.ComputeService;
import com.vmware.photon.controller.model.resources.ComputeService.ComputeState;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.ServiceDocument;
import com.vmware.xenon.common.ServiceDocumentQueryResult;
import com.vmware.xenon.common.ServiceHost;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.Utils;
import com.vmware.xenon.common.test.VerificationHost;

public class CertificateCleanupUtilTest_Purified {

    private VerificationHost host;

    private SslTrustCertificateState sslTrustCert;

    private Set<String> entitiesToDelete;

    @Before
    public void setup() throws Throwable {
        entitiesToDelete = new HashSet<>();
        host = createVerificationHost();
        startServices();
        sslTrustCert = createSslTrustCert();
    }

    @After
    public void cleanup() {
        entitiesToDelete.forEach(link -> {
            try {
                deleteEntity(link);
            } catch (Throwable e) {
                host.log(Level.WARNING, "Failed to delete entity [%s] as part of cleanup: %s", link, Utils.toString(e));
            }
        });
    }

    private VerificationHost createVerificationHost() throws Throwable {
        VerificationHost host = new VerificationHost();
        ServiceHost.Arguments args = VerificationHost.buildDefaultServiceHostArguments(0);
        VerificationHost.initialize(host, args);
        host.start();
        host.setMaintenanceIntervalMicros(TimeUnit.MILLISECONDS.toMicros(100));
        return host;
    }

    private void startServices() {
        host.startFactory(new ComputeService());
        host.startService(new SslTrustCertificateFactoryService());
        waitForServices(SslTrustCertificateService.FACTORY_LINK, ComputeService.FACTORY_LINK);
    }

    private void waitForServices(String... services) {
        host.testStart(services.length);
        host.registerForServiceAvailability(host.getCompletion(), services);
        host.testWait();
    }

    private long getTrustCertsCount() throws Throwable {
        Operation get = Operation.createGet(host, SslTrustCertificateService.FACTORY_LINK).setReferer(host.getUri());
        return host.sendWithDeferredResult(get, ServiceDocumentQueryResult.class).toCompletionStage().toCompletableFuture().get().documentCount;
    }

    private void trustCertCleanup(String excludeComputeLink) throws Throwable {
        CertificateCleanupUtil.removeTrustCertsIfUnused(host, Collections.singleton(sslTrustCert.documentSelfLink), excludeComputeLink == null ? null : Collections.singleton(excludeComputeLink)).toCompletionStage().toCompletableFuture().get();
    }

    private SslTrustCertificateState createSslTrustCert() throws Throwable {
        if (this.sslTrustCert != null) {
            return this.sslTrustCert;
        }
        String sslTrustPem = FileUtil.getResourceAsString("/certs/ca.pem", true).trim();
        SslTrustCertificateState sslTrustCert = new SslTrustCertificateState();
        sslTrustCert.certificate = sslTrustPem;
        return createEntity(sslTrustCert, SslTrustCertificateService.FACTORY_LINK);
    }

    private ComputeState createComputeState(String certLink) throws Throwable {
        ComputeState computeState = new ComputeState();
        computeState.name = UUID.randomUUID().toString();
        computeState.address = UUID.randomUUID().toString();
        computeState.descriptionLink = UUID.randomUUID().toString();
        if (certLink != null && !certLink.isEmpty()) {
            computeState.customProperties = Collections.singletonMap(CertificateUtilExtended.CUSTOM_PROPERTY_TRUST_CERT_LINK, certLink);
        }
        return createEntity(computeState, ComputeService.FACTORY_LINK);
    }

    @SuppressWarnings("unchecked")
    private <T extends ServiceDocument> T createEntity(T entityState, String factoryLink) throws Throwable {
        Operation post = Operation.createPost(host, factoryLink).setReferer(host.getUri()).setBody(entityState);
        T result = host.sendWithDeferredResult(post, (Class<T>) entityState.getClass()).exceptionally(ex -> {
            host.log(Level.SEVERE, "Failed to create entity for factory [%s]: %s", factoryLink, Utils.toString(ex));
            throw ex instanceof CompletionException ? (CompletionException) ex : new CompletionException(ex);
        }).toCompletionStage().toCompletableFuture().get();
        entitiesToDelete.add(result.documentSelfLink);
        return result;
    }

    private void deleteEntity(String entityLink) throws Throwable {
        Operation delete = Operation.createDelete(host, entityLink).setReferer(host.getUri());
        host.sendWithFuture(delete).exceptionally(ex -> {
            host.log(Level.SEVERE, "Failed to delete entity [%s]: %s", entityLink, Utils.toString(ex));
            throw ex instanceof CompletionException ? (CompletionException) ex : new CompletionException(ex);
        }).get();
    }

    @Test
    public void testCleanupWithNoHostsPasses_1() throws Throwable {
        assertEquals(1, getTrustCertsCount());
    }

    @Test
    public void testCleanupWithNoHostsPasses_2() throws Throwable {
        assertEquals(0, getTrustCertsCount());
    }

    @Test
    public void testCleanupWhithHostWithoutCertsPasses_1() throws Throwable {
        assertEquals(1, getTrustCertsCount());
    }

    @Test
    public void testCleanupWhithHostWithoutCertsPasses_2() throws Throwable {
        assertEquals(0, getTrustCertsCount());
    }

    @Test
    public void testCleanupWhithUnrelatedHostPasses_1() throws Throwable {
        assertEquals(1, getTrustCertsCount());
    }

    @Test
    public void testCleanupWhithUnrelatedHostPasses_2() throws Throwable {
        assertEquals(0, getTrustCertsCount());
    }

    @Test
    public void testCleanupAfterHostDeletionPasses_1() throws Throwable {
        assertEquals(1, getTrustCertsCount());
    }

    @Test
    public void testCleanupAfterHostDeletionPasses_2() throws Throwable {
        assertEquals(1, getTrustCertsCount());
    }

    @Test
    public void testCleanupAfterHostDeletionPasses_3() throws Throwable {
        assertEquals(0, getTrustCertsCount());
    }

    @Test
    public void testCleanupWhenHostIsIgnoredPasses_1() throws Throwable {
        assertEquals(1, getTrustCertsCount());
    }

    @Test
    public void testCleanupWhenHostIsIgnoredPasses_2() throws Throwable {
        assertEquals(0, getTrustCertsCount());
    }
}
