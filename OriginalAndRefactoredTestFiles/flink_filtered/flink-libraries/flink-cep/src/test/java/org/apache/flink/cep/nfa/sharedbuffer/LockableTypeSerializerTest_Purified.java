package org.apache.flink.cep.nfa.sharedbuffer;

import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.runtime.state.heap.TestDuplicateSerializer;
import org.junit.Assert;
import org.junit.Test;

public class LockableTypeSerializerTest_Purified {

    @Test
    public void testDuplicate_1_testMerged_1() {
        IntSerializer nonDuplicatingInnerSerializer = IntSerializer.INSTANCE;
        Assert.assertSame(nonDuplicatingInnerSerializer, nonDuplicatingInnerSerializer.duplicate());
        Lockable.LockableTypeSerializer<Integer> candidateTestShallowDuplicate = new Lockable.LockableTypeSerializer<>(nonDuplicatingInnerSerializer);
        Assert.assertSame(candidateTestShallowDuplicate, candidateTestShallowDuplicate.duplicate());
    }

    @Test
    public void testDuplicate_3_testMerged_2() {
        TestDuplicateSerializer duplicatingInnerSerializer = new TestDuplicateSerializer();
        Assert.assertNotSame(duplicatingInnerSerializer, duplicatingInnerSerializer.duplicate());
        Lockable.LockableTypeSerializer<Integer> candidateTestDeepDuplicate = new Lockable.LockableTypeSerializer<>(duplicatingInnerSerializer);
        Lockable.LockableTypeSerializer<Integer> deepDuplicate = candidateTestDeepDuplicate.duplicate();
        Assert.assertNotSame(candidateTestDeepDuplicate, deepDuplicate);
        Assert.assertNotSame(candidateTestDeepDuplicate.getElementSerializer(), deepDuplicate.getElementSerializer());
    }
}
