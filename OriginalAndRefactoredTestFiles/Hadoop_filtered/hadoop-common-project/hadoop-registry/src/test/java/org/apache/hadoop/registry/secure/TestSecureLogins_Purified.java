package org.apache.hadoop.registry.secure;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.kerberos.KerberosPrincipal;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.apache.zookeeper.Environment;
import org.apache.zookeeper.data.ACL;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.security.HadoopKerberosName;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.authentication.util.KerberosName;
import org.apache.hadoop.security.authentication.util.KerberosUtil;
import org.apache.hadoop.registry.client.impl.zk.RegistrySecurity;
import org.apache.hadoop.registry.client.impl.zk.ZookeeperConfigOptions;
import static org.apache.hadoop.security.authentication.util.KerberosName.DEFAULT_MECHANISM;
import static org.apache.hadoop.security.authentication.util.KerberosName.MECHANISM_HADOOP;
import static org.apache.hadoop.security.authentication.util.KerberosName.MECHANISM_MIT;
import static org.apache.hadoop.util.PlatformName.IBM_JAVA;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSecureLogins_Purified extends AbstractSecureRegistryTest {

    private static final Logger LOG = LoggerFactory.getLogger(TestSecureLogins.class);

    public LoginContext createLoginContextZookeeperLocalhost() throws LoginException {
        String principalAndRealm = getPrincipalAndRealm(ZOOKEEPER_LOCALHOST);
        Set<Principal> principals = new HashSet<Principal>();
        principals.add(new KerberosPrincipal(ZOOKEEPER_LOCALHOST));
        Subject subject = new Subject(false, principals, new HashSet<Object>(), new HashSet<Object>());
        return new LoginContext("", subject, null, KerberosConfiguration.createServerConfig(ZOOKEEPER_LOCALHOST, keytab_zk));
    }

    @Test
    public void testJaasFileSetup_1() throws Throwable {
        assertNotNull("jaasFile", jaasFile);
    }

    @Test
    public void testJaasFileSetup_2() throws Throwable {
        String confFilename = System.getProperty(Environment.JAAS_CONF_KEY);
        assertEquals(jaasFile.getAbsolutePath(), confFilename);
    }

    @Test
    public void testJaasFileBinding_1() throws Throwable {
        assertNotNull("jaasFile", jaasFile);
    }

    @Test
    public void testJaasFileBinding_2() throws Throwable {
        RegistrySecurity.bindJVMtoJAASFile(jaasFile);
        String confFilename = System.getProperty(Environment.JAAS_CONF_KEY);
        assertEquals(jaasFile.getAbsolutePath(), confFilename);
    }

    @Test
    public void testKerberosRulesValid_1() throws Throwable {
        assertTrue("!KerberosName.hasRulesBeenSet()", KerberosName.hasRulesBeenSet());
    }

    @Test
    public void testKerberosRulesValid_2() throws Throwable {
        String rules = KerberosName.getRules();
        assertEquals(kerberosRule, rules);
    }
}
