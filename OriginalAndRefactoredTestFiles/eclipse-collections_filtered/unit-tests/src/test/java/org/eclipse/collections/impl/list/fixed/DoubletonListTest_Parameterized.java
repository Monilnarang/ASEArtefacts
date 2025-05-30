package org.eclipse.collections.impl.list.fixed;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Procedures2;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DoubletonListTest_Parameterized extends AbstractMemoryEfficientMutableListTestCase {

    @Override
    protected int getSize() {
        return 2;
    }

    @Override
    protected Class<?> getListType() {
        return DoubletonList.class;
    }

    @Test
    public void testContains_3() {
        assertFalse(this.list.contains("3"));
    }

    @Test
    public void testGetFirstGetLast_1() {
        assertEquals("1", this.list.getFirst());
    }

    @Test
    public void testGetFirstGetLast_2() {
        assertEquals("2", this.list.getLast());
    }

    @ParameterizedTest
    @MethodSource("Provider_testContains_1to2")
    public void testContains_1to2(int param1) {
        assertTrue(this.list.contains(param1));
    }

    static public Stream<Arguments> Provider_testContains_1to2() {
        return Stream.of(arguments(1), arguments(2));
    }
}
