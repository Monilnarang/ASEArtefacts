package org.graylog2.inputs.converters;

import org.graylog2.ConfigurationException;
import org.junit.Test;
import java.util.Collections;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class SplitAndCountConverterTest_Purified {

    private Map<String, Object> config(final String splitBy) {
        return Collections.singletonMap("split_by", splitBy);
    }

    @Test
    public void testConvert_1() throws Exception {
        assertEquals(0, new SplitAndCountConverter(config("x")).convert(""));
    }

    @Test
    public void testConvert_2() throws Exception {
        assertEquals(1, new SplitAndCountConverter(config("_")).convert("foo-bar-baz"));
    }

    @Test
    public void testConvert_3() throws Exception {
        assertEquals(1, new SplitAndCountConverter(config("-")).convert("foo"));
    }

    @Test
    public void testConvert_4() throws Exception {
        assertEquals(2, new SplitAndCountConverter(config("-")).convert("foo-bar"));
    }

    @Test
    public void testConvert_5() throws Exception {
        assertEquals(3, new SplitAndCountConverter(config("-")).convert("foo-bar-baz"));
    }

    @Test
    public void testConvert_6() throws Exception {
        assertEquals(3, new SplitAndCountConverter(config(".")).convert("foo.bar.baz"));
    }
}
