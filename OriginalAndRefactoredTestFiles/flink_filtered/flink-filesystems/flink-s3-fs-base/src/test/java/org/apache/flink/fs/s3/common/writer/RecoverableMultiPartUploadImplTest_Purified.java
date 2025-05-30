package org.apache.flink.fs.s3.common.writer;

import org.apache.flink.core.fs.RefCountedBufferingFileStream;
import org.apache.flink.core.fs.RefCountedFileWithStream;
import org.apache.flink.util.IOUtils;
import org.apache.flink.util.MathUtils;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.UploadPartResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecoverableMultiPartUploadImplTest_Purified {

    private static final int BUFFER_SIZE = 10;

    private static final String TEST_OBJECT_NAME = "TEST-OBJECT";

    @TempDir
    File temporaryFolder;

    private StubMultiPartUploader stubMultiPartUploader;

    private RecoverableMultiPartUploadImpl multiPartUploadUnderTest;

    @BeforeEach
    void before() throws IOException {
        stubMultiPartUploader = new StubMultiPartUploader();
        multiPartUploadUnderTest = RecoverableMultiPartUploadImpl.newUpload(stubMultiPartUploader, new MainThreadExecutor(), TEST_OBJECT_NAME);
    }

    private static void assertThatHasMultiPartUploadWithPart(StubMultiPartUploader actual, byte[] content, int partNo) {
        TestUploadPartResult expectedCompletePart = createUploadPartResult(TEST_OBJECT_NAME, partNo, content);
        assertThat(actual.getCompletePartsUploaded()).contains(expectedCompletePart);
    }

    private static void assertThatHasUploadedObject(StubMultiPartUploader actual, byte[] content) {
        TestPutObjectResult expectedIncompletePart = createPutObjectResult(TEST_OBJECT_NAME, content);
        assertThat(actual.getIncompletePartsUploaded()).contains(expectedIncompletePart);
    }

    private static void assertThatIsEqualTo(S3Recoverable actualRecoverable, byte[] incompletePart, byte[]... completeParts) {
        S3Recoverable expectedRecoverable = createS3Recoverable(incompletePart, completeParts);
        assertThat(actualRecoverable.getObjectName()).isEqualTo(expectedRecoverable.getObjectName());
        assertThat(actualRecoverable.uploadId()).isEqualTo(expectedRecoverable.uploadId());
        assertThat(actualRecoverable.numBytesInParts()).isEqualTo(expectedRecoverable.numBytesInParts());
        assertThat(actualRecoverable.incompleteObjectLength()).isEqualTo(expectedRecoverable.incompleteObjectLength());
        assertThat(actualRecoverable.parts().stream().map(PartETag::getETag).toArray()).isEqualTo(expectedRecoverable.parts().stream().map(PartETag::getETag).toArray());
    }

    private static byte[] bytesOf(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    private static S3Recoverable createS3Recoverable(byte[] incompletePart, byte[]... completeParts) {
        final List<PartETag> eTags = new ArrayList<>();
        int index = 1;
        long bytesInPart = 0L;
        for (byte[] part : completeParts) {
            eTags.add(new PartETag(index, createETag(TEST_OBJECT_NAME, index)));
            bytesInPart += part.length;
            index++;
        }
        return new S3Recoverable(TEST_OBJECT_NAME, createMPUploadId(TEST_OBJECT_NAME), eTags, bytesInPart, "IGNORED-DUE-TO-RANDOMNESS", (long) incompletePart.length);
    }

    private static RecoverableMultiPartUploadImplTest.TestPutObjectResult createPutObjectResult(String key, byte[] content) {
        final RecoverableMultiPartUploadImplTest.TestPutObjectResult result = new RecoverableMultiPartUploadImplTest.TestPutObjectResult();
        result.setETag(createETag(key, -1));
        result.setContent(content);
        return result;
    }

    private static RecoverableMultiPartUploadImplTest.TestUploadPartResult createUploadPartResult(String key, int number, byte[] payload) {
        final RecoverableMultiPartUploadImplTest.TestUploadPartResult result = new RecoverableMultiPartUploadImplTest.TestUploadPartResult();
        result.setETag(createETag(key, number));
        result.setPartNumber(number);
        result.setContent(payload);
        return result;
    }

    private static String createMPUploadId(String key) {
        return "MPU-" + key;
    }

    private static String createETag(String key, int partNo) {
        return "ETAG-" + key + '-' + partNo;
    }

    private S3Recoverable uploadObject(byte[] content) throws IOException {
        final RefCountedBufferingFileStream incompletePartFile = writeContent(content);
        incompletePartFile.flush();
        return multiPartUploadUnderTest.snapshotAndGetRecoverable(incompletePartFile);
    }

    private void uploadPart(final byte[] content) throws IOException {
        RefCountedBufferingFileStream partFile = writeContent(content);
        partFile.close();
        multiPartUploadUnderTest.uploadPart(partFile);
    }

    private RefCountedBufferingFileStream writeContent(byte[] content) throws IOException {
        final File newFile = new File(temporaryFolder, ".tmp_" + UUID.randomUUID());
        final OutputStream out = Files.newOutputStream(newFile.toPath(), StandardOpenOption.CREATE_NEW);
        final RefCountedBufferingFileStream testStream = new RefCountedBufferingFileStream(RefCountedFileWithStream.newFile(newFile, out), BUFFER_SIZE);
        testStream.write(content, 0, content.length);
        return testStream;
    }

    private static class MainThreadExecutor implements Executor {

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private static class StubMultiPartUploader implements S3AccessHelper {

        private final List<RecoverableMultiPartUploadImplTest.TestUploadPartResult> completePartsUploaded = new ArrayList<>();

        private final List<RecoverableMultiPartUploadImplTest.TestPutObjectResult> incompletePartsUploaded = new ArrayList<>();

        List<RecoverableMultiPartUploadImplTest.TestUploadPartResult> getCompletePartsUploaded() {
            return completePartsUploaded;
        }

        List<RecoverableMultiPartUploadImplTest.TestPutObjectResult> getIncompletePartsUploaded() {
            return incompletePartsUploaded;
        }

        @Override
        public String startMultiPartUpload(String key) throws IOException {
            return createMPUploadId(key);
        }

        @Override
        public UploadPartResult uploadPart(String key, String uploadId, int partNumber, File inputFile, long length) throws IOException {
            final byte[] content = getFileContentBytes(inputFile, MathUtils.checkedDownCast(length));
            return storeAndGetUploadPartResult(key, partNumber, content);
        }

        @Override
        public PutObjectResult putObject(String key, File inputFile) throws IOException {
            final byte[] content = getFileContentBytes(inputFile, MathUtils.checkedDownCast(inputFile.length()));
            return storeAndGetPutObjectResult(key, content);
        }

        @Override
        public boolean deleteObject(String key) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getObject(String key, File targetLocation) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public CompleteMultipartUploadResult commitMultiPartUpload(String key, String uploadId, List<PartETag> partETags, long length, AtomicInteger errorCount) throws IOException {
            return null;
        }

        @Override
        public ObjectMetadata getObjectMetadata(String key) throws IOException {
            throw new UnsupportedOperationException();
        }

        private byte[] getFileContentBytes(File file, int length) throws IOException {
            final byte[] content = new byte[length];
            IOUtils.readFully(new FileInputStream(file), content, 0, length);
            return content;
        }

        private RecoverableMultiPartUploadImplTest.TestUploadPartResult storeAndGetUploadPartResult(String key, int number, byte[] payload) {
            final RecoverableMultiPartUploadImplTest.TestUploadPartResult result = createUploadPartResult(key, number, payload);
            completePartsUploaded.add(result);
            return result;
        }

        private RecoverableMultiPartUploadImplTest.TestPutObjectResult storeAndGetPutObjectResult(String key, byte[] payload) {
            final RecoverableMultiPartUploadImplTest.TestPutObjectResult result = createPutObjectResult(key, payload);
            incompletePartsUploaded.add(result);
            return result;
        }
    }

    private static class TestPutObjectResult extends PutObjectResult {

        private static final long serialVersionUID = 1L;

        private byte[] content;

        void setContent(byte[] payload) {
            this.content = payload;
        }

        public byte[] getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final TestPutObjectResult that = (TestPutObjectResult) o;
            return Arrays.equals(getContent(), that.getContent());
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(getContent());
        }

        @Override
        public String toString() {
            return '{' + " eTag=" + getETag() + ", payload=" + Arrays.toString(content) + '}';
        }
    }

    private static class TestUploadPartResult extends UploadPartResult {

        private static final long serialVersionUID = 1L;

        private byte[] content;

        void setContent(byte[] content) {
            this.content = content;
        }

        public byte[] getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final TestUploadPartResult that = (TestUploadPartResult) o;
            return getETag().equals(that.getETag()) && getPartNumber() == that.getPartNumber() && Arrays.equals(content, that.content);
        }

        @Override
        public int hashCode() {
            return 31 * Objects.hash(getETag(), getPartNumber()) + Arrays.hashCode(getContent());
        }

        @Override
        public String toString() {
            return '{' + "etag=" + getETag() + ", partNo=" + getPartNumber() + ", content=" + Arrays.toString(content) + '}';
        }
    }

    @Test
    void multiplePartAndObjectUploadsShouldBeIncluded_1() throws IOException {
        final byte[] firstCompletePart = bytesOf("hello world");
        uploadPart(firstCompletePart);
        assertThatHasMultiPartUploadWithPart(stubMultiPartUploader, firstCompletePart, 1);
    }

    @Test
    void multiplePartAndObjectUploadsShouldBeIncluded_2() throws IOException {
        final byte[] secondCompletePart = bytesOf("hello again");
        uploadPart(secondCompletePart);
        assertThatHasMultiPartUploadWithPart(stubMultiPartUploader, secondCompletePart, 2);
    }

    @Test
    void multiplePartAndObjectUploadsShouldBeIncluded_3() throws IOException {
        final byte[] thirdIncompletePart = bytesOf("!!!");
        uploadObject(thirdIncompletePart);
        assertThatHasUploadedObject(stubMultiPartUploader, thirdIncompletePart);
    }
}
