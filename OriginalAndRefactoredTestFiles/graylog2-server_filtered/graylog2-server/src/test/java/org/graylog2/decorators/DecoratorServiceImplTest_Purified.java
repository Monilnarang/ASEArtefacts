package org.graylog2.decorators;

import org.graylog.testing.mongodb.MongoDBFixtures;
import org.graylog.testing.mongodb.MongoDBInstance;
import org.graylog2.bindings.providers.MongoJackObjectMapperProvider;
import org.graylog2.database.MongoCollections;
import org.graylog2.database.NotFoundException;
import org.graylog2.shared.bindings.providers.ObjectMapperProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class DecoratorServiceImplTest_Purified {

    @Rule
    public final MongoDBInstance mongodb = MongoDBInstance.createForClass();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private DecoratorServiceImpl decoratorService;

    @Before
    public void setUp() {
        final ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider();
        final MongoJackObjectMapperProvider provider = new MongoJackObjectMapperProvider(objectMapperProvider.get());
        decoratorService = new DecoratorServiceImpl(new MongoCollections(provider, mongodb.mongoConnection()));
    }

    @Test
    @MongoDBFixtures("DecoratorServiceImplTest.json")
    public void delete_1() {
        assertThat(decoratorService.findAll()).hasSize(3);
    }

    @Test
    @MongoDBFixtures("DecoratorServiceImplTest.json")
    public void delete_2() {
        assertThat(decoratorService.delete("588bcafebabedeadbeef0001")).isEqualTo(1);
    }

    @Test
    @MongoDBFixtures("DecoratorServiceImplTest.json")
    public void delete_3() {
        assertThat(decoratorService.findAll()).hasSize(2);
    }

    @Test
    @MongoDBFixtures("DecoratorServiceImplTest.json")
    public void delete_4() {
        assertThat(decoratorService.delete("588bcafebabedeadbeef0001")).isEqualTo(0);
    }

    @Test
    @MongoDBFixtures("DecoratorServiceImplTest.json")
    public void delete_5() {
        assertThat(decoratorService.delete("588bcafebabedeadbeef9999")).isEqualTo(0);
    }
}
