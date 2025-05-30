package org.apache.seata.core.context;

import org.apache.seata.common.exception.ShouldNeverHappenException;
import org.apache.seata.core.model.BranchType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class RootContextTest_Purified {

    private final String DEFAULT_XID = "default_xid";

    private final BranchType DEFAULT_BRANCH_TYPE = BranchType.AT;

    @Test
    public void testBind_And_Unbind_1() {
        assertThat(RootContext.unbind()).isNull();
    }

    @Test
    public void testBind_And_Unbind_2_testMerged_2() {
        RootContext.bind(DEFAULT_XID);
        assertThat(RootContext.unbind()).isEqualTo(DEFAULT_XID);
        RootContext.unbind();
        assertThat(RootContext.getXID()).isNull();
    }

    @Test
    public void testInGlobalTransaction_1() {
        assertThat(RootContext.inGlobalTransaction()).isFalse();
    }

    @Test
    public void testInGlobalTransaction_2_testMerged_2() {
        RootContext.bind(DEFAULT_XID);
        assertThat(RootContext.inGlobalTransaction()).isTrue();
        RootContext.unbind();
        assertThat(RootContext.getXID()).isNull();
    }

    @Test
    public void testInGlobalTransaction_3() {
        assertThat(RootContext.inGlobalTransaction()).isFalse();
    }

    @Test
    public void testAssertNotInGlobalTransaction_1() {
        RootContext.assertNotInGlobalTransaction();
    }

    @Test
    public void testAssertNotInGlobalTransaction_2() {
        assertThat(RootContext.getXID()).isNull();
    }

    @Test
    public void testBindBranchType_And_UnbindBranchType_1() {
        assertThat(RootContext.getBranchType()).isNull();
    }

    @Test
    public void testBindBranchType_And_UnbindBranchType_2() {
        assertThat(RootContext.unbindBranchType()).isNull();
    }

    @Test
    public void testBindBranchType_And_UnbindBranchType_3() {
        RootContext.bindBranchType(DEFAULT_BRANCH_TYPE);
        assertThat(RootContext.unbindBranchType()).isEqualTo(DEFAULT_BRANCH_TYPE);
    }

    @Test
    public void testBindBranchType_And_UnbindBranchType_4() {
        assertThat(RootContext.getBranchType()).isNull();
    }

    @Test
    public void testBindBranchType_And_UnbindBranchType_5() {
        assertThat(RootContext.unbindBranchType()).isNull();
    }
}
