package org.apache.flink.runtime.fs.hdfs;

import org.apache.flink.core.fs.FileSystemKind;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class HdfsKindTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testS3fileSystemSchemes_1to4")
    void testS3fileSystemSchemes_1to4(String param1) {
        assertThat(HadoopFileSystem.getKindForScheme(param1)).isEqualTo(FileSystemKind.OBJECT_STORE);
    }

    static public Stream<Arguments> Provider_testS3fileSystemSchemes_1to4() {
        return Stream.of(arguments("s3"), arguments("s3n"), arguments("s3a"), arguments("EMRFS"));
    }
}
