package org.eclipse.collections.impl.set.fixed;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EmptySetTest_Purified extends AbstractMemoryEfficientMutableSetTestCase {

    private EmptySet<Object> emptySet;

    @BeforeEach
    public void setUp() {
        this.emptySet = new EmptySet<>();
    }

    @Override
    protected MutableSet<String> classUnderTest() {
        return new EmptySet<>();
    }

    @Override
    protected MutableSet<String> classUnderTestWithNull() {
        throw new AssertionError();
    }

    @Test
    public void testEmpty_1() {
        assertTrue(this.emptySet.isEmpty());
    }

    @Test
    public void testEmpty_2() {
        assertFalse(this.emptySet.notEmpty());
    }

    @Test
    public void testEmpty_3() {
        assertTrue(Sets.fixedSize.of().isEmpty());
    }

    @Test
    public void testEmpty_4() {
        assertFalse(Sets.fixedSize.of().notEmpty());
    }

    @Test
    public void testContains_1() {
        assertFalse(this.emptySet.contains("Something"));
    }

    @Test
    public void testContains_2() {
        assertFalse(this.emptySet.contains(null));
    }

    @Test
    public void testGetFirstLast_1() {
        assertNull(this.emptySet.getFirst());
    }

    @Test
    public void testGetFirstLast_2() {
        assertNull(this.emptySet.getLast());
    }

    @Test
    public void testReadResolve_1() {
        Verify.assertInstanceOf(EmptySet.class, Sets.fixedSize.of());
    }

    @Test
    public void testReadResolve_2() {
        Verify.assertPostSerializedIdentity(Sets.fixedSize.of());
    }
}
