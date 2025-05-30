package org.apache.flink.connector.file.table;

import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.file.table.PartitionWriter.Context;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.table.utils.LegacyRowResource;
import org.apache.flink.types.Row;
import org.apache.flink.util.CollectionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class PartitionWriterTest_Purified {

    private final LegacyRowResource usesLegacyRows = LegacyRowResource.INSTANCE;

    @TempDir
    private java.nio.file.Path tmpDir;

    private PartitionTempFileManager manager;

    @BeforeEach
    void before() throws IOException {
        manager = new PartitionTempFileManager(fsFactory, new Path(tmpDir.toUri()), 0, 0);
        usesLegacyRows.before();
    }

    @AfterEach
    void after() {
        usesLegacyRows.after();
    }

    private final Map<String, List<Row>> records = new LinkedHashMap<>();

    private final OutputFormatFactory<Row> factory = path -> new OutputFormat<Row>() {

        private static final long serialVersionUID = -5797045183913321175L;

        @Override
        public void configure(Configuration parameters) {
        }

        @Override
        public void open(InitializationContext context) {
            records.put(getKey(), new ArrayList<>());
        }

        private String getKey() {
            Path parent = path.getParent();
            return parent.getName().startsWith("task-") ? parent.getName() : parent.getParent().getName() + Path.SEPARATOR + parent.getName();
        }

        @Override
        public void writeRecord(Row record) {
            records.get(getKey()).add(record);
        }

        @Override
        public void close() {
        }
    };

    private final Context<Row> context = new Context<>(null, path -> factory.createOutputFormat(path));

    private FileSystemFactory fsFactory = FileSystem::get;

    private PartitionComputer<Row> computer = new PartitionComputer<Row>() {

        @Override
        public LinkedHashMap<String, String> generatePartValues(Row in) {
            LinkedHashMap<String, String> ret = CollectionUtil.newLinkedHashMapWithExpectedSize(1);
            ret.put("p", in.getField(0).toString());
            return ret;
        }

        @Override
        public Row projectColumnsToWrite(Row in) {
            return in;
        }
    };

    public PartitionWriterTest() throws Exception {
    }

    @Test
    void testSingleDirectoryWriter_1() throws Exception {
        assertThat(records.toString()).isEqualTo("{task-0-attempt-0=[p1,1, p1,2, p2,2]}");
    }

    @Test
    void testSingleDirectoryWriter_2() throws Exception {
        assertThat(records.toString()).isEqualTo("{task-0-attempt-0=[p1,1, p1,2, p2,2], task-1-attempt-0=[p3,3, p5,5, p2,2]}");
    }

    @Test
    void testGroupedPartitionWriter_1() throws Exception {
        assertThat(records.toString()).isEqualTo("{task-0-attempt-0/p=p1=[p1,1, p1,2], task-0-attempt-0/p=p2=[p2,2]}");
    }

    @Test
    void testGroupedPartitionWriter_2() throws Exception {
        assertThat(records.toString()).isEqualTo("{task-0-attempt-0/p=p1=[p1,1, p1,2], task-0-attempt-0/p=p2=[p2,2], task-1-attempt-1/p=p3=[p3,3], task-1-attempt-1/p=p4=[p4,5], task-1-attempt-1/p=p5=[p5,2]}");
    }

    @Test
    void testDynamicPartitionWriter_1() throws Exception {
        assertThat(records.toString()).isEqualTo("{task-0-attempt-0/p=p1=[p1,1, p1,2], task-0-attempt-0/p=p2=[p2,2]}");
    }

    @Test
    void testDynamicPartitionWriter_2() throws Exception {
        assertThat(records.toString()).isEqualTo("{task-0-attempt-0/p=p1=[p1,1, p1,2], task-0-attempt-0/p=p2=[p2,2], task-1-attempt-1/p=p4=[p4,5], task-1-attempt-1/p=p3=[p3,3], task-1-attempt-1/p=p5=[p5,2]}");
    }
}
