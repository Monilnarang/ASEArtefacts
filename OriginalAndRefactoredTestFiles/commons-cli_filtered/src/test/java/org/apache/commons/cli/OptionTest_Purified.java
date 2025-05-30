package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.jupiter.api.Test;

public class OptionTest_Purified {

    private static final class DefaultOption extends Option {

        private static final long serialVersionUID = 1L;

        private final String defaultValue;

        DefaultOption(final String opt, final String description, final String defaultValue) throws IllegalArgumentException {
            super(opt, true, description);
            this.defaultValue = defaultValue;
        }

        @Override
        public String getValue() {
            return super.getValue() != null ? super.getValue() : defaultValue;
        }
    }

    private static final class TestOption extends Option {

        private static final long serialVersionUID = 1L;

        TestOption(final String opt, final boolean hasArg, final String description) throws IllegalArgumentException {
            super(opt, hasArg, description);
        }

        @Override
        public boolean addValue(final String value) {
            processValue(value);
            return true;
        }
    }

    private static void checkOption(final Option option, final String opt, final String description, final String longOpt, final int numArgs, final String argName, final boolean required, final boolean optionalArg, final char valueSeparator, final Class<?> cls, final String deprecatedDesc, final Boolean deprecatedForRemoval, final String deprecatedSince) {
        assertEquals(opt, option.getOpt());
        assertEquals(description, option.getDescription());
        assertEquals(longOpt, option.getLongOpt());
        assertEquals(numArgs, option.getArgs());
        assertEquals(argName, option.getArgName());
        assertEquals(required, option.isRequired());
        assertEquals(optionalArg, option.hasOptionalArg());
        assertEquals(numArgs > 0, option.hasArg());
        assertEquals(numArgs > 0, option.acceptsArg());
        assertEquals(valueSeparator, option.getValueSeparator());
        assertEquals(cls, option.getType());
        if (deprecatedDesc != null) {
            assertEquals(deprecatedDesc, option.getDeprecated().getDescription());
        }
        if (deprecatedForRemoval != null) {
            assertEquals(deprecatedForRemoval, option.getDeprecated().isForRemoval());
        }
        if (deprecatedSince != null) {
            assertEquals(deprecatedSince, option.getDeprecated().getSince());
        }
    }

    private Option roundTrip(final Option o) throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        final ObjectInputStream ois = new ObjectInputStream(bais);
        return (Option) ois.readObject();
    }

    @Test
    public void testHashCode_1() {
        assertNotEquals(Option.builder("test").build().hashCode(), Option.builder("test2").build().hashCode());
    }

    @Test
    public void testHashCode_2() {
        assertNotEquals(Option.builder("test").build().hashCode(), Option.builder().longOpt("test").build().hashCode());
    }

    @Test
    public void testHashCode_3() {
        assertNotEquals(Option.builder("test").build().hashCode(), Option.builder("test").longOpt("long test").build().hashCode());
    }
}
