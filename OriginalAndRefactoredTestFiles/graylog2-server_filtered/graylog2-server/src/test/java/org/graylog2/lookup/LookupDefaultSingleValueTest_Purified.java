package org.graylog2.lookup;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.assertj.core.api.Assertions.assertThat;

public class LookupDefaultSingleValueTest_Purified {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void createSingle_1() throws Exception {
        assertThat(LookupDefaultSingleValue.create("foo", LookupDefaultSingleValue.Type.STRING).value()).isEqualTo("foo");
    }

    @Test
    public void createSingle_2() throws Exception {
        assertThat(LookupDefaultSingleValue.create("123", LookupDefaultSingleValue.Type.STRING).value()).isEqualTo("123");
    }

    @Test
    public void createSingle_3() throws Exception {
    }

    @Test
    public void createSingle_4() throws Exception {
    }

    @Test
    public void createSingle_5() throws Exception {
    }

    @Test
    public void createSingle_6() throws Exception {
    }

    @Test
    public void createSingle_7() throws Exception {
    }

    @Test
    public void createSingle_8() throws Exception {
        assertThat(LookupDefaultSingleValue.create("something", LookupDefaultSingleValue.Type.NULL).value()).isNull();
    }
}
