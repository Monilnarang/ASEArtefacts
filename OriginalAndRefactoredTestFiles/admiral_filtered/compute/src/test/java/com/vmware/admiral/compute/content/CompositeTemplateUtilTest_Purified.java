package com.vmware.admiral.compute.content;

import static com.vmware.admiral.compute.content.CompositeTemplateUtil.assertComponentTypes;
import static com.vmware.admiral.compute.content.CompositeTemplateUtil.deserializeCompositeTemplate;
import static com.vmware.admiral.compute.content.CompositeTemplateUtil.deserializeDockerCompose;
import static com.vmware.admiral.compute.content.CompositeTemplateUtil.fromCompositeTemplateToDockerCompose;
import static com.vmware.admiral.compute.content.CompositeTemplateUtil.fromDockerComposeToCompositeTemplate;
import static com.vmware.admiral.compute.content.CompositeTemplateUtil.getYamlType;
import static com.vmware.admiral.compute.content.CompositeTemplateUtil.isNullOrEmpty;
import static com.vmware.admiral.compute.content.CompositeTemplateUtil.serializeCompositeTemplate;
import static com.vmware.admiral.compute.content.CompositeTemplateUtil.serializeDockerCompose;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.common.util.FileUtil;
import com.vmware.admiral.common.util.YamlMapper;
import com.vmware.admiral.compute.ResourceType;
import com.vmware.admiral.compute.container.CompositeDescriptionService.CompositeDescription.Status;
import com.vmware.admiral.compute.container.ContainerDescriptionService.ContainerDescription;
import com.vmware.admiral.compute.container.HealthChecker.HealthConfig;
import com.vmware.admiral.compute.container.LogConfig;
import com.vmware.admiral.compute.content.CompositeTemplateUtil.YamlType;
import com.vmware.admiral.compute.content.compose.DockerCompose;
import com.vmware.admiral.host.HostInitComputeServicesConfig;
import com.vmware.xenon.common.LocalizableValidationException;

public class CompositeTemplateUtilTest_Purified {

    @Before
    public void beforeForCompositeTemplateUtilTest() throws Throwable {
        HostInitComputeServicesConfig.initCompositeComponentRegistry();
    }

    public static void assertEqualsYamls(String expected, String actual) throws IOException {
        assertEqualsYamls(expected, actual, false);
    }

    @SuppressWarnings("rawtypes")
    public static void assertEqualsYamls(String expected, String actual, boolean ignoreEmptyValues) throws IOException {
        Map expectedMap = YamlMapper.objectMapper().readValue(expected, Map.class);
        Map actualMap = YamlMapper.objectMapper().readValue(actual, Map.class);
        if (ignoreEmptyValues) {
            removeEmptyValues(expectedMap);
            removeEmptyValues(actualMap);
        }
        Logger.getLogger(CompositeTemplateUtilTest.class.getName()).log(Level.INFO, "ignoreEmptyValues: " + ignoreEmptyValues + " \nassertEqualsYamls : expected=<" + YamlMapper.objectMapper().writeValueAsString(expectedMap) + "> and actual=<" + YamlMapper.objectMapper().writeValueAsString(actualMap) + ">");
        assertEquals(expectedMap, actualMap);
    }

