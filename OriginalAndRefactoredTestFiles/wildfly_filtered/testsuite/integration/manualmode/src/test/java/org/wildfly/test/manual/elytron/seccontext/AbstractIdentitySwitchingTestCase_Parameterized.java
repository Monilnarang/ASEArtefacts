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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public abstract class AbstractIdentitySwitchingTestCase_Parameterized extends AbstractSecurityContextPropagationTestBase {

    protected void setupServer1() throws CommandLineException, IOException, MgmtOperationException {
        server1.resetContainerConfiguration(new ServerConfigurationBuilder().withDeployments(JAR_ENTRY_EJB, WAR_ENTRY_SERVLET_BASIC, WAR_ENTRY_SERVLET_FORM, WAR_ENTRY_SERVLET_BEARER_TOKEN).withCliCommands("/subsystem=elytron/simple-permission-mapper=seccontext-server-permissions" + ":write-attribute(name=permission-mappings[1],value={principals=[entry]," + "permissions=[{class-name=org.wildfly.security.auth.permission.LoginPermission}," + "{class-name=org.wildfly.security.auth.permission.RunAsPrincipalPermission, target-name=authz}," + "{class-name=org.wildfly.security.auth.permission.RunAsPrincipalPermission, target-name=" + "no-server2-identity}]})").build());
    }

    protected void setupServer2() throws CommandLineException, IOException, MgmtOperationException {
        server2.resetContainerConfiguration(new ServerConfigurationBuilder().withDeployments(WAR_WHOAMI).withCliCommands("/subsystem=elytron/simple-permission-mapper=seccontext-server-permissions" + ":write-attribute(name=permission-mappings[1],value={principals=[entry]," + "permissions=[{class-name=org.wildfly.security.auth.permission.LoginPermission}," + "{class-name=org.wildfly.security.auth.permission.RunAsPrincipalPermission, target-name=authz}," + "{class-name=org.wildfly.security.auth.permission.RunAsPrincipalPermission, target-name=" + "no-server2-identity}]})").build());
    }

    @ParameterizedTest
    @MethodSource("Provider_testServletBasicToEjbAuthenticationContext_1to3")
    public void testServletBasicToEjbAuthenticationContext_1to3(String param1, String param2, String param3, String param4, String param5, String param6) throws Exception {
        assertEquals(param1, param2, Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, param5, param6, ReAuthnType.AC_AUTHENTICATION), param3, param4, SC_OK));
    }

    static public Stream<Arguments> Provider_testServletBasicToEjbAuthenticationContext_1to3() {
        return Stream.of(arguments("Unexpected username returned", "whoami", "servlet", "servlet", "whoami", "whoami"), arguments("Unexpected username returned", "admin", "servlet", "servlet", "admin", "admin"), arguments("Unexpected username returned", "whoami", "admin", "admin", "whoami", "whoami"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testServletBasicToEjbAuthenticationContext_4to5")
    public void testServletBasicToEjbAuthenticationContext_4to5(String param1, String param2, String param3, String param4) throws Exception {
        assertThat(Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, param3, param4, ReAuthnType.AC_AUTHENTICATION), param1, param2, SC_OK), isEjbAuthenticationError());
    }

    static public Stream<Arguments> Provider_testServletBasicToEjbAuthenticationContext_4to5() {
        return Stream.of(arguments("admin", "admin", "xadmin", "admin"), arguments("admin", "admin", "admin", "adminx"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testServletBasicToEjbAuthenticationContext_6to8")
    public void testServletBasicToEjbAuthenticationContext_6to8(String param1, String param2, String param3, String param4, String param5, String param6, String param7) throws Exception {
        assertEquals(param1, param2, Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, param5, param6, param7, ReAuthnType.AC_AUTHORIZATION), param3, param4, SC_OK));
    }

    static public Stream<Arguments> Provider_testServletBasicToEjbAuthenticationContext_6to8() {
        return Stream.of(arguments("Unexpected username returned", "whoami", "servlet", "servlet", "server", "server", "whoami"), arguments("Unexpected username returned", "authz", "servlet", "servlet", "entry", "entry", "authz"), arguments("Unexpected username returned", "authz", "admin", "admin", "entry", "entry", "authz"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testServletBasicToEjbAuthenticationContext_9to10")
    public void testServletBasicToEjbAuthenticationContext_9to10(String param1, String param2, String param3, String param4, String param5) throws Exception {
        assertThat(Utils.makeCallWithBasicAuthn(getEntryServletUrl(WAR_ENTRY_SERVLET_BASIC, param3, param4, param5, ReAuthnType.AC_AUTHORIZATION), param1, param2, SC_OK), isEjbAuthenticationError());
    }

    static public Stream<Arguments> Provider_testServletBasicToEjbAuthenticationContext_9to10() {
        return Stream.of(arguments("admin", "admin", "xentry", "entry", "authz"), arguments("admin", "admin", "entry", "entryx", "authz"));
    }
}
