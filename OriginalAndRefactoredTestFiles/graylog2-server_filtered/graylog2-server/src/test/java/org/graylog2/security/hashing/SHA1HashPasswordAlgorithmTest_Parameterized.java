package org.graylog2.security.hashing;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SHA1HashPasswordAlgorithmTest_Parameterized {

    private SHA1HashPasswordAlgorithm SHA1HashPasswordAlgorithm;

    @Before
    public void setUp() throws Exception {
        this.SHA1HashPasswordAlgorithm = new SHA1HashPasswordAlgorithm("passwordSecret");
    }

    @Test
    public void testSupports_1() throws Exception {
        assertThat(SHA1HashPasswordAlgorithm.supports("deadbeefaffedeadbeefdeadbeefaffedeadbeef")).isTrue();
    }

    @ParameterizedTest
    @MethodSource("Provider_testSupports_2to3")
    public void testSupports_2to3(String param1) throws Exception {
        assertThat(SHA1HashPasswordAlgorithm.supports(param1)).isFalse();
    }

    static public Stream<Arguments> Provider_testSupports_2to3() {
        return Stream.of(arguments("{bcrypt}foobar"), arguments("{foobar}foobar"));
    }
}
