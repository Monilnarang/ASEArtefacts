package org.apache.hadoop.yarn.server.nodemanager.containermanager.linux.runtime.docker;

import static org.junit.Assert.assertEquals;
import org.apache.hadoop.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestDockerRmCommand_Parameterized {

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
    public void testGetCommandWithArguments_3() {
        assertEquals(2, dockerRmCommand.getDockerCommandWithArguments().size());
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
    public void testGetCommandWithEmptyCgroup_3() {
        assertEquals(2, dockerRmCommandWithEmptyCgroupArg.getDockerCommandWithArguments().size());
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetCommandWithArguments_1to2")
    public void testGetCommandWithArguments_1to2(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.join(param2, dockerRmCommand.getDockerCommandWithArguments().get(param3)));
    }

    static public Stream<Arguments> Provider_testGetCommandWithArguments_1to2() {
        return Stream.of(arguments("rm", ",", "docker-command"), arguments("foo", ",", "name"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetCommandWithCgroup_1to2")
    public void testGetCommandWithCgroup_1to2(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.join(param2, dockerRmCommandWithCgroupArg.getDockerCommandWithArguments().get(param3)));
    }

    static public Stream<Arguments> Provider_testGetCommandWithCgroup_1to2() {
        return Stream.of(arguments("rm", ",", "docker-command"), arguments("foo", ",", "name"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetCommandWithEmptyCgroup_1to2")
    public void testGetCommandWithEmptyCgroup_1to2(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.join(param2, dockerRmCommandWithEmptyCgroupArg.getDockerCommandWithArguments().get(param3)));
    }

    static public Stream<Arguments> Provider_testGetCommandWithEmptyCgroup_1to2() {
        return Stream.of(arguments("rm", ",", "docker-command"), arguments("foo", ",", "name"));
    }
}
