package org.eclipse.collections.impl.set.immutable.primitive;

import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;

public class ImmutableBooleanHashSetSerializationTest_Purified {

    @Test
    public void serializedForm_1() {
        Verify.assertSerializedForm("rO0ABXNyAFpvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5pbW11dGFibGUucHJpbWl0\n" + "aXZlLkltbXV0YWJsZUJvb2xlYW5TZXRTZXJpYWxpemF0aW9uUHJveHkAAAAAAAAAAQwAAHhwdwQA\n" + "AAAAeA==", BooleanSets.immutable.with());
    }

    @Test
    public void serializedForm_2() {
        Verify.assertSerializedForm("rO0ABXNyAFpvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5pbW11dGFibGUucHJpbWl0\n" + "aXZlLkltbXV0YWJsZUJvb2xlYW5TZXRTZXJpYWxpemF0aW9uUHJveHkAAAAAAAAAAQwAAHhwdwUA\n" + "AAABAHg=", BooleanSets.immutable.with(false));
    }

    @Test
    public void serializedForm_3() {
        Verify.assertSerializedForm("rO0ABXNyAFpvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5pbW11dGFibGUucHJpbWl0\n" + "aXZlLkltbXV0YWJsZUJvb2xlYW5TZXRTZXJpYWxpemF0aW9uUHJveHkAAAAAAAAAAQwAAHhwdwUA\n" + "AAABAXg=", BooleanSets.immutable.with(true));
    }

    @Test
    public void serializedForm_4() {
        Verify.assertSerializedForm("rO0ABXNyAFpvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5pbW11dGFibGUucHJpbWl0\n" + "aXZlLkltbXV0YWJsZUJvb2xlYW5TZXRTZXJpYWxpemF0aW9uUHJveHkAAAAAAAAAAQwAAHhwdwYA\n" + "AAACAAF4", BooleanSets.immutable.with(false, true));
    }
}
