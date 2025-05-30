package org.apache.druid.sql.calcite.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.druid.error.DruidException;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.matchers.ThrowableMessageMatcher;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SqlResultsTest_Purified extends InitializedNullHandlingTest {

    private static final SqlResults.Context DEFAULT_CONTEXT = new SqlResults.Context(DateTimeZone.UTC, false);

    private ObjectMapper jsonMapper;

    @Before
    public void setUp() {
        jsonMapper = TestHelper.JSON_MAPPER;
    }

    private void assertCoerce(Object expected, Object toCoerce, SqlTypeName typeName) {
        Assert.assertEquals(StringUtils.format("Coerce [%s] to [%s]", toCoerce, typeName), expected, SqlResults.coerce(jsonMapper, DEFAULT_CONTEXT, toCoerce, typeName, "fieldName"));
    }

    private void assertCannotCoerce(Object toCoerce, SqlTypeName typeName) {
        final DruidException e = Assert.assertThrows(StringUtils.format("Coerce [%s] to [%s]", toCoerce, typeName), DruidException.class, () -> SqlResults.coerce(jsonMapper, DEFAULT_CONTEXT, toCoerce, typeName, ""));
        MatcherAssert.assertThat(e, ThrowableMessageMatcher.hasMessage(CoreMatchers.containsString("Cannot coerce")));
    }

    private void assertCoerceArrayToList(Object expected, Object toCoerce) {
        Object coerced = SqlResults.coerce(jsonMapper, DEFAULT_CONTEXT, toCoerce, SqlTypeName.ARRAY, "");
        Assert.assertEquals(expected, coerced);
    }

    @Test
    public void testCoerceBoolean_1() {
        assertCoerce(false, false, SqlTypeName.BOOLEAN);
    }

    @Test
    public void testCoerceBoolean_2() {
        assertCoerce(false, "xyz", SqlTypeName.BOOLEAN);
    }

    @Test
    public void testCoerceBoolean_3() {
        assertCoerce(false, 0, SqlTypeName.BOOLEAN);
    }

    @Test
    public void testCoerceBoolean_4() {
        assertCoerce(false, "false", SqlTypeName.BOOLEAN);
    }

    @Test
    public void testCoerceBoolean_5() {
        assertCoerce(true, true, SqlTypeName.BOOLEAN);
    }

    @Test
    public void testCoerceBoolean_6() {
        assertCoerce(true, "true", SqlTypeName.BOOLEAN);
    }

    @Test
    public void testCoerceBoolean_7() {
        assertCoerce(true, 1, SqlTypeName.BOOLEAN);
    }

    @Test
    public void testCoerceBoolean_8() {
        assertCoerce(true, 1.0, SqlTypeName.BOOLEAN);
    }

    @Test
    public void testCoerceBoolean_9() {
        assertCoerce(null, null, SqlTypeName.BOOLEAN);
    }

    @Test
    public void testCoerceBoolean_10() {
        assertCannotCoerce(Collections.emptyList(), SqlTypeName.BOOLEAN);
    }

    @Test
    public void testCoerceInteger_1() {
        assertCoerce(0, 0, SqlTypeName.INTEGER);
    }

    @Test
    public void testCoerceInteger_2() {
        assertCoerce(1, 1L, SqlTypeName.INTEGER);
    }

    @Test
    public void testCoerceInteger_3() {
        assertCoerce(1, 1f, SqlTypeName.INTEGER);
    }

    @Test
    public void testCoerceInteger_4() {
        assertCoerce(1, "1", SqlTypeName.INTEGER);
    }

    @Test
    public void testCoerceInteger_5() {
        assertCoerce(null, "1.1", SqlTypeName.INTEGER);
    }

    @Test
    public void testCoerceInteger_6() {
        assertCoerce(null, "xyz", SqlTypeName.INTEGER);
    }

    @Test
    public void testCoerceInteger_7() {
        assertCoerce(null, null, SqlTypeName.INTEGER);
    }

    @Test
    public void testCoerceInteger_8() {
        assertCannotCoerce(Collections.emptyList(), SqlTypeName.INTEGER);
    }

    @Test
    public void testCoerceInteger_9() {
        assertCannotCoerce(false, SqlTypeName.INTEGER);
    }

    @Test
    public void testCoerceFloat_1() {
        assertCoerce(0f, 0, SqlTypeName.FLOAT);
    }

    @Test
    public void testCoerceFloat_2() {
        assertCoerce(1f, 1L, SqlTypeName.FLOAT);
    }

    @Test
    public void testCoerceFloat_3() {
        assertCoerce(1f, 1f, SqlTypeName.FLOAT);
    }

    @Test
    public void testCoerceFloat_4() {
        assertCoerce(1.1f, "1.1", SqlTypeName.FLOAT);
    }

    @Test
    public void testCoerceFloat_5() {
        assertCoerce(null, "xyz", SqlTypeName.FLOAT);
    }

    @Test
    public void testCoerceFloat_6() {
        assertCoerce(null, null, SqlTypeName.FLOAT);
    }

    @Test
    public void testCoerceFloat_7() {
        assertCannotCoerce(Collections.emptyList(), SqlTypeName.FLOAT);
    }

    @Test
    public void testCoerceFloat_8() {
        assertCannotCoerce(false, SqlTypeName.FLOAT);
    }

    @Test
    public void testCoerceDouble_1() {
        assertCoerce(0d, 0, SqlTypeName.DOUBLE);
    }

    @Test
    public void testCoerceDouble_2() {
        assertCoerce(1d, 1L, SqlTypeName.DOUBLE);
    }

    @Test
    public void testCoerceDouble_3() {
        assertCoerce(1d, 1f, SqlTypeName.DOUBLE);
    }

    @Test
    public void testCoerceDouble_4() {
        assertCoerce(1.1d, "1.1", SqlTypeName.DOUBLE);
    }

    @Test
    public void testCoerceDouble_5() {
        assertCoerce(null, "xyz", SqlTypeName.DOUBLE);
    }

    @Test
    public void testCoerceDouble_6() {
        assertCoerce(null, null, SqlTypeName.DOUBLE);
    }

    @Test
    public void testCoerceDouble_7() {
        assertCannotCoerce(Collections.emptyList(), SqlTypeName.DOUBLE);
    }

    @Test
    public void testCoerceDouble_8() {
        assertCannotCoerce(false, SqlTypeName.DOUBLE);
    }
}
