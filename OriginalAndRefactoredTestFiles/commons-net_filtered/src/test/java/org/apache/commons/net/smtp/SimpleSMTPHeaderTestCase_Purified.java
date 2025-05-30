package org.apache.commons.net.smtp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;

public class SimpleSMTPHeaderTestCase_Purified {

    private SimpleSMTPHeader header;

    private Date beforeDate;

    private String checkDate(final String msg) {
        final Pattern pat = Pattern.compile("^(Date: (.+))$", Pattern.MULTILINE);
        final Matcher m = pat.matcher(msg);
        if (m.find()) {
            final String date = m.group(2);
            final String pattern = "EEE, dd MMM yyyy HH:mm:ss Z";
            final SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
            try {
                final Date sentDate = format.parse(date);
                final long sentSecs = sentDate.getTime() / 1000;
                final long beforeDateSecs = beforeDate.getTime() / 1000;
                final Date afterDate = new Date();
                final long afterDateSecs = afterDate.getTime() / 1000;
                if (sentSecs < beforeDateSecs) {
                    fail(sentDate + " should be after " + beforeDate);
                }
                if (sentSecs > afterDateSecs) {
                    fail(sentDate + " should be before " + afterDate);
                }
            } catch (final ParseException e) {
                fail("" + e);
            }
            final int start = m.start(1);
            final int end = m.end(1);
            if (start == 0) {
                return msg.substring(end + 1);
            }
            return msg.substring(0, start) + msg.substring(end + 1);
        }
        fail("Expecting Date header in " + msg);
        return null;
    }

    @Before
    public void setUp() {
        beforeDate = new Date();
        header = new SimpleSMTPHeader("from@here.invalid", "to@there.invalid", "Test email");
    }

    @Test
    public void testToString_1() {
        assertNotNull(header);
    }

    @Test
    public void testToString_2() {
        assertEquals("From: from@here.invalid\nTo: to@there.invalid\nSubject: Test email\n\n", checkDate(header.toString()));
    }
}
