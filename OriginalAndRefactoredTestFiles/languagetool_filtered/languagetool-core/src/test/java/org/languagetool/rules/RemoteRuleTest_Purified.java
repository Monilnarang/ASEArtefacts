package org.languagetool.rules;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.UserConfig;
import org.languagetool.language.Demo;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import static org.junit.Assert.*;

public class RemoteRuleTest_Purified {

    protected static String sentence;

    private static JLanguageTool lt;

    private static RemoteRule rule;

    private static final RemoteRuleConfig config;

    private static long waitTime;

    private static boolean fail = false;

    private static int calls;

    static {
        config = new RemoteRuleConfig();
        config.ruleId = TestRemoteRule.ID;
        config.baseTimeoutMilliseconds = 50;
        config.minimumNumberOfCalls = 2;
        config.failureRateThreshold = 50f;
        config.downMilliseconds = 200L;
        config.slidingWindowSize = 2;
        config.slidingWindowType = CircuitBreakerConfig.SlidingWindowType.COUNT_BASED.name();
    }

    static class TestRemoteRule extends RemoteRule {

        static final String ID = "TEST_REMOTE_RULE";

        TestRemoteRule(RemoteRuleConfig config) {
            super(new Demo(), JLanguageTool.getMessageBundle(), config, false);
        }

        class TestRemoteRequest extends RemoteRequest {

            private final List<AnalyzedSentence> sentences;

            TestRemoteRequest(List<AnalyzedSentence> sentences) {
                this.sentences = sentences;
            }
        }

        @Override
        protected RemoteRequest prepareRequest(List<AnalyzedSentence> sentences, Long textSessionId) {
            return new TestRemoteRequest(sentences);
        }

        private RuleMatch testMatch(AnalyzedSentence s) {
            return new RuleMatch(this, s, 0, 1, "Test match");
        }

        @Override
        protected Callable<RemoteRuleResult> executeRequest(RemoteRequest request, long timeoutMilliseconds) throws TimeoutException {
            return () -> {
                calls++;
                if (fail) {
                    throw new RuntimeException("Failing for testing purposes");
                }
                TestRemoteRequest req = (TestRemoteRequest) request;
                long deadline = System.currentTimeMillis() + waitTime;
                while (System.currentTimeMillis() < deadline) ;
                List<RuleMatch> matches = req.sentences.stream().map(this::testMatch).collect(Collectors.toList());
                return new RemoteRuleResult(true, true, matches, req.sentences);
            };
        }

        @Override
        protected RemoteRuleResult fallbackResults(RemoteRequest request) {
            TestRemoteRequest req = (TestRemoteRequest) request;
            System.out.println("Fallback matches");
            return new RemoteRuleResult(false, false, Collections.emptyList(), req.sentences);
        }

        @Override
        public String getDescription() {
            return "TEST REMOTE RULE";
        }
    }

    @BeforeClass
    public static void setUp() throws IOException {
        lt = new JLanguageTool(new Demo());
        lt.getAllActiveRules().forEach(r -> lt.disableRule(r.getId()));
        rule = new TestRemoteRule(config);
        sentence = "This is a test.";
        lt.addRule(rule);
    }

    @After
    public void tearDown() {
        rule.circuitBreaker().reset();
        waitTime = 0;
        calls = 0;
        fail = false;
    }

    private void assertMatches(String msg, int expected) throws IOException {
        List<RuleMatch> matches = lt.check(sentence);
        assertEquals(msg, expected, matches.size());
    }

    private UserConfig getUserConfigWithAbTest(List<String> abTest) {
        UserConfig userConfig = new UserConfig(Collections.emptyList(), Collections.emptyList(), Collections.emptyMap(), 5, null, null, null, null, false, abTest, null, false, null);
        return userConfig;
    }

    private UserConfig getUserConfigWithThirdPartyAI(boolean thirdPartyAI) {
        UserConfig userConfig = new UserConfig(Collections.emptyList(), Collections.emptyList(), Collections.emptyMap(), 5, null, null, null, null, false, null, null, false, null, true, thirdPartyAI);
        return userConfig;
    }

    private UserConfig getUserConfigWithThirdPartyAIAndABTest(boolean thirdPartyAI, List<String> abTest) {
        UserConfig userConfig = new UserConfig(Collections.emptyList(), Collections.emptyList(), Collections.emptyMap(), 5, null, null, null, null, false, abTest, null, false, null, true, thirdPartyAI);
        return userConfig;
    }

    @Test
    public void testMatch_1() throws IOException {
        assertMatches("no matches before - sanity check", 0);
    }

    @Test
    public void testMatch_2() throws IOException {
        assertMatches("test rule creates match", 1);
    }
}
