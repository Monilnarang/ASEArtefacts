package com.vmware.admiral.auth.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.auth.AuthBaseTest;
import com.vmware.admiral.auth.project.ProjectService.ProjectState;
import com.vmware.admiral.compute.container.GroupResourcePlacementService;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.ServiceDocumentQueryResult;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.Utils;
import com.vmware.xenon.services.common.QueryTask.QuerySpecification;

public class ProjectServiceWithCustomPropertiesTest_Purified extends AuthBaseTest {

    private static final String PROJECT_NAME = "test-name";

    private static final String PROJECT_DESCRIPTION = "testDescription";

    private static final boolean PROJECT_IS_PUBLIC = false;

    private static final String CUSTOM_PROP_KEY_A = "customPropertyA";

    private static final String CUSTOM_PROP_VAL_A = "valueA";

    private static final String CUSTOM_PROP_KEY_B = "customPropertyB";

    private static final String CUSTOM_PROP_VAL_B = "valueB";

    private ProjectState project;

    @Before
    public void setUp() throws Throwable {
        waitForServiceAvailability(ProjectFactoryService.SELF_LINK);
        waitForServiceAvailability(GroupResourcePlacementService.FACTORY_LINK);
        host.assumeIdentity(buildUserServicePath(USER_EMAIL_ADMIN));
        Map<String, String> customProperties = createCustomPropertiesMap(CUSTOM_PROP_KEY_A, CUSTOM_PROP_VAL_A, CUSTOM_PROP_KEY_B, CUSTOM_PROP_VAL_B);
        project = createProject(PROJECT_NAME, PROJECT_DESCRIPTION, PROJECT_IS_PUBLIC, customProperties);
    }

    private Map<String, String> createCustomPropertiesMap(String... keyValues) {
        assertNotNull(keyValues);
        assertTrue("keyValues must contain an even number of elements", keyValues.length % 2 == 0);
        HashMap<String, String> result = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            result.put(keyValues[i], keyValues[i + 1]);
        }
        return result;
    }

    @Test
    public void testCreateProjectWithCustomProperties_1() throws Throwable {
        assertNotNull(project);
    }

    @Test
    public void testCreateProjectWithCustomProperties_2() throws Throwable {
        assertEquals(PROJECT_NAME, project.name);
    }

    @Test
    public void testCreateProjectWithCustomProperties_3() throws Throwable {
        assertEquals(PROJECT_DESCRIPTION, project.description);
    }

    @Test
    public void testCreateProjectWithCustomProperties_4() throws Throwable {
        assertEquals(PROJECT_IS_PUBLIC, project.isPublic);
    }

    @Test
    public void testCreateProjectWithCustomProperties_5() throws Throwable {
        assertNotNull(project.customProperties);
    }

    @Test
    public void testCreateProjectWithCustomProperties_6() throws Throwable {
        assertEquals(3, project.customProperties.size());
    }

    @Test
    public void testCreateProjectWithCustomProperties_7() throws Throwable {
        assertEquals(CUSTOM_PROP_VAL_A, project.customProperties.get(CUSTOM_PROP_KEY_A));
    }

    @Test
    public void testCreateProjectWithCustomProperties_8() throws Throwable {
        assertEquals(CUSTOM_PROP_VAL_B, project.customProperties.get(CUSTOM_PROP_KEY_B));
    }
}
