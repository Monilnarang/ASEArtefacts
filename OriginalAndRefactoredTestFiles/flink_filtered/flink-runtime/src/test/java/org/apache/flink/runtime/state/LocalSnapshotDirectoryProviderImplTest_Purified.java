package org.apache.flink.runtime.state;

import org.apache.flink.api.common.JobID;
import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.jobgraph.JobVertexID;
import org.apache.flink.testutils.junit.utils.TempDirUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalSnapshotDirectoryProviderImplTest_Purified {

    private static final JobID JOB_ID = new JobID();

    private static final JobVertexID JOB_VERTEX_ID = new JobVertexID();

    private static final int SUBTASK_INDEX = 0;

    @TempDir
    private java.nio.file.Path tmpFolder;

    private LocalSnapshotDirectoryProviderImpl directoryProvider;

    private File[] allocBaseFolders;

    @BeforeEach
    void setup() throws IOException {
        this.allocBaseFolders = new File[] { TempDirUtils.newFolder(tmpFolder), TempDirUtils.newFolder(tmpFolder), TempDirUtils.newFolder(tmpFolder) };
        this.directoryProvider = new LocalSnapshotDirectoryProviderImpl(allocBaseFolders, JOB_ID, JOB_VERTEX_ID, SUBTASK_INDEX);
    }

    @Test
    void testPathStringConstants_1() {
        assertThat(directoryProvider.subtaskDirString()).isEqualTo("jid_" + JOB_ID + Path.SEPARATOR + "vtx_" + JOB_VERTEX_ID + "_sti_" + SUBTASK_INDEX);
    }

    @Test
    void testPathStringConstants_2() {
        final long checkpointId = 42;
        assertThat(directoryProvider.checkpointDirString(checkpointId)).isEqualTo("chk_" + checkpointId);
    }
}
