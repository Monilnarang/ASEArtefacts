package org.graylog.testing.mongodb;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.RegisterExtension;
import static com.mongodb.client.model.Filters.eq;
import static org.assertj.core.api.Assertions.assertThat;

class MongoDBExtensionWithRegistrationAsStaticFieldTest_Purified {

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

    @Test
    void withoutFixtures_1(MongoDBTestService mongodb) {
        assertThat(mongodb.mongoCollection("test_1").countDocuments()).isEqualTo(0);
    }

    @Test
    void withoutFixtures_2(MongoDBTestService mongodb) {
        assertThat(mongodb.mongoCollection("test_2").countDocuments()).isEqualTo(0);
    }
}
