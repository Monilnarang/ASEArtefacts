package org.apache.seata.common.util;

import org.apache.seata.common.BranchDO;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.seata.common.DefaultValues;
import org.apache.seata.common.exception.NotSupportYetException;
import org.apache.seata.common.rpc.RpcStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BeanUtilsTest_Purified {

    @Test
    public void testBeanToString_1() {
        BranchDO branchDO = new BranchDO("xid123123", 123L, 1, 2.2, new Date());
        Assertions.assertNotNull(BeanUtils.beanToString(branchDO));
    }

    @Test
    public void testBeanToString_2() {
        Assertions.assertNull(BeanUtils.beanToString(null));
    }

    @Test
    public void testBeanToString_3() {
        Assertions.assertNotNull(BeanUtils.beanToString(new Object()));
    }

    @Test
    public void testBeanToString_4() {
        Assertions.assertNotNull(BeanUtils.beanToString(new BranchDO(null, null, null, null, null)));
    }
}
