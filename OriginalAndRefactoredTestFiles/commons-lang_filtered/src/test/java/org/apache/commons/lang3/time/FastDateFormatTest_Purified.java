package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;
import org.junitpioneer.jupiter.DefaultTimeZone;

public class FastDateFormatTest_Purified extends AbstractLangTest {

    private static final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZ";

    private static final int NTHREADS = 10;

    private static final int NROUNDS = 10000;

    final Locale FINNISH = Locale.forLanguageTag("fi");

    final Locale HUNGARIAN = Locale.forLanguageTag("hu");

    private AtomicLongArray measureTime(final Format printer, final Format parser) throws InterruptedException {
        final ExecutorService pool = Executors.newFixedThreadPool(NTHREADS);
        final AtomicInteger failures = new AtomicInteger();
        final AtomicLongArray totalElapsed = new AtomicLongArray(2);
        try {
            for (int i = 0; i < NTHREADS; ++i) {
                pool.submit(() -> {
                    for (int j = 0; j < NROUNDS; ++j) {
                        try {
                            final Date date = new Date();
                            final long t0Millis = System.currentTimeMillis();
                            final String formattedDate = printer.format(date);
                            totalElapsed.addAndGet(0, System.currentTimeMillis() - t0Millis);
                            final long t1Millis = System.currentTimeMillis();
                            final Object pd = parser.parseObject(formattedDate);
                            totalElapsed.addAndGet(1, System.currentTimeMillis() - t1Millis);
                            if (!date.equals(pd)) {
                                failures.incrementAndGet();
                            }
                        } catch (final Exception e) {
                            failures.incrementAndGet();
                            e.printStackTrace();
                        }
                    }
                });
            }
        } finally {
            pool.shutdown();
            if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                fail("did not complete tasks");
            }
        }
        assertEquals(0, failures.get());
        return totalElapsed;
    }

    @Test
    public void testDateDefaults_1() {
        assertEquals(FastDateFormat.getDateInstance(FastDateFormat.LONG, Locale.CANADA), FastDateFormat.getDateInstance(FastDateFormat.LONG, TimeZone.getDefault(), Locale.CANADA));
    }

    @Test
    public void testDateDefaults_2() {
        assertEquals(FastDateFormat.getDateInstance(FastDateFormat.LONG, TimeZone.getTimeZone("America/New_York")), FastDateFormat.getDateInstance(FastDateFormat.LONG, TimeZone.getTimeZone("America/New_York"), Locale.getDefault()));
    }

    @Test
    public void testDateDefaults_3() {
        assertEquals(FastDateFormat.getDateInstance(FastDateFormat.LONG), FastDateFormat.getDateInstance(FastDateFormat.LONG, TimeZone.getDefault(), Locale.getDefault()));
    }

    @Test
    public void testLang1641_1() {
        assertSame(FastDateFormat.getInstance(ISO_8601_DATE_FORMAT), FastDateFormat.getInstance(ISO_8601_DATE_FORMAT));
    }

    @Test
    public void testLang1641_2() {
        assertSame(FastDateFormat.getInstance(ISO_8601_DATE_FORMAT, FastTimeZone.getGmtTimeZone()), FastDateFormat.getInstance(ISO_8601_DATE_FORMAT, FastTimeZone.getGmtTimeZone()));
    }

    @Test
    public void testLang1641_3() {
        assertSame(FastDateFormat.getInstance(ISO_8601_DATE_FORMAT, TimeZone.getDefault()), FastDateFormat.getInstance(ISO_8601_DATE_FORMAT, TimeZone.getDefault()));
    }

    @Test
    public void testLang1641_4() {
        assertNotSame(FastDateFormat.getInstance(ISO_8601_DATE_FORMAT, TimeZone.getTimeZone("Australia/Broken_Hill")), FastDateFormat.getInstance(ISO_8601_DATE_FORMAT, TimeZone.getTimeZone("Australia/Yancowinna")));
    }

    @Test
    public void testParseCentralEuropeanSummerTime_1() throws ParseException {
        assertNotNull(FastDateFormat.getInstance("dd.MM.yyyy HH:mm:ss", Locale.GERMANY).parse("26.10.2014 02:00:00"));
    }

    @Test
    public void testParseCentralEuropeanSummerTime_2() throws ParseException {
        assertNotNull(FastDateFormat.getInstance("dd.MM.yyyy HH:mm:ss z", Locale.US).parse("26.10.2014 02:00:00 CEST"));
    }

    @Test
    public void testParseCentralEuropeanSummerTime_3() throws ParseException {
        assertNotNull(FastDateFormat.getInstance("dd.MM.yyyy HH:mm:ss z", Locale.GERMANY).parse("26.10.2014 02:00:00 MESZ"));
    }

    @Test
    public void testTimeDateDefaults_1() {
        assertEquals(FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, Locale.CANADA), FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, TimeZone.getDefault(), Locale.CANADA));
    }

    @Test
    public void testTimeDateDefaults_2() {
        assertEquals(FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, TimeZone.getTimeZone("America/New_York")), FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, TimeZone.getTimeZone("America/New_York"), Locale.getDefault()));
    }

    @Test
    public void testTimeDateDefaults_3() {
        assertEquals(FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM), FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, TimeZone.getDefault(), Locale.getDefault()));
    }

    @Test
    public void testTimeDefaults_1() {
        assertEquals(FastDateFormat.getTimeInstance(FastDateFormat.LONG, Locale.CANADA), FastDateFormat.getTimeInstance(FastDateFormat.LONG, TimeZone.getDefault(), Locale.CANADA));
    }

    @Test
    public void testTimeDefaults_2() {
        assertEquals(FastDateFormat.getTimeInstance(FastDateFormat.LONG, TimeZone.getTimeZone("America/New_York")), FastDateFormat.getTimeInstance(FastDateFormat.LONG, TimeZone.getTimeZone("America/New_York"), Locale.getDefault()));
    }

    @Test
    public void testTimeDefaults_3() {
        assertEquals(FastDateFormat.getTimeInstance(FastDateFormat.LONG), FastDateFormat.getTimeInstance(FastDateFormat.LONG, TimeZone.getDefault(), Locale.getDefault()));
    }
}
