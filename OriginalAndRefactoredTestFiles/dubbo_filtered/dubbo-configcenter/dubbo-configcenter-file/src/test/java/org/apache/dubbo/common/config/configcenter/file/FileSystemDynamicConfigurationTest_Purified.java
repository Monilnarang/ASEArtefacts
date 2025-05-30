package org.apache.dubbo.common.config.configcenter.file;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.URL.valueOf;
import static org.apache.dubbo.common.config.configcenter.DynamicConfiguration.DEFAULT_GROUP;
import static org.apache.dubbo.common.config.configcenter.file.FileSystemDynamicConfiguration.CONFIG_CENTER_DIR_PARAM_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
class FileSystemDynamicConfigurationTest_Purified {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private FileSystemDynamicConfiguration configuration;

    private static final String KEY = "abc-def-ghi";

    private static final String CONTENT = "Hello,World";

    @BeforeEach
    public void init() {
        File rootDirectory = new File(getClassPath(), "config-center");
        rootDirectory.mkdirs();
        try {
            FileUtils.cleanDirectory(rootDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL url = valueOf("dubbo://127.0.0.1:20880").addParameter(CONFIG_CENTER_DIR_PARAM_NAME, rootDirectory.getAbsolutePath());
        configuration = new FileSystemDynamicConfiguration(url);
    }

    @AfterEach
    public void destroy() throws Exception {
        FileUtils.deleteQuietly(configuration.getRootDirectory());
        configuration.close();
    }

    private String getClassPath() {
        return getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    @Test
    void testPublishAndGetConfig_1() {
        assertTrue(configuration.publishConfig(KEY, CONTENT));
    }

    @Test
    void testPublishAndGetConfig_2() {
        assertTrue(configuration.publishConfig(KEY, CONTENT));
    }

    @Test
    void testPublishAndGetConfig_3() {
        assertTrue(configuration.publishConfig(KEY, CONTENT));
    }

    @Test
    void testPublishAndGetConfig_4() {
        assertEquals(CONTENT, configuration.getConfig(KEY, DEFAULT_GROUP));
    }

    @Test
    void testRemoveConfig_1() throws Exception {
        assertTrue(configuration.publishConfig(KEY, DEFAULT_GROUP, "A"));
    }

    @Test
    void testRemoveConfig_2() throws Exception {
        assertEquals("A", FileUtils.readFileToString(configuration.configFile(KEY, DEFAULT_GROUP), configuration.getEncoding()));
    }

    @Test
    void testRemoveConfig_3() throws Exception {
        assertTrue(configuration.removeConfig(KEY, DEFAULT_GROUP));
    }

    @Test
    void testRemoveConfig_4() throws Exception {
        assertFalse(configuration.configFile(KEY, DEFAULT_GROUP).exists());
    }
}
