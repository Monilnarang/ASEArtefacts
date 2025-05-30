package org.graylog2.security.hashing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PBKDF2PasswordAlgorithmTest_Parameterized {

    private static PBKDF2PasswordAlgorithm hasher;

    @BeforeAll
    public static void setUp() throws Exception {
        hasher = new PBKDF2PasswordAlgorithm(PBKDF2PasswordAlgorithm.DEFAULT_ITERATIONS);
    }

    @Test
    void supports_4() {
        assertThat(hasher.supports("{PBKDF2}100%salt%hash")).isTrue();
    }

    @ParameterizedTest
    @MethodSource("Provider_supports_1to3")
    void supports_1to3(String param1) {
        assertThat(hasher.supports(param1)).isFalse();
    }

    static public Stream<Arguments> Provider_supports_1to3() {
        return Stream.of(arguments("some_string"), arguments("{PBKDF2}"), arguments("{PBKDF2}somestring%foo"));
    }
}
