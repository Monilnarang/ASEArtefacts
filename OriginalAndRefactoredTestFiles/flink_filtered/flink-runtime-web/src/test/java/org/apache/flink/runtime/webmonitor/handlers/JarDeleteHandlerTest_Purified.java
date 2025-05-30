package org.apache.flink.runtime.webmonitor.handlers;

import org.apache.flink.runtime.rest.handler.HandlerRequest;
import org.apache.flink.runtime.rest.handler.HandlerRequestException;
import org.apache.flink.runtime.rest.handler.RestHandlerException;
import org.apache.flink.runtime.rest.messages.EmptyRequestBody;
import org.apache.flink.runtime.webmonitor.RestfulGateway;
import org.apache.flink.runtime.webmonitor.TestingRestfulGateway;
import org.apache.flink.util.ExceptionUtils;
import org.apache.flink.util.concurrent.Executors;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpResponseStatus;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JarDeleteHandlerTest_Purified {

    private static final String TEST_JAR_NAME = "test.jar";

    private JarDeleteHandler jarDeleteHandler;

    private RestfulGateway restfulGateway;

    private Path jarDir;

    @BeforeEach
    void setUp(@TempDir File tempDir) throws Exception {
        jarDir = tempDir.toPath();
        restfulGateway = new TestingRestfulGateway.Builder().build();
        jarDeleteHandler = new JarDeleteHandler(() -> CompletableFuture.completedFuture(restfulGateway), Duration.ofSeconds(10), Collections.emptyMap(), new JarDeleteHeaders(), jarDir, Executors.directExecutor());
        Files.createFile(jarDir.resolve(TEST_JAR_NAME));
    }

    private static HandlerRequest<EmptyRequestBody> createRequest(final String jarFileName) throws HandlerRequestException {
        return HandlerRequest.resolveParametersAndCreate(EmptyRequestBody.getInstance(), new JarDeleteMessageParameters(), Collections.singletonMap(JarIdPathParameter.KEY, jarFileName), Collections.emptyMap(), Collections.emptyList());
    }

    private void makeJarDirReadOnly() {
        try {
            Files.setPosixFilePermissions(jarDir, new HashSet<>(Arrays.asList(PosixFilePermission.OTHERS_READ, PosixFilePermission.GROUP_READ, PosixFilePermission.OWNER_READ, PosixFilePermission.OTHERS_EXECUTE, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OWNER_EXECUTE)));
        } catch (final Exception e) {
            Assumptions.assumeTrue(e == null);
        }
    }

    @Test
    void testDeleteJarById_1() throws Exception {
        assertThat(Files.exists(jarDir.resolve(TEST_JAR_NAME))).isTrue();
    }

    @Test
    void testDeleteJarById_2() throws Exception {
        final HandlerRequest<EmptyRequestBody> request = createRequest(TEST_JAR_NAME);
        jarDeleteHandler.handleRequest(request, restfulGateway).get();
        assertThat(Files.exists(jarDir.resolve(TEST_JAR_NAME))).isFalse();
    }
}
