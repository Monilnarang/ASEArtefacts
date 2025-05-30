package org.apache.hadoop.registry.client.impl;

import org.apache.curator.framework.api.CuratorEvent;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.apache.hadoop.fs.PathIsNotEmptyDirectoryException;
import org.apache.hadoop.fs.PathNotFoundException;
import org.apache.hadoop.service.ServiceOperations;
import org.apache.hadoop.registry.AbstractZKRegistryTest;
import org.apache.hadoop.registry.client.impl.zk.CuratorService;
import org.apache.hadoop.registry.client.impl.zk.RegistrySecurity;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.ACL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

public class TestCuratorService_Purified extends AbstractZKRegistryTest {

    private static final Logger LOG = LoggerFactory.getLogger(TestCuratorService.class);

    protected CuratorService curatorService;

    public static final String MISSING = "/missing";

    private List<ACL> rootACL;

    @Before
    public void startCurator() throws IOException {
        createCuratorService();
    }

    @After
    public void stopCurator() {
        ServiceOperations.stop(curatorService);
    }

    protected void createCuratorService() throws IOException {
        curatorService = new CuratorService("curatorService");
        curatorService.init(createRegistryConfiguration());
        curatorService.start();
        rootACL = RegistrySecurity.WorldReadWriteACL;
        curatorService.maybeCreate("", CreateMode.PERSISTENT, rootACL, true);
    }

    private void mkPath(String path, CreateMode mode) throws IOException {
        curatorService.zkMkPath(path, mode, false, RegistrySecurity.WorldReadWriteACL);
    }

    public void pathMustExist(String path) throws IOException {
        curatorService.zkPathMustExist(path);
    }

    protected byte[] getTestBuffer() {
        byte[] buffer = new byte[1];
        buffer[0] = '0';
        return buffer;
    }

    public void verifyNotExists(String path) throws IOException {
        if (curatorService.zkPathExists(path)) {
            fail("Path should not exist: " + path);
        }
    }

    @Test
    public void testMaybeCreate_1() throws Throwable {
        assertTrue(curatorService.maybeCreate("/p3", CreateMode.PERSISTENT, RegistrySecurity.WorldReadWriteACL, false));
    }

    @Test
    public void testMaybeCreate_2() throws Throwable {
        assertFalse(curatorService.maybeCreate("/p3", CreateMode.PERSISTENT, RegistrySecurity.WorldReadWriteACL, false));
    }
}
