package org.apache.amoro.scan;

import org.apache.amoro.io.MixedDataTestHelpers;
import org.apache.amoro.io.TableDataTestBase;
import org.apache.amoro.io.writer.GenericChangeTaskWriter;
import org.apache.amoro.io.writer.GenericTaskWriters;
import org.apache.amoro.shade.guava32.com.google.common.collect.ImmutableList;
import org.apache.iceberg.AppendFiles;
import org.apache.iceberg.data.Record;
import org.apache.iceberg.io.CloseableIterable;
import org.apache.iceberg.io.CloseableIterator;
import org.apache.iceberg.io.WriteResult;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestKeyedTableScan_Purified extends TableDataTestBase {

    private void assertFileCount(int baseFileCnt, int insertFileCnt, int equDeleteFileCnt) throws IOException {
        CloseableIterable<CombinedScanTask> combinedScanTasks = getMixedTable().asKeyedTable().newScan().planTasks();
        final List<MixedFileScanTask> allBaseTasks = new ArrayList<>();
        final List<MixedFileScanTask> allInsertTasks = new ArrayList<>();
        final List<MixedFileScanTask> allEquDeleteTasks = new ArrayList<>();
        try (CloseableIterator<CombinedScanTask> initTasks = combinedScanTasks.iterator()) {
            while (initTasks.hasNext()) {
                CombinedScanTask combinedScanTask = initTasks.next();
                combinedScanTask.tasks().forEach(task -> {
                    allBaseTasks.addAll(task.baseTasks());
                    allInsertTasks.addAll(task.insertTasks());
                    allEquDeleteTasks.addAll(task.mixedEquityDeletes());
                });
            }
        }
        Assert.assertEquals(baseFileCnt, allBaseTasks.size());
        Assert.assertEquals(insertFileCnt, allInsertTasks.size());
        Assert.assertEquals(equDeleteFileCnt, allEquDeleteTasks.size());
    }

    private void writeInsertFileIntoBaseStore() throws IOException {
        ImmutableList.Builder<Record> builder = ImmutableList.builder();
        builder.add(MixedDataTestHelpers.createRecord(7, "mary", 0, "2022-01-01T12:00:00"));
        builder.add(MixedDataTestHelpers.createRecord(8, "mack", 0, "2022-01-01T12:00:00"));
        ImmutableList<Record> records = builder.build();
        GenericChangeTaskWriter writer = GenericTaskWriters.builderFor(getMixedTable().asKeyedTable()).withTransactionId(5L).buildChangeWriter();
        for (Record record : records) {
            writer.write(record);
        }
        WriteResult result = writer.complete();
        AppendFiles baseAppend = getMixedTable().asKeyedTable().baseTable().newAppend();
        Arrays.stream(result.dataFiles()).forEach(baseAppend::appendFile);
        baseAppend.commit();
    }

    @Test
    public void testScanWithInsertFileInBaseStore_1() throws IOException {
        assertFileCount(4, 2, 1);
    }

    @Test
    public void testScanWithInsertFileInBaseStore_2() throws IOException {
        assertFileCount(6, 2, 1);
    }
}
