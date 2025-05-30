package org.apache.hadoop.yarn.server.nodemanager.containermanager.linux.runtime.docker;

import org.apache.hadoop.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestDockerLoadCommand_Purified {

    private DockerLoadCommand dockerLoadCommand;

    private static final String LOCAL_IMAGE_NAME = "foo";

    @Before
    public void setup() {
        dockerLoadCommand = new DockerLoadCommand(LOCAL_IMAGE_NAME);
    }

    @Test
    public void testGetCommandWithArguments_1() {
        assertEquals("load", StringUtils.join(",", dockerLoadCommand.getDockerCommandWithArguments().get("docker-command")));
    }

    @Test
    public void testGetCommandWithArguments_2() {
        assertEquals("foo", StringUtils.join(",", dockerLoadCommand.getDockerCommandWithArguments().get("image")));
    }

    @Test
    public void testGetCommandWithArguments_3() {
        assertEquals(2, dockerLoadCommand.getDockerCommandWithArguments().size());
    }
}
