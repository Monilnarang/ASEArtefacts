package org.apache.hadoop.yarn.server.nodemanager.containermanager.linux.runtime.docker;

import org.apache.hadoop.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestDockerLoadCommand_Parameterized {

    private DockerLoadCommand dockerLoadCommand;

    private static final String LOCAL_IMAGE_NAME = "foo";

    @Before
    public void setup() {
        dockerLoadCommand = new DockerLoadCommand(LOCAL_IMAGE_NAME);
    }

    @Test
    public void testGetCommandWithArguments_3() {
        assertEquals(2, dockerLoadCommand.getDockerCommandWithArguments().size());
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetCommandWithArguments_1to2")
    public void testGetCommandWithArguments_1to2(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.join(param2, dockerLoadCommand.getDockerCommandWithArguments().get(param3)));
    }

    static public Stream<Arguments> Provider_testGetCommandWithArguments_1to2() {
        return Stream.of(arguments("load", ",", "docker-command"), arguments("foo", ",", "image"));
    }
}
