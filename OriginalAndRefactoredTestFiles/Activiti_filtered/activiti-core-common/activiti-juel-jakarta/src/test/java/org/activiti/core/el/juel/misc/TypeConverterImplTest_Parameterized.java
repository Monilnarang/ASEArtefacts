package org.activiti.core.el.juel.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import jakarta.el.ELException;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.activiti.core.el.juel.test.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TypeConverterImplTest_Parameterized extends TestCase {

    public static class DateEditor implements PropertyEditor {

        private Date value;

        public void addPropertyChangeListener(PropertyChangeListener listener) {
        }

        public String getAsText() {
            return value == null ? null : "" + value.getTime();
        }

        public Component getCustomEditor() {
            return null;
        }

        public String getJavaInitializationString() {
            return null;
        }

        public String[] getTags() {
            return null;
        }

        public Object getValue() {
            return value;
        }

        public boolean isPaintable() {
            return false;
        }

        public void paintValue(Graphics gfx, Rectangle box) {
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
        }

        public void setAsText(String text) throws IllegalArgumentException {
            value = new Date(Long.parseLong(text));
        }

        public void setValue(Object value) {
            this.value = (Date) value;
        }

        public boolean supportsCustomEditor() {
            return false;
        }
    }

    static {
        PropertyEditorManager.registerEditor(Date.class, DateEditor.class);
    }

    static enum Foo {

        BAR, BAZ {

            @Override
            public String toString() {
                return "XXX";
            }
        }

    }

    TypeConverterImpl converter = new TypeConverterImpl();

    @Test
    public void testToBoolean_1() {
        assertFalse(converter.coerceToBoolean(null));
    }

    @Test
    public void testToBoolean_3() {
        assertTrue(converter.coerceToBoolean(Boolean.TRUE));
    }

    @Test
    public void testToBoolean_4() {
        assertFalse(converter.coerceToBoolean(Boolean.FALSE));
    }

    @Test
    public void testToBoolean_5() {
        assertTrue(converter.coerceToBoolean("true"));
    }

    @Test
    public void testToString_1() {
        assertSame("foo", converter.coerceToString("foo"));
    }

    @Test
    public void testToString_2() {
        assertEquals("", converter.coerceToString(null));
    }

    @Test
    public void testToString_3() {
        assertEquals(Foo.BAR.name(), converter.coerceToString(Foo.BAR));
    }

    @Test
    public void testToString_4() {
        Object value = new BigDecimal("99.345");
        assertEquals(value.toString(), converter.coerceToString(value));
    }

    @Test
    public void testToEnum_1() {
        assertNull(converter.coerceToEnum(null, Foo.class));
    }

    @Test
    public void testToEnum_2() {
        assertSame(Foo.BAR, converter.coerceToEnum(Foo.BAR, Foo.class));
    }

    @Test
    public void testToEnum_3() {
        assertNull(converter.coerceToEnum("", Foo.class));
    }

    @Test
    public void testToEnum_4() {
        assertSame(Foo.BAR, converter.coerceToEnum("BAR", Foo.class));
    }

    @Test
    public void testToEnum_5() {
        assertSame(Foo.BAZ, converter.coerceToEnum("BAZ", Foo.class));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToBoolean_2_6to7")
    public void testToBoolean_2_6to7(String param1) {
        assertFalse(converter.coerceToBoolean(param1));
    }

    static public Stream<Arguments> Provider_testToBoolean_2_6to7() {
        return Stream.of(arguments(""), arguments(false), arguments("yes"));
    }
}
