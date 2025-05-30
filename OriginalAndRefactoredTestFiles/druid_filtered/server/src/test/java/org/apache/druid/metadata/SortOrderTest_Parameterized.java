package org.apache.druid.metadata;

import org.apache.druid.error.DruidExceptionMatcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SortOrderTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testAsc_1_3_5")
    public void testAsc_1_3_5(String param1) {
        Assert.assertEquals(SortOrder.ASC, SortOrder.fromValue(param1));
    }

    static public Stream<Arguments> Provider_testAsc_1_3_5() {
        return Stream.of(arguments("asc"), arguments("ASC"), arguments("AsC"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAsc_2_2_4_4_6_6")
    public void testAsc_2_2_4_4_6_6(String param1, String param2) {
        Assert.assertEquals(param1, SortOrder.fromValue(param2).toString());
    }

    static public Stream<Arguments> Provider_testAsc_2_2_4_4_6_6() {
        return Stream.of(arguments("ASC", "asc"), arguments("ASC", "ASC"), arguments("ASC", "AsC"), arguments("DESC", "desc"), arguments("DESC", "DESC"), arguments("DESC", "DesC"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDesc_1_3_5")
    public void testDesc_1_3_5(String param1) {
        Assert.assertEquals(SortOrder.DESC, SortOrder.fromValue(param1));
    }

    static public Stream<Arguments> Provider_testDesc_1_3_5() {
        return Stream.of(arguments("desc"), arguments("DESC"), arguments("DesC"));
    }
}
