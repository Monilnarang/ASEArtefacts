package org.eclipse.collections.impl.list.fixed;

import java.util.Collections;
import java.util.Comparator;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.FixedSizeList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.Counter;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.stack.mutable.ArrayStack;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractMemoryEfficientMutableListTestCase_Purified {

    protected MutableList<String> list;

    @BeforeEach
    public void setUp() {
        this.list = this.classUnderTest();
    }

    protected MutableList<String> classUnderTest() {
        return Lists.fixedSize.ofAll(this.getNStrings());
    }

    private MutableList<String> getNStrings() {
        return Interval.oneTo(this.getSize()).collect(String::valueOf).toList();
    }

    protected abstract int getSize();

    protected abstract Class<?> getListType();

    @Test
    public void sort_1() {
        assertEquals(this.getNStrings(), this.list);
    }

    @Test
    public void sort_2() {
        assertEquals(this.getNStrings(), this.list);
    }
}
