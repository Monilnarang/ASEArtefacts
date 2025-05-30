package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import com.iluwatar.lockableobject.domain.CreatureStats;
import com.iluwatar.lockableobject.domain.CreatureType;
import com.iluwatar.lockableobject.domain.Elf;
import com.iluwatar.lockableobject.domain.Orc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreatureTest_Purified {

    private Creature orc;

    private Creature elf;

    private Lockable sword;

    @BeforeEach
    void init() {
        elf = new Elf("Elf test");
        orc = new Orc("Orc test");
        sword = new SwordOfAragorn();
    }

    void killCreature(Creature source, Creature target) {
        while (target.isAlive()) {
            source.attack(target);
        }
    }

    @Test
    void hitTest_1_testMerged_1() {
        elf.hit(CreatureStats.ELF_HEALTH.getValue() / 2);
        Assertions.assertEquals(CreatureStats.ELF_HEALTH.getValue() / 2, elf.getHealth());
        Assertions.assertFalse(elf.isAlive());
    }

    @Test
    void hitTest_3_testMerged_2() {
        Assertions.assertEquals(0, orc.getInstruments().size());
    }

    @Test
    void hitTest_4() {
        Assertions.assertTrue(orc.acquire(sword));
    }

    @Test
    void hitTest_5() {
        Assertions.assertEquals(1, orc.getInstruments().size());
    }

    @Test
    void testAcqusition_1() throws InterruptedException {
        Assertions.assertTrue(elf.acquire(sword));
    }

    @Test
    void testAcqusition_2() throws InterruptedException {
        Assertions.assertEquals(elf.getName(), sword.getLocker().getName());
    }

    @Test
    void testAcqusition_3() throws InterruptedException {
        Assertions.assertTrue(elf.getInstruments().contains(sword));
    }

    @Test
    void testAcqusition_4() throws InterruptedException {
        Assertions.assertFalse(orc.acquire(sword));
    }

    @Test
    void testAcqusition_5_testMerged_5() throws InterruptedException {
        killCreature(orc, elf);
        Assertions.assertTrue(orc.acquire(sword));
        Assertions.assertEquals(orc, sword.getLocker());
    }
}
