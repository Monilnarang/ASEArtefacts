package org.apache.hadoop.oncrpc;

import org.apache.hadoop.oncrpc.RpcDeniedReply.RejectState;
import org.apache.hadoop.oncrpc.RpcReply.ReplyState;
import org.apache.hadoop.oncrpc.security.VerifierNone;
import org.junit.Assert;
import org.junit.Test;

public class TestRpcDeniedReply_Purified {

    @Test
    public void testRejectStateFromValue_1() {
        Assert.assertEquals(RejectState.RPC_MISMATCH, RejectState.fromValue(0));
    }

    @Test
    public void testRejectStateFromValue_2() {
        Assert.assertEquals(RejectState.AUTH_ERROR, RejectState.fromValue(1));
    }
}
