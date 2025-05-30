package org.apache.hadoop.fs.slive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.slive.ArgumentParser.ParsedOutput;
import org.apache.hadoop.fs.slive.Constants.OperationType;
import org.apache.hadoop.fs.slive.DataVerifier.VerifyOutput;
import org.apache.hadoop.fs.slive.DataWriter.GenerateOutput;
import org.apache.hadoop.util.ToolRunner;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSlive_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestSlive.class);

    private static final Random rnd = new Random(1L);

    private static final String TEST_DATA_PROP = "test.build.data";

    private static Configuration getBaseConfig() {
        Configuration conf = new Configuration();
        return conf;
    }

    private static File getWriteLoc() {
        String writeLoc = System.getProperty(TEST_DATA_PROP, "build/test/data/");
        File writeDir = new File(writeLoc, "slive");
        writeDir.mkdirs();
        return writeDir;
    }

    private static File getFlowLocation() {
        return new File(getWriteLoc(), "flow");
    }

    private static File getTestDir() {
        return new File(getWriteLoc(), "slivedir");
    }

    private static File getTestFile() {
        return new File(getWriteLoc(), "slivefile");
    }

    private static File getTestRenameFile() {
        return new File(getWriteLoc(), "slivefile1");
    }

    private static File getResultFile() {
        return new File(getWriteLoc(), "sliveresfile");
    }

    private static File getImaginaryFile() {
        return new File(getWriteLoc(), "slivenofile");
    }

    private String[] getTestArgs(boolean sleep) {
        List<String> args = new LinkedList<String>();
        {
            args.add("-" + ConfigOption.WRITE_SIZE.getOpt());
            args.add("1M,2M");
            args.add("-" + ConfigOption.OPS.getOpt());
            args.add(Constants.OperationType.values().length + "");
            args.add("-" + ConfigOption.MAPS.getOpt());
            args.add("2");
            args.add("-" + ConfigOption.REDUCES.getOpt());
            args.add("2");
            args.add("-" + ConfigOption.APPEND_SIZE.getOpt());
            args.add("1M,2M");
            args.add("-" + ConfigOption.BLOCK_SIZE.getOpt());
            args.add("1M,2M");
            args.add("-" + ConfigOption.REPLICATION_AM.getOpt());
            args.add("1,1");
            if (sleep) {
                args.add("-" + ConfigOption.SLEEP_TIME.getOpt());
                args.add("10,10");
            }
            args.add("-" + ConfigOption.RESULT_FILE.getOpt());
            args.add(getResultFile().toString());
            args.add("-" + ConfigOption.BASE_DIR.getOpt());
            args.add(getFlowLocation().toString());
            args.add("-" + ConfigOption.DURATION.getOpt());
            args.add("10");
            args.add("-" + ConfigOption.DIR_SIZE.getOpt());
            args.add("10");
            args.add("-" + ConfigOption.FILES.getOpt());
            args.add("10");
            args.add("-" + ConfigOption.TRUNCATE_SIZE.getOpt());
            args.add("0,1M");
        }
        return args.toArray(new String[args.size()]);
    }

    private ConfigExtractor getTestConfig(boolean sleep) throws Exception {
        ArgumentParser parser = new ArgumentParser(getTestArgs(sleep));
        ParsedOutput out = parser.parse();
        assertTrue(!out.shouldOutputHelp());
        ConfigMerger merge = new ConfigMerger();
        Configuration cfg = merge.getMerged(out, getBaseConfig());
        ConfigExtractor extractor = new ConfigExtractor(cfg);
        return extractor;
    }

    @Before
    public void ensureDeleted() throws Exception {
        rDelete(getTestFile());
        rDelete(getTestDir());
        rDelete(getTestRenameFile());
        rDelete(getResultFile());
        rDelete(getFlowLocation());
        rDelete(getImaginaryFile());
    }

    private void rDelete(File place) throws Exception {
        if (place.isFile()) {
            LOG.info("Deleting file " + place);
            assertTrue(place.delete());
        } else if (place.isDirectory()) {
            deleteDir(place);
        }
    }

    private void deleteDir(File dir) throws Exception {
        String[] fns = dir.list();
        for (String afn : fns) {
            File fn = new File(dir, afn);
            rDelete(fn);
        }
        LOG.info("Deleting directory " + dir);
        assertTrue(dir.delete());
    }

    private void runOperationBad(ConfigExtractor cfg, Operation op) throws Exception {
        FileSystem fs = FileSystem.get(cfg.getConfig());
        List<OperationOutput> data = op.run(fs);
        assertTrue(!data.isEmpty());
        boolean foundFail = false;
        for (OperationOutput d : data) {
            if (d.getMeasurementType().equals(ReportWriter.FAILURES)) {
                foundFail = true;
            }
            if (d.getMeasurementType().equals(ReportWriter.NOT_FOUND)) {
                foundFail = true;
            }
        }
        assertTrue(foundFail);
    }

    private void runOperationOk(ConfigExtractor cfg, Operation op, boolean checkOk) throws Exception {
        FileSystem fs = FileSystem.get(cfg.getConfig());
        List<OperationOutput> data = op.run(fs);
        assertTrue(!data.isEmpty());
        if (checkOk) {
            boolean foundSuc = false;
            boolean foundOpCount = false;
            boolean foundTime = false;
            for (OperationOutput d : data) {
                assertTrue(!d.getMeasurementType().equals(ReportWriter.FAILURES));
                if (d.getMeasurementType().equals(ReportWriter.SUCCESSES)) {
                    foundSuc = true;
                }
                if (d.getMeasurementType().equals(ReportWriter.OP_COUNT)) {
                    foundOpCount = true;
                }
                if (d.getMeasurementType().equals(ReportWriter.OK_TIME_TAKEN)) {
                    foundTime = true;
                }
            }
            assertTrue(foundSuc);
            assertTrue(foundOpCount);
            assertTrue(foundTime);
        }
    }

    @Test
    public void testMRFlow_1() throws Exception {
        SliveTest s = new SliveTest(getBaseConfig());
        int ec = ToolRunner.run(s, getTestArgs(false));
        assertTrue(ec == 0);
    }

    @Test
    public void testMRFlow_2() throws Exception {
        ConfigExtractor extractor = getTestConfig(false);
        String resFile = extractor.getResultFile();
        File fn = new File(resFile);
        assertTrue(fn.exists());
    }
}
