package org.apache.druid.query.extraction;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StrlenExtractionFnTest_Parameterized {

    @Test
    public void testApply_1() {
        Assert.assertNull(StrlenExtractionFn.instance().apply(null));
    }

    @Test
    public void testApply_8() {
        Assert.assertEquals("2", StrlenExtractionFn.instance().apply(-1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testApply_2to7")
    public void testApply_2to7(int param1, String param2) {
        Assert.assertEquals(param1, StrlenExtractionFn.instance().apply(param2));
    }

    static public Stream<Arguments> Provider_testApply_2to7() {
        return Stream.of(arguments(0, ""), arguments(1, "x"), arguments(3, "foo"), arguments(3, "föo"), arguments(2, "\uD83D\uDE02"), arguments(1, 1));
    }
}
