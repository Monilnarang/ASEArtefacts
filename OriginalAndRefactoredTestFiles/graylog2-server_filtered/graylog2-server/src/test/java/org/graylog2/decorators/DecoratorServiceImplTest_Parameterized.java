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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DecoratorServiceImplTest_Parameterized {

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

    @MongoDBFixtures("DecoratorServiceImplTest.json")
    @ParameterizedTest
    @MethodSource("Provider_delete_1_3")
    public void delete_1_3(int param1) {
        assertThat(decoratorService.findAll()).hasSize(param1);
    }

    static public Stream<Arguments> Provider_delete_1_3() {
        return Stream.of(arguments(3), arguments(2));
    }

    @MongoDBFixtures("DecoratorServiceImplTest.json")
    @ParameterizedTest
    @MethodSource("Provider_delete_2_4to5")
    public void delete_2_4to5(int param1, String param2) {
        assertThat(decoratorService.delete(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_delete_2_4to5() {
        return Stream.of(arguments(1, "588bcafebabedeadbeef0001"), arguments(0, "588bcafebabedeadbeef0001"), arguments(0, "588bcafebabedeadbeef9999"));
    }
}
