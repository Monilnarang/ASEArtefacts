package org.apache.druid.msq.sql.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.error.DruidException;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.msq.indexing.error.MSQException;
import org.apache.druid.msq.indexing.error.QueryNotSupportedFault;
import org.apache.druid.msq.sql.SqlStatementState;
import org.apache.druid.msq.sql.resources.SqlStatementResourceTest;
import org.junit.Assert;
import org.junit.Test;

public class SqlStatementResultTest_Purified {

    public static final MSQException MSQ_EXCEPTION = new MSQException(QueryNotSupportedFault.instance());

    public static final ObjectMapper MAPPER = DefaultObjectMapper.INSTANCE;

    public static final String JSON_STRING = "{\"queryId\":\"q1\"," + "\"state\":\"RUNNING\"," + "\"createdAt\":\"2023-05-31T12:00:00.000Z\"," + "\"schema\":[{\"name\":\"_time\",\"type\":\"TIMESTAMP\",\"nativeType\":\"LONG\"},{\"name\":\"alias\",\"type\":\"VARCHAR\",\"nativeType\":\"STRING\"},{\"name\":\"market\",\"type\":\"VARCHAR\",\"nativeType\":\"STRING\"}]," + "\"durationMs\":100," + "\"result\":{\"numTotalRows\":1,\"totalSizeInBytes\":1,\"resultFormat\":\"object\",\"dataSource\":\"ds\",\"pages\":[{\"id\":0,\"sizeInBytes\":1}]}," + "\"errorDetails\":{\"error\":\"druidException\",\"errorCode\":\"QueryNotSupported\",\"persona\":\"USER\",\"category\":\"UNCATEGORIZED\",\"errorMessage\":\"QueryNotSupported\",\"context\":{}}}";

    public static final SqlStatementResult SQL_STATEMENT_RESULT = new SqlStatementResult("q1", SqlStatementState.RUNNING, SqlStatementResourceTest.CREATED_TIME, SqlStatementResourceTest.COL_NAME_AND_TYPES, 100L, ResultSetInformationTest.RESULTS, DruidException.fromFailure(new DruidException.Failure(MSQ_EXCEPTION.getFault().getErrorCode()) {

        @Override
        protected DruidException makeException(DruidException.DruidExceptionBuilder bob) {
            return bob.forPersona(DruidException.Persona.USER).ofCategory(DruidException.Category.UNCATEGORIZED).build(MSQ_EXCEPTION.getMessage());
        }
    }).toErrorResponse());

    @Test
    public void sanityTest_1() throws JsonProcessingException {
        Assert.assertEquals(JSON_STRING, MAPPER.writeValueAsString(SQL_STATEMENT_RESULT));
    }

    @Test
    public void sanityTest_2() throws JsonProcessingException {
        Assert.assertEquals("SqlStatementResult{" + "queryId='q1'," + " state=RUNNING," + " createdAt=2023-05-31T12:00:00.000Z," + " sqlRowSignature=[ColumnNameAndTypes{colName='_time', sqlTypeName='TIMESTAMP', nativeTypeName='LONG'}, ColumnNameAndTypes{colName='alias', sqlTypeName='VARCHAR', nativeTypeName='STRING'}, ColumnNameAndTypes{colName='market', sqlTypeName='VARCHAR', nativeTypeName='STRING'}]," + " durationInMs=100," + " resultSetInformation=ResultSetInformation{numTotalRows=1, totalSizeInBytes=1, resultFormat=object, records=null, dataSource='ds', pages=[PageInformation{id=0, numRows=null, sizeInBytes=1, worker=null, partition=null}]}," + " errorResponse={error=druidException, errorCode=QueryNotSupported, persona=USER, category=UNCATEGORIZED, errorMessage=QueryNotSupported, context={}}," + " stages=null," + " counters=null," + " warnings=null}", SQL_STATEMENT_RESULT.toString());
    }
}
