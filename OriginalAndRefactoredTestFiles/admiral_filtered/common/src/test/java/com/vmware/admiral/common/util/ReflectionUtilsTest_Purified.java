package com.vmware.admiral.common.util;

import static org.junit.Assert.assertEquals;
import javax.ws.rs.Path;
import org.junit.Test;
import com.vmware.admiral.common.util.ReflectionUtils.CustomPath;

public class ReflectionUtilsTest_Purified {

    @Path("/foo")
    class DummyClass {
    }

    @Test
    public void testSetPathAnnotation_1() {
        assertEquals("/foo", DummyClass.class.getAnnotation(Path.class).value());
    }

    @Test
    public void testSetPathAnnotation_2() {
        assertEquals("/bar", DummyClass.class.getAnnotation(Path.class).value());
    }
}
