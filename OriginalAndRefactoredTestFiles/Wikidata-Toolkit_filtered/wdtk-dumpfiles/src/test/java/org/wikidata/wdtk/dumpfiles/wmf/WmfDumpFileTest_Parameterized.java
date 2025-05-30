package org.wikidata.wdtk.dumpfiles.wmf;

import org.junit.Test;
import org.wikidata.wdtk.util.CompressionType;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WmfDumpFileTest_Parameterized {

    @Test
    public void getDumpFileCompressionType_1() {
        assertEquals(WmfDumpFile.getDumpFileCompressionType("foo.tar.gz"), CompressionType.GZIP);
    }

    @Test
    public void getDumpFileCompressionType_2() {
        assertEquals(WmfDumpFile.getDumpFileCompressionType("bar.txt.bz2"), CompressionType.BZ2);
    }

    @ParameterizedTest
    @MethodSource("Provider_getDumpFileCompressionType_3to4")
    public void getDumpFileCompressionType_3to4(String param1) {
        assertEquals(WmfDumpFile.getDumpFileCompressionType(param1), CompressionType.NONE);
    }

    static public Stream<Arguments> Provider_getDumpFileCompressionType_3to4() {
        return Stream.of(arguments("baz.txt"), arguments("bat.txt"));
    }
}
