package org.graylog2.security.hashing;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BCryptPasswordAlgorithmTest_Purified {

    private BCryptPasswordAlgorithm bCryptPasswordAlgorithm;

    @Before
    public void setUp() throws Exception {
        this.bCryptPasswordAlgorithm = new BCryptPasswordAlgorithm(10);
    }

    @Test
    public void testSupports_1() throws Exception {
        assertThat(bCryptPasswordAlgorithm.supports("foobar")).isFalse();
    }

    @Test
    public void testSupports_2() throws Exception {
        assertThat(bCryptPasswordAlgorithm.supports("{bcrypt}foobar")).isFalse();
    }

    @Test
    public void testSupports_3() throws Exception {
        assertThat(bCryptPasswordAlgorithm.supports("{bcrypt}foobar{salt}pepper")).isTrue();
    }

    @Test
    public void testSupports_4() throws Exception {
        assertThat(bCryptPasswordAlgorithm.supports("{foobar}foobar")).isFalse();
    }
}
