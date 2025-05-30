package org.eclipse.collections.impl.stack.primitive;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.api.stack.primitive.BooleanStack;
import org.eclipse.collections.api.stack.primitive.ImmutableBooleanStack;
import org.eclipse.collections.impl.collection.mutable.primitive.AbstractBooleanIterableTestCase;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.stack.mutable.ArrayStack;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractBooleanStackTestCase_Purified extends AbstractBooleanIterableTestCase {

    @Override
    protected abstract BooleanStack classUnderTest();

    @Override
    protected abstract BooleanStack newWith(boolean... elements);

    @Override
    protected abstract BooleanStack newMutableCollectionWith(boolean... elements);

    @Override
    protected RichIterable<Object> newObjectCollectionWith(Object... elements) {
        return ArrayStack.newStackWith(elements);
    }

    protected abstract BooleanStack newWithTopToBottom(boolean... elements);

    protected String createExpectedString(String start, String sep, String end) {
        StringBuilder expectedString = new StringBuilder(start);
        int size = this.classUnderTest().size();
        for (int i = 0; i < size; i++) {
            boolean sizeEven = (this.classUnderTest().size() & 1) == 0;
            boolean iEven = (i & 1) == 0;
            expectedString.append(sizeEven != iEven);
            expectedString.append(i == size - 1 ? "" : sep);
        }
        expectedString.append(end);
        return expectedString.toString();
    }

    @Test
    public void peek_1() {
        assertEquals((this.classUnderTest().size() & 1) != 0, this.classUnderTest().peek());
    }

    @Test
    public void peek_2() {
        assertEquals(BooleanArrayList.newListWith(), this.classUnderTest().peek(0));
    }

    @Test
    public void peek_3() {
        assertEquals(BooleanArrayList.newListWith((this.classUnderTest().size() & 1) != 0, (this.classUnderTest().size() & 1) == 0), this.classUnderTest().peek(2));
    }

    @Override
    @Test
    public void makeString_1() {
        assertEquals(this.createExpectedString("", ", ", ""), this.classUnderTest().makeString());
    }

    @Override
    @Test
    public void makeString_2() {
        assertEquals(this.createExpectedString("", "|", ""), this.classUnderTest().makeString("|"));
    }

    @Override
    @Test
    public void makeString_3() {
        assertEquals(this.createExpectedString("{", "|", "}"), this.classUnderTest().makeString("{", "|", "}"));
    }

    @Override
    @Test
    public void appendString_1() {
        StringBuilder appendable1 = new StringBuilder();
        this.classUnderTest().appendString(appendable1);
        assertEquals(this.createExpectedString("", ", ", ""), appendable1.toString());
    }

    @Override
    @Test
    public void appendString_2() {
        StringBuilder appendable2 = new StringBuilder();
        this.classUnderTest().appendString(appendable2, "|");
        assertEquals(this.createExpectedString("", "|", ""), appendable2.toString());
    }

    @Override
    @Test
    public void appendString_3() {
        StringBuilder appendable3 = new StringBuilder();
        this.classUnderTest().appendString(appendable3, "{", "|", "}");
        assertEquals(this.createExpectedString("{", "|", "}"), appendable3.toString());
    }

    @Test
    public void toImmutable_1() {
        assertEquals(this.classUnderTest(), this.classUnderTest().toImmutable());
    }

    @Test
    public void toImmutable_2() {
        Verify.assertInstanceOf(ImmutableBooleanStack.class, this.classUnderTest().toImmutable());
    }
}
