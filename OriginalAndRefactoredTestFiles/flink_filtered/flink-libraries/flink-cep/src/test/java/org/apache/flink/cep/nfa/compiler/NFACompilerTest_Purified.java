package org.apache.flink.cep.nfa.compiler;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.cep.Event;
import org.apache.flink.cep.SubEvent;
import org.apache.flink.cep.nfa.NFA;
import org.apache.flink.cep.nfa.State;
import org.apache.flink.cep.nfa.StateTransition;
import org.apache.flink.cep.nfa.StateTransitionAction;
import org.apache.flink.cep.nfa.aftermatch.AfterMatchSkipStrategy;
import org.apache.flink.cep.pattern.MalformedPatternException;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.WithinType;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.util.TestLogger;
import org.apache.flink.shaded.guava33.com.google.common.collect.Sets;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.apache.flink.cep.utils.NFAUtils.compile;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NFACompilerTest_Purified extends TestLogger {

    private static final SimpleCondition<Event> startFilter = SimpleCondition.of(value -> value.getPrice() > 2);

    private static final SimpleCondition<Event> endFilter = SimpleCondition.of(value -> value.getName().equals("end"));

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static class TestFilter extends SimpleCondition<Event> {

        private static final long serialVersionUID = -3863103355752267133L;

        @Override
        public boolean filter(Event value) throws Exception {
            throw new RuntimeException("It should never arrive here.");
        }
    }

    private <T> Set<Tuple2<String, StateTransitionAction>> unfoldTransitions(final State<T> state) {
        final Set<Tuple2<String, StateTransitionAction>> transitions = new HashSet<>();
        for (StateTransition<T> transition : state.getStateTransitions()) {
            transitions.add(Tuple2.of(transition.getTargetState().getName(), transition.getAction()));
        }
        return transitions;
    }

    @Test
    public void testCheckingEmptyMatches_1() {
        assertThat(NFACompiler.canProduceEmptyMatches(Pattern.begin("a").optional()), is(true));
    }

    @Test
    public void testCheckingEmptyMatches_2() {
        assertThat(NFACompiler.canProduceEmptyMatches(Pattern.begin("a").oneOrMore().optional()), is(true));
    }

    @Test
    public void testCheckingEmptyMatches_3() {
        assertThat(NFACompiler.canProduceEmptyMatches(Pattern.begin("a").oneOrMore().optional().next("b").optional()), is(true));
    }

    @Test
    public void testCheckingEmptyMatches_4() {
        assertThat(NFACompiler.canProduceEmptyMatches(Pattern.begin("a")), is(false));
    }

    @Test
    public void testCheckingEmptyMatches_5() {
        assertThat(NFACompiler.canProduceEmptyMatches(Pattern.begin("a").oneOrMore()), is(false));
    }

    @Test
    public void testCheckingEmptyMatches_6() {
        assertThat(NFACompiler.canProduceEmptyMatches(Pattern.begin("a").oneOrMore().next("b").optional()), is(false));
    }
}
