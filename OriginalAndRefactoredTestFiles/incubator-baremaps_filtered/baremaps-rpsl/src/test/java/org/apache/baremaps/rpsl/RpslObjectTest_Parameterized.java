package org.apache.baremaps.rpsl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.apache.baremaps.testing.TestFiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RpslObjectTest_Parameterized {

    private List<RpslObject> objects;

    @BeforeEach
    public void before() throws IOException {
        var file = TestFiles.resolve("baremaps-testing/data/ripe/sample.txt");
        try (var input = Files.newInputStream(file)) {
            objects = new RpslReader().read(input).toList();
        }
    }

    @ParameterizedTest
    @MethodSource("Provider_type_1to3")
    void type_1to3(String param1, int param2) {
        assertEquals(param1, objects.get(param2).type());
    }

    static public Stream<Arguments> Provider_type_1to3() {
        return Stream.of(arguments("inetnum", 0), arguments("organisation", 8), arguments("inet6num", 9));
    }

    @ParameterizedTest
    @MethodSource("Provider_id_1to3")
    void id_1to3(String param1, int param2) {
        assertEquals(param1, objects.get(param2).id());
    }

    static public Stream<Arguments> Provider_id_1to3() {
        return Stream.of(arguments("0.0.0.0 - 0.0.0.255", 0), arguments("ORG-VDN2-RIPE", 8), arguments("2001:7fa:0:2::/64", 9));
    }

    @ParameterizedTest
    @MethodSource("Provider_attributes_1to3")
    void attributes_1to3(int param1, int param2) {
        assertEquals(param1, objects.get(param2).attributes().size());
    }

    static public Stream<Arguments> Provider_attributes_1to3() {
        return Stream.of(arguments(7, 0), arguments(5, 8), arguments(7, 9));
    }
}
