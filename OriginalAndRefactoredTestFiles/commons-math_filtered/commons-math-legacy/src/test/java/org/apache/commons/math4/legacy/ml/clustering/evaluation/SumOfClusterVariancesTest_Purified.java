package org.apache.commons.math4.legacy.ml.clustering.evaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.legacy.ml.clustering.Cluster;
import org.apache.commons.math4.legacy.ml.clustering.DoublePoint;
import org.apache.commons.math4.legacy.ml.clustering.ClusterEvaluator;
import org.apache.commons.math4.legacy.ml.distance.EuclideanDistance;
import org.junit.Before;
import org.junit.Test;

public class SumOfClusterVariancesTest_Purified {

    private ClusterEvaluator evaluator;

    @Before
    public void setUp() {
        evaluator = new SumOfClusterVariances(new EuclideanDistance());
    }

    @Test
    public void testOrdering_1() {
        assertTrue(evaluator.isBetterScore(10, 20));
    }

    @Test
    public void testOrdering_2() {
        assertFalse(evaluator.isBetterScore(20, 1));
    }
}
