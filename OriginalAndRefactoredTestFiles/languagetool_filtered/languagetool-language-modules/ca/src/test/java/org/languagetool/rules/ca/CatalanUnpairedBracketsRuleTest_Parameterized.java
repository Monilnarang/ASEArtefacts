package org.languagetool.rules.ca;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Catalan;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.TextLevelRule;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CatalanUnpairedBracketsRuleTest_Parameterized {

    private TextLevelRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new CatalanUnpairedBracketsRule(TestTools.getEnglishMessages(), Catalan.getInstance());
        lt = new JLanguageTool(Catalan.getInstance());
    }

    private void assertMatches(String input, int expectedMatches) throws IOException {
        final RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(input)));
        assertEquals(expectedMatches, matches.length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to75")
    public void testRule_1to75(String param1, int param2) throws IOException {
        assertMatches(param1, param2);
    }

    static public Stream<Arguments> Provider_testRule_1to75() {
        return Stream.of(arguments("Guns N' Roses", 0), arguments("L'«home és així»", 0), arguments("l'«home»", 0), arguments("«\"És així\" o no»", 0), arguments("«\"És així\", va dir.»", 0), arguments("«És \"així\" o no»", 0), arguments("(l'execució a mans d'\"especialistes\")", 0), arguments("(L'\"especialista\")", 0), arguments("\"Vine\", li va dir.", 0), arguments("(Una frase de prova).", 0), arguments("Aquesta és la paraula 'prova'.", 0), arguments("This is a sentence with a smiley :-)", 0), arguments("This is a sentence with a smiley ;-) and so on...", 0), arguments("Aquesta és l'hora de les decisions.", 0), arguments("Aquesta és l’hora de les decisions.", 0), arguments("(fig. 20)", 0), arguments("\"Sóc la teva filla. El corcó no et rosegarà més.\"\n\n", 0), arguments("–\"Club dels llagoters\" –va repetir en Ron.", 0), arguments("—\"Club dels llagoters\" –va repetir en Ron.", 0), arguments("»Això em porta a demanar-t'ho.", 0), arguments("»Això em porta (sí) a demanar-t'ho.", 0), arguments("al capítol 12 \"Llavors i fruits oleaginosos\"", 0), arguments("\"Per què serveixen les forquilles?\" i aquest respon \"per menjar\".", 0), arguments("És a 60º 50' 23\"", 0), arguments("És a 60º 50' 23'", 0), arguments("60° 50' 23'", 0), arguments("60° 50'", 0), arguments("El tràiler té una picada d'ullet quan diu que \"no es pot fer una pel·lícula 'slasher' com si fos una sèrie\".", 0), arguments("El tràiler –que té una picada d'ullet quan diu que \"no es pot fer una pel·lícula 'slasher' com si fos una sèrie\"– ja ", 0), arguments("This is a [test] sentence...", 0), arguments("The plight of Tamil refugees caused a surge of support from most of the Tamil political parties.[90]", 0), arguments("This is what he said: \"We believe in freedom. This is what we do.\"", 0), arguments("(([20] [20] [20]))", 0), arguments("This is a \"special test\", right?", 0), arguments("We discussed this in Chapter 1).", 0), arguments("The jury recommended that: (1) Four additional deputies be employed.", 0), arguments("We discussed this in section 1a).", 0), arguments("We discussed this in section iv).", 0), arguments("In addition, the government would pay a $1,000 \"cost of education\" grant to the schools.", 0), arguments("Porta'l cap ací.", 0), arguments("Porta-me'n cinquanta!", 0), arguments("Harper's Dictionary of Classical Antiquities", 0), arguments("Harper’s Dictionary of Classical Antiquities", 0), arguments("Això és “important i ara què passa. ", 1), arguments("Això és \"important i ara què passa. ", 1), arguments("Això és (impossible. ", 1), arguments("Això es (impossible. ", 1), arguments("Això és) impossible. ", 1), arguments("Això es impossible). ", 1), arguments("Això és «important, oi que sí?", 1), arguments("(aquesta 'és la solució)", 1), arguments("(L'\"especialista\"", 0), arguments("(L'\"especialista\".", 1), arguments("L'«home és així", 0), arguments("L'«home és així.", 1), arguments("S'«esperava 'el' (segon) \"resultat\"", 0), arguments("S'«esperava 'el' (segon) \"resultat\".", 1), arguments("l'«home", 0), arguments("l'«home.", 1), arguments("Ploraria.\"", 1), arguments("Aquesta és l555’hora de les decisions.", 1), arguments("Vine\", li va dir.", 1), arguments("Aquesta és l‘hora de les decisions.", 1), arguments("(This is a test sentence.", 1), arguments("This is a test with an apostrophe &'.", 1), arguments("&'", 0), arguments("&'.", 1), arguments("!'", 0), arguments("!'.", 1), arguments("What?'", 0), arguments("What?'.", 1), arguments("Some text (and some funny remark :-) with more text to follow", 0), arguments("Some text (and some funny remark :-) with more text to follow?", 1), arguments("(This is a test” sentence.", 2), arguments("This [is (a test} sentence.", 3));
    }
}
