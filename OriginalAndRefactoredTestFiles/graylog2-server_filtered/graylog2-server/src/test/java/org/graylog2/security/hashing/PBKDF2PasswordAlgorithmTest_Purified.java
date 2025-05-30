package org.graylog2.security.hashing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PBKDF2PasswordAlgorithmTest_Purified {

    private static PBKDF2PasswordAlgorithm hasher;

    @BeforeAll
    public static void setUp() throws Exception {
        hasher = new PBKDF2PasswordAlgorithm(PBKDF2PasswordAlgorithm.DEFAULT_ITERATIONS);
    }

    @Test
    void supports_1() {
        assertThat(hasher.supports("some_string")).isFalse();
    }

    @Test
    void supports_2() {
        assertThat(hasher.supports("{PBKDF2}")).isFalse();
    }

    @Test
    void supports_3() {
        assertThat(hasher.supports("{PBKDF2}somestring%foo")).isFalse();
    }

    @Test
    void supports_4() {
        assertThat(hasher.supports("{PBKDF2}100%salt%hash")).isTrue();
    }
}
