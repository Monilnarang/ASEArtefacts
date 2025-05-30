package org.apache.hadoop.mapred;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.TaskInputOutputContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.mapreduce.server.jobtracker.JTConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation")
public class TestMRWithDistributedCache_Purified {

    private static Path TEST_ROOT_DIR = new Path(System.getProperty("test.build.data", "/tmp"));

    private static File symlinkFile = new File("distributed.first.symlink");

    private static File expectedAbsentSymlinkFile = new File("distributed.second.jar");

    private static Configuration conf = new Configuration();

    private static FileSystem localFs;

    static {
        try {
            localFs = FileSystem.getLocal(conf);
        } catch (IOException io) {
            throw new RuntimeException("problem getting local fs", io);
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(TestMRWithDistributedCache.class);

    private static class DistributedCacheChecker {

        public void setup(TaskInputOutputContext<?, ?, ?, ?> context) throws IOException {
            Configuration conf = context.getConfiguration();
            Path[] localFiles = context.getLocalCacheFiles();
            URI[] files = context.getCacheFiles();
            Path[] localArchives = context.getLocalCacheArchives();
            URI[] archives = context.getCacheArchives();
            FileSystem fs = LocalFileSystem.get(conf);
            assertEquals(2, localFiles.length);
            assertEquals(2, localArchives.length);
            assertEquals(2, files.length);
            assertEquals(2, archives.length);
            assertTrue(files[0].getPath().endsWith("distributed.first"));
            assertTrue(files[1].getPath().endsWith("distributed.second.jar"));
            assertEquals(1, fs.getFileStatus(localFiles[0]).getLen());
            assertTrue(fs.getFileStatus(localFiles[1]).getLen() > 1);
            assertTrue(fs.exists(new Path(localArchives[0], "distributed.jar.inside3")));
            assertTrue(fs.exists(new Path(localArchives[1], "distributed.jar.inside4")));
            LOG.info("Java Classpath: " + System.getProperty("java.class.path"));
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            assertNotNull(cl.getResource("distributed.jar.inside2"));
            assertNotNull(cl.getResource("distributed.jar.inside3"));
            assertNull(cl.getResource("distributed.jar.inside4"));
            assertTrue(symlinkFile.exists(), "symlink distributed.first.symlink doesn't exist");
            assertEquals(1, symlinkFile.length(), "symlink distributed.first.symlink length not 1");
            assertTrue(expectedAbsentSymlinkFile.exists(), "second file should be symlinked too");
        }
    }

    public static class DistributedCacheCheckerMapper extends Mapper<LongWritable, Text, NullWritable, NullWritable> {

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            new DistributedCacheChecker().setup(context);
        }
    }

    public static class DistributedCacheCheckerReducer extends Reducer<LongWritable, Text, NullWritable, NullWritable> {

        @Override
        public void setup(Context context) throws IOException {
            new DistributedCacheChecker().setup(context);
        }
    }

    private Path createTempFile(String filename, String contents) throws IOException {
        Path path = new Path(TEST_ROOT_DIR, filename);
        FSDataOutputStream os = localFs.create(path);
        os.writeBytes(contents);
        os.close();
        return path;
    }

    private Path makeJar(Path p, int index) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(new File(p.toString()));
        JarOutputStream jos = new JarOutputStream(fos);
        ZipEntry ze = new ZipEntry("distributed.jar.inside" + index);
        jos.putNextEntry(ze);
        jos.write(("inside the jar!" + index).getBytes());
        jos.closeEntry();
        jos.close();
        return p;
    }

