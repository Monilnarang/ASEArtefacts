package net.sf.marineapi.nmea.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;

public class DateTest_Purified {

    private Date instance;

    private GregorianCalendar cal;

    @Before
    public void setUp() throws Exception {
        instance = new Date();
        cal = new GregorianCalendar();
    }

    @Test
    public void testConstructor_1() {
        assertEquals(cal.get(Calendar.YEAR), instance.getYear());
    }

    @Test
    public void testConstructor_2() {
        assertEquals(cal.get(Calendar.MONTH) + 1, instance.getMonth());
    }

    @Test
    public void testConstructor_3() {
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), instance.getDay());
    }

    @Test
    public void testEqualsAfterInit_1() {
        Date d = new Date();
        assertTrue(d.equals(instance));
    }

    @Test
    public void testEqualsAfterInit_2() {
        Date one = new Date(2010, 6, 15);
        Date two = new Date(2010, 6, 15);
        assertTrue(one.equals(two));
    }

    @Test
    public void testEqualsWrongType_1() {
        Object str = new String("foobar");
        assertFalse(instance.equals(str));
    }

    @Test
    public void testEqualsWrongType_2() {
        Object dbl = Double.valueOf(123);
        assertFalse(instance.equals(dbl));
    }
}
