package org.apache.baremaps.rpsl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.apache.baremaps.testing.TestFiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RpslObjectTest_Purified {

    private List<RpslObject> objects;

    @BeforeEach
    public void before() throws IOException {
        var file = TestFiles.resolve("baremaps-testing/data/ripe/sample.txt");
        try (var input = Files.newInputStream(file)) {
            objects = new RpslReader().read(input).toList();
        }
    }

    @Test
    void type_1() {
        assertEquals("inetnum", objects.get(0).type());
    }

    @Test
    void type_2() {
        assertEquals("organisation", objects.get(8).type());
    }

    @Test
    void type_3() {
        assertEquals("inet6num", objects.get(9).type());
    }

    @Test
    void id_1() {
        assertEquals("0.0.0.0 - 0.0.0.255", objects.get(0).id());
    }

    @Test
    void id_2() {
        assertEquals("ORG-VDN2-RIPE", objects.get(8).id());
    }

    @Test
    void id_3() {
        assertEquals("2001:7fa:0:2::/64", objects.get(9).id());
    }

    @Test
    void attributes_1() {
        assertEquals(7, objects.get(0).attributes().size());
    }

    @Test
    void attributes_2() {
        assertEquals(5, objects.get(8).attributes().size());
    }

    @Test
    void attributes_3() {
        assertEquals(7, objects.get(9).attributes().size());
    }
}
