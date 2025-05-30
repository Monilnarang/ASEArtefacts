package org.graylog.testing.mongodb;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.RegisterExtension;
import static com.mongodb.client.model.Filters.eq;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MongoDBExtensionWithRegistrationAsStaticFieldTest_Parameterized {

    @SuppressWarnings("unused")
    @RegisterExtension
    static MongoDBExtension mongodbExtension = MongoDBExtension.createWithDefaultVersion();

    static String instanceId = null;

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class UsingSameInstanceForAllTests {

        @Test
        @Order(1)
        void recordInstanceId(MongoDBTestService mongodb) {
            instanceId = mongodb.instanceId();
        }

        @Test
        @Order(2)
        void checkThatSameInstanceIdIsUsed(MongoDBTestService mongodb) {
            assertThat(instanceId).withFailMessage("All test methods should use the same MongoDB instance, but we registered more than one").isEqualTo(mongodb.instanceId());
        }
    }

    @ParameterizedTest
    @MethodSource("Provider_withoutFixtures_1to2")
    void withoutFixtures_1to2(MongoDBTestService mongodb, int param1, String param2) {
        assertThat(mongodb.mongoCollection(param2).countDocuments()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_withoutFixtures_1to2() {
        return Stream.of(arguments(0, "test_1"), arguments(0, "test_2"));
    }
}
