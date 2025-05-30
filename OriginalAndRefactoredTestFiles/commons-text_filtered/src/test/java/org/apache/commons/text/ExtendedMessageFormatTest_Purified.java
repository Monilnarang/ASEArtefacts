package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtendedMessageFormatTest_Purified {

    private static final class LowerCaseFormat extends Format {

        static final Format INSTANCE = new LowerCaseFormat();

        static final FormatFactory FACTORY = (n, a, l) -> LowerCaseFormat.INSTANCE;

        private static final long serialVersionUID = 1L;

        @Override
        public StringBuffer format(final Object obj, final StringBuffer toAppendTo, final FieldPosition pos) {
            return toAppendTo.append(((String) obj).toLowerCase(Locale.ROOT));
        }

        @Override
        public Object parseObject(final String source, final ParsePosition pos) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class OtherExtendedMessageFormat extends ExtendedMessageFormat {

        private static final long serialVersionUID = 1L;

        OtherExtendedMessageFormat(final String pattern, final Locale locale, final Map<String, ? extends FormatFactory> registry) {
            super(pattern, locale, registry);
        }
    }

    private static final class OverrideShortDateFormatFactory {

        static final FormatFactory FACTORY = (n, a, l) -> !"short".equals(a) ? null : l == null ? DateFormat.getDateInstance(DateFormat.DEFAULT) : DateFormat.getDateInstance(DateFormat.DEFAULT, l);
    }

    private static final class UpperCaseFormat extends Format {

        static final Format INSTANCE = new UpperCaseFormat();

        static final FormatFactory FACTORY = (n, a, l) -> UpperCaseFormat.INSTANCE;

        private static final long serialVersionUID = 1L;

        @Override
        public StringBuffer format(final Object obj, final StringBuffer toAppendTo, final FieldPosition pos) {
            return toAppendTo.append(((String) obj).toUpperCase(Locale.ROOT));
        }

        @Override
        public Object parseObject(final String source, final ParsePosition pos) {
            throw new UnsupportedOperationException();
        }
    }

    private final Map<String, FormatFactory> registry = new HashMap<>();

    private void checkBuiltInFormat(final String pattern, final Map<String, ?> registryUnused, final Object[] args, final Locale locale) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("Pattern=[");
        buffer.append(pattern);
        buffer.append("], locale=[");
        buffer.append(locale);
        buffer.append("]");
        final MessageFormat mf = createMessageFormat(pattern, locale);
        ExtendedMessageFormat emf = null;
        if (locale == null) {
            emf = new ExtendedMessageFormat(pattern);
        } else {
            emf = new ExtendedMessageFormat(pattern, locale);
        }
        assertEquals(mf.format(args), emf.format(args), "format " + buffer.toString());
        assertEquals(mf.toPattern(), emf.toPattern(), "toPattern " + buffer.toString());
    }

    private void checkBuiltInFormat(final String pattern, final Map<String, ?> fmtRegistry, final Object[] args, final Locale[] locales) {
        checkBuiltInFormat(pattern, fmtRegistry, args, (Locale) null);
        for (final Locale locale : locales) {
            checkBuiltInFormat(pattern, fmtRegistry, args, locale);
        }
    }

    private void checkBuiltInFormat(final String pattern, final Object[] args, final Locale[] locales) {
        checkBuiltInFormat(pattern, null, args, locales);
    }

    private MessageFormat createMessageFormat(final String pattern, final Locale locale) {
        final MessageFormat result = new MessageFormat(pattern);
        if (locale != null) {
            result.setLocale(locale);
            result.applyPattern(pattern);
        }
        return result;
    }

    @BeforeEach
    public void setUp() {
        registry.put("lower", LowerCaseFormat.FACTORY);
        registry.put("upper", UpperCaseFormat.FACTORY);
    }

    @Test
    public void testEscapedBraces_LANG_948_1() {
        final String pattern = "Message without placeholders '{}'";
        final ExtendedMessageFormat emf = new ExtendedMessageFormat(pattern, registry);
        assertEquals("Message without placeholders {}", emf.format(new Object[] { "DUMMY" }));
    }

    @Test
    public void testEscapedBraces_LANG_948_2() {
        final String pattern2 = "Message with placeholder ''{0}''";
        final ExtendedMessageFormat emf2 = new ExtendedMessageFormat(pattern2, registry);
        assertEquals("Message with placeholder 'DUMMY'", emf2.format(new Object[] { "DUMMY" }));
    }
}
