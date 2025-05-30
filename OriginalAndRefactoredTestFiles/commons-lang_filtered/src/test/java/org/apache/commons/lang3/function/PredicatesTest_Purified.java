package org.apache.commons.lang3.function;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

public class PredicatesTest_Purified {

    @Test
    public void testFalsePredicate_1() {
        assertFalse(Predicates.falsePredicate().test(null));
    }

    @Test
    public void testFalsePredicate_2() {
        assertFalse(Predicates.falsePredicate().test(new Object()));
    }

    @Test
    public void testFalsePredicate_3_testMerged_3() {
        final Predicate<String> stringPredicate = Predicates.falsePredicate();
        assertFalse(stringPredicate.test(null));
        assertFalse(stringPredicate.test(""));
    }

    @Test
    public void testTruePredicate_1() {
        assertTrue(Predicates.truePredicate().test(null));
    }

    @Test
    public void testTruePredicate_2() {
        assertTrue(Predicates.truePredicate().test(new Object()));
    }

    @Test
    public void testTruePredicate_3_testMerged_3() {
        final Predicate<String> stringPredicate = Predicates.truePredicate();
        assertTrue(stringPredicate.test(null));
        assertTrue(stringPredicate.test(""));
    }
}