    private static void removeEmptyValues(Map<String, Object> map) {
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (value instanceof Map) {
                removeEmptyValues((Map) value);
            }
            if (value instanceof Collection && ((Collection) value).isEmpty()) {
                map.remove(key);
            }
        }
    }

    public static String getContent(String filename) {
        return FileUtil.getResourceAsString("/compose/" + filename, true);
    }

    private static String toUnixLineEnding(String s) {
        if (s == null) {
            return null;
        }
        return s.replace("\r\n", "\n");
    }

    public static void assertContainersComponents(String type, int expected, Map<String, ComponentTemplate<?>> components) {
        assertNotNull(components);
        int actual = 0;
        for (ComponentTemplate<?> component : components.values()) {
            if (type.equals(component.type)) {
                actual++;
            }
        }
        assertEquals("Expected " + expected + " elements of type '" + type + "', but found " + actual + "!", expected, actual);
    }

    @Test
    public void testConvertDockerComposeToCompositeTemplateWithNetwork_1_testMerged_1() throws IOException {
        DockerCompose compose1 = deserializeDockerCompose(getContent("docker.simple.network.yaml"));
        CompositeTemplate template1 = fromDockerComposeToCompositeTemplate(compose1);
        assertComponentTypes(template1.components);
        assertContainersComponents(ResourceType.CONTAINER_TYPE.getContentType(), 3, template1.components);
        assertContainersComponents(ResourceType.NETWORK_TYPE.getContentType(), 2, template1.components);
        assertContainersComponents(ResourceType.VOLUME_TYPE.getContentType(), 0, template1.components);
        String template1Yaml = serializeCompositeTemplate(template1);
        assertEqualsYamls(toUnixLineEnding(template1Yaml), toUnixLineEnding(getContent("composite.simple.network.yaml")), true);
    }

    @Test
    public void testConvertDockerComposeToCompositeTemplateWithNetwork_5() throws IOException {
        CompositeTemplate expectedTemplate = deserializeCompositeTemplate(getContent("composite.simple.network.yaml"));
        String expectedTemplateYaml = serializeCompositeTemplate(expectedTemplate);
        assertEqualsYamls(toUnixLineEnding(expectedTemplateYaml), toUnixLineEnding(getContent("composite.simple.network.expected2.yaml")), true);
    }

    @Test
    public void testConvertDockerComposeToCompositeTemplateWithNetwork_7_testMerged_3() throws IOException {
        DockerCompose compose2 = deserializeDockerCompose(getContent("docker.complex.network.yaml"));
        CompositeTemplate template2 = fromDockerComposeToCompositeTemplate(compose2);
        assertComponentTypes(template2.components);
        assertContainersComponents(ResourceType.CONTAINER_TYPE.getContentType(), 3, template2.components);
        assertContainersComponents(ResourceType.NETWORK_TYPE.getContentType(), 3, template2.components);
        assertContainersComponents(ResourceType.VOLUME_TYPE.getContentType(), 0, template2.components);
        String template2Yaml = serializeCompositeTemplate(template2);
        assertEqualsYamls(toUnixLineEnding(getContent("composite.simple.network.expected.yaml")), toUnixLineEnding(template2Yaml), true);
    }

    @Test
    public void testConvertCompositeTemplateToDockerComposeWithNetwork_1_testMerged_1() throws IOException {
        DockerCompose expectedCompose = deserializeDockerCompose(getContent("docker.simple.network.yaml"));
        String expectedComposeYaml = serializeDockerCompose(expectedCompose);
        assertEqualsYamls(toUnixLineEnding(expectedComposeYaml), toUnixLineEnding(getContent("docker.simple.network.yaml")));
        expectedCompose = deserializeDockerCompose(getContent("docker.complex.network.yaml"));
        expectedComposeYaml = serializeDockerCompose(expectedCompose);
        CompositeTemplate template2 = deserializeCompositeTemplate(getContent("composite.complex.network.yaml"));
        DockerCompose compose2 = fromCompositeTemplateToDockerCompose(template2);
        String compose2Yaml = serializeDockerCompose(compose2);
        assertEqualsYamls(expectedComposeYaml, compose2Yaml);
    }

    @Test
    public void testConvertCompositeTemplateToDockerComposeWithNetwork_2() throws IOException {
        CompositeTemplate template = deserializeCompositeTemplate(getContent("composite.simple.network.yaml"));
        DockerCompose compose = fromCompositeTemplateToDockerCompose(template);
        String composeYaml = serializeDockerCompose(compose);
        assertEqualsYamls(toUnixLineEnding(composeYaml), toUnixLineEnding(getContent("docker.simple.network.expected.yaml")));
    }

    @Test
    public void testConvertSmallDockerCompose_1() throws IOException {
        DockerCompose compose1 = deserializeDockerCompose(getContent("docker.django.yaml"));
        CompositeTemplate template1 = fromDockerComposeToCompositeTemplate(compose1);
        String template1Yaml = serializeCompositeTemplate(template1);
        assertTrue((template1Yaml != null) && (!template1Yaml.isEmpty()));
    }

    @Test
    public void testConvertSmallDockerCompose_2() throws IOException {
        DockerCompose compose2 = deserializeDockerCompose(getContent("docker.rails.yaml"));
        CompositeTemplate template2 = fromDockerComposeToCompositeTemplate(compose2);
        String template2Yaml = serializeCompositeTemplate(template2);
        assertTrue((template2Yaml != null) && (!template2Yaml.isEmpty()));
    }
}
