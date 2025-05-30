package org.apache.seata.spring.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.core.Ordered;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderUtilTest_Purified {

    @Test
    public void test_lowerThan_1() {
        assertThat(OrderUtil.lowerThan(Ordered.LOWEST_PRECEDENCE, Ordered.HIGHEST_PRECEDENCE)).isTrue();
    }

    @Test
    public void test_lowerThan_2() {
        assertThat(OrderUtil.lowerThan(1, 0)).isTrue();
    }

    @Test
    public void test_lowerThan_3() {
        assertThat(OrderUtil.lowerThan(1, 1)).isFalse();
    }

    @Test
    public void test_lowerThan_4() {
        assertThat(OrderUtil.lowerOrEquals(1, 1)).isTrue();
    }

    @Test
    public void test_lowerThan_5() {
        assertThat(OrderUtil.lowerThan(String.class, Integer.class)).isTrue();
    }

    @Test
    public void test_lowerThan_6() {
        assertThat(OrderUtil.lowerThan(String.class, String.class)).isFalse();
    }

    @Test
    public void test_lowerThan_7() {
        assertThat(OrderUtil.lowerOrEquals(String.class, String.class)).isTrue();
    }

    @Test
    public void test_lowerThan_8_testMerged_8() {
        Advisor advisor11 = new MockAdvisor(1, new MockAdvice1());
        Advisor advisor12 = new MockAdvisor(1, new MockAdvice2());
        Advisor advisor21 = new MockAdvisor(2, new MockAdvice1());
        Advisor advisor22 = new MockAdvisor(2, new MockAdvice2());
        assertThat(OrderUtil.lowerThan(advisor11, advisor11)).isFalse();
        assertThat(OrderUtil.lowerThan(advisor12, advisor11)).isTrue();
        assertThat(OrderUtil.lowerThan(advisor21, advisor12)).isTrue();
        assertThat(OrderUtil.lowerThan(advisor22, advisor21)).isTrue();
        assertThat(OrderUtil.lowerOrEquals(advisor11, advisor11)).isTrue();
        assertThat(OrderUtil.lowerOrEquals(advisor21, advisor11)).isTrue();
        assertThat(OrderUtil.lowerOrEquals(advisor12, advisor11)).isTrue();
        assertThat(OrderUtil.lowerOrEquals(advisor22, advisor21)).isTrue();
    }

    @Test
    public void test_higherThan_1() {
        assertThat(OrderUtil.higherThan(Ordered.HIGHEST_PRECEDENCE, Ordered.LOWEST_PRECEDENCE)).isTrue();
    }

    @Test
    public void test_higherThan_2() {
        assertThat(OrderUtil.higherThan(0, 1)).isTrue();
    }

    @Test
    public void test_higherThan_3() {
        assertThat(OrderUtil.higherThan(1, 1)).isFalse();
    }

    @Test
    public void test_higherThan_4() {
        assertThat(OrderUtil.higherOrEquals(1, 1)).isTrue();
    }

    @Test
    public void test_higherThan_5() {
        assertThat(OrderUtil.higherThan(Integer.class, String.class)).isTrue();
    }

    @Test
    public void test_higherThan_6() {
        assertThat(OrderUtil.higherThan(String.class, String.class)).isFalse();
    }

    @Test
    public void test_higherThan_7() {
        assertThat(OrderUtil.higherOrEquals(String.class, String.class)).isTrue();
    }

    @Test
    public void test_higherThan_8_testMerged_8() {
        Advisor advisor11 = new MockAdvisor(1, new MockAdvice1());
        Advisor advisor12 = new MockAdvisor(1, new MockAdvice2());
        Advisor advisor21 = new MockAdvisor(2, new MockAdvice1());
        Advisor advisor22 = new MockAdvisor(2, new MockAdvice2());
        assertThat(OrderUtil.higherThan(advisor11, advisor11)).isFalse();
        assertThat(OrderUtil.higherThan(advisor11, advisor12)).isTrue();
        assertThat(OrderUtil.higherThan(advisor12, advisor21)).isTrue();
        assertThat(OrderUtil.higherThan(advisor21, advisor22)).isTrue();
        assertThat(OrderUtil.higherOrEquals(advisor11, advisor11)).isTrue();
        assertThat(OrderUtil.higherOrEquals(advisor11, advisor21)).isTrue();
        assertThat(OrderUtil.higherOrEquals(advisor11, advisor12)).isTrue();
        assertThat(OrderUtil.higherOrEquals(advisor21, advisor22)).isTrue();
    }
}
