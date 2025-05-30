package org.apache.hadoop.hdfs.server.datanode;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_BLOCK_SCANNER_SKIP_RECENT_ACCESSED;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_BLOCK_SCANNER_VOLUME_JOIN_TIMEOUT_MSEC_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_SCAN_PERIOD_HOURS_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_BLOCK_SCANNER_VOLUME_BYTES_PER_SECOND;
import static org.apache.hadoop.hdfs.server.datanode.BlockScanner.Conf.INTERNAL_DFS_DATANODE_SCAN_PERIOD_MS;
import static org.apache.hadoop.hdfs.server.datanode.BlockScanner.Conf.INTERNAL_VOLUME_SCANNER_SCAN_RESULT_HANDLER;
import static org.apache.hadoop.hdfs.server.datanode.BlockScanner.Conf.INTERNAL_DFS_BLOCK_SCANNER_CURSOR_SAVE_INTERVAL_MS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.AppendTestUtil;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.apache.hadoop.hdfs.protocol.ExtendedBlock;
import org.apache.hadoop.hdfs.server.datanode.FsDatasetTestUtils.MaterializedReplica;
import org.apache.hadoop.hdfs.server.datanode.fsdataset.FsDatasetSpi;
import org.apache.hadoop.hdfs.server.datanode.VolumeScanner.ScanResultHandler;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.server.datanode.fsdataset.FsVolumeSpi;
import org.apache.hadoop.hdfs.server.datanode.fsdataset.FsVolumeSpi.BlockIterator;
import org.apache.hadoop.hdfs.server.datanode.fsdataset.impl.FsVolumeImpl;
import org.apache.hadoop.hdfs.server.datanode.VolumeScanner.Statistics;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Time;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestBlockScanner_Parameterized {

    public static final Logger LOG = LoggerFactory.getLogger(TestBlockScanner.class);

    @Before
    public void before() {
        BlockScanner.Conf.allowUnitTestSettings = true;
        GenericTestUtils.setLogLevel(BlockScanner.LOG, Level.TRACE);
        GenericTestUtils.setLogLevel(VolumeScanner.LOG, Level.TRACE);
        GenericTestUtils.setLogLevel(FsVolumeImpl.LOG, Level.TRACE);
    }

    private static void disableBlockScanner(Configuration conf) {
        conf.setLong(DFS_BLOCK_SCANNER_VOLUME_BYTES_PER_SECOND, 0L);
    }

    private static class TestContext implements Closeable {

        final int numNameServices;

        final MiniDFSCluster cluster;

        final DistributedFileSystem[] dfs;

        final String[] bpids;

        final DataNode datanode;

        final BlockScanner blockScanner;

        final FsDatasetSpi<? extends FsVolumeSpi> data;

        final FsDatasetSpi.FsVolumeReferences volumes;

        TestContext(Configuration conf, int numNameServices) throws Exception {
            this.numNameServices = numNameServices;
            File basedir = new File(GenericTestUtils.getRandomizedTempPath());
            long volumeScannerTimeOutFromConf = conf.getLong(DFS_BLOCK_SCANNER_VOLUME_JOIN_TIMEOUT_MSEC_KEY, -1);
            long expectedVScannerTimeOut = volumeScannerTimeOutFromConf == -1 ? MiniDFSCluster.DEFAULT_SCANNER_VOLUME_JOIN_TIMEOUT_MSEC : volumeScannerTimeOutFromConf;
            MiniDFSCluster.Builder bld = new MiniDFSCluster.Builder(conf, basedir).numDataNodes(1).storagesPerDatanode(1);
            assertEquals(expectedVScannerTimeOut, conf.getLong(DFS_BLOCK_SCANNER_VOLUME_JOIN_TIMEOUT_MSEC_KEY, -1));
            if (numNameServices > 1) {
                bld.nnTopology(MiniDFSNNTopology.simpleFederatedTopology(numNameServices));
            }
            cluster = bld.build();
            cluster.waitActive();
            dfs = new DistributedFileSystem[numNameServices];
            for (int i = 0; i < numNameServices; i++) {
                dfs[i] = cluster.getFileSystem(i);
            }
            bpids = new String[numNameServices];
            for (int i = 0; i < numNameServices; i++) {
                bpids[i] = cluster.getNamesystem(i).getBlockPoolId();
            }
            datanode = cluster.getDataNodes().get(0);
            blockScanner = datanode.getBlockScanner();
            for (int i = 0; i < numNameServices; i++) {
                dfs[i].mkdirs(new Path("/test"));
            }
            data = datanode.getFSDataset();
            volumes = data.getFsVolumeReferences();
        }

        @Override
        public void close() throws IOException {
            volumes.close();
            if (cluster != null) {
                for (int i = 0; i < numNameServices; i++) {
                    dfs[i].delete(new Path("/test"), true);
                    dfs[i].close();
                }
                cluster.shutdown();
            }
        }

        public void createFiles(int nsIdx, int numFiles, int length) throws Exception {
            for (int blockIdx = 0; blockIdx < numFiles; blockIdx++) {
                DFSTestUtil.createFile(dfs[nsIdx], getPath(blockIdx), length, (short) 1, 123L);
            }
        }

        public Path getPath(int fileIdx) {
            return new Path("/test/" + fileIdx);
        }

        public ExtendedBlock getFileBlock(int nsIdx, int fileIdx) throws Exception {
            return DFSTestUtil.getFirstBlock(dfs[nsIdx], getPath(fileIdx));
        }

        public MaterializedReplica getMaterializedReplica(int nsIdx, int fileIdx) throws Exception {
            return cluster.getMaterializedReplica(0, getFileBlock(nsIdx, fileIdx));
        }
    }

    public static class TestScanResultHandler extends ScanResultHandler {

        static class Info {

            boolean shouldRun = false;

            final Set<ExtendedBlock> badBlocks = new HashSet<ExtendedBlock>();

            final Set<ExtendedBlock> goodBlocks = new HashSet<ExtendedBlock>();

            long blocksScanned = 0;

            Semaphore sem = null;

            @Override
            public String toString() {
                final StringBuilder bld = new StringBuilder();
                bld.append("ScanResultHandler.Info{");
                bld.append("shouldRun=").append(shouldRun).append(", ");
                bld.append("blocksScanned=").append(blocksScanned).append(", ");
                bld.append("sem#availablePermits=").append(sem.availablePermits()).append(", ");
                bld.append("badBlocks=").append(badBlocks).append(", ");
                bld.append("goodBlocks=").append(goodBlocks);
                bld.append("}");
                return bld.toString();
            }
        }

        private VolumeScanner scanner;

        final static ConcurrentHashMap<String, Info> infos = new ConcurrentHashMap<String, Info>();

        static Info getInfo(FsVolumeSpi volume) {
            Info newInfo = new Info();
            Info prevInfo = infos.putIfAbsent(volume.getStorageID(), newInfo);
            return prevInfo == null ? newInfo : prevInfo;
        }

        @Override
        public void setup(VolumeScanner scanner) {
            this.scanner = scanner;
            Info info = getInfo(scanner.volume);
            LOG.info("about to start scanning.");
            synchronized (info) {
                while (!info.shouldRun) {
                    try {
                        info.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
            LOG.info("starting scanning.");
        }

        @Override
        public void handle(ExtendedBlock block, IOException e) {
            LOG.info("handling block {} (exception {})", block, e);
            Info info = getInfo(scanner.volume);
            Semaphore sem;
            synchronized (info) {
                sem = info.sem;
            }
            if (sem != null) {
                try {
                    sem.acquire();
                } catch (InterruptedException ie) {
                    throw new RuntimeException("interrupted");
                }
            }
            synchronized (info) {
                if (!info.shouldRun) {
                    throw new RuntimeException("stopping volumescanner thread.");
                }
                if (e == null) {
                    info.goodBlocks.add(block);
                } else {
                    info.badBlocks.add(block);
                }
                info.blocksScanned++;
            }
        }
    }

    private void waitForRescan(final TestScanResultHandler.Info info, final int numExpectedBlocks) throws TimeoutException, InterruptedException {
        LOG.info("Waiting for the first 1 blocks to be scanned.");
        GenericTestUtils.waitFor(new Supplier<Boolean>() {

            @Override
            public Boolean get() {
                synchronized (info) {
                    if (info.blocksScanned >= numExpectedBlocks) {
                        LOG.info("info = {}.  blockScanned has now reached 1.", info);
                        return true;
                    } else {
                        LOG.info("info = {}.  Waiting for blockScanned to reach 1.", info);
                        return false;
                    }
                }
            }
        }, 1000, 30000);
        synchronized (info) {
            assertEquals("Expected 1 good block.", numExpectedBlocks, info.goodBlocks.size());
            info.goodBlocks.clear();
            assertEquals("Expected 1 blocksScanned", numExpectedBlocks, info.blocksScanned);
            assertEquals("Did not expect bad blocks.", 0, info.badBlocks.size());
            info.blocksScanned = 0;
        }
    }

    private static class DelayVolumeScannerResponseToInterrupt extends VolumeScannerCBInjector {

        final private long delayAmountNS;

        final private Set<VolumeScanner> scannersToShutDown;

        DelayVolumeScannerResponseToInterrupt(long delayMS) {
            delayAmountNS = TimeUnit.NANOSECONDS.convert(delayMS, TimeUnit.MILLISECONDS);
            scannersToShutDown = ConcurrentHashMap.newKeySet();
        }

        @Override
        public void preSavingBlockIteratorTask(VolumeScanner volumeScanner) {
            long remainingTimeNS = delayAmountNS;
            long startTime = Time.monotonicNowNanos();
            long endTime = startTime + remainingTimeNS;
            long currTime, waitTime = 0;
            while ((currTime = Time.monotonicNowNanos()) < endTime) {
                waitTime = currTime - startTime;
            }
            LOG.info("VolumeScanner {} finished delayed Task after {}", volumeScanner.toString(), TimeUnit.NANOSECONDS.convert(waitTime, TimeUnit.MILLISECONDS));
        }

        @Override
        public void shutdownCallBack(VolumeScanner volumeScanner) {
            scannersToShutDown.add(volumeScanner);
        }

        @Override
        public void terminationCallBack(VolumeScanner volumeScanner) {
            scannersToShutDown.remove(volumeScanner);
        }

        public void waitForScanners() throws TimeoutException, InterruptedException {
            GenericTestUtils.waitFor(() -> scannersToShutDown.isEmpty(), 10, 120000);
        }
    }

    @Test(timeout = 120000)
    public void testCalculateNeededBytesPerSec_2() throws Exception {
        Assert.assertFalse(VolumeScanner.calculateShouldScan("test", 100, 101 * 3600, 1000, 5000));
    }

    @Test(timeout = 120000)
    public void testCalculateNeededBytesPerSec_5() throws Exception {
        Assert.assertFalse(VolumeScanner.calculateShouldScan("test", 100000L, 365000000L, 0, 60));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCalculateNeededBytesPerSec_1_3to4")
    public void testCalculateNeededBytesPerSec_1_3to4(String param1, int param2, int param3, int param4, int param5) throws Exception {
        Assert.assertTrue(VolumeScanner.calculateShouldScan(param1, param2, param3, param4, param5));
    }

    static public Stream<Arguments> Provider_testCalculateNeededBytesPerSec_1_3to4() {
        return Stream.of(arguments("test", 100, 0, 0, 60), arguments("test", 1, 3540, 0, 60), arguments("test", 100000L, 354000000L, 0, 60));
    }
}
