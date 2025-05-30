package org.apache.hadoop.yarn.server.resourcemanager.webapp;

import com.google.inject.Guice;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.http.JettyUtils;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.Sets;
import org.apache.hadoop.yarn.api.records.QueueState;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.server.resourcemanager.MockRM;
import org.apache.hadoop.yarn.server.resourcemanager.ResourceManager;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.ResourceScheduler;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacityScheduler;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacitySchedulerConfiguration;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.QueuePath;
import org.apache.hadoop.yarn.server.resourcemanager.webapp.dao.NodeLabelInfo;
import org.apache.hadoop.yarn.server.resourcemanager.webapp.dao.NodeLabelsInfo;
import org.apache.hadoop.yarn.webapp.GenericExceptionHandler;
import org.apache.hadoop.yarn.webapp.GuiceServletConfig;
import org.apache.hadoop.yarn.webapp.JerseyTestBase;
import org.apache.hadoop.yarn.webapp.dao.QueueConfigInfo;
import org.apache.hadoop.yarn.webapp.dao.SchedConfUpdateInfo;
import org.apache.hadoop.yarn.webapp.util.YarnWebServiceUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacitySchedulerConfiguration.ACCESSIBLE_NODE_LABELS;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacitySchedulerConfiguration.CAPACITY;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacitySchedulerConfiguration.MAXIMUM_CAPACITY;
import static org.apache.hadoop.yarn.webapp.util.YarnWebServiceUtils.toJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacitySchedulerConfiguration.ORDERING_POLICY;

public class TestRMWebServicesConfigurationMutation_Purified extends JerseyTestBase {

    private static final Logger LOG = LoggerFactory.getLogger(TestRMWebServicesConfigurationMutation.class);

    private static final File CONF_FILE = new File(new File("target", "test-classes"), YarnConfiguration.CS_CONFIGURATION_FILE);

    private static final File OLD_CONF_FILE = new File(new File("target", "test-classes"), YarnConfiguration.CS_CONFIGURATION_FILE + ".tmp");

    private static final String LABEL_1 = "label1";

    public static final QueuePath ROOT = new QueuePath("root");

    public static final QueuePath ROOT_A = new QueuePath("root", "a");

    public static final QueuePath ROOT_A_A1 = QueuePath.createFromQueues("root", "a", "a1");

    public static final QueuePath ROOT_A_A2 = QueuePath.createFromQueues("root", "a", "a2");

    private static MockRM rm;

    private static String userName;

    private static CapacitySchedulerConfiguration csConf;

    private static YarnConfiguration conf;

    private static class WebServletModule extends ServletModule {

        @Override
        protected void configureServlets() {
            bind(JAXBContextResolver.class);
            bind(RMWebServices.class);
            bind(GenericExceptionHandler.class);
            try {
                userName = UserGroupInformation.getCurrentUser().getShortUserName();
            } catch (IOException ioe) {
                throw new RuntimeException("Unable to get current user name " + ioe.getMessage(), ioe);
            }
            csConf = new CapacitySchedulerConfiguration(new Configuration(false), false);
            setupQueueConfiguration(csConf);
            conf = new YarnConfiguration();
            conf.setClass(YarnConfiguration.RM_SCHEDULER, CapacityScheduler.class, ResourceScheduler.class);
            conf.set(YarnConfiguration.SCHEDULER_CONFIGURATION_STORE_CLASS, YarnConfiguration.MEMORY_CONFIGURATION_STORE);
            conf.set(YarnConfiguration.YARN_ADMIN_ACL, userName);
            try {
                if (CONF_FILE.exists()) {
                    if (!CONF_FILE.renameTo(OLD_CONF_FILE)) {
                        throw new RuntimeException("Failed to rename conf file");
                    }
                }
                FileOutputStream out = new FileOutputStream(CONF_FILE);
                csConf.writeXml(out);
                out.close();
            } catch (IOException e) {
                throw new RuntimeException("Failed to write XML file", e);
            }
            rm = new MockRM(conf);
            bind(ResourceManager.class).toInstance(rm);
            serve("/*").with(GuiceContainer.class);
            filter("/*").through(TestRMWebServicesAppsModification.TestRMCustomAuthFilter.class);
        }
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        GuiceServletConfig.setInjector(Guice.createInjector(new WebServletModule()));
    }

