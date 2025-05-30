package org.apache.druid.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.druid.frame.Frame;
import org.apache.druid.frame.allocation.HeapMemoryAllocator;
import org.apache.druid.frame.allocation.SingleMemoryAllocatorFactory;
import org.apache.druid.frame.segment.FrameCursorUtils;
import org.apache.druid.frame.write.FrameWriterUtils;
import org.apache.druid.frame.write.FrameWriters;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.Pair;
import org.apache.druid.java.util.common.guava.Sequence;
import org.apache.druid.segment.Cursor;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import java.io.Closeable;
import java.util.ArrayList;

public class FrameBasedInlineDataSourceSerializerTest_Purified {

    private static final Interval INTERVAL = Intervals.of("2000/P1Y");

    private static final RowSignature FOO_INLINE_SIGNATURE = RowSignature.builder().addTimeColumn().add("s", ColumnType.STRING).add("n", ColumnType.LONG).build();

    private static final InlineDataSource FOO_INLINE = InlineDataSource.fromIterable(ImmutableList.<Object[]>builder().add(new Object[] { INTERVAL.getStartMillis(), "x", 1 }).add(new Object[] { INTERVAL.getStartMillis(), "x", 2 }).add(new Object[] { INTERVAL.getStartMillis(), "y", 3 }).add(new Object[] { INTERVAL.getStartMillis(), "z", 4 }).build(), FOO_INLINE_SIGNATURE);

    private static final RowSignature BAR_INLINE_SIGNATURE = RowSignature.builder().addTimeColumn().add("s", ColumnType.STRING).add("n", ColumnType.LONG).build();

    private static final InlineDataSource BAR_INLINE = InlineDataSource.fromIterable(ImmutableList.<Object[]>builder().add(new Object[] { INTERVAL.getStartMillis(), "a", 1 }).add(new Object[] { INTERVAL.getStartMillis(), "a", 2 }).add(new Object[] { INTERVAL.getStartMillis(), "b", 3 }).add(new Object[] { INTERVAL.getStartMillis(), "c", 4 }).build(), BAR_INLINE_SIGNATURE);

    private static final RowSignature MULTI_VALUE_INLINE_SIGNATURE = RowSignature.builder().addTimeColumn().add("s", ColumnType.STRING_ARRAY).add("n", ColumnType.LONG).build();

    private static final InlineDataSource MULTI_VALUE_INLINE = InlineDataSource.fromIterable(ImmutableList.<Object[]>builder().add(new Object[] { INTERVAL.getStartMillis(), ImmutableList.of("a", "b"), 1 }).add(new Object[] { INTERVAL.getStartMillis(), ImmutableList.of("a", "c"), 2 }).add(new Object[] { INTERVAL.getStartMillis(), ImmutableList.of("b"), 3 }).add(new Object[] { INTERVAL.getStartMillis(), ImmutableList.of("c"), 4 }).build(), MULTI_VALUE_INLINE_SIGNATURE);

    ObjectMapper objectMapper = new DefaultObjectMapper();

    private FrameBasedInlineDataSource convertToFrameBasedInlineDataSource(InlineDataSource inlineDataSource, RowSignature rowSignature) {
        Pair<Cursor, Closeable> cursorAndCloseable = IterableRowsCursorHelper.getCursorFromIterable(inlineDataSource.getRows(), rowSignature);
        Cursor cursor = cursorAndCloseable.lhs;
        RowSignature modifiedRowSignature = FrameWriterUtils.replaceUnknownTypesWithNestedColumns(rowSignature);
        Sequence<Frame> frames = FrameCursorUtils.cursorToFramesSequence(cursor, FrameWriters.makeRowBasedFrameWriterFactory(new SingleMemoryAllocatorFactory(HeapMemoryAllocator.unlimited()), modifiedRowSignature, new ArrayList<>(), false));
        return new FrameBasedInlineDataSource(frames.map(frame -> new FrameSignaturePair(frame, rowSignature)).withBaggage(cursorAndCloseable.rhs).toList(), modifiedRowSignature);
    }

    private void assertConversionBetweenFrameBasedAndIterableBasedInlineDataSource(FrameBasedInlineDataSource frameBasedInlineDataSource, InlineDataSource inlineDataSource) throws JsonProcessingException {
        String s = objectMapper.writeValueAsString(frameBasedInlineDataSource);
        DataSource back = objectMapper.readValue(s, DataSource.class);
        Assert.assertEquals(inlineDataSource, back);
    }

    @Test
    public void serialize_1() throws JsonProcessingException {
        assertConversionBetweenFrameBasedAndIterableBasedInlineDataSource(convertToFrameBasedInlineDataSource(FOO_INLINE, FOO_INLINE_SIGNATURE), FOO_INLINE);
    }

    @Test
    public void serialize_2() throws JsonProcessingException {
        assertConversionBetweenFrameBasedAndIterableBasedInlineDataSource(convertToFrameBasedInlineDataSource(BAR_INLINE, BAR_INLINE_SIGNATURE), BAR_INLINE);
    }

    @Test
    public void serialize_3() throws JsonProcessingException {
        assertConversionBetweenFrameBasedAndIterableBasedInlineDataSource(convertToFrameBasedInlineDataSource(MULTI_VALUE_INLINE, MULTI_VALUE_INLINE_SIGNATURE), MULTI_VALUE_INLINE);
    }
}
