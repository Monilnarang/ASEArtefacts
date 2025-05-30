package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import com.iluwatar.lockableobject.domain.Elf;
import com.iluwatar.lockableobject.domain.Feind;
import com.iluwatar.lockableobject.domain.Orc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeindTest_Purified {

    private Creature elf;

    private Creature orc;

    private Lockable sword;

    @BeforeEach
    void init() {
        elf = new Elf("Nagdil");
        orc = new Orc("Ghandar");
        sword = new SwordOfAragorn();
    }

    @Test
    void testBaseCase_1_testMerged_1() throws InterruptedException {
        Assertions.assertNull(sword.getLocker());
    }

    @Test
    void testBaseCase_2() throws InterruptedException {
        Assertions.assertEquals(orc, sword.getLocker());
    }

    @Test
    void testBaseCase_3() throws InterruptedException {
        Assertions.assertTrue(sword.isLocked());
    }
}
