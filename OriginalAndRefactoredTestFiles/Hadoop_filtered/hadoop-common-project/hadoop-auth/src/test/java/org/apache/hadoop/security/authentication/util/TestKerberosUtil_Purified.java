package org.apache.hadoop.security.authentication.util;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.kerby.kerberos.kerb.keytab.Keytab;
import org.apache.kerby.kerberos.kerb.keytab.KeytabEntry;
import org.apache.kerby.kerberos.kerb.type.KerberosTime;
import org.apache.kerby.kerberos.kerb.type.base.EncryptionKey;
import org.apache.kerby.kerberos.kerb.type.base.EncryptionType;
import org.apache.kerby.kerberos.kerb.type.base.PrincipalName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class TestKerberosUtil_Purified {

    static String testKeytab = "test.keytab";

    static String[] testPrincipals = new String[] { "HTTP@testRealm", "test/testhost@testRealm", "HTTP/testhost@testRealm", "HTTP1/testhost@testRealm", "HTTP/testhostanother@testRealm" };

    @After
    public void deleteKeytab() {
        File keytabFile = new File(testKeytab);
        if (keytabFile.exists()) {
            keytabFile.delete();
        }
    }

    private void createKeyTab(String fileName, String[] principalNames) throws IOException {
        List<KeytabEntry> lstEntries = new ArrayList<KeytabEntry>();
        for (String principal : principalNames) {
            for (int kvno = 1; kvno <= 3; kvno++) {
                EncryptionKey key = new EncryptionKey(EncryptionType.NONE, "samplekey1".getBytes(), kvno);
                KeytabEntry keytabEntry = new KeytabEntry(new PrincipalName(principal), new KerberosTime(), (byte) 1, key);
                lstEntries.add(keytabEntry);
            }
        }
        Keytab keytab = new Keytab();
        keytab.addKeytabEntries(lstEntries);
        keytab.store(new File(testKeytab));
    }

    private static String getPrincipal(String token) {
        return KerberosUtil.getTokenServerName(Base64.getDecoder().decode(token));
    }

    @Test
    public void testServicePrincipalDecode_1() throws Exception {
        String krb5Default = "YIIB2AYJKoZIhvcSAQICAQBuggHHMIIBw6ADAgEFoQMCAQ6iBwMFACAAAACj" + "gethgegwgeWgAwIBBaENGwtFWEFNUExFLkNPTaIcMBqgAwIBAKETMBEbBEhU" + "VFAbCWxvY2FsaG9zdKOBsDCBraADAgERoQMCAQGigaAEgZ23QsT1+16T23ni" + "JI1uFRU0FN13hhPSLAl4+oAqpV5s1Z6E+G2VKGx2+rUF21utOdlwUK/J5CKF" + "HxM4zfNsmzRFhdk5moJW6AWHuRqGJ9hrZgTxA2vOBIn/tju+n/vJVEcUvW0f" + "DiPfjPIPFOlc7V9GlWvZFyr5NMJSFwspKJXYh/FSNpSVTecfGskjded9TZzR" + "2tOVzgpjFvAu/DETpIG/MIG8oAMCARGigbQEgbGWnbKlV1oo7/gzT4hi/Q41" + "ff2luDnSxADEmo6M8LC42scsYMLNgU4iLJhuf4YLb7ueh790HrbB6Kdes71/" + "gSBiLI2/mn3BqNE43gt94dQ8VFBix4nJCsYnuORYxLJjRSJE+3ImJNsSjqaf" + "GRI0sp9w3hc4IVm8afb3Ggm6PgRIyyGNdTzK/p03v+zA01MJh3htuOgLKUOV" + "z002pHnGzu/purZ5mOyaQT12vHxJ2T+Cwi8=";
        assertEquals("HTTP/localhost@EXAMPLE.COM", getPrincipal(krb5Default));
    }

    @Test
    public void testServicePrincipalDecode_2() throws Exception {
        String krb5Other = "YIIB2AYJKoZIhvcSAQICAQBuggHHMIIBw6ADAgEFoQMCAQ6iBwMFACAAAACj" + "gethgegwgeWgAwIBBaENGwtBQkNERUZHLk9SR6IcMBqgAwIBAKETMBEbBEhU" + "VFAbCW90aGVyaG9zdKOBsDCBraADAgERoQMCAQGigaAEgZ23QsT1+16T23ni" + "JI1uFRU0FN13hhPSLAl4+oAqpV5s1Z6E+G2VKGx2+rUF21utOdlwUK/J5CKF" + "HxM4zfNsmzRFhdk5moJW6AWHuRqGJ9hrZgTxA2vOBIn/tju+n/vJVEcUvW0f" + "DiPfjPIPFOlc7V9GlWvZFyr5NMJSFwspKJXYh/FSNpSVTecfGskjded9TZzR" + "2tOVzgpjFvAu/DETpIG/MIG8oAMCARGigbQEgbGWnbKlV1oo7/gzT4hi/Q41" + "ff2luDnSxADEmo6M8LC42scsYMLNgU4iLJhuf4YLb7ueh790HrbB6Kdes71/" + "gSBiLI2/mn3BqNE43gt94dQ8VFBix4nJCsYnuORYxLJjRSJE+3ImJNsSjqaf" + "GRI0sp9w3hc4IVm8afb3Ggm6PgRIyyGNdTzK/p03v+zA01MJh3htuOgLKUOV" + "z002pHnGzu/purZ5mOyaQT12vHxJ2T+Cwi8K";
        assertEquals("HTTP/otherhost@ABCDEFG.ORG", getPrincipal(krb5Other));
    }

    @Test
    public void testServicePrincipalDecode_3() throws Exception {
        String spnegoDefault = "YIICCQYGKwYBBQUCoIIB/TCCAfmgDTALBgkqhkiG9xIBAgKhBAMCAXaiggHg" + "BIIB3GCCAdgGCSqGSIb3EgECAgEAboIBxzCCAcOgAwIBBaEDAgEOogcDBQAg" + "AAAAo4HrYYHoMIHloAMCAQWhDRsLRVhBTVBMRS5DT02iHDAaoAMCAQChEzAR" + "GwRIVFRQGwlsb2NhbGhvc3SjgbAwga2gAwIBEaEDAgEBooGgBIGdBWbzvV1R" + "Iqb7WuPIW3RTkFtwjU9P/oFAbujGPd8h/qkCszroNdvHhUkPntuOqhFBntMo" + "bilgTqNEdDUGvBbfkJaRklNGqT/IAOUV6tlGpBUCXquR5UdPzPpUvGZiVRUu" + "FGH5DGGHvYF1CwXPp2l1Jq373vSLQ1kBl6TXl+aKLsZYhVUjKvE7Auippclb" + "hv/GGGex/TcjNH48k47OQaSBvzCBvKADAgERooG0BIGxeChp3TMVtWbCdFGo" + "YL+35r2762j+OEwZRfcj4xCK7j0mUTcxLtyVGxyY9Ax+ljl5gTwzRhXcJq0T" + "TjiQwKJckeZ837mXQAURbfJpFc3VLAXGfNkMFCR7ZkWpGA1Vzc3PeUNczn2D" + "Lpu8sme55HFFQDi/0akW6Lwv/iCrpwIkZPyZPjaEmwLVALu4E8m0Ka3fJkPV" + "GAhamg9OQpuREIK0pCk3ZSHhJz8qMwduzRZHc4vN";
        assertEquals("HTTP/localhost@EXAMPLE.COM", getPrincipal(spnegoDefault));
    }

    @Test
    public void testServicePrincipalDecode_4() throws Exception {
        String spnegoOther = "YIICCQYGKwYBBQUCoIIB/TCCAfmgDTALBgkqhkiG9xIBAgKhBAMCAXaiggHg" + "BIIB3GCCAdgGCSqGSIb3EgECAgEAboIBxzCCAcOgAwIBBaEDAgEOogcDBQAg" + "AAAAo4HrYYHoMIHloAMCAQWhDRsLQUJDREVGRy5PUkeiHDAaoAMCAQChEzAR" + "GwRIVFRQGwlvdGhlcmhvc3SjgbAwga2gAwIBEaEDAgEBooGgBIGdBWbzvV1R" + "Iqb7WuPIW3RTkFtwjU9P/oFAbujGPd8h/qkCszroNdvHhUkPntuOqhFBntMo" + "bilgTqNEdDUGvBbfkJaRklNGqT/IAOUV6tlGpBUCXquR5UdPzPpUvGZiVRUu" + "FGH5DGGHvYF1CwXPp2l1Jq373vSLQ1kBl6TXl+aKLsZYhVUjKvE7Auippclb" + "hv/GGGex/TcjNH48k47OQaSBvzCBvKADAgERooG0BIGxeChp3TMVtWbCdFGo" + "YL+35r2762j+OEwZRfcj4xCK7j0mUTcxLtyVGxyY9Ax+ljl5gTwzRhXcJq0T" + "TjiQwKJckeZ837mXQAURbfJpFc3VLAXGfNkMFCR7ZkWpGA1Vzc3PeUNczn2D" + "Lpu8sme55HFFQDi/0akW6Lwv/iCrpwIkZPyZPjaEmwLVALu4E8m0Ka3fJkPV" + "GAhamg9OQpuREIK0pCk3ZSHhJz8qMwduzRZHc4vNCg==";
        assertEquals("HTTP/otherhost@ABCDEFG.ORG", getPrincipal(spnegoOther));
    }
}
