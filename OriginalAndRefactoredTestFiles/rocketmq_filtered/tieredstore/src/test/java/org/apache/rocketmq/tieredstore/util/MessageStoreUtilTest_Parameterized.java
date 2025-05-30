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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MessageStoreUtilTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_offset2FileNameTest_1to2")
    public void offset2FileNameTest_1to2(String param1, int param2) {
        Assert.assertEquals(param1, MessageStoreUtil.offset2FileName(param2));
    }

    static public Stream<Arguments> Provider_offset2FileNameTest_1to2() {
        return Stream.of(arguments("cfcd208400000000000000000000", 0), arguments("b10da56800000000004294937144", 4294937144L));
    }

    @ParameterizedTest
    @MethodSource("Provider_fileName2OffsetTest_1to2")
    public void fileName2OffsetTest_1to2(int param1, String param2) {
        Assert.assertEquals(param1, MessageStoreUtil.fileName2Offset(param2));
    }

    static public Stream<Arguments> Provider_fileName2OffsetTest_1to2() {
        return Stream.of(arguments(0, "cfcd208400000000000000000000"), arguments(4294937144L, "b10da56800000000004294937144"));
    }
}
