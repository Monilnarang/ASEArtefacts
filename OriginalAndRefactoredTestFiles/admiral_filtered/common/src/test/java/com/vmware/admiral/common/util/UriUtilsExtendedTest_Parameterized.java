package com.vmware.admiral.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.net.URI;
import org.junit.Test;
import com.vmware.admiral.service.common.ReverseProxyService;
import com.vmware.xenon.common.LocalizableValidationException;
import com.vmware.xenon.common.UriUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UriUtilsExtendedTest_Parameterized {

    private static final String SAMPLE_URL = "http://github.com/ob?branch=master&product=admiral";

    private static final URI SAMPLE_URI = UriUtils.buildUri(SAMPLE_URL);

    @Test
    public void testReverseProxyRequest_1() {
        assertNotNull(SAMPLE_URI);
    }

    @Test
    public void testReverseProxyRequest_2_testMerged_2() {
        URI rpUri = UriUtilsExtended.getReverseProxyUri(SAMPLE_URI);
        assertNotEquals(SAMPLE_URI, rpUri);
        URI targetUri = UriUtilsExtended.getReverseProxyTargetUri(rpUri);
        assertNotEquals(rpUri, targetUri);
        assertEquals(SAMPLE_URI, targetUri);
    }

    @Test
    public void testExtractHostPort_3() {
        assertEquals(null, UriUtilsExtended.extractPort("localhost"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testExtractHostPort_1to2")
    public void testExtractHostPort_1to2(int param1, String param2) {
        assertEquals(param1, UriUtilsExtended.extractPort(param2));
    }

    static public Stream<Arguments> Provider_testExtractHostPort_1to2() {
        return Stream.of(arguments(5000, "localhost:5000"), arguments(5000, "https://test-host:5000"));
    }
}
