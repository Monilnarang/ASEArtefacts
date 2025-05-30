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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JLanguageToolTest_Parameterized {

    private Language lang = Catalan.getInstance();

    private JLanguageTool tool = new JLanguageTool(lang);

    @ParameterizedTest
    @MethodSource("Provider_testAdvancedTypography_1to8")
    public void testAdvancedTypography_1to8(String param1, String param2) throws IOException {
        assertEquals(lang.toAdvancedTypography(param2), param1);
    }

    static public Stream<Arguments> Provider_testAdvancedTypography_1to8() {
        return Stream.of(arguments("És l’«hora»!", "És l'\"hora\"!"), arguments("És l’‘hora’!", "És l''hora'!"), arguments("És l’«hora»!", "És l'«hora»!"), arguments("És l’‘hora’.", "És l''hora'."), arguments("Cal evitar el «‘lo’ neutre».", "Cal evitar el \"'lo' neutre\"."), arguments("És «molt ‘important’».", "És \"molt 'important'\"."), arguments("Si és del v.\u00a0‘haver’.", "Si és del v. 'haver'."), arguments("Amb el so de ‘s’.", "Amb el so de 's'."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAdvancedTypography_9to19")
    public void testAdvancedTypography_9to19(String param1, String param2) throws IOException {
        assertEquals(lang.adaptSuggestion(param2), param1);
    }

    static public Stream<Arguments> Provider_testAdvancedTypography_9to19() {
        return Stream.of(arguments("L'IEC", "L'IEC"), arguments("t'estimava", "te estimava"), arguments("l'Albert", "el Albert"), arguments("l'Albert", "l'Albert"), arguments("l'«Albert»", "l'«Albert»"), arguments("l’«Albert»", "l’«Albert»"), arguments("l'\"Albert\"", "l'\"Albert\""), arguments("em tancava", "m'tancava"), arguments("es tancava", "s'tancava"), arguments("l'R+D", "l'R+D"), arguments("l'FBI", "l'FBI"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMultitokenSpeller_1to52_55to56")
    public void testMultitokenSpeller_1to52_55to56(String param1, String param2) throws IOException {
        assertEquals(param1, lang.getMultitokenSpeller().getSuggestions(param2).toString());
    }

    static public Stream<Arguments> Provider_testMultitokenSpeller_1to52_55to56() {
        return Stream.of(arguments("[Jacques-Louis David]", "Jacques Louis David"), arguments("[Chiang Kai-shek]", "Chiang Kaishek"), arguments("[Comédie-Française]", "Comédie Français"), arguments("[]", "Luis Leante"), arguments("[in vino veritas]", "in vinos verita"), arguments("[]", "Marina Buisan"), arguments("[Homo sapiens]", "Homos Sapiens"), arguments("[]", "Garcia Horta"), arguments("[John Venn]", "Jon Benn"), arguments("[]", "josue garcia"), arguments("[]", "Franco more"), arguments("[]", "maria Lopez"), arguments("[]", "carlos fesi"), arguments("[Nikolai Rimski-Kórsakov]", "Nicolai Rimski-Kórsakov"), arguments("[Rimski-Kórsakov]", "Rimsky-Korsakov"), arguments("[Johann Sebastian Bach]", "Johan Sebastián Bach"), arguments("[Johann Sebastian Bach]", "Johan Sebastián Bach"), arguments("[Johann Sebastian Bach]", "Johann Sebastián Bach"), arguments("[]", "Plantation Boy"), arguments("[Woody Allen]", "Woodie Alen"), arguments("[]", "Eugenio Granjo"), arguments("[]", "Julia García"), arguments("[Deutsche Bank]", "Deustche Bank"), arguments("[Dmitri Mendeléiev]", "Dimitri Mendeleev"), arguments("[]", "Caralp Mariné"), arguments("[]", "Andrew Cyrille"), arguments("[]", "Alejandro Varón"), arguments("[]", "Alejandro Mellado"), arguments("[]", "Alejandro Erazo"), arguments("[]", "Alberto Saoner"), arguments("[]", "è più"), arguments("[]", "Josep Maria Jové"), arguments("[]", "Josep Maria Canudas"), arguments("[]", "Francisco Javier Dra."), arguments("[]", "the usage of our"), arguments("[]", "A paso"), arguments("[]", "A sin"), arguments("[]", "A xente"), arguments("[]", "A lus"), arguments("[]", "A Month"), arguments("[peix espasa]", "peis espaba"), arguments("[]", "Jean-François Davy"), arguments("[]", "finç abui"), arguments("[Led Zeppelin]", "Led Zepelin"), arguments("[Led Zeppelin]", "Led Sepelin"), arguments("[Marie Curie]", "Marie Cuirie"), arguments("[William Byrd]", "William Bird"), arguments("[Lluís Llach]", "Lluis Llach"), arguments("[University of Texas]", "Universiti of Tejas"), arguments("[Cyndi Lauper]", "Cindy Lauper"), arguments("[García Márquez]", "Garcìa Mraquez"), arguments("[Yuval Noah Harari]", "yuval Noha Harari"), arguments("[José María Aznar]", "Jose Maria Asnar"), arguments("[José María Aznar]", "José María Asnar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMultitokenSpeller_53to54")
    public void testMultitokenSpeller_53to54(String param1) throws IOException {
        assertEquals(Collections.emptyList(), lang.getMultitokenSpeller().getSuggestions(param1));
    }

    static public Stream<Arguments> Provider_testMultitokenSpeller_53to54() {
        return Stream.of(arguments("Frederic Udina"), arguments("Josep Maria Piñol"));
    }
}
