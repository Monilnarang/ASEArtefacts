package org.eclipse.collections.impl.list.fixed;

import java.util.ListIterator;
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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TripletonListTest_Parameterized extends AbstractMemoryEfficientMutableListTestCase {

    @Override
    protected int getSize() {
        return 3;
    }

    @Override
    protected Class<?> getListType() {
        return TripletonList.class;
    }

    private void assertUnchanged() {
        Verify.assertInstanceOf(TripletonList.class, this.list);
        Verify.assertSize(3, this.list);
        Verify.assertNotContains("4", this.list);
        assertEquals(FastList.newListWith("1", "2", "3"), this.list);
    }

    @Test
    public void testContains_4() {
        assertFalse(this.list.contains("4"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testContains_1to3")
    public void testContains_1to3(int param1) {
        assertTrue(this.list.contains(param1));
    }

    static public Stream<Arguments> Provider_testContains_1to3() {
        return Stream.of(arguments(1), arguments(2), arguments(3));
    }
}
