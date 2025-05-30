package org.apache.druid.guice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.contrib.java.lang.system.RestoreSystemProperties;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class JsonConfiguratorTest_Purified {

    private static final String PROP_PREFIX = "test.property.prefix.";

    private final ObjectMapper jsonMapper = new DefaultObjectMapper();

    private final Properties properties = new Properties();

    @Rule
    public final RestoreSystemProperties restoreSystemProperties = new RestoreSystemProperties();

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Before
    public void setUp() {
        jsonMapper.registerSubtypes(MappableObject.class);
    }

    final Validator validator = new Validator() {

        @Override
        public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
            return ImmutableSet.of();
        }

        @Override
        public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
            return ImmutableSet.of();
        }

        @Override
        public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
            return ImmutableSet.of();
        }

        @Override
        public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
            return null;
        }

        @Override
        public <T> T unwrap(Class<T> type) {
            return null;
        }

        @Override
        public ExecutableValidator forExecutables() {
            return null;
        }
    };

    @Test
    public void testTest_1() {
        Assert.assertEquals(new MappableObject("p1", ImmutableList.of("p2"), "p2"), new MappableObject("p1", ImmutableList.of("p2"), "p2"));
    }

    @Test
    public void testTest_2() {
        Assert.assertEquals(new MappableObject("p1", null, null), new MappableObject("p1", ImmutableList.of(), null));
    }
}
