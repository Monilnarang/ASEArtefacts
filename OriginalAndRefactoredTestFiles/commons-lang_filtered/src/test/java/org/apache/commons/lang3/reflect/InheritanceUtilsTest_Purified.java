package org.apache.commons.lang3.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.reflect.testbed.AnotherChild;
import org.apache.commons.lang3.reflect.testbed.AnotherParent;
import org.apache.commons.lang3.reflect.testbed.Grandchild;
import org.junit.jupiter.api.Test;

public class InheritanceUtilsTest_Purified extends AbstractLangTest {

    @Test
    public void testDistanceGreaterThanZero_1() {
        assertEquals(1, InheritanceUtils.distance(AnotherChild.class, AnotherParent.class));
    }

    @Test
    public void testDistanceGreaterThanZero_2() {
        assertEquals(1, InheritanceUtils.distance(Grandchild.class, AnotherChild.class));
    }

    @Test
    public void testDistanceGreaterThanZero_3() {
        assertEquals(2, InheritanceUtils.distance(Grandchild.class, AnotherParent.class));
    }

    @Test
    public void testDistanceGreaterThanZero_4() {
        assertEquals(3, InheritanceUtils.distance(Grandchild.class, Object.class));
    }
}
