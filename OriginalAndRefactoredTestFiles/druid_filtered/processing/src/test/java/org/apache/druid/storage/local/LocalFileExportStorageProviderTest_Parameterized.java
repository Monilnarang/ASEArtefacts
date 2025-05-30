package org.apache.druid.storage.local;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.error.DruidException;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.storage.ExportStorageProvider;
import org.apache.druid.storage.StorageConfig;
import org.apache.druid.storage.StorageConnectorModule;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LocalFileExportStorageProviderTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testExportManifestFilePath_1to2")
    public void testExportManifestFilePath_1to2(String param1, String param2, String param3) {
        Assert.assertEquals(param1, new LocalFileExportStorageProvider(param3).getFilePathForManifest(param2));
    }

    static public Stream<Arguments> Provider_testExportManifestFilePath_1to2() {
        return Stream.of(arguments("file:/base/path1/file1", "file1", "/base/path1"), arguments("file:/base/path1/file1", "file1", "/base/../base/path1"));
    }
}
