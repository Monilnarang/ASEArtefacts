package org.activiti.core.el.juel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import jakarta.el.ELException;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.test.TestCase;
import org.junit.jupiter.api.Test;

public class ObjectValueExpressionTest_Purified extends TestCase {

    private TypeConverter converter = TypeConverter.DEFAULT;

    @Test
    public void testEqualsObject_1() {
        assertTrue(new ObjectValueExpression(converter, "foo", Object.class).equals(new ObjectValueExpression(converter, "foo", Object.class)));
    }

    @Test
    public void testEqualsObject_2() {
        assertTrue(new ObjectValueExpression(converter, new String("foo"), Object.class).equals(new ObjectValueExpression(converter, "foo", Object.class)));
    }

    @Test
    public void testEqualsObject_3() {
        assertFalse(new ObjectValueExpression(converter, "foo", Object.class).equals(new ObjectValueExpression(converter, "bar", Object.class)));
    }
}
