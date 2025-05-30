package org.eclipse.collections.impl.block.function.primitive;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Deprecated
public class CharFunctionTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_toUppercase_1to3")
    public void toUppercase_1to3(String param1, String param2) {
        assertEquals(param1, CharFunction.TO_UPPERCASE.valueOf(param2));
    }

    static public Stream<Arguments> Provider_toUppercase_1to3() {
        return Stream.of(arguments("A", "a"), arguments("A", "A"), arguments(1, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_toLowercase_1to3")
    public void toLowercase_1to3(String param1, String param2) {
        assertEquals(param1, CharFunction.TO_LOWERCASE.valueOf(param2));
    }

    static public Stream<Arguments> Provider_toLowercase_1to3() {
        return Stream.of(arguments("a", "a"), arguments("a", "A"), arguments(1, 1));
    }
}
