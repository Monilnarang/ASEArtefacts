package org.apache.storm.tuple;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FieldsTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_fieldsConstructorDoesNotThrowWithValidArgsTest_1to2")
    public void fieldsConstructorDoesNotThrowWithValidArgsTest_1to2(int param1, String param2, String param3) {
        assertEquals(new Fields(param2, param3).size(), param1);
    }

    static public Stream<Arguments> Provider_fieldsConstructorDoesNotThrowWithValidArgsTest_1to2() {
        return Stream.of(arguments(2, "foo", "bar"), arguments(2, "foo", "bar"));
    }
}
