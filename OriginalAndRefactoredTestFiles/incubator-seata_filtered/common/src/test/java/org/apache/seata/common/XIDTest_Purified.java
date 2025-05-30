package org.apache.seata.common;

import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class XIDTest_Purified {

    @Test
    public void testGetTransactionId_1() {
        assertThat(XID.getTransactionId(null)).isEqualTo(-1);
    }

    @Test
    public void testGetTransactionId_2() {
        assertThat(XID.getTransactionId("127.0.0.1:8080:8577662204289747564")).isEqualTo(8577662204289747564L);
    }
}
