package org.apache.seata.common.holder;

import org.apache.seata.common.exception.ShouldNeverHappenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectHolderTest_Purified {

    @BeforeEach
    void setUp() {
        ObjectHolder.INSTANCE.setObject("objectHolderTest", this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testGetObjectByName_1() {
        Assertions.assertNotNull(ObjectHolder.INSTANCE.getObject("objectHolderTest"));
    }

    @Test
    public void testGetObjectByName_2() {
        Assertions.assertNull(ObjectHolder.INSTANCE.getObject("objectHolder"));
    }
}
