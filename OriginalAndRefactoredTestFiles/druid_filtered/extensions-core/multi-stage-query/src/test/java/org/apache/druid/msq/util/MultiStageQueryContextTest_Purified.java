package org.apache.druid.msq.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.indexing.common.TaskLockType;
import org.apache.druid.indexing.common.task.Tasks;
import org.apache.druid.msq.indexing.destination.MSQSelectDestination;
import org.apache.druid.msq.kernel.WorkerAssignmentStrategy;
import org.apache.druid.query.BadQueryContextException;
import org.apache.druid.query.QueryContext;
import org.apache.druid.segment.IndexSpec;
import org.apache.druid.segment.column.StringEncodingStrategy;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.matchers.ThrowableMessageMatcher;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_ARRAY_INGEST_MODE;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_DURABLE_SHUFFLE_STORAGE;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_FAULT_TOLERANCE;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_FINALIZE_AGGREGATIONS;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_MAX_NUM_TASKS;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_MSQ_MODE;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_REMOVE_NULL_BYTES;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_ROWS_IN_MEMORY;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_ROWS_PER_SEGMENT;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_SORT_ORDER;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_TASK_ASSIGNMENT_STRATEGY;
import static org.apache.druid.msq.util.MultiStageQueryContext.CTX_USE_AUTO_SCHEMAS;
import static org.apache.druid.msq.util.MultiStageQueryContext.DEFAULT_MAX_NUM_TASKS;

public class MultiStageQueryContextTest_Purified {

    private static List<String> decodeSortOrder(@Nullable final String input) {
        return MultiStageQueryContext.decodeList(MultiStageQueryContext.CTX_SORT_ORDER, input);
    }

    private static IndexSpec decodeIndexSpec(@Nullable final Object inputSpecObject) {
        return MultiStageQueryContext.decodeIndexSpec(inputSpecObject, new ObjectMapper());
    }

    @Test
    public void removeNullBytes_set_returnsCorrectValue_1() {
        Assert.assertTrue(MultiStageQueryContext.removeNullBytes(QueryContext.of(ImmutableMap.of(CTX_REMOVE_NULL_BYTES, true))));
    }

    @Test
    public void removeNullBytes_set_returnsCorrectValue_2() {
        Assert.assertFalse(MultiStageQueryContext.removeNullBytes(QueryContext.of(ImmutableMap.of(CTX_REMOVE_NULL_BYTES, false))));
    }
}
