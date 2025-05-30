package org.languagetool.rules.de;

import org.junit.Test;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.tagging.de.GermanToken;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GermanHelperTest_Parameterized {

    @Test
    public void testGetDeterminerGender_1() throws Exception {
        assertThat(GermanHelper.getDeterminerGender(null), is(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDeterminerGender_2to3")
    public void testGetDeterminerGender_2to3(String param1, String param2) throws Exception {
        assertThat(GermanHelper.getDeterminerGender(param1), is(param2));
    }

    static public Stream<Arguments> Provider_testGetDeterminerGender_2to3() {
        return Stream.of(arguments("", ""), arguments("ART:DEF:DAT:SIN:FEM", "FEM"));
    }
}
