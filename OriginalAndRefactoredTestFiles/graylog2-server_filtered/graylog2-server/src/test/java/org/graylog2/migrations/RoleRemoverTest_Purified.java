package org.graylog2.migrations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.graylog.testing.mongodb.MongoDBInstance;
import org.graylog2.database.MongoConnection;
import org.graylog2.users.RoleServiceImpl;
import org.graylog2.users.UserImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RoleRemoverTest_Purified {

    private static final String ADMIN_ROLE = "77777ef17ad37b64ee87eb57";

    private static final String FTM_MANAGER_ROLE = "77777ef17ad37b64ee87ebdd";

    private static final String TEST_ADMIN_USER_WITH_BOTH_ROLES = "test-admin-user-with-both-roles";

    private static final String TEST_USER_WITH_FTM_MANAGER_ROLE_ONLY = "test-user-with-field-type-manager-role-only";

    @Rule
    public final MongoDBInstance mongodb = MongoDBInstance.createForClass();

    private RoleRemover toTest;

    private MongoCollection<Document> rolesCollection;

    private MongoCollection<Document> usersCollection;

    @Before
    public void setUp() {
        final MongoConnection mongoConnection = mongodb.mongoConnection();
        mongodb.importFixture("roles_and_users.json", RoleRemoverTest.class);
        final MongoDatabase mongoDatabase = mongoConnection.getMongoDatabase();
        rolesCollection = mongoDatabase.getCollection(RoleServiceImpl.ROLES_COLLECTION_NAME);
        usersCollection = mongoDatabase.getCollection(UserImpl.COLLECTION_NAME);
        toTest = new RoleRemover(mongoConnection);
    }

    @Test
    public void testAttemptToRemoveNonExistingRoleDoesNotHaveEffectOnExistingUsersAndRoles_1() {
        assertEquals(2, rolesCollection.countDocuments());
    }

    @Test
    public void testAttemptToRemoveNonExistingRoleDoesNotHaveEffectOnExistingUsersAndRoles_2_testMerged_2() {
        final Document adminUserBefore = usersCollection.find(Filters.eq(UserImpl.USERNAME, TEST_ADMIN_USER_WITH_BOTH_ROLES)).first();
        final Document testUserBefore = usersCollection.find(Filters.eq(UserImpl.USERNAME, TEST_USER_WITH_FTM_MANAGER_ROLE_ONLY)).first();
        final Document adminUser = usersCollection.find(Filters.eq(UserImpl.USERNAME, TEST_ADMIN_USER_WITH_BOTH_ROLES)).first();
        final Document testUser = usersCollection.find(Filters.eq(UserImpl.USERNAME, TEST_USER_WITH_FTM_MANAGER_ROLE_ONLY)).first();
        assertEquals(adminUserBefore, adminUser);
        assertEquals(testUserBefore, testUser);
    }
}
