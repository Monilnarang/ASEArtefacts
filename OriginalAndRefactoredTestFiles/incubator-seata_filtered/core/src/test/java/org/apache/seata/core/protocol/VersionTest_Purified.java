package org.apache.seata.core.protocol;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VersionTest_Purified {

    @Test
    public void isAboveOrEqualVersion150_1() {
        Assertions.assertTrue(Version.isAboveOrEqualVersion150("2.0.2"));
    }

    @Test
    public void isAboveOrEqualVersion150_2() {
        Assertions.assertTrue(Version.isAboveOrEqualVersion150("1.5"));
    }

    @Test
    public void isAboveOrEqualVersion150_3() {
        Assertions.assertFalse(Version.isAboveOrEqualVersion150("1.4.9"));
    }

    @Test
    public void isAboveOrEqualVersion150_4() {
        Assertions.assertFalse(Version.isAboveOrEqualVersion150(""));
    }

    @Test
    public void isAboveOrEqualVersion150_5() {
        Assertions.assertFalse(Version.isAboveOrEqualVersion150("abd"));
    }
}
