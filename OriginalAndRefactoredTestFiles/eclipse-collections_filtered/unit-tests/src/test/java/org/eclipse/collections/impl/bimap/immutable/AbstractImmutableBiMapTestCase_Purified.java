package org.eclipse.collections.impl.bimap.immutable;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.collections.api.bimap.ImmutableBiMap;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.factory.BiMaps;
import org.eclipse.collections.impl.map.immutable.ImmutableMapIterableTestCase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractImmutableBiMapTestCase_Purified extends ImmutableMapIterableTestCase {

    @Override
    protected abstract ImmutableBiMap<Integer, String> classUnderTest();

    protected abstract ImmutableBiMap<Integer, String> newEmpty();

    protected abstract ImmutableBiMap<Integer, String> newWithMap();

    protected abstract ImmutableBiMap<Integer, String> newWithHashBiMap();

    protected abstract ImmutableBiMap<Integer, String> newWithImmutableMap();

    @Override
    protected int size() {
        return 4;
    }

    @Test
    public void containsKey_1() {
        assertTrue(this.classUnderTest().containsKey(1));
    }

    @Test
    public void containsKey_2() {
        assertFalse(this.classUnderTest().containsKey(5));
    }
}
