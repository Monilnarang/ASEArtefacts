package org.apache.flink.docs.configuration;

import org.apache.flink.annotation.Experimental;
import org.apache.flink.annotation.Internal;
import org.apache.flink.annotation.Public;
import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.annotation.docs.ConfigGroup;
import org.apache.flink.annotation.docs.ConfigGroups;
import org.apache.flink.annotation.docs.Documentation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.configuration.DescribedEnum;
import org.apache.flink.configuration.MemorySize;
import org.apache.flink.configuration.description.Formatter;
import org.apache.flink.configuration.description.HtmlFormatter;
import org.apache.flink.configuration.description.InlineElement;
import org.apache.flink.docs.configuration.data.TestCommonOptions;
import org.apache.flink.docs.util.ConfigurationOptionLocator;
import org.apache.flink.docs.util.OptionsClassLocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.apache.flink.configuration.description.TextElement.text;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SuppressWarnings("unused")
class ConfigOptionsDocGeneratorTest_Purified {

    public static class TestConfigGroup {

        public static ConfigOption<Integer> firstOption = ConfigOptions.key("first.option.a").intType().defaultValue(2).withDescription("This is example description for the first option.");

        public static ConfigOption<String> secondOption = ConfigOptions.key("second.option.a").stringType().noDefaultValue().withDescription("This is long example description for the second option.");
    }

    private enum TestEnum {

        VALUE_1, VALUE_2, VALUE_3
    }

    private enum DescribedTestEnum implements DescribedEnum {

        A(text("First letter of the alphabet")), B(text("Second letter of the alphabet"));

        private final InlineElement description;

        DescribedTestEnum(InlineElement description) {
            this.description = description;
        }

        @Override
        public InlineElement getDescription() {
            return description;
        }
    }

    public static class TypeTestConfigGroup {

        public static ConfigOption<TestEnum> enumOption = ConfigOptions.key("option.enum").enumType(TestEnum.class).defaultValue(TestEnum.VALUE_1).withDescription("Description");

        public static ConfigOption<List<TestEnum>> enumListOption = ConfigOptions.key("option.enum.list").enumType(TestEnum.class).asList().defaultValues(TestEnum.VALUE_1, TestEnum.VALUE_2).withDescription("Description");

        public static ConfigOption<DescribedTestEnum> describedEnum = ConfigOptions.key("option.enum.described").enumType(DescribedTestEnum.class).noDefaultValue().withDescription("Description");

        public static ConfigOption<MemorySize> memoryOption = ConfigOptions.key("option.memory").memoryType().defaultValue(new MemorySize(1024)).withDescription("Description");

        public static ConfigOption<Map<String, String>> mapOption = ConfigOptions.key("option.map").mapType().defaultValue(Collections.singletonMap("key1", "value1")).withDescription("Description");

        public static ConfigOption<List<Map<String, String>>> mapListOption = ConfigOptions.key("option.map.list").mapType().asList().defaultValues(Collections.singletonMap("key1", "value1"), Collections.singletonMap("key2", "value2")).withDescription("Description");

        public static ConfigOption<Duration> durationOption = ConfigOptions.key("option.duration").durationType().defaultValue(Duration.ofMinutes(1)).withDescription("Description");
    }

    @ConfigGroups(groups = { @ConfigGroup(name = "group1", keyPrefix = "a.b"), @ConfigGroup(name = "group2", keyPrefix = "a.b.c.d") })
    public static class TestConfigPrefix {

        public static ConfigOption<Integer> option1 = ConfigOptions.key("a.option").intType().defaultValue(2);

        public static ConfigOption<String> option2 = ConfigOptions.key("a.b.option").stringType().noDefaultValue();

        public static ConfigOption<Integer> option3 = ConfigOptions.key("a.b.c.option").intType().defaultValue(2);

        public static ConfigOption<Integer> option4 = ConfigOptions.key("a.b.c.e.option").intType().defaultValue(2);

        public static ConfigOption<String> option5 = ConfigOptions.key("a.c.b.option").stringType().noDefaultValue();

        public static ConfigOption<Integer> option6 = ConfigOptions.key("a.b.c.d.option").intType().defaultValue(2);
    }

    @ConfigGroups(groups = { @ConfigGroup(name = "firstGroup", keyPrefix = "first"), @ConfigGroup(name = "secondGroup", keyPrefix = "second") })
    public static class TestConfigMultipleSubGroup {

