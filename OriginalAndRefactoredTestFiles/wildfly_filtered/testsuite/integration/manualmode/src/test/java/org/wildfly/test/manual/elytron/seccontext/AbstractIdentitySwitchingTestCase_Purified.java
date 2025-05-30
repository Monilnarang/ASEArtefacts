package org.wildfly.test.manual.elytron.seccontext;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.wildfly.test.manual.elytron.seccontext.SeccontextUtil.JAR_ENTRY_EJB;
import static org.wildfly.test.manual.elytron.seccontext.SeccontextUtil.WAR_ENTRY_SERVLET_BASIC;
import static org.wildfly.test.manual.elytron.seccontext.SeccontextUtil.WAR_ENTRY_SERVLET_BEARER_TOKEN;
import static org.wildfly.test.manual.elytron.seccontext.SeccontextUtil.WAR_ENTRY_SERVLET_FORM;
import static org.wildfly.test.manual.elytron.seccontext.SeccontextUtil.WAR_WHOAMI;
import java.io.IOException;
import java.util.concurrent.Callable;
import jakarta.ejb.EJBAccessException;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.test.integration.management.util.MgmtOperationException;
import org.jboss.as.test.integration.security.common.Utils;
import org.junit.Ignore;
import org.junit.Test;

public abstract class AbstractIdentitySwitchingTestCase_Purified extends AbstractSecurityContextPropagationTestBase {

    protected void setupServer1() throws CommandLineException, IOException, MgmtOperationException {
        server1.resetContainerConfiguration(new ServerConfigurationBuilder().withDeployments(JAR_ENTRY_EJB, WAR_ENTRY_SERVLET_BASIC, WAR_ENTRY_SERVLET_FORM, WAR_ENTRY_SERVLET_BEARER_TOKEN).withCliCommands("/subsystem=elytron/simple-permission-mapper=seccontext-server-permissions" + ":write-attribute(name=permission-mappings[1],value={principals=[entry]," + "permissions=[{class-name=org.wildfly.security.auth.permission.LoginPermission}," + "{class-name=org.wildfly.security.auth.permission.RunAsPrincipalPermission, target-name=authz}," + "{class-name=org.wildfly.security.auth.permission.RunAsPrincipalPermission, target-name=" + "no-server2-identity}]})").build());
    }

    protected void setupServer2() throws CommandLineException, IOException, MgmtOperationException {
        server2.resetContainerConfiguration(new ServerConfigurationBuilder().withDeployments(WAR_WHOAMI).withCliCommands("/subsystem=elytron/simple-permission-mapper=seccontext-server-permissions" + ":write-attribute(name=permission-mappings[1],value={principals=[entry]," + "permissions=[{class-name=org.wildfly.security.auth.permission.LoginPermission}," + "{class-name=org.wildfly.security.auth.permission.RunAsPrincipalPermission, target-name=authz}," + "{class-name=org.wildfly.security.auth.permission.RunAsPrincipalPermission, target-name=" + "no-server2-identity}]})").build());
    }

    @Test
    public void testServletBasicToEjbAuthenticationContext_1() throws Exception {
        assertEquals("Unexpected username returned", "whoami", Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, "whoami", "whoami", ReAuthnType.AC_AUTHENTICATION), "servlet", "servlet", SC_OK));
    }

    @Test
    public void testServletBasicToEjbAuthenticationContext_2() throws Exception {
        assertEquals("Unexpected username returned", "admin", Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, "admin", "admin", ReAuthnType.AC_AUTHENTICATION), "servlet", "servlet", SC_OK));
    }

    @Test
    public void testServletBasicToEjbAuthenticationContext_3() throws Exception {
        assertEquals("Unexpected username returned", "whoami", Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, "whoami", "whoami", ReAuthnType.AC_AUTHENTICATION), "admin", "admin", SC_OK));
    }

    @Test
    public void testServletBasicToEjbAuthenticationContext_4() throws Exception {
        assertThat(Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, "xadmin", "admin", ReAuthnType.AC_AUTHENTICATION), "admin", "admin", SC_OK), isEjbAuthenticationError());
    }

    @Test
    public void testServletBasicToEjbAuthenticationContext_5() throws Exception {
        assertThat(Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, "admin", "adminx", ReAuthnType.AC_AUTHENTICATION), "admin", "admin", SC_OK), isEjbAuthenticationError());
    }

    @Test
    public void testServletBasicToEjbAuthenticationContext_6() throws Exception {
        assertEquals("Unexpected username returned", "whoami", Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, "server", "server", "whoami", ReAuthnType.AC_AUTHORIZATION), "servlet", "servlet", SC_OK));
    }

    @Test
    public void testServletBasicToEjbAuthenticationContext_7() throws Exception {
        assertEquals("Unexpected username returned", "authz", Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, "entry", "entry", "authz", ReAuthnType.AC_AUTHORIZATION), "servlet", "servlet", SC_OK));
    }

    @Test
    public void testServletBasicToEjbAuthenticationContext_8() throws Exception {
        assertEquals("Unexpected username returned", "authz", Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, "entry", "entry", "authz", ReAuthnType.AC_AUTHORIZATION), "admin", "admin", SC_OK));
    }

    @Test
    public void testServletBasicToEjbAuthenticationContext_9() throws Exception {
        assertThat(Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, "xentry", "entry", "authz", ReAuthnType.AC_AUTHORIZATION), "admin", "admin", SC_OK), isEjbAuthenticationError());
    }

    @Test
    public void testServletBasicToEjbAuthenticationContext_10() throws Exception {
        assertThat(Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, "entry", "entryx", "authz", ReAuthnType.AC_AUTHORIZATION), "admin", "admin", SC_OK), isEjbAuthenticationError());
    }
}
