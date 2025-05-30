package org.languagetool.rules.nl;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Dutch;
import org.languagetool.rules.GenericUnpairedBracketsRule;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.Collections;
import static org.junit.Assert.assertEquals;

public class GenericUnpairedBracketsRuleTest_Purified {

    private GenericUnpairedBracketsRule rule;

    private JLanguageTool lt;

    private void assertMatches(String input, int expectedMatches) throws IOException {
        final RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(input)));
        assertEquals(expectedMatches, matches.length);
    }

    @Test
    public void testDutchRule_1() throws IOException {
        assertMatches("Het centrale probleem van het werk is de ‘dichterlijke kuischheid’.", 0);
    }

    @Test
    public void testDutchRule_2() throws IOException {
        assertMatches(" Eurlings: “De gegevens van de dienst zijn van cruciaal belang voor de veiligheid van de luchtvaart en de scheepvaart”.", 0);
    }

    @Test
    public void testDutchRule_3() throws IOException {
        assertMatches(" Eurlings: \u201eDe gegevens van de dienst zijn van cruciaal belang voor de veiligheid van de luchtvaart en de scheepvaart\u201d.", 0);
    }

    @Test
    public void testDutchRule_4() throws IOException {
        assertMatches("Het centrale probleem van het werk is de „dichterlijke kuischheid.", 1);
    }

    @Test
    public void testDutchRule_5() throws IOException {
        assertMatches(" Eurlings: “De gegevens van de dienst zijn van cruciaal belang voor de veiligheid van de luchtvaart en de scheepvaart.", 1);
    }
}
