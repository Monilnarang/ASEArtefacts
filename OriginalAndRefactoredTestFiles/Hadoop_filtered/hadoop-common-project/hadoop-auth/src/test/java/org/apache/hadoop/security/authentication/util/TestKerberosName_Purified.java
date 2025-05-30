package org.apache.hadoop.security.authentication.util;

import java.io.IOException;
import org.apache.hadoop.security.authentication.KerberosTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class TestKerberosName_Purified {

    @Before
    public void setUp() throws Exception {
        System.setProperty("java.security.krb5.realm", KerberosTestUtils.getRealm());
        System.setProperty("java.security.krb5.kdc", "localhost:88");
        String rules = "RULE:[1:$1@$0](.*@YAHOO\\.COM)s/@.*//\n" + "RULE:[2:$1](johndoe)s/^.*$/guest/\n" + "RULE:[2:$1;$2](^.*;admin$)s/;admin$//\n" + "RULE:[2:$2](root)\n" + "DEFAULT";
        KerberosName.setRuleMechanism(KerberosName.MECHANISM_HADOOP);
        KerberosName.setRules(rules);
        KerberosName.printRules();
    }

    private void checkTranslation(String from, String to) throws Exception {
        System.out.println("Translate " + from);
        KerberosName nm = new KerberosName(from);
        String simple = nm.getShortName();
        System.out.println("to " + simple);
        Assert.assertEquals("short name incorrect", to, simple);
    }

    private void checkBadName(String name) {
        System.out.println("Checking " + name + " to ensure it is bad.");
        try {
            new KerberosName(name);
            Assert.fail("didn't get exception for " + name);
        } catch (IllegalArgumentException iae) {
        }
    }

    private void checkBadTranslation(String from) {
        System.out.println("Checking bad translation for " + from);
        KerberosName nm = new KerberosName(from);
        try {
            nm.getShortName();
            Assert.fail("didn't get exception for " + from);
        } catch (IOException ie) {
        }
    }

    @After
    public void clear() {
        System.clearProperty("java.security.krb5.realm");
        System.clearProperty("java.security.krb5.kdc");
    }

    @Test
    public void testParsing_1_testMerged_1() throws Exception {
        final String principalNameFull = "HTTP/abc.com@EXAMPLE.COM";
        final KerberosName kerbNameFull = new KerberosName(principalNameFull);
        Assert.assertEquals("HTTP", kerbNameFull.getServiceName());
        Assert.assertEquals("abc.com", kerbNameFull.getHostName());
        Assert.assertEquals("EXAMPLE.COM", kerbNameFull.getRealm());
    }

    @Test
    public void testParsing_4_testMerged_2() throws Exception {
        final String principalNameWoRealm = "HTTP/abc.com";
        final KerberosName kerbNamewoRealm = new KerberosName(principalNameWoRealm);
        Assert.assertEquals("HTTP", kerbNamewoRealm.getServiceName());
        Assert.assertEquals("abc.com", kerbNamewoRealm.getHostName());
        Assert.assertEquals(null, kerbNamewoRealm.getRealm());
    }

    @Test
    public void testParsing_7_testMerged_3() throws Exception {
        final String principalNameWoHost = "HTTP@EXAMPLE.COM";
        final KerberosName kerbNameWoHost = new KerberosName(principalNameWoHost);
        Assert.assertEquals("HTTP", kerbNameWoHost.getServiceName());
        Assert.assertEquals(null, kerbNameWoHost.getHostName());
        Assert.assertEquals("EXAMPLE.COM", kerbNameWoHost.getRealm());
    }
}
