package org.apache.seata.core.protocol;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class VersionTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_isAboveOrEqualVersion150_1to2")
    public void isAboveOrEqualVersion150_1to2(String param1) {
        Assertions.assertTrue(Version.isAboveOrEqualVersion150(param1));
    }

    static public Stream<Arguments> Provider_isAboveOrEqualVersion150_1to2() {
        return Stream.of(arguments("2.0.2"), arguments(1.5));
    }

    @ParameterizedTest
    @MethodSource("Provider_isAboveOrEqualVersion150_3to5")
    public void isAboveOrEqualVersion150_3to5(String param1) {
        Assertions.assertFalse(Version.isAboveOrEqualVersion150(param1));
    }

    static public Stream<Arguments> Provider_isAboveOrEqualVersion150_3to5() {
        return Stream.of(arguments("1.4.9"), arguments(""), arguments("abd"));
    }
}
