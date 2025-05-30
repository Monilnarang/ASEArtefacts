package org.graylog2.shared.utilities;

import org.junit.Test;
import java.io.IOException;
import java.net.SocketTimeoutException;
import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionUtilsTest_Purified {

    private void setTestStackTrace(Throwable t, String className, String method, int line) {
        final StackTraceElement traceElement = new StackTraceElement(className, method, className + ".java", line);
        t.setStackTrace(new StackTraceElement[] { traceElement, traceElement });
    }

    @Test
    public void hasCauseOf_returnsTrueIfTheExceptionItselfIsSubtypeOfTheProvidedType_1() {
        assertThat(ExceptionUtils.hasCauseOf(new SocketTimeoutException("asdasd"), IOException.class)).isTrue();
    }

    @Test
    public void hasCauseOf_returnsTrueIfTheExceptionItselfIsSubtypeOfTheProvidedType_2() {
        assertThat(ExceptionUtils.hasCauseOf(new IOException("asdasd"), IOException.class)).isTrue();
    }

    @Test
    public void hasCauseOf_returnsTrueIfTheCauseIsSubtypeOfTheProvidedType_1() {
        assertThat(ExceptionUtils.hasCauseOf(new RuntimeException("parent", new SocketTimeoutException("asdasd")), IOException.class)).isTrue();
    }

    @Test
    public void hasCauseOf_returnsTrueIfTheCauseIsSubtypeOfTheProvidedType_2() {
        assertThat(ExceptionUtils.hasCauseOf(new RuntimeException("parent", new IOException("asdasd")), IOException.class)).isTrue();
    }
}
