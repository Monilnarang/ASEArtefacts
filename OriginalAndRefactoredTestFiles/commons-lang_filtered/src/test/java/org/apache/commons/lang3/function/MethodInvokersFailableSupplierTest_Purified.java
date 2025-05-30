package org.apache.commons.lang3.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

public class MethodInvokersFailableSupplierTest_Purified extends MethodFixtures {

    @Test
    public void testSupplierStatic_1() throws Throwable {
        assertEquals(staticGetString(), MethodInvokers.asFailableSupplier(getMethodForStaticGetString()).get());
    }

    @Test
    public void testSupplierStatic_2() throws Throwable {
        assertEquals(staticGetString(), MethodInvokers.asFailableSupplier(getMethodForStaticGetString()).get());
    }
}
