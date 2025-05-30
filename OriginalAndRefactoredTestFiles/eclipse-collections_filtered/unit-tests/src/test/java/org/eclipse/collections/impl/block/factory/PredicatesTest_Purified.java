package org.eclipse.collections.impl.block.factory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.utility.ListIterate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PredicatesTest_Purified {

    private Employee alice;

    private Employee bob;

    private Employee charlie;

    private Employee diane;

    private MutableList<Employee> employees;

    @BeforeEach
    public void setUp() {
        this.alice = new Employee(new Address(State.ARIZONA));
        this.alice.addDependent(new Dependent(DependentType.SPOUSE));
        this.alice.addDependent(new Dependent(DependentType.CHILD));
        this.alice.addDependent(new Dependent(DependentType.CHILD));
        this.alice.addDependent(new Dependent(DependentType.PARENT));
        this.bob = new Employee(new Address(State.ALASKA));
        this.bob.addDependent(new Dependent(DependentType.SPOUSE));
        this.bob.addDependent(new Dependent(DependentType.CHILD));
        this.charlie = new Employee(new Address(State.ARIZONA));
        this.charlie.addDependent(new Dependent(DependentType.SPOUSE));
        this.charlie.addDependent(new Dependent(DependentType.CHILD));
        this.charlie.addDependent(new Dependent(DependentType.CHILD));
        this.diane = new Employee(new Address(State.ALASKA));
        this.diane.addDependent(new Dependent(DependentType.SPOUSE));
        this.diane.addDependent(new Dependent(DependentType.PARENT));
        this.diane.addDependent(new Dependent(DependentType.PARENT));
        this.diane.addDependent(new Dependent(DependentType.GRANDPARENT));
        this.employees = FastList.newListWith(this.alice, this.bob, this.charlie, this.diane);
    }

    private MyRuntimeException throwMyException(Object each, Throwable exception) {
        return new MyRuntimeException(String.valueOf(each), exception);
    }

    private static void assertIf(Predicate<List<Object>> predicate, boolean bool) {
        assertEquals(bool, predicate.accept(Lists.fixedSize.of()));
        assertEquals(!bool, predicate.accept(FastList.newListWith((Object) null)));
        PredicatesTest.assertToString(predicate);
    }

    private static void assertToString(Predicate<?> predicate) {
        String toString = predicate.toString();
        assertTrue(toString.startsWith("Predicates"));
    }

    private static <T> void assertAccepts(Predicate<? super T> predicate, T... elements) {
        for (T element : elements) {
            assertTrue(predicate.accept(element));
        }
    }

    private static <T> void assertRejects(Predicate<? super T> predicate, T... elements) {
        for (T element : elements) {
            assertFalse(predicate.accept(element));
        }
    }

    private static void assertBetweenInclusive(Predicate<Integer> oneToThree) {
        PredicatesTest.assertRejects(oneToThree, 0, 4);
        PredicatesTest.assertAccepts(oneToThree, 1, 2, 3);
    }

    private static void assertStringBetweenInclusive(Predicate<String> oneToThree) {
        PredicatesTest.assertRejects(oneToThree, "0", "4");
        PredicatesTest.assertAccepts(oneToThree, "1", "2", "3");
    }

    private static void assertBetweenInclusiveFrom(Predicate<Integer> oneToThree) {
        PredicatesTest.assertRejects(oneToThree, 0, 3, 4);
        PredicatesTest.assertAccepts(oneToThree, 1, 2);
    }

    private static void assertStringBetweenInclusiveFrom(Predicate<String> oneToThree) {
        PredicatesTest.assertRejects(oneToThree, "0", "3", "4");
        PredicatesTest.assertAccepts(oneToThree, "1", "2");
    }

    private static void assertBetweenInclusiveTo(Predicate<Integer> oneToThree) {
        PredicatesTest.assertRejects(oneToThree, 0, 1, 4);
        PredicatesTest.assertAccepts(oneToThree, 2, 3);
    }

    private static void assertStringBetweenInclusiveTo(Predicate<String> oneToThree) {
        PredicatesTest.assertRejects(oneToThree, "0", "1", "4");
        PredicatesTest.assertAccepts(oneToThree, "2", "3");
    }

    private static void assertBetweenExclusive(Predicate<Integer> oneToThree) {
        PredicatesTest.assertRejects(oneToThree, 0, 1, 3, 4);
        PredicatesTest.assertAccepts(oneToThree, 2);
    }

    private static void assertStringBetweenExclusive(Predicate<String> oneToThree) {
        PredicatesTest.assertRejects(oneToThree, "0", "1", "3", "4");
        PredicatesTest.assertAccepts(oneToThree, "2");
    }

    public static final class Employee {

        public static final Function<Employee, MutableList<Address>> TO_ADDRESSES = employee -> employee.addresses;

        public static final Function<Employee, MutableList<Dependent>> TO_DEPENEDENTS = employee -> employee.dependents;

        private final MutableList<Address> addresses;

        private final MutableList<Dependent> dependents = Lists.mutable.of();

        private Employee(Address addr) {
            this.addresses = FastList.newListWith(addr);
        }

        private void addDependent(Dependent dependent) {
            this.dependents.add(dependent);
        }
    }

    public static final class Address {

        private final State state;

        private Address(State state) {
            this.state = state;
        }

        public State getState() {
            return this.state;
        }
    }

    public enum State {

        ARIZONA("AZ"), ALASKA("AK"), ALABAMA("AL");

        private final String abbreviation;

        State(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAbbreviation() {
            return this.abbreviation;
        }
    }

    public static final class Dependent {

        public static final Predicate<Dependent> IS_IMMEDIATE = Dependent::isImmediate;

        private final DependentType type;

        private Dependent(DependentType type) {
            this.type = type;
        }

        public boolean isImmediate() {
            return this.type.isImmediate();
        }
    }

    public enum DependentType {

        SPOUSE {

            @Override
            public boolean isImmediate() {
                return true;
            }
        }
        , CHILD {

            @Override
            public boolean isImmediate() {
                return true;
            }
        }
        , PARENT {

            @Override
            public boolean isImmediate() {
                return false;
            }
        }
        , GRANDPARENT {

            @Override
            public boolean isImmediate() {
                return false;
            }
        }
        ;

        public abstract boolean isImmediate();
    }

    private static class MyRuntimeException extends RuntimeException {

        MyRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Test
    public void alwaysTrue_1() {
        PredicatesTest.assertAccepts(Predicates.alwaysTrue(), (Object) null);
    }

    @Test
    public void alwaysTrue_2() {
        PredicatesTest.assertToString(Predicates.alwaysTrue());
    }

    @Test
    public void alwaysFalse_1() {
        PredicatesTest.assertRejects(Predicates.alwaysFalse(), (Object) null);
    }

    @Test
    public void alwaysFalse_2() {
        PredicatesTest.assertToString(Predicates.alwaysFalse());
    }

    @Test
    public void instanceNot_1() {
        PredicatesTest.assertRejects(Predicates.alwaysTrue().not(), (Object) null);
    }

    @Test
    public void instanceNot_2() {
        PredicatesTest.assertToString(Predicates.alwaysTrue().not());
    }

    @Test
    public void adapt_1() {
        PredicatesTest.assertAccepts(Predicates.adapt(Predicates.alwaysTrue()), new Object());
    }

    @Test
    public void adapt_2() {
        PredicatesTest.assertToString(Predicates.adapt(Predicates.alwaysTrue()));
    }

    @Test
    public void staticOr_1() {
        PredicatesTest.assertAccepts(Predicates.or(Predicates.alwaysTrue(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void staticOr_2() {
        PredicatesTest.assertRejects(Predicates.or(Predicates.alwaysFalse(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void staticOr_3() {
        PredicatesTest.assertAccepts(Predicates.or(Predicates.alwaysTrue(), Predicates.alwaysTrue()), new Object());
    }

    @Test
    public void staticOr_4() {
        PredicatesTest.assertToString(Predicates.or(Predicates.alwaysTrue(), Predicates.alwaysTrue()));
    }

    @Test
    public void instanceOr_1() {
        PredicatesTest.assertAccepts(Predicates.alwaysTrue().or(Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void instanceOr_2() {
        PredicatesTest.assertRejects(Predicates.alwaysFalse().or(Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void instanceOr_3() {
        PredicatesTest.assertAccepts(Predicates.alwaysTrue().or(Predicates.alwaysTrue()), new Object());
    }

    @Test
    public void instanceOr_4() {
        PredicatesTest.assertToString(Predicates.alwaysTrue().or(Predicates.alwaysTrue()));
    }

    @Test
    public void varArgOr_1() {
        PredicatesTest.assertAccepts(Predicates.or(Predicates.alwaysTrue(), Predicates.alwaysFalse(), null), new Object());
    }

    @Test
    public void varArgOr_2() {
        PredicatesTest.assertRejects(Predicates.or(Predicates.alwaysFalse(), Predicates.alwaysFalse(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void varArgOr_3() {
        PredicatesTest.assertAccepts(Predicates.or(Predicates.alwaysTrue(), Predicates.alwaysTrue(), Predicates.alwaysTrue()), new Object());
    }

    @Test
    public void varArgOr_4() {
        PredicatesTest.assertToString(Predicates.or(Predicates.alwaysTrue(), Predicates.alwaysTrue(), Predicates.alwaysTrue()));
    }

    @Test
    public void staticAnd_1() {
        PredicatesTest.assertAccepts(Predicates.and(Predicates.alwaysTrue(), Predicates.alwaysTrue()), new Object());
    }

    @Test
    public void staticAnd_2() {
        PredicatesTest.assertRejects(Predicates.and(Predicates.alwaysTrue(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void staticAnd_3() {
        PredicatesTest.assertRejects(Predicates.and(Predicates.alwaysFalse(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void staticAnd_4() {
        PredicatesTest.assertToString(Predicates.and(Predicates.alwaysTrue(), Predicates.alwaysTrue()));
    }

    @Test
    public void instanceAnd_1() {
        PredicatesTest.assertAccepts(Predicates.alwaysTrue().and(Predicates.alwaysTrue()), new Object());
    }

    @Test
    public void instanceAnd_2() {
        PredicatesTest.assertRejects(Predicates.alwaysTrue().and(Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void instanceAnd_3() {
        PredicatesTest.assertRejects(Predicates.alwaysFalse().and(Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void instanceAnd_4() {
        PredicatesTest.assertToString(Predicates.alwaysTrue().and(Predicates.alwaysTrue()));
    }

    @Test
    public void varArgAnd_1() {
        PredicatesTest.assertAccepts(Predicates.and(Predicates.alwaysTrue(), Predicates.alwaysTrue()), new Object());
    }

    @Test
    public void varArgAnd_2() {
        PredicatesTest.assertRejects(Predicates.and(Predicates.alwaysTrue(), Predicates.alwaysTrue(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void varArgAnd_3() {
        PredicatesTest.assertRejects(Predicates.and(Predicates.alwaysFalse(), Predicates.alwaysFalse(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void varArgAnd_4() {
        PredicatesTest.assertToString(Predicates.and(Predicates.alwaysTrue(), Predicates.alwaysTrue(), null));
    }

    @Test
    public void notEqual_1() {
        PredicatesTest.assertRejects(Predicates.notEqual(1), 1);
    }

    @Test
    public void notEqual_2() {
        PredicatesTest.assertAccepts(Predicates.notEqual(1), 2);
    }

    @Test
    public void notEqual_3() {
        PredicatesTest.assertRejects(Predicates.notEqual("test"), "test");
    }

    @Test
    public void notEqual_4() {
        PredicatesTest.assertAccepts(Predicates.notEqual("test"), "production");
    }

    @Test
    public void notEqual_5() {
        PredicatesTest.assertAccepts(Predicates.notEqual(null), "test");
    }

    @Test
    public void notEqual_6() {
        PredicatesTest.assertToString(Predicates.notEqual(1));
    }

    @Test
    public void testNull_1() {
        PredicatesTest.assertAccepts(Predicates.isNull(), (Object) null);
    }

    @Test
    public void testNull_2() {
        PredicatesTest.assertRejects(Predicates.isNull(), new Object());
    }

    @Test
    public void testNull_3() {
        PredicatesTest.assertToString(Predicates.isNull());
    }

    @Test
    public void notNull_1() {
        PredicatesTest.assertAccepts(Predicates.notNull(), new Object());
    }

    @Test
    public void notNull_2() {
        PredicatesTest.assertRejects(Predicates.notNull(), (Object) null);
    }

    @Test
    public void notNull_3() {
        PredicatesTest.assertToString(Predicates.notNull());
    }

    @Test
    public void neither_1() {
        PredicatesTest.assertRejects(Predicates.neither(Predicates.alwaysTrue(), Predicates.alwaysTrue()), new Object());
    }

    @Test
    public void neither_2() {
        PredicatesTest.assertRejects(Predicates.neither(Predicates.alwaysTrue(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void neither_3() {
        PredicatesTest.assertAccepts(Predicates.neither(Predicates.alwaysFalse(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void neither_4() {
        PredicatesTest.assertToString(Predicates.neither(Predicates.alwaysFalse(), Predicates.alwaysFalse()));
    }

    @Test
    public void noneOf_1() {
        PredicatesTest.assertRejects(Predicates.noneOf(Predicates.alwaysTrue(), Predicates.alwaysTrue(), Predicates.alwaysTrue()), new Object());
    }

    @Test
    public void noneOf_2() {
        PredicatesTest.assertRejects(Predicates.noneOf(Predicates.alwaysTrue(), Predicates.alwaysTrue(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void noneOf_3() {
        PredicatesTest.assertAccepts(Predicates.noneOf(Predicates.alwaysFalse(), Predicates.alwaysFalse(), Predicates.alwaysFalse()), new Object());
    }

    @Test
    public void noneOf_4() {
        PredicatesTest.assertToString(Predicates.noneOf(Predicates.alwaysFalse(), Predicates.alwaysFalse(), Predicates.alwaysFalse()));
    }

    @Test
    public void instanceOf_1() {
        assertTrue(Predicates.instanceOf(Integer.class).accept(1));
    }

    @Test
    public void instanceOf_2() {
        assertFalse(Predicates.instanceOf(Integer.class).accept(1.0));
    }

    @Test
    public void instanceOf_3() {
        PredicatesTest.assertToString(Predicates.instanceOf(Integer.class));
    }

    @Test
    public void assignableFrom_1() {
        PredicatesTest.assertAccepts(Predicates.assignableFrom(Number.class), 1);
    }

    @Test
    public void assignableFrom_2() {
        PredicatesTest.assertAccepts(Predicates.assignableFrom(Integer.class), 1);
    }

    @Test
    public void assignableFrom_3() {
        PredicatesTest.assertRejects(Predicates.assignableFrom(List.class), 1);
    }

    @Test
    public void assignableFrom_4() {
        PredicatesTest.assertToString(Predicates.assignableFrom(Number.class));
    }

    @Test
    public void notInstanceOf_1() {
        assertFalse(Predicates.notInstanceOf(Integer.class).accept(1));
    }

    @Test
    public void notInstanceOf_2() {
        assertTrue(Predicates.notInstanceOf(Integer.class).accept(1.0));
    }

    @Test
    public void notInstanceOf_3() {
        PredicatesTest.assertToString(Predicates.notInstanceOf(Integer.class));
    }

    @Test
    public void attributeIsNull_1() {
        PredicatesTest.assertAccepts(Predicates.attributeIsNull(Functions.getPassThru()), (Object) null);
    }

    @Test
    public void attributeIsNull_2() {
        PredicatesTest.assertRejects(Predicates.attributeIsNull(Functions.getPassThru()), new Object());
    }

    @Test
    public void attributeNotNullWithFunction_1() {
        PredicatesTest.assertRejects(Predicates.attributeNotNull(Functions.getPassThru()), (Object) null);
    }

    @Test
    public void attributeNotNullWithFunction_2() {
        PredicatesTest.assertAccepts(Predicates.attributeNotNull(Functions.getPassThru()), new Object());
    }

    @Test
    public void inInterval_1() {
        PredicatesTest.assertAccepts(Predicates.in(Interval.oneTo(3)), 2);
    }

    @Test
    public void inInterval_2() {
        PredicatesTest.assertToString(Predicates.in(Interval.oneTo(3)));
    }

    @Test
    public void notInInterval_1() {
        PredicatesTest.assertAccepts(Predicates.notIn(Interval.oneTo(3)), 4);
    }

    @Test
    public void notInInterval_2() {
        PredicatesTest.assertToString(Predicates.notIn(Interval.oneTo(3)));
    }

    @Test
    public void lessThanOrEqualTo_1() {
        PredicatesTest.assertAccepts(Predicates.lessThanOrEqualTo(0), 0, -1);
    }

    @Test
    public void lessThanOrEqualTo_2() {
        PredicatesTest.assertRejects(Predicates.lessThanOrEqualTo(0), 1);
    }

    @Test
    public void lessThanOrEqualTo_3() {
        PredicatesTest.assertToString(Predicates.lessThanOrEqualTo(0));
    }

    @Test
    public void greaterThan_1() {
        PredicatesTest.assertAccepts(Predicates.greaterThan(0), 1);
    }

    @Test
    public void greaterThan_2() {
        PredicatesTest.assertRejects(Predicates.greaterThan(0), 0, -1);
    }

    @Test
    public void greaterThan_3() {
        PredicatesTest.assertToString(Predicates.greaterThan(0));
    }

    @Test
    public void greaterThanOrEqualTo_1() {
        PredicatesTest.assertAccepts(Predicates.greaterThanOrEqualTo(0), 0, 1);
    }

    @Test
    public void greaterThanOrEqualTo_2() {
        PredicatesTest.assertRejects(Predicates.greaterThanOrEqualTo(0), -1);
    }

    @Test
    public void greaterThanOrEqualTo_3() {
        PredicatesTest.assertToString(Predicates.greaterThanOrEqualTo(0));
    }

    @Test
    public void betweenInclusiveNumber_1() {
        PredicatesTest.assertBetweenInclusive(Predicates.betweenInclusive(1, 3));
    }

    @Test
    public void betweenInclusiveNumber_2() {
        PredicatesTest.assertBetweenInclusive(Predicates.attributeBetweenInclusive(Functions.getIntegerPassThru(), 1, 3));
    }

    @Test
    public void betweenInclusiveFromNumber_1() {
        PredicatesTest.assertBetweenInclusiveFrom(Predicates.betweenInclusiveFrom(1, 3));
    }

    @Test
    public void betweenInclusiveFromNumber_2() {
        PredicatesTest.assertBetweenInclusiveFrom(Predicates.attributeBetweenInclusiveFrom(Functions.getIntegerPassThru(), 1, 3));
    }

    @Test
    public void betweenInclusiveToNumber_1() {
        PredicatesTest.assertBetweenInclusiveTo(Predicates.betweenInclusiveTo(1, 3));
    }

    @Test
    public void betweenInclusiveToNumber_2() {
        PredicatesTest.assertBetweenInclusiveTo(Predicates.attributeBetweenInclusiveTo(Functions.getIntegerPassThru(), 1, 3));
    }

    @Test
    public void betweenExclusiveNumber_1() {
        PredicatesTest.assertBetweenExclusive(Predicates.betweenExclusive(1, 3));
    }

    @Test
    public void betweenExclusiveNumber_2() {
        PredicatesTest.assertBetweenExclusive(Predicates.attributeBetweenExclusive(Functions.getIntegerPassThru(), 1, 3));
    }

    @Test
    public void attributeNotNull_1_testMerged_1() {
        Twin<String> testCandidate = Tuples.twin("Hello", null);
        PredicatesTest.assertAccepts(Predicates.attributeNotNull(Functions.firstOfPair()), testCandidate);
        PredicatesTest.assertRejects(Predicates.attributeNotNull(Functions.secondOfPair()), testCandidate);
    }

    @Test
    public void attributeNotNull_3() {
        PredicatesTest.assertToString(Predicates.attributeNotNull(Functions.<String>firstOfPair()));
    }
}
