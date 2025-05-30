package org.apache.hadoop.yarn.server.nodemanager.containermanager.linux.runtime.docker;

import org.apache.hadoop.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDockerStartCommand_Purified {

    private DockerStartCommand dockerStartCommand;

    private static final String CONTAINER_NAME = "foo";

    @Before
    public void setUp() {
        dockerStartCommand = new DockerStartCommand(CONTAINER_NAME);
    }

    @Test
    public void testGetCommandWithArguments_1() {
        assertEquals("start", StringUtils.join(",", dockerStartCommand.getDockerCommandWithArguments().get("docker-command")));
    }

    @Test
    public void testGetCommandWithArguments_2() {
        assertEquals("foo", StringUtils.join(",", dockerStartCommand.getDockerCommandWithArguments().get("name")));
    }

    @Test
    public void testGetCommandWithArguments_3() {
        assertEquals(2, dockerStartCommand.getDockerCommandWithArguments().size());
    }
}
