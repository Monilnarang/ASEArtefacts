package org.apache.amoro.flink.util;

import static org.apache.flink.table.api.DataTypes.BIGINT;
import static org.apache.flink.table.api.DataTypes.BOOLEAN;
import static org.apache.flink.table.api.DataTypes.DOUBLE;
import static org.apache.flink.table.api.DataTypes.FIELD;
import static org.apache.flink.table.api.DataTypes.INT;
import static org.apache.flink.table.api.DataTypes.ROW;
import static org.apache.flink.table.api.DataTypes.STRING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.apache.flink.table.types.DataType;
import org.junit.jupiter.api.Test;

class TestProjection_Purified {

    @Test
    void testIsNested_1() {
        assertThat(Projection.of(new int[] { 2, 1 }).isNested()).isFalse();
    }

    @Test
    void testIsNested_2() {
        assertThat(Projection.of(new int[][] { new int[] { 1 }, new int[] { 3 } }).isNested()).isFalse();
    }

    @Test
    void testIsNested_3() {
        assertThat(Projection.of(new int[][] { new int[] { 1 }, new int[] { 1, 2 }, new int[] { 3 } }).isNested()).isTrue();
    }

    @Test
    void testToNestedIndexes_1() {
        assertThat(Projection.of(new int[] { 1, 2, 3, 4 }).toNestedIndexes()).isEqualTo(new int[][] { new int[] { 1 }, new int[] { 2 }, new int[] { 3 }, new int[] { 4 } });
    }

    @Test
    void testToNestedIndexes_2() {
        assertThat(Projection.of(new int[][] { new int[] { 4 }, new int[] { 1, 3 }, new int[] { 2 } }).toNestedIndexes()).isEqualTo(new int[][] { new int[] { 4 }, new int[] { 1, 3 }, new int[] { 2 } });
    }
}
