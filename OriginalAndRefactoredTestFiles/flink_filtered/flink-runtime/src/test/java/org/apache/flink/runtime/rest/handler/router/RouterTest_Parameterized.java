package org.apache.flink.runtime.rest.handler.router;

import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpMethod.GET;
import static org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RouterTest_Parameterized {

    private Router<String> router;

    @BeforeEach
    public void setUp() {
        router = StringRouter.create();
    }

    private static final class StringRouter {

        private StringRouter() {
        }

        static Router<String> create() {
            return new Router<String>().addGet("/articles", "index").addGet("/articles/new", "new").addGet("/articles/overview", "overview").addGet("/articles/overview/detailed", "detailed").addGet("/articles/:id", "show").addGet("/articles/:id/:format", "show").addPost("/articles", "post").addPatch("/articles/:id", "patch").addDelete("/articles/:id", "delete").addAny("/anyMethod", "anyMethod").addGet("/download/:*", "download").notFound("404");
        }
    }

    private interface Action {
    }

    private class Index implements Action {
    }

    private class Show implements Action {
    }

    @ParameterizedTest
    @MethodSource("Provider_testIgnoreSlashesAtBothEnds_1to7")
    void testIgnoreSlashesAtBothEnds_1to7(String param1, String param2) {
        assertThat(router.route(GET, param2).target()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testIgnoreSlashesAtBothEnds_1to7() {
        return Stream.of(arguments("index", "articles"), arguments("index", "/articles"), arguments("index", "//articles"), arguments("index", "articles/"), arguments("index", "articles//"), arguments("index", "/articles/"), arguments("index", "//articles//"));
    }
}
