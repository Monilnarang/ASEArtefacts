package org.graylog2.plugin.indexer.searches.timeranges;

import org.graylog.plugins.views.search.timeranges.OffsetRange;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.Test;
import static org.graylog.testing.jackson.JacksonSubtypesAssertions.assertThatDto;

class TimeRangeTest_Purified {

    @Test
    void subtypes_1() {
        final var now = DateTime.now(DateTimeZone.UTC);
        final var absoluteRange = AbsoluteRange.create(now, now);
    }

    @Test
    void subtypes_2() {
        final var relativeRange = RelativeRange.create(500);
    }

    @Test
    void subtypes_3() {
        final var keywordRange = KeywordRange.create("yesterday", "UTC");
    }

    @Test
    void subtypes_4() {
        final var offsetRange = OffsetRange.Builder.builder().offset(1).source("foo").build();
    }
}
