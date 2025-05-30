package org.eclipse.collections.impl.collection.immutable;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.collection.ImmutableCollection;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.collection.primitive.ImmutableBooleanCollection;
import org.eclipse.collections.api.collection.primitive.ImmutableByteCollection;
import org.eclipse.collections.api.collection.primitive.ImmutableCharCollection;
import org.eclipse.collections.api.collection.primitive.ImmutableDoubleCollection;
import org.eclipse.collections.api.collection.primitive.ImmutableFloatCollection;
import org.eclipse.collections.api.collection.primitive.ImmutableIntCollection;
import org.eclipse.collections.api.collection.primitive.ImmutableLongCollection;
import org.eclipse.collections.api.collection.primitive.ImmutableShortCollection;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectLongMap;
import org.eclipse.collections.api.partition.PartitionImmutableCollection;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.Counter;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.SortedBags;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractImmutableCollectionTestCase_Purified {

    public static final Predicate<Integer> ERROR_THROWING_PREDICATE = each -> {
        throw new AssertionError();
    };

    public static final Predicates2<Integer, Class<Integer>> ERROR_THROWING_PREDICATE_2 = new Predicates2<Integer, Class<Integer>>() {

        public boolean accept(Integer argument1, Class<Integer> argument2) {
            throw new AssertionError();
        }
    };

    protected abstract ImmutableCollection<Integer> classUnderTest();

    protected abstract <T> MutableCollection<T> newMutable();

    protected ImmutableCollection<Integer> classUnderTestWithNull() {
        return this.classUnderTest().reject(Integer.valueOf(1)::equals).newWith(null);
    }

    @Test
    public void makeString_1() {
        assertEquals(FastList.newList(this.classUnderTest()).toString(), '[' + this.classUnderTest().makeString() + ']');
    }

    @Test
    public void makeString_2() {
        assertEquals(FastList.newList(this.classUnderTest()).toString(), '[' + this.classUnderTest().makeString(", ") + ']');
    }

    @Test
    public void makeString_3() {
        assertEquals(FastList.newList(this.classUnderTest()).toString(), this.classUnderTest().makeString("[", ", ", "]"));
    }

    @Test
    public void appendString_1() {
        Appendable builder1 = new StringBuilder();
        this.classUnderTest().appendString(builder1);
        assertEquals(FastList.newList(this.classUnderTest()).toString(), '[' + builder1.toString() + ']');
    }

    @Test
    public void appendString_2() {
        Appendable builder2 = new StringBuilder();
        this.classUnderTest().appendString(builder2, ", ");
        assertEquals(FastList.newList(this.classUnderTest()).toString(), '[' + builder2.toString() + ']');
    }

    @Test
    public void appendString_3() {
        Appendable builder3 = new StringBuilder();
        this.classUnderTest().appendString(builder3, "[", ", ", "]");
        assertEquals(FastList.newList(this.classUnderTest()).toString(), builder3.toString());
    }
}
