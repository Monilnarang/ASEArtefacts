package org.apache.baremaps.pmtiles;

import static org.junit.jupiter.api.Assertions.*;
import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.math.LongMath;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.baremaps.testing.TestFiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PMTilesSerializerTest_Parameterized {

    private final VarIntSerializer varIntSerializer = new VarIntSerializer();

    private final HeaderSerializer headerSerializer = new HeaderSerializer();

    private final EntrySerializer entrySerializer = new EntrySerializer();

    private final DirectorySerializer directorySerializer = new DirectorySerializer();

    @ParameterizedTest
    @MethodSource("Provider_zxyToTileId_1to6")
    void zxyToTileId_1to6(int param1, int param2, int param3, int param4) {
        assertEquals(param1, TileIdConverter.zxyToTileId(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_zxyToTileId_1to6() {
        return Stream.of(arguments(0, 0, 0, 0), arguments(1, 1, 0, 0), arguments(2, 1, 0, 1), arguments(3, 1, 1, 1), arguments(4, 1, 1, 0), arguments(5, 2, 0, 0));
    }
}
