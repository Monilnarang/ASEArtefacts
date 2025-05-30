package org.apache.seata.core.serializer;

import org.apache.seata.core.protocol.HeartbeatMessage;
import org.apache.seata.core.protocol.Version;
import org.apache.seata.core.protocol.transaction.AbstractBranchEndRequest;
import org.apache.seata.core.protocol.transaction.BranchCommitRequest;
import org.apache.seata.core.protocol.transaction.BranchCommitResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SerializerSecurityRegistryTest_Purified {

    @Test
    public void getAllowClassType_1() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassType().contains(Long.class));
    }

    @Test
    public void getAllowClassType_2() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassType().contains(Integer.class));
    }

    @Test
    public void getAllowClassType_3() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassType().contains(HeartbeatMessage.class));
    }

    @Test
    public void getAllowClassType_4() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassType().contains(BranchCommitRequest.class));
    }

    @Test
    public void getAllowClassType_5() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassType().contains(BranchCommitResponse.class));
    }

    @Test
    public void getAllowClassType_6() {
        Assertions.assertFalse(SerializerSecurityRegistry.getAllowClassType().contains(AbstractBranchEndRequest.class));
    }

    @Test
    public void getAllowClassType_7() {
        Assertions.assertFalse(SerializerSecurityRegistry.getAllowClassType().contains(Version.class));
    }

    @Test
    public void getAllowClassPattern_1() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassPattern().contains(Long.class.getCanonicalName()));
    }

    @Test
    public void getAllowClassPattern_2() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassPattern().contains(Integer.class.getCanonicalName()));
    }

    @Test
    public void getAllowClassPattern_3() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassPattern().contains(HeartbeatMessage.class.getCanonicalName()));
    }

    @Test
    public void getAllowClassPattern_4() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassPattern().contains(BranchCommitRequest.class.getCanonicalName()));
    }

    @Test
    public void getAllowClassPattern_5() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassPattern().contains(BranchCommitResponse.class.getCanonicalName()));
    }

    @Test
    public void getAllowClassPattern_6() {
        Assertions.assertFalse(SerializerSecurityRegistry.getAllowClassPattern().contains(AbstractBranchEndRequest.class.getCanonicalName()));
    }

    @Test
    public void getAllowClassPattern_7() {
        Assertions.assertFalse(SerializerSecurityRegistry.getAllowClassPattern().contains(Version.class.getCanonicalName()));
    }

    @Test
    public void getAllowClassPattern_8() {
        Assertions.assertTrue(SerializerSecurityRegistry.getAllowClassPattern().contains("org.apache.seata.*"));
    }
}
