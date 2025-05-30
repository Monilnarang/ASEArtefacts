package org.apache.hadoop.fs.permission;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAcl_Purified {

    private static AclEntry ENTRY1, ENTRY2, ENTRY3, ENTRY4, ENTRY5, ENTRY6, ENTRY7, ENTRY8, ENTRY9, ENTRY10, ENTRY11, ENTRY12, ENTRY13;

    private static AclStatus STATUS1, STATUS2, STATUS3, STATUS4;

    @BeforeClass
    public static void setUp() {
        AclEntry.Builder aclEntryBuilder = new AclEntry.Builder().setType(AclEntryType.USER).setName("user1").setPermission(FsAction.ALL);
        ENTRY1 = aclEntryBuilder.build();
        ENTRY2 = aclEntryBuilder.build();
        ENTRY3 = new AclEntry.Builder().setType(AclEntryType.GROUP).setName("group2").setPermission(FsAction.READ_WRITE).build();
        ENTRY4 = new AclEntry.Builder().setType(AclEntryType.OTHER).setPermission(FsAction.NONE).setScope(AclEntryScope.DEFAULT).build();
        ENTRY5 = new AclEntry.Builder().setType(AclEntryType.USER).setPermission(FsAction.ALL).build();
        ENTRY6 = new AclEntry.Builder().setType(AclEntryType.GROUP).setName("group3").setPermission(FsAction.READ_WRITE).setScope(AclEntryScope.DEFAULT).build();
        ENTRY7 = new AclEntry.Builder().setType(AclEntryType.OTHER).setPermission(FsAction.NONE).build();
        ENTRY8 = new AclEntry.Builder().setType(AclEntryType.USER).setName("user3").setPermission(FsAction.ALL).setScope(AclEntryScope.DEFAULT).build();
        ENTRY9 = new AclEntry.Builder().setType(AclEntryType.MASK).setPermission(FsAction.READ).build();
        ENTRY10 = new AclEntry.Builder().setType(AclEntryType.MASK).setPermission(FsAction.READ_EXECUTE).setScope(AclEntryScope.DEFAULT).build();
        ENTRY11 = new AclEntry.Builder().setType(AclEntryType.GROUP).setPermission(FsAction.READ).build();
        ENTRY12 = new AclEntry.Builder().setType(AclEntryType.GROUP).setPermission(FsAction.READ).setScope(AclEntryScope.DEFAULT).build();
        ENTRY13 = new AclEntry.Builder().setType(AclEntryType.USER).setPermission(FsAction.ALL).setScope(AclEntryScope.DEFAULT).build();
        AclStatus.Builder aclStatusBuilder = new AclStatus.Builder().owner("owner1").group("group1").addEntry(ENTRY1).addEntry(ENTRY3).addEntry(ENTRY4);
        STATUS1 = aclStatusBuilder.build();
        STATUS2 = aclStatusBuilder.build();
        STATUS3 = new AclStatus.Builder().owner("owner2").group("group2").stickyBit(true).build();
        STATUS4 = new AclStatus.Builder().addEntry(ENTRY1).addEntry(ENTRY3).addEntry(ENTRY4).addEntry(ENTRY5).addEntry(ENTRY6).addEntry(ENTRY7).addEntry(ENTRY8).addEntry(ENTRY9).addEntry(ENTRY10).addEntry(ENTRY11).addEntry(ENTRY12).addEntry(ENTRY13).build();
    }

    @Test
    public void testEntryEquals_1() {
        assertNotSame(ENTRY1, ENTRY2);
    }

    @Test
    public void testEntryEquals_2() {
        assertNotSame(ENTRY1, ENTRY3);
    }

    @Test
    public void testEntryEquals_3() {
        assertNotSame(ENTRY1, ENTRY4);
    }

    @Test
    public void testEntryEquals_4() {
        assertNotSame(ENTRY2, ENTRY3);
    }

    @Test
    public void testEntryEquals_5() {
        assertNotSame(ENTRY2, ENTRY4);
    }

    @Test
    public void testEntryEquals_6() {
        assertNotSame(ENTRY3, ENTRY4);
    }

    @Test
    public void testEntryEquals_7() {
        assertEquals(ENTRY1, ENTRY1);
    }

    @Test
    public void testEntryEquals_8() {
        assertEquals(ENTRY2, ENTRY2);
    }

    @Test
    public void testEntryEquals_9() {
        assertEquals(ENTRY1, ENTRY2);
    }

    @Test
    public void testEntryEquals_10() {
        assertEquals(ENTRY2, ENTRY1);
    }

    @Test
    public void testEntryEquals_11() {
        assertFalse(ENTRY1.equals(ENTRY3));
    }

    @Test
    public void testEntryEquals_12() {
        assertFalse(ENTRY1.equals(ENTRY4));
    }

    @Test
    public void testEntryEquals_13() {
        assertFalse(ENTRY3.equals(ENTRY4));
    }

    @Test
    public void testEntryEquals_14() {
        assertFalse(ENTRY1.equals(null));
    }

    @Test
    public void testEntryEquals_15() {
        assertFalse(ENTRY1.equals(new Object()));
    }

    @Test
    public void testEntryHashCode_1() {
        assertEquals(ENTRY1.hashCode(), ENTRY2.hashCode());
    }

    @Test
    public void testEntryHashCode_2() {
        assertFalse(ENTRY1.hashCode() == ENTRY3.hashCode());
    }

    @Test
    public void testEntryHashCode_3() {
        assertFalse(ENTRY1.hashCode() == ENTRY4.hashCode());
    }

    @Test
    public void testEntryHashCode_4() {
        assertFalse(ENTRY3.hashCode() == ENTRY4.hashCode());
    }

    @Test
    public void testEntryScopeIsAccessIfUnspecified_1() {
        assertEquals(AclEntryScope.ACCESS, ENTRY1.getScope());
    }

    @Test
    public void testEntryScopeIsAccessIfUnspecified_2() {
        assertEquals(AclEntryScope.ACCESS, ENTRY2.getScope());
    }

    @Test
    public void testEntryScopeIsAccessIfUnspecified_3() {
        assertEquals(AclEntryScope.ACCESS, ENTRY3.getScope());
    }

    @Test
    public void testEntryScopeIsAccessIfUnspecified_4() {
        assertEquals(AclEntryScope.DEFAULT, ENTRY4.getScope());
    }

    @Test
    public void testStatusEquals_1() {
        assertNotSame(STATUS1, STATUS2);
    }

    @Test
    public void testStatusEquals_2() {
        assertNotSame(STATUS1, STATUS3);
    }

    @Test
    public void testStatusEquals_3() {
        assertNotSame(STATUS2, STATUS3);
    }

    @Test
    public void testStatusEquals_4() {
        assertEquals(STATUS1, STATUS1);
    }

    @Test
    public void testStatusEquals_5() {
        assertEquals(STATUS2, STATUS2);
    }

    @Test
    public void testStatusEquals_6() {
        assertEquals(STATUS1, STATUS2);
    }

    @Test
    public void testStatusEquals_7() {
        assertEquals(STATUS2, STATUS1);
    }

    @Test
    public void testStatusEquals_8() {
        assertFalse(STATUS1.equals(STATUS3));
    }

    @Test
    public void testStatusEquals_9() {
        assertFalse(STATUS2.equals(STATUS3));
    }

    @Test
    public void testStatusEquals_10() {
        assertFalse(STATUS1.equals(null));
    }

    @Test
    public void testStatusEquals_11() {
        assertFalse(STATUS1.equals(new Object()));
    }

    @Test
    public void testStatusHashCode_1() {
        assertEquals(STATUS1.hashCode(), STATUS2.hashCode());
    }

    @Test
    public void testStatusHashCode_2() {
        assertFalse(STATUS1.hashCode() == STATUS3.hashCode());
    }

    @Test
    public void testToString_1() {
        assertEquals("user:user1:rwx", ENTRY1.toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("user:user1:rwx", ENTRY2.toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("group:group2:rw-", ENTRY3.toString());
    }

    @Test
    public void testToString_4() {
        assertEquals("default:other::---", ENTRY4.toString());
    }

    @Test
    public void testToString_5() {
        assertEquals("owner: owner1, group: group1, acl: {entries: [user:user1:rwx, group:group2:rw-, default:other::---], stickyBit: false}", STATUS1.toString());
    }

    @Test
    public void testToString_6() {
        assertEquals("owner: owner1, group: group1, acl: {entries: [user:user1:rwx, group:group2:rw-, default:other::---], stickyBit: false}", STATUS2.toString());
    }

    @Test
    public void testToString_7() {
        assertEquals("owner: owner2, group: group2, acl: {entries: [], stickyBit: true}", STATUS3.toString());
    }
}
