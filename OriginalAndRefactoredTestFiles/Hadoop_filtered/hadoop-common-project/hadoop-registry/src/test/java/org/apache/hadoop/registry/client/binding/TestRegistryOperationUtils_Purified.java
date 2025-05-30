package org.apache.hadoop.registry.client.binding;

import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Assert;
import org.junit.Test;

public class TestRegistryOperationUtils_Purified extends Assert {

    @Test
    public void testShortenUsername_1() throws Throwable {
        assertEquals("hbase", RegistryUtils.convertUsername("hbase@HADOOP.APACHE.ORG"));
    }

    @Test
    public void testShortenUsername_2() throws Throwable {
        assertEquals("hbase", RegistryUtils.convertUsername("hbase/localhost@HADOOP.APACHE.ORG"));
    }

    @Test
    public void testShortenUsername_3() throws Throwable {
        assertEquals("hbase", RegistryUtils.convertUsername("hbase"));
    }

    @Test
    public void testShortenUsername_4() throws Throwable {
        assertEquals("hbase user", RegistryUtils.convertUsername("hbase user"));
    }
}
