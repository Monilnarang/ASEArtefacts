package com.iluwatar.hexagonal.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.iluwatar.hexagonal.domain.LotteryNumbers;
import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.LotteryTicketId;
import com.iluwatar.hexagonal.domain.PlayerDetails;
import com.iluwatar.hexagonal.mongo.MongoConnectionPropertiesLoader;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class MongoTicketRepositoryTest_Purified {

    private static final String TEST_DB = "lotteryTestDB";

    private static final String TEST_TICKETS_COLLECTION = "lotteryTestTickets";

    private static final String TEST_COUNTERS_COLLECTION = "testCounters";

    private MongoTicketRepository repository;

    @BeforeEach
    void init() {
        MongoConnectionPropertiesLoader.load();
        var mongoClient = new MongoClient(System.getProperty("mongo-host"), Integer.parseInt(System.getProperty("mongo-port")));
        mongoClient.dropDatabase(TEST_DB);
        mongoClient.close();
        repository = new MongoTicketRepository(TEST_DB, TEST_TICKETS_COLLECTION, TEST_COUNTERS_COLLECTION);
    }

    @Test
    void testSetup_1() {
        assertEquals(1, repository.getCountersCollection().countDocuments());
    }

    @Test
    void testSetup_2() {
        assertEquals(0, repository.getTicketsCollection().countDocuments());
    }

    @Test
    void testNextId_1() {
        assertEquals(1, repository.getNextId());
    }

    @Test
    void testNextId_2() {
        assertEquals(2, repository.getNextId());
    }

    @Test
    void testNextId_3() {
        assertEquals(3, repository.getNextId());
    }
}
