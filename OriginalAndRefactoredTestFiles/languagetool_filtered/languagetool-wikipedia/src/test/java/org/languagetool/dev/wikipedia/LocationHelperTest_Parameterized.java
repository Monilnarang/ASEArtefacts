package org.languagetool.dev.wikipedia;

import org.junit.Ignore;
import org.junit.Test;
import xtc.tree.Location;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Ignore
public class LocationHelperTest_Parameterized {

    private int checkLocation(int line, int col, String text) {
        return LocationHelper.absolutePositionFor(new Location("", line, col), text);
    }

    @ParameterizedTest
    @MethodSource("Provider_testAbsolutePositionFor_1to4")
    public void testAbsolutePositionFor_1to4(int param1, int param2, String param3, int param4) {
        assertThat(checkLocation(param1, param2, param3), is(param4));
    }

    static public Stream<Arguments> Provider_testAbsolutePositionFor_1to4() {
        return Stream.of(arguments(1, 1, "hallo", 0), arguments(1, 2, "hallo", 1), arguments(2, 1, "hallo\nx", 6), arguments(3, 3, "\n\nxyz", 4));
    }
}
