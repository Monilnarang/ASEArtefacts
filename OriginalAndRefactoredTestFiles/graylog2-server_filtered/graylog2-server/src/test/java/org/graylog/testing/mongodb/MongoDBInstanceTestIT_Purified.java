package org.graylog.testing.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static org.assertj.core.api.Assertions.assertThat;

public class MongoDBInstanceTestIT_Purified {

    @Rule
    public final MongoDBInstance mongodb = MongoDBInstance.createForClass();

    private MongoCollection<Document> collection1;

    private MongoCollection<Document> collection2;

    @Before
    public void setUp() throws Exception {
        collection1 = mongodb.mongoConnection().getMongoDatabase().getCollection("test_1");
        collection2 = mongodb.mongoConnection().getMongoDatabase().getCollection("test_2");
    }

    @Test
    public void clientWorks_1() {
        assertThat(mongodb.mongoConnection()).isNotNull();
    }

    @Test
    public void clientWorks_2() {
        assertThat(mongodb.mongoConnection().getMongoDatabase()).isNotNull();
    }

    @Test
    public void clientWorks_3() {
        assertThat(mongodb.mongoConnection().getMongoDatabase().getName()).isEqualTo("graylog");
    }

    @Test
    public void clientWorks_4_testMerged_4() {
        final Document document = new Document("hello", "world");
        collection1.insertOne(document);
        assertThat(collection1.countDocuments()).isEqualTo(1);
        assertThat(collection1.find(Filters.eq("hello", "world")).first()).isEqualTo(document);
        assertThat(collection1.find(Filters.eq("hello", "world2")).first()).isNull();
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void fixturesWork_1() {
        assertThat(collection1.countDocuments()).isEqualTo(2);
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void fixturesWork_2() {
        assertThat(collection1.find(Filters.eq("hello", "world")).first().get("_id")).isEqualTo(new ObjectId("54e3deadbeefdeadbeefaffe"));
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void fixturesWork_3() {
        assertThat(collection1.find(Filters.eq("hello", "world2")).first()).isNull();
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void fixturesWork_4() {
        assertThat(collection1.find(Filters.eq("another", "test")).first().get("_id")).isEqualTo(new ObjectId("54e3deadbeefdeadbeefafff"));
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void fixturesWork_5() {
        assertThat(collection2.countDocuments()).isEqualTo(1);
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void fixturesWork_6() {
        assertThat(collection2.find(Filters.eq("field_a", "content1")).first().get("_id")).isEqualTo(new ObjectId("54e3deadbeefdeadbeefaffe"));
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void fixturesWork_7() {
        assertThat(collection2.find(Filters.eq("field_a", "missing")).first()).isNull();
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void fixturesWork_8_testMerged_8() {
        final Date date = new Date(ZonedDateTime.parse("2018-12-31T23:59:59.999Z").toInstant().toEpochMilli());
        assertThat(collection2.find(Filters.gt("created_at", date)).first().get("_id")).isEqualTo(new ObjectId("54e3deadbeefdeadbeefaffe"));
        assertThat(collection2.find(Filters.lte("created_at", date)).first()).isNull();
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void globalFixturesWork_1() {
        assertThat(collection1.countDocuments()).isEqualTo(2);
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void globalFixturesWork_2() {
        assertThat(collection1.find(Filters.eq("hello", "world")).first().get("_id")).isEqualTo(new ObjectId("54e3deadbeefdeadbeefaffe"));
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void globalFixturesWork_3() {
        assertThat(collection1.find(Filters.eq("hello", "world2")).first()).isNull();
    }

    @Test
    @MongoDBFixtures("MongoDBBaseTestIT.json")
    public void globalFixturesWork_4() {
        assertThat(collection1.find(Filters.eq("another", "test")).first().get("_id")).isEqualTo(new ObjectId("54e3deadbeefdeadbeefafff"));
    }
}
