package org.apache.seata.spring.boot.autoconfigure.properties.server.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StorePropertiesTest_Purified {

    @Test
    public void testStoreProperties_1_testMerged_1() {
        StoreProperties storeProperties = new StoreProperties();
        storeProperties.setMode("mode");
        storeProperties.setPublicKey("public");
        Assertions.assertEquals("mode", storeProperties.getMode());
        Assertions.assertEquals("public", storeProperties.getPublicKey());
    }

    @Test
    public void testStoreProperties_3() {
        StoreProperties.Session session = new StoreProperties.Session();
        session.setMode("mode");
        Assertions.assertEquals("mode", session.getMode());
    }

    @Test
    public void testStoreProperties_4() {
        StoreProperties.Lock lock = new StoreProperties.Lock();
        lock.setMode("mode");
        Assertions.assertEquals("mode", lock.getMode());
    }
}
