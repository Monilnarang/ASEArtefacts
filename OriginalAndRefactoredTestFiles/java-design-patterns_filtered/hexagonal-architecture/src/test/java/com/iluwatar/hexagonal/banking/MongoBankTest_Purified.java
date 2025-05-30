package com.iluwatar.hexagonal.banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.commands.ServerAddress;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.transitions.Mongod;
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess;
import de.flapdoodle.reverse.TransitionWalker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MongoBankTest_Purified {

    private static final String TEST_DB = "lotteryDBTest";

    private static final String TEST_ACCOUNTS_COLLECTION = "testAccounts";

    private static MongoClient mongoClient;

    private static MongoDatabase mongoDatabase;

    private MongoBank mongoBank;

    private static TransitionWalker.ReachedState<RunningMongodProcess> mongodProcess;

    private static ServerAddress serverAddress;

    @BeforeAll
    static void setUp() {
        mongodProcess = Mongod.instance().start(Version.Main.V7_0);
        serverAddress = mongodProcess.current().getServerAddress();
        mongoClient = MongoClients.create("mongodb://" + serverAddress.toString());
        mongoClient.startSession();
        mongoDatabase = mongoClient.getDatabase(TEST_DB);
    }

    @AfterAll
    static void tearDown() {
        mongoClient.close();
        mongodProcess.close();
    }

    @BeforeEach
    void init() {
        System.setProperty("mongo-host", serverAddress.getHost());
        System.setProperty("mongo-port", String.valueOf(serverAddress.getPort()));
        mongoDatabase.drop();
        mongoBank = new MongoBank(mongoDatabase.getName(), TEST_ACCOUNTS_COLLECTION);
    }

    @Test
    void testFundTransfers_1() {
        assertEquals(0, mongoBank.getFunds("000-000"));
    }

    @Test
    void testFundTransfers_2_testMerged_2() {
        mongoBank.setFunds("000-000", 10);
        assertEquals(10, mongoBank.getFunds("000-000"));
        assertEquals(0, mongoBank.getFunds("111-111"));
        mongoBank.transferFunds(9, "000-000", "111-111");
        assertEquals(1, mongoBank.getFunds("000-000"));
        assertEquals(9, mongoBank.getFunds("111-111"));
    }
}
