package org.graylog2.shared.utilities;

import org.junit.Test;
import java.io.IOException;
import java.net.SocketTimeoutException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ExceptionUtilsTest_Parameterized {

    private void setTestStackTrace(Throwable t, String className, String method, int line) {
        final StackTraceElement traceElement = new StackTraceElement(className, method, className + ".java", line);
        t.setStackTrace(new StackTraceElement[] { traceElement, traceElement });
    }

    @ParameterizedTest
    @MethodSource("Provider_hasCauseOf_returnsTrueIfTheExceptionItselfIsSubtypeOfTheProvidedType_1to2")
    public void hasCauseOf_returnsTrueIfTheExceptionItselfIsSubtypeOfTheProvidedType_1to2(String param1) {
        assertThat(ExceptionUtils.hasCauseOf(new SocketTimeoutException(param1), IOException.class)).isTrue();
    }

    static public Stream<Arguments> Provider_hasCauseOf_returnsTrueIfTheExceptionItselfIsSubtypeOfTheProvidedType_1to2() {
        return Stream.of(arguments("asdasd"), arguments("asdasd"));
    }

    @ParameterizedTest
    @MethodSource("Provider_hasCauseOf_returnsTrueIfTheCauseIsSubtypeOfTheProvidedType_1to2")
    public void hasCauseOf_returnsTrueIfTheCauseIsSubtypeOfTheProvidedType_1to2(String param1, String param2) {
        assertThat(ExceptionUtils.hasCauseOf(new RuntimeException(param1, new SocketTimeoutException(param2)), IOException.class)).isTrue();
    }

    static public Stream<Arguments> Provider_hasCauseOf_returnsTrueIfTheCauseIsSubtypeOfTheProvidedType_1to2() {
        return Stream.of(arguments("parent", "asdasd"), arguments("parent", "asdasd"));
    }
}
