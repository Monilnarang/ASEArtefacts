package org.apache.druid.msq.sql.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.druid.error.DruidException;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.common.guava.Sequences;
import org.apache.druid.java.util.common.guava.Yielders;
import org.apache.druid.msq.indexing.MSQControllerTask;
import org.apache.druid.msq.indexing.destination.MSQSelectDestination;
import org.apache.druid.msq.indexing.error.InsertCannotBeEmptyFault;
import org.apache.druid.msq.indexing.error.MSQException;
import org.apache.druid.msq.sql.SqlStatementState;
import org.apache.druid.msq.sql.entity.ColumnNameAndTypes;
import org.apache.druid.msq.sql.entity.PageInformation;
import org.apache.druid.msq.sql.entity.ResultSetInformation;
import org.apache.druid.msq.sql.entity.SqlStatementResult;
import org.apache.druid.msq.test.MSQTestBase;
import org.apache.druid.msq.test.MSQTestOverlordServiceClient;
import org.apache.druid.msq.util.MultiStageQueryContext;
import org.apache.druid.query.ExecutionMode;
import org.apache.druid.query.QueryContexts;
import org.apache.druid.segment.column.ValueType;
import org.apache.druid.sql.calcite.util.CalciteTests;
import org.apache.druid.sql.http.ResultFormat;
import org.apache.druid.sql.http.SqlQuery;
import org.apache.druid.storage.NilStorageConnector;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlMSQStatementResourcePostTest_Purified extends MSQTestBase {

    private SqlStatementResource resource;

    @BeforeEach
    public void init() {
        resource = new SqlStatementResource(sqlStatementFactory, objectMapper, indexingServiceClient, s -> localFileStorageConnector, authorizerMapper);
    }

    private byte[] createExpectedResultsInFormat(ResultFormat resultFormat, List<Object[]> resultsList, List<ColumnNameAndTypes> rowSignature, ObjectMapper jsonMapper) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (final ResultFormat.Writer writer = resultFormat.createFormatter(os, jsonMapper)) {
            SqlStatementResource.resultPusherInternal(writer, Yielders.each(Sequences.simple(resultsList)), rowSignature);
        }
        return os.toByteArray();
    }

    private void assertExpectedResults(String expectedResult, Response resultsResponse, ObjectMapper objectMapper) throws IOException {
        byte[] bytes = responseToByteArray(resultsResponse, objectMapper);
        Assert.assertEquals(expectedResult, new String(bytes, StandardCharsets.UTF_8));
    }

    public static byte[] responseToByteArray(Response resp, ObjectMapper objectMapper) throws IOException {
        if (resp.getEntity() instanceof StreamingOutput) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ((StreamingOutput) resp.getEntity()).write(baos);
            return baos.toByteArray();
        } else {
            return objectMapper.writeValueAsBytes(resp.getEntity());
        }
    }

    private static Map<String, Object> defaultAsyncContext() {
        Map<String, Object> context = new HashMap<>();
        context.put(QueryContexts.CTX_EXECUTION_MODE, ExecutionMode.ASYNC.name());
        return context;
    }

    private void assertSqlStatementResult(SqlStatementResult expected, SqlStatementResult actual) {
        Assert.assertEquals(expected.getQueryId(), actual.getQueryId());
        Assert.assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        Assert.assertEquals(expected.getSqlRowSignature(), actual.getSqlRowSignature());
        Assert.assertEquals(expected.getDurationMs(), actual.getDurationMs());
        Assert.assertEquals(expected.getStages(), actual.getStages());
        Assert.assertEquals(expected.getState(), actual.getState());
        Assert.assertEquals(expected.getWarnings(), actual.getWarnings());
        Assert.assertEquals(expected.getResultSetInformation(), actual.getResultSetInformation());
        if (actual.getCounters() == null || expected.getCounters() == null) {
            Assert.assertEquals(expected.getCounters(), actual.getCounters());
        } else {
            Assert.assertEquals(expected.getCounters().toString(), actual.getCounters().toString());
        }
        if (actual.getErrorResponse() == null || expected.getErrorResponse() == null) {
            Assert.assertEquals(expected.getErrorResponse(), actual.getErrorResponse());
        } else {
            Assert.assertEquals(expected.getErrorResponse().getAsMap(), actual.getErrorResponse().getAsMap());
        }
    }

    @Test
    public void nonSupportedModes_1() {
        SqlStatementResourceTest.assertExceptionMessage(resource.doPost(new SqlQuery("select * from foo", null, false, false, false, ImmutableMap.of(), null), SqlStatementResourceTest.makeOkRequest()), "Execution mode is not provided to the sql statement api. " + "Please set [executionMode] to [ASYNC] in the query context", Response.Status.BAD_REQUEST);
    }

    @Test
    public void nonSupportedModes_2() {
        SqlStatementResourceTest.assertExceptionMessage(resource.doPost(new SqlQuery("select * from foo", null, false, false, false, ImmutableMap.of(QueryContexts.CTX_EXECUTION_MODE, ExecutionMode.SYNC.name()), null), SqlStatementResourceTest.makeOkRequest()), "The sql statement api currently does not support the provided execution mode [SYNC]. " + "Please set [executionMode] to [ASYNC] in the query context", Response.Status.BAD_REQUEST);
    }
}
