package org.apache.rocketmq.common.action;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ActionTest_Purified {

    @Test
    public void getCode_ReturnsCorrectCode_1() {
        assertEquals((byte) 1, Action.ALL.getCode());
    }

    @Test
    public void getCode_ReturnsCorrectCode_2() {
        assertEquals((byte) 2, Action.ANY.getCode());
    }

    @Test
    public void getCode_ReturnsCorrectCode_3() {
        assertEquals((byte) 3, Action.PUB.getCode());
    }

    @Test
    public void getCode_ReturnsCorrectCode_4() {
        assertEquals((byte) 4, Action.SUB.getCode());
    }

    @Test
    public void getCode_ReturnsCorrectCode_5() {
        assertEquals((byte) 5, Action.CREATE.getCode());
    }

    @Test
    public void getCode_ReturnsCorrectCode_6() {
        assertEquals((byte) 6, Action.UPDATE.getCode());
    }

    @Test
    public void getCode_ReturnsCorrectCode_7() {
        assertEquals((byte) 7, Action.DELETE.getCode());
    }

    @Test
    public void getCode_ReturnsCorrectCode_8() {
        assertEquals((byte) 8, Action.GET.getCode());
    }

    @Test
    public void getCode_ReturnsCorrectCode_9() {
        assertEquals((byte) 9, Action.LIST.getCode());
    }

    @Test
    public void getName_ReturnsCorrectName_1() {
        assertEquals("All", Action.ALL.getName());
    }

    @Test
    public void getName_ReturnsCorrectName_2() {
        assertEquals("Any", Action.ANY.getName());
    }

    @Test
    public void getName_ReturnsCorrectName_3() {
        assertEquals("Pub", Action.PUB.getName());
    }

    @Test
    public void getName_ReturnsCorrectName_4() {
        assertEquals("Sub", Action.SUB.getName());
    }

    @Test
    public void getName_ReturnsCorrectName_5() {
        assertEquals("Create", Action.CREATE.getName());
    }

    @Test
    public void getName_ReturnsCorrectName_6() {
        assertEquals("Update", Action.UPDATE.getName());
    }

    @Test
    public void getName_ReturnsCorrectName_7() {
        assertEquals("Delete", Action.DELETE.getName());
    }

    @Test
    public void getName_ReturnsCorrectName_8() {
        assertEquals("Get", Action.GET.getName());
    }

    @Test
    public void getName_ReturnsCorrectName_9() {
        assertEquals("List", Action.LIST.getName());
    }
}
