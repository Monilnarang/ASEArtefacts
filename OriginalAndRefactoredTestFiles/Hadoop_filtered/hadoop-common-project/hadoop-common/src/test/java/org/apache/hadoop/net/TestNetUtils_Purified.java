package org.apache.hadoop.net;

import static org.junit.Assert.*;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.CharacterCodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.KerberosAuthException;
import org.apache.hadoop.security.NetUtilsTestResolver;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.LambdaTestUtils;
import org.apache.hadoop.util.Shell;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestNetUtils_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestNetUtils.class);

    private static final int DEST_PORT = 4040;

    private static final String DEST_PORT_NAME = Integer.toString(DEST_PORT);

    private static final int LOCAL_PORT = 8080;

    private static final String LOCAL_PORT_NAME = Integer.toString(LOCAL_PORT);

    static final long TIME_FUDGE_MILLIS = 200;

    private void doSocketReadTimeoutTest(boolean withChannel) throws IOException {
        ServerSocket ss = new ServerSocket(0);
        Socket s;
        if (withChannel) {
            s = NetUtils.getDefaultSocketFactory(new Configuration()).createSocket();
            Assume.assumeNotNull(s.getChannel());
        } else {
            s = new Socket();
            assertNull(s.getChannel());
        }
        SocketInputWrapper stm = null;
        try {
            NetUtils.connect(s, ss.getLocalSocketAddress(), 1000);
            stm = NetUtils.getInputStream(s, 1000);
            assertReadTimeout(stm, 1000);
            stm.setTimeout(1);
            assertReadTimeout(stm, 1);
            s.setSoTimeout(1000);
            if (withChannel) {
                assertReadTimeout(stm, 1);
            } else {
                assertReadTimeout(stm, 1000);
            }
        } finally {
            IOUtils.closeStream(stm);
            IOUtils.closeSocket(s);
            ss.close();
        }
    }

    private void assertReadTimeout(SocketInputWrapper stm, int timeoutMillis) throws IOException {
        long st = System.nanoTime();
        try {
            stm.read();
            fail("Didn't time out");
        } catch (SocketTimeoutException ste) {
            assertTimeSince(st, timeoutMillis);
        }
    }

    private void assertTimeSince(long startNanos, int expectedMillis) {
        long durationNano = System.nanoTime() - startNanos;
        long millis = TimeUnit.MILLISECONDS.convert(durationNano, TimeUnit.NANOSECONDS);
        assertTrue("Expected " + expectedMillis + "ms, but took " + millis, Math.abs(millis - expectedMillis) < TIME_FUDGE_MILLIS);
    }

    private void assertRemoteDetailsIncluded(IOException wrapped) throws Throwable {
        assertInException(wrapped, "desthost");
        assertInException(wrapped, DEST_PORT_NAME);
    }

    private void assertLocalDetailsIncluded(IOException wrapped) throws Throwable {
        assertInException(wrapped, "localhost");
        assertInException(wrapped, LOCAL_PORT_NAME);
    }

    private void assertWikified(Exception e) throws Throwable {
        assertInException(e, NetUtils.HADOOP_WIKI);
    }

    private void assertInException(Exception e, String text) throws Throwable {
        String message = extractExceptionMessage(e);
        if (!(message.contains(text))) {
            throw new AssertionError("Wrong text in message " + "\"" + message + "\"" + " expected \"" + text + "\"").initCause(e);
        }
    }

    private String extractExceptionMessage(Exception e) throws Throwable {
        assertNotNull("Null Exception", e);
        String message = e.getMessage();
        if (message == null) {
            throw new AssertionError("Empty text in exception " + e).initCause(e);
        }
        return message;
    }

    private void assertNotInException(Exception e, String text) throws Throwable {
        String message = extractExceptionMessage(e);
        if (message.contains(text)) {
            throw new AssertionError("Wrong text in message " + "\"" + message + "\"" + " did not expect \"" + text + "\"").initCause(e);
        }
    }

    private IOException verifyExceptionClass(IOException e, Class expectedClass) throws Throwable {
        assertNotNull("Null Exception", e);
        IOException wrapped = NetUtils.wrapException("desthost", DEST_PORT, "localhost", LOCAL_PORT, e);
        LOG.info(wrapped.toString(), wrapped);
        if (!(wrapped.getClass().equals(expectedClass))) {
            throw new AssertionError("Wrong exception class; expected " + expectedClass + " got " + wrapped.getClass() + ": " + wrapped).initCause(wrapped);
        }
        return wrapped;
    }

    static NetUtilsTestResolver resolver;

    static Configuration config;

    @BeforeClass
    public static void setupResolver() {
        resolver = NetUtilsTestResolver.install();
    }

    @Before
    public void resetResolver() {
        resolver.reset();
        config = new Configuration();
    }

    private void verifyGetByExactNameSearch(String host, String... searches) {
        assertNull(resolver.getByExactName(host));
        assertBetterArrayEquals(searches, resolver.getHostSearches());
    }

    private void verifyGetByNameWithSearch(String host, String... searches) {
        assertNull(resolver.getByNameWithSearch(host));
        assertBetterArrayEquals(searches, resolver.getHostSearches());
    }

    private void verifyGetByName(String host, String... searches) {
        InetAddress addr = null;
        try {
            addr = resolver.getByName(host);
        } catch (UnknownHostException e) {
        }
        assertNull(addr);
        assertBetterArrayEquals(searches, resolver.getHostSearches());
    }

    private InetAddress verifyResolve(String host, String... searches) {
        InetAddress addr = null;
        try {
            addr = resolver.getByName(host);
        } catch (UnknownHostException e) {
        }
        assertNotNull(addr);
        assertBetterArrayEquals(searches, resolver.getHostSearches());
        return addr;
    }

    private void verifyInetAddress(InetAddress addr, String host, String ip) {
        assertNotNull(addr);
        assertEquals(host, addr.getHostName());
        assertEquals(ip, addr.getHostAddress());
    }

    private <T> void assertBetterArrayEquals(T[] expect, T[] got) {
        String expectStr = StringUtils.join(expect, ", ");
        String gotStr = StringUtils.join(got, ", ");
        assertEquals(expectStr, gotStr);
    }

    @Test
    public void testGetLocalInetAddress_1() throws Exception {
        assertNotNull(NetUtils.getLocalInetAddress("127.0.0.1"));
    }

    @Test
    public void testGetLocalInetAddress_2() throws Exception {
        assertNull(NetUtils.getLocalInetAddress("invalid-address-for-test"));
    }

    @Test
    public void testGetLocalInetAddress_3() throws Exception {
        assertNull(NetUtils.getLocalInetAddress(null));
    }

    @Test
    public void testGetHostNameOfIP_1() {
        assertNull(NetUtils.getHostNameOfIP(null));
    }

    @Test
    public void testGetHostNameOfIP_2() {
        assertNull(NetUtils.getHostNameOfIP(""));
    }

    @Test
    public void testGetHostNameOfIP_3() {
        assertNull(NetUtils.getHostNameOfIP("crazytown"));
    }

    @Test
    public void testGetHostNameOfIP_4() {
        assertNull(NetUtils.getHostNameOfIP("127.0.0.1:"));
    }

    @Test
    public void testGetHostNameOfIP_5() {
        assertNull(NetUtils.getHostNameOfIP("127.0.0.1:-1"));
    }

    @Test
    public void testGetHostNameOfIP_6() {
        assertNull(NetUtils.getHostNameOfIP("127.0.0.1:A"));
    }

    @Test
    public void testGetHostNameOfIP_7() {
        assertNotNull(NetUtils.getHostNameOfIP("127.0.0.1"));
    }

    @Test
    public void testGetHostNameOfIP_8() {
        assertNotNull(NetUtils.getHostNameOfIP("127.0.0.1:1"));
    }

    @Test
    public void testBindToLocalAddress_1() throws Exception {
        assertNotNull(NetUtils.bindToLocalAddress(NetUtils.getLocalInetAddress("127.0.0.1"), false));
    }

    @Test
    public void testBindToLocalAddress_2() throws Exception {
        assertNull(NetUtils.bindToLocalAddress(NetUtils.getLocalInetAddress("127.0.0.1"), true));
    }
}
