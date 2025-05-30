package org.languagetool.dev.wikipedia;

import org.junit.Ignore;
import org.junit.Test;
import xtc.tree.Location;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@Ignore
public class LocationHelperTest_Purified {

    private int checkLocation(int line, int col, String text) {
        return LocationHelper.absolutePositionFor(new Location("", line, col), text);
    }

    @Test
    public void testAbsolutePositionFor_1() {
        assertThat(checkLocation(1, 1, "hallo"), is(0));
    }

    @Test
    public void testAbsolutePositionFor_2() {
        assertThat(checkLocation(1, 2, "hallo"), is(1));
    }

    @Test
    public void testAbsolutePositionFor_3() {
        assertThat(checkLocation(2, 1, "hallo\nx"), is(6));
    }

    @Test
    public void testAbsolutePositionFor_4() {
        assertThat(checkLocation(3, 3, "\n\nxyz"), is(4));
    }
}
