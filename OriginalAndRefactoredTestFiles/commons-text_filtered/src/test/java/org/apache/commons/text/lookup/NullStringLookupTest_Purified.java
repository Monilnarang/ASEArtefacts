package org.apache.commons.text.lookup;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NullStringLookupTest_Purified {

    @Test
    public void test_1() {
        Assertions.assertNull(StringLookupFactory.INSTANCE_NULL.lookup("EverythingIsNull"));
    }

    @Test
    public void test_2() {
        Assertions.assertNull(StringLookupFactory.INSTANCE_NULL.lookup(null));
    }
}
