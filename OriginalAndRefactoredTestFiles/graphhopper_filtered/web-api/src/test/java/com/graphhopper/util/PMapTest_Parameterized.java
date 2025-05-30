package com.graphhopper.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PMapTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_empty_1to2")
    public void empty_1to2(String param1) {
        assertTrue(new PMap(param1).toMap().isEmpty());
    }

    static public Stream<Arguments> Provider_empty_1to2() {
        return Stream.of(arguments(""), arguments("name"));
    }
}
