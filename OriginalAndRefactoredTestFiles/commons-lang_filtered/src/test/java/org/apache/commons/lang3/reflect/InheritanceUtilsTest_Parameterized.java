package org.apache.commons.lang3.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.reflect.testbed.AnotherChild;
import org.apache.commons.lang3.reflect.testbed.AnotherParent;
import org.apache.commons.lang3.reflect.testbed.Grandchild;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class InheritanceUtilsTest_Parameterized extends AbstractLangTest {

    @ParameterizedTest
    @MethodSource("Provider_testDistanceGreaterThanZero_1to4")
    public void testDistanceGreaterThanZero_1to4(int param1) {
        assertEquals(param1, InheritanceUtils.distance(AnotherChild.class, AnotherParent.class));
    }

    static public Stream<Arguments> Provider_testDistanceGreaterThanZero_1to4() {
        return Stream.of(arguments(1), arguments(1), arguments(2), arguments(3));
    }
}
