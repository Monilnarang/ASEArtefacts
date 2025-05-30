package org.apache.seata.server.session;

import org.apache.seata.core.model.GlobalStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SessionStatusValidatorTest_Purified {

    @Test
    public void testValidateUpdateStatus_1() {
        Assertions.assertTrue(SessionStatusValidator.validateUpdateStatus(GlobalStatus.Begin, GlobalStatus.Committing));
    }

    @Test
    public void testValidateUpdateStatus_2() {
        Assertions.assertTrue(SessionStatusValidator.validateUpdateStatus(GlobalStatus.Committing, GlobalStatus.Committed));
    }

    @Test
    public void testValidateUpdateStatus_3() {
        Assertions.assertFalse(SessionStatusValidator.validateUpdateStatus(GlobalStatus.Committing, GlobalStatus.TimeoutRollbacking));
    }

    @Test
    public void testValidateUpdateStatus_4() {
        Assertions.assertFalse(SessionStatusValidator.validateUpdateStatus(GlobalStatus.TimeoutRollbacking, GlobalStatus.Committing));
    }

    @Test
    public void testValidateUpdateStatus_5() {
        Assertions.assertFalse(SessionStatusValidator.validateUpdateStatus(GlobalStatus.Committing, GlobalStatus.Rollbacking));
    }

    @Test
    public void testValidateUpdateStatus_6() {
        Assertions.assertFalse(SessionStatusValidator.validateUpdateStatus(GlobalStatus.Rollbacking, GlobalStatus.Committing));
    }

    @Test
    public void testValidateUpdateStatus_7() {
        Assertions.assertFalse(SessionStatusValidator.validateUpdateStatus(GlobalStatus.Committed, GlobalStatus.Rollbacked));
    }

    @Test
    public void testValidateUpdateStatus_8() {
        Assertions.assertFalse(SessionStatusValidator.validateUpdateStatus(GlobalStatus.Committed, GlobalStatus.TimeoutRollbacking));
    }
}
