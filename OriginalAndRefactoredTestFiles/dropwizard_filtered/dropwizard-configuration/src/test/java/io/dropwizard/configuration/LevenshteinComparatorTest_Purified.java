package io.dropwizard.configuration;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class LevenshteinComparatorTest_Purified {

    private final ConfigurationParsingException.Builder.LevenshteinComparator c = new ConfigurationParsingException.Builder.LevenshteinComparator("base");

    @Test
    void testLevenshteinCompare_1() {
        assertThat(c.compare("z", "v")).isZero();
    }

    @Test
    void testLevenshteinCompare_2() {
        assertThat(c.compare("b", "v")).isEqualTo(-1);
    }

    @Test
    void testLevenshteinCompare_3() {
        assertThat(c.compare("v", "b")).isEqualTo(1);
    }
}
