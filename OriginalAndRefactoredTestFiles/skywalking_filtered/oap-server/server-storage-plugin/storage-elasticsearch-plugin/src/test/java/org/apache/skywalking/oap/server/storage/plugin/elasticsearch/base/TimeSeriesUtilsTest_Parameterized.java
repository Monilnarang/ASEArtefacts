package org.apache.skywalking.oap.server.storage.plugin.elasticsearch.base;

import com.google.common.collect.Lists;
import org.apache.skywalking.oap.server.core.analysis.DownSampling;
import org.apache.skywalking.oap.server.core.analysis.metrics.Metrics;
import org.apache.skywalking.oap.server.core.analysis.record.Record;
import org.apache.skywalking.oap.server.core.query.enumeration.Step;
import org.apache.skywalking.oap.server.core.storage.model.BanyanDBModelExtension;
import org.apache.skywalking.oap.server.core.storage.model.ElasticSearchModelExtension;
import org.apache.skywalking.oap.server.core.storage.model.Model;
import org.apache.skywalking.oap.server.core.storage.model.SQLDatabaseModelExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.skywalking.oap.server.storage.plugin.elasticsearch.base.TimeSeriesUtils.compressTimeBucket;
import static org.apache.skywalking.oap.server.storage.plugin.elasticsearch.base.TimeSeriesUtils.writeIndexName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TimeSeriesUtilsTest_Parameterized {

    private Model superDatasetModel;

    private Model normalRecordModel;

    private Model normalMetricsModel;

    @BeforeEach
    public void prepare() {
        superDatasetModel = new Model("superDatasetModel", Lists.newArrayList(), 0, DownSampling.Second, true, Record.class, true, new SQLDatabaseModelExtension(), new BanyanDBModelExtension(), new ElasticSearchModelExtension());
        normalRecordModel = new Model("normalRecordModel", Lists.newArrayList(), 0, DownSampling.Second, false, Record.class, true, new SQLDatabaseModelExtension(), new BanyanDBModelExtension(), new ElasticSearchModelExtension());
        normalMetricsModel = new Model("normalMetricsModel", Lists.newArrayList(), 0, DownSampling.Minute, false, Metrics.class, true, new SQLDatabaseModelExtension(), new BanyanDBModelExtension(), new ElasticSearchModelExtension());
        TimeSeriesUtils.setSUPER_DATASET_DAY_STEP(1);
        TimeSeriesUtils.setDAY_STEP(3);
    }

    @Test
    public void testIndexRolling_1_testMerged_1() {
        long secondTimeBucket = 2020_0809_1010_59L;
        Assertions.assertEquals("superDatasetModel-20200809", writeIndexName(superDatasetModel, secondTimeBucket));
        Assertions.assertEquals("records-all-20200807", writeIndexName(normalRecordModel, secondTimeBucket));
        secondTimeBucket += 1000000;
        Assertions.assertEquals("superDatasetModel-20200810", writeIndexName(superDatasetModel, secondTimeBucket));
        Assertions.assertEquals("records-all-20200810", writeIndexName(normalRecordModel, secondTimeBucket));
    }

    @Test
    public void testIndexRolling_3_testMerged_2() {
        long minuteTimeBucket = 2020_0809_1010L;
        Assertions.assertEquals("metrics-all-20200807", writeIndexName(normalMetricsModel, minuteTimeBucket));
        minuteTimeBucket += 10000;
        Assertions.assertEquals("metrics-all-20200810", writeIndexName(normalMetricsModel, minuteTimeBucket));
    }

    @Test
    public void queryIndexNameTest_1() {
        Assertions.assertEquals("metrics-apdex-20220710", TimeSeriesUtils.queryIndexName("metrics-apdex", 20220710111111L, Step.SECOND, false, false));
    }

    @Test
    public void queryIndexNameTest_2() {
        Assertions.assertEquals("metrics-apdex-20220710", TimeSeriesUtils.queryIndexName("metrics-apdex", 202207101111L, Step.MINUTE, false, false));
    }

    @Test
    public void queryIndexNameTest_3() {
        Assertions.assertEquals("metrics-apdex-20220710", TimeSeriesUtils.queryIndexName("metrics-apdex", 2022071011L, Step.HOUR, false, false));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompressTimeBucket_1to6")
    public void testCompressTimeBucket_1to6(long param1, int param2, int param3) {
        Assertions.assertEquals(param1, compressTimeBucket(param2, param3));
    }

    static public Stream<Arguments> Provider_testCompressTimeBucket_1to6() {
        return Stream.of(arguments(20000101L, 20000105, 11), arguments(20000101L, 20000111, 11), arguments(20000112L, 20000112, 11), arguments(20000112L, 20000122, 11), arguments(20000123L, 20000123, 11), arguments(20000123L, 20000125, 11));
    }

    @ParameterizedTest
    @MethodSource("Provider_queryIndexNameTest_4to5")
    public void queryIndexNameTest_4to5(String param1, String param2, long param3) {
        Assertions.assertEquals(param1, TimeSeriesUtils.queryIndexName(param2, param3, Step.DAY, false, false));
    }

    static public Stream<Arguments> Provider_queryIndexNameTest_4to5() {
        return Stream.of(arguments("metrics-apdex-20220710", "metrics-apdex", 20220710L), arguments("metrics-apdex-20220710", "metrics-apdex", 20220710111111L));
    }
}
