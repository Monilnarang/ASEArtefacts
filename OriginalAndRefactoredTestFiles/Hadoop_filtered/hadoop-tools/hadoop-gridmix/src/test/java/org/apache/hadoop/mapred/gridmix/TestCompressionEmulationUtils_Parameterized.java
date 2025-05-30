package org.apache.hadoop.mapred.gridmix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Utils;
import org.apache.hadoop.mapred.gridmix.CompressionEmulationUtil.RandomTextDataMapper;
import org.apache.hadoop.mapred.gridmix.GenerateData.GenSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestCompressionEmulationUtils_Parameterized {

    static class CustomInputFormat extends GenerateData.GenDataFormat {

        @Override
        public List<InputSplit> getSplits(JobContext jobCtxt) throws IOException {
            long toGen = jobCtxt.getConfiguration().getLong(GenerateData.GRIDMIX_GEN_BYTES, -1);
            if (toGen < 0) {
                throw new IOException("Invalid/missing generation bytes: " + toGen);
            }
            int totalMappersConfigured = jobCtxt.getConfiguration().getInt(MRJobConfig.NUM_MAPS, -1);
            if (totalMappersConfigured < 0) {
                throw new IOException("Invalid/missing num mappers: " + totalMappersConfigured);
            }
            final long bytesPerTracker = toGen / totalMappersConfigured;
            final ArrayList<InputSplit> splits = new ArrayList<InputSplit>(totalMappersConfigured);
            for (int i = 0; i < totalMappersConfigured; ++i) {
                splits.add(new GenSplit(bytesPerTracker, new String[] { "tracker_local" }));
            }
            return splits;
        }
    }

    private static void runDataGenJob(Configuration conf, Path tempDir) throws IOException, ClassNotFoundException, InterruptedException {
        JobClient client = new JobClient(conf);
        conf.setInt(MRJobConfig.NUM_MAPS, 1);
        Job job = Job.getInstance(conf);
        CompressionEmulationUtil.configure(job);
        job.setInputFormatClass(CustomInputFormat.class);
        FileOutputFormat.setOutputPath(job, tempDir);
        job.submit();
        int ret = job.waitForCompletion(true) ? 0 : 1;
        assertEquals("Job Failed", 0, ret);
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompressionRatioStandardization_1to4")
    public void testCompressionRatioStandardization_1to4(double param1, double param2, double param3) throws Exception {
        assertEquals(param1, CompressionEmulationUtil.standardizeCompressionRatio(param3), param2);
    }

    static public Stream<Arguments> Provider_testCompressionRatioStandardization_1to4() {
        return Stream.of(arguments(0.55F, 0.0D, 0.55F), arguments(0.65F, 0.0D, 0.652F), arguments(0.78F, 0.0D, 0.777F), arguments(0.86F, 0.0D, 0.855F));
    }
}
