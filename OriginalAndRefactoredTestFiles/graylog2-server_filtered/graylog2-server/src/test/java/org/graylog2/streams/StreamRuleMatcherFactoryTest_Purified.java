package org.graylog2.streams;

import org.graylog2.plugin.streams.StreamRuleType;
import org.graylog2.streams.matchers.AlwaysMatcher;
import org.graylog2.streams.matchers.InputMatcher;
import org.graylog2.streams.matchers.ExactMatcher;
import org.graylog2.streams.matchers.FieldPresenceMatcher;
import org.graylog2.streams.matchers.GreaterMatcher;
import org.graylog2.streams.matchers.RegexMatcher;
import org.graylog2.streams.matchers.SmallerMatcher;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StreamRuleMatcherFactoryTest_Purified {

    @Test
    public void buildReturnsCorrectStreamRuleMatcher_1() throws Exception {
        assertThat(StreamRuleMatcherFactory.build(StreamRuleType.EXACT)).isInstanceOf(ExactMatcher.class);
    }

    @Test
    public void buildReturnsCorrectStreamRuleMatcher_2() throws Exception {
        assertThat(StreamRuleMatcherFactory.build(StreamRuleType.REGEX)).isInstanceOf(RegexMatcher.class);
    }

    @Test
    public void buildReturnsCorrectStreamRuleMatcher_3() throws Exception {
        assertThat(StreamRuleMatcherFactory.build(StreamRuleType.GREATER)).isInstanceOf(GreaterMatcher.class);
    }

    @Test
    public void buildReturnsCorrectStreamRuleMatcher_4() throws Exception {
        assertThat(StreamRuleMatcherFactory.build(StreamRuleType.SMALLER)).isInstanceOf(SmallerMatcher.class);
    }

    @Test
    public void buildReturnsCorrectStreamRuleMatcher_5() throws Exception {
        assertThat(StreamRuleMatcherFactory.build(StreamRuleType.PRESENCE)).isInstanceOf(FieldPresenceMatcher.class);
    }

    @Test
    public void buildReturnsCorrectStreamRuleMatcher_6() throws Exception {
        assertThat(StreamRuleMatcherFactory.build(StreamRuleType.ALWAYS_MATCH)).isInstanceOf(AlwaysMatcher.class);
    }

    @Test
    public void buildReturnsCorrectStreamRuleMatcher_7() throws Exception {
        assertThat(StreamRuleMatcherFactory.build(StreamRuleType.MATCH_INPUT)).isInstanceOf(InputMatcher.class);
    }
}
