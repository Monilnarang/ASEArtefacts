package org.apache.hadoop.yarn.server.nodemanager.containermanager.linux.runtime.docker;

import org.apache.hadoop.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestDockerStartCommand_Parameterized {

    private DockerStartCommand dockerStartCommand;

    private static final String CONTAINER_NAME = "foo";

    @Before
    public void setUp() {
        dockerStartCommand = new DockerStartCommand(CONTAINER_NAME);
    }

    @Test
    public void testGetCommandWithArguments_3() {
        assertEquals(2, dockerStartCommand.getDockerCommandWithArguments().size());
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetCommandWithArguments_1to2")
    public void testGetCommandWithArguments_1to2(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.join(param2, dockerStartCommand.getDockerCommandWithArguments().get(param3)));
    }

    static public Stream<Arguments> Provider_testGetCommandWithArguments_1to2() {
        return Stream.of(arguments("start", ",", "docker-command"), arguments("foo", ",", "name"));
    }
}
