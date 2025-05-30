package org.eclipse.collections.impl.list.mutable.primitive;

import java.lang.reflect.Field;
import java.util.BitSet;
import org.eclipse.collections.api.block.predicate.primitive.BooleanPredicate;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanArrayListTest_Purified extends AbstractBooleanListTestCase {

    private final BooleanArrayList list = this.classUnderTest();

    @Override
    protected final BooleanArrayList classUnderTest() {
        return BooleanArrayList.newListWith(true, false, true);
    }

    @Override
    protected BooleanArrayList newWith(boolean... elements) {
        return BooleanArrayList.newListWith(elements);
    }

    private static class LastValueBeforeFalseWasFalse implements BooleanPredicate {

        private static final long serialVersionUID = 1L;

        private boolean value = true;

        @Override
        public boolean accept(boolean currentValue) {
            boolean oldValue = this.value;
            this.value = currentValue;
            return !currentValue && !oldValue;
        }
    }

    @Override
    @Test
    public void size_1() {
        Verify.assertSize(0, new BooleanArrayList());
    }

    @Override
    @Test
    public void size_2() {
        Verify.assertSize(0, new BooleanArrayList(1));
    }

    @Override
    @Test
    public void size_3() {
        Verify.assertSize(1, BooleanArrayList.newListWith(false));
    }

    @Override
    @Test
    public void size_4() {
        Verify.assertSize(3, this.list);
    }

    @Override
    @Test
    public void size_5() {
        Verify.assertSize(3, BooleanArrayList.newList(this.list));
    }
}
