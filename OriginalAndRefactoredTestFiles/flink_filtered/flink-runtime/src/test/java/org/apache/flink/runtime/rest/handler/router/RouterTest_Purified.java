package org.apache.flink.runtime.rest.handler.router;

import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpMethod.GET;
import static org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;

class RouterTest_Purified {

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

    @Test
    void testIgnoreSlashesAtBothEnds_1() {
        assertThat(router.route(GET, "articles").target()).isEqualTo("index");
    }

    @Test
    void testIgnoreSlashesAtBothEnds_2() {
        assertThat(router.route(GET, "/articles").target()).isEqualTo("index");
    }

    @Test
    void testIgnoreSlashesAtBothEnds_3() {
        assertThat(router.route(GET, "//articles").target()).isEqualTo("index");
    }

    @Test
    void testIgnoreSlashesAtBothEnds_4() {
        assertThat(router.route(GET, "articles/").target()).isEqualTo("index");
    }

    @Test
    void testIgnoreSlashesAtBothEnds_5() {
        assertThat(router.route(GET, "articles//").target()).isEqualTo("index");
    }

    @Test
    void testIgnoreSlashesAtBothEnds_6() {
        assertThat(router.route(GET, "/articles/").target()).isEqualTo("index");
    }

    @Test
    void testIgnoreSlashesAtBothEnds_7() {
        assertThat(router.route(GET, "//articles//").target()).isEqualTo("index");
    }
}
