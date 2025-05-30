package org.apache.hadoop.oncrpc;

import org.apache.hadoop.oncrpc.RpcReply.ReplyState;
import org.apache.hadoop.oncrpc.security.VerifierNone;
import org.junit.Assert;
import org.junit.Test;

public class TestRpcReply_Purified {

    @Test
    public void testReplyStateFromValue_1() {
        Assert.assertEquals(ReplyState.MSG_ACCEPTED, ReplyState.fromValue(0));
    }

    @Test
    public void testReplyStateFromValue_2() {
        Assert.assertEquals(ReplyState.MSG_DENIED, ReplyState.fromValue(1));
    }
}
