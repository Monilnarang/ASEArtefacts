package org.apache.flink.core.fs;

import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.util.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;

public abstract class FileSystemBehaviorTestSuite_Purified {

    private static final Random RND = new Random();

    private FileSystem fs;

    private Path basePath;

    protected abstract FileSystem getFileSystem() throws Exception;

    protected abstract Path getBasePath() throws Exception;

    protected abstract FileSystemKind getFileSystemKind();

    @BeforeEach
    void prepare() throws Exception {
        fs = getFileSystem();
        basePath = new Path(getBasePath(), randomName());
        fs.mkdirs(basePath);
    }

    @AfterEach
    void cleanup() throws Exception {
        fs.delete(basePath, true);
    }

    private static String randomName() {
        return StringUtils.getRandomString(RND, 16, 16, 'a', 'z');
    }

    private void createFile(Path file) throws IOException {
        try (FSDataOutputStream out = fs.create(file, WriteMode.NO_OVERWRITE)) {
            out.write(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        }
    }

    private Path createRandomFileInDirectory(Path directory) throws IOException {
        fs.mkdirs(directory);
        final Path filePath = new Path(directory, randomName());
        createFile(filePath);
        return filePath;
    }

    private void assumeNotObjectStore() {
        assumeThat(getFileSystemKind() != FileSystemKind.OBJECT_STORE).describedAs("Test does not apply to object stores").isTrue();
    }

    @Test
    void testPathAndScheme_1() throws Exception {
        assertThat(fs.getUri()).isEqualTo(getBasePath().getFileSystem().getUri());
    }

    @Test
    void testPathAndScheme_2() throws Exception {
        assertThat(fs.getUri().getScheme()).isEqualTo(getBasePath().toUri().getScheme());
    }
}
