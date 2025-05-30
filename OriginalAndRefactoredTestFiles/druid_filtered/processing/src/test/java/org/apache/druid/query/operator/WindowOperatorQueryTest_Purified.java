package org.apache.druid.query.operator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.query.InlineDataSource;
import org.apache.druid.query.QueryContext;
import org.apache.druid.query.TableDataSource;
import org.apache.druid.query.spec.LegacySegmentSpec;
import org.apache.druid.query.spec.MultipleIntervalSegmentSpec;
import org.apache.druid.query.spec.QuerySegmentSpec;
import org.apache.druid.segment.column.RowSignature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WindowOperatorQueryTest_Purified {

    WindowOperatorQuery query;

    @Before
    public void setUp() {
        query = new WindowOperatorQuery(InlineDataSource.fromIterable(new ArrayList<>(), RowSignature.empty()), new LegacySegmentSpec(Intervals.ETERNITY), ImmutableMap.of("sally", "sue"), RowSignature.empty(), new ArrayList<>(), null);
    }

    @Test
    public void withOverriddenContext_1() {
        Assert.assertEquals("sue", query.context().get("sally"));
    }

    @Test
    public void withOverriddenContext_2() {
        final QueryContext context = query.withOverriddenContext(ImmutableMap.of("sally", "soo")).context();
        Assert.assertEquals("soo", context.get("sally"));
    }
}
