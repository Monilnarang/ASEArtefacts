package com.iluwatar.compositeentity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PersistenceTest_Purified {

    static final ConsoleCoarseGrainedObject console = new ConsoleCoarseGrainedObject();

    @Test
    void coarseGrainedObjectChangedForPersistenceTest_1() {
        console.init();
        assertNull(console.dependentObjects[0].getData());
    }

    @Test
    void coarseGrainedObjectChangedForPersistenceTest_2() {
        MessageDependentObject dependentObject = new MessageDependentObject();
        String message = "Danger";
        console.setData(message);
        assertEquals(message, dependentObject.getData());
    }
}
