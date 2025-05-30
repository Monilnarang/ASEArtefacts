package org.graylog2.migrations;

import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.MoreExecutors;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.graylog.testing.mongodb.MongoDBInstance;
import org.graylog2.events.ClusterEventBus;
import org.graylog2.grok.GrokPatternsDeletedEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class V2018070614390000_EnforceUniqueGrokPatternsTest_Purified {

    @Rule
    public final MongoDBInstance mongodb = MongoDBInstance.createForClass();

    private MongoCollection<Document> collection;

    private V2018070614390000_EnforceUniqueGrokPatterns migration;

    private ClusterEventBus clusterEventBus;

    private TestSubscriber subscriber;

    @Before
    public void setUp() {
        collection = mongodb.mongoConnection().getMongoDatabase().getCollection("grok_patterns");
        subscriber = new TestSubscriber();
        clusterEventBus = new ClusterEventBus(MoreExecutors.newDirectExecutorService());
        clusterEventBus.registerClusterEventSubscriber(subscriber);
        migration = new V2018070614390000_EnforceUniqueGrokPatterns(collection, clusterEventBus);
    }

    private Document grokPattern(String name, String pattern) {
        return new Document().append("_id", new ObjectId()).append("name", name).append("pattern", pattern);
    }

    private static class TestSubscriber {

        public final List<GrokPatternsDeletedEvent> events = new CopyOnWriteArrayList<>();

        @Subscribe
        public void handleGrokPatternsChangedEvent(GrokPatternsDeletedEvent event) {
            events.add(event);
        }
    }

    @Test
    public void upgradeAbortsIfIndexExists_1() {
        migration.upgrade();
        assertThat(migration.isIndexCreated()).isFalse();
    }

    @Test
    public void upgradeAbortsIfIndexExists_2() {
        assertThat(subscriber.events).isEmpty();
    }

    @Test
    public void upgradeRunsIfIndexDoesNotExist_1() {
        migration.upgrade();
        assertThat(migration.isIndexCreated()).isTrue();
    }

    @Test
    public void upgradeRunsIfIndexDoesNotExist_2() {
        assertThat(subscriber.events).isEmpty();
    }
}
