package org.apache.hadoop.yarn.server.nodemanager.containermanager.linux.runtime.docker;

import org.apache.hadoop.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestDockerPullCommand_Purified {

    private DockerPullCommand dockerPullCommand;

    private static final String IMAGE_NAME = "foo";

    @Before
    public void setup() {
        dockerPullCommand = new DockerPullCommand(IMAGE_NAME);
    }

    @Test
    public void testGetCommandWithArguments_1() {
        assertEquals("pull", StringUtils.join(",", dockerPullCommand.getDockerCommandWithArguments().get("docker-command")));
    }

    @Test
    public void testGetCommandWithArguments_2() {
        assertEquals("foo", StringUtils.join(",", dockerPullCommand.getDockerCommandWithArguments().get("image")));
    }

    @Test
    public void testGetCommandWithArguments_3() {
        assertEquals(2, dockerPullCommand.getDockerCommandWithArguments().size());
    }
}
