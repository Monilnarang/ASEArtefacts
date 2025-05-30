package io.dropwizard.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DiscoverableSubtypeResolverTest_Parameterized {

    private final ObjectMapper mapper = new ObjectMapper();

    private final DiscoverableSubtypeResolver resolver = new DiscoverableSubtypeResolver(ExampleTag.class);

    @BeforeEach
    void setUp() throws Exception {
        mapper.setSubtypeResolver(resolver);
    }

    @ParameterizedTest
    @MethodSource("Provider_discoversSubtypes_1to2")
    void discoversSubtypes_1to2(String param1) throws Exception {
        assertThat(mapper.readValue(param1, ExampleSPI.class)).isInstanceOf(ImplA.class);
    }

    static public Stream<Arguments> Provider_discoversSubtypes_1to2() {
        return Stream.of(arguments("{\"type\":\"a\"}"), arguments("{\"type\":\"b\"}"));
    }
}
