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

public class TestProxyUriUtils_Purified {

    @Test
    void testGetPathApplicationId_1() {
        assertEquals("/proxy/application_100_0001", ProxyUriUtils.getPath(BuilderUtils.newApplicationId(100l, 1)));
    }

    @Test
    void testGetPathApplicationId_2() {
        assertEquals("/proxy/application_6384623_0005", ProxyUriUtils.getPath(BuilderUtils.newApplicationId(6384623l, 5)));
    }

    @Test
    void testGetPathApplicationIdString_1() {
        assertEquals("/proxy/application_6384623_0005", ProxyUriUtils.getPath(BuilderUtils.newApplicationId(6384623l, 5), null));
    }

    @Test
    void testGetPathApplicationIdString_2() {
        assertEquals("/proxy/application_6384623_0005/static/app", ProxyUriUtils.getPath(BuilderUtils.newApplicationId(6384623l, 5), "/static/app"));
    }

    @Test
    void testGetPathApplicationIdString_3() {
        assertEquals("/proxy/application_6384623_0005/", ProxyUriUtils.getPath(BuilderUtils.newApplicationId(6384623l, 5), "/"));
    }

    @Test
    void testGetPathApplicationIdString_4() {
        assertEquals("/proxy/application_6384623_0005/some/path", ProxyUriUtils.getPath(BuilderUtils.newApplicationId(6384623l, 5), "some/path"));
    }

    @Test
    void testGetPathAndQuery_1() {
        assertEquals("/proxy/application_6384623_0005/static/app?foo=bar", ProxyUriUtils.getPathAndQuery(BuilderUtils.newApplicationId(6384623l, 5), "/static/app", "?foo=bar", false));
    }

    @Test
    void testGetPathAndQuery_2() {
        assertEquals("/proxy/application_6384623_0005/static/app?foo=bar&bad=good&proxyapproved=true", ProxyUriUtils.getPathAndQuery(BuilderUtils.newApplicationId(6384623l, 5), "/static/app", "foo=bar&bad=good", true));
    }
}
