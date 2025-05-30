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

public class UriUtilsExtendedTest_Purified {

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
    public void testExtractHostPort_1() {
        assertEquals("5000", UriUtilsExtended.extractPort("localhost:5000"));
    }

    @Test
    public void testExtractHostPort_2() {
        assertEquals("5000", UriUtilsExtended.extractPort("https://test-host:5000"));
    }

    @Test
    public void testExtractHostPort_3() {
        assertEquals(null, UriUtilsExtended.extractPort("localhost"));
    }
}
