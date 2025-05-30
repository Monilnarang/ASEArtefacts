package org.graylog2.decorators;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import org.graylog2.plugin.MessageFactory;
import org.graylog2.plugin.TestMessageFactory;
import org.graylog2.plugin.Tools;
import org.graylog2.rest.models.messages.responses.ResultMessageSummary;
import org.graylog2.rest.models.system.indexer.responses.IndexRangeSummary;
import org.graylog2.rest.resources.search.responses.SearchResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LinkFieldDecoratorTest_Parameterized {

    private final MessageFactory messageFactory = new TestMessageFactory();

    private static final String TEST_FIELD = "test_field";

    private LinkFieldDecorator decorator;

    @Before
    public void setUp() throws Exception {
        final HashMap<String, Object> config = new HashMap<>();
        config.put(LinkFieldDecorator.CK_LINK_FIELD, TEST_FIELD);
        decorator = new LinkFieldDecorator(DecoratorImpl.create("id", "link", config, Optional.empty(), 0), messageFactory);
    }

    private Object getDecoratorMessage(String urlFieldValue) {
        return executeDecoratorGetFirstMessage(urlFieldValue).message().get(TEST_FIELD);
    }

    private Object getDecoratorUrl(String urlFieldValue) {
        return ((HashMap) executeDecoratorGetFirstMessage(urlFieldValue).message().get(TEST_FIELD)).get("href");
    }

    private ResultMessageSummary executeDecoratorGetFirstMessage(String urlFieldValue) {
        return decorator.apply(createSearchResponse(urlFieldValue)).messages().get(0);
    }

    private SearchResponse createSearchResponse(String urlFieldValue) {
        final List<ResultMessageSummary> messages = ImmutableList.of(ResultMessageSummary.create(ImmutableMultimap.of(), ImmutableMap.of("_id", "a", TEST_FIELD, urlFieldValue), "graylog_0"));
        final IndexRangeSummary indexRangeSummary = IndexRangeSummary.create("graylog_0", Tools.nowUTC().minusDays(1), Tools.nowUTC(), null, 100);
        return SearchResponse.builder().query("foo").builtQuery("foo").usedIndices(ImmutableSet.of(indexRangeSummary)).messages(messages).fields(ImmutableSet.of(TEST_FIELD)).time(100L).totalResults(messages.size()).from(Tools.nowUTC().minusHours(1)).to(Tools.nowUTC()).build();
    }

    @ParameterizedTest
    @MethodSource("Provider_verifyUnsafeLinksAreRemoved_1to7")
    public void verifyUnsafeLinksAreRemoved_1to7(String param1, String param2) {
        Assert.assertEquals(param1, getDecoratorUrl(param2));
    }

    static public Stream<Arguments> Provider_verifyUnsafeLinksAreRemoved_1to7() {
        return Stream.of(arguments("http://full-local-allowed", "http://full-local-allowed"), arguments("http://full-url-allowed.com", "http://full-url-allowed.com"), arguments("http://full-url-allowed.com/test", "http://full-url-allowed.com/test"), arguments("http://full-url-allowed.com/test?with=param", "http://full-url-allowed.com/test?with=param"), arguments("https://https-is-allowed-too.com", "https://https-is-allowed-too.com"), arguments("HTTPS://upper-case-https-all-good.com", "HTTPS://upper-case-https-all-good.com"), arguments("https://graylog.com//releases", "https://graylog.com//releases"));
    }

    @ParameterizedTest
    @MethodSource("Provider_verifyUnsafeLinksAreRemoved_8to10")
    public void verifyUnsafeLinksAreRemoved_8to10(String param1, String param2) {
        Assert.assertEquals(param1, getDecoratorMessage(param2));
    }

    static public Stream<Arguments> Provider_verifyUnsafeLinksAreRemoved_8to10() {
        return Stream.of(arguments("javascript:alert('Javascript is not allowed.')", "javascript:alert('Javascript is not allowed.')"), arguments("alert('Javascript this way is still not allowed", "alert('Javascript this way is still not allowed"), arguments("ntp://other-stuff-is-not-allowed", "ntp://other-stuff-is-not-allowed"));
    }
}
