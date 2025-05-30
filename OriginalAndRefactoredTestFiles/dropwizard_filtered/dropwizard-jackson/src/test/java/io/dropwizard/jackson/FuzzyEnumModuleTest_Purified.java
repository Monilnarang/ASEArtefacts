package io.dropwizard.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.ClientInfoStatus;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FuzzyEnumModuleTest_Purified {

    private final ObjectMapper mapper = new ObjectMapper();

    private enum EnumWithLowercase {

        lower_case_enum, mixedCaseEnum
    }

    private enum EnumWithCreator {

        TEST;

        @JsonCreator
        public static EnumWithCreator fromString(String value) {
            return EnumWithCreator.TEST;
        }
    }

    private enum CurrencyCode {

        USD("United States dollar"),
        AUD("a_u_d"),
        CAD("c-a-d"),
        BLA("b.l.a"),
        EUR("Euro"),
        GBP("Pound sterling");

        private final String description;

        CurrencyCode(String name) {
            this.description = name;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    enum EnumWithPropertyAnno {

        @JsonProperty("a a")
        A, @JsonProperty("b b")
        B {

            @Override
            public String toString() {
                return "bb";
            }
        }
        , @JsonProperty("forgot password")
        FORGOT_PASSWORD, @JsonEnumDefaultValue
        DEFAULT
    }

    @BeforeEach
    void setUp() throws Exception {
        mapper.registerModule(new FuzzyEnumModule());
    }

    @Test
    void readsEnumsUsingToStringWithDeserializationFeatureOff_1() throws Exception {
        assertThat(mapper.readValue("\"Pound sterling\"", CurrencyCode.class)).isEqualTo(CurrencyCode.GBP);
    }

    @Test
    void readsEnumsUsingToStringWithDeserializationFeatureOff_2() throws Exception {
        assertThat(mapper.readValue("\"a_u_d\"", CurrencyCode.class)).isEqualTo(CurrencyCode.AUD);
    }

    @Test
    void readsEnumsUsingToStringWithDeserializationFeatureOff_3() throws Exception {
        assertThat(mapper.readValue("\"c-a-d\"", CurrencyCode.class)).isEqualTo(CurrencyCode.CAD);
    }

    @Test
    void readsEnumsUsingToStringWithDeserializationFeatureOff_4() throws Exception {
        assertThat(mapper.readValue("\"b.l.a\"", CurrencyCode.class)).isEqualTo(CurrencyCode.BLA);
    }
}
