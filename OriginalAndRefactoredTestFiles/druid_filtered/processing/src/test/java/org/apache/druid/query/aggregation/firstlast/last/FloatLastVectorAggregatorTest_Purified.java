package org.apache.druid.query.aggregation.firstlast.last;

import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Pair;
import org.apache.druid.query.aggregation.SerializablePairLongFloat;
import org.apache.druid.query.aggregation.VectorAggregator;
import org.apache.druid.query.aggregation.firstlast.FirstLastVectorAggregator;
import org.apache.druid.query.aggregation.firstlast.FloatFirstLastVectorAggregator;
import org.apache.druid.query.dimension.DimensionSpec;
import org.apache.druid.segment.column.ColumnCapabilities;
import org.apache.druid.segment.column.ColumnCapabilitiesImpl;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.vector.BaseLongVectorValueSelector;
import org.apache.druid.segment.vector.MultiValueDimensionVectorSelector;
import org.apache.druid.segment.vector.NoFilterVectorOffset;
import org.apache.druid.segment.vector.ReadableVectorInspector;
import org.apache.druid.segment.vector.SingleValueDimensionVectorSelector;
import org.apache.druid.segment.vector.VectorColumnSelectorFactory;
import org.apache.druid.segment.vector.VectorObjectSelector;
import org.apache.druid.segment.vector.VectorValueSelector;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

public class FloatLastVectorAggregatorTest_Purified extends InitializedNullHandlingTest {

    private static final double EPSILON = 1e-5;

    private static final float[] VALUES = new float[] { 7.2f, 15.6f, 2.1f, 150.0f };

    private static final long[] LONG_VALUES = new long[] { 1L, 2L, 3L, 4L };

    private static final float[] FLOAT_VALUES = new float[] { 1.0f, 2.0f, 3.0f, 4.0f };

    private static final double[] DOUBLE_VALUES = new double[] { 1.0, 2.0, 3.0, 4.0 };

    private static final boolean[] NULLS = new boolean[] { false, false, false, false };

    private static final String NAME = "NAME";

    private static final String FIELD_NAME = "FIELD_NAME";

    private static final String FIELD_NAME_LONG = "LONG_NAME";

    private static final String TIME_COL = "__time";

    private final long[] times = { 2345001L, 2345100L, 2345200L, 2345300L };

    private final SerializablePairLongFloat[] pairs = { new SerializablePairLongFloat(2345001L, 1.2F), new SerializablePairLongFloat(2345100L, 2.2F), new SerializablePairLongFloat(2345200L, 3.2F), new SerializablePairLongFloat(2345300L, 4.2F) };

    private VectorObjectSelector selector;

    private BaseLongVectorValueSelector timeSelector;

    private ByteBuffer buf;

    private FloatFirstLastVectorAggregator target;

    private FloatLastAggregatorFactory floatLastAggregatorFactory;

    private VectorColumnSelectorFactory selectorFactory;

    private VectorValueSelector nonFloatValueSelector;

    @Before
    public void setup() {
        byte[] randomBytes = new byte[1024];
        ThreadLocalRandom.current().nextBytes(randomBytes);
        buf = ByteBuffer.wrap(randomBytes);
        timeSelector = new BaseLongVectorValueSelector(new NoFilterVectorOffset(times.length, 0, times.length) {
        }) {

            @Override
            public long[] getLongVector() {
                return times;
            }

            @Nullable
            @Override
            public boolean[] getNullVector() {
                return null;
            }
        };
        selector = new VectorObjectSelector() {

            @Override
            public Object[] getObjectVector() {
                return pairs;
            }

            @Override
            public int getMaxVectorSize() {
                return 4;
            }

            @Override
            public int getCurrentVectorSize() {
                return 0;
            }
        };
        nonFloatValueSelector = new BaseLongVectorValueSelector(new NoFilterVectorOffset(LONG_VALUES.length, 0, LONG_VALUES.length)) {

            @Override
            public long[] getLongVector() {
                return LONG_VALUES;
            }

            @Override
            public float[] getFloatVector() {
                return FLOAT_VALUES;
            }

            @Override
            public double[] getDoubleVector() {
                return DOUBLE_VALUES;
            }

            @Nullable
            @Override
            public boolean[] getNullVector() {
                return NULLS;
            }

            @Override
            public int getMaxVectorSize() {
                return 4;
            }

            @Override
            public int getCurrentVectorSize() {
                return 4;
            }
        };
        selectorFactory = new VectorColumnSelectorFactory() {

            @Override
            public ReadableVectorInspector getReadableVectorInspector() {
                return new NoFilterVectorOffset(VALUES.length, 0, VALUES.length);
            }

            @Override
            public SingleValueDimensionVectorSelector makeSingleValueDimensionSelector(DimensionSpec dimensionSpec) {
                return null;
            }

            @Override
            public MultiValueDimensionVectorSelector makeMultiValueDimensionSelector(DimensionSpec dimensionSpec) {
                return null;
            }

            @Override
            public VectorValueSelector makeValueSelector(String column) {
                if (TIME_COL.equals(column)) {
                    return timeSelector;
                } else if (FIELD_NAME.equals(column)) {
                    return nonFloatValueSelector;
                } else {
                    return null;
                }
            }

            @Override
            public VectorObjectSelector makeObjectSelector(String column) {
                if (FIELD_NAME.equals(column)) {
                    return selector;
                } else {
                    return null;
                }
            }

            @Nullable
            @Override
            public ColumnCapabilities getColumnCapabilities(String column) {
                if (FIELD_NAME.equals(column)) {
                    return ColumnCapabilitiesImpl.createSimpleNumericColumnCapabilities(ColumnType.FLOAT);
                } else if (FIELD_NAME_LONG.equals(column)) {
                    return ColumnCapabilitiesImpl.createSimpleNumericColumnCapabilities(ColumnType.LONG);
                }
                return null;
            }
        };
        target = new FloatLastVectorAggregator(timeSelector, selector);
        clearBufferForPositions(0, 0);
        floatLastAggregatorFactory = new FloatLastAggregatorFactory(NAME, FIELD_NAME, TIME_COL);
    }

    private void clearBufferForPositions(int offset, int... positions) {
        for (int position : positions) {
            target.init(buf, offset + position);
        }
    }

    @Test
    public void testFactory_1() {
        Assert.assertTrue(floatLastAggregatorFactory.canVectorize(selectorFactory));
    }

    @Test
    public void testFactory_2_testMerged_2() {
        VectorAggregator vectorAggregator = floatLastAggregatorFactory.factorizeVector(selectorFactory);
        Assert.assertNotNull(vectorAggregator);
        Assert.assertEquals(FloatLastVectorAggregator.class, vectorAggregator.getClass());
    }
}
