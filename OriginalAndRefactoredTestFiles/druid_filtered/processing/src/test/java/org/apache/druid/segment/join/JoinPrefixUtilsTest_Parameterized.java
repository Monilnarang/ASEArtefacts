package org.apache.druid.segment.join;

import org.apache.druid.segment.column.ColumnHolder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JoinPrefixUtilsTest_Parameterized {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_isPrefixedBy_4() {
        Assert.assertFalse(JoinPrefixUtils.isPrefixedBy("foo", "foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_isPrefixedBy_1to3")
    public void test_isPrefixedBy_1to3(String param1, String param2) {
        Assert.assertTrue(JoinPrefixUtils.isPrefixedBy(param1, param2));
    }

    static public Stream<Arguments> Provider_test_isPrefixedBy_1to3() {
        return Stream.of(arguments("foo", ""), arguments("foo", "f"), arguments("foo", "fo"));
    }
}
