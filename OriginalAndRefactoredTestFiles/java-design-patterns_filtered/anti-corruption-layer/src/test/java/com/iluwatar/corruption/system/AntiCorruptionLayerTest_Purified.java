package com.iluwatar.corruption.system;

import static org.junit.jupiter.api.Assertions.*;
import com.iluwatar.corruption.system.legacy.LegacyOrder;
import com.iluwatar.corruption.system.legacy.LegacyShop;
import com.iluwatar.corruption.system.modern.Customer;
import com.iluwatar.corruption.system.modern.ModernOrder;
import com.iluwatar.corruption.system.modern.ModernShop;
import com.iluwatar.corruption.system.modern.Shipment;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AntiCorruptionLayerTest_Purified {

    @Autowired
    private LegacyShop legacyShop;

    @Autowired
    private ModernShop modernShop;

    @Test
    public void antiCorruptionLayerTest_1() throws ShopException {
        LegacyOrder legacyOrder = new LegacyOrder("1", "addr1", "item1", 1, 1);
        legacyShop.placeOrder(legacyOrder);
        Optional<LegacyOrder> legacyOrderWithIdOne = legacyShop.findOrder("1");
        assertEquals(Optional.of(legacyOrder), legacyOrderWithIdOne);
    }

    @Test
    public void antiCorruptionLayerTest_2() throws ShopException {
        ModernOrder modernOrder = new ModernOrder("1", new Customer("addr1"), new Shipment("item1", 1, 1), "");
        modernShop.placeOrder(modernOrder);
        Optional<ModernOrder> modernOrderWithIdOne = modernShop.findOrder("1");
        assertTrue(modernOrderWithIdOne.isEmpty());
    }
}
