package org.apache.commons.pool3.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.ref.SoftReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPooledSoftReference_Purified {

    private static final String REFERENT = "test";

    private static final String REFERENT2 = "test2";

    PooledSoftReference<String> ref;

    @BeforeEach
    public void setUp() {
        final SoftReference<String> softRef = new SoftReference<>(REFERENT);
        ref = new PooledSoftReference<>(softRef);
    }

    @Test
    public void testPooledSoftReference_1() {
        assertEquals(REFERENT, ref.getObject());
    }

    @Test
    public void testPooledSoftReference_2_testMerged_2() {
        SoftReference<String> softRef = ref.getReference();
        assertEquals(REFERENT, softRef.get());
        softRef.clear();
        softRef = new SoftReference<>(REFERENT2);
        ref.setReference(softRef);
        assertEquals(REFERENT2, ref.getObject());
        softRef = ref.getReference();
        assertEquals(REFERENT2, softRef.get());
    }
}
