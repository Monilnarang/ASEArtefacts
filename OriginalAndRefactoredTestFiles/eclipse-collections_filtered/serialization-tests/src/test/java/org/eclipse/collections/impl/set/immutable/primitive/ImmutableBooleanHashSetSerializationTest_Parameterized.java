package org.eclipse.collections.impl.set.immutable.primitive;

import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ImmutableBooleanHashSetSerializationTest_Parameterized {

    @Test
    public void serializedForm_1() {
        Verify.assertSerializedForm("rO0ABXNyAFpvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5pbW11dGFibGUucHJpbWl0\n" + "aXZlLkltbXV0YWJsZUJvb2xlYW5TZXRTZXJpYWxpemF0aW9uUHJveHkAAAAAAAAAAQwAAHhwdwQA\n" + "AAAAeA==", BooleanSets.immutable.with());
    }

    @Test
    public void serializedForm_4() {
        Verify.assertSerializedForm("rO0ABXNyAFpvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5pbW11dGFibGUucHJpbWl0\n" + "aXZlLkltbXV0YWJsZUJvb2xlYW5TZXRTZXJpYWxpemF0aW9uUHJveHkAAAAAAAAAAQwAAHhwdwYA\n" + "AAACAAF4", BooleanSets.immutable.with(false, true));
    }

    @ParameterizedTest
    @MethodSource("Provider_serializedForm_2to3")
    public void serializedForm_2to3(String param1, String param2, String param3) {
        Verify.assertSerializedForm(param3 + "aXZlLkltbXV0YWJsZUJvb2xlYW5TZXRTZXJpYWxpemF0aW9uUHJveHkAAAAAAAAAAQwAAHhwdwUA\n" + param1, BooleanSets.immutable.with(param2));
    }

    static public Stream<Arguments> Provider_serializedForm_2to3() {
        return Stream.of(arguments("AAABAHg=", "rO0ABXNyAFpvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5pbW11dGFibGUucHJpbWl0\n", "aXZlLkltbXV0YWJsZUJvb2xlYW5TZXRTZXJpYWxpemF0aW9uUHJveHkAAAAAAAAAAQwAAHhwdwUA\n"), arguments("AAABAXg=", "rO0ABXNyAFpvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5pbW11dGFibGUucHJpbWl0\n", "aXZlLkltbXV0YWJsZUJvb2xlYW5TZXRTZXJpYWxpemF0aW9uUHJveHkAAAAAAAAAAQwAAHhwdwUA\n"));
    }
}
