package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.CreatureStats;
import com.iluwatar.lockableobject.domain.Elf;
import com.iluwatar.lockableobject.domain.Human;
import com.iluwatar.lockableobject.domain.Orc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubCreaturesTests_Purified {

    @Test
    void statsTest_1_testMerged_1() {
        var elf = new Elf("Limbar");
        Assertions.assertEquals(CreatureStats.ELF_HEALTH.getValue(), elf.getHealth());
        Assertions.assertEquals(CreatureStats.ELF_DAMAGE.getValue(), elf.getDamage());
    }

    @Test
    void statsTest_3_testMerged_2() {
        var orc = new Orc("Dargal");
        Assertions.assertEquals(CreatureStats.ORC_DAMAGE.getValue(), orc.getDamage());
        Assertions.assertEquals(CreatureStats.ORC_HEALTH.getValue(), orc.getHealth());
    }

    @Test
    void statsTest_5_testMerged_3() {
        var human = new Human("Jerry");
        Assertions.assertEquals(CreatureStats.HUMAN_DAMAGE.getValue(), human.getDamage());
        Assertions.assertEquals(CreatureStats.HUMAN_HEALTH.getValue(), human.getHealth());
    }
}
