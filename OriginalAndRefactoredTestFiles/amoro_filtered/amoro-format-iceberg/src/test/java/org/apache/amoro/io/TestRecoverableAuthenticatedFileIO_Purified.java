package org.apache.amoro.io;

import org.apache.amoro.BasicTableTestHelper;
import org.apache.amoro.TableFormat;
import org.apache.amoro.catalog.BasicCatalogTestHelper;
import org.apache.amoro.catalog.TableTestBase;
import org.apache.amoro.shade.guava32.com.google.common.collect.Streams;
import org.apache.amoro.table.MixedTable;
import org.apache.amoro.table.TableProperties;
import org.apache.iceberg.io.OutputFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

public class TestRecoverableAuthenticatedFileIO_Purified extends TableTestBase {

    private RecoverableHadoopFileIO recoverableHadoopFileIO;

    private AuthenticatedFileIO authenticatedFileIO;

    TableTrashManager trashManager;

    private String file1;

    private String file2;

    private String file3;

    public TestRecoverableAuthenticatedFileIO() {
        super(new BasicCatalogTestHelper(TableFormat.MIXED_ICEBERG), new BasicTableTestHelper(true, true));
    }

    @Before
    public void before() {
        MixedTable mixedTable = getMixedTable();
        trashManager = TableTrashManagers.build(mixedTable.id(), mixedTable.location(), mixedTable.properties(), (AuthenticatedHadoopFileIO) mixedTable.io());
        recoverableHadoopFileIO = new RecoverableHadoopFileIO(getTableMetaStore(), trashManager, TableProperties.TABLE_TRASH_FILE_PATTERN_DEFAULT);
        authenticatedFileIO = mixedTable.io();
        file1 = getMixedTable().location() + "/base/test/test1/test1.parquet";
        file2 = getMixedTable().location() + "/base/test/test2/test2.parquet";
        file3 = getMixedTable().location() + "/base/test/test2.parquet";
    }

    private void createFile(String path) throws IOException {
        OutputFile baseOrphanDataFile = authenticatedFileIO.newOutputFile(path);
        baseOrphanDataFile.createOrOverwrite().close();
    }

    @Test
    public void exists_1() throws IOException {
        createFile(file1);
        Assert.assertTrue(recoverableHadoopFileIO.exists(file1));
    }

    @Test
    public void exists_2() throws IOException {
        Assert.assertFalse(recoverableHadoopFileIO.exists(file2));
    }

    @Test
    public void isDirectory_1() throws IOException {
        createFile(file1);
        Assert.assertFalse(recoverableHadoopFileIO.isDirectory(file1));
    }

    @Test
    public void isDirectory_2() throws IOException {
        Assert.assertTrue(recoverableHadoopFileIO.isDirectory(getMixedTable().location()));
    }

    @Test
    public void isEmptyDirectory_1() {
        String dir = getMixedTable().location() + "/location";
        authenticatedFileIO.asFileSystemIO().makeDirectories(dir);
        Assert.assertTrue(recoverableHadoopFileIO.isEmptyDirectory(dir));
    }

    @Test
    public void isEmptyDirectory_2() {
        Assert.assertFalse(recoverableHadoopFileIO.isEmptyDirectory(getMixedTable().location()));
    }

    @Test
    public void trashFilePattern_1() {
        Assert.assertTrue(recoverableHadoopFileIO.matchTrashFilePattern(file1));
    }

    @Test
    public void trashFilePattern_2() {
        Assert.assertTrue(recoverableHadoopFileIO.matchTrashFilePattern(file2));
    }

    @Test
    public void trashFilePattern_3() {
        Assert.assertTrue(recoverableHadoopFileIO.matchTrashFilePattern(file3));
    }

    @Test
    public void trashFilePattern_4() {
        Assert.assertTrue(recoverableHadoopFileIO.matchTrashFilePattern(getMixedTable().location() + "/metadata/version-hint.text"));
    }

    @Test
    public void trashFilePattern_5() {
        Assert.assertTrue(recoverableHadoopFileIO.matchTrashFilePattern(getMixedTable().location() + "/metadata/v2.metadata.json"));
    }

    @Test
    public void trashFilePattern_6() {
        Assert.assertTrue(recoverableHadoopFileIO.matchTrashFilePattern(getMixedTable().location() + "/metadata/snap-1515213806302741636-1-85fc817e-941d-4e9a-ab41-2dbf7687bfcd.avro"));
    }

    @Test
    public void trashFilePattern_7() {
        Assert.assertTrue(recoverableHadoopFileIO.matchTrashFilePattern(getMixedTable().location() + "/metadata/3ce7600d-4853-45d0-8533-84c12a611916-m0.avro"));
    }

    @Test
    public void trashFilePattern_8() {
        Assert.assertFalse(recoverableHadoopFileIO.matchTrashFilePattern(getMixedTable().location() + "/metadata/3ce7600d-4853-45d0-8533-84c12a611916.avro"));
    }
}
