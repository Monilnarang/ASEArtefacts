package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.QuantityValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;

public class QuantityValueImplTest_Purified {

    private final ObjectMapper mapper = new ObjectMapper();

    private final BigDecimal nv = new BigDecimal("0.123456789012345678901234567890123456789");

    private final BigDecimal lb = new BigDecimal("0.123456789012345678901234567890123456788");

    private final BigDecimal ub = new BigDecimal("0.123456789012345678901234567890123456790");

    private final String unitMeter = "http://wikidata.org/entity/Q11573";

    private final ItemIdValue unitMeterId = ItemIdValueImpl.fromIri(unitMeter);

    private final QuantityValue q1 = new QuantityValueImpl(nv, lb, ub, unitMeterId);

    private final QuantityValue q2 = new QuantityValueImpl(nv, lb, ub, unitMeterId);

    private final QuantityValue q3 = new QuantityValueImpl(nv, null, null, unitMeterId);

    private final QuantityValue q4 = new QuantityValueImpl(nv, lb, ub, (ItemIdValue) null);

    private static String JSON_QUANTITY_VALUE = "{\"value\":{\"amount\":\"+0.123456789012345678901234567890123456789\",\"lowerBound\":\"+0.123456789012345678901234567890123456788\",\"upperBound\":\"+0.123456789012345678901234567890123456790\",\"unit\":\"http://wikidata.org/entity/Q11573\"},\"type\":\"quantity\"}";

    private static String JSON_UNBOUNDED_QUANTITY_VALUE = "{\"value\":{\"amount\":\"+0.123456789012345678901234567890123456789\",\"unit\":\"http://wikidata.org/entity/Q11573\"},\"type\":\"quantity\"}";

    private static String JSON_INVALID_UNIT = "{\"value\":{\"amount\":\"+0.1234567890123\",\"unit\":\"not_a_url\"},\"type\":\"quantity\"}";

    @Test
    public void gettersWorking_1() {
        assertEquals(q1.getNumericValue(), nv);
    }

    @Test
    public void gettersWorking_2() {
        assertEquals(q1.getLowerBound(), lb);
    }

    @Test
    public void gettersWorking_3() {
        assertEquals(q1.getUpperBound(), ub);
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(q1, q1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(q1, q2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        assertNotEquals(q1, q3);
    }

    @Test
    public void equalityBasedOnContent_4_testMerged_4() {
        BigDecimal nvplus = new BigDecimal("0.1234567890123456789012345678901234567895");
        QuantityValue q4 = new QuantityValueImpl(nvplus, lb, ub, unitMeterId);
        QuantityValue q6 = new QuantityValueImpl(nv, lb, nvplus, unitMeterId);
        assertNotEquals(q1, q4);
        assertNotEquals(q1, q6);
    }

    @Test
    public void equalityBasedOnContent_5() {
        BigDecimal nvminus = new BigDecimal("0.1234567890123456789012345678901234567885");
        QuantityValue q5 = new QuantityValueImpl(nv, nvminus, ub, unitMeterId);
        assertNotEquals(q1, q5);
    }

    @Test
    public void equalityBasedOnContent_7() {
        QuantityValue q7 = new QuantityValueImpl(nv, lb, ub, (ItemIdValue) null);
        assertNotEquals(q1, q7);
    }

    @Test
    public void equalityBasedOnContent_8() {
        assertNotEquals(q1, null);
    }

    @Test
    public void equalityBasedOnContent_9() {
        assertNotEquals(q1, this);
    }
}
