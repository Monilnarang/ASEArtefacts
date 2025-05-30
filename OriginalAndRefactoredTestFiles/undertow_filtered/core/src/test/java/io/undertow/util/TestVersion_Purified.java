package io.undertow.util;

import io.undertow.Version;
import io.undertow.testutils.category.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class TestVersion_Purified {

    @Test
    public void testVersionSet_1() {
        Assert.assertNotNull(Version.getVersionString());
    }

    @Test
    public void testVersionSet_2() {
        String version = Version.getVersionString();
        System.out.println("version = " + version);
        Assert.assertNotSame("Unknown", version);
    }
}
