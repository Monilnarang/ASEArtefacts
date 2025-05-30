package org.apache.commons.lang3.compare;

import static org.apache.commons.lang3.compare.ComparableUtils.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.time.Instant;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ComparableUtilsTest_Purified extends AbstractLangTest {

    @Nested
    final class A_is_1 {

        @DisplayName("B is 0 (B < A)")
        @Nested
        final class B_is_0 {

            @DisplayName("C is 0 ([B=C] < A)")
            @Nested
            final class C_is_0 {

                BigDecimal c = BigDecimal.ZERO;

                @Test
                void between_returns_false() {
                    assertFalse(is(a).between(b, c));
                }

                @Test
                void betweenExclusive_returns_false() {
                    assertFalse(is(a).betweenExclusive(b, c));
                }

                @Test
                void static_between_returns_false() {
                    assertFalse(ComparableUtils.between(b, c).test(a));
                }

                @Test
                void static_betweenExclusive_returns_false() {
                    assertFalse(ComparableUtils.betweenExclusive(b, c).test(a));
                }
            }

            @DisplayName("C is 1 (B < A = C)")
            @Nested
            final class C_is_1 {

                BigDecimal c = BigDecimal.ONE;

                @Test
                void between_returns_true() {
                    assertTrue(is(a).between(b, c));
                }

                @Test
                void betweenExclusive_returns_false() {
                    assertFalse(is(a).betweenExclusive(b, c));
                }

                @Test
                void static_between_returns_true() {
                    assertTrue(ComparableUtils.between(b, c).test(a));
                }

                @Test
                void static_betweenExclusive_returns_false() {
                    assertFalse(ComparableUtils.betweenExclusive(b, c).test(a));
                }
            }

            @DisplayName("C is 10 (B < A < C)")
            @Nested
            final class C_is_10 {

                BigDecimal c = BigDecimal.TEN;

                @Test
                void between_returns_true() {
                    assertTrue(is(a).between(b, c));
                }

                @Test
                void betweenExclusive_returns_true() {
                    assertTrue(is(a).betweenExclusive(b, c));
                }

                @Test
                void static_between_returns_true() {
                    assertTrue(ComparableUtils.between(b, c).test(a));
                }

                @Test
                void static_betweenExclusive_returns_true() {
                    assertTrue(ComparableUtils.betweenExclusive(b, c).test(a));
                }
            }

            BigDecimal b = BigDecimal.ZERO;

            @Test
            void equalTo_returns_false() {
                assertFalse(is(a).equalTo(b));
            }

            @Test
            void greaterThan_returns_true() {
                assertTrue(is(a).greaterThan(b));
            }

            @Test
            void greaterThanOrEqualTo_returns_true() {
                assertTrue(is(a).greaterThanOrEqualTo(b));
            }

            @Test
            void lessThan_returns_false() {
                assertFalse(is(a).lessThan(b));
            }

            @Test
            void lessThanOrEqualTo_returns_false() {
                assertFalse(is(a).lessThanOrEqualTo(b));
            }

            @Test
            void static_ge_returns_true() {
                assertTrue(ComparableUtils.ge(b).test(a));
            }

            @Test
            void static_gt_returns_true() {
                assertTrue(ComparableUtils.gt(b).test(a));
            }

            @Test
            void static_le_returns_false() {
                assertFalse(ComparableUtils.le(b).test(a));
            }

            @Test
            void static_lt_returns_false() {
                assertFalse(ComparableUtils.lt(b).test(a));
            }
        }

        @DisplayName("B is 1 (B = A)")
        @Nested
        final class B_is_1 {

            @DisplayName("C is 0 (B = A > C)")
            @Nested
            final class C_is_0 {

                BigDecimal c = BigDecimal.ZERO;

                @Test
                void between_returns_true() {
                    assertTrue(is(a).between(b, c));
                }

                @Test
                void betweenExclusive_returns_false() {
                    assertFalse(is(a).betweenExclusive(b, c));
                }

                @Test
                void static_between_returns_true() {
                    assertTrue(ComparableUtils.between(b, c).test(a));
                }

                @Test
                void static_betweenExclusive_returns_false() {
                    assertFalse(ComparableUtils.betweenExclusive(b, c).test(a));
                }
            }

            @DisplayName("C is 1 (B = A = C)")
            @Nested
            final class C_is_1 {

                BigDecimal c = BigDecimal.ONE;

                @Test
                void between_returns_true() {
                    assertTrue(is(a).between(b, c));
                }

                @Test
                void betweenExclusive_returns_false() {
                    assertFalse(is(a).betweenExclusive(b, c));
                }

                @Test
                void static_between_returns_true() {
                    assertTrue(ComparableUtils.between(b, c).test(a));
                }

                @Test
                void static_betweenExclusive_returns_false() {
                    assertFalse(ComparableUtils.betweenExclusive(b, c).test(a));
                }
            }

            @DisplayName("C is 10 (B = A < C)")
            @Nested
            final class C_is_10 {

                BigDecimal c = BigDecimal.TEN;

                @Test
                void between_returns_true() {
                    assertTrue(is(a).between(b, c));
                }

                @Test
                void betweenExclusive_returns_false() {
                    assertFalse(is(a).betweenExclusive(b, c));
                }

                @Test
                void static_between_returns_true() {
                    assertTrue(ComparableUtils.between(b, c).test(a));
                }

                @Test
                void static_betweenExclusive_returns_false() {
                    assertFalse(ComparableUtils.betweenExclusive(b, c).test(a));
                }
            }

            BigDecimal b = BigDecimal.ONE;

            @Test
            void equalTo_returns_true() {
                assertTrue(is(a).equalTo(b));
            }

            @Test
            void greaterThan_returns_false() {
                assertFalse(is(a).greaterThan(b));
            }

            @Test
            void greaterThanOrEqualTo_returns_true() {
                assertTrue(is(a).greaterThanOrEqualTo(b));
            }

            @Test
            void lessThan_returns_false() {
                assertFalse(is(a).lessThan(b));
            }

            @Test
            void lessThanOrEqualTo_returns_true() {
                assertTrue(is(a).lessThanOrEqualTo(b));
            }

            @Test
            void static_ge_returns_true() {
                assertTrue(ComparableUtils.ge(b).test(a));
            }

            @Test
            void static_gt_returns_false() {
                assertFalse(ComparableUtils.gt(b).test(a));
            }

            @Test
            void static_le_returns_true() {
                assertTrue(ComparableUtils.le(b).test(a));
            }

            @Test
            void static_lt_returns_false() {
                assertFalse(ComparableUtils.lt(b).test(a));
            }
        }

        @DisplayName("B is 10 (B > A)")
        @Nested
        final class B_is_10 {

            @DisplayName("C is 0 (B > A > C)")
            @Nested
            final class C_is_0 {

                BigDecimal c = BigDecimal.ZERO;

                @Test
                void between_returns_true() {
                    assertTrue(is(a).between(b, c));
                }

                @Test
                void betweenExclusive_returns_true() {
                    assertTrue(is(a).betweenExclusive(b, c));
                }

                @Test
                void static_between_returns_true() {
                    assertTrue(ComparableUtils.between(b, c).test(a));
                }

                @Test
                void static_betweenExclusive_returns_true() {
                    assertTrue(ComparableUtils.betweenExclusive(b, c).test(a));
                }
            }

            @DisplayName("C is 1 (B > A = C)")
            @Nested
            final class C_is_1 {

                BigDecimal c = BigDecimal.ONE;

                @Test
                void between_returns_true() {
                    assertTrue(is(a).between(b, c));
                }

                @Test
                void betweenExclusive_returns_false() {
                    assertFalse(is(a).betweenExclusive(b, c));
                }

                @Test
                void static_between_returns_true() {
                    assertTrue(ComparableUtils.between(b, c).test(a));
                }

                @Test
                void static_betweenExclusive_returns_false() {
                    assertFalse(ComparableUtils.betweenExclusive(b, c).test(a));
                }
            }

            @DisplayName("C is 10 ([B,C] > A)")
            @Nested
            final class C_is_10 {

                BigDecimal c = BigDecimal.TEN;

                @Test
                void between_returns_false() {
                    assertFalse(is(a).between(b, c));
                }

                @Test
                void betweenExclusive_returns_false() {
                    assertFalse(is(a).betweenExclusive(b, c));
                }

                @Test
                void static_between_returns_false() {
                    assertFalse(ComparableUtils.between(b, c).test(a));
                }

                @Test
                void static_betweenExclusive_returns_false() {
                    assertFalse(ComparableUtils.betweenExclusive(b, c).test(a));
                }
            }

            BigDecimal b = BigDecimal.TEN;

            @Test
            void equalTo_returns_false() {
                assertFalse(is(a).equalTo(b));
            }

            @Test
            void greaterThan_returns_false() {
                assertFalse(is(a).greaterThan(b));
            }

            @Test
            void greaterThanOrEqualTo_returns_false() {
                assertFalse(is(a).greaterThanOrEqualTo(b));
            }

            @Test
            void lessThan_returns_true() {
                assertTrue(is(a).lessThan(b));
            }

            @Test
            void lessThanOrEqualTo_returns_true() {
                assertTrue(is(a).lessThanOrEqualTo(b));
            }

            @Test
            void static_ge_returns_false() {
                assertFalse(ComparableUtils.ge(b).test(a));
            }

            @Test
            void static_gt_returns_false() {
                assertFalse(ComparableUtils.gt(b).test(a));
            }

            @Test
            void static_le_returns_true() {
                assertTrue(ComparableUtils.le(b).test(a));
            }

            @Test
            void static_lt_returns_true() {
                assertTrue(ComparableUtils.lt(b).test(a));
            }
        }

        BigDecimal a = BigDecimal.ONE;
    }

    @Test
    public void testMax_1() {
        assertEquals(Instant.MAX, ComparableUtils.max(Instant.MAX, Instant.MAX));
    }

    @Test
    public void testMax_2() {
        assertEquals(Instant.MIN, ComparableUtils.max(Instant.MIN, Instant.MIN));
    }

    @Test
    public void testMax_3() {
        assertEquals(Instant.MAX, ComparableUtils.max(Instant.MIN, Instant.MAX));
    }

    @Test
    public void testMax_4() {
        assertEquals(Instant.MAX, ComparableUtils.max(Instant.MAX, Instant.MIN));
    }

    @Test
    public void testMax_5() {
        assertEquals(Integer.MIN_VALUE, ComparableUtils.max(Integer.valueOf(Integer.MIN_VALUE), Integer.valueOf(Integer.MIN_VALUE)));
    }

    @Test
    public void testMax_6() {
        assertEquals(Integer.MAX_VALUE, ComparableUtils.max(Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    public void testMax_7() {
        assertEquals(Integer.MAX_VALUE, ComparableUtils.max(Integer.valueOf(Integer.MIN_VALUE), Integer.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    public void testMax_8() {
        assertEquals(Integer.MAX_VALUE, ComparableUtils.max(Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(Integer.MIN_VALUE)));
    }

    @Test
    public void testMax_9() {
        assertEquals(Instant.MAX, ComparableUtils.max(null, Instant.MAX));
    }

    @Test
    public void testMax_10() {
        assertEquals(Instant.MAX, ComparableUtils.max(Instant.MAX, null));
    }

    @Test
    public void testMin_1() {
        assertEquals(Instant.MAX, ComparableUtils.min(Instant.MAX, Instant.MAX));
    }

    @Test
    public void testMin_2() {
        assertEquals(Instant.MIN, ComparableUtils.min(Instant.MIN, Instant.MIN));
    }

    @Test
    public void testMin_3() {
        assertEquals(Instant.MIN, ComparableUtils.min(Instant.MIN, Instant.MAX));
    }

    @Test
    public void testMin_4() {
        assertEquals(Instant.MIN, ComparableUtils.min(Instant.MAX, Instant.MIN));
    }

    @Test
    public void testMin_5() {
        assertEquals(Integer.MIN_VALUE, ComparableUtils.min(Integer.valueOf(Integer.MIN_VALUE), Integer.valueOf(Integer.MIN_VALUE)));
    }

    @Test
    public void testMin_6() {
        assertEquals(Integer.MAX_VALUE, ComparableUtils.min(Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    public void testMin_7() {
        assertEquals(Integer.MIN_VALUE, ComparableUtils.min(Integer.valueOf(Integer.MIN_VALUE), Integer.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    public void testMin_8() {
        assertEquals(Integer.MIN_VALUE, ComparableUtils.min(Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(Integer.MIN_VALUE)));
    }

    @Test
    public void testMin_9() {
        assertEquals(Instant.MAX, ComparableUtils.min(null, Instant.MAX));
    }

    @Test
    public void testMin_10() {
        assertEquals(Instant.MAX, ComparableUtils.min(Instant.MAX, null));
    }
}
