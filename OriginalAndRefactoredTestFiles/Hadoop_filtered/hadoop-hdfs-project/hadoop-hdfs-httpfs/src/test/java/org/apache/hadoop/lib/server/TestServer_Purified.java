package org.apache.hadoop.lib.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.lib.lang.XException;
import org.apache.hadoop.test.HTestCase;
import org.apache.hadoop.test.TestDir;
import org.apache.hadoop.test.TestDirHelper;
import org.apache.hadoop.test.TestException;
import org.apache.hadoop.util.StringUtils;
import org.junit.Test;

public class TestServer_Purified extends HTestCase {

    private Server createServer(Configuration conf) {
        return new Server("server", TestDirHelper.getTestDir().getAbsolutePath(), TestDirHelper.getTestDir().getAbsolutePath(), TestDirHelper.getTestDir().getAbsolutePath(), TestDirHelper.getTestDir().getAbsolutePath(), conf);
    }

    public static class LifeCycleService extends BaseService {

        public LifeCycleService() {
            super("lifecycle");
        }

        @Override
        protected void init() throws ServiceException {
            assertEquals(getServer().getStatus(), Server.Status.BOOTING);
        }

        @Override
        public void destroy() {
            assertEquals(getServer().getStatus(), Server.Status.SHUTTING_DOWN);
            super.destroy();
        }

        @Override
        public Class getInterface() {
            return LifeCycleService.class;
        }
    }

    public static class TestService implements Service {

        static List<String> LIFECYCLE = new ArrayList<String>();

        @Override
        public void init(Server server) throws ServiceException {
            LIFECYCLE.add("init");
        }

        @Override
        public void postInit() throws ServiceException {
            LIFECYCLE.add("postInit");
        }

        @Override
        public void destroy() {
            LIFECYCLE.add("destroy");
        }

        @Override
        public Class[] getServiceDependencies() {
            return new Class[0];
        }

        @Override
        public Class getInterface() {
            return TestService.class;
        }

        @Override
        public void serverStatusChange(Server.Status oldStatus, Server.Status newStatus) throws ServiceException {
            LIFECYCLE.add("serverStatusChange");
        }
    }

    public static class TestServiceExceptionOnStatusChange extends TestService {

        @Override
        public void serverStatusChange(Server.Status oldStatus, Server.Status newStatus) throws ServiceException {
            throw new RuntimeException();
        }
    }

    private static List<String> ORDER = new ArrayList<String>();

    public abstract static class MyService implements Service, XException.ERROR {

        private String id;

        private Class serviceInterface;

        private Class[] dependencies;

        private boolean failOnInit;

        private boolean failOnDestroy;

        protected MyService(String id, Class serviceInterface, Class[] dependencies, boolean failOnInit, boolean failOnDestroy) {
            this.id = id;
            this.serviceInterface = serviceInterface;
            this.dependencies = dependencies;
            this.failOnInit = failOnInit;
            this.failOnDestroy = failOnDestroy;
        }

        @Override
        public void init(Server server) throws ServiceException {
            ORDER.add(id + ".init");
            if (failOnInit) {
                throw new ServiceException(this);
            }
        }

        @Override
        public void postInit() throws ServiceException {
            ORDER.add(id + ".postInit");
        }

        @Override
        public String getTemplate() {
            return "";
        }

        @Override
        public void destroy() {
            ORDER.add(id + ".destroy");
            if (failOnDestroy) {
                throw new RuntimeException();
            }
        }

        @Override
        public Class[] getServiceDependencies() {
            return dependencies;
        }

        @Override
        public Class getInterface() {
            return serviceInterface;
        }

        @Override
        public void serverStatusChange(Server.Status oldStatus, Server.Status newStatus) throws ServiceException {
        }
    }

    public static class MyService1 extends MyService {

        public MyService1() {
            super("s1", MyService1.class, null, false, false);
        }

        protected MyService1(String id, Class serviceInterface, Class[] dependencies, boolean failOnInit, boolean failOnDestroy) {
            super(id, serviceInterface, dependencies, failOnInit, failOnDestroy);
        }
    }

    public static class MyService2 extends MyService {

        public MyService2() {
            super("s2", MyService2.class, null, true, false);
        }
    }

    public static class MyService3 extends MyService {

        public MyService3() {
            super("s3", MyService3.class, null, false, false);
        }
    }

    public static class MyService1a extends MyService1 {

        public MyService1a() {
            super("s1a", MyService1.class, null, false, false);
        }
    }

    public static class MyService4 extends MyService1 {

        public MyService4() {
            super("s4a", String.class, null, false, false);
        }
    }

    public static class MyService5 extends MyService {

        public MyService5() {
            super("s5", MyService5.class, null, false, true);
        }

        protected MyService5(String id, Class serviceInterface, Class[] dependencies, boolean failOnInit, boolean failOnDestroy) {
            super(id, serviceInterface, dependencies, failOnInit, failOnDestroy);
        }
    }

    public static class MyService5a extends MyService5 {

        public MyService5a() {
            super("s5a", MyService5.class, null, false, false);
        }
    }

    public static class MyService6 extends MyService {

        public MyService6() {
            super("s6", MyService6.class, new Class[] { MyService1.class }, false, false);
        }
    }

    public static class MyService7 extends MyService {

        @SuppressWarnings({ "UnusedParameters" })
        public MyService7(String foo) {
            super("s6", MyService7.class, new Class[] { MyService1.class }, false, false);
        }
    }

    private static String getAbsolutePath(String relativePath) {
        return new File(TestDirHelper.getTestDir(), relativePath).getAbsolutePath();
    }

    @Test
    @TestDir
    public void serviceLifeCycle_1() throws Exception {
        Configuration conf = new Configuration(false);
        conf.set("server.services", TestService.class.getName());
        Server server = createServer(conf);
        server.init();
        assertNotNull(server.get(TestService.class));
    }

    @Test
    @TestDir
    public void serviceLifeCycle_2() throws Exception {
        TestService.LIFECYCLE.clear();
        assertEquals(TestService.LIFECYCLE, Arrays.asList("init", "postInit", "serverStatusChange", "destroy"));
    }
}
