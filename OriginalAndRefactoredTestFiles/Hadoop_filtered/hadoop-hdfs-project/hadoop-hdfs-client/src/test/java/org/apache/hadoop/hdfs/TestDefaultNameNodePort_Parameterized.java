package org.apache.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.client.HdfsClientConfigKeys;
import org.junit.Test;
import java.net.InetSocketAddress;
import java.net.URI;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestDefaultNameNodePort_Parameterized {

    @Test
    public void testGetUri_1() {
        assertEquals(URI.create("hdfs://foo:555"), DFSUtilClient.getNNUri(new InetSocketAddress("foo", 555)));
    }

    @Test
    public void testGetUri_2() {
        assertEquals(URI.create("hdfs://foo"), DFSUtilClient.getNNUri(new InetSocketAddress("foo", HdfsClientConfigKeys.DFS_NAMENODE_RPC_PORT_DEFAULT)));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetAddressFromString_1to2")
    public void testGetAddressFromString_1to2(String param1) throws Exception {
        assertEquals(HdfsClientConfigKeys.DFS_NAMENODE_RPC_PORT_DEFAULT, DFSUtilClient.getNNAddress(param1).getPort());
    }

    static public Stream<Arguments> Provider_testGetAddressFromString_1to2() {
        return Stream.of(arguments("foo"), arguments("hdfs://foo/"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetAddressFromString_3to4")
    public void testGetAddressFromString_3to4(int param1, String param2) throws Exception {
        assertEquals(param1, DFSUtilClient.getNNAddress(param2).getPort());
    }

    static public Stream<Arguments> Provider_testGetAddressFromString_3to4() {
        return Stream.of(arguments(555, "hdfs://foo:555"), arguments(555, "foo:555"));
    }
}
