package org.languagetool.rules.de;

import org.junit.Test;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.tagging.de.GermanToken;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GermanHelperTest_Purified {

    @Test
    public void testGetDeterminerGender_1() throws Exception {
        assertThat(GermanHelper.getDeterminerGender(null), is(""));
    }

    @Test
    public void testGetDeterminerGender_2() throws Exception {
        assertThat(GermanHelper.getDeterminerGender(""), is(""));
    }

    @Test
    public void testGetDeterminerGender_3() throws Exception {
        assertThat(GermanHelper.getDeterminerGender("ART:DEF:DAT:SIN:FEM"), is("FEM"));
    }
}
