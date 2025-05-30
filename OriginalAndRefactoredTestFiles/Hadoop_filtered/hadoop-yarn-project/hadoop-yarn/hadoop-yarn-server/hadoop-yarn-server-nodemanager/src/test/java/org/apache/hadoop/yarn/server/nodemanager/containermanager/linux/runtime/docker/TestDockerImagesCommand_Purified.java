package org.apache.hadoop.yarn.server.nodemanager.containermanager.linux.runtime.docker;

import org.apache.hadoop.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestDockerImagesCommand_Purified {

    private DockerImagesCommand dockerImagesCommand;

    private static final String IMAGE_NAME = "foo";

    @Before
    public void setup() {
        dockerImagesCommand = new DockerImagesCommand();
    }

    @Test
    public void testAllImages_1() {
        assertEquals("images", StringUtils.join(",", dockerImagesCommand.getDockerCommandWithArguments().get("docker-command")));
    }

    @Test
    public void testAllImages_2() {
        assertEquals(1, dockerImagesCommand.getDockerCommandWithArguments().size());
    }
}
