package org.apache.flink.runtime.blob;

import org.apache.flink.api.common.JobID;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.blob.BlobKey.BlobType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlobCacheSizeTrackerTest_Purified {

    private BlobCacheSizeTracker tracker;

    private JobID jobId;

    private BlobKey blobKey;

    @BeforeEach
    void setup() {
        tracker = new BlobCacheSizeTracker(5L);
        jobId = new JobID();
        blobKey = BlobKey.createKey(BlobType.PERMANENT_BLOB);
        tracker.track(jobId, blobKey, 3L);
    }

    @Test
    void testTrack_1() {
        assertThat(tracker.getSize(jobId, blobKey)).isEqualTo(3L);
    }

    @Test
    void testTrack_2() {
        assertThat(tracker.getBlobKeysByJobId(jobId)).contains(blobKey);
    }

    @Test
    void testUntrack_1() {
        assertThat(tracker.checkLimit(3L)).hasSize(1);
    }

    @Test
    void testUntrack_2_testMerged_2() {
        tracker.untrack(Tuple2.of(jobId, blobKey));
        assertThat(tracker.getSize(jobId, blobKey)).isNull();
        assertThat(tracker.getBlobKeysByJobId(jobId)).isEmpty();
        assertThat(tracker.checkLimit(3L)).isEmpty();
    }
}
