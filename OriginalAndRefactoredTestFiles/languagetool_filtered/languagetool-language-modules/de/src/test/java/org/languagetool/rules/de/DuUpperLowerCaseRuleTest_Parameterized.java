package org.languagetool.rules.de;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DuUpperLowerCaseRuleTest_Parameterized {

    private final DuUpperLowerCaseRule rule = new DuUpperLowerCaseRule(TestTools.getEnglishMessages());

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("de-DE"));

    private void assertErrors(String input, int expectedMatches) throws IOException {
        AnalyzedSentence sentence = lt.getAnalyzedSentence(input);
        RuleMatch[] matches = rule.match(Collections.singletonList(sentence));
        assertThat("Expected " + expectedMatches + ", got " + matches.length + ": " + sentence.getText() + " -> " + Arrays.toString(matches), matches.length, is(expectedMatches));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to24")
    public void testRule_1to24(String param1, int param2) throws IOException {
        assertErrors(param1, param2);
    }

    static public Stream<Arguments> Provider_testRule_1to24() {
        return Stream.of(arguments("Du bist noch jung.", 0), arguments("Du bist noch jung, du bist noch fit.", 0), arguments("Aber du bist noch jung, du bist noch fit.", 0), arguments("Aber du bist noch jung, dir ist das egal.", 0), arguments("Hast Du ihre Brieftasche gesehen?", 0), arguments("Aber Du bist noch jung, du bist noch fit.", 1), arguments("Aber Du bist noch jung, dir ist das egal.", 1), arguments("Aber Du bist noch jung. Und dir ist das egal.", 1), arguments("Aber du bist noch jung. Und Du bist noch fit.", 1), arguments("Aber du bist noch jung, Dir ist das egal.", 1), arguments("Aber du bist noch jung. Und Dir ist das egal.", 1), arguments("Aber du bist noch jung, sagt euer Vater oft.", 0), arguments("Aber Du bist noch jung, sagt Euer Vater oft.", 0), arguments("Aber Du bist noch jung, sagt euer Vater oft.", 1), arguments("Aber du bist noch jung, sagt Euer Vater oft.", 1), arguments("Könnt Ihr Euch das vorstellen???", 0), arguments("Könnt ihr euch das vorstellen???", 0), arguments("Aber Samstags geht ihr Sohn zum Sport. Stellt Euch das mal vor!", 0), arguments("Wie geht es euch? Herr Meier, wie war ihr Urlaub?", 0), arguments("Wie geht es Euch? Herr Meier, wie war Ihr Urlaub?", 0), arguments("\"Du sagtest, du würdest es schaffen!\"", 0), arguments("Egal, was du tust: Du musst dein Bestes geben.", 0), arguments("Was auch immer du tust: ICH UND DU KÖNNEN ES SCHAFFEN.", 0), arguments("Hast Du die Adresse von ihr?", 0));
    }
}
