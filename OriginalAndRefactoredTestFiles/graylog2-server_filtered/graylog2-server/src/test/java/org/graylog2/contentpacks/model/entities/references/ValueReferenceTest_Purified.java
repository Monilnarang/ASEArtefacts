package org.graylog2.contentpacks.model.entities.references;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.graylog2.security.encryption.EncryptedValue;
import org.graylog2.security.encryption.EncryptedValueService;
import org.graylog2.shared.bindings.providers.ObjectMapperProvider;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValueReferenceTest_Purified {

    private final ObjectMapper objectMapper = new ObjectMapperProvider().get();

    public void assertJsonEqualsNonStrict(String json1, String json2) {
        try {
            JSONAssert.assertEquals(json1, json2, false);
        } catch (JSONException jse) {
            throw new IllegalArgumentException(jse.getMessage());
        }
    }

    enum TestEnum {

        A, B, C
    }

    @Test
    public void asEncryptedValue_1() {
        assertThat(ValueReference.of(EncryptedValue.createUnset())).isNull();
    }

    @Test
    public void asEncryptedValue_2() {
        final EncryptedValue encryptedValue = new EncryptedValueService(UUID.randomUUID().toString()).encrypt("secret");
        assertThat(ValueReference.of(encryptedValue).asString()).startsWith("<Encrypted value was replaced");
    }

    @Test
    public void serializeBoolean_1() throws IOException {
        assertJsonEqualsNonStrict(objectMapper.writeValueAsString(ValueReference.of(true)), "{\"@type\":\"boolean\",\"@value\":true}");
    }

    @Test
    public void serializeBoolean_2() throws IOException {
        assertJsonEqualsNonStrict(objectMapper.writeValueAsString(ValueReference.of(false)), "{\"@type\":\"boolean\",\"@value\":false}");
    }

    @Test
    public void deserializeBoolean_1() throws IOException {
        assertThat(objectMapper.readValue("{\"@type\":\"boolean\",\"@value\":true}", ValueReference.class)).isEqualTo(ValueReference.of(true));
    }

    @Test
    public void deserializeBoolean_2() throws IOException {
        assertThat(objectMapper.readValue("{\"@type\":\"boolean\",\"@value\":false}", ValueReference.class)).isEqualTo(ValueReference.of(false));
    }

    @Test
    public void serializeEnum_1() throws IOException {
        assertJsonEqualsNonStrict(objectMapper.writeValueAsString(ValueReference.of(TestEnum.A)), "{\"@type\":\"string\",\"@value\":\"A\"}");
    }

    @Test
    public void serializeEnum_2() throws IOException {
        assertJsonEqualsNonStrict(objectMapper.writeValueAsString(ValueReference.of(TestEnum.B)), "{\"@type\":\"string\",\"@value\":\"B\"}");
    }

    @Test
    public void deserializeEnum_1() throws IOException {
        assertThat(objectMapper.readValue("{\"@type\":\"string\",\"@value\":\"A\"}", ValueReference.class)).isEqualTo(ValueReference.of(TestEnum.A));
    }

    @Test
    public void deserializeEnum_2() throws IOException {
        assertThat(objectMapper.readValue("{\"@type\":\"string\",\"@value\":\"B\"}", ValueReference.class)).isEqualTo(ValueReference.of(TestEnum.B));
    }

    @Test
    public void serializeFloat_1() throws IOException {
        assertJsonEqualsNonStrict(objectMapper.writeValueAsString(ValueReference.of(1.0f)), "{\"@type\":\"float\",\"@value\":1.0}");
    }

    @Test
    public void serializeFloat_2() throws IOException {
        assertJsonEqualsNonStrict(objectMapper.writeValueAsString(ValueReference.of(42.4f)), "{\"@type\":\"float\",\"@value\":42.4}");
    }

    @Test
    @Ignore("FIXME: Jackson automatically deserializes floating point numbers as double")
    public void deserializeFloat_1() throws IOException {
        assertThat(objectMapper.readValue("{\"@type\":\"float\",\"@value\":1.0}", ValueReference.class)).isEqualTo(ValueReference.of(1.0f));
    }

    @Test
    @Ignore("FIXME: Jackson automatically deserializes floating point numbers as double")
    public void deserializeFloat_2() throws IOException {
        assertThat(objectMapper.readValue("{\"@type\":\"float\",\"@value\":42.4}", ValueReference.class)).isEqualTo(ValueReference.of(42.4f));
    }

    @Test
    public void serializeInteger_1() throws IOException {
        assertJsonEqualsNonStrict(objectMapper.writeValueAsString(ValueReference.of(1)), "{\"@type\":\"integer\",\"@value\":1}");
    }

    @Test
    public void serializeInteger_2() throws IOException {
        assertJsonEqualsNonStrict(objectMapper.writeValueAsString(ValueReference.of(42)), "{\"@type\":\"integer\",\"@value\":42}");
    }

    @Test
    public void deserializeInteger_1() throws IOException {
        assertThat(objectMapper.readValue("{\"@type\":\"integer\",\"@value\":1}", ValueReference.class)).isEqualTo(ValueReference.of(1));
    }

    @Test
    public void deserializeInteger_2() throws IOException {
        assertThat(objectMapper.readValue("{\"@type\":\"integer\",\"@value\":42}", ValueReference.class)).isEqualTo(ValueReference.of(42));
    }

    @Test
    public void serializeString_1() throws IOException {
        assertJsonEqualsNonStrict(objectMapper.writeValueAsString(ValueReference.of("")), "{\"@type\":\"string\",\"@value\":\"\"}");
    }

    @Test
    public void serializeString_2() throws IOException {
        assertJsonEqualsNonStrict(objectMapper.writeValueAsString(ValueReference.of("Test")), "{\"@type\":\"string\",\"@value\":\"Test\"}");
    }

    @Test
    public void deserializeString_1() throws IOException {
        assertThat(objectMapper.readValue("{\"@type\":\"string\",\"@value\":\"\"}", ValueReference.class)).isEqualTo(ValueReference.of(""));
    }

    @Test
    public void deserializeString_2() throws IOException {
        assertThat(objectMapper.readValue("{\"@type\":\"string\",\"@value\":\"Test\"}", ValueReference.class)).isEqualTo(ValueReference.of("Test"));
    }

    @Test
    public void testOfNullable_1() {
        assertThat(ValueReference.ofNullable("test")).isNotNull();
    }

    @Test
    public void testOfNullable_2() {
        assertThat(ValueReference.ofNullable((String) null)).isNull();
    }

    @Test
    public void testOfNullable_3() {
        assertThat(ValueReference.ofNullable(TestEnum.A)).isNotNull();
    }

    @Test
    public void testOfNullable_4() {
        assertThat(ValueReference.ofNullable((TestEnum) null)).isNull();
    }
}
