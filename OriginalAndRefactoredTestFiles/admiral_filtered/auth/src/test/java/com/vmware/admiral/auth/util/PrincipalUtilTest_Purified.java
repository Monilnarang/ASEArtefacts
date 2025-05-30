package com.vmware.admiral.auth.util;

import static com.vmware.admiral.auth.util.PrincipalUtil.copyPrincipalData;
import static com.vmware.admiral.auth.util.PrincipalUtil.decode;
import static com.vmware.admiral.auth.util.PrincipalUtil.encode;
import static com.vmware.admiral.auth.util.PrincipalUtil.fromLocalPrincipalToPrincipal;
import static com.vmware.admiral.auth.util.PrincipalUtil.fromPrincipalToLocalPrincipal;
import static com.vmware.admiral.auth.util.PrincipalUtil.fromQueryResultToPrincipalList;
import static com.vmware.admiral.auth.util.PrincipalUtil.toNameAndDomain;
import static com.vmware.admiral.auth.util.PrincipalUtil.toPrincipalId;
import static com.vmware.admiral.auth.util.PrincipalUtil.toPrincipalName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.auth.idm.Principal;
import com.vmware.admiral.auth.idm.Principal.PrincipalType;
import com.vmware.admiral.auth.idm.local.LocalPrincipalFactoryService;
import com.vmware.admiral.auth.idm.local.LocalPrincipalService.LocalPrincipalState;
import com.vmware.admiral.auth.idm.local.LocalPrincipalService.LocalPrincipalType;
import com.vmware.admiral.common.util.ConfigurationUtil;
import com.vmware.admiral.service.common.ConfigurationService.ConfigurationState;
import com.vmware.photon.controller.model.adapters.util.Pair;
import com.vmware.xenon.common.ServiceDocumentQueryResult;
import com.vmware.xenon.common.UriUtils;

public class PrincipalUtilTest_Purified {

    private LocalPrincipalState testLocalPrincipal;

    private Principal testPrincipal;

    @Before
    public void setup() {
        testLocalPrincipal = new LocalPrincipalState();
        testLocalPrincipal.id = "test@admiral.com";
        testLocalPrincipal.email = testLocalPrincipal.id;
        testLocalPrincipal.name = "Test User";
        testLocalPrincipal.type = LocalPrincipalType.USER;
        testLocalPrincipal.documentSelfLink = "testSelfLink";
        testPrincipal = new Principal();
        testPrincipal.id = "test@admiral.com";
        testPrincipal.email = testLocalPrincipal.id;
        testPrincipal.name = "Test User";
        testPrincipal.type = PrincipalType.USER;
    }

    @Test
    public void testFromLocalPrincipalToPrincipalOfTypeUser_1() {
        assertEquals(null, fromLocalPrincipalToPrincipal(null));
    }

    @Test
    public void testFromLocalPrincipalToPrincipalOfTypeUser_2_testMerged_2() {
        Principal principal = fromLocalPrincipalToPrincipal(testLocalPrincipal);
        assertEquals(testLocalPrincipal.id, principal.id);
        assertEquals(testLocalPrincipal.email, principal.email);
        assertEquals(testLocalPrincipal.name, principal.name);
        assertEquals(PrincipalType.USER, principal.type);
    }

    @Test
    public void testFromPrincipalToLocalPrincipalOfTypeUser_1() {
        assertEquals(null, fromPrincipalToLocalPrincipal(null));
    }

    @Test
    public void testFromPrincipalToLocalPrincipalOfTypeUser_2_testMerged_2() {
        LocalPrincipalState localPrincipal = fromPrincipalToLocalPrincipal(testPrincipal);
        assertEquals(testPrincipal.id, localPrincipal.id);
        assertEquals(testPrincipal.email, localPrincipal.email);
        assertEquals(testPrincipal.name, localPrincipal.name);
        assertEquals(LocalPrincipalType.USER, localPrincipal.type);
    }

    @Test
    public void testEncodeDecodeNoop_1() {
        assertEquals(null, encode(null));
    }

    @Test
    public void testEncodeDecodeNoop_2() {
        assertEquals("", encode(""));
    }

    @Test
    public void testEncodeDecodeNoop_3() {
        assertEquals(null, decode(null));
    }

    @Test
    public void testEncodeDecodeNoop_4() {
        assertEquals("", decode(""));
    }

    @Test
    public void testCopyPrincipalData_1() {
        assertEquals(null, copyPrincipalData(null, new Principal()));
    }

    @Test
    public void testCopyPrincipalData_2_testMerged_2() {
        Principal principal = new Principal();
        assertEquals(principal, copyPrincipalData(principal, null));
        principal = copyPrincipalData(testPrincipal, principal);
        assertEquals(testPrincipal.id, principal.id);
        assertEquals(testPrincipal.email, principal.email);
        assertEquals(testPrincipal.type, principal.type);
        assertEquals(testPrincipal.name, principal.name);
        assertEquals(testPrincipal.password, principal.password);
    }
}
