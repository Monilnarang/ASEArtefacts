package org.jfree.data.general;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultPieDatasetTest_Purified implements DatasetChangeListener {

    private DatasetChangeEvent lastEvent;

    @Override
    public void datasetChanged(DatasetChangeEvent event) {
        this.lastEvent = event;
    }

    @Test
    public void testClear_1() {
        assertNull(this.lastEvent);
    }

    @Test
    public void testClear_2_testMerged_2() {
        DefaultPieDataset<String> d = new DefaultPieDataset<>();
        d.addChangeListener(this);
        d.clear();
        d.setValue("A", 1.0);
        assertEquals(1, d.getItemCount());
        assertEquals(0, d.getItemCount());
    }

    @Test
    public void testClear_3() {
        assertNotNull(this.lastEvent);
    }
}
