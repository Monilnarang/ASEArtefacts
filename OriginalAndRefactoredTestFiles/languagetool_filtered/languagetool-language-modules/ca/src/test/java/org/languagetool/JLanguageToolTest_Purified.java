package org.languagetool;

import org.junit.Test;
import org.languagetool.language.Catalan;
import org.languagetool.language.ValencianCatalan;
import org.languagetool.language.BalearicCatalan;
import org.languagetool.rules.CommaWhitespaceRule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.ca.SimpleReplaceAnglicism;
import org.languagetool.rules.ca.SimpleReplaceMultiwordsRule;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class JLanguageToolTest_Purified {

    private Language lang = Catalan.getInstance();

    private JLanguageTool tool = new JLanguageTool(lang);

    @Test
    public void testAdvancedTypography_1() throws IOException {
        assertEquals(lang.toAdvancedTypography("És l'\"hora\"!"), "És l’«hora»!");
    }

    @Test
    public void testAdvancedTypography_2() throws IOException {
        assertEquals(lang.toAdvancedTypography("És l''hora'!"), "És l’‘hora’!");
    }

    @Test
    public void testAdvancedTypography_3() throws IOException {
        assertEquals(lang.toAdvancedTypography("És l'«hora»!"), "És l’«hora»!");
    }

    @Test
    public void testAdvancedTypography_4() throws IOException {
        assertEquals(lang.toAdvancedTypography("És l''hora'."), "És l’‘hora’.");
    }

    @Test
    public void testAdvancedTypography_5() throws IOException {
        assertEquals(lang.toAdvancedTypography("Cal evitar el \"'lo' neutre\"."), "Cal evitar el «‘lo’ neutre».");
    }

    @Test
    public void testAdvancedTypography_6() throws IOException {
        assertEquals(lang.toAdvancedTypography("És \"molt 'important'\"."), "És «molt ‘important’».");
    }

    @Test
    public void testAdvancedTypography_7() throws IOException {
        assertEquals(lang.toAdvancedTypography("Si és del v. 'haver'."), "Si és del v.\u00a0‘haver’.");
    }

    @Test
    public void testAdvancedTypography_8() throws IOException {
        assertEquals(lang.toAdvancedTypography("Amb el so de 's'."), "Amb el so de ‘s’.");
    }

    @Test
    public void testAdvancedTypography_9() throws IOException {
        assertEquals(lang.adaptSuggestion("L'IEC"), "L'IEC");
    }

    @Test
    public void testAdvancedTypography_10() throws IOException {
        assertEquals(lang.adaptSuggestion("te estimava"), "t'estimava");
    }

    @Test
    public void testAdvancedTypography_11() throws IOException {
        assertEquals(lang.adaptSuggestion("el Albert"), "l'Albert");
    }

    @Test
    public void testAdvancedTypography_12() throws IOException {
        assertEquals(lang.adaptSuggestion("l'Albert"), "l'Albert");
    }

    @Test
    public void testAdvancedTypography_13() throws IOException {
        assertEquals(lang.adaptSuggestion("l'«Albert»"), "l'«Albert»");
    }

    @Test
    public void testAdvancedTypography_14() throws IOException {
        assertEquals(lang.adaptSuggestion("l’«Albert»"), "l’«Albert»");
    }

    @Test
    public void testAdvancedTypography_15() throws IOException {
        assertEquals(lang.adaptSuggestion("l'\"Albert\""), "l'\"Albert\"");
    }

    @Test
    public void testAdvancedTypography_16() throws IOException {
        assertEquals(lang.adaptSuggestion("m'tancava"), "em tancava");
    }

    @Test
    public void testAdvancedTypography_17() throws IOException {
        assertEquals(lang.adaptSuggestion("s'tancava"), "es tancava");
    }

    @Test
    public void testAdvancedTypography_18() throws IOException {
        assertEquals(lang.adaptSuggestion("l'R+D"), "l'R+D");
    }

    @Test
    public void testAdvancedTypography_19() throws IOException {
        assertEquals(lang.adaptSuggestion("l'FBI"), "l'FBI");
    }

    @Test
    public void testMultitokenSpeller_1() throws IOException {
        assertEquals("[Jacques-Louis David]", lang.getMultitokenSpeller().getSuggestions("Jacques Louis David").toString());
    }

    @Test
    public void testMultitokenSpeller_2() throws IOException {
        assertEquals("[Chiang Kai-shek]", lang.getMultitokenSpeller().getSuggestions("Chiang Kaishek").toString());
    }

    @Test
    public void testMultitokenSpeller_3() throws IOException {
        assertEquals("[Comédie-Française]", lang.getMultitokenSpeller().getSuggestions("Comédie Français").toString());
    }

    @Test
    public void testMultitokenSpeller_4() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Luis Leante").toString());
    }

    @Test
    public void testMultitokenSpeller_5() throws IOException {
        assertEquals("[in vino veritas]", lang.getMultitokenSpeller().getSuggestions("in vinos verita").toString());
    }

    @Test
    public void testMultitokenSpeller_6() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Marina Buisan").toString());
    }

    @Test
    public void testMultitokenSpeller_7() throws IOException {
        assertEquals("[Homo sapiens]", lang.getMultitokenSpeller().getSuggestions("Homos Sapiens").toString());
    }

    @Test
    public void testMultitokenSpeller_8() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Garcia Horta").toString());
    }

    @Test
    public void testMultitokenSpeller_9() throws IOException {
        assertEquals("[John Venn]", lang.getMultitokenSpeller().getSuggestions("Jon Benn").toString());
    }

    @Test
    public void testMultitokenSpeller_10() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("josue garcia").toString());
    }

    @Test
    public void testMultitokenSpeller_11() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Franco more").toString());
    }

    @Test
    public void testMultitokenSpeller_12() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("maria Lopez").toString());
    }

    @Test
    public void testMultitokenSpeller_13() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("carlos fesi").toString());
    }

    @Test
    public void testMultitokenSpeller_14() throws IOException {
        assertEquals("[Nikolai Rimski-Kórsakov]", lang.getMultitokenSpeller().getSuggestions("Nicolai Rimski-Kórsakov").toString());
    }

    @Test
    public void testMultitokenSpeller_15() throws IOException {
        assertEquals("[Rimski-Kórsakov]", lang.getMultitokenSpeller().getSuggestions("Rimsky-Korsakov").toString());
    }

    @Test
    public void testMultitokenSpeller_16() throws IOException {
        assertEquals("[Johann Sebastian Bach]", lang.getMultitokenSpeller().getSuggestions("Johan Sebastián Bach").toString());
    }

    @Test
    public void testMultitokenSpeller_17() throws IOException {
        assertEquals("[Johann Sebastian Bach]", lang.getMultitokenSpeller().getSuggestions("Johan Sebastián Bach").toString());
    }

    @Test
    public void testMultitokenSpeller_18() throws IOException {
        assertEquals("[Johann Sebastian Bach]", lang.getMultitokenSpeller().getSuggestions("Johann Sebastián Bach").toString());
    }

    @Test
    public void testMultitokenSpeller_19() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Plantation Boy").toString());
    }

    @Test
    public void testMultitokenSpeller_20() throws IOException {
        assertEquals("[Woody Allen]", lang.getMultitokenSpeller().getSuggestions("Woodie Alen").toString());
    }

    @Test
    public void testMultitokenSpeller_21() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Eugenio Granjo").toString());
    }

    @Test
    public void testMultitokenSpeller_22() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Julia García").toString());
    }

    @Test
    public void testMultitokenSpeller_23() throws IOException {
        assertEquals("[Deutsche Bank]", lang.getMultitokenSpeller().getSuggestions("Deustche Bank").toString());
    }

    @Test
    public void testMultitokenSpeller_24() throws IOException {
        assertEquals("[Dmitri Mendeléiev]", lang.getMultitokenSpeller().getSuggestions("Dimitri Mendeleev").toString());
    }

    @Test
    public void testMultitokenSpeller_25() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Caralp Mariné").toString());
    }

    @Test
    public void testMultitokenSpeller_26() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Andrew Cyrille").toString());
    }

    @Test
    public void testMultitokenSpeller_27() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Alejandro Varón").toString());
    }

    @Test
    public void testMultitokenSpeller_28() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Alejandro Mellado").toString());
    }

    @Test
    public void testMultitokenSpeller_29() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Alejandro Erazo").toString());
    }

    @Test
    public void testMultitokenSpeller_30() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Alberto Saoner").toString());
    }

    @Test
    public void testMultitokenSpeller_31() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("è più").toString());
    }

    @Test
    public void testMultitokenSpeller_32() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Josep Maria Jové").toString());
    }

    @Test
    public void testMultitokenSpeller_33() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Josep Maria Canudas").toString());
    }

    @Test
    public void testMultitokenSpeller_34() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Francisco Javier Dra.").toString());
    }

    @Test
    public void testMultitokenSpeller_35() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("the usage of our").toString());
    }

    @Test
    public void testMultitokenSpeller_36() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("A paso").toString());
    }

    @Test
    public void testMultitokenSpeller_37() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("A sin").toString());
    }

    @Test
    public void testMultitokenSpeller_38() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("A xente").toString());
    }

    @Test
    public void testMultitokenSpeller_39() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("A lus").toString());
    }

    @Test
    public void testMultitokenSpeller_40() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("A Month").toString());
    }

    @Test
    public void testMultitokenSpeller_41() throws IOException {
        assertEquals("[peix espasa]", lang.getMultitokenSpeller().getSuggestions("peis espaba").toString());
    }

    @Test
    public void testMultitokenSpeller_42() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("Jean-François Davy").toString());
    }

    @Test
    public void testMultitokenSpeller_43() throws IOException {
        assertEquals("[]", lang.getMultitokenSpeller().getSuggestions("finç abui").toString());
    }

    @Test
    public void testMultitokenSpeller_44() throws IOException {
        assertEquals("[Led Zeppelin]", lang.getMultitokenSpeller().getSuggestions("Led Zepelin").toString());
    }

    @Test
    public void testMultitokenSpeller_45() throws IOException {
        assertEquals("[Led Zeppelin]", lang.getMultitokenSpeller().getSuggestions("Led Sepelin").toString());
    }

    @Test
    public void testMultitokenSpeller_46() throws IOException {
        assertEquals("[Marie Curie]", lang.getMultitokenSpeller().getSuggestions("Marie Cuirie").toString());
    }

    @Test
    public void testMultitokenSpeller_47() throws IOException {
        assertEquals("[William Byrd]", lang.getMultitokenSpeller().getSuggestions("William Bird").toString());
    }

    @Test
    public void testMultitokenSpeller_48() throws IOException {
        assertEquals("[Lluís Llach]", lang.getMultitokenSpeller().getSuggestions("Lluis Llach").toString());
    }

    @Test
    public void testMultitokenSpeller_49() throws IOException {
        assertEquals("[University of Texas]", lang.getMultitokenSpeller().getSuggestions("Universiti of Tejas").toString());
    }

    @Test
    public void testMultitokenSpeller_50() throws IOException {
        assertEquals("[Cyndi Lauper]", lang.getMultitokenSpeller().getSuggestions("Cindy Lauper").toString());
    }

    @Test
    public void testMultitokenSpeller_51() throws IOException {
        assertEquals("[García Márquez]", lang.getMultitokenSpeller().getSuggestions("Garcìa Mraquez").toString());
    }

    @Test
    public void testMultitokenSpeller_52() throws IOException {
        assertEquals("[Yuval Noah Harari]", lang.getMultitokenSpeller().getSuggestions("yuval Noha Harari").toString());
    }

    @Test
    public void testMultitokenSpeller_53() throws IOException {
        assertEquals(Collections.emptyList(), lang.getMultitokenSpeller().getSuggestions("Frederic Udina"));
    }

    @Test
    public void testMultitokenSpeller_54() throws IOException {
        assertEquals(Collections.emptyList(), lang.getMultitokenSpeller().getSuggestions("Josep Maria Piñol"));
    }

    @Test
    public void testMultitokenSpeller_55() throws IOException {
        assertEquals("[José María Aznar]", lang.getMultitokenSpeller().getSuggestions("Jose Maria Asnar").toString());
    }

    @Test
    public void testMultitokenSpeller_56() throws IOException {
        assertEquals("[José María Aznar]", lang.getMultitokenSpeller().getSuggestions("José María Asnar").toString());
    }
}
