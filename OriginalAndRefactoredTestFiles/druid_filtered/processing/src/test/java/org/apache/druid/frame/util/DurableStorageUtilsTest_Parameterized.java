package org.apache.druid.frame.util;

import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DurableStorageUtilsTest_Parameterized {

    private static final String CONTROLLER_ID = "controller_id_1";

    private static final String TASK_ID = "task_id_1";

    private static final int WORKER_NUMBER = 2;

    private static final int STAGE_NUMBER = 1;

    private static final int PARTITION_NUMBER = 3;

    @Test
    public void getNextDirNameWithPrefixFromPath_5() {
        Assert.assertNull(DurableStorageUtils.getNextDirNameWithPrefixFromPath(null));
    }

    @Test
    public void isQueryResultFileActive_1() {
        Assert.assertTrue(DurableStorageUtils.isQueryResultFileActive(DurableStorageUtils.QUERY_RESULTS_DIR + "/123/result", ImmutableSet.of("123")));
    }

    @Test
    public void isQueryResultFileActive_4() {
        Assert.assertFalse(DurableStorageUtils.isQueryResultFileActive(null, ImmutableSet.of("123")));
    }

    @Test
    public void isQueryResultFileActive_5() {
        Assert.assertFalse(DurableStorageUtils.isQueryResultFileActive(DurableStorageUtils.QUERY_RESULTS_DIR, ImmutableSet.of("123")));
    }

    @ParameterizedTest
    @MethodSource("Provider_getNextDirNameWithPrefixFromPath_1to4")
    public void getNextDirNameWithPrefixFromPath_1to4(String param1, String param2) {
        Assert.assertEquals(param1, DurableStorageUtils.getNextDirNameWithPrefixFromPath(param2));
    }

    static public Stream<Arguments> Provider_getNextDirNameWithPrefixFromPath_1to4() {
        return Stream.of(arguments("", "/123/123"), arguments(123, 123), arguments("controller_query_123", "controller_query_123/123"), arguments("", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_isQueryResultFileActive_2to3")
    public void isQueryResultFileActive_2to3(String param1, String param2) {
        Assert.assertFalse(DurableStorageUtils.isQueryResultFileActive(DurableStorageUtils.QUERY_RESULTS_DIR + param1, ImmutableSet.of(param2)));
    }

    static public Stream<Arguments> Provider_isQueryResultFileActive_2to3() {
        return Stream.of(arguments("/123/result", ""), arguments("/", 123));
    }
}
