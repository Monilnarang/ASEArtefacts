package org.apache.storm.windowing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.storm.generated.GlobalStreamId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WaterMarkEventGeneratorTest_Purified {

    WaterMarkEventGenerator<Integer> waterMarkEventGenerator;

    WindowManager<Integer> windowManager;

    List<Event<Integer>> eventList = new ArrayList<>();

    private GlobalStreamId streamId(String component) {
        return new GlobalStreamId(component, "default");
    }

    @BeforeEach
    public void setUp() {
        windowManager = new WindowManager<Integer>(null) {

            @Override
            public void add(Event<Integer> event) {
                eventList.add(event);
            }
        };
        waterMarkEventGenerator = new WaterMarkEventGenerator<>(windowManager, 100000, 5, Collections.singleton(streamId("s1")));
        waterMarkEventGenerator.start();
    }

    @AfterEach
    public void tearDown() {
        waterMarkEventGenerator.shutdown();
    }

    @Test
    public void testTrackSingleStream_1() {
        assertTrue(eventList.get(0).isWatermark());
    }

    @Test
    public void testTrackSingleStream_2() {
        assertEquals(105, eventList.get(0).getTimestamp());
    }

    @Test
    public void testTrackSingleStreamOutOfOrder_1() {
        assertTrue(eventList.get(0).isWatermark());
    }

    @Test
    public void testTrackSingleStreamOutOfOrder_2() {
        assertEquals(105, eventList.get(0).getTimestamp());
    }

    @Test
    public void testLateEvent_1() {
        assertTrue(waterMarkEventGenerator.track(streamId("s1"), 100));
    }

    @Test
    public void testLateEvent_2() {
        assertTrue(waterMarkEventGenerator.track(streamId("s1"), 110));
    }

    @Test
    public void testLateEvent_3() {
        assertTrue(eventList.get(0).isWatermark());
    }

    @Test
    public void testLateEvent_4() {
        assertEquals(105, eventList.get(0).getTimestamp());
    }

    @Test
    public void testLateEvent_5_testMerged_5() {
        waterMarkEventGenerator.run();
        assertTrue(waterMarkEventGenerator.track(streamId("s1"), 105));
        assertTrue(waterMarkEventGenerator.track(streamId("s1"), 106));
        assertTrue(waterMarkEventGenerator.track(streamId("s1"), 115));
        assertFalse(waterMarkEventGenerator.track(streamId("s1"), 104));
    }

    @Test
    public void testLateEvent_9() {
        assertTrue(eventList.get(0).isWatermark());
    }

    @Test
    public void testLateEvent_10() {
        eventList.clear();
        assertEquals(110, eventList.get(0).getTimestamp());
    }
}
