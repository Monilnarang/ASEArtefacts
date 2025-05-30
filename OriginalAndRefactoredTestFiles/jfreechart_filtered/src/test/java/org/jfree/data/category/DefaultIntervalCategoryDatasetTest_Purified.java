package org.jfree.data.category;

import java.util.List;
import org.jfree.chart.TestUtils;
import org.jfree.data.DataUtils;
import org.jfree.data.UnknownKeyException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultIntervalCategoryDatasetTest_Purified {

    @Test
    public void testEquals_1_testMerged_1() {
        double[] starts_S1A = new double[] { 0.1, 0.2, 0.3 };
        double[] starts_S2A = new double[] { 0.3, 0.4, 0.5 };
        double[] ends_S1A = new double[] { 0.5, 0.6, 0.7 };
        double[] ends_S2A = new double[] { 0.7, 0.8, 0.9 };
        double[][] startsA = new double[][] { starts_S1A, starts_S2A };
        double[][] endsA = new double[][] { ends_S1A, ends_S2A };
        DefaultIntervalCategoryDataset dA = new DefaultIntervalCategoryDataset(startsA, endsA);
        double[] starts_S1B = new double[] { 0.1, 0.2, 0.3 };
        double[] starts_S2B = new double[] { 0.3, 0.4, 0.5 };
        double[] ends_S1B = new double[] { 0.5, 0.6, 0.7 };
        double[] ends_S2B = new double[] { 0.7, 0.8, 0.9 };
        double[][] startsB = new double[][] { starts_S1B, starts_S2B };
        double[][] endsB = new double[][] { ends_S1B, ends_S2B };
        DefaultIntervalCategoryDataset dB = new DefaultIntervalCategoryDataset(startsB, endsB);
        assertEquals(dA, dB);
        assertEquals(dB, dA);
    }

    @Test
    public void testEquals_3() {
        DefaultIntervalCategoryDataset empty1 = new DefaultIntervalCategoryDataset(new double[0][0], new double[0][0]);
        DefaultIntervalCategoryDataset empty2 = new DefaultIntervalCategoryDataset(new double[0][0], new double[0][0]);
        assertEquals(empty1, empty2);
    }
}
