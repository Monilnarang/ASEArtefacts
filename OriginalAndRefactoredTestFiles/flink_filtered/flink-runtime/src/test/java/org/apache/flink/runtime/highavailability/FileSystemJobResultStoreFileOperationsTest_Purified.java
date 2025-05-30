package org.apache.flink.runtime.highavailability;

import org.apache.flink.api.common.JobID;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.testutils.FlinkAssertions;
import org.apache.flink.runtime.jobmaster.JobResult;
import org.apache.flink.util.FileUtils;
import org.apache.flink.util.TestLoggerExtension;
import org.apache.flink.util.concurrent.ManuallyTriggeredScheduledExecutor;
import org.apache.flink.util.jackson.JacksonMapperFactory;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.apache.flink.runtime.highavailability.JobResultStoreContractTest.DUMMY_JOB_RESULT_ENTRY;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestLoggerExtension.class)
public class FileSystemJobResultStoreFileOperationsTest_Purified {

    private static final ObjectMapper MAPPER = JacksonMapperFactory.createObjectMapper();

    private final ManuallyTriggeredScheduledExecutor manuallyTriggeredExecutor = new ManuallyTriggeredScheduledExecutor();

    private FileSystemJobResultStore fileSystemJobResultStore;

    @TempDir
    File temporaryFolder;

    private Path basePath;

    @BeforeEach
    public void setupTest() throws IOException {
        basePath = new Path(temporaryFolder.toURI());
        fileSystemJobResultStore = new FileSystemJobResultStore(basePath.getFileSystem(), basePath, false, manuallyTriggeredExecutor);
    }

    private static String stripSucceedingSlash(Path path) {
        final String uriStr = path.toUri().toString();
        if (uriStr.charAt(uriStr.length() - 1) == '/') {
            return uriStr.substring(0, uriStr.length() - 1);
        }
        return uriStr;
    }

    private List<JobID> getCleanResultIdsFromFileSystem() throws IOException {
        final List<JobID> cleanResults = new ArrayList<>();
        final File[] cleanFiles = temporaryFolder.listFiles((dir, name) -> !FileSystemJobResultStore.hasValidDirtyJobResultStoreEntryExtension(name));
        assert cleanFiles != null;
        for (File cleanFile : cleanFiles) {
            final FileSystemJobResultStore.JsonJobResultEntry entry = MAPPER.readValue(cleanFile, FileSystemJobResultStore.JsonJobResultEntry.class);
            cleanResults.add(entry.getJobResult().getJobId());
        }
        return cleanResults;
    }

    private File expectedDirtyFile(JobResultEntry entry) {
        return new File(temporaryFolder.toURI().getPath(), entry.getJobId().toString() + FileSystemJobResultStore.DIRTY_FILE_EXTENSION);
    }

    private File expectedCleanFile(JobResultEntry entry) {
        return new File(temporaryFolder.toURI().getPath(), entry.getJobId().toString() + FileSystemJobResultStore.FILE_EXTENSION);
    }

    @Test
    public void testStoreDirtyJobResultCreatesFile_1_testMerged_1() throws Exception {
        CompletableFuture<Void> dirtyResultAsync = fileSystemJobResultStore.createDirtyResultAsync(DUMMY_JOB_RESULT_ENTRY);
        assertThat(expectedDirtyFile(DUMMY_JOB_RESULT_ENTRY)).doesNotExist();
        FlinkAssertions.assertThatFuture(dirtyResultAsync).eventuallySucceeds();
    }

    @Test
    public void testStoreDirtyJobResultCreatesFile_3() throws Exception {
        assertThat(getCleanResultIdsFromFileSystem()).isEmpty();
    }
}
