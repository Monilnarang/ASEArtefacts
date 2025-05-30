package org.apache.druid.msq.dart.controller.messages;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.msq.guice.MSQIndexingModule;
import org.apache.druid.msq.indexing.error.MSQErrorReport;
import org.apache.druid.msq.indexing.error.UnknownFault;
import org.apache.druid.msq.kernel.StageId;
import org.apache.druid.msq.statistics.PartialKeyStatisticsInformation;
import org.apache.druid.segment.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Collections;

public class ControllerMessageTest_Purified {

    private static final StageId STAGE_ID = StageId.fromString("xyz_2");

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = TestHelper.JSON_MAPPER.copy();
        objectMapper.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
        objectMapper.registerModules(new MSQIndexingModule().getJacksonModules());
    }

    private void assertSerde(final ControllerMessage message) throws IOException {
        final String json = objectMapper.writeValueAsString(message);
        final ControllerMessage message2 = objectMapper.readValue(json, ControllerMessage.class);
        Assertions.assertEquals(message, message2, json);
    }

    @Test
    public void testSerde_1_testMerged_1() throws IOException {
        final PartialKeyStatisticsInformation partialKeyStatisticsInformation = new PartialKeyStatisticsInformation(Collections.emptySet(), false, 0);
        assertSerde(new PartialKeyStatistics(STAGE_ID, 1, partialKeyStatisticsInformation));
        assertSerde(new WorkerWarning(STAGE_ID.getQueryId(), Collections.singletonList(MSQErrorReport.fromFault("task", null, null, UnknownFault.forMessage("oops")))));
    }

    @Test
    public void testSerde_2() throws IOException {
        assertSerde(new DoneReadingInput(STAGE_ID, 1));
    }

    @Test
    public void testSerde_3() throws IOException {
        assertSerde(new ResultsComplete(STAGE_ID, 1, "foo"));
    }

    @Test
    public void testSerde_4() throws IOException {
        assertSerde(new WorkerError(STAGE_ID.getQueryId(), MSQErrorReport.fromFault("task", null, null, UnknownFault.forMessage("oops"))));
    }
}
