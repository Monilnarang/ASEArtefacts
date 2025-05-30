package org.jfree.data.xy;

import org.jfree.chart.TestUtils;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.DatasetChangeConfirmation;
import org.jfree.data.Range;
import org.jfree.data.UnknownKeyException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class XYSeriesCollectionTest_Purified {

    private static final double EPSILON = 0.0000000001;

    @Test
    public void testSerialization_1_testMerged_1() {
        XYSeries<String> s1 = new XYSeries<>("Series");
        s1.add(1.0, 1.1);
        XYSeriesCollection<String> c1 = new XYSeriesCollection<>();
        c1.addSeries(s1);
        XYSeriesCollection<String> c2 = TestUtils.serialised(c1);
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
