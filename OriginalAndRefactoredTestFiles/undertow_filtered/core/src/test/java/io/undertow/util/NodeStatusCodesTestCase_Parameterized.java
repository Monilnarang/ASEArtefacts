package io.undertow.util;

import io.undertow.testutils.category.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Category(UnitTest.class)
public class NodeStatusCodesTestCase_Parameterized {

    @Test
    public void testUnknownCode_1() {
        Assert.assertEquals("Unexpected reason phrase", "Unknown", StatusCodes.getReason(-1));
    }

    @Test
    public void testUnknownCode_4() {
        Assert.assertEquals("Unexpected reason phrase", "Unknown", StatusCodes.getReason(Integer.MAX_VALUE));
    }

    @Test
    public void testUnknownCode_5() {
        Assert.assertEquals("Unexpected reason phrase", "Unknown", StatusCodes.getReason(Integer.MIN_VALUE));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnknownCode_2to3")
    public void testUnknownCode_2to3(String param1, String param2, int param3) {
        Assert.assertEquals(param1, param2, StatusCodes.getReason(param3));
    }

    static public Stream<Arguments> Provider_testUnknownCode_2to3() {
        return Stream.of(arguments("Unexpected reason phrase", "Unknown", 999), arguments("Unexpected reason phrase", "Unknown", 735));
    }
}
