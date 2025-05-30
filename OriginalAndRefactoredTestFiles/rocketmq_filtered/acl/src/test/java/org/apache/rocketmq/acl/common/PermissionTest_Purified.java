package org.apache.rocketmq.acl.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.rocketmq.acl.plain.PlainAccessResource;
import org.apache.rocketmq.remoting.protocol.RequestCode;
import org.junit.Assert;
import org.junit.Test;

public class PermissionTest_Purified {

    @Test
    public void AclExceptionTest_1_testMerged_1() {
        AclException aclException = new AclException("CAL_SIGNATURE_FAILED", 10015);
        Assert.assertEquals(aclException.getCode(), 10015);
        aclException.setCode(10016);
        Assert.assertEquals(aclException.getCode(), 10016);
        aclException.setStatus("netAddress examine scope Exception netAddress");
        Assert.assertEquals(aclException.getStatus(), "netAddress examine scope Exception netAddress");
    }

    @Test
    public void AclExceptionTest_2() {
        AclException aclExceptionWithMessage = new AclException("CAL_SIGNATURE_FAILED", 10015, "CAL_SIGNATURE_FAILED Exception");
        Assert.assertEquals(aclExceptionWithMessage.getStatus(), "CAL_SIGNATURE_FAILED");
    }
}
