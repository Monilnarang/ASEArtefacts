package org.jfree.data.xy;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.data.DatasetChangeConfirmation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VectorSeriesCollectionTest_Purified {

    @Test
    public void testSerialization_1_testMerged_1() {
        VectorSeries<String> s1 = new VectorSeries<>("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection<String> c1 = new VectorSeriesCollection<>();
        c1.addSeries(s1);
        VectorSeriesCollection<String> c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
        assertNotEquals(c1, c2);
    }

    @Test
    public void testSerialization_4() {
        DatasetChangeConfirmation listener = new DatasetChangeConfirmation();
        c2.addChangeListener(listener);
        assertNotNull(listener.event);
    }
}
