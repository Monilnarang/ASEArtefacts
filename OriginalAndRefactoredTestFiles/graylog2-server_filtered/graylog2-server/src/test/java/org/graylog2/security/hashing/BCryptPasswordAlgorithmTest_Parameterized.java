package org.graylog2.security.hashing;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BCryptPasswordAlgorithmTest_Parameterized {

    private BCryptPasswordAlgorithm bCryptPasswordAlgorithm;

    @Before
    public void setUp() throws Exception {
        this.bCryptPasswordAlgorithm = new BCryptPasswordAlgorithm(10);
    }

    @Test
    public void testSupports_3() throws Exception {
        assertThat(bCryptPasswordAlgorithm.supports("{bcrypt}foobar{salt}pepper")).isTrue();
    }

    @ParameterizedTest
    @MethodSource("Provider_testSupports_1to2_4")
    public void testSupports_1to2_4(String param1) throws Exception {
        assertThat(bCryptPasswordAlgorithm.supports(param1)).isFalse();
    }

    static public Stream<Arguments> Provider_testSupports_1to2_4() {
        return Stream.of(arguments("foobar"), arguments("{bcrypt}foobar"), arguments("{foobar}foobar"));
    }
}