    @Test
    @Timeout(10000)
    public void testDeprecatedFunctions_1_testMerged_1() throws Exception {
        DistributedCache.addLocalArchives(conf, "Test Local Archives 1");
        assertEquals("Test Local Archives 1", conf.get(DistributedCache.CACHE_LOCALARCHIVES));
        assertEquals(1, JobContextImpl.getLocalCacheArchives(conf).length);
        assertEquals("Test Local Archives 1", JobContextImpl.getLocalCacheArchives(conf)[0].getName());
        DistributedCache.addLocalArchives(conf, "Test Local Archives 2");
        assertEquals("Test Local Archives 1,Test Local Archives 2", conf.get(DistributedCache.CACHE_LOCALARCHIVES));
        assertEquals(2, JobContextImpl.getLocalCacheArchives(conf).length);
        assertEquals("Test Local Archives 2", JobContextImpl.getLocalCacheArchives(conf)[1].getName());
        DistributedCache.setLocalArchives(conf, "Test Local Archives 3");
        assertEquals("Test Local Archives 3", conf.get(DistributedCache.CACHE_LOCALARCHIVES));
        assertEquals("Test Local Archives 3", JobContextImpl.getLocalCacheArchives(conf)[0].getName());
        DistributedCache.addLocalFiles(conf, "Test Local Files 1");
        assertEquals("Test Local Files 1", conf.get(DistributedCache.CACHE_LOCALFILES));
        assertEquals(1, JobContextImpl.getLocalCacheFiles(conf).length);
        assertEquals("Test Local Files 1", JobContextImpl.getLocalCacheFiles(conf)[0].getName());
        DistributedCache.addLocalFiles(conf, "Test Local Files 2");
        assertEquals("Test Local Files 1,Test Local Files 2", conf.get(DistributedCache.CACHE_LOCALFILES));
        assertEquals(2, JobContextImpl.getLocalCacheFiles(conf).length);
        assertEquals("Test Local Files 2", JobContextImpl.getLocalCacheFiles(conf)[1].getName());
        DistributedCache.setLocalFiles(conf, "Test Local Files 3");
        assertEquals("Test Local Files 3", conf.get(DistributedCache.CACHE_LOCALFILES));
        assertEquals("Test Local Files 3", JobContextImpl.getLocalCacheFiles(conf)[0].getName());
        DistributedCache.setArchiveTimestamps(conf, "1234567890");
        assertEquals(1234567890, conf.getLong(DistributedCache.CACHE_ARCHIVES_TIMESTAMPS, 0));
        assertEquals(1, JobContextImpl.getArchiveTimestamps(conf).length);
        assertEquals(1234567890, JobContextImpl.getArchiveTimestamps(conf)[0]);
        DistributedCache.setFileTimestamps(conf, "1234567890");
        assertEquals(1234567890, conf.getLong(DistributedCache.CACHE_FILES_TIMESTAMPS, 0));
        assertEquals(1, JobContextImpl.getFileTimestamps(conf).length);
        assertEquals(1234567890, JobContextImpl.getFileTimestamps(conf)[0]);
        DistributedCache.createAllSymlink(conf, new File("Test Job Cache Dir"), new File("Test Work Dir"));
        assertNull(conf.get(DistributedCache.CACHE_SYMLINK));
        assertTrue(DistributedCache.getSymlink(conf));
        FileStatus fileStatus = DistributedCache.getFileStatus(conf, symlinkFile.toURI());
        assertNotNull(fileStatus);
        assertEquals(fileStatus.getModificationTime(), DistributedCache.getTimestamp(conf, symlinkFile.toURI()));
        assertTrue(symlinkFile.delete());
        Job.addCacheArchive(symlinkFile.toURI(), conf);
        assertEquals(symlinkFile.toURI().toString(), conf.get(DistributedCache.CACHE_ARCHIVES));
        assertEquals(1, JobContextImpl.getCacheArchives(conf).length);
        assertEquals(symlinkFile.toURI(), JobContextImpl.getCacheArchives(conf)[0]);
        Job.addCacheFile(symlinkFile.toURI(), conf);
        assertEquals(symlinkFile.toURI().toString(), conf.get(DistributedCache.CACHE_FILES));
        assertEquals(1, JobContextImpl.getCacheFiles(conf).length);
        assertEquals(symlinkFile.toURI(), JobContextImpl.getCacheFiles(conf)[0]);
    }

    @Test
    @Timeout(10000)
    public void testDeprecatedFunctions_27() throws Exception {
        assertTrue(symlinkFile.createNewFile());
    }
}
