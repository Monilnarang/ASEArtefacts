package org.graylog.plugins.views.search.querystrings;

import com.google.common.eventbus.EventBus;
import org.graylog.plugins.views.search.rest.TestUser;
import org.graylog.testing.mongodb.MongoDBExtension;
import org.graylog.testing.mongodb.MongoDBTestService;
import org.graylog.testing.mongodb.MongoJackExtension;
import org.graylog2.bindings.providers.MongoJackObjectMapperProvider;
import org.graylog2.plugin.database.users.User;
import org.graylog2.users.events.UserDeletedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MongoDBExtension.class)
@ExtendWith(MongoJackExtension.class)
class MongoLastUsedQueryStringsServiceTest_Purified {

    private User user;

    private User admin;

    private MongoLastUsedQueryStringsService service;

    @BeforeEach
    void setUp(MongoDBTestService mongodb, MongoJackObjectMapperProvider mongoJackObjectMapperProvider) {
        admin = TestUser.builder().withId("637748db06e1d74da0a54331").withUsername("local:admin").isLocalAdmin(true).build();
        user = TestUser.builder().withId("637748db06e1d74da0a54330").withUsername("test").isLocalAdmin(false).build();
        this.service = new MongoLastUsedQueryStringsService(mongodb.mongoConnection(), mongoJackObjectMapperProvider, new EventBus(), 3);
    }

    private List<String> queryStrings(List<QueryString> queryStrings) {
        return queryStrings.stream().map(QueryString::query).toList();
    }

    @Test
    void storedQueryStringCanBeRetrieved_1() {
        assertThat(queryStrings(service.get(user))).isEmpty();
    }

    @Test
    void storedQueryStringCanBeRetrieved_2_testMerged_2() {
        service.save(user, "http_method:GET");
        assertThat(queryStrings(service.get(user))).containsExactly("http_method:GET");
        assertThat(queryStrings(service.get(admin))).isEmpty();
    }

    @Test
    void queryStringsAreScopedByUser_1() {
        assertThat(service.get(user)).isEmpty();
    }

    @Test
    void queryStringsAreScopedByUser_2_testMerged_2() {
        service.save(user, "http_method:GET");
        assertThat(queryStrings(service.get(admin))).isEmpty();
        service.save(admin, "action:foo");
        assertThat(queryStrings(service.get(user))).containsExactly("http_method:GET");
    }
}
