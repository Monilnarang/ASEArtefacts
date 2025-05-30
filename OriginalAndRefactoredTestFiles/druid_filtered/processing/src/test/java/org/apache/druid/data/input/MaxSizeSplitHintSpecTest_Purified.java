package org.apache.druid.data.input;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.java.util.common.HumanReadableBytes;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class MaxSizeSplitHintSpecTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testDefaults_1() {
        Assert.assertEquals(MaxSizeSplitHintSpec.DEFAULT_MAX_SPLIT_SIZE, new MaxSizeSplitHintSpec(null, null).getMaxSplitSize());
    }

    @Test
    public void testDefaults_2() {
        Assert.assertEquals(MaxSizeSplitHintSpec.DEFAULT_MAX_NUM_FILES, new MaxSizeSplitHintSpec(null, null).getMaxNumFiles());
    }
}