        public static ConfigOption<Integer> firstOption = ConfigOptions.key("first.option.a").intType().defaultValue(2).withDescription("This is example description for the first option.");

        public static ConfigOption<String> secondOption = ConfigOptions.key("second.option.a").stringType().noDefaultValue().withDescription("This is long example description for the second option.");

        public static ConfigOption<Integer> thirdOption = ConfigOptions.key("third.option.a").intType().defaultValue(2).withDescription("This is example description for the third option.");

        public static ConfigOption<String> fourthOption = ConfigOptions.key("fourth.option.a").stringType().noDefaultValue().withDescription("This is long example description for the fourth option.");
    }

    public static class TestConfigGroupWithOverriddenDefault {

        @Documentation.OverrideDefault("default_1")
        public static ConfigOption<Integer> firstOption = ConfigOptions.key("first.option.a").intType().defaultValue(2).withDescription("This is example description for the first option.");

        @Documentation.OverrideDefault("default_2")
        public static ConfigOption<String> secondOption = ConfigOptions.key("second.option.a").stringType().noDefaultValue().withDescription("This is long example description for the second option.");
    }

    public static class TestConfigGroupWithExclusion {

        public static ConfigOption<Integer> firstOption = ConfigOptions.key("first.option.a").intType().defaultValue(2).withDescription("This is example description for the first option.");

        @Documentation.ExcludeFromDocumentation
        public static ConfigOption<String> excludedOption = ConfigOptions.key("excluded.option.a").stringType().noDefaultValue().withDescription("This should not be documented.");
    }

    private enum TestEnumWithExclusion {

        VALUE_1, @Documentation.ExcludeFromDocumentation
        VALUE_2, VALUE_3
    }

    private enum DescribedTestEnumWithExclusion implements DescribedEnum {

        A(text("First letter of the alphabet")), B(text("Second letter of the alphabet")), @Documentation.ExcludeFromDocumentation
        C(text("Third letter of the alphabet"));

        private final InlineElement description;

        DescribedTestEnumWithExclusion(InlineElement description) {
            this.description = description;
        }

        @Override
        public InlineElement getDescription() {
            return description;
        }
    }

    public static class TestConfigGroupWithEnumConstantExclusion {

        public static ConfigOption<TestEnumWithExclusion> enumWithExclusion = ConfigOptions.key("exclude.enum").enumType(TestEnumWithExclusion.class).defaultValue(TestEnumWithExclusion.VALUE_1).withDescription("Description");

        public static ConfigOption<List<TestEnumWithExclusion>> enumListWithExclusion = ConfigOptions.key("exclude.enum.list").enumType(TestEnumWithExclusion.class).asList().defaultValues(TestEnumWithExclusion.VALUE_1, TestEnumWithExclusion.VALUE_3).withDescription("Description");

        public static ConfigOption<DescribedTestEnumWithExclusion> enumDescribedWithExclusion = ConfigOptions.key("exclude.enum.desc").enumType(DescribedTestEnumWithExclusion.class).noDefaultValue().withDescription("Description");
    }

    @ConfigGroups(groups = { @ConfigGroup(name = "firstGroup", keyPrefix = "first") })
    public static class EmptyConfigOptions {
    }

    @Public
    static class PublicOptions {
    }

    @PublicEvolving
    static class PublicEvolvingOptions {
    }

    @Experimental
    static class ExperimentalOptions {
    }

    @Internal
    static class InternalOptions {
    }

    static class OptionsWithoutAnnotation {
    }

    static String getProjectRootDir() {
        final String dirFromProperty = System.getProperty("rootDir");
        if (dirFromProperty != null) {
            return dirFromProperty;
        }
        final String currentDir = System.getProperty("user.dir");
        return new File(currentDir).getParent();
    }

    @Test
    void testSnakeCaseConversion_1() {
        assertThat(ConfigOptionsDocGenerator.toSnakeCase("RocksOptions")).isEqualTo("rocks_options");
    }

    @Test
    void testSnakeCaseConversion_2() {
        assertThat(ConfigOptionsDocGenerator.toSnakeCase("RocksDBOptions")).isEqualTo("rocksdb_options");
    }

    @Test
    void testSnakeCaseConversion_3() {
        assertThat(ConfigOptionsDocGenerator.toSnakeCase("DBOptions")).isEqualTo("db_options");
    }
}
