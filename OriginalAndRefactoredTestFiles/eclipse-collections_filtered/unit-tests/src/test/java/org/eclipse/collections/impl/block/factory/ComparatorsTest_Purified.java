package org.eclipse.collections.impl.block.factory;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.eclipse.collections.api.block.SerializableComparator;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
import org.eclipse.collections.api.block.function.primitive.ByteFunction;
import org.eclipse.collections.api.block.function.primitive.CharFunction;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.function.primitive.FloatFunction;
import org.eclipse.collections.api.block.function.primitive.IntFunction;
import org.eclipse.collections.api.block.function.primitive.LongFunction;
import org.eclipse.collections.api.block.function.primitive.ShortFunction;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.test.domain.Person;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.eclipse.collections.impl.factory.Iterables.mList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparatorsTest_Purified {

    private void assertScalarFunctionParameter(SerializableComparator<Integer> comparator) {
        Verify.assertPositive(comparator.compare(2, 1));
        Verify.assertPositive(comparator.compare(-1, -2));
        Verify.assertZero(comparator.compare(1, 1));
        Verify.assertZero(comparator.compare(0, 0));
        Verify.assertZero(comparator.compare(-1, -1));
        Verify.assertNegative(comparator.compare(3, 5));
        Verify.assertNegative(comparator.compare(-5, -3));
    }

    public static class OneOfEach {

        public static final Function<OneOfEach, Date> TO_DATE_VALUE = oneOfEach -> new Date(oneOfEach.dateValue.getTime());

        private Date dateValue = Timestamp.valueOf("2004-12-12 22:20:30");

        public OneOfEach(Date dateValue) {
            this.dateValue = new Date(dateValue.getTime());
        }
    }

    public static class FancyDateComparator implements Comparator<Date>, Serializable {

        private static final String FANCY_DATE_FORMAT = "EEE, MMM d, ''yy";

        private static final long serialVersionUID = 1L;

        private final DateFormat formatter = new SimpleDateFormat(FANCY_DATE_FORMAT, Locale.ENGLISH);

        @Override
        public int compare(Date o1, Date o2) {
            String date1 = this.formatter.format(o1);
            String date2 = this.formatter.format(o2);
            return date1.compareTo(date2);
        }
    }

    @Test
    public void nullSafeEquals_1() {
        assertTrue(Comparators.nullSafeEquals("Fred", "Fred"));
    }

    @Test
    public void nullSafeEquals_2() {
        assertTrue(Comparators.nullSafeEquals(null, null));
    }

    @Test
    public void nullSafeEquals_3() {
        assertFalse(Comparators.nullSafeEquals("Fred", "Jim"));
    }

    @Test
    public void nullSafeEquals_4() {
        assertFalse(Comparators.nullSafeEquals("Fred", null));
    }

    @Test
    public void nullSafeEquals_5() {
        assertFalse(Comparators.nullSafeEquals(null, "Jim"));
    }

    @Test
    public void nullSafeCompare_1() {
        assertEquals(0, Comparators.nullSafeCompare(null, null));
    }

    @Test
    public void nullSafeCompare_2() {
        assertEquals(0, Comparators.nullSafeCompare("Sheila", "Sheila"));
    }

    @Test
    public void nullSafeCompare_3() {
        assertTrue(Comparators.nullSafeCompare("Fred", "Jim") < 0);
    }

    @Test
    public void nullSafeCompare_4() {
        assertTrue(Comparators.nullSafeCompare("Sheila", "Jim") > 0);
    }

    @Test
    public void nullSafeCompare_5() {
        assertEquals(1, Comparators.nullSafeCompare("Fred", null));
    }

    @Test
    public void nullSafeCompare_6() {
        assertEquals(-1, Comparators.nullSafeCompare(null, "Jim"));
    }
}
