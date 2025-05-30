package org.apache.flink.runtime.blob;

import org.apache.flink.api.common.JobID;
import org.apache.flink.core.fs.FSDataOutputStream;
import org.apache.flink.core.fs.local.LocalDataOutputStream;
import org.apache.flink.runtime.state.filesystem.TestFs;
import org.apache.flink.testutils.TestFileSystem;
import org.apache.flink.util.Preconditions;
import org.apache.flink.util.function.FunctionWithException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicReference;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileSystemBlobStoreTest_Purified {

    private FileSystemBlobStore testInstance;

    private Path storagePath;

    @BeforeEach
    void createTestInstance(@TempDir Path storagePath) throws IOException {
        this.testInstance = new FileSystemBlobStore(new TestFileSystem(), storagePath.toString());
        this.storagePath = storagePath;
    }

    @AfterEach
    void finalizeTestInstance() throws IOException {
        testInstance.close();
    }

    private Path createTemporaryFileWithContent(String operationLabel) throws IOException {
        final String actualContent = String.format("Content for testing the %s operation", operationLabel);
        final Path temporaryFile = Files.createTempFile(String.format("filesystemblobstoretest-%s-", operationLabel), "");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temporaryFile.toAbsolutePath().toString()))) {
            writer.write(actualContent);
        }
        return temporaryFile;
    }

    private Path getBlobDirectoryPath() {
        return storagePath.resolve(FileSystemBlobStore.BLOB_PATH_NAME);
    }

    private Path getPath(JobID jobId) {
        return getBlobDirectoryPath().resolve(String.format("job_%s", jobId));
    }

    private Path getPath(JobID jobId, BlobKey blobKey) {
        return getPath(jobId).resolve(String.format("blob_%s", blobKey));
    }

    private BlobKey createPermanentBlobKeyFromFile(Path path) throws IOException {
        Preconditions.checkArgument(!Files.isDirectory(path));
        Preconditions.checkArgument(Files.exists(path));
        MessageDigest md = BlobUtils.createMessageDigest();
        try (InputStream is = Files.newInputStream(path.toFile().toPath())) {
            final byte[] buf = new byte[1024];
            int bytesRead = is.read(buf);
            while (bytesRead >= 0) {
                md.update(buf, 0, bytesRead);
                bytesRead = is.read(buf);
            }
            return BlobKey.createKey(BlobKey.BlobType.PERMANENT_BLOB, md.digest());
        }
    }

    private static class TestingLocalDataOutputStream extends LocalDataOutputStream {

        private boolean hasSyncBeenCalled = false;

        private TestingLocalDataOutputStream(File file) throws IOException {
            super(file);
        }

        @Override
        public void sync() throws IOException {
            hasSyncBeenCalled = true;
            super.sync();
        }

        public boolean hasSyncBeenCalled() {
            return hasSyncBeenCalled;
        }
    }

    @Test
    void testSuccessfulPut_1() throws IOException {
        assertThat(getBlobDirectoryPath()).doesNotExist();
    }

    @Test
    void testSuccessfulPut_2_testMerged_2() throws IOException {
        hasSameTextualContentAs(temporaryFile);
        final Path temporaryFile = createTemporaryFileWithContent("put");
        final JobID jobId = new JobID();
        final BlobKey blobKey = createPermanentBlobKeyFromFile(temporaryFile);
        final boolean successfullyWritten = testInstance.put(temporaryFile.toFile(), jobId, blobKey);
        assertThat(successfullyWritten).isTrue();
    }
}
