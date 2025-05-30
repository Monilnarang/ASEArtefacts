package org.apache.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.client.HdfsClientConfigKeys;
import org.junit.Test;
import java.net.InetSocketAddress;
import java.net.URI;
import static org.junit.Assert.assertEquals;

public class TestDefaultNameNodePort_Purified {

    @Test
    public void testGetAddressFromString_1() throws Exception {
        assertEquals(HdfsClientConfigKeys.DFS_NAMENODE_RPC_PORT_DEFAULT, DFSUtilClient.getNNAddress("foo").getPort());
    }

    @Test
    public void testGetAddressFromString_2() throws Exception {
        assertEquals(HdfsClientConfigKeys.DFS_NAMENODE_RPC_PORT_DEFAULT, DFSUtilClient.getNNAddress("hdfs://foo/").getPort());
    }

    @Test
    public void testGetAddressFromString_3() throws Exception {
        assertEquals(555, DFSUtilClient.getNNAddress("hdfs://foo:555").getPort());
    }

    @Test
    public void testGetAddressFromString_4() throws Exception {
        assertEquals(555, DFSUtilClient.getNNAddress("foo:555").getPort());
    }

    @Test
    public void testGetUri_1() {
        assertEquals(URI.create("hdfs://foo:555"), DFSUtilClient.getNNUri(new InetSocketAddress("foo", 555)));
    }

    @Test
    public void testGetUri_2() {
        assertEquals(URI.create("hdfs://foo"), DFSUtilClient.getNNUri(new InetSocketAddress("foo", HdfsClientConfigKeys.DFS_NAMENODE_RPC_PORT_DEFAULT)));
    }
}
