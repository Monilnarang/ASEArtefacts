package org.graylog2.inputs.converters;

import org.graylog2.ConfigurationException;
import org.graylog2.plugin.inputs.Converter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.jodatime.api.Assertions.assertThat;

public class DateConverterTest_Purified {

    @Before
    public void setUp() {
        DateTimeUtils.setCurrentMillisFixed(DateTime.parse("2017-01-01T00:00:00.000Z").getMillis());
    }

    @After
    public void tearDown() {
        DateTimeUtils.setCurrentMillisSystem();
    }

    private Map<String, Object> config(final String dateFormat, final String timeZone, final String locale) {
        final Map<String, Object> config = new HashMap<>();
        config.put("date_format", dateFormat);
        config.put("time_zone", timeZone);
        config.put("locale", locale);
        return config;
    }

    @Test
    public void issue2648_1() throws Exception {
        final Converter utc = new DateConverter(config("YYYY-MM-dd HH:mm:ss", "UTC", null));
        final DateTime utcDate = (DateTime) utc.convert("2016-08-10 12:00:00");
        assertThat(utcDate).isEqualTo("2016-08-10T12:00:00.000Z");
    }

    @Test
    public void issue2648_2() throws Exception {
        final Converter cet = new DateConverter(config("YYYY-MM-dd HH:mm:ss", "CET", null));
        final DateTime cetDate = (DateTime) cet.convert("2016-08-10 12:00:00");
        assertThat(cetDate).isEqualTo(new DateTime("2016-08-10T12:00:00.000", DateTimeZone.forID("CET")));
    }

    @Test
    public void issue2648_3() throws Exception {
        final Converter berlin = new DateConverter(config("YYYY-MM-dd HH:mm:ss", "Europe/Berlin", null));
        final DateTime berlinDate = (DateTime) berlin.convert("2016-08-10 12:00:00");
        assertThat(berlinDate).isEqualTo(new DateTime("2016-08-10T12:00:00.000", DateTimeZone.forID("Europe/Berlin")));
    }
}
