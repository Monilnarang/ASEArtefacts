package org.apache.skywalking.oap.server.library.datacarrier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SystemStubsExtension.class)
public class EnvUtilTest_Purified {

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

    @Test
    public void getInt_2() {
        assertEquals(234, EnvUtil.getLong("wrongInt", 234));
    }

    @Test
    public void getLong_1() {
        assertEquals(12345678901234567L, EnvUtil.getLong("myLong", 123L));
    }

    @Test
    public void getLong_2() {
        assertEquals(987654321987654321L, EnvUtil.getLong("wrongLong", 987654321987654321L));
    }
}
