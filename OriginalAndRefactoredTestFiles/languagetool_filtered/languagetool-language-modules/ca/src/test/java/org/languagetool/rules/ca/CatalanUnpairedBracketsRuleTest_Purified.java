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

public class CatalanUnpairedBracketsRuleTest_Purified {

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

    @Test
    public void testRule_1() throws IOException {
        assertMatches("Guns N' Roses", 0);
    }

    @Test
    public void testRule_2() throws IOException {
        assertMatches("L'«home és així»", 0);
    }

    @Test
    public void testRule_3() throws IOException {
        assertMatches("l'«home»", 0);
    }

    @Test
    public void testRule_4() throws IOException {
        assertMatches("«\"És així\" o no»", 0);
    }

    @Test
    public void testRule_5() throws IOException {
        assertMatches("«\"És així\", va dir.»", 0);
    }

    @Test
    public void testRule_6() throws IOException {
        assertMatches("«És \"així\" o no»", 0);
    }

    @Test
    public void testRule_7() throws IOException {
        assertMatches("(l'execució a mans d'\"especialistes\")", 0);
    }

    @Test
    public void testRule_8() throws IOException {
        assertMatches("(L'\"especialista\")", 0);
    }

    @Test
    public void testRule_9() throws IOException {
        assertMatches("\"Vine\", li va dir.", 0);
    }

    @Test
    public void testRule_10() throws IOException {
        assertMatches("(Una frase de prova).", 0);
    }

    @Test
    public void testRule_11() throws IOException {
        assertMatches("Aquesta és la paraula 'prova'.", 0);
    }

    @Test
    public void testRule_12() throws IOException {
        assertMatches("This is a sentence with a smiley :-)", 0);
    }

    @Test
    public void testRule_13() throws IOException {
        assertMatches("This is a sentence with a smiley ;-) and so on...", 0);
    }

    @Test
    public void testRule_14() throws IOException {
        assertMatches("Aquesta és l'hora de les decisions.", 0);
    }

    @Test
    public void testRule_15() throws IOException {
        assertMatches("Aquesta és l’hora de les decisions.", 0);
    }

    @Test
    public void testRule_16() throws IOException {
        assertMatches("(fig. 20)", 0);
    }

    @Test
    public void testRule_17() throws IOException {
        assertMatches("\"Sóc la teva filla. El corcó no et rosegarà més.\"\n\n", 0);
    }

    @Test
    public void testRule_18() throws IOException {
        assertMatches("–\"Club dels llagoters\" –va repetir en Ron.", 0);
    }

    @Test
    public void testRule_19() throws IOException {
        assertMatches("—\"Club dels llagoters\" –va repetir en Ron.", 0);
    }

    @Test
    public void testRule_20() throws IOException {
        assertMatches("»Això em porta a demanar-t'ho.", 0);
    }

    @Test
    public void testRule_21() throws IOException {
        assertMatches("»Això em porta (sí) a demanar-t'ho.", 0);
    }

    @Test
    public void testRule_22() throws IOException {
        assertMatches("al capítol 12 \"Llavors i fruits oleaginosos\"", 0);
    }

    @Test
    public void testRule_23() throws IOException {
        assertMatches("\"Per què serveixen les forquilles?\" i aquest respon \"per menjar\".", 0);
    }

    @Test
    public void testRule_24() throws IOException {
        assertMatches("És a 60º 50' 23\"", 0);
    }

    @Test
    public void testRule_25() throws IOException {
        assertMatches("És a 60º 50' 23'", 0);
    }

    @Test
    public void testRule_26() throws IOException {
        assertMatches("60° 50' 23'", 0);
    }

    @Test
    public void testRule_27() throws IOException {
        assertMatches("60° 50'", 0);
    }

    @Test
    public void testRule_28() throws IOException {
        assertMatches("El tràiler té una picada d'ullet quan diu que \"no es pot fer una pel·lícula 'slasher' com si fos una sèrie\".", 0);
    }

    @Test
    public void testRule_29() throws IOException {
        assertMatches("El tràiler –que té una picada d'ullet quan diu que \"no es pot fer una pel·lícula 'slasher' com si fos una sèrie\"– ja ", 0);
    }

    @Test
    public void testRule_30() throws IOException {
        assertMatches("This is a [test] sentence...", 0);
    }

    @Test
    public void testRule_31() throws IOException {
        assertMatches("The plight of Tamil refugees caused a surge of support from most of the Tamil political parties.[90]", 0);
    }

    @Test
    public void testRule_32() throws IOException {
        assertMatches("This is what he said: \"We believe in freedom. This is what we do.\"", 0);
    }

    @Test
    public void testRule_33() throws IOException {
        assertMatches("(([20] [20] [20]))", 0);
    }

    @Test
    public void testRule_34() throws IOException {
        assertMatches("This is a \"special test\", right?", 0);
    }

