package io.dropwizard.jersey.validation;

import io.dropwizard.jersey.AbstractJerseyTest;
import io.dropwizard.jersey.DropwizardResourceConfig;
import io.dropwizard.jersey.errors.LoggingExceptionMapper;
import io.dropwizard.jersey.jackson.JacksonMessageBodyProviderTest.Example;
import io.dropwizard.jersey.jackson.JacksonMessageBodyProviderTest.ListExample;
import io.dropwizard.jersey.jackson.JacksonMessageBodyProviderTest.PartialExample;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

class ConstraintViolationExceptionMapperTest_Purified extends AbstractJerseyTest {

    private static class LoggingExceptionMapperBinder extends AbstractBinder {

        protected void configure() {
            this.bind(new LoggingExceptionMapper<Throwable>() {
            }).to(ExceptionMapper.class);
        }
    }

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    @Override
    protected Application configure() {
        return DropwizardResourceConfig.forTesting().packages("io.dropwizard.jersey.validation").register(new ValidatingResource2()).register(new LoggingExceptionMapperBinder()).register(new HibernateValidationBinder(Validators.newValidator()));
    }

    @BeforeAll
    static void init() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @AfterAll
    static void shutdown() {
        Locale.setDefault(DEFAULT_LOCALE);
    }

    @Test
    void functionWithSameNameReturnDifferentErrors_1() {
        final Response response = target("/valid/head").request().get();
        String ret = "{\"errors\":[\"header cheese must not be empty\"]}";
        assertThat(response.readEntity(String.class)).isEqualTo(ret);
    }

    @Test
    void functionWithSameNameReturnDifferentErrors_2() {
        final Response response2 = target("/valid/headCopy").request().get();
        assertThat(response2.readEntity(String.class)).isEqualTo("{\"code\":400,\"message\":\"query param cheese is not a number.\"}");
    }

    @Test
    void minCustomMessage_1_testMerged_1() {
        final Response response = target("/valid/messageValidation").queryParam("length", 1).request().get();
        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(String.class)).containsOnlyOnce("query param length The value 1 is less then 2");
    }

    @Test
    void minCustomMessage_3_testMerged_2() {
        final Response response2 = target("/valid/messageValidation").queryParam("length", 0).request().get();
        assertThat(response2.getStatus()).isEqualTo(400);
        assertThat(response2.readEntity(String.class)).containsOnlyOnce("query param length The value 0 is less then 2");
    }
}
