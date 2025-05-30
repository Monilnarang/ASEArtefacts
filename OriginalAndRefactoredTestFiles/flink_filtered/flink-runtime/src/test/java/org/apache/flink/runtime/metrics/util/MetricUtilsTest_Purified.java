package org.apache.flink.runtime.metrics.util;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.MetricOptions;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.MeterView;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.runtime.clusterframework.types.AllocationID;
import org.apache.flink.runtime.memory.MemoryAllocationException;
import org.apache.flink.runtime.memory.MemoryManager;
import org.apache.flink.runtime.metrics.MetricNames;
import org.apache.flink.runtime.rpc.RpcService;
import org.apache.flink.runtime.rpc.RpcSystem;
import org.apache.flink.runtime.taskexecutor.TaskManagerServices;
import org.apache.flink.runtime.taskexecutor.TaskManagerServicesBuilder;
import org.apache.flink.runtime.taskexecutor.slot.TestingTaskSlotTable;
import org.apache.flink.runtime.taskmanager.Task;
import org.apache.flink.testutils.ClassLoaderUtils;
import org.apache.flink.util.ChildFirstClassLoader;
import org.apache.flink.util.FlinkException;
import org.apache.flink.util.function.CheckedSupplier;
import org.apache.flink.shaded.guava33.com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import javax.management.ObjectName;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.apache.flink.runtime.metrics.util.MetricUtils.METRIC_GROUP_FLINK;
import static org.apache.flink.runtime.metrics.util.MetricUtils.METRIC_GROUP_MANAGED_MEMORY;
import static org.apache.flink.runtime.metrics.util.MetricUtils.METRIC_GROUP_MEMORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class MetricUtilsTest_Purified {

    private final List<Object> referencedObjects = new ArrayList<>();

    @AfterEach
    void cleanupReferencedObjects() {
        referencedObjects.clear();
    }

    private static void validateCollectorMetric(InterceptingOperatorMetricGroup group, long count, long time) {
        assertThat(((Gauge) group.get("Count")).getValue()).isEqualTo(count);
        assertThat(((Gauge) group.get("Time")).getValue()).isEqualTo(time);
        MeterView perSecond = ((MeterView) group.get("TimeMsPerSecond"));
        perSecond.update();
        assertThat(perSecond.getRate()).isGreaterThan(0.);
    }

    private static class Dummy {
    }

    private static Class<?> redefineDummyClass() throws ClassNotFoundException {
        Class<?> clazz = Dummy.class;
        ChildFirstClassLoader classLoader = new ChildFirstClassLoader(ClassLoaderUtils.getClasspathURLs(), clazz.getClassLoader(), new String[] { "java." }, ignored -> {
        });
        Class<?> newClass = classLoader.loadClass(clazz.getName());
        assertThat(newClass).isNotSameAs(clazz);
        assertThat(newClass.getName()).isEqualTo(clazz.getName());
        return newClass;
    }

    private static boolean hasMetaspaceMemoryPool() {
        return ManagementFactory.getMemoryPoolMXBeans().stream().anyMatch(bean -> "Metaspace".equals(bean.getName()));
    }

    private void runUntilMetricChanged(String name, int maxRuns, CheckedSupplier<Object> objectCreator, Gauge<Long> metric) throws Exception {
        maxRuns = Math.max(1, maxRuns);
        long initialValue = metric.getValue();
        for (int i = 0; i < maxRuns; i++) {
            Object object = objectCreator.get();
            long currentValue = metric.getValue();
            if (currentValue != initialValue) {
                return;
            }
            referencedObjects.add(object);
            Thread.sleep(50);
        }
        String msg = String.format("%s usage metric never changed its value after %d runs.", name, maxRuns);
        fail(msg);
    }

    static class TestGcBean implements GarbageCollectorMXBean {

        final String name;

        final long collectionCount;

        final long collectionTime;

        public TestGcBean(String name, long collectionCount, long collectionTime) {
            this.name = name;
            this.collectionCount = collectionCount;
            this.collectionTime = collectionTime;
        }

        @Override
        public long getCollectionCount() {
            return collectionCount;
        }

        @Override
        public long getCollectionTime() {
            return collectionTime;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isValid() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String[] getMemoryPoolNames() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectName getObjectName() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    void testTruncateOperatorName_1() {
        assertThat(MetricUtils.truncateOperatorName(null)).isNull();
    }

    @Test
    void testTruncateOperatorName_2() {
        final String operatorNameLess = "testOperatorName";
        assertThat(MetricUtils.truncateOperatorName(operatorNameLess)).isEqualTo(operatorNameLess);
    }

    @Test
    void testTruncateOperatorName_3() {
        final String operatorNameLessEndWithWriter = "testOperatorName: Writer";
        assertThat(MetricUtils.truncateOperatorName(operatorNameLessEndWithWriter)).isEqualTo(operatorNameLessEndWithWriter);
    }

    @Test
    void testTruncateOperatorName_4() {
        final String operatorNameLessEndWithCommitter = "testOperatorName: Committer";
        assertThat(MetricUtils.truncateOperatorName(operatorNameLessEndWithCommitter)).isEqualTo(operatorNameLessEndWithCommitter);
    }

    @Test
    void testTruncateOperatorName_5() {
        final String operatorNameLessAndContainsWriter = "test: WriterOperatorName";
        assertThat(MetricUtils.truncateOperatorName(operatorNameLessAndContainsWriter)).isEqualTo(operatorNameLessAndContainsWriter);
    }

    @Test
    void testTruncateOperatorName_6() {
        final String operatorNameLessAndContainsCommitter = "test: CommitterOperatorName";
        assertThat(MetricUtils.truncateOperatorName(operatorNameLessAndContainsCommitter)).isEqualTo(operatorNameLessAndContainsCommitter);
    }

    @Test
    void testTruncateOperatorName_7_testMerged_7() {
        final String operatorNameMore = "testLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongOperatorName";
        final String expectedOperatorNameMore = "testLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLong";
        assertThat(MetricUtils.truncateOperatorName(operatorNameMore)).isEqualTo(expectedOperatorNameMore);
        final String operatorNameMoreAndContainsWriter = "testLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLong: WriterOperatorName";
        assertThat(MetricUtils.truncateOperatorName(operatorNameMoreAndContainsWriter)).isEqualTo(expectedOperatorNameMore);
        final String operatorNameMoreAndContainsCommitter = "testLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLong: CommitterOperatorName";
        assertThat(MetricUtils.truncateOperatorName(operatorNameMoreAndContainsCommitter)).isEqualTo(expectedOperatorNameMore);
    }

    @Test
    void testTruncateOperatorName_8() {
        final String operatorNameMoreEndWithWriter = "testLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongOperatorName: Writer";
        final String expectedOperatorNameMoreEndWithWriter = "testLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLong: Writer";
        assertThat(MetricUtils.truncateOperatorName(operatorNameMoreEndWithWriter)).isEqualTo(expectedOperatorNameMoreEndWithWriter);
    }

    @Test
    void testTruncateOperatorName_9() {
        final String operatorNameMoreEndWithCommitter = "testLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongOperatorName: Committer";
        final String expectedOperatorNameMoreEndWithCommitter = "testLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongLongL: Committer";
        assertThat(MetricUtils.truncateOperatorName(operatorNameMoreEndWithCommitter)).isEqualTo(expectedOperatorNameMoreEndWithCommitter);
    }
}