    @Test
    public void testRule_35() throws IOException {
        assertMatches("We discussed this in Chapter 1).", 0);
    }

    @Test
    public void testRule_36() throws IOException {
        assertMatches("The jury recommended that: (1) Four additional deputies be employed.", 0);
    }

    @Test
    public void testRule_37() throws IOException {
        assertMatches("We discussed this in section 1a).", 0);
    }

    @Test
    public void testRule_38() throws IOException {
        assertMatches("We discussed this in section iv).", 0);
    }

    @Test
    public void testRule_39() throws IOException {
        assertMatches("In addition, the government would pay a $1,000 \"cost of education\" grant to the schools.", 0);
    }

    @Test
    public void testRule_40() throws IOException {
        assertMatches("Porta'l cap ací.", 0);
    }

    @Test
    public void testRule_41() throws IOException {
        assertMatches("Porta-me'n cinquanta!", 0);
    }

    @Test
    public void testRule_42() throws IOException {
        assertMatches("Harper's Dictionary of Classical Antiquities", 0);
    }

    @Test
    public void testRule_43() throws IOException {
        assertMatches("Harper’s Dictionary of Classical Antiquities", 0);
    }

    @Test
    public void testRule_44() throws IOException {
        assertMatches("Això és “important i ara què passa. ", 1);
    }

    @Test
    public void testRule_45() throws IOException {
        assertMatches("Això és \"important i ara què passa. ", 1);
    }

    @Test
    public void testRule_46() throws IOException {
        assertMatches("Això és (impossible. ", 1);
    }

    @Test
    public void testRule_47() throws IOException {
        assertMatches("Això es (impossible. ", 1);
    }

    @Test
    public void testRule_48() throws IOException {
        assertMatches("Això és) impossible. ", 1);
    }

    @Test
    public void testRule_49() throws IOException {
        assertMatches("Això es impossible). ", 1);
    }

    @Test
    public void testRule_50() throws IOException {
        assertMatches("Això és «important, oi que sí?", 1);
    }

    @Test
    public void testRule_51() throws IOException {
        assertMatches("(aquesta 'és la solució)", 1);
    }

    @Test
    public void testRule_52() throws IOException {
        assertMatches("(L'\"especialista\"", 0);
    }

    @Test
    public void testRule_53() throws IOException {
        assertMatches("(L'\"especialista\".", 1);
    }

    @Test
    public void testRule_54() throws IOException {
        assertMatches("L'«home és així", 0);
    }

    @Test
    public void testRule_55() throws IOException {
        assertMatches("L'«home és així.", 1);
    }

    @Test
    public void testRule_56() throws IOException {
        assertMatches("S'«esperava 'el' (segon) \"resultat\"", 0);
    }

    @Test
    public void testRule_57() throws IOException {
        assertMatches("S'«esperava 'el' (segon) \"resultat\".", 1);
    }

    @Test
    public void testRule_58() throws IOException {
        assertMatches("l'«home", 0);
    }

    @Test
    public void testRule_59() throws IOException {
        assertMatches("l'«home.", 1);
    }

    @Test
    public void testRule_60() throws IOException {
        assertMatches("Ploraria.\"", 1);
    }

    @Test
    public void testRule_61() throws IOException {
        assertMatches("Aquesta és l555’hora de les decisions.", 1);
    }

    @Test
    public void testRule_62() throws IOException {
        assertMatches("Vine\", li va dir.", 1);
    }

    @Test
    public void testRule_63() throws IOException {
        assertMatches("Aquesta és l‘hora de les decisions.", 1);
    }

    @Test
    public void testRule_64() throws IOException {
        assertMatches("(This is a test sentence.", 1);
    }

    @Test
    public void testRule_65() throws IOException {
        assertMatches("This is a test with an apostrophe &'.", 1);
    }

    @Test
    public void testRule_66() throws IOException {
        assertMatches("&'", 0);
    }

    @Test
    public void testRule_67() throws IOException {
        assertMatches("&'.", 1);
    }

    @Test
    public void testRule_68() throws IOException {
        assertMatches("!'", 0);
    }

    @Test
    public void testRule_69() throws IOException {
        assertMatches("!'.", 1);
    }

    @Test
    public void testRule_70() throws IOException {
        assertMatches("What?'", 0);
    }

    @Test
    public void testRule_71() throws IOException {
        assertMatches("What?'.", 1);
    }

    @Test
    public void testRule_72() throws IOException {
        assertMatches("Some text (and some funny remark :-) with more text to follow", 0);
    }

    @Test
    public void testRule_73() throws IOException {
        assertMatches("Some text (and some funny remark :-) with more text to follow?", 1);
    }

    @Test
    public void testRule_74() throws IOException {
        assertMatches("(This is a test” sentence.", 2);
    }

    @Test
    public void testRule_75() throws IOException {
        assertMatches("This [is (a test} sentence.", 3);
    }
}
