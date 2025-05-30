package org.eclipse.collections.impl.list.immutable.primitive;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.list.primitive.ImmutableBooleanList;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ImmutableBooleanEmptyListTest_Parameterized extends AbstractImmutableBooleanListTestCase {

    @Override
    protected ImmutableBooleanList classUnderTest() {
        return ImmutableBooleanEmptyList.INSTANCE;
    }

    @Override
    @Test
    public void testEquals_1() {
        Verify.assertEqualsAndHashCode(this.newMutableCollectionWith(), this.classUnderTest());
    }

    @Override
    @Test
    public void testEquals_2() {
        Verify.assertPostSerializedIdentity(this.newWith());
    }

    @Override
    @Test
    public void testEquals_3() {
        assertNotEquals(this.classUnderTest(), this.newWith(false, false, false, true));
    }

    @Override
    @Test
    public void testEquals_4() {
        assertNotEquals(this.classUnderTest(), this.newWith(true));
    }

    @Override
    @ParameterizedTest
    @MethodSource("Provider_indexOf_1to2")
    public void indexOf_1to2(long param1) {
        assertEquals(-param1, this.classUnderTest().indexOf(true));
    }

    static public Stream<Arguments> Provider_indexOf_1to2() {
        return Stream.of(arguments(1L), arguments(1L));
    }

    @Override
    @ParameterizedTest
    @MethodSource("Provider_lastIndexOf_1to2")
    public void lastIndexOf_1to2(long param1) {
        assertEquals(-param1, this.classUnderTest().lastIndexOf(true));
    }

    static public Stream<Arguments> Provider_lastIndexOf_1to2() {
        return Stream.of(arguments(1L), arguments(1L));
    }
}
