package org.apache.flink.kubernetes.artifact;

import org.apache.flink.client.cli.ArtifactFetchOptions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.PipelineOptions;
import org.apache.flink.kubernetes.configuration.KubernetesConfigOptions;
import org.apache.flink.kubernetes.utils.KubernetesUtils;
import org.apache.flink.testutils.TestingUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultKubernetesArtifactUploaderTest_Purified {

    private final DefaultKubernetesArtifactUploader artifactUploader = new DefaultKubernetesArtifactUploader();

    @TempDir
    private Path tmpDir;

    private Configuration config;

    private DummyFs dummyFs;

    @BeforeEach
    void setup() throws IOException {
        config = new Configuration();
        config.set(KubernetesConfigOptions.LOCAL_UPLOAD_ENABLED, true);
        config.set(KubernetesConfigOptions.LOCAL_UPLOAD_TARGET, getTargetDirUri());
        dummyFs = (DummyFs) new org.apache.flink.core.fs.Path(getTargetDirUri()).getFileSystem();
        dummyFs.resetCallCounters();
    }

    private String getTargetDirUri() {
        return "dummyfs://" + tmpDir;
    }

    private File getFlinkKubernetesJar() throws IOException {
        return TestingUtils.getFileFromTargetDir(DefaultKubernetesArtifactUploader.class, p -> org.apache.flink.util.FileUtils.isJarFile(p) && p.toFile().getName().startsWith("flink-kubernetes"));
    }

    private void assertJobJarUri(String filename) {
        String expectedUri = "dummyfs:" + tmpDir.resolve(filename);
        List<String> result = config.get(PipelineOptions.JARS);
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(expectedUri);
    }

    @Test
    void testUploadNoOverwrite_1() throws Exception {
        assertThat(dummyFs.getExistsCallCounter()).isOne();
    }

    @Test
    void testUploadNoOverwrite_2() throws Exception {
        assertThat(dummyFs.getCreateCallCounter()).isZero();
    }

    @Test
    void testUploadOverwrite_1() throws Exception {
        assertThat(dummyFs.getExistsCallCounter()).isEqualTo(2);
    }

    @Test
    void testUploadOverwrite_2() throws Exception {
        assertThat(dummyFs.getCreateCallCounter()).isOne();
    }
}
