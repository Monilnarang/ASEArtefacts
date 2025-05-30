package org.apache.seata.common.exception;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionUtilTest_Purified {

    private Exception exception;

    @Test
    public void unwrap_1() {
        InvocationTargetException targetException = new InvocationTargetException(new RuntimeException("invocation"));
        Assertions.assertInstanceOf(RuntimeException.class, ExceptionUtil.unwrap(targetException));
    }

    @Test
    public void unwrap_2() {
        UndeclaredThrowableException exception = new UndeclaredThrowableException(new RuntimeException("undeclared"));
        Assertions.assertInstanceOf(RuntimeException.class, ExceptionUtil.unwrap(exception));
    }

    @Test
    public void unwrap_3() {
        RuntimeException runtimeException = new RuntimeException("runtime");
        Assertions.assertInstanceOf(RuntimeException.class, ExceptionUtil.unwrap(runtimeException));
    }
}
