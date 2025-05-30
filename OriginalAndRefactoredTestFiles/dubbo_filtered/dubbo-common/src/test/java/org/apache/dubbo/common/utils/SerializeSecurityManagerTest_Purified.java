package org.apache.dubbo.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SerializeSecurityManagerTest_Purified {

    @Test
    void testStatus1_1_testMerged_1() {
        SerializeSecurityManager ssm = new SerializeSecurityManager();
        ssm.registerListener(new TestAllowClassNotifyListener());
        Assertions.assertEquals(AllowClassNotifyListener.DEFAULT_STATUS, ssm.getCheckStatus());
        ssm.setCheckStatus(SerializeCheckStatus.STRICT);
        Assertions.assertEquals(ssm.getCheckStatus(), TestAllowClassNotifyListener.getStatus());
        Assertions.assertEquals(SerializeCheckStatus.STRICT, TestAllowClassNotifyListener.getStatus());
        ssm.setCheckStatus(SerializeCheckStatus.WARN);
        Assertions.assertEquals(SerializeCheckStatus.WARN, TestAllowClassNotifyListener.getStatus());
        ssm.setCheckStatus(SerializeCheckStatus.DISABLE);
        Assertions.assertEquals(SerializeCheckStatus.DISABLE, TestAllowClassNotifyListener.getStatus());
    }

    @Test
    void testStatus1_2() {
        Assertions.assertEquals(AllowClassNotifyListener.DEFAULT_STATUS, TestAllowClassNotifyListener.getStatus());
    }

    @Test
    void testSerializable_1_testMerged_1() {
        SerializeSecurityManager ssm = new SerializeSecurityManager();
        ssm.registerListener(new TestAllowClassNotifyListener());
        Assertions.assertTrue(ssm.isCheckSerializable());
        ssm.setCheckSerializable(true);
        ssm.setCheckSerializable(false);
        Assertions.assertFalse(ssm.isCheckSerializable());
    }

    @Test
    void testSerializable_2_testMerged_2() {
        Assertions.assertTrue(TestAllowClassNotifyListener.isCheckSerializable());
    }

    @Test
    void testSerializable_6_testMerged_3() {
        Assertions.assertFalse(TestAllowClassNotifyListener.isCheckSerializable());
    }
}
