package com.vmware.admiral.image.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.nio.file.Paths;
import java.util.Collection;
import org.junit.Test;
import com.vmware.admiral.common.test.BaseTestCase;
import com.vmware.admiral.common.util.FileUtil;
import com.vmware.admiral.host.HostInitCommonServiceConfig;
import com.vmware.admiral.host.HostInitImageServicesConfig;
import com.vmware.admiral.service.common.ConfigurationService.ConfigurationFactoryService;
import com.vmware.admiral.service.common.ConfigurationService.ConfigurationState;
import com.vmware.xenon.common.UriUtils;

public class PopularImagesServiceTest_Purified extends BaseTestCase {

    @Test
    public void testGetExternalPopularImages_1() throws Throwable {
        ConfigurationState config = new ConfigurationState();
        ConfigurationState storedConfig = doPut(config);
        assertNotNull(storedConfig);
    }

    @Test
    public void testGetExternalPopularImages_2_testMerged_2() throws Throwable {
        waitForServiceAvailability(PopularImagesService.SELF_LINK);
        Collection<?> images = getDocument(Collection.class, PopularImagesService.SELF_LINK);
        assertNotNull(images);
        assertEquals(5, images.size());
    }
}
