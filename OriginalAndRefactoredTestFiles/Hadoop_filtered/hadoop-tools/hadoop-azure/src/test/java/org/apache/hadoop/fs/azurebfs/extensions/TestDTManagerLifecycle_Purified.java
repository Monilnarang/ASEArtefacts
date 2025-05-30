package org.apache.hadoop.fs.azurebfs.extensions;

import java.net.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.azurebfs.AbstractAbfsTestWithTimeout;
import org.apache.hadoop.fs.azurebfs.security.AbfsDelegationTokenManager;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.delegation.web.DelegationTokenIdentifier;
import static org.apache.hadoop.fs.azurebfs.extensions.KerberizedAbfsCluster.newURI;
import static org.apache.hadoop.fs.azurebfs.extensions.StubDelegationTokenManager.createToken;
import static org.apache.hadoop.fs.azurebfs.extensions.StubAbfsTokenIdentifier.decodeIdentifier;

@SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
public class TestDTManagerLifecycle_Purified extends AbstractAbfsTestWithTimeout {

    public static final String RENEWER = "resourcemanager";

    private Configuration conf;

    public static final String ABFS = "abfs://testing@account.dfs.core.windows.net";

    public static final URI FSURI = newURI(ABFS);

    public static final Text OWNER = new Text("owner");

    public static final Text KIND2 = new Text("kind2");

    @Before
    public void setup() throws Exception {
        conf = StubDelegationTokenManager.useStubDTManager(new Configuration());
    }

    @After
    public void teardown() throws Exception {
    }

    protected void assertTokenKind(final Text kind, final Token<DelegationTokenIdentifier> dt) {
        assertEquals("Token Kind", kind, dt.getKind());
    }

    protected StubDelegationTokenManager getTokenManager(final AbfsDelegationTokenManager manager) {
        return (StubDelegationTokenManager) manager.getTokenManager();
    }

    @Test
    public void testRenewalThroughManager_1_testMerged_1() throws Throwable {
        AbfsDelegationTokenManager manager = new AbfsDelegationTokenManager(conf);
        StubDelegationTokenManager stub = getTokenManager(manager);
        assertNull("Stub should not bebound " + stub, stub.getFsURI());
        assertEquals("Renewal count in " + stub, 1, stub.getRenewals());
        assertEquals("Cancel count in " + stub, 1, stub.getCancellations());
        assertTrue("Not closed: " + stub, stub.isClosed());
    }

    @Test
    public void testRenewalThroughManager_2_testMerged_2() throws Throwable {
        Token<DelegationTokenIdentifier> dt = createToken(0, FSURI, OWNER, new Text(RENEWER));
        StubAbfsTokenIdentifier dtId = (StubAbfsTokenIdentifier) dt.decodeIdentifier();
        String idStr = dtId.toString();
        assertEquals("URI in " + idStr, FSURI, dtId.getUri());
        assertEquals("renewer in " + idStr, RENEWER, dtId.getRenewer().toString());
    }
}
