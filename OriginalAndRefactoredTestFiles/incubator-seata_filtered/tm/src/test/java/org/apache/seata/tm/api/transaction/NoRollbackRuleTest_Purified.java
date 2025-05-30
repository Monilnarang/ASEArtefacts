package org.apache.seata.tm.api.transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NoRollbackRuleTest_Purified {

    @Test
    public void equalsTest_1() {
        RollbackRule rollbackRuleByClass = new NoRollbackRule(Exception.class);
        RollbackRule otherRollbackRuleByClass = new NoRollbackRule(Exception.class);
        Assertions.assertEquals(rollbackRuleByClass, otherRollbackRuleByClass);
    }

    @Test
    public void equalsTest_2() {
        RollbackRule rollbackRuleByName = new NoRollbackRule(Exception.class.getName());
        RollbackRule otherRollbackRuleByName = new NoRollbackRule(Exception.class.getName());
        Assertions.assertEquals(rollbackRuleByName, otherRollbackRuleByName);
    }

    @Test
    public void equalsTest_3() {
        NoRollbackRule otherRollbackRuleByName3 = new NoRollbackRule(Exception.class.getName());
        Assertions.assertEquals(otherRollbackRuleByName3.toString(), "NoRollbackRule with pattern [" + Exception.class.getName() + "]");
    }
}
