package com.vmware.admiral.compute.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.common.ManagementUriParts;
import com.vmware.admiral.common.util.ConfigurationUtil;
import com.vmware.admiral.compute.ComponentDescription;
import com.vmware.admiral.compute.ResourceType;
import com.vmware.admiral.compute.container.CompositeDescriptionService.CompositeDescription;
import com.vmware.admiral.compute.container.CompositeDescriptionService.CompositeDescriptionExpanded;
import com.vmware.admiral.compute.container.CompositeDescriptionService.CompositeDescriptionImages;
import com.vmware.admiral.compute.container.ContainerDescriptionService.ContainerDescription;
import com.vmware.admiral.service.common.ConfigurationService.ConfigurationState;
import com.vmware.xenon.common.Operation;
import com.vmware.xenon.common.ServiceDocument;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.Utils;

public class CompositeDescriptionServiceTest_Purified extends ComputeBaseTest {

    private ContainerDescription createdFirstContainer;

    private ContainerDescription createdSecondContainer;

    private CompositeDescription createdComposite;

    @Before
    public void setUp() throws Throwable {
        waitForServiceAvailability(CompositeDescriptionFactoryService.SELF_LINK);
    }

    @Before
    public void initObjects() throws Throwable {
        ContainerDescription firstContainer = new ContainerDescription();
        firstContainer.name = "testContainer";
        firstContainer.image = "registry.hub.docker.com/nginx";
        firstContainer._cluster = 1;
        firstContainer.maximumRetryCount = 1;
        firstContainer.privileged = true;
        firstContainer.affinity = new String[] { "cond1", "cond2" };
        firstContainer.customProperties = new HashMap<String, String>();
        firstContainer.customProperties.put("key1", "value1");
        firstContainer.customProperties.put("key2", "value2");
        createdFirstContainer = doPost(firstContainer, ContainerDescriptionService.FACTORY_LINK);
        ContainerDescription secondContainer = new ContainerDescription();
        secondContainer.name = "testContainer2";
        secondContainer.image = "registry.hub.docker.com/kitematic/hello-world-nginx";
        createdSecondContainer = doPost(secondContainer, ContainerDescriptionService.FACTORY_LINK);
        CompositeDescription composite = new CompositeDescription();
        composite.name = "testComposite";
        composite.customProperties = new HashMap<String, String>();
        composite.customProperties.put("key1", "value1");
        composite.customProperties.put("key2", "value2");
        composite.descriptionLinks = new ArrayList<String>();
        composite.descriptionLinks.add(createdFirstContainer.documentSelfLink);
        composite.descriptionLinks.add(createdSecondContainer.documentSelfLink);
        createdComposite = doPost(composite, CompositeDescriptionService.FACTORY_LINK);
    }

    private void checkRetrievedContainers(List<ComponentDescription> retrievedContainers, ContainerDescription... createdContainers) {
        for (ContainerDescription createdContainer : createdContainers) {
            for (int i = 0; i < retrievedContainers.size(); i++) {
                ContainerDescription retrievedContainer = (ContainerDescription) retrievedContainers.get(i).getServiceDocument();
                if (retrievedContainer.documentSelfLink.equals(createdContainer.documentSelfLink)) {
                    checkContainersForЕquality(createdContainer, retrievedContainer);
                    retrievedContainers.remove(i);
                    break;
                }
            }
        }
        assertEquals(0, retrievedContainers.size());
    }

    private void checkContainersForЕquality(ContainerDescription createdContainer, ServiceDocument retrievedContainerInput) {
        ContainerDescription retrievedContainer = (ContainerDescription) retrievedContainerInput;
        assertNotNull(createdContainer);
        assertNotNull(retrievedContainer);
        assertEquals(createdContainer.documentSelfLink, retrievedContainer.documentSelfLink);
        assertEquals(createdContainer.name, retrievedContainer.name);
        assertEquals(createdContainer.image, retrievedContainer.image);
        assertEquals(createdContainer._cluster, retrievedContainer._cluster);
        assertEquals(createdContainer.maximumRetryCount, retrievedContainer.maximumRetryCount);
        assertEquals(createdContainer.privileged, retrievedContainer.privileged);
        assertTrue(Arrays.equals(createdContainer.affinity, retrievedContainer.affinity));
        assertTrue(Arrays.equals(createdContainer.env, retrievedContainer.env));
        assertEquals(createdContainer.customProperties, retrievedContainer.customProperties);
        assertEquals(createdContainer.tenantLinks, retrievedContainer.tenantLinks);
    }

    private void checkCompositesForEquality(CompositeDescription createdComposite, CompositeDescription retrievedComposite) {
        assertNotNull(createdComposite);
        assertNotNull(retrievedComposite);
        assertEquals(createdComposite.documentSelfLink, retrievedComposite.documentSelfLink);
        assertEquals(createdComposite.name, retrievedComposite.name);
        assertEquals(createdComposite.customProperties, retrievedComposite.customProperties);
        assertEquals(createdComposite.descriptionLinks, retrievedComposite.descriptionLinks);
    }

    @Test
    public void testPutExpanded_1() throws Throwable {
        CompositeDescription cd = new CompositeDescription();
        cd = doPost(cd, CompositeDescriptionFactoryService.SELF_LINK);
        CompositeDescription foundCd = searchForDocument(CompositeDescription.class, cd.documentSelfLink);
        assertEquals(Utils.buildKind(CompositeDescription.class), foundCd.documentKind);
    }

    @Test
    public void testPutExpanded_2() throws Throwable {
        ContainerDescription container = new ContainerDescription();
        container = doPost(container, ContainerDescriptionService.FACTORY_LINK);
        containerComponent.updateServiceDocument(container);
        container = getDocument(ContainerDescription.class, container.documentSelfLink);
        assertEquals("updated", container.name);
    }
}
