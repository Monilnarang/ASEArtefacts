package org.graylog2.contentpacks.facades;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.graph.Graph;
import org.graylog.plugins.sidecar.rest.models.Collector;
import org.graylog.plugins.sidecar.services.CollectorService;
import org.graylog.testing.mongodb.MongoDBFixtures;
import org.graylog.testing.mongodb.MongoDBInstance;
import org.graylog2.bindings.providers.MongoJackObjectMapperProvider;
import org.graylog2.contentpacks.EntityDescriptorIds;
import org.graylog2.contentpacks.model.ModelId;
import org.graylog2.contentpacks.model.ModelTypes;
import org.graylog2.contentpacks.model.entities.Entity;
import org.graylog2.contentpacks.model.entities.EntityDescriptor;
import org.graylog2.contentpacks.model.entities.EntityExcerpt;
import org.graylog2.contentpacks.model.entities.EntityV1;
import org.graylog2.contentpacks.model.entities.NativeEntity;
import org.graylog2.contentpacks.model.entities.NativeEntityDescriptor;
import org.graylog2.contentpacks.model.entities.SidecarCollectorEntity;
import org.graylog2.contentpacks.model.entities.references.ValueReference;
import org.graylog2.database.MongoCollections;
import org.graylog2.shared.bindings.providers.ObjectMapperProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.Collections;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

public class SidecarCollectorFacadeTest_Purified {

    @Rule
    public final MongoDBInstance mongodb = MongoDBInstance.createForClass();

    private final ObjectMapper objectMapper = new ObjectMapperProvider().get();

    private CollectorService collectorService;

    private SidecarCollectorFacade facade;

    @Before
    public void setUp() throws Exception {
        final MongoJackObjectMapperProvider mapperProvider = new MongoJackObjectMapperProvider(objectMapper);
        collectorService = new CollectorService(new MongoCollections(mapperProvider, mongodb.mongoConnection()));
        facade = new SidecarCollectorFacade(objectMapper, collectorService);
    }

    @Test
    public void createNativeEntity_1() {
        assertThat(collectorService.count()).isEqualTo(0L);
    }

    @Test
    public void createNativeEntity_2_testMerged_2() {
        final Entity entity = EntityV1.builder().id(ModelId.of("0")).type(ModelTypes.SIDECAR_COLLECTOR_V1).data(objectMapper.convertValue(SidecarCollectorEntity.create(ValueReference.of("filebeat"), ValueReference.of("exec"), ValueReference.of("linux"), ValueReference.of("/usr/lib/graylog-sidecar/filebeat"), ValueReference.of("-c %s"), ValueReference.of("test config -c %s"), ValueReference.of("")), JsonNode.class)).build();
        final NativeEntity<Collector> nativeEntity = facade.createNativeEntity(entity, Collections.emptyMap(), Collections.emptyMap(), "username");
        assertThat(collectorService.count()).isEqualTo(1L);
        final Collector collector = collectorService.findByName("filebeat");
        assertThat(collector).isNotNull();
        final NativeEntityDescriptor expectedDescriptor = NativeEntityDescriptor.create(entity.id(), collector.id(), ModelTypes.SIDECAR_COLLECTOR_V1, collector.name(), false);
        assertThat(nativeEntity.descriptor()).isEqualTo(expectedDescriptor);
        assertThat(nativeEntity.entity()).isEqualTo(collector);
    }
}
