package org.apache.hadoop.metrics2.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import org.apache.hadoop.metrics2.lib.MutableInverseQuantiles;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestSampleQuantiles_Purified {

    static final Quantile[] quantiles = { new Quantile(0.50, 0.050), new Quantile(0.75, 0.025), new Quantile(0.90, 0.010), new Quantile(0.95, 0.005), new Quantile(0.99, 0.001) };

    SampleQuantiles estimator;

    final static int NUM_REPEATS = 10;

    @Before
    public void init() {
        estimator = new SampleQuantiles(quantiles);
    }

    @Test
    public void testCount_1() throws IOException {
        assertThat(estimator.getCount()).isZero();
    }

    @Test
    public void testCount_2() throws IOException {
        assertThat(estimator.getSampleCount()).isZero();
    }

    @Test
    public void testCount_3() throws IOException {
        assertThat(estimator.snapshot()).isNull();
    }

    @Test
    public void testCount_4_testMerged_4() throws IOException {
        estimator.insert(1337);
        assertThat(estimator.getCount()).isOne();
        estimator.snapshot();
        assertThat(estimator.getSampleCount()).isOne();
        assertThat(estimator.toString()).isEqualTo("50.00 %ile +/- 5.00%: 1337\n" + "75.00 %ile +/- 2.50%: 1337\n" + "90.00 %ile +/- 1.00%: 1337\n" + "95.00 %ile +/- 0.50%: 1337\n" + "99.00 %ile +/- 0.10%: 1337");
    }
}
