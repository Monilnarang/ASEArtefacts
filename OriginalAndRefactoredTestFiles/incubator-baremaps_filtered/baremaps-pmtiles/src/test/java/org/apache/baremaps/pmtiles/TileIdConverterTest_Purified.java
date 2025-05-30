package org.apache.baremaps.pmtiles;

import static org.junit.jupiter.api.Assertions.*;
import com.google.common.math.LongMath;
import org.junit.jupiter.api.Test;

class TileIdConverterTest_Purified {

    @Test
    void zxyToTileId_1() {
        assertEquals(0, TileIdConverter.zxyToTileId(0, 0, 0));
    }

    @Test
    void zxyToTileId_2() {
        assertEquals(1, TileIdConverter.zxyToTileId(1, 0, 0));
    }

    @Test
    void zxyToTileId_3() {
        assertEquals(2, TileIdConverter.zxyToTileId(1, 0, 1));
    }

    @Test
    void zxyToTileId_4() {
        assertEquals(3, TileIdConverter.zxyToTileId(1, 1, 1));
    }

    @Test
    void zxyToTileId_5() {
        assertEquals(4, TileIdConverter.zxyToTileId(1, 1, 0));
    }

    @Test
    void zxyToTileId_6() {
        assertEquals(5, TileIdConverter.zxyToTileId(2, 0, 0));
    }
}
