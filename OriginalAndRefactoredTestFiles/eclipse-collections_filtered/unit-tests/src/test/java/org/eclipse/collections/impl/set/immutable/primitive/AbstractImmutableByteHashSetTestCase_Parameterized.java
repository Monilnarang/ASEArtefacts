package org.eclipse.collections.impl.set.immutable.primitive;

import java.util.NoSuchElementException;
import org.eclipse.collections.api.LazyByteIterable;
import org.eclipse.collections.api.iterator.ByteIterator;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.ImmutableByteSet;
import org.eclipse.collections.api.set.primitive.MutableByteSet;
import org.eclipse.collections.api.tuple.primitive.ByteBytePair;
import org.eclipse.collections.impl.bag.mutable.primitive.ByteHashBag;
import org.eclipse.collections.impl.block.factory.primitive.BytePredicates;
import org.eclipse.collections.impl.collection.immutable.primitive.AbstractImmutableByteCollectionTestCase;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.list.mutable.primitive.ByteArrayList;
import org.eclipse.collections.impl.map.mutable.primitive.CollisionGeneratorUtil;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.mutable.primitive.ByteHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public abstract class AbstractImmutableByteHashSetTestCase_Parameterized extends AbstractImmutableByteCollectionTestCase {

    @Override
    protected abstract ImmutableByteSet classUnderTest();

    @Override
    protected abstract ImmutableByteSet newWith(byte... elements);

    @Override
    protected MutableByteSet newMutableCollectionWith(byte... elements) {
        return ByteHashSet.newSetWith(elements);
    }

    @Override
    protected MutableSet<Byte> newObjectCollectionWith(Byte... elements) {
        return UnifiedSet.newSetWith(elements);
    }

    protected static ByteArrayList generateCollisions() {
        return CollisionGeneratorUtil.generateCollisions();
    }

    private void assertUnion(ImmutableByteSet set1, ImmutableByteSet set2, ImmutableByteSet expected) {
        ImmutableByteSet actual = set1.union(set2);
        assertEquals(expected, actual);
    }

    private void assertIntersect(ImmutableByteSet set1, ImmutableByteSet set2, ImmutableByteSet expected) {
        ImmutableByteSet actual = set1.intersect(set2);
        assertEquals(expected, actual);
    }

    private void assertDifference(ImmutableByteSet set1, ImmutableByteSet set2, ImmutableByteSet expected) {
        ImmutableByteSet actual = set1.difference(set2);
        assertEquals(expected, actual);
    }

    private void assertSymmetricDifference(ImmutableByteSet set1, ImmutableByteSet set2, ImmutableByteSet expected) {
        ImmutableByteSet actual = set1.symmetricDifference(set2);
        assertEquals(expected, actual);
    }

    private void assertIsSubsetOf(ImmutableByteSet set1, ImmutableByteSet set2, boolean expected) {
        boolean actual = set1.isSubsetOf(set2);
        assertEquals(expected, actual);
    }

    private void assertIsProperSubsetOf(ImmutableByteSet set1, ImmutableByteSet set2, boolean expected) {
        boolean actual = set1.isProperSubsetOf(set2);
        assertEquals(expected, actual);
    }

    private void assertCartesianProduct(ImmutableByteSet set1, ImmutableByteSet set2, ImmutableSet<ByteBytePair> expected) {
        ImmutableSet<ByteBytePair> actual = set1.cartesianProduct(set2).toSet().toImmutable();
        assertEquals(expected, actual);
    }

    @Override
    @Test
    public void toBag_1() {
        assertEquals(ByteHashBag.newBagWith((byte) 1, (byte) 2, (byte) 3), this.classUnderTest().toBag());
    }

    @Override
    @Test
    public void toBag_2() {
        assertEquals(ByteHashBag.newBagWith((byte) 0, (byte) 1, (byte) 31), this.newWith((byte) 0, (byte) 1, (byte) 31).toBag());
    }

    @Override
    @Test
    public void toBag_3() {
        assertEquals(ByteHashBag.newBagWith((byte) 0, (byte) 1, (byte) 31, (byte) 32), this.newWith((byte) 0, (byte) 1, (byte) 31, (byte) 32).toBag());
    }

    @Test
    public void toImmutable_1() {
        assertEquals(0, this.newWith().toImmutable().size());
    }

    @Test
    public void toImmutable_2() {
        assertEquals(1, this.newWith((byte) 1).toImmutable().size());
    }

    @Test
    public void toImmutable_3() {
        assertEquals(3, this.newWith((byte) 1, (byte) 2, (byte) 3).toImmutable().size());
    }

    @Test
    public void union_1() {
        this.assertUnion(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith((byte) 3, (byte) 4, (byte) 5), this.newWith((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5));
    }

    @Test
    public void union_2() {
        this.assertUnion(this.newWith((byte) 1, (byte) 2, (byte) 3, (byte) 6), this.newWith((byte) 3, (byte) 4, (byte) 5), this.newWith((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6));
    }

    @Test
    public void union_3() {
        this.assertUnion(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith((byte) 3, (byte) 4, (byte) 5, (byte) 6), this.newWith((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6));
    }

    @Test
    public void union_4() {
        this.assertUnion(this.newWith(), this.newWith(), this.newWith());
    }

    @Test
    public void union_5() {
        this.assertUnion(this.newWith(), this.newWith((byte) 3, (byte) 4, (byte) 5), this.newWith((byte) 3, (byte) 4, (byte) 5));
    }

    @Test
    public void union_6() {
        this.assertUnion(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith(), this.newWith((byte) 1, (byte) 2, (byte) 3));
    }

    @Test
    public void intersect_1() {
        this.assertIntersect(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith((byte) 3, (byte) 4, (byte) 5), this.newWith((byte) 3));
    }

    @Test
    public void intersect_2() {
        this.assertIntersect(this.newWith((byte) 1, (byte) 2, (byte) 3, (byte) 6), this.newWith((byte) 3, (byte) 4, (byte) 5), this.newWith((byte) 3));
    }

    @Test
    public void intersect_3() {
        this.assertIntersect(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith((byte) 3, (byte) 4, (byte) 5, (byte) 6), this.newWith((byte) 3));
    }

    @Test
    public void intersect_4() {
        this.assertIntersect(this.newWith(), this.newWith(), this.newWith());
    }

    @Test
    public void intersect_5() {
        this.assertIntersect(this.newWith(), this.newWith((byte) 3, (byte) 4, (byte) 5), this.newWith());
    }

    @Test
    public void intersect_6() {
        this.assertIntersect(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith(), this.newWith());
    }

    @Test
    public void difference_1() {
        this.assertDifference(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith((byte) 3, (byte) 4, (byte) 5), this.newWith((byte) 1, (byte) 2));
    }

    @Test
    public void difference_2() {
        this.assertDifference(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith());
    }

    @Test
    public void difference_3() {
        this.assertDifference(this.newWith(), this.newWith(), this.newWith());
    }

    @Test
    public void difference_4() {
        this.assertDifference(this.newWith(), this.newWith((byte) 3, (byte) 4, (byte) 5), this.newWith());
    }

    @Test
    public void difference_5() {
        this.assertDifference(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith(), this.newWith((byte) 1, (byte) 2, (byte) 3));
    }

    @Test
    public void symmetricDifference_1() {
        this.assertSymmetricDifference(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith((byte) 2, (byte) 3, (byte) 4), this.newWith((byte) 1, (byte) 4));
    }

    @Test
    public void symmetricDifference_2() {
        this.assertSymmetricDifference(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith());
    }

    @Test
    public void symmetricDifference_3() {
        this.assertSymmetricDifference(this.newWith(), this.newWith(), this.newWith());
    }

    @Test
    public void symmetricDifference_4() {
        this.assertSymmetricDifference(this.newWith(), this.newWith((byte) 3, (byte) 4, (byte) 5), this.newWith((byte) 3, (byte) 4, (byte) 5));
    }

    @Test
    public void symmetricDifference_5() {
        this.assertSymmetricDifference(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith(), this.newWith((byte) 1, (byte) 2, (byte) 3));
    }

    @Test
    public void isSubsetOf_3() {
        this.assertIsSubsetOf(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith((byte) 1, (byte) 2, (byte) 3), true);
    }

    @Test
    public void isSubsetOf_4() {
        this.assertIsSubsetOf(this.newWith(), this.newWith(), true);
    }

    @Test
    public void isSubsetOf_5() {
        this.assertIsSubsetOf(this.newWith(), this.newWith((byte) 3, (byte) 4, (byte) 5), true);
    }

    @Test
    public void isSubsetOf_6() {
        this.assertIsSubsetOf(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith(), false);
    }

    @Test
    public void isProperSubsetOf_3() {
        this.assertIsProperSubsetOf(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith((byte) 1, (byte) 2, (byte) 3), false);
    }

    @Test
    public void isProperSubsetOf_4() {
        this.assertIsProperSubsetOf(this.newWith(), this.newWith(), false);
    }

    @Test
    public void isProperSubsetOf_5() {
        this.assertIsProperSubsetOf(this.newWith(), this.newWith((byte) 3, (byte) 4, (byte) 5), true);
    }

    @Test
    public void isProperSubsetOf_6() {
        this.assertIsProperSubsetOf(this.newWith((byte) 1, (byte) 2, (byte) 3), this.newWith(), false);
    }

    @Test
    public void isCartesianProduct_3() {
        this.assertCartesianProduct(this.newWith((byte) 1, (byte) 2), this.newWith(), Sets.immutable.empty());
    }

    @Test
    public void isCartesianProduct_4() {
        this.assertCartesianProduct(this.newWith(), this.newWith((byte) 1, (byte) 2), Sets.immutable.empty());
    }

    @Test
    public void isCartesianProduct_5() {
        this.assertCartesianProduct(this.newWith(), this.newWith(), Sets.immutable.empty());
    }

    @ParameterizedTest
    @MethodSource("Provider_isSubsetOf_1to2")
    public void isSubsetOf_1to2(int param1, int param2, int param3, int param4, int param5) {
        this.assertIsSubsetOf(this.newWith((byte) param2, (byte) param3), this.newWith((byte) param4, (byte) param5, (byte) 3), param1);
    }

    static public Stream<Arguments> Provider_isSubsetOf_1to2() {
        return Stream.of(arguments(1, 2, 1, 2, 3), arguments(1, 4, 1, 2, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_isProperSubsetOf_1to2")
    public void isProperSubsetOf_1to2(int param1, int param2, int param3, int param4, int param5) {
        this.assertIsProperSubsetOf(this.newWith((byte) param2, (byte) param3), this.newWith((byte) param4, (byte) param5, (byte) 3), param1);
    }

    static public Stream<Arguments> Provider_isProperSubsetOf_1to2() {
        return Stream.of(arguments(1, 2, 1, 2, 3), arguments(1, 4, 1, 2, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_isCartesianProduct_1to2")
    public void isCartesianProduct_1to2(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9, int param10, int param11, int param12) {
        this.assertCartesianProduct(this.newWith((byte) param1, (byte) param2), this.newWith((byte) param3, (byte) param4), Sets.immutable.with(PrimitiveTuples.pair((byte) param5, (byte) param6), PrimitiveTuples.pair((byte) param7, (byte) param8), PrimitiveTuples.pair((byte) param9, (byte) param10), PrimitiveTuples.pair((byte) param11, (byte) param12)));
    }

    static public Stream<Arguments> Provider_isCartesianProduct_1to2() {
        return Stream.of(arguments(1, 2, 3, 4, 1, 3, 1, 4, 2, 3, 2, 4), arguments(1, 2, 1, 2, 1, 1, 1, 2, 2, 1, 2, 2));
    }
}
