package com.graphhopper.storage;

import com.graphhopper.util.BitUtil;
import com.graphhopper.util.Helper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public abstract class DataAccessTest_Purified {

    private final File folder = new File("./target/tmp/da");

    protected String directory;

    protected String name = "dataacess";

    public DataAccess createDataAccess(String location) {
        return createDataAccess(location, 128);
    }

    public abstract DataAccess createDataAccess(String location, int segmentSize);

    @BeforeEach
    public void setUp() {
        if (!Helper.removeDir(folder))
            throw new IllegalStateException("cannot delete folder " + folder);
        folder.mkdirs();
        directory = folder.getAbsolutePath() + "/";
    }

    @AfterEach
    public void tearDown() {
        Helper.removeDir(folder);
    }

    @Test
    public void testSet_GetBytes_1() {
        DataAccess da = createDataAccess(name);
        da.create(300);
        assertEquals(128, da.getSegmentSize());
    }

    @Test
    public void testSet_GetBytes_2_testMerged_2() {
        byte[] bytes = BitUtil.LITTLE.fromInt(Integer.MAX_VALUE / 3);
        da.setBytes(8, bytes, bytes.length);
        bytes = new byte[4];
        da.getBytes(8, bytes, bytes.length);
        assertEquals(Integer.MAX_VALUE / 3, BitUtil.LITTLE.toInt(bytes));
    }

    @Test
    public void testSet_GetBytes_4_testMerged_3() {
        long bytePos = 4294967296L + 11111;
        int segmentSizePower = 24;
        int segmentSizeInBytes = 1 << segmentSizePower;
        int indexDivisor = segmentSizeInBytes - 1;
        int bufferIndex = (int) (bytePos >>> segmentSizePower);
        int index = (int) (bytePos & indexDivisor);
        assertEquals(256, bufferIndex);
        assertEquals(11111, index);
    }
}
