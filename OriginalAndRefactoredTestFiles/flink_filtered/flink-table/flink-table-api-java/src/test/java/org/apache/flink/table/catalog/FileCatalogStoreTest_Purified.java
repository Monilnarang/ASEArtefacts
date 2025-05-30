package org.apache.flink.table.catalog;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.catalog.exceptions.CatalogException;
import org.apache.flink.util.OperatingSystem;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;

class FileCatalogStoreTest_Purified {

    private static final String CATALOG_STORE_DIR_NAME = "dummy-catalog-store";

    private static final String DUMMY = "dummy";

    private static final CatalogDescriptor DUMMY_CATALOG;

    static {
        Configuration conf = new Configuration();
        conf.set(CommonCatalogOptions.CATALOG_TYPE, DUMMY);
        conf.set(GenericInMemoryCatalogFactoryOptions.DEFAULT_DATABASE, "dummy_db");
        DUMMY_CATALOG = CatalogDescriptor.of(DUMMY, conf);
    }

    @TempDir
    private Path tempDir;

    private void assertCatalogStoreNotOpened(ThrowableAssert.ThrowingCallable shouldRaiseThrowable) {
        assertThatThrownBy(shouldRaiseThrowable).isInstanceOf(IllegalStateException.class).hasMessageContaining("CatalogStore is not opened yet.");
    }

    private CatalogStore initCatalogStore() {
        Path catalogStorePath = tempDir.resolve(CATALOG_STORE_DIR_NAME);
        return new FileCatalogStore(catalogStorePath.toString());
    }

    private File getCatalogFile() {
        return tempDir.resolve(CATALOG_STORE_DIR_NAME).resolve(DUMMY + FileCatalogStore.FILE_EXTENSION).toFile();
    }

    @Test
    void testRemoveExisting_1_testMerged_1() {
        CatalogStore catalogStore = initCatalogStore();
        catalogStore.open();
        catalogStore.storeCatalog(DUMMY, DUMMY_CATALOG);
        assertThat(catalogStore.listCatalogs().size()).isEqualTo(1);
        catalogStore.removeCatalog(DUMMY, false);
        assertThat(catalogStore.listCatalogs().size()).isEqualTo(0);
        assertThat(catalogStore.contains(DUMMY)).isFalse();
    }

    @Test
    void testRemoveExisting_4() {
        File catalog = getCatalogFile();
        assertThat(catalog.exists()).isFalse();
    }
}
