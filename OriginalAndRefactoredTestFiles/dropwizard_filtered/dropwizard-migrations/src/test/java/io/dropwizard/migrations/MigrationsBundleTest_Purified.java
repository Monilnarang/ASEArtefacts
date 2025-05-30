package io.dropwizard.migrations;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.PooledDataSourceFactory;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class MigrationsBundleTest_Purified {

    private final MigrationsBundle<TestMigrationConfiguration> migrationsBundle = new MigrationsBundle<TestMigrationConfiguration>() {

        @Override
        public PooledDataSourceFactory getDataSourceFactory(TestMigrationConfiguration configuration) {
            return configuration.getDataSource();
        }
    };

    private final MigrationsBundle<TestMigrationConfiguration> migrationsBundleWithScopedObjects = new MigrationsBundle<TestMigrationConfiguration>() {

        @Override
        public PooledDataSourceFactory getDataSourceFactory(TestMigrationConfiguration configuration) {
            return configuration.getDataSource();
        }

        @Override
        public Map<String, Object> getScopedObjects() {
            return new HashMap<>();
        }
    };

    private final Application<TestMigrationConfiguration> application = new Application<TestMigrationConfiguration>() {

        @Override
        public void run(TestMigrationConfiguration configuration, Environment environment) throws Exception {
        }
    };

    @Test
    void testMigrationsBundle_1() {
        assertThat(migrationsBundle.name()).isEqualTo("db");
    }

    @Test
    void testMigrationsBundle_2() {
        assertThat(migrationsBundle.getMigrationsFileName()).isEqualTo("migrations.xml");
    }

    @Test
    void testMigrationsBundle_3() {
        assertThat(migrationsBundle.getScopedObjects()).isNull();
    }

    @Test
    void testMigrationsBundle_4() {
        Bootstrap<TestMigrationConfiguration> bootstrap = new Bootstrap<>(application);
        migrationsBundle.initialize(bootstrap);
    }
}
