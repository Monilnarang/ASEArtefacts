package org.eclipse.collections.impl.list.mutable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArrayListAdapterTest_Purified extends AbstractListTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayListAdapterTest.class);

    @Override
    protected <T> ArrayListAdapter<T> newWith(T... littleElements) {
        return ArrayListAdapter.<T>newList().with(littleElements);
    }

    @Test
    public void testWithMethods_1() {
        Verify.assertContainsAll(ArrayListAdapter.newList().with(1), 1);
    }

    @Test
    public void testWithMethods_2() {
        Verify.assertContainsAll(ArrayListAdapter.newList().with(1, 2), 1, 2);
    }

    @Test
    public void testWithMethods_3() {
        Verify.assertContainsAll(ArrayListAdapter.newList().with(1, 2, 3), 1, 2, 3);
    }

    @Test
    public void testWithMethods_4() {
        Verify.assertContainsAll(ArrayListAdapter.newList().with(1, 2, 3, 4), 1, 2, 3, 4);
    }
}
