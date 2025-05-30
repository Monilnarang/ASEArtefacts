package org.graylog2.indexer.searches;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import static org.assertj.jodatime.api.Assertions.assertThat;

public class IndexRangeStatsTest_Purified {

    @Test
    public void testEmptyInstance_1() throws Exception {
        assertThat(IndexRangeStats.EMPTY.min()).isEqualTo(new DateTime(0L, DateTimeZone.UTC));
    }

    @Test
    public void testEmptyInstance_2() throws Exception {
        assertThat(IndexRangeStats.EMPTY.max()).isEqualTo(new DateTime(0L, DateTimeZone.UTC));
    }
}
