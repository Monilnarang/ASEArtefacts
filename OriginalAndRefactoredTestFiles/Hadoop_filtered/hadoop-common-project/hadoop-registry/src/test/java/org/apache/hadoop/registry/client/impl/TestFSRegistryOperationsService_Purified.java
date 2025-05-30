package org.apache.hadoop.registry.client.impl;

import org.junit.Test;
import java.io.IOException;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathIsNotEmptyDirectoryException;
import org.apache.hadoop.fs.PathNotFoundException;
import org.apache.hadoop.registry.client.exceptions.InvalidPathnameException;
import org.apache.hadoop.registry.client.types.ServiceRecord;
import org.apache.hadoop.registry.client.types.yarn.YarnRegistryAttributes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

public class TestFSRegistryOperationsService_Purified {

    private static FSRegistryOperationsService registry = new FSRegistryOperationsService();

    private static FileSystem fs;

    @BeforeClass
    public static void initRegistry() throws IOException {
        Assert.assertNotNull(registry);
        registry.init(new Configuration());
        fs = registry.getFs();
        fs.delete(new Path("test"), true);
    }

    @Before
    public void createTestDir() throws IOException {
        fs.mkdirs(new Path("test"));
    }

    @After
    public void cleanTestDir() throws IOException {
        fs.delete(new Path("test"), true);
    }

    private ServiceRecord createRecord(String id) {
        System.out.println("Creating mock service record");
        ServiceRecord record = new ServiceRecord();
        record.set(YarnRegistryAttributes.YARN_ID, id);
        record.description = "testRecord";
        return record;
    }

    @Test
    public void testMkNodeRecursive_1_testMerged_1() throws IOException {
        boolean result = false;
        result = registry.mknode("test/registryTestNode", true);
        Assert.assertTrue(result);
    }

    @Test
    public void testMkNodeRecursive_2() throws IOException {
        Assert.assertTrue(fs.exists(new Path("test/registryTestNode")));
    }

    @Test
    public void testMkNodeRecursive_4() throws IOException {
        Assert.assertTrue(fs.exists(new Path("test/parent/registryTestNode")));
    }

    @Test
    public void testMkNodeAlreadyExists_1() throws IOException {
        Assert.assertFalse(registry.mknode("test/registryTestNode", true));
    }

    @Test
    public void testMkNodeAlreadyExists_2() throws IOException {
        Assert.assertFalse(registry.mknode("test/registryTestNode", false));
    }
}
