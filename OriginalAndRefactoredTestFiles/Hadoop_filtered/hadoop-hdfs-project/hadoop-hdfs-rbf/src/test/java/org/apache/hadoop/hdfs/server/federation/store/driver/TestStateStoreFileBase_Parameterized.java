package org.apache.hadoop.hdfs.server.federation.store.driver;

import static org.apache.hadoop.hdfs.server.federation.store.driver.impl.StateStoreFileBaseImpl.isOldTempRecord;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.util.Time;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestStateStoreFileBase_Parameterized {

    @Test
    public void testTempOld_3_testMerged_3() {
        long tnow = Time.now();
        String tmpFile1 = "test." + tnow + ".tmp";
        assertFalse(isOldTempRecord(tmpFile1));
        long told = Time.now() - TimeUnit.MINUTES.toMillis(1);
        String tmpFile2 = "test." + told + ".tmp";
        assertTrue(isOldTempRecord(tmpFile2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTempOld_1to2")
    public void testTempOld_1to2(String param1) {
        assertFalse(isOldTempRecord(param1));
    }

    static public Stream<Arguments> Provider_testTempOld_1to2() {
        return Stream.of(arguments("test.txt"), arguments("testfolder/test.txt"));
    }
}
