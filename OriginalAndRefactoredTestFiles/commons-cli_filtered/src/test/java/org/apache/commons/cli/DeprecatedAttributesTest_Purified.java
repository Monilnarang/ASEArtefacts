package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DeprecatedAttributesTest_Purified {

    @Test
    public void testBuilderNonDefaultsToString_1() {
        assertEquals("Deprecated for removal since 2.0: Use Bar instead!", DeprecatedAttributes.builder().setDescription("Use Bar instead!").setForRemoval(true).setSince("2.0").get().toString());
    }

    @Test
    public void testBuilderNonDefaultsToString_2() {
        assertEquals("Deprecated for removal: Use Bar instead!", DeprecatedAttributes.builder().setDescription("Use Bar instead!").setForRemoval(true).get().toString());
    }

    @Test
    public void testBuilderNonDefaultsToString_3() {
        assertEquals("Deprecated since 2.0: Use Bar instead!", DeprecatedAttributes.builder().setDescription("Use Bar instead!").setSince("2.0").get().toString());
    }

    @Test
    public void testBuilderNonDefaultsToString_4() {
        assertEquals("Deprecated: Use Bar instead!", DeprecatedAttributes.builder().setDescription("Use Bar instead!").get().toString());
    }
}
