package org.eclipse.collections.impl.set.sorted.immutable;

import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;

public class ImmutableTreeSetSerializationTest_Purified {

    @Test
    public void serializedForm_no_comparator_1() {
        Verify.assertSerializedForm(2L, "rO0ABXNyAFZvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5zb3J0ZWQuaW1tdXRhYmxl\n" + "LkltbXV0YWJsZVNvcnRlZFNldFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABDAAAeHBwdwQAAAAE\n" + "c3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVt\n" + "YmVyhqyVHQuU4IsCAAB4cAAAAAFzcQB+AAIAAAACc3EAfgACAAAAA3NxAH4AAgAAAAR4", ImmutableTreeSet.newSetWith(1, 2, 3, 4));
    }

    @Test
    public void serializedForm_no_comparator_2() {
        Verify.assertSerializedForm(2L, "rO0ABXNyAFZvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5zb3J0ZWQuaW1tdXRhYmxl\n" + "LkltbXV0YWJsZVNvcnRlZFNldFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABDAAAeHBwdwQAAAAA\n" + "eA==", ImmutableTreeSet.newSetWith());
    }

    @Test
    public void serializedForm_comparator_1() {
        Verify.assertSerializedForm(2L, "rO0ABXNyAFZvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5zb3J0ZWQuaW1tdXRhYmxl\n" + "LkltbXV0YWJsZVNvcnRlZFNldFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABDAAAeHBzcgBYb3Jn\n" + "LmVjbGlwc2UuY29sbGVjdGlvbnMuYXBpLmJsb2NrLmZhY3RvcnkuU2VyaWFsaXphYmxlQ29tcGFy\n" + "YXRvcnMkTmF0dXJhbE9yZGVyQ29tcGFyYXRvcgAAAAAAAAABAgAAeHB3BAAAAARzcgARamF2YS5s\n" + "YW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5Tg\n" + "iwIAAHhwAAAAAXNxAH4ABAAAAAJzcQB+AAQAAAADc3EAfgAEAAAABHg=", ImmutableTreeSet.newSetWith(Comparators.naturalOrder(), 1, 2, 3, 4));
    }

    @Test
    public void serializedForm_comparator_2() {
        Verify.assertSerializedForm(2L, "rO0ABXNyAFZvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5zb3J0ZWQuaW1tdXRhYmxl\n" + "LkltbXV0YWJsZVNvcnRlZFNldFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABDAAAeHBzcgBYb3Jn\n" + "LmVjbGlwc2UuY29sbGVjdGlvbnMuYXBpLmJsb2NrLmZhY3RvcnkuU2VyaWFsaXphYmxlQ29tcGFy\n" + "YXRvcnMkTmF0dXJhbE9yZGVyQ29tcGFyYXRvcgAAAAAAAAABAgAAeHB3BAAAAAB4", ImmutableTreeSet.newSetWith(Comparators.naturalOrder()));
    }

    @Test
    public void serializedForm_comparator_old_1() {
        Verify.assertSerializedForm(2L, "rO0ABXNyAFZvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5zb3J0ZWQuaW1tdXRhYmxl\n" + "LkltbXV0YWJsZVNvcnRlZFNldFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABDAAAeHBzcgBNb3Jn\n" + "LmVjbGlwc2UuY29sbGVjdGlvbnMuaW1wbC5ibG9jay5mYWN0b3J5LkNvbXBhcmF0b3JzJE5hdHVy\n" + "YWxPcmRlckNvbXBhcmF0b3IAAAAAAAAAAQIAAHhwdwQAAAAEc3IAEWphdmEubGFuZy5JbnRlZ2Vy\n" + "EuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFz\n" + "cQB+AAQAAAACc3EAfgAEAAAAA3NxAH4ABAAAAAR4", ImmutableTreeSet.newSetWith(Comparators.originalNaturalOrder(), 1, 2, 3, 4));
    }

    @Test
    public void serializedForm_comparator_old_2() {
        Verify.assertSerializedForm(2L, "rO0ABXNyAFZvcmcuZWNsaXBzZS5jb2xsZWN0aW9ucy5pbXBsLnNldC5zb3J0ZWQuaW1tdXRhYmxl\n" + "LkltbXV0YWJsZVNvcnRlZFNldFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABDAAAeHBzcgBNb3Jn\n" + "LmVjbGlwc2UuY29sbGVjdGlvbnMuaW1wbC5ibG9jay5mYWN0b3J5LkNvbXBhcmF0b3JzJE5hdHVy\n" + "YWxPcmRlckNvbXBhcmF0b3IAAAAAAAAAAQIAAHhwdwQAAAAAeA==", ImmutableTreeSet.newSetWith(Comparators.originalNaturalOrder()));
    }
}
