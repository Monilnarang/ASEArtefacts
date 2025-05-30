package org.apache.seata.common.metadata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

public class NodeTest_Purified {

    @Test
    public void testNode_1_testMerged_1() {
        Node node = new Node();
        node.setMetadata(new HashMap<>());
        Assertions.assertEquals(new HashMap<>(), node.getMetadata());
        Node.Endpoint endpoint = node.createEndpoint("127.0.0.1", 80, "get");
        node.setControl(endpoint);
        Assertions.assertEquals(endpoint, node.getControl());
        node.setTransaction(endpoint);
        Assertions.assertEquals(endpoint, node.getTransaction());
    }

    @Test
    public void testNode_4_testMerged_2() {
        Node.Endpoint endpoint1 = new Node.Endpoint();
        endpoint1.setHost("127.0.0.1");
        Assertions.assertEquals("127.0.0.1", endpoint1.getHost());
        endpoint1.setPort(80);
        Assertions.assertEquals(80, endpoint1.getPort());
        Assertions.assertEquals("127.0.0.1:80", endpoint1.createAddress());
        Assertions.assertEquals("Endpoint{host='127.0.0.1', port=80}", endpoint1.toString());
    }

    @Test
    public void testNode_8() {
        Assertions.assertEquals("Endpoint{host='127.0.0.1', port=80}", new Node.Endpoint("127.0.0.1", 80).toString());
    }
}
