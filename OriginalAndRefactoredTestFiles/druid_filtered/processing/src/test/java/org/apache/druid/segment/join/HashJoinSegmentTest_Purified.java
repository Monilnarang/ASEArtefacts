package org.apache.druid.segment.join;

import com.google.common.collect.ImmutableList;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.io.Closer;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.segment.CursorFactory;
import org.apache.druid.segment.MaxIngestedEventTimeInspector;
import org.apache.druid.segment.QueryableIndex;
import org.apache.druid.segment.QueryableIndexSegment;
import org.apache.druid.segment.ReferenceCountingSegment;
import org.apache.druid.segment.SegmentReference;
import org.apache.druid.segment.TimeBoundaryInspector;
import org.apache.druid.segment.join.table.IndexedTableJoinable;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.apache.druid.timeline.SegmentId;
import org.hamcrest.CoreMatchers;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HashJoinSegmentTest_Purified extends InitializedNullHandlingTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private QueryableIndexSegment baseSegment;

    private ReferenceCountingSegment referencedSegment;

    private HashJoinSegment hashJoinSegment;

    private int allReferencesAcquireCount;

    private int allReferencesCloseCount;

    private int referencedSegmentAcquireCount;

    private int referencedSegmentClosedCount;

    private int indexedTableJoinableReferenceAcquireCount;

    private int indexedTableJoinableReferenceCloseCount;

    private boolean j0Closed;

    private boolean j1Closed;

    @Before
    public void setUp() throws IOException {
        allReferencesAcquireCount = 0;
        allReferencesCloseCount = 0;
        referencedSegmentAcquireCount = 0;
        referencedSegmentClosedCount = 0;
        indexedTableJoinableReferenceAcquireCount = 0;
        indexedTableJoinableReferenceCloseCount = 0;
        j0Closed = false;
        j1Closed = false;
        baseSegment = new QueryableIndexSegment(JoinTestHelper.createFactIndexBuilder(temporaryFolder.newFolder()).buildMMappedIndex(), SegmentId.dummy("facts"));
        List<JoinableClause> joinableClauses = ImmutableList.of(new JoinableClause("j0.", new IndexedTableJoinable(JoinTestHelper.createCountriesIndexedTable()) {

            @Override
            public Optional<Closeable> acquireReferences() {
                if (!j0Closed) {
                    indexedTableJoinableReferenceAcquireCount++;
                    Closer closer = Closer.create();
                    closer.register(() -> indexedTableJoinableReferenceCloseCount++);
                    return Optional.of(closer);
                }
                return Optional.empty();
            }
        }, JoinType.LEFT, JoinConditionAnalysis.forExpression("1", "j0.", ExprMacroTable.nil())), new JoinableClause("j1.", new IndexedTableJoinable(JoinTestHelper.createRegionsIndexedTable()) {

            @Override
            public Optional<Closeable> acquireReferences() {
                if (!j1Closed) {
                    indexedTableJoinableReferenceAcquireCount++;
                    Closer closer = Closer.create();
                    closer.register(() -> indexedTableJoinableReferenceCloseCount++);
                    return Optional.of(closer);
                }
                return Optional.empty();
            }
        }, JoinType.LEFT, JoinConditionAnalysis.forExpression("1", "j1.", ExprMacroTable.nil())));
        referencedSegment = ReferenceCountingSegment.wrapRootGenerationSegment(baseSegment);
        SegmentReference testWrapper = new SegmentReference() {

            @Override
            public Optional<Closeable> acquireReferences() {
                Closer closer = Closer.create();
                return referencedSegment.acquireReferences().map(closeable -> {
                    referencedSegmentAcquireCount++;
                    closer.register(closeable);
                    closer.register(() -> referencedSegmentClosedCount++);
                    return closer;
                });
            }

            @Override
            public SegmentId getId() {
                return referencedSegment.getId();
            }

            @Override
            public Interval getDataInterval() {
                return referencedSegment.getDataInterval();
            }

            @Nullable
            @Override
            public <T> T as(@Nonnull Class<T> clazz) {
                return referencedSegment.as(clazz);
            }

            @Override
            public void close() {
                referencedSegment.close();
            }
        };
        hashJoinSegment = new HashJoinSegment(testWrapper, null, joinableClauses, null) {

            @Override
            public Optional<Closeable> acquireReferences() {
                Closer closer = Closer.create();
                return super.acquireReferences().map(closeable -> {
                    allReferencesAcquireCount++;
                    closer.register(closeable);
                    closer.register(() -> allReferencesCloseCount++);
                    return closer;
                });
            }
        };
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_1_testMerged_1() throws IOException {
        Assert.assertFalse(referencedSegment.isClosed());
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_2() throws IOException {
        Optional<Closeable> maybeCloseable = hashJoinSegment.acquireReferences();
        Assert.assertTrue(maybeCloseable.isPresent());
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_3() throws IOException {
        Assert.assertEquals(1, referencedSegmentAcquireCount);
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_4() throws IOException {
        Assert.assertEquals(2, indexedTableJoinableReferenceAcquireCount);
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_5() throws IOException {
        Assert.assertEquals(1, allReferencesAcquireCount);
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_6() throws IOException {
        Assert.assertEquals(0, referencedSegmentClosedCount);
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_7() throws IOException {
        Assert.assertEquals(0, indexedTableJoinableReferenceCloseCount);
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_8() throws IOException {
        Assert.assertEquals(0, allReferencesCloseCount);
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_10() throws IOException {
        Assert.assertEquals(1, referencedSegmentClosedCount);
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_11() throws IOException {
        Assert.assertEquals(2, indexedTableJoinableReferenceCloseCount);
    }

    @Test
    public void testJoinableClausesAreClosedWhenReferencesUsed_12() throws IOException {
        Assert.assertEquals(1, allReferencesCloseCount);
    }

    @Test
    public void testJoinableClausesClosedIfSegmentIsAlreadyClosed_1() {
        Assert.assertFalse(referencedSegment.isClosed());
    }

    @Test
    public void testJoinableClausesClosedIfSegmentIsAlreadyClosed_2() {
        referencedSegment.close();
        Assert.assertTrue(referencedSegment.isClosed());
    }

    @Test
    public void testJoinableClausesClosedIfSegmentIsAlreadyClosed_3() {
        Optional<Closeable> maybeCloseable = hashJoinSegment.acquireReferences();
        Assert.assertFalse(maybeCloseable.isPresent());
    }

    @Test
    public void testJoinableClausesClosedIfSegmentIsAlreadyClosed_4() {
        Assert.assertEquals(0, referencedSegmentAcquireCount);
    }

    @Test
    public void testJoinableClausesClosedIfSegmentIsAlreadyClosed_5() {
        Assert.assertEquals(0, indexedTableJoinableReferenceAcquireCount);
    }

    @Test
    public void testJoinableClausesClosedIfSegmentIsAlreadyClosed_6() {
        Assert.assertEquals(0, allReferencesAcquireCount);
    }

    @Test
    public void testJoinableClausesClosedIfSegmentIsAlreadyClosed_7() {
        Assert.assertEquals(0, referencedSegmentClosedCount);
    }

    @Test
    public void testJoinableClausesClosedIfSegmentIsAlreadyClosed_8() {
        Assert.assertEquals(0, indexedTableJoinableReferenceCloseCount);
    }

    @Test
    public void testJoinableClausesClosedIfSegmentIsAlreadyClosed_9() {
        Assert.assertEquals(0, allReferencesCloseCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableZeroIsAlreadyClosed_1() {
        Assert.assertFalse(referencedSegment.isClosed());
    }

    @Test
    public void testJoinableClausesClosedIfJoinableZeroIsAlreadyClosed_2() {
        Optional<Closeable> maybeCloseable = hashJoinSegment.acquireReferences();
        Assert.assertFalse(maybeCloseable.isPresent());
    }

    @Test
    public void testJoinableClausesClosedIfJoinableZeroIsAlreadyClosed_3() {
        Assert.assertEquals(1, referencedSegmentAcquireCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableZeroIsAlreadyClosed_4() {
        Assert.assertEquals(0, indexedTableJoinableReferenceAcquireCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableZeroIsAlreadyClosed_5() {
        Assert.assertEquals(0, allReferencesAcquireCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableZeroIsAlreadyClosed_6() {
        Assert.assertEquals(1, referencedSegmentClosedCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableZeroIsAlreadyClosed_7() {
        Assert.assertEquals(0, indexedTableJoinableReferenceCloseCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableZeroIsAlreadyClosed_8() {
        Assert.assertEquals(0, allReferencesCloseCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableOneIsAlreadyClosed_1() {
        Assert.assertFalse(referencedSegment.isClosed());
    }

    @Test
    public void testJoinableClausesClosedIfJoinableOneIsAlreadyClosed_2() {
        Optional<Closeable> maybeCloseable = hashJoinSegment.acquireReferences();
        Assert.assertFalse(maybeCloseable.isPresent());
    }

    @Test
    public void testJoinableClausesClosedIfJoinableOneIsAlreadyClosed_3() {
        Assert.assertEquals(1, referencedSegmentAcquireCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableOneIsAlreadyClosed_4() {
        Assert.assertEquals(1, indexedTableJoinableReferenceAcquireCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableOneIsAlreadyClosed_5() {
        Assert.assertEquals(0, allReferencesAcquireCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableOneIsAlreadyClosed_6() {
        Assert.assertEquals(1, referencedSegmentClosedCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableOneIsAlreadyClosed_7() {
        Assert.assertEquals(1, indexedTableJoinableReferenceCloseCount);
    }

    @Test
    public void testJoinableClausesClosedIfJoinableOneIsAlreadyClosed_8() {
        Assert.assertEquals(0, allReferencesCloseCount);
    }
}
