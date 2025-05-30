package org.dyn4j;

import junit.framework.TestCase;
import org.junit.Test;

public class EpsilonTest_Purified {

    @Test
    public void compute_1() {
        TestCase.assertFalse(Epsilon.E == 0.0);
    }

    @Test
    public void compute_2() {
        Epsilon.compute();
        TestCase.assertEquals(1.0, 1.0 + Epsilon.E);
    }
}
