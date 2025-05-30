package org.apache.skywalking.oap.server.library.datacarrier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(SystemStubsExtension.class)
public class EnvUtilTest_Parameterized {

    @SystemStub
    private final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @BeforeEach
    public void before() {
        environmentVariables.set("myInt", "123");
        environmentVariables.set("wrongInt", "wrong123");
        environmentVariables.set("myLong", "12345678901234567");
        environmentVariables.set("wrongLong", "wrong123");
    }

    @Test
    public void getInt_1() {
        assertEquals(123, EnvUtil.getInt("myInt", 234));
    }

    @ParameterizedTest
    @MethodSource("Provider_getInt_1to2_2")
    public void getInt_1to2_2(int param1, String param2, int param3) {
        assertEquals(param1, EnvUtil.getLong(param2, param3));
    }

    static public Stream<Arguments> Provider_getInt_1to2_2() {
        return Stream.of(arguments(234, "wrongInt", 234), arguments(12345678901234567L, "myLong", 123L), arguments(987654321987654321L, "wrongLong", 987654321987654321L));
    }
}
