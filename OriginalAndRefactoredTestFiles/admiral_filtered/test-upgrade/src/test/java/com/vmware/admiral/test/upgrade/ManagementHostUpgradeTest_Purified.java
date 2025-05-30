package com.vmware.admiral.test.upgrade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import com.vmware.admiral.common.util.ServiceClientFactory;
import com.vmware.admiral.test.upgrade.version1.UpgradeOldHost;
import com.vmware.admiral.test.upgrade.version1.UpgradeOldService1.UpgradeOldService1State;
import com.vmware.admiral.test.upgrade.version1.UpgradeOldService2.UpgradeOldService2State;
import com.vmware.admiral.test.upgrade.version1.UpgradeOldService3.UpgradeOldService3State;
import com.vmware.admiral.test.upgrade.version1.UpgradeOldService4.UpgradeOldService4State;
import com.vmware.admiral.test.upgrade.version1.UpgradeOldService5.UpgradeOldService5State;
import com.vmware.admiral.test.upgrade.version1.UpgradeOldService6.UpgradeOldService6State;
import com.vmware.admiral.test.upgrade.version1.UpgradeOldService7.UpgradeOldService7State;
import com.vmware.admiral.test.upgrade.version1.UpgradeOldService8.UpgradeOldService8State;
import com.vmware.admiral.test.upgrade.version2.BrandNewService.BrandNewServiceState;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewHost;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService1;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService1.UpgradeNewService1State;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService2;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService2.UpgradeNewService2State;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService3;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService3.UpgradeNewService3State;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService4;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService4.UpgradeNewService4State;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService5;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService5.UpgradeNewService5State;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService6;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService6.UpgradeNewService6State;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService7;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService7.UpgradeNewService7State;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService8;
import com.vmware.admiral.test.upgrade.version2.UpgradeNewService8.UpgradeNewService8State;

public class ManagementHostUpgradeTest_Purified extends ManagementHostBaseTest {

    private static final TemporaryFolder SANDBOX = new TemporaryFolder();

    private int hostPort;

    private String hostSandbox;

    @BeforeClass
    public static void beforeClass() {
        serviceClient = ServiceClientFactory.createServiceClient(null);
    }

    @AfterClass
    public static void afterClass() {
        serviceClient.stop();
    }

    @Before
    public void beforeTest() throws Exception {
        hostPort = 0;
        SANDBOX.create();
        hostSandbox = SANDBOX.getRoot().toPath().toString();
    }

    @After
    public void afterTest() {
        if (upgradeHost != null) {
            stopHost(upgradeHost);
        }
        SANDBOX.delete();
    }

    @Test
    public void testService1AddNewFieldOptional_1_testMerged_1() throws Throwable {
        UpgradeOldService1State oldState = new UpgradeOldService1State();
        UpgradeOldService1State instance1 = createUpgradeServiceInstance(oldState);
        assertNotNull(instance1);
        assertEquals(oldState.field1, instance1.field1);
        assertEquals(oldState.field2, instance1.field2);
        UpgradeNewService1State instance2 = getUpgradeServiceInstance(instance1.documentSelfLink, UpgradeNewService1State.class);
        assertNotNull(instance2);
        assertEquals(oldState.field1, instance2.field1);
        assertEquals(oldState.field2, instance2.field2);
        assertEquals(null, instance2.field3);
        assertEquals(null, instance2.field4);
        assertEquals(null, instance2.field5);
        instance2 = updateUpgradeServiceInstance(instance2);
        UpgradeNewService1State newState = new UpgradeNewService1State();
        instance2 = createUpgradeServiceInstance(newState);
        assertEquals(newState.field1, instance2.field1);
        assertEquals(newState.field2, instance2.field2);
        assertEquals(newState.field3, instance2.field3);
        assertEquals(newState.field4, instance2.field4);
        assertEquals(newState.field5, instance2.field5);
    }

    @Test
    public void testService1AddNewFieldOptional_22() throws Throwable {
        Collection<UpgradeNewService1State> instances;
        instances = queryUpgradeServiceInstances(UpgradeNewService1State.class, "field3", "new");
        assertEquals(1, instances.size());
    }

    @Test
    public void testService2AddNewFieldRequired_1_testMerged_1() throws Throwable {
        UpgradeOldService2State oldState = new UpgradeOldService2State();
        UpgradeOldService2State instance1 = createUpgradeServiceInstance(oldState);
        assertNotNull(instance1);
        assertEquals(oldState.field1, instance1.field1);
        assertEquals(oldState.field2, instance1.field2);
        UpgradeNewService2State instance2 = getUpgradeServiceInstance(instance1.documentSelfLink, UpgradeNewService2State.class);
        assertNotNull(instance2);
        assertEquals(oldState.field1, instance2.field1);
        assertEquals(oldState.field2, instance2.field2);
        assertEquals("default value", instance2.field3);
        assertEquals(Long.valueOf(42), instance2.field4);
        assertEquals(Arrays.asList("a", "b", "c"), instance2.field5);
        instance2 = updateUpgradeServiceInstance(instance2);
        assertEquals("default value updated", instance2.field3);
        UpgradeNewService2State newState = new UpgradeNewService2State();
        instance2 = createUpgradeServiceInstance(newState);
        assertEquals(newState.field1, instance2.field1);
        assertEquals(newState.field2, instance2.field2);
        assertEquals(newState.field3, instance2.field3);
        assertEquals(newState.field4, instance2.field4);
        assertEquals(newState.field5, instance2.field5);
    }

