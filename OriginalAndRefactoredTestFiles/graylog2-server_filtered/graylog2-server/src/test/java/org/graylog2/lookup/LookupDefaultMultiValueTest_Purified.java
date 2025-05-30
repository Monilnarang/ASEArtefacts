package org.graylog2.lookup;

import com.google.common.collect.ImmutableMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Collections;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class LookupDefaultMultiValueTest_Purified {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void createMulti_1() throws Exception {
        isEqualTo(Collections.emptyMap());
    }

    @Test
    public void createMulti_2() throws Exception {
        isEqualTo(ImmutableMap.of("hello", "world", "number", 42));
    }

    @Test
    public void createMulti_3() throws Exception {
        assertThat(LookupDefaultMultiValue.create("something", LookupDefaultMultiValue.Type.NULL).value()).isNull();
    }
}
