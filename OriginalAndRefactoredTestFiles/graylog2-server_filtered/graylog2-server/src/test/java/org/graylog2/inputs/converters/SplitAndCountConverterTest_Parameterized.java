package org.graylog2.inputs.converters;

import org.graylog2.ConfigurationException;
import org.junit.Test;
import java.util.Collections;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SplitAndCountConverterTest_Parameterized {

    private Map<String, Object> config(final String splitBy) {
        return Collections.singletonMap("split_by", splitBy);
    }

    @ParameterizedTest
    @MethodSource("Provider_testConvert_1to6")
    public void testConvert_1to6(int param1, String param2, String param3) throws Exception {
        assertEquals(param1, new SplitAndCountConverter(config(param3)).convert(param2));
    }

    static public Stream<Arguments> Provider_testConvert_1to6() {
        return Stream.of(arguments(0, "", "x"), arguments(1, "foo-bar-baz", "_"), arguments(1, "foo", "-"), arguments(2, "foo-bar", "-"), arguments(3, "foo-bar-baz", "-"), arguments(3, "foo.bar.baz", "."));
    }
}
