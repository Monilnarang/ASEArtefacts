package org.apache.flink.table.planner.typeutils;

import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.types.logical.BigIntType;
import org.apache.flink.table.types.logical.IntType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.table.types.logical.VarCharType;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RowTypeUtilsTest_Purified {

    private final RowType srcType = RowType.of(new LogicalType[] { new IntType(), new VarCharType(), new BigIntType() }, new String[] { "f0", "f1", "f2" });

    @Test
    void testGetUniqueName_1() {
        assertThat(RowTypeUtils.getUniqueName(Arrays.asList("Dave", "Evan"), Arrays.asList("Alice", "Bob"))).isEqualTo(Arrays.asList("Dave", "Evan"));
    }

    @Test
    void testGetUniqueName_2() {
        assertThat(RowTypeUtils.getUniqueName(Arrays.asList("Bob", "Bob", "Dave", "Alice"), Arrays.asList("Alice", "Bob"))).isEqualTo(Arrays.asList("Bob_0", "Bob_1", "Dave", "Alice_0"));
    }
}
