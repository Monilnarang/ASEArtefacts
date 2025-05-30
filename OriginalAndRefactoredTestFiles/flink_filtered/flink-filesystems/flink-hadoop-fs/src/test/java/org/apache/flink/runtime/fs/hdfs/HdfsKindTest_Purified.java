package org.apache.flink.runtime.fs.hdfs;

import org.apache.flink.core.fs.FileSystemKind;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class HdfsKindTest_Purified {

    @Test
    void testS3fileSystemSchemes_1() {
        assertThat(HadoopFileSystem.getKindForScheme("s3")).isEqualTo(FileSystemKind.OBJECT_STORE);
    }

    @Test
    void testS3fileSystemSchemes_2() {
        assertThat(HadoopFileSystem.getKindForScheme("s3n")).isEqualTo(FileSystemKind.OBJECT_STORE);
    }

    @Test
    void testS3fileSystemSchemes_3() {
        assertThat(HadoopFileSystem.getKindForScheme("s3a")).isEqualTo(FileSystemKind.OBJECT_STORE);
    }

    @Test
    void testS3fileSystemSchemes_4() {
        assertThat(HadoopFileSystem.getKindForScheme("EMRFS")).isEqualTo(FileSystemKind.OBJECT_STORE);
    }
}
