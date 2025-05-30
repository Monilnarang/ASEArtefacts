package org.apache.hadoop.mapreduce.lib.output.committer.manifest;

import java.io.FileNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.output.committer.manifest.files.ManifestSuccessData;
import org.apache.hadoop.mapreduce.lib.output.committer.manifest.files.TaskManifest;
import org.apache.hadoop.mapreduce.lib.output.committer.manifest.stages.CleanupJobStage;
import org.apache.hadoop.mapreduce.lib.output.committer.manifest.stages.CommitJobStage;
import org.apache.hadoop.mapreduce.lib.output.committer.manifest.stages.CommitTaskStage;
import org.apache.hadoop.mapreduce.lib.output.committer.manifest.stages.SetupJobStage;
import org.apache.hadoop.mapreduce.lib.output.committer.manifest.stages.SetupTaskStage;
import org.apache.hadoop.mapreduce.lib.output.committer.manifest.stages.StageConfig;
import static org.apache.hadoop.mapreduce.lib.output.committer.manifest.ManifestCommitterStatisticNames.OP_STAGE_JOB_CLEANUP;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;

public class TestCommitTaskStage_Purified extends AbstractManifestCommitterTest {

    @Override
    public void setup() throws Exception {
        super.setup();
        Path destDir = methodPath();
        StageConfig stageConfig = createStageConfigForJob(JOB1, destDir);
        setJobStageConfig(stageConfig);
        new SetupJobStage(stageConfig).apply(true);
    }

    @Test
    public void testCommitEmptyDirectory_1_testMerged_1() throws Throwable {
        String tid = String.format("task_%03d", 2);
        String taskAttemptId = String.format("%s_%02d", tid, 1);
        StageConfig taskStageConfig = createTaskStageConfig(JOB1, tid, taskAttemptId);
        new SetupTaskStage(taskStageConfig).apply("setup");
        CommitTaskStage.Result result = new CommitTaskStage(taskStageConfig).apply(null);
        final TaskManifest manifest = result.getTaskManifest();
    }

    @Test
    public void testCommitEmptyDirectory_3() throws Throwable {
        final CommitJobStage.Result outcome = new CommitJobStage(getJobStageConfig()).apply(new CommitJobStage.Arguments(true, true, null, new CleanupJobStage.Arguments(OP_STAGE_JOB_CLEANUP, true, true, false)));
        final Path successPath = outcome.getSuccessPath();
        final ManifestSuccessData successData = outcome.getJobSuccessData();
    }
}
