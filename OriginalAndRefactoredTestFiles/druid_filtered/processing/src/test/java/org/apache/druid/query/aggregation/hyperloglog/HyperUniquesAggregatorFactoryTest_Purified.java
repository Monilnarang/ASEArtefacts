package org.apache.druid.query.aggregation.hyperloglog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.druid.error.DruidException;
import org.apache.druid.hll.HyperLogLogCollector;
import org.apache.druid.hll.VersionZeroHyperLogLogCollector;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.query.aggregation.AggregatorFactory;
import org.apache.druid.query.aggregation.NoopAggregator;
import org.apache.druid.query.aggregation.NoopBufferAggregator;
import org.apache.druid.query.aggregation.NoopVectorAggregator;
import org.apache.druid.segment.ColumnSelectorFactory;
import org.apache.druid.segment.NilColumnValueSelector;
import org.apache.druid.segment.TestColumnSelectorFactory;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.column.ColumnCapabilitiesImpl;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.vector.TestVectorColumnSelectorFactory;
import org.apache.druid.segment.vector.VectorColumnSelectorFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HyperUniquesAggregatorFactoryTest_Purified {

    static final HyperUniquesAggregatorFactory AGGREGATOR_FACTORY = new HyperUniquesAggregatorFactory("hyperUnique", "uniques");

    static final String V0_BASE64 = "AAYbEyQwFyQVASMCVFEQQgEQIxIhM4ISAQMhUkICEDFDIBMhMgFQFAFAMjAAEhEREyVAEiUBAhIjISATMCECMiERIRIiVRFRAyIAEgFCQSMEJAITATAAEAMQgCEBEjQiAyUTAyEQASJyAGURAAISAwISATETQhAREBYDIVIlFTASAzJgERIgRCcmUyAwNAMyEJMjIhQXQhEWECABQDETATEREjIRAgEyIiMxMBQiAkBBMDYAMEQQACMzMhIkMTQSkYIRABIBADMBAhIEISAENkEBQDAxETMAIEEwEzQiQSEVQSFBBAQDICIiAVIAMTAQIQYBIRABADMDEzEAQSMkEiAYFBAQI0AmECEyQSARRTIVMhEkMiKAMCUBxUghAkIBI3EmMAQiACEAJDJCAAADOzESEDBCRjMgEUQQETQwEWIhA6MlAiAAZDI1AgEIIDUyFDIHMQEEAwIRBRABBStCZCQhAgJSMQIiQEEURTBmM1MxACIAETGhMgQnBRICNiIREyIUNAEAAkABAwQSEBJBIhIhIRERAiIRACUhEUAVMkQGEVMjECYjACBwEQQSIRIgAAEyExQUFSEAIBJCIDIDYTAgMiNBIUADUiETADMoFEADETMCIwUEQkIAESMSIzIABDERIXEhIiACQgUSEgJiQCAUARIRAREDQiEUAkQgAgQiIEAzIxRCARIgBAAVAzMAECEwE0Qh8gAAASEhEiAiMhUxcRImIVABATYyUBAwIoE1QhRDIiYBIBEBEiQSQyERAAADMAARAEACFYUwQSQBIRIgURITARFSEzEHEBACOTMREBIAMjIgEhU0cxEQIRIhIi1wEgMRUBEgMQIRAnAVASURMHQBAiEyBSAAEBQTAWQ5EQA0IUMSISAUEiASIjIhMhMFJBBSEjEAECEwACASEQFBAjARITEQIgYTEKEAeAAiMkEyARowARFBAicRISIBIxAQAgEBARMCIRQgMSIVIAkjMxIAIEMyADASMgFRIjEyKjEjBBIEQCUAARYBEQMxMCIBACNCACRCMlEzUUAAUDM1MhAjEgAxAAISAVFQECAhQAMBMhEzEgASNxAhFRIxECMRJBQAERAToBgQMhJSRQFAEhAwMiIhMQAwAgQiBQJiIGMQQhEiQxR1MiAjIAIEEiAkARECEzQlMjECIRATBgIhEBQAIQAEATEjBCMwAgMBMhAhIyFBIxQAARI1AAEABCIDFBIRUzMBIgAgEiARQCASMQQDQCFBAQAUJwMUElAyIAIRBSIRITICEAIxMAEUBEYTcBMBEEIxMREwIRIDAGIAEgYxBAEANCAhBAI2UhIiIgIRABIEVRAwNEIQERQgEFMhFCQSIAEhQDMTEQMiAjJyEQ==";

    private final HashFunction fn = Hashing.murmur3_128();

    private ColumnSelectorFactory metricFactory;

    private VectorColumnSelectorFactory vectorFactory;

    @Before
    public void setup() {
        final ColumnCapabilitiesImpl columnCapabilities = ColumnCapabilitiesImpl.createDefault().setType(ColumnType.NESTED_DATA);
        metricFactory = new TestColumnSelectorFactory().addCapabilities("uniques", columnCapabilities).addColumnSelector("uniques", null);
        vectorFactory = new TestVectorColumnSelectorFactory().addCapabilities("uniques", columnCapabilities);
    }

    @Test
    public void testEstimateCardinalityForZeroCardinality_1() {
        Assert.assertEquals(0L, HyperUniquesAggregatorFactory.estimateCardinality(null, true));
    }

    @Test
    public void testEstimateCardinalityForZeroCardinality_2() {
        Assert.assertEquals(0d, HyperUniquesAggregatorFactory.estimateCardinality(null, false));
    }

    @Test
    public void testEstimateCardinalityForZeroCardinality_3_testMerged_3() {
        HyperLogLogCollector emptyHyperLogLogCollector = HyperUniquesBufferAggregator.doGet(ByteBuffer.allocate(HyperLogLogCollector.getLatestNumBytesForDenseStorage()), 0);
        Assert.assertEquals(0L, HyperUniquesAggregatorFactory.estimateCardinality(emptyHyperLogLogCollector, true));
        Assert.assertEquals(0d, HyperUniquesAggregatorFactory.estimateCardinality(emptyHyperLogLogCollector, false));
        Assert.assertEquals(HyperUniquesAggregatorFactory.estimateCardinality(emptyHyperLogLogCollector, true).getClass(), HyperUniquesAggregatorFactory.estimateCardinality(null, true).getClass());
        Assert.assertEquals(HyperUniquesAggregatorFactory.estimateCardinality(emptyHyperLogLogCollector, false).getClass(), HyperUniquesAggregatorFactory.estimateCardinality(null, false).getClass());
    }
}
