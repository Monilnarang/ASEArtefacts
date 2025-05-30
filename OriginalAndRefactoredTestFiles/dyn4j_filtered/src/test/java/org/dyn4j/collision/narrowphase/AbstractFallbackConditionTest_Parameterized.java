package org.dyn4j.collision.narrowphase;

import org.dyn4j.geometry.Convex;
import org.junit.Test;
import junit.framework.TestCase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AbstractFallbackConditionTest_Parameterized {

    private class TestCondition extends AbstractFallbackCondition {

        public TestCondition(int index) {
            super(index);
        }

        @Override
        public boolean isMatch(Convex convex1, Convex convex2) {
            return false;
        }
    }

    private class TestTypedFallbackCondition extends TypedFallbackCondition {

        @Override
        public boolean isMatch(Class<? extends Convex> type1, Class<? extends Convex> type2) {
            return false;
        }
    }

    @Test
    public void create_2() {
        TestTypedFallbackCondition tc2 = new TestTypedFallbackCondition();
        TestCase.assertEquals(0, tc2.getSortIndex());
    }

    @Test
    public void getSortIndex_2() {
        TestCondition tc2 = new TestCondition(2);
        TestCase.assertEquals(2, tc2.getSortIndex());
    }

    @Test
    public void getSortIndex_3() {
        TestCondition tc3 = new TestCondition(5);
        TestCase.assertEquals(5, tc3.getSortIndex());
    }

    @Test
    public void getSortIndex_4() {
        TestCondition tc4 = new TestCondition(-1);
        TestCase.assertEquals(-1, tc4.getSortIndex());
    }

    @Test
    public void getSortIndex_5() {
        TestCondition tc5 = new TestCondition(2);
        TestCase.assertEquals(2, tc5.getSortIndex());
    }

    @Test
    public void compareTo_1_testMerged_1() {
        TestCondition tc1 = new TestCondition(1);
        TestCondition tc3 = new TestCondition(5);
        TestCondition tc4 = new TestCondition(-1);
        TestCase.assertTrue(tc1.compareTo(tc4) > 0);
        TestCase.assertTrue(tc4.compareTo(tc1) < 0);
        TestCase.assertTrue(tc1.compareTo(tc3) < 0);
        TestCase.assertTrue(tc3.compareTo(tc1) > 0);
    }

    @Test
    public void compareTo_3_testMerged_2() {
        TestCondition tc2 = new TestCondition(2);
        TestCondition tc5 = new TestCondition(2);
        TestCase.assertEquals(0, tc2.compareTo(tc5));
        TestCase.assertEquals(0, tc5.compareTo(tc2));
    }

    @ParameterizedTest
    @MethodSource("Provider_create_1_1")
    public void create_1_1(int param1, int param2) {
        TestCondition tc1 = new TestCondition(param2);
        TestCase.assertEquals(param1, tc1.getSortIndex());
    }

    static public Stream<Arguments> Provider_create_1_1() {
        return Stream.of(arguments(1, 1), arguments(1, 1));
    }
}
