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

public class LocalFileExportStorageProviderTest_Purified {

    @Test
    public void testExportManifestFilePath_1() {
        Assert.assertEquals("file:/base/path1/file1", new LocalFileExportStorageProvider("/base/path1").getFilePathForManifest("file1"));
    }

    @Test
    public void testExportManifestFilePath_2() {
        Assert.assertEquals("file:/base/path1/file1", new LocalFileExportStorageProvider("/base/../base/path1").getFilePathForManifest("file1"));
    }
}
