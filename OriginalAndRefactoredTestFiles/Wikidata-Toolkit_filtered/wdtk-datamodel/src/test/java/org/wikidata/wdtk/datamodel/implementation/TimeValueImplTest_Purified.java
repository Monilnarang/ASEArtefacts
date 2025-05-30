package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.io.IOException;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.TimeValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TimeValueImplTest_Purified {

    private final ObjectMapper mapper = new ObjectMapper();

    private final TimeValue t1 = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 0, 1, 60, TimeValue.CM_GREGORIAN_PRO);

    private final TimeValue t2 = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 0, 1, 60, TimeValue.CM_GREGORIAN_PRO);

    private final TimeValue t3 = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 0, 1, 60, "foo");

    private final String JSON_TIME_VALUE = "{\"value\":{\"time\":\"+2007-05-12T10:45:00Z\",\"timezone\":60,\"before\":0,\"after\":1,\"precision\":14,\"calendarmodel\":\"http://www.wikidata.org/entity/Q1985727\"},\"type\":\"time\"}";

    @Test
    public void storedValuesCorrect_1() {
        assertEquals(t1.getYear(), 2007);
    }

    @Test
    public void storedValuesCorrect_2() {
        assertEquals(t1.getMonth(), 5);
    }

    @Test
    public void storedValuesCorrect_3() {
        assertEquals(t1.getDay(), 12);
    }

    @Test
    public void storedValuesCorrect_4() {
        assertEquals(t1.getHour(), 10);
    }

    @Test
    public void storedValuesCorrect_5() {
        assertEquals(t1.getMinute(), 45);
    }

    @Test
    public void storedValuesCorrect_6() {
        assertEquals(t1.getSecond(), 0);
    }

    @Test
    public void storedValuesCorrect_7() {
        assertEquals(t1.getPrecision(), TimeValue.PREC_SECOND);
    }

    @Test
    public void storedValuesCorrect_8() {
        assertEquals(t1.getBeforeTolerance(), 0);
    }

    @Test
    public void storedValuesCorrect_9() {
        assertEquals(t1.getAfterTolerance(), 1);
    }

    @Test
    public void storedValuesCorrect_10() {
        assertEquals(t1.getTimezoneOffset(), 60);
    }

    @Test
    public void storedValuesCorrect_11() {
        assertEquals(t1.getPreferredCalendarModel(), TimeValue.CM_GREGORIAN_PRO);
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(t1, t1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(t1, t2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        TimeValue tdYear = new TimeValueImpl(2013, (byte) 5, (byte) 12, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 0, 1, 60, TimeValue.CM_GREGORIAN_PRO);
        assertNotEquals(t1, tdYear);
    }

    @Test
    public void equalityBasedOnContent_4() {
        TimeValue tdMonth = new TimeValueImpl(2007, (byte) 6, (byte) 12, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 0, 1, 60, TimeValue.CM_GREGORIAN_PRO);
        assertNotEquals(t1, tdMonth);
    }

    @Test
    public void equalityBasedOnContent_5() {
        TimeValue tdDay = new TimeValueImpl(2007, (byte) 5, (byte) 13, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 0, 1, 60, TimeValue.CM_GREGORIAN_PRO);
        assertNotEquals(t1, tdDay);
    }

    @Test
    public void equalityBasedOnContent_6() {
        TimeValue tdHour = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 11, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 0, 1, 60, TimeValue.CM_GREGORIAN_PRO);
        assertNotEquals(t1, tdHour);
    }

    @Test
    public void equalityBasedOnContent_7() {
        TimeValue tdMinute = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 10, (byte) 47, (byte) 0, TimeValue.PREC_SECOND, 0, 1, 60, TimeValue.CM_GREGORIAN_PRO);
        assertNotEquals(t1, tdMinute);
    }

    @Test
    public void equalityBasedOnContent_8() {
        TimeValue tdSecond = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 10, (byte) 45, (byte) 1, TimeValue.PREC_SECOND, 0, 1, 60, TimeValue.CM_GREGORIAN_PRO);
        assertNotEquals(t1, tdSecond);
    }

    @Test
    public void equalityBasedOnContent_9() {
        TimeValue tdTimezone = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 0, 1, 120, TimeValue.CM_GREGORIAN_PRO);
        assertNotEquals(t1, tdTimezone);
    }

    @Test
    public void equalityBasedOnContent_10() {
        TimeValue tdBefore = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 1, 1, 60, TimeValue.CM_GREGORIAN_PRO);
        assertNotEquals(t1, tdBefore);
    }

    @Test
    public void equalityBasedOnContent_11() {
        TimeValue tdAfter = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 0, 2, 60, TimeValue.CM_GREGORIAN_PRO);
        assertNotEquals(t1, tdAfter);
    }

    @Test
    public void equalityBasedOnContent_12() {
        TimeValue tdPrecision = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_DAY, 0, 1, 60, TimeValue.CM_GREGORIAN_PRO);
        assertNotEquals(t1, tdPrecision);
    }

    @Test
    public void equalityBasedOnContent_13() {
        TimeValue tdCalendar = new TimeValueImpl(2007, (byte) 5, (byte) 12, (byte) 10, (byte) 45, (byte) 0, TimeValue.PREC_SECOND, 0, 1, 60, TimeValue.CM_JULIAN_PRO);
        assertNotEquals(t1, tdCalendar);
    }

    @Test
    public void equalityBasedOnContent_14() {
        assertNotEquals(t1, null);
    }

    @Test
    public void equalityBasedOnContent_15() {
        assertNotEquals(t1, this);
    }
}
