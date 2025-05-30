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

public class TimeSeriesUtilsTest_Purified {

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
    public void testCompressTimeBucket_1() {
        Assertions.assertEquals(20000101L, compressTimeBucket(20000105, 11));
    }

    @Test
    public void testCompressTimeBucket_2() {
        Assertions.assertEquals(20000101L, compressTimeBucket(20000111, 11));
    }

    @Test
    public void testCompressTimeBucket_3() {
        Assertions.assertEquals(20000112L, compressTimeBucket(20000112, 11));
    }

    @Test
    public void testCompressTimeBucket_4() {
        Assertions.assertEquals(20000112L, compressTimeBucket(20000122, 11));
    }

    @Test
    public void testCompressTimeBucket_5() {
        Assertions.assertEquals(20000123L, compressTimeBucket(20000123, 11));
    }

    @Test
    public void testCompressTimeBucket_6() {
        Assertions.assertEquals(20000123L, compressTimeBucket(20000125, 11));
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

    @Test
    public void queryIndexNameTest_4() {
        Assertions.assertEquals("metrics-apdex-20220710", TimeSeriesUtils.queryIndexName("metrics-apdex", 20220710L, Step.DAY, false, false));
    }

    @Test
    public void queryIndexNameTest_5() {
        Assertions.assertEquals("metrics-apdex-20220710", TimeSeriesUtils.queryIndexName("metrics-apdex", 20220710111111L, Step.DAY, true, true));
    }
}
