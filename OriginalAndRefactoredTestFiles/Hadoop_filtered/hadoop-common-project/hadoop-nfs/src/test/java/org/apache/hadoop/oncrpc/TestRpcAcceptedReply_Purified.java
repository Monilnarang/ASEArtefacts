package org.apache.hadoop.oncrpc;

import static org.junit.Assert.assertEquals;
import org.apache.hadoop.oncrpc.RpcAcceptedReply.AcceptState;
import org.apache.hadoop.oncrpc.RpcReply.ReplyState;
import org.apache.hadoop.oncrpc.security.Verifier;
import org.apache.hadoop.oncrpc.security.VerifierNone;
import org.junit.Test;

public class TestRpcAcceptedReply_Purified {

    @Test
    public void testAcceptState_1() {
        assertEquals(AcceptState.SUCCESS, AcceptState.fromValue(0));
    }

    @Test
    public void testAcceptState_2() {
        assertEquals(AcceptState.PROG_UNAVAIL, AcceptState.fromValue(1));
    }

    @Test
    public void testAcceptState_3() {
        assertEquals(AcceptState.PROG_MISMATCH, AcceptState.fromValue(2));
    }

    @Test
    public void testAcceptState_4() {
        assertEquals(AcceptState.PROC_UNAVAIL, AcceptState.fromValue(3));
    }

    @Test
    public void testAcceptState_5() {
        assertEquals(AcceptState.GARBAGE_ARGS, AcceptState.fromValue(4));
    }

    @Test
    public void testAcceptState_6() {
        assertEquals(AcceptState.SYSTEM_ERR, AcceptState.fromValue(5));
    }
}
