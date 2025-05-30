package org.apache.druid.common.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ConfigsTest_Parameterized {

    @Test
    public void testValueOrDefault_2() {
        Assert.assertEquals(11, Configs.valueOrDefault((Integer) null, 11));
    }

    @Test
    public void testValueOrDefault_5() {
        Assert.assertFalse(Configs.valueOrDefault((Boolean) false, true));
    }

    @Test
    public void testValueOrDefault_6() {
        Assert.assertTrue(Configs.valueOrDefault(null, true));
    }

    @Test
    public void testValueOrDefault_7() {
        Assert.assertEquals("abc", Configs.valueOrDefault("abc", "def"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValueOrDefault_1_3")
    public void testValueOrDefault_1_3(int param1, int param2, int param3) {
        Assert.assertEquals(param1, Configs.valueOrDefault((Integer) param3, param2));
    }

    static public Stream<Arguments> Provider_testValueOrDefault_1_3() {
        return Stream.of(arguments(10, 11, 10), arguments(10, 11L, 10L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValueOrDefault_4_8")
    public void testValueOrDefault_4_8(int param1, long param2) {
        Assert.assertEquals(param1, Configs.valueOrDefault(param2, 11L));
    }

    static public Stream<Arguments> Provider_testValueOrDefault_4_8() {
        return Stream.of(arguments(11, 11L), arguments("def", "def"));
    }
}
