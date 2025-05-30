package org.apache.flink.connector.file.table;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.local.LocalFileSystem;
import org.apache.flink.table.catalog.ObjectIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

public class FileSystemCommitterTest_Purified {

    private static final String SUCCESS_FILE_NAME = "_SUCCESS";

    private final FileSystemFactory fileSystemFactory = FileSystem::get;

    private TableMetaStoreFactory metaStoreFactory;

    private List<PartitionCommitPolicy> policies;

    private ObjectIdentifier identifier;

    @TempDir
    private java.nio.file.Path outputPath;

    @TempDir
    private java.nio.file.Path path;

    @BeforeEach
    public void before() throws IOException {
        metaStoreFactory = new TestMetaStoreFactory(new Path(outputPath.toString()));
        policies = new PartitionCommitPolicyFactory("metastore,success-file", null, SUCCESS_FILE_NAME, null).createPolicyChain(Thread.currentThread().getContextClassLoader(), LocalFileSystem::getSharedInstance);
        identifier = ObjectIdentifier.of("hiveCatalog", "default", "test");
    }

    private void createFile(java.nio.file.Path parent, String path, String... files) throws IOException {
        java.nio.file.Path dir = Files.createDirectories(Paths.get(parent.toString(), path));
        for (String file : files) {
            Files.createFile(dir.resolve(file));
        }
    }

    public static class TestMetaStoreFactory implements TableMetaStoreFactory {

        private static final long serialVersionUID = 1L;

        private final Path outputPath;

        public TestMetaStoreFactory(Path outputPath) {
            this.outputPath = outputPath;
        }

        @Override
        public TableMetaStore createTableMetaStore() {
            return new TableMetaStore() {

                @Override
                public Path getLocationPath() {
                    return outputPath;
                }

                @Override
                public Optional<Path> getPartition(LinkedHashMap<String, String> partSpec) {
                    return Optional.empty();
                }

                @Override
                public void createOrAlterPartition(LinkedHashMap<String, String> partitionSpec, Path partitionPath) throws Exception {
                }

                @Override
                public void close() {
                }
            };
        }
    }

    @Test
    void testPartition_1() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=0/f1")).exists();
    }

    @Test
    void testPartition_2() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=0/f2")).exists();
    }

    @Test
    void testPartition_3() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=0/f3")).exists();
    }

    @Test
    void testPartition_4() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=0/" + SUCCESS_FILE_NAME)).exists();
    }

    @Test
    void testPartition_5() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=1/f4")).exists();
    }

    @Test
    void testPartition_6() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=1/" + SUCCESS_FILE_NAME)).exists();
    }

    @Test
    void testPartition_7() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=0/f1")).exists();
    }

    @Test
    void testPartition_8() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=0/f2")).exists();
    }

    @Test
    void testPartition_9() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=0/f3")).exists();
    }

    @Test
    void testPartition_10() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=0/" + SUCCESS_FILE_NAME)).exists();
    }

    @Test
    void testPartition_11() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=1/f5")).exists();
    }

    @Test
    void testPartition_12() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=1/" + SUCCESS_FILE_NAME)).exists();
    }

    @Test
    void testPartition_13() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=1/f5")).exists();
    }

    @Test
    void testPartition_14() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=1/f6")).exists();
    }

    @Test
    void testPartition_15() throws Exception {
        assertThat(new File(outputPath.toFile(), "p1=0/p2=1/" + SUCCESS_FILE_NAME)).exists();
    }

    @Test
    void testNotPartition_1() throws Exception {
        assertThat(new File(outputPath.toFile(), "f1")).exists();
    }

    @Test
    void testNotPartition_2() throws Exception {
        assertThat(new File(outputPath.toFile(), "f2")).exists();
    }

    @Test
    void testNotPartition_3() throws Exception {
        assertThat(new File(outputPath.toFile(), "f3")).exists();
    }

    @Test
    void testNotPartition_4() throws Exception {
        assertThat(new File(outputPath.toFile(), SUCCESS_FILE_NAME)).exists();
    }

    @Test
    void testNotPartition_5() throws Exception {
        assertThat(new File(outputPath.toFile(), "f4")).exists();
    }

    @Test
    void testNotPartition_6() throws Exception {
        assertThat(new File(outputPath.toFile(), SUCCESS_FILE_NAME)).exists();
    }

    @Test
    void testNotPartition_7() throws Exception {
        assertThat(new File(outputPath.toFile(), "f4")).exists();
    }

    @Test
    void testNotPartition_8() throws Exception {
        assertThat(new File(outputPath.toFile(), "f5")).exists();
    }

    @Test
    void testNotPartition_9() throws Exception {
        assertThat(new File(outputPath.toFile(), SUCCESS_FILE_NAME)).exists();
    }
}
