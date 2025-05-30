package org.apache.flink.client.program;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.net.URISyntaxException;
import static org.apache.flink.client.program.PackagedProgramUtils.resolveURI;
import static org.assertj.core.api.Assertions.assertThat;

class PackagedProgramUtilsTest_Purified {

    @Test
    void testResolveURI_1() throws URISyntaxException {
        final String relativeFile = "path/of/user.jar";
        hasPath(new File(System.getProperty("user.dir"), relativeFile).getAbsolutePath());
    }

    @Test
    void testResolveURI_2() throws URISyntaxException {
        final String absoluteFile = "/path/of/user.jar";
        hasPath(absoluteFile);
    }

    @Test
    void testResolveURI_3_testMerged_3() throws URISyntaxException {
        final String fileSchemaFile = "file:///path/of/user.jar";
        assertThat(resolveURI(fileSchemaFile).getScheme()).isEqualTo("file");
        assertThat(resolveURI(fileSchemaFile)).hasToString(fileSchemaFile);
    }

    @Test
    void testResolveURI_5_testMerged_4() throws URISyntaxException {
        final String localSchemaFile = "local:///path/of/user.jar";
        assertThat(resolveURI(localSchemaFile).getScheme()).isEqualTo("local");
        assertThat(resolveURI(localSchemaFile)).hasToString(localSchemaFile);
    }
}
