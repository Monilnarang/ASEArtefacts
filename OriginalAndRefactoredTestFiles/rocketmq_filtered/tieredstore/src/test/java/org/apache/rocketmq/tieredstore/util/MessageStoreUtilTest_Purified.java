package org.apache.rocketmq.tieredstore.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageStoreUtilTest_Purified {

    private static final Logger log = LoggerFactory.getLogger(MessageStoreUtil.TIERED_STORE_LOGGER_NAME);

    private static final String TIERED_STORE_PATH = "tiered_store_test";

    public static String getRandomStorePath() {
        return Paths.get(System.getProperty("user.home"), TIERED_STORE_PATH, UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 16)).toString();
    }

    public static void deleteStoreDirectory(String storePath) {
        try {
            FileUtils.deleteDirectory(new File(storePath));
        } catch (IOException e) {
            log.error("Delete store directory failed, filePath: {}", storePath, e);
        }
    }

    @Test
    public void offset2FileNameTest_1() {
        Assert.assertEquals("cfcd208400000000000000000000", MessageStoreUtil.offset2FileName(0));
    }

    @Test
    public void offset2FileNameTest_2() {
        Assert.assertEquals("b10da56800000000004294937144", MessageStoreUtil.offset2FileName(4294937144L));
    }

    @Test
    public void fileName2OffsetTest_1() {
        Assert.assertEquals(0, MessageStoreUtil.fileName2Offset("cfcd208400000000000000000000"));
    }

    @Test
    public void fileName2OffsetTest_2() {
        Assert.assertEquals(4294937144L, MessageStoreUtil.fileName2Offset("b10da56800000000004294937144"));
    }
}
