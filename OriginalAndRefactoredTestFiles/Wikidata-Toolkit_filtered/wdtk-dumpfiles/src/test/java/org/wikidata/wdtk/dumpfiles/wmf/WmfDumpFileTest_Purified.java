package org.wikidata.wdtk.dumpfiles.wmf;

import org.junit.Test;
import org.wikidata.wdtk.util.CompressionType;
import static org.junit.Assert.assertEquals;

public class WmfDumpFileTest_Purified {

    @Test
    public void getDumpFileCompressionType_1() {
        assertEquals(WmfDumpFile.getDumpFileCompressionType("foo.tar.gz"), CompressionType.GZIP);
    }

    @Test
    public void getDumpFileCompressionType_2() {
        assertEquals(WmfDumpFile.getDumpFileCompressionType("bar.txt.bz2"), CompressionType.BZ2);
    }

    @Test
    public void getDumpFileCompressionType_3() {
        assertEquals(WmfDumpFile.getDumpFileCompressionType("baz.txt"), CompressionType.NONE);
    }

    @Test
    public void getDumpFileCompressionType_4() {
        assertEquals(WmfDumpFile.getDumpFileCompressionType("bat.txt"), CompressionType.NONE);
    }
}
