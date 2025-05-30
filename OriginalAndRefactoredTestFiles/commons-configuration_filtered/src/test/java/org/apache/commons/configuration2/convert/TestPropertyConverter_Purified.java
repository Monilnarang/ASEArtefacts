package org.apache.commons.configuration2.convert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.lang.annotation.ElementType;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import org.apache.commons.configuration2.ex.ConversionException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestPropertyConverter_Purified {

    private static final Class<ElementType> ENUM_CLASS = ElementType.class;

    @Test
    public void testToCustomNumber_1() {
        assertEquals(new MyNumber(1), PropertyConverter.to(MyNumber.class, "1", null));
    }

    @Test
    public void testToCustomNumber_2() {
        assertEquals(new MyNumber(2), PropertyConverter.to(MyNumber.class, new MyNumber(2), null));
    }

    @Test
    public void testToCustomNumber_3() {
        assertEquals(new MyNumber(0), PropertyConverter.to(MyNumber.class, null, null));
    }

    @Test
    public void testToNumberDirect_1() {
        final Integer i = Integer.valueOf(42);
        assertSame(i, PropertyConverter.toNumber(i, Integer.class));
    }

    @Test
    public void testToNumberDirect_2() {
        final BigDecimal d = new BigDecimal("3.1415");
        assertSame(d, PropertyConverter.toNumber(d, Integer.class));
    }

    @Test
    public void testToNumberFromString_1() {
        assertEquals(Integer.valueOf(42), PropertyConverter.toNumber("42", Integer.class));
    }

    @Test
    public void testToNumberFromString_2() {
        assertEquals(Short.valueOf((short) 10), PropertyConverter.toNumber(new StringBuffer("10"), Short.class));
    }
}
