package org.eclipse.collections.impl.list.fixed;

import java.util.ArrayList;
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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class QuadrupletonListTest_Parameterized extends AbstractMemoryEfficientMutableListTestCase {

    @Override
    protected int getSize() {
        return 4;
    }

    @Override
    protected Class<?> getListType() {
        return QuadrupletonList.class;
    }

    private void assertUnchanged() {
        Verify.assertSize(4, this.list);
        Verify.assertNotContains("5", this.list);
        assertEquals(FastList.newListWith("1", "2", "3", "4"), this.list);
    }

    @Test
    public void testContains_5() {
        Verify.assertNotContains("5", this.list);
    }

    @ParameterizedTest
    @MethodSource("Provider_testContains_1to4")
    public void testContains_1to4(int param1) {
        Verify.assertContains(param1, this.list);
    }

    static public Stream<Arguments> Provider_testContains_1to4() {
        return Stream.of(arguments(1), arguments(2), arguments(3), arguments(4));
    }
}
