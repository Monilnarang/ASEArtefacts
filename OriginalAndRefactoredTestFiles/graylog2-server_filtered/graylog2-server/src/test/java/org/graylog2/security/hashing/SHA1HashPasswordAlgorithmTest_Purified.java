package org.graylog2.security.hashing;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SHA1HashPasswordAlgorithmTest_Purified {

    private SHA1HashPasswordAlgorithm SHA1HashPasswordAlgorithm;

    @Before
    public void setUp() throws Exception {
        this.SHA1HashPasswordAlgorithm = new SHA1HashPasswordAlgorithm("passwordSecret");
    }

    @Test
    public void testSupports_1() throws Exception {
        assertThat(SHA1HashPasswordAlgorithm.supports("deadbeefaffedeadbeefdeadbeefaffedeadbeef")).isTrue();
    }

    @Test
    public void testSupports_2() throws Exception {
        assertThat(SHA1HashPasswordAlgorithm.supports("{bcrypt}foobar")).isFalse();
    }

    @Test
    public void testSupports_3() throws Exception {
        assertThat(SHA1HashPasswordAlgorithm.supports("{foobar}foobar")).isFalse();
    }
}
