package org.graylog2.indexer.fieldtypes;

import com.google.common.collect.ImmutableSet;
import org.graylog.testing.mongodb.MongoDBInstance;
import org.graylog2.bindings.providers.MongoJackObjectMapperProvider;
import org.graylog2.database.MongoCollections;
import org.graylog2.shared.bindings.providers.ObjectMapperProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static com.google.common.collect.ImmutableSet.of;
import static org.assertj.core.api.Assertions.assertThat;

public class IndexFieldTypesServiceTest_Purified {

    @Rule
    public final MongoDBInstance mongodb = MongoDBInstance.createForClass();

    private IndexFieldTypesService dbService;

    @Before
    public void setUp() throws Exception {
        final MongoJackObjectMapperProvider objectMapperProvider = new MongoJackObjectMapperProvider(new ObjectMapperProvider().get());
        this.dbService = new IndexFieldTypesService(new MongoCollections(objectMapperProvider, mongodb.mongoConnection()));
    }

    @After
    public void tearDown() {
        mongodb.mongoConnection().getMongoDatabase().drop();
    }

    private IndexFieldTypesDTO createDto(String indexName, String indexSetId, Set<FieldTypeDTO> fields) {
        return IndexFieldTypesDTO.builder().indexName(indexName).indexSetId(indexSetId).fields(ImmutableSet.<FieldTypeDTO>builder().add(FieldTypeDTO.create("message", "text")).add(FieldTypeDTO.create("source", "text")).add(FieldTypeDTO.create("timestamp", "date")).add(FieldTypeDTO.create("http_method", "keyword")).add(FieldTypeDTO.create("http_status", "long")).addAll(fields).build()).build();
    }

    private IndexFieldTypesDTO createDto(String indexName, Set<FieldTypeDTO> fields) {
        return createDto(indexName, "abc123", fields);
    }

    @Test
    public void upsert_1() {
        assertThat(dbService.findAll().size()).isEqualTo(0);
    }

    @Test
    public void upsert_2_testMerged_2() {
        final IndexFieldTypesDTO newDto1 = createDto("graylog_0", Collections.emptySet());
        final IndexFieldTypesDTO newDto2 = createDto("graylog_1", Collections.emptySet());
        final IndexFieldTypesDTO upsertedDto1 = dbService.upsert(newDto1).orElse(null);
        final IndexFieldTypesDTO upsertedDto2 = dbService.upsert(newDto2).orElse(null);
        assertThat(upsertedDto1).isNotNull();
        assertThat(upsertedDto2).isNotNull();
        assertThat(upsertedDto1.indexName()).isEqualTo("graylog_0");
        assertThat(upsertedDto2.indexName()).isEqualTo("graylog_1");
        assertThat(dbService.findAll().size()).isEqualTo(2);
        assertThat(dbService.upsert(newDto1)).isNotPresent();
        assertThat(dbService.upsert(newDto2)).isNotPresent();
    }
}
