package org.graylog2.plugin.inputs;

import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.graylog.failure.ProcessingFailureCause;
import org.graylog2.inputs.converters.DateConverter;
import org.graylog2.inputs.extractors.ExtractorException;
import org.graylog2.plugin.Message;
import org.graylog2.plugin.MessageFactory;
import org.graylog2.plugin.TestMessageFactory;
import org.graylog2.plugin.inputs.Extractor.Result;
import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.graylog2.plugin.inputs.Converter.Type.NUMERIC;
import static org.graylog2.plugin.inputs.Extractor.ConditionType.NONE;
import static org.graylog2.plugin.inputs.Extractor.ConditionType.REGEX;
import static org.graylog2.plugin.inputs.Extractor.ConditionType.STRING;
import static org.graylog2.plugin.inputs.Extractor.CursorStrategy.COPY;
import static org.graylog2.plugin.inputs.Extractor.CursorStrategy.CUT;
import static org.joda.time.DateTimeZone.UTC;

public class ExtractorTest_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(ExtractorTest.class);

    private final MessageFactory messageFactory = new TestMessageFactory();

    private Message createMessage(String message) {
        return messageFactory.createMessage(message, "localhost", DateTime.now(UTC));
    }

    private static class TestExtractor extends Extractor {

        private final Callable<Result[]> callback;

        public TestExtractor(Callable<Result[]> callback, MetricRegistry metricRegistry, String id, String title, long order, Type type, CursorStrategy cursorStrategy, String sourceField, String targetField, Map<String, Object> extractorConfig, String creatorUserId, List<Converter> converters, ConditionType conditionType, String conditionValue) throws ReservedFieldException {
            super(metricRegistry, id, title, order, type, cursorStrategy, sourceField, targetField, extractorConfig, creatorUserId, converters, conditionType, conditionValue);
            this.callback = callback;
        }

        @Override
        protected Result[] run(String field) {
            try {
                return callback.call();
            } catch (Exception e) {
                if (e instanceof ExtractorException) {
                    throw (ExtractorException) e;
                }
                LOG.error("Error calling callback", e);
                return null;
            }
        }

        public static class Builder {

            private Callable<Result[]> callback = new Callable<Result[]>() {

                @Override
                public Result[] call() throws Exception {
                    return new Result[] { new Result("canary", -1, -1) };
                }
            };

            private String sourceField = "message";

            private String targetField = "target";

            private ConditionType conditionType = NONE;

            private String conditionValue = "";

            private CursorStrategy cursorStrategy = COPY;

            private List<Converter> converters = Collections.emptyList();

            public Builder cursorStrategy(CursorStrategy cursorStrategy) {
                this.cursorStrategy = cursorStrategy;
                return this;
            }

            public Builder callback(Callable<Result[]> callback) {
                this.callback = callback;
                return this;
            }

            public Builder conditionType(ConditionType conditionType) {
                this.conditionType = conditionType;
                return this;
            }

            public Builder conditionValue(String conditionValue) {
                this.conditionValue = conditionValue;
                return this;
            }

            public Builder sourceField(String sourceField) {
                this.sourceField = sourceField;
                return this;
            }

            public Builder targetField(String targetField) {
                this.targetField = targetField;
                return this;
            }

            public Builder converters(List<Converter> converters) {
                this.converters = converters;
                return this;
            }

            public TestExtractor build() throws ReservedFieldException {
                return new TestExtractor(callback, new MetricRegistry(), "test-id", "test-title", 0L, Extractor.Type.REGEX, cursorStrategy, sourceField, targetField, Collections.<String, Object>emptyMap(), "user", converters, conditionType, conditionValue);
            }
        }
    }

    private static class TestConverter extends Converter {

        private final boolean multiple;

        private final Function<Object, Object> callback;

        public TestConverter(Type type, Map<String, Object> config, boolean multiple, Function<Object, Object> callback) {
            super(type, config);
            this.multiple = multiple;
            this.callback = callback;
        }

        @Override
        public Object convert(String value) {
            try {
                return callback.apply(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean buildsMultipleFields() {
            return multiple;
        }

        public static class Builder {

            private boolean multiple = false;

            private Function<Object, Object> callback = new Function<Object, Object>() {

                @Override
                public Object apply(Object input) {
                    return null;
                }
            };

            public Builder multiple(boolean multiple) {
                this.multiple = multiple;
                return this;
            }

            public Builder callback(Function<Object, Object> callback) {
                this.callback = callback;
                return this;
            }

            public TestConverter build() {
                return new TestConverter(NUMERIC, Maps.<String, Object>newHashMap(), multiple, callback);
            }
        }
    }

    @Test
    public void testRunExtractorCheckSourceValueIsString_1() throws Exception {
        final Message msg1 = createMessage("the message");
        msg1.addField("a_field", 1);
        extractor.runExtractor(msg1);
        assertThat(msg1.hasField("target")).isFalse();
    }

    @Test
    public void testRunExtractorCheckSourceValueIsString_2() throws Exception {
        final Message msg2 = createMessage("the message");
        msg2.addField("a_field", "the source");
        extractor.runExtractor(msg2);
        assertThat(msg2.hasField("target")).isTrue();
    }

    @Test
    public void testWithStringCondition_1() throws Exception {
        final Message msg1 = createMessage("hello world");
        extractor.runExtractor(msg1);
        assertThat(msg1.hasField("target")).isTrue();
    }

    @Test
    public void testWithStringCondition_2() throws Exception {
        final Message msg2 = createMessage("the message");
        extractor.runExtractor(msg2);
        assertThat(msg2.hasField("target")).isFalse();
    }

    @Test
    public void testWithRegexpCondition_1() throws Exception {
        final Message msg1 = createMessage("hello world");
        extractor.runExtractor(msg1);
        assertThat(msg1.hasField("target")).isTrue();
    }

    @Test
    public void testWithRegexpCondition_2() throws Exception {
        final Message msg2 = createMessage("the hello");
        extractor.runExtractor(msg2);
        assertThat(msg2.hasField("target")).isFalse();
    }
}
