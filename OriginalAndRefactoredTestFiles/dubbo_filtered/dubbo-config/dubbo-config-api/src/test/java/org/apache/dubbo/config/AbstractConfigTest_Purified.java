package org.apache.dubbo.config;

import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.api.Greeting;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.context.ConfigMode;
import org.apache.dubbo.config.support.Nested;
import org.apache.dubbo.config.support.Parameter;
import org.apache.dubbo.config.utils.ConfigValidationUtils;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.model.ScopeModel;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class AbstractConfigTest_Purified {

    @BeforeEach
    public void beforeEach() {
        DubboBootstrap.reset();
    }

    @AfterEach
    public void afterEach() {
        SysProps.clear();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.ANNOTATION_TYPE })
    public @interface ConfigField {

        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
    public @interface Config {

        Class<?> interfaceClass() default void.class;

        String interfaceName() default "";

        String[] filter() default {};

        String[] listener() default {};

        String[] parameters() default {};

        ConfigField[] configFields() default {};

        ConfigField configField() default @ConfigField;
    }

    private static class OverrideConfig extends AbstractConfig {

        public String address;

        public String protocol;

        public String exclude;

        public String key;

        public String key2;

        public String escape;

        public String notConflictKey;

        public String notConflictKey2;

        protected Map<String, String> parameters;

        public OverrideConfig() {
        }

        public OverrideConfig(ScopeModel scopeModel) {
            super(scopeModel);
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        @Parameter(excluded = true)
        public String getExclude() {
            return exclude;
        }

        public void setExclude(String exclude) {
            this.exclude = exclude;
        }

        @Parameter(key = "key1")
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Parameter(key = "mykey")
        public String getKey2() {
            return key2;
        }

        public void setKey2(String key2) {
            this.key2 = key2;
        }

        @Parameter(escaped = true)
        public String getEscape() {
            return escape;
        }

        public void setEscape(String escape) {
            this.escape = escape;
        }

        public String getNotConflictKey() {
            return notConflictKey;
        }

        public void setNotConflictKey(String notConflictKey) {
            this.notConflictKey = notConflictKey;
        }

        public String getNotConflictKey2() {
            return notConflictKey2;
        }

        public void setNotConflictKey2(String notConflictKey2) {
            this.notConflictKey2 = notConflictKey2;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }
    }

    private static class ParameterConfig {

        private int number;

        private String name;

        private int age;

        private String secret;

        private String detailAddress;

        ParameterConfig() {
        }

        ParameterConfig(int number, String name, int age, String secret) {
            this(number, name, age, secret, "");
        }

        ParameterConfig(int number, String name, int age, String secret, String detailAddress) {
            this.number = number;
            this.name = name;
            this.age = age;
            this.secret = secret;
            this.detailAddress = detailAddress;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        @Parameter(key = "num", append = true)
        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        @Parameter(key = "naming", append = true, escaped = true, required = true)
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Parameter(excluded = true)
        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public Map getParameters() {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key.1", "one");
            map.put("key.2", "two");
            return map;
        }
    }

    private static class AnnotationConfig extends AbstractConfig {

        private Class interfaceClass;

        private String filter;

        private String listener;

        private Map<String, String> parameters;

        private String[] configFields;

        public Class getInterface() {
            return interfaceClass;
        }

        public void setInterface(Class interfaceName) {
            this.interfaceClass = interfaceName;
        }

        public String getFilter() {
            return filter;
        }

        public void setFilter(String filter) {
            this.filter = filter;
        }

        public String getListener() {
            return listener;
        }

        public void setListener(String listener) {
            this.listener = listener;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        public String[] getConfigFields() {
            return configFields;
        }

        public void setConfigFields(String[] configFields) {
            this.configFields = configFields;
        }
    }

    private static class OuterConfig extends AbstractConfig {

        private Integer a1;

        @Nested
        private InnerConfig b;

        OuterConfig() {
        }

        OuterConfig(String id) {
            this.setId(id);
        }

        public Integer getA1() {
            return a1;
        }

        public void setA1(Integer a1) {
            this.a1 = a1;
        }

        public InnerConfig getB() {
            return b;
        }

        public void setB(InnerConfig b) {
            this.b = b;
        }
    }

    public static class InnerConfig {

        private Integer b1;

        private Integer b2;

        public Integer getB1() {
            return b1;
        }

        public void setB1(Integer b1) {
            this.b1 = b1;
        }

        public Integer getB2() {
            return b2;
        }

        public void setB2(Integer b2) {
            this.b2 = b2;
        }
    }

    @Test
    void testEquals_1_testMerged_1() {
        ApplicationConfig application1 = new ApplicationConfig();
        ApplicationConfig application2 = new ApplicationConfig();
        application1.setName("app1");
        application2.setName("app2");
        Assertions.assertNotEquals(application1, application2);
        application1.setName("sameName");
        application2.setName("sameName");
        Assertions.assertEquals(application1, application2);
    }

    @Test
    void testEquals_3() {
        ProtocolConfig protocol1 = new ProtocolConfig();
        protocol1.setName("dubbo");
        protocol1.setPort(1234);
        ProtocolConfig protocol2 = new ProtocolConfig();
        protocol2.setName("dubbo");
        protocol2.setPort(1235);
        Assertions.assertNotEquals(protocol1, protocol2);
    }
}
