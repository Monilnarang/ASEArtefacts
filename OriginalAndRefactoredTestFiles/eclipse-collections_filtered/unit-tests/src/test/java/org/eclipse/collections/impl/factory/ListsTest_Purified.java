package org.eclipse.collections.impl.factory;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.eclipse.collections.api.factory.list.FixedSizeListFactory;
import org.eclipse.collections.api.factory.list.ImmutableListFactory;
import org.eclipse.collections.api.factory.list.MultiReaderListFactory;
import org.eclipse.collections.api.factory.list.MutableListFactory;
import org.eclipse.collections.api.list.FixedSizeList;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MultiReaderList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.MultiReaderFastList;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ListsTest_Purified {

    private void assertPresizedListEquals(int initialCapacity, FastList<String> list) {
        try {
            Field itemsField = FastList.class.getDeclaredField("items");
            itemsField.setAccessible(true);
            Object[] items = (Object[]) itemsField.get(list);
            assertEquals(initialCapacity, items.length);
        } catch (SecurityException ignored) {
            fail("Unable to modify the visibility of the field 'items' on FastList");
        } catch (NoSuchFieldException ignored) {
            fail("No field named 'items' in FastList");
        } catch (IllegalAccessException ignored) {
            fail("No access to the field 'items' in FastList");
        }
    }

    private static void assertPresizedMultiReaderListEquals(int initialCapacity, MultiReaderFastList<String> list) {
        try {
            Field delegateField = MultiReaderFastList.class.getDeclaredField("delegate");
            delegateField.setAccessible(true);
            FastList<String> delegate = (FastList<String>) delegateField.get(list);
            Field itemsField = FastList.class.getDeclaredField("items");
            itemsField.setAccessible(true);
            Object[] items = (Object[]) itemsField.get(delegate);
            assertEquals(initialCapacity, items.length);
        } catch (SecurityException | NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    @Test
    public void emptyList_1() {
        assertTrue(Lists.immutable.of().isEmpty());
    }

    @Test
    public void emptyList_2() {
        assertSame(Lists.immutable.of(), Lists.immutable.of());
    }

    @Test
    public void emptyList_3() {
        Verify.assertPostSerializedIdentity(Lists.immutable.of());
    }
}
