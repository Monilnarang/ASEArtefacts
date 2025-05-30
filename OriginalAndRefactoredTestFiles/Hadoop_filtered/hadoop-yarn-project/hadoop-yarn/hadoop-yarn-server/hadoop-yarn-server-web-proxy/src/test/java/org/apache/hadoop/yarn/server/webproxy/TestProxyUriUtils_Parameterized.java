package org.apache.hadoop.yarn.server.webproxy;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.apache.hadoop.util.Lists;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.server.utils.BuilderUtils;
import org.apache.hadoop.yarn.util.TrackingUriPlugin;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestProxyUriUtils_Parameterized {

    @Test
    void testGetPathApplicationIdString_1() {
        assertEquals("/proxy/application_6384623_0005", ProxyUriUtils.getPath(BuilderUtils.newApplicationId(6384623l, 5), null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetPathApplicationId_1to2")
    void testGetPathApplicationId_1to2(String param1, long param2, int param3) {
        assertEquals(param1, ProxyUriUtils.getPath(BuilderUtils.newApplicationId(param2, param3)));
    }

    static public Stream<Arguments> Provider_testGetPathApplicationId_1to2() {
        return Stream.of(arguments("/proxy/application_100_0001", 100l, 1), arguments("/proxy/application_6384623_0005", 6384623l, 5));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetPathApplicationIdString_2to4")
    void testGetPathApplicationIdString_2to4(String param1, String param2, long param3, int param4) {
        assertEquals(param1, ProxyUriUtils.getPath(BuilderUtils.newApplicationId(param3, param4), param2));
    }

    static public Stream<Arguments> Provider_testGetPathApplicationIdString_2to4() {
        return Stream.of(arguments("/proxy/application_6384623_0005/static/app", "/static/app", 6384623l, 5), arguments("/proxy/application_6384623_0005/", "/", 6384623l, 5), arguments("/proxy/application_6384623_0005/some/path", "some/path", 6384623l, 5));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetPathAndQuery_1to2")
    void testGetPathAndQuery_1to2(String param1, String param2, String param3, long param4, int param5) {
        assertEquals(param1, ProxyUriUtils.getPathAndQuery(BuilderUtils.newApplicationId(param5, 5), param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testGetPathAndQuery_1to2() {
        return Stream.of(arguments("/proxy/application_6384623_0005/static/app?foo=bar", "/static/app", "?foo=bar", 6384623l, 5), arguments("/proxy/application_6384623_0005/static/app?foo=bar&bad=good&proxyapproved=true", "/static/app", "foo=bar&bad=good", 6384623l, 5));
    }
}