    @Test
    public void testService2AddNewFieldRequired_10_testMerged_2() throws Throwable {
        Collection<UpgradeNewService2State> instances;
        instances = queryUpgradeServiceInstances(UpgradeNewService2State.class, "field3", "default value");
        assertEquals(1, instances.size());
        assertEquals(0, instances.size());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testService5SplitFieldValue_1_testMerged_1() throws Throwable {
        UpgradeOldService5State oldState = new UpgradeOldService5State();
        UpgradeOldService5State instance1 = createUpgradeServiceInstance(oldState);
        assertNotNull(instance1);
        assertEquals(oldState.field1, instance1.field1);
        assertEquals(oldState.field2, instance1.field2);
        assertEquals(oldState.field3, instance1.field3);
        assertEquals(oldState.field4, instance1.field4);
        assertEquals(oldState.field5, instance1.field5);
        assertEquals(oldState.field678, instance1.field678);
        UpgradeNewService5State instance2 = getUpgradeServiceInstance(instance1.documentSelfLink, UpgradeNewService5State.class);
        assertNotNull(instance2);
        assertEquals(oldState.field1, instance2.field1);
        assertEquals(oldState.field2, instance2.field2);
        assertEquals(null, instance2.field3);
        assertEquals(null, instance2.field4);
        assertEquals(null, instance2.field5);
        assertEquals("field3#field4#field5", instance2.field345);
        assertEquals("field6", instance2.field6);
        assertEquals("field7", instance2.field7);
        assertEquals("field8", instance2.field8);
        assertEquals(null, instance2.field678);
        instance2 = updateUpgradeServiceInstance(instance2);
        assertEquals("field3#field4#field5-new", instance2.field345);
        UpgradeNewService5State newState = new UpgradeNewService5State();
        instance2 = createUpgradeServiceInstance(newState);
        assertEquals(newState.field1, instance2.field1);
        assertEquals(newState.field2, instance2.field2);
        assertEquals(newState.field345, instance2.field345);
        assertEquals(newState.field6, instance2.field6);
        assertEquals(newState.field7, instance2.field7);
        assertEquals(newState.field8, instance2.field8);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testService5SplitFieldValue_19_testMerged_2() throws Throwable {
        Collection<UpgradeNewService5State> instances;
        instances = queryUpgradeServiceInstances(UpgradeNewService5State.class, "field3", "field3", "field4", "field4", "field5", "field5");
        assertEquals(0, instances.size());
        instances = queryUpgradeServiceInstances(UpgradeNewService5State.class, "field345", "field3#field4#field5");
        assertEquals(1, instances.size());
    }

    @Test
    public void testService7ChangeFieldName_1_testMerged_1() throws Throwable {
        UpgradeOldService7State oldState = new UpgradeOldService7State();
        UpgradeOldService7State instance1 = createUpgradeServiceInstance(oldState);
        assertNotNull(instance1);
        assertEquals(oldState.field1, instance1.field1);
        assertEquals(oldState.field2, instance1.field2);
        assertEquals(oldState.field3, instance1.field3);
        UpgradeNewService7State instance2 = getUpgradeServiceInstance(instance1.documentSelfLink, UpgradeNewService7State.class);
        assertNotNull(instance2);
        assertEquals(oldState.field1, instance2.field1);
        assertEquals(oldState.field2, instance2.field2);
        assertEquals(oldState.field3, instance2.upgradedField3);
        instance2 = updateUpgradeServiceInstance(instance2);
        assertEquals("field3-new", instance2.upgradedField3);
        UpgradeNewService7State newState = new UpgradeNewService7State();
        instance2 = createUpgradeServiceInstance(newState);
        assertEquals(newState.field1, instance2.field1);
        assertEquals(newState.field2, instance2.field2);
        assertEquals(newState.upgradedField3, instance2.upgradedField3);
    }

    @Test
    public void testService7ChangeFieldName_9_testMerged_2() throws Throwable {
        Collection<UpgradeNewService7State> instances;
        instances = queryUpgradeServiceInstances(UpgradeNewService7State.class, "field3", "field3");
        assertEquals(0, instances.size());
        instances = queryUpgradeServiceInstances(UpgradeNewService7State.class, "upgradedField3", "field3");
        assertEquals(1, instances.size());
    }

    @Test
    public void testService8RemoveField_1_testMerged_1() throws Throwable {
        UpgradeOldService8State oldState = new UpgradeOldService8State();
        UpgradeOldService8State instance1 = createUpgradeServiceInstance(oldState);
        assertNotNull(instance1);
        assertEquals(oldState.field1, instance1.field1);
        assertEquals(oldState.field2, instance1.field2);
        assertEquals(oldState.field3, instance1.field3);
        UpgradeNewService8State instance2 = getUpgradeServiceInstance(instance1.documentSelfLink, UpgradeNewService8State.class);
        assertNotNull(instance2);
        assertEquals(oldState.field1, instance2.field1);
        assertEquals(oldState.field2, instance2.field2);
        instance2 = updateUpgradeServiceInstance(instance2);
        assertEquals("foo-new", instance2.field1);
        UpgradeNewService8State newState = new UpgradeNewService8State();
        instance2 = createUpgradeServiceInstance(newState);
        assertEquals(newState.field1, instance2.field1);
        assertEquals(newState.field2, instance2.field2);
    }

    @Test
    public void testService8RemoveField_8() throws Throwable {
        Collection<UpgradeNewService7State> instances;
        instances = queryUpgradeServiceInstances(UpgradeNewService7State.class, "field3", "field3");
        assertEquals(0, instances.size());
    }
}
