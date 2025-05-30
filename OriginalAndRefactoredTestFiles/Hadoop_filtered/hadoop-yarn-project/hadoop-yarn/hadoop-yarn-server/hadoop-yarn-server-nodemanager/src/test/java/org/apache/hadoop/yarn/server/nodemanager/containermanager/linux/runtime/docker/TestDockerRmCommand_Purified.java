package org.apache.hadoop.yarn.server.nodemanager.containermanager.linux.runtime.docker;

import static org.junit.Assert.assertEquals;
import org.apache.hadoop.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class TestDockerRmCommand_Purified {

    private DockerRmCommand dockerRmCommand;

    private DockerRmCommand dockerRmCommandWithCgroupArg;

    private DockerRmCommand dockerRmCommandWithEmptyCgroupArg;

    private static final String CONTAINER_NAME = "foo";

    private static final String CGROUP_HIERARCHY_NAME = "hadoop-yarn";

    @Before
    public void setUp() {
        dockerRmCommand = new DockerRmCommand(CONTAINER_NAME, null);
        dockerRmCommandWithCgroupArg = new DockerRmCommand(CONTAINER_NAME, CGROUP_HIERARCHY_NAME);
        dockerRmCommandWithEmptyCgroupArg = new DockerRmCommand(CONTAINER_NAME, "");
    }

    @Test
    public void testGetCommandWithArguments_1() {
        assertEquals("rm", StringUtils.join(",", dockerRmCommand.getDockerCommandWithArguments().get("docker-command")));
    }

    @Test
    public void testGetCommandWithArguments_2() {
        assertEquals("foo", StringUtils.join(",", dockerRmCommand.getDockerCommandWithArguments().get("name")));
    }

    @Test
    public void testGetCommandWithArguments_3() {
        assertEquals(2, dockerRmCommand.getDockerCommandWithArguments().size());
    }

    @Test
    public void testGetCommandWithCgroup_1() {
        assertEquals("rm", StringUtils.join(",", dockerRmCommandWithCgroupArg.getDockerCommandWithArguments().get("docker-command")));
    }

    @Test
    public void testGetCommandWithCgroup_2() {
        assertEquals("foo", StringUtils.join(",", dockerRmCommandWithCgroupArg.getDockerCommandWithArguments().get("name")));
    }

    @Test
    public void testGetCommandWithCgroup_3() {
        assertEquals(CGROUP_HIERARCHY_NAME, StringUtils.join(",", dockerRmCommandWithCgroupArg.getDockerCommandWithArguments().get("hierarchy")));
    }

    @Test
    public void testGetCommandWithCgroup_4() {
        assertEquals(3, dockerRmCommandWithCgroupArg.getDockerCommandWithArguments().size());
    }

    @Test
    public void testGetCommandWithEmptyCgroup_1() {
        assertEquals("rm", StringUtils.join(",", dockerRmCommandWithEmptyCgroupArg.getDockerCommandWithArguments().get("docker-command")));
    }

    @Test
    public void testGetCommandWithEmptyCgroup_2() {
        assertEquals("foo", StringUtils.join(",", dockerRmCommandWithEmptyCgroupArg.getDockerCommandWithArguments().get("name")));
    }

    @Test
    public void testGetCommandWithEmptyCgroup_3() {
        assertEquals(2, dockerRmCommandWithEmptyCgroupArg.getDockerCommandWithArguments().size());
    }
}
