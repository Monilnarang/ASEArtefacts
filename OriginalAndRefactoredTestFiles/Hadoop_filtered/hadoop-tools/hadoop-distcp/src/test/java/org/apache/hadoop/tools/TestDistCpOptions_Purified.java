package org.apache.hadoop.tools;

import java.util.Collections;
import org.apache.hadoop.conf.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.tools.DistCpOptions.FileAttribute;
import static org.apache.hadoop.test.GenericTestUtils.assertExceptionContains;
import static org.apache.hadoop.tools.DistCpOptions.MAX_NUM_LISTSTATUS_THREADS;
import static org.junit.Assert.fail;

public class TestDistCpOptions_Purified {

    private static final float DELTA = 0.001f;

    @Test
    public void testToString_1() {
        DistCpOptions option = new DistCpOptions.Builder(new Path("abc"), new Path("xyz")).build();
        String val = "DistCpOptions{atomicCommit=false, syncFolder=false, " + "deleteMissing=false, ignoreFailures=false, overwrite=false, " + "append=false, useDiff=false, useRdiff=false, " + "fromSnapshot=null, toSnapshot=null, " + "skipCRC=false, blocking=true, numListstatusThreads=0, maxMaps=20, " + "mapBandwidth=0.0, copyStrategy='uniformsize', preserveStatus=[], " + "atomicWorkPath=null, logPath=null, sourceFileListing=abc, " + "sourcePaths=null, targetPath=xyz, filtersFile='null', " + "blocksPerChunk=0, copyBufferSize=8192, verboseLog=false, " + "directWrite=false, useiterator=false, updateRoot=false}";
        String optionString = option.toString();
        Assert.assertEquals(val, optionString);
    }

    @Test
    public void testToString_2() {
        Assert.assertNotSame(DistCpOptionSwitch.ATOMIC_COMMIT.toString(), DistCpOptionSwitch.ATOMIC_COMMIT.name());
    }
}
