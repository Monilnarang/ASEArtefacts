package org.graylog.failure;

import org.graylog2.plugin.Tools;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class FailureBatchTest_Purified {

    private IndexingFailure createIndexingFailure() {
        return new IndexingFailure(IndexingFailureCause.MappingError, "Mapping Failed", "Cannot cast String to Double", Tools.nowUTC(), null, "target-index");
    }

    private ProcessingFailure createProcessingFailure() {
        return new ProcessingFailure(ProcessingFailureCause.UNKNOWN, "failure-cause", "failure-details", Tools.nowUTC(), null, true);
    }

    @Test
    public void containsIndexingFailures_returnsTrueForIndexingFailuresAndFalseForOtherFailures_1() {
        assertThat(FailureBatch.indexingFailureBatch(List.of(createIndexingFailure())).containsIndexingFailures()).isTrue();
    }

    @Test
    public void containsIndexingFailures_returnsTrueForIndexingFailuresAndFalseForOtherFailures_2() {
        assertThat(FailureBatch.processingFailureBatch(List.of(createProcessingFailure())).containsIndexingFailures()).isFalse();
    }

    @Test
    public void containsProcessingFailures_returnsTrueForProcessingFailuresAndFalseForOtherFailures_1() {
        assertThat(FailureBatch.processingFailureBatch(List.of(createProcessingFailure())).containsProcessingFailures()).isTrue();
    }

    @Test
    public void containsProcessingFailures_returnsTrueForProcessingFailuresAndFalseForOtherFailures_2() {
        assertThat(FailureBatch.indexingFailureBatch(List.of(createIndexingFailure())).containsProcessingFailures()).isFalse();
    }
}
