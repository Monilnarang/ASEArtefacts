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

public class LinkFieldDecoratorTest_Purified {

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

    @Test
    public void verifyUnsafeLinksAreRemoved_1() {
        Assert.assertEquals("http://full-local-allowed", getDecoratorUrl("http://full-local-allowed"));
    }

    @Test
    public void verifyUnsafeLinksAreRemoved_2() {
        Assert.assertEquals("http://full-url-allowed.com", getDecoratorUrl("http://full-url-allowed.com"));
    }

    @Test
    public void verifyUnsafeLinksAreRemoved_3() {
        Assert.assertEquals("http://full-url-allowed.com/test", getDecoratorUrl("http://full-url-allowed.com/test"));
    }

    @Test
    public void verifyUnsafeLinksAreRemoved_4() {
        Assert.assertEquals("http://full-url-allowed.com/test?with=param", getDecoratorUrl("http://full-url-allowed.com/test?with=param"));
    }

    @Test
    public void verifyUnsafeLinksAreRemoved_5() {
        Assert.assertEquals("https://https-is-allowed-too.com", getDecoratorUrl("https://https-is-allowed-too.com"));
    }

    @Test
    public void verifyUnsafeLinksAreRemoved_6() {
        Assert.assertEquals("HTTPS://upper-case-https-all-good.com", getDecoratorUrl("HTTPS://upper-case-https-all-good.com"));
    }

    @Test
    public void verifyUnsafeLinksAreRemoved_7() {
        Assert.assertEquals("https://graylog.com//releases", getDecoratorUrl("https://graylog.com//releases"));
    }

    @Test
    public void verifyUnsafeLinksAreRemoved_8() {
        Assert.assertEquals("javascript:alert('Javascript is not allowed.')", getDecoratorMessage("javascript:alert('Javascript is not allowed.')"));
    }

    @Test
    public void verifyUnsafeLinksAreRemoved_9() {
        Assert.assertEquals("alert('Javascript this way is still not allowed", getDecoratorMessage("alert('Javascript this way is still not allowed"));
    }

    @Test
    public void verifyUnsafeLinksAreRemoved_10() {
        Assert.assertEquals("ntp://other-stuff-is-not-allowed", getDecoratorMessage("ntp://other-stuff-is-not-allowed"));
    }
}