    private static void setupQueueConfiguration(CapacitySchedulerConfiguration config) {
        config.setQueues(CapacitySchedulerConfiguration.ROOT, new String[] { "a", "b", "c", "mappedqueue" });
        final String a = CapacitySchedulerConfiguration.ROOT + ".a";
        config.setCapacity(a, 25f);
        config.setMaximumCapacity(a, 50f);
        final String a1 = a + ".a1";
        final String a2 = a + ".a2";
        config.setQueues(a, new String[] { "a1", "a2" });
        config.setCapacity(a1, 100f);
        config.setCapacity(a2, 0f);
        final String b = CapacitySchedulerConfiguration.ROOT + ".b";
        config.setCapacity(b, 75f);
        final String c = CapacitySchedulerConfiguration.ROOT + ".c";
        config.setCapacity(c, 0f);
        final String c1 = c + ".c1";
        config.setQueues(c, new String[] { "c1" });
        config.setCapacity(c1, 0f);
        final String d = CapacitySchedulerConfiguration.ROOT + ".d";
        config.setCapacity(d, 0f);
        config.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, "g:hadoop:mappedqueue");
    }

    public TestRMWebServicesConfigurationMutation() {
        super(new WebAppDescriptor.Builder("org.apache.hadoop.yarn.server.resourcemanager.webapp").contextListenerClass(GuiceServletConfig.class).filterClass(com.google.inject.servlet.GuiceFilter.class).contextPath("jersey-guice-filter").servletPath("/").build());
    }

    private CapacitySchedulerConfiguration getSchedulerConf() throws JSONException {
        WebResource r = resource();
        ClientResponse response = r.path("ws").path("v1").path("cluster").queryParam("user.name", userName).path("scheduler-conf").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        JSONObject json = response.getEntity(JSONObject.class);
        JSONArray items = (JSONArray) json.get("property");
        CapacitySchedulerConfiguration parsedConf = new CapacitySchedulerConfiguration();
        for (int i = 0; i < items.length(); i++) {
            JSONObject obj = (JSONObject) items.get(i);
            parsedConf.set(obj.get("name").toString(), obj.get("value").toString());
        }
        return parsedConf;
    }

    private long getConfigVersion() throws Exception {
        WebResource r = resource();
        ClientResponse response = r.path("ws").path("v1").path("cluster").queryParam("user.name", userName).path(RMWSConsts.SCHEDULER_CONF_VERSION).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        JSONObject json = response.getEntity(JSONObject.class);
        return Long.parseLong(json.get("versionID").toString());
    }

    private void stopQueue(String... queuePaths) throws Exception {
        WebResource r = resource();
        ClientResponse response;
        SchedConfUpdateInfo updateInfo = new SchedConfUpdateInfo();
        Map<String, String> stoppedParam = new HashMap<>();
        stoppedParam.put(CapacitySchedulerConfiguration.STATE, QueueState.STOPPED.toString());
        for (String queue : queuePaths) {
            QueueConfigInfo stoppedInfo = new QueueConfigInfo(queue, stoppedParam);
            updateInfo.getUpdateQueueInfo().add(stoppedInfo);
        }
        response = r.path("ws").path("v1").path("cluster").path("scheduler-conf").queryParam("user.name", userName).accept(MediaType.APPLICATION_JSON).entity(toJson(updateInfo, SchedConfUpdateInfo.class), MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        CapacitySchedulerConfiguration newCSConf = ((CapacityScheduler) rm.getResourceScheduler()).getConfiguration();
        for (String queue : queuePaths) {
            assertEquals(QueueState.STOPPED, newCSConf.getState(queue));
        }
    }

    private String getConfValueForQueueAndLabelAndType(CapacityScheduler cs, QueuePath queuePath, String label, String type) {
        return cs.getConfiguration().get(CapacitySchedulerConfiguration.getNodeLabelPrefix(queuePath.getFullPath(), label) + type);
    }

    private Object logAndReturnJson(WebResource ws, String json) {
        LOG.info("Sending to web resource: {}, json: {}", ws, json);
        return json;
    }

    private String getAccessibleNodeLabelsCapacityPropertyName(String label) {
        return String.format("%s.%s.%s", ACCESSIBLE_NODE_LABELS, label, CAPACITY);
    }

    private String getAccessibleNodeLabelsMaxCapacityPropertyName(String label) {
        return String.format("%s.%s.%s", ACCESSIBLE_NODE_LABELS, label, MAXIMUM_CAPACITY);
    }

    @Override
    @After
    public void tearDown() throws Exception {
        if (rm != null) {
            rm.stop();
        }
        CONF_FILE.delete();
        if (!OLD_CONF_FILE.renameTo(CONF_FILE)) {
            throw new RuntimeException("Failed to re-copy old configuration file");
        }
        super.tearDown();
    }

    @Test
    public void testSchedulerConfigVersion_1() throws Exception {
        assertEquals(1, getConfigVersion());
    }

    @Test
    public void testSchedulerConfigVersion_2() throws Exception {
        assertEquals(2, getConfigVersion());
    }

    @Test
    public void testRemoveQueue_1() throws Exception {
        WebResource r = resource();
        ClientResponse response;
        SchedConfUpdateInfo updateInfo = new SchedConfUpdateInfo();
        updateInfo.getRemoveQueueInfo().add("root.a.a2");
        response = r.path("ws").path("v1").path("cluster").path("scheduler-conf").queryParam("user.name", userName).accept(MediaType.APPLICATION_JSON).entity(toJson(updateInfo, SchedConfUpdateInfo.class), MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRemoveQueue_2_testMerged_2() throws Exception {
        CapacitySchedulerConfiguration newCSConf = ((CapacityScheduler) rm.getResourceScheduler()).getConfiguration();
        assertEquals("Failed to remove the queue", 1, newCSConf.getQueues("root.a").length);
        assertEquals("Failed to remove the right queue", "a1", newCSConf.getQueues("root.a")[0]);
    }

    @Test
    public void testRemoveParentQueue_1() throws Exception {
        WebResource r = resource();
        ClientResponse response;
        SchedConfUpdateInfo updateInfo = new SchedConfUpdateInfo();
        updateInfo.getRemoveQueueInfo().add("root.c");
        response = r.path("ws").path("v1").path("cluster").path("scheduler-conf").queryParam("user.name", userName).accept(MediaType.APPLICATION_JSON).entity(toJson(updateInfo, SchedConfUpdateInfo.class), MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRemoveParentQueue_2_testMerged_2() throws Exception {
        CapacitySchedulerConfiguration newCSConf = ((CapacityScheduler) rm.getResourceScheduler()).getConfiguration();
        assertEquals(3, newCSConf.getQueues("root").length);
        assertNull(newCSConf.getQueues("root.c"));
    }
}
