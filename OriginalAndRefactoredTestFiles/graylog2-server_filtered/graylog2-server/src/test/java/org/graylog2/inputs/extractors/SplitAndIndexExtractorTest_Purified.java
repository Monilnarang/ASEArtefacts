package org.graylog2.inputs.extractors;

import org.graylog2.ConfigurationException;
import org.graylog2.plugin.Message;
import org.graylog2.plugin.MessageFactory;
import org.graylog2.plugin.TestMessageFactory;
import org.graylog2.plugin.Tools;
import org.graylog2.plugin.inputs.Extractor;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SplitAndIndexExtractorTest_Purified extends AbstractExtractorTest {

    private final MessageFactory messageFactory = new TestMessageFactory();

    public static Map<String, Object> config(final Object splitChar, final Object targetIndex) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("index", targetIndex);
        map.put("split_by", splitChar);
        return map;
    }

    @Test
    public void testCutWorksWithNull_1() throws Exception {
        assertNull(SplitAndIndexExtractor.cut(null, " ", 1));
    }

    @Test
    public void testCutWorksWithNull_2() throws Exception {
        assertNull(SplitAndIndexExtractor.cut("foobar", null, 1));
    }

    @Test
    public void testCutWorksWithNull_3() throws Exception {
        assertNull(SplitAndIndexExtractor.cut("foobar", " ", -1));
    }
}
