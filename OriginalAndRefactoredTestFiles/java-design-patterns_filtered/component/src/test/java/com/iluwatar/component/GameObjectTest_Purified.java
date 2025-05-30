package com.iluwatar.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.event.KeyEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class GameObjectTest_Purified {

    GameObject playerTest;

    GameObject npcTest;

    @BeforeEach
    public void initEach() {
        playerTest = GameObject.createPlayer();
        npcTest = GameObject.createNpc();
    }

    @Test
    void objectTest_1() {
        assertEquals("player", playerTest.getName());
    }

    @Test
    void objectTest_2() {
        assertEquals("npc", npcTest.getName());
    }
}
