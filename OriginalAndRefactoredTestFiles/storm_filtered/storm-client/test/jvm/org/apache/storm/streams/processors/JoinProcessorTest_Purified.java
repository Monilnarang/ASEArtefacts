package org.apache.storm.streams.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.apache.storm.streams.Pair;
import org.apache.storm.streams.operations.PairValueJoiner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JoinProcessorTest_Purified {

    JoinProcessor<Integer, Pair<Integer, Integer>, Integer, Integer> joinProcessor;

    String leftStream = "left";

    String rightStream = "right";

    List<Pair<Integer, Pair<Integer, Integer>>> res = new ArrayList<>();

    ProcessorContext<Pair<Integer, Pair<Integer, Integer>>> context = new ProcessorContext<Pair<Integer, Pair<Integer, Integer>>>() {

        @Override
        public void forward(Pair<Integer, Pair<Integer, Integer>> input) {
            res.add(input);
        }

        @Override
        public void forward(Pair<Integer, Pair<Integer, Integer>> input, String stream) {
        }

        @Override
        public boolean isWindowed() {
            return true;
        }

        @Override
        public Set<String> getWindowedParentStreams() {
            return null;
        }
    };

    List<Pair<Integer, Integer>> leftKeyValues = Arrays.asList(Pair.of(2, 4), Pair.of(5, 25), Pair.of(7, 49));

    List<Pair<Integer, Integer>> rightKeyValues = Arrays.asList(Pair.of(1, 1), Pair.of(2, 8), Pair.of(5, 125), Pair.of(6, 216));

    private void processValues() {
        res.clear();
        joinProcessor.init(context);
        for (Pair<Integer, Integer> kv : leftKeyValues) {
            joinProcessor.execute(kv, leftStream);
        }
        for (Pair<Integer, Integer> kv : rightKeyValues) {
            joinProcessor.execute(kv, rightStream);
        }
        joinProcessor.finish();
    }

    @Test
    public void testInnerJoin_1() {
        assertEquals(Pair.of(2, Pair.of(4, 8)), res.get(0));
    }

    @Test
    public void testInnerJoin_2() {
        assertEquals(Pair.of(5, Pair.of(25, 125)), res.get(1));
    }

    @Test
    public void testLeftOuterJoin_1() {
        assertEquals(Pair.of(2, Pair.of(4, 8)), res.get(0));
    }

    @Test
    public void testLeftOuterJoin_2() {
        assertEquals(Pair.of(5, Pair.of(25, 125)), res.get(1));
    }

    @Test
    public void testLeftOuterJoin_3() {
        assertEquals(Pair.of(7, Pair.of(49, null)), res.get(2));
    }

    @Test
    public void testRightOuterJoin_1() {
        assertEquals(Pair.of(1, Pair.of(null, 1)), res.get(0));
    }

    @Test
    public void testRightOuterJoin_2() {
        assertEquals(Pair.of(2, Pair.of(4, 8)), res.get(1));
    }

    @Test
    public void testRightOuterJoin_3() {
        assertEquals(Pair.of(5, Pair.of(25, 125)), res.get(2));
    }

    @Test
    public void testRightOuterJoin_4() {
        assertEquals(Pair.of(6, Pair.of(null, 216)), res.get(3));
    }

    @Test
    public void testFullOuterJoin_1() {
        assertEquals(Pair.of(1, Pair.of(null, 1)), res.get(0));
    }

    @Test
    public void testFullOuterJoin_2() {
        assertEquals(Pair.of(2, Pair.of(4, 8)), res.get(1));
    }

    @Test
    public void testFullOuterJoin_3() {
        assertEquals(Pair.of(5, Pair.of(25, 125)), res.get(2));
    }

    @Test
    public void testFullOuterJoin_4() {
        assertEquals(Pair.of(6, Pair.of(null, 216)), res.get(3));
    }

    @Test
    public void testFullOuterJoin_5() {
        assertEquals(Pair.of(7, Pair.of(49, null)), res.get(4));
    }
}
