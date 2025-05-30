package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.WikimediaLanguageCodes;

public class WikimediaLanguageCodesTest_Purified {

    @Test
    public void getSomeLanguageCodes_1() {
        assertEquals("gsw", WikimediaLanguageCodes.getLanguageCode("als"));
    }

    @Test
    public void getSomeLanguageCodes_2() {
        assertEquals("en", WikimediaLanguageCodes.getLanguageCode("en"));
    }

    @Test
    public void fixDeprecatedLanguageCode_1() {
        assertEquals("nb", WikimediaLanguageCodes.fixLanguageCodeIfDeprecated("no"));
    }

    @Test
    public void fixDeprecatedLanguageCode_2() {
        assertEquals("en", WikimediaLanguageCodes.fixLanguageCodeIfDeprecated("en"));
    }
}
