package org.graylog2.plugin.streams;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MatchingTypeTest_Purified {

    @Test
    public void testValueOfOrDefault_1() throws Exception {
        assertEquals(Stream.MatchingType.AND, Stream.MatchingType.valueOfOrDefault("AND"));
    }

    @Test
    public void testValueOfOrDefault_2() throws Exception {
        assertEquals(Stream.MatchingType.OR, Stream.MatchingType.valueOfOrDefault("OR"));
    }

    @Test
    public void testValueOfOrDefault_3() throws Exception {
        assertEquals(Stream.MatchingType.DEFAULT, Stream.MatchingType.valueOfOrDefault(null));
    }

    @Test
    public void testValueOfOrDefault_4() throws Exception {
        assertEquals(Stream.MatchingType.DEFAULT, Stream.MatchingType.valueOfOrDefault(""));
    }
}
